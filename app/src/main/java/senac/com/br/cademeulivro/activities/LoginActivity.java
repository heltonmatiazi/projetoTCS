package senac.com.br.cademeulivro.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

// por gentileza não removam esses imports. Não perguntem.
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.google.android.gms.common.api.ResultCallback;
import java.util.concurrent.Callable;
import android.view.View.OnClickListener;

import senac.com.br.cademeulivro.R;
import senac.com.br.cademeulivro.helpers.InstanceController;


public class LoginActivity extends AppCompatActivity   {
    // referencia estática para login com google
    private static final int RC_SIGN_IN = 9001;
    // references dos botões de login.
    private SignInButton mGoogleSignInButton;
    private LoginButton mFacebookSignInButton;
    // controles de instancia
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager mFacebookCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inicializando a sdk do facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        mFacebookCallbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        // instanciando o botão do google
        mGoogleSignInButton = (SignInButton)findViewById(R.id.google_button);
        mGoogleSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });
        // instanciando o botão do facebook
        mFacebookSignInButton = (LoginButton)findViewById(R.id.facebook_button);
        // registrando a ação de callBack que invoca o login no facebook
        mFacebookSignInButton.registerCallback(mFacebookCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        handleSignInResult(new Callable<Void>() {
                            @Override
                            public Void call() throws Exception {
                                LoginManager.getInstance().logOut();
                                return null;
                            }
                        });
                    }
                    @Override
                    public void onCancel() {
                        handleSignInResult(null);
                    }
                    @Override
                    public void onError(FacebookException error) {
                        Log.d(LoginActivity.class.getCanonicalName(), error.getMessage());
                        handleSignInResult(null);
                    }
                }
        );
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // resultado do intent GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()) {
                final GoogleApiClient client = mGoogleApiClient;
                    handleSignInResult(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        if (client != null) {
                            Auth.GoogleSignInApi.signOut(client).setResultCallback(
                                    new ResultCallback<Status>() {
                                        @Override
                                        public void onResult(Status status) {
                                            Log.d(LoginActivity.class.getCanonicalName(),
                                                    status.getStatusMessage());
                                        }
                                    }
                            );
                        }
                        return null;
                    }
                });
            }
        } else {
            mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void signInWithGoogle() {
        if(mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        final Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void handleSignInResult(Callable<Void> logout) {
        if(logout == null) {
            /* Login error */
            Toast.makeText(getApplicationContext(), R.string.login_error, Toast.LENGTH_SHORT).show();
        } else {
            /* Login success */
            InstanceController.getInstance().setLogoutCallable(logout);
            startActivity(new Intent(this, MainActivity.class));
        }
    }

}