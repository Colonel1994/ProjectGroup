package dollar.com.travel_v.mien_nam;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dollar.com.travel_v.API;
import dollar.com.travel_v.App;
import dollar.com.travel_v.R;

/**
 * Created by anhch_000 on 30/03/2017.
 */

public class MienNamActivity extends Activity {
    private RecyclerView recyclerView;

    private MienNamAdapter mienNamAdapter;

    private ArrayList<MienNam> mienNams;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_layout_mien_bac);
        init();
    }

    private void init() {
        new GetDataAsynTask().execute();
        mienNams = new ArrayList<>();
        mienNamAdapter = new MienNamAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_layout_bac);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private class GetDataAsynTask extends AsyncTask<Void, Void, Boolean> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MienNamActivity.this);
            progressDialog.setMessage("chờ tý nha ...");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                mienNamAdapter.setMienNams(mienNams);
                recyclerView.setAdapter(mienNamAdapter);
            } else {
                Toast.makeText(App.getContext(), "Faile.....!", Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
            super.onPostExecute(aBoolean);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String jsonString = API.callService(API.GET_QUAN_LY_URL, null);

            if (!TextUtils.isEmpty(jsonString)) {
                try {

                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray jsonArray = jsonObject.getJSONArray("tblmienbac");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        MienNam mienNam = new MienNam();
                        mienNam.setId(object.getInt("id"));
                        /*if (!TextUtils.isEmpty(object.getString("image"))) {
                            byte[] bytes = Base64.decode(object.getString("image"), Base64.DEFAULT);

                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                            mienBac.setImage(bitmap);
                        }*/
                        mienNam.setName(object.getString("name"));
                        mienNam.setTitle(object.getString("title"));
                        mienNam.setImage(object.getString("image"));
                        mienNams.add(mienNam);
                    }
                    return true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return false;
        }
    }

}
