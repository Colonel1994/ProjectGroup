package dollar.com.travel_v;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /*Login-------------------------*/
    private static final String TAG = "MainActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient googleApiClient;
    private GoogleApiClient.OnConnectionFailedListener connectionFailedListener;
    private SignInButton signInButton;
    private ProgressDialog mProgressDialog;
    private FragmentActivity fragmentActivity;
    private TextView tvStatus;
    private ImageView imgAvatar;
    /*---------------------------*/

    private HomeFragment homeFragment;
    private TravelFragment travelFragment;
    private MoneyFragment moneyFragment;
    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }
    private void initComponents() {
        findViewById(R.id.btn_home).setOnClickListener(this);
        findViewById(R.id.btn_search).setOnClickListener(this);
        findViewById(R.id.btn_travel).setOnClickListener(this);
        findViewById(R.id.btn_money).setOnClickListener(this);

        if (homeFragment == null){
            homeFragment = new HomeFragment();
            homeFragment.setMenuVisibility(homeFragment.isVisible());
        }
        replaceFragment(homeFragment);

        /*if (loginFragment == null){
            loginFragment = new LoginFragment();
        }
        replaceFragment(loginFragment);*/

        /*Login-------------------------*/
        /*imgAvatar = (ImageView)findViewById(R.id.img_avatar);*/
        /*tvStatus = (TextView)findViewById(R.id.tv_status);*/
        findViewById(R.id.btn_google).setOnClickListener(this);
        /*findViewById(R.id.sign_out_google).setOnClickListener(this);
        findViewById(R.id.disconnect_google).setOnClickListener(this);*/

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, (GoogleApiClient.OnConnectionFailedListener) this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton = (SignInButton) findViewById(R.id.btn_google);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
        /*---------------------------*/


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
                /*rlct.setVisibility(View.GONE);
                rltv.setVisibility(View.VISIBLE);*/
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
            case R.id.btn_google:
                signIn();
                break;
            /*case R.id.sign_out_google:
                signOut();
                break;
            case R.id.disconnect_google:
                revokeAccess();
                break;*/

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    /*Login-------------------------*/

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void hideProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.e(TAG, "getPhotoUrl:" +acct.getPhotoUrl());
            Uri personPhoto = acct.getPhotoUrl();
            imgAvatar.setImageURI(personPhoto);
            /*tvStatus.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()+"\n"+acct.getEmail()));*/

            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.btn_google).setVisibility(View.GONE);
            /*findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);*/
        } else {
            tvStatus.setText(R.string.signed_out);

            findViewById(R.id.btn_google).setVisibility(View.VISIBLE);
            /*findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);*/
        }
    }


}
