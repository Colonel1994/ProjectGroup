package dollar.com.travel_v;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;

public class LoginActivity extends FragmentActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient googleApiClient;
    private GoogleApiClient.OnConnectionFailedListener connectionFailedListener;
    private SignInButton signInButton;
    private ProgressDialog mProgressDialog;
    private OtherFragment otherFragment;
    private TextView txtStatus;
    private ImageView imgAvatar;
    private Intent intent;

    private TextView tvName;
    private Button btnFaceboook;
    private CallbackManager mCallbackManager;

    private Profile profile;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;
    private ProfileTracker profileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        imgAvatar = (ImageView) findViewById(R.id.img_avatar);
        txtStatus = (TextView) findViewById(R.id.tv_status);

        tvName = (TextView)findViewById(R.id.tv_name);

        findViewById(R.id.btn_login_google).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.disconnect_button).setOnClickListener(this);
        findViewById(R.id.btn_login_facebook).setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton = (SignInButton) findViewById(R.id.btn_login_google);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setBackgroundColor(SignInButton.COLOR_AUTO);
        signInButton.setColorScheme(SignInButton.COLOR_AUTO);
        signInButton.setScopes(gso.getScopeArray());

    }
    private void loginFacebook(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                tvName.setText("User ID:"+loginResult.getAccessToken().getUserId()
                        +"\n"+"Auth Token:"+loginResult.getAccessToken().getToken());
                Toast.makeText(getBaseContext(),"Login successful !",Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getBaseContext(),"Login has been canceled !",Toast.LENGTH_SHORT).show();
                /*tvName.setText("Đăng nhập đã bị hủy bỏ !");*/
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getBaseContext(),"Login failed !",Toast.LENGTH_SHORT).show();
                /*tvName.setText("Login attempt failed !");*/
            }
        });
        LoginManager.getInstance().logInWithPublishPermissions(this, Arrays.asList("publish_actions"));

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Đã đăng nhập, lưu accessToken lại và làm việc sau khi đăng nhập
                accessToken = currentAccessToken;
            }
        };
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile profile, Profile profile1) {
                tvName.setText(displayMessage(profile1));
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    /*@Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);

        tvStattus = (TextView)findViewById(R.id.tv_name);
        tvStattus.setText("You are not logged in");
        btnFaceboook = (LoginButton)findViewById(R.id.btn_facebook);
        btnFaceboook.setCompoundDrawables(null, null, null, null);
        btnFaceboook.setReadPermissions("user_friends");
        btnFaceboook.registerCallback(mCallbackManager, mFacebookCallback);
    }*/

    /*private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            profile = Profile.getCurrentProfile();
            tvName.setText(displayMessage(profile));
        }
        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };*/

    @Override
    public void onResume() {
        super.onResume();
        /*Profile profile = Profile.getCurrentProfile();*/
        /*tvName.setText(displayMessage(profile));*/
    }

    /*@Override
    public void onStop() {
        super.onStop();
        profileTracker.stopTracking();
        accessTokenTracker.stopTracking();
    }*/

    private String displayMessage(Profile profile) {
        StringBuilder stringBuilder = new StringBuilder();
        if (profile != null) {
            stringBuilder.append("Logged In " + profile.getFirstName());
            Toast.makeText(this, "Start Playing with the data " + profile.getFirstName(), Toast.LENGTH_SHORT).show();
        } else {
            stringBuilder.append("You are not logged in");
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            /*showProgressDialog();*/
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);

                }
            });
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }
    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.hide();
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            /*if (acct.getPhotoUrl()!=null){
                new LoadProfileImage(imgAvatar).execute(acct.getPhotoUrl().toString());
            }*/

            txtStatus.setText(acct.getDisplayName() + "\n" + acct.getEmail());

            String Name = txtStatus.getText().toString();

            intent = new Intent(this,MainActivity.class);

            intent.putExtra("Name",Name);
            startActivity(intent);

            updateUI(true);

        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... uri) {
            String url = uri[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {

            if (result != null) {

                Bitmap resized = Bitmap.createScaledBitmap(result, 200, 200, true);
                bmImage.setImageBitmap(ImageHelper.getRoundedCornerBitmap(getBaseContext(), resized, 250, 200, 200, false, false, false, false));

            }
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
                findViewById(R.id.btn_login_google).setVisibility(View.GONE);
                findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
            } else {
                txtStatus.setText(R.string.signed_out);

                findViewById(R.id.btn_login_google).setVisibility(View.VISIBLE);
                findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
            }
        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            Log.d(TAG, "onConnectionFailed:" + connectionResult);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_login_google:
                    signIn();
                    break;
                case R.id.sign_out_button:
                    signOut();
                    break;
                case R.id.disconnect_button:
                    revokeAccess();
                    break;
                case R.id.btn_login_facebook:
                    loginFacebook();
                    break;
                default:
                    break;
            }
        }
    }
