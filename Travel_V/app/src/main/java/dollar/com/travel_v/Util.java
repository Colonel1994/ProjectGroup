package dollar.com.travel_v;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.Map;

/**
 * Created by anhch_000 on 14/03/2017.
 */

public class Util {

    public static Uri.Builder builderParams(Uri.Builder builder, Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        return builder;
    }

    public static void showToast(String message) {
        Toast.makeText(App.getContext(), message, Toast.LENGTH_SHORT).show();
    }


    public static boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        }
        return false;
    }

    public static void showLog(String message) {
        if (!TextUtils.isEmpty(message)) {

            Log.d("chuoiiiiiiiiii : ", message);
        }
    }
}