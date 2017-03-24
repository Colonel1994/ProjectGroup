package dollar.com.travel_v;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by anhch_000 on 14/03/2017.
 */

public class API {

    public static final String SCHEME = "http";
    public static final String HOST = "192.168.0.103";
    public static final String PATH = "WebService";

    public static final String PATH_QUANLY = "MienBac/";

    public static final String GET_QUAN_LY_URL = PATH_QUANLY + "QuanlyGet.php";


    public static String callService(String url, Map<String, String> getParams) {
        return callService(url, getParams, null);
    }

    public static String callService(String url, Map<String, String> getParams, Map<String, String> postParams) {
        String response = null;
        HttpURLConnection connect;
        InputStream is;

        Uri.Builder builder = new Uri.Builder()
                .scheme(SCHEME)
                .authority(HOST)
                .appendPath(PATH)
                .appendEncodedPath(url);

        if (getParams != null) {
            builder = Util.builderParams(builder, getParams);
        }
        try {
            connect = (HttpURLConnection) new URL(builder.build().toString()).openConnection();
            connect.setRequestProperty("accept", "application/json");
            connect.setRequestProperty("Connection", "close");
            connect.setDoInput(true);
            connect.setConnectTimeout(5000);

            if (postParams != null) {
                Uri.Builder builderPostParams = new Uri.Builder();
                builderPostParams = Util.builderParams(builderPostParams, postParams);

                connect.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded;charset=UTF-8");
                connect.setRequestMethod("POST");
                connect.setDoOutput(true);
                connect.setReadTimeout(5000);

                OutputStream outputStream = connect.getOutputStream();
                outputStream.write(builderPostParams.build().getEncodedQuery().getBytes());
                outputStream.close();
            } else connect.setRequestMethod("GET");

            is = connect.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            response = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Util.showLog(response);
        return response;
    }
}

