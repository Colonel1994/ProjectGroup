package dollar.com.travel_v;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private HomeFragment homeFragment;
    private TravelFragment travelFragment;
    private MoneyFragment moneyFragment;
    private OtherFragment otherFragment;
    private TextView tvName;
    private ImageView imvAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }
    private void initComponents() {
        tvName = (TextView)findViewById(R.id.tv_name);
        imvAvatar = (ImageView)findViewById(R.id.img_avatar);
        findViewById(R.id.btn_home).setOnClickListener(this);
        findViewById(R.id.btn_search).setOnClickListener(this);
        findViewById(R.id.btn_travel).setOnClickListener(this);
        findViewById(R.id.btn_money).setOnClickListener(this);
        findViewById(R.id.btn_other).setOnClickListener(this);

        if (homeFragment == null){
            homeFragment = new HomeFragment();
        }
        replaceFragment(homeFragment);

        /*Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            String hello = (String)bundle.get("Name");
            tvName.setText(hello);
        }*/
    }

    private void replaceFragment(Fragment fragment) {
        Fragment f = getSupportFragmentManager()
                .findFragmentByTag(fragment.getClass().getName());
        if (f != null && f == fragment) {
            if (fragment.isVisible()) {
                return;
            }
            getSupportFragmentManager().beginTransaction()
                    .show(fragment)
                    .commit();
            return;
        }

        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment, fragment.getClass().getName())
                .addToBackStack(null)
                .commit();
        /*getSupportFragmentManager().beginTransaction()
                .add(R.id.content, fragment).show(fragment)
                .commit();*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_home:
                replaceFragment(homeFragment);
                break;
            case R.id.btn_search:

                break;
            case R.id.btn_travel:

                if (travelFragment == null){
                    travelFragment = new TravelFragment();
                }
                replaceFragment(travelFragment);
                break;
            case R.id.btn_money:
                if (moneyFragment == null){
                    moneyFragment = new MoneyFragment();
                }
                replaceFragment(moneyFragment);
                break;
            case R.id.btn_favorite:

                break;
            case R.id.btn_other:
                if (otherFragment == null){
                    otherFragment = new OtherFragment();
                }
                replaceFragment(otherFragment);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
