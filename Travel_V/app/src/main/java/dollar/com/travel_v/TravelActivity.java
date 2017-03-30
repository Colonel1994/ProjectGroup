package dollar.com.travel_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dollar.com.travel_v.mien_bac.MienBacActivity;

/**
 * Created by Administrator on 2/7/2017.
 */

public class TravelActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imageView;
    private TextView textView;
    private MienBacActivity mienBacActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_layout);
        init();
    }


    private void init() {

        imageView = (ImageView) findViewById(R.id.btn_bac);
        imageView.setOnClickListener(this);
        textView = (TextView) findViewById(R.id.textView);
        textView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MienBacActivity.class);
        startActivity(intent);
    }

}


