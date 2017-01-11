package pt.isec.a21240321.pplfinder;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import pt.isec.a21240321.pplfinder.Exceptions.FirebaseException;

public class LoginActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener{

    private static final int RC_SIGN_IN = 10;
    SignInButton mSignInButton;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.checkNetworkConnection();

        // <editor-fold defaultstate="collapsed" desc="Google Sign In">

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // </editor-fold>

        mSignInButton = (SignInButton)findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new SignInListener());
    }

    // <editor-fold defaultstate="collapsed" desc="Google Sign In">
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount userAccoutInfo = result.getSignInAccount();

            // <editor-fold defaultstate="collapsed" desc="Create User">
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Adicionar ao Firebase">

            //Se já existir vai buscar todas as informações e altera só o activo = true
            //Se não existir cria um novo e adiciona-o lá

            try {
                FirebaseConnection firebaseConnection = new FirebaseConnection();
                User user = firebaseConnection.getUserById(userAccoutInfo.getId());
                if(user == null){
                    user = new User(userAccoutInfo.getId(), userAccoutInfo.getDisplayName(),
                            userAccoutInfo.getEmail(), true);
                    firebaseConnection.addUserToFirebase(user);
                }


            } catch (FirebaseException e) {
                ErrorDialog errorDialog = new ErrorDialog(R.string.firebase_error_title,
                        R.string.firebase_error_message, R.drawable.ic_error);
                errorDialog.show(getFragmentManager(), "FirebaseException");
                if(errorDialog.isToClose())
                    finish();
            }


                /*
                DatabaseReference mSpotFoodDataBaseReference = FirebaseDatabase.getInstance().getReference();
                User user = new User("1");
                mSpotFoodDataBaseReference.child("USERS").child(user.getId()).setValue(user);
                */
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Chamar MainActivity">
            // </editor-fold>
            
            //iv.setImageBitmap(getImageBitmap(acct.getPhotoUrl().toString()));
            //iv.setImageURI(acct.getPhotoUrl());


            Uri uri = userAccoutInfo.getPhotoUrl();


            if(uri != null) {
                //mStatusTextView.setText(uri.toString());
                //new MainActivity.DownloadImageTask(mImageView).execute(uri.toString());
            }

            //Intent intent = new Intent(this, MainActivity.class);
            //intent.putExtra("NOME", acct.getDisplayName());
            //intent.putExtra("GIVENNAME", acct.getGivenName());
            //startActivity(intent);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Internet Connection">
    private void checkNetworkConnection(){
        if(!hasNetworkConnection()) {
            ErrorDialog errorDialog = new ErrorDialog(R.string.error_connection_title,
                    R.string.error_connection_message, R.drawable.ic_error);
            errorDialog.show(getFragmentManager(), "NoInternetConnection");
            if(errorDialog.isToClose())
                finish();
        }
    }

    //Check if there is internet connection on android phone
    private boolean hasNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    // </editor-fold>

    class SignInListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }
}
