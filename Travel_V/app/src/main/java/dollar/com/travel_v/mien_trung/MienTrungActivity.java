package dollar.com.travel_v.mien_trung;

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

public class MienTrungActivity extends Activity {

    private RecyclerView recyclerView;

    private MienTrungAdapter mienTrungAdapter;

    private ArrayList<MienTrung> mienTrungs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_layout_mien_bac);
        init();
    }

    private void init() {
        new GetDataAsynTask().execute();
        mienTrungs = new ArrayList<>();
        mienTrungAdapter = new MienTrungAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_layout_bac);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private class GetDataAsynTask extends AsyncTask<Void, Void, Boolean> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MienTrungActivity.this);
            progressDialog.setMessage("chờ tý nha ...");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                mienTrungAdapter.setMienTrungs(mienTrungs);
                recyclerView.setAdapter(mienTrungAdapter);
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
                        MienTrung mienTrung = new MienTrung();
                        mienTrung.setId(object.getInt("id"));
                        /*if (!TextUtils.isEmpty(object.getString("image"))) {
                            byte[] bytes = Base64.decode(object.getString("image"), Base64.DEFAULT);

                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                            mienBac.setImage(bitmap);
                        }*/
                        mienTrung.setName(object.getString("name"));
                        mienTrung.setTitle(object.getString("title"));
                        mienTrung.setImage(object.getString("image"));
                        mienTrungs.add(mienTrung);
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
