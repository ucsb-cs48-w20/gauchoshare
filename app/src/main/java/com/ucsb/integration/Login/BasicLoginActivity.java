package com.ucsb.integration.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.ucsb.integration.MainActivity;
import com.ucsb.integration.MainPage.Profile.SetupActivity;
import com.ucsb.integration.R;




public class BasicLoginActivity extends AppCompatActivity {

    private SignInButton signInButton;
    private static final int RC_SIGN_IN=9001;
    private GoogleSignInClient mGoogleSignInClient;
    private String TAG ="BasicLoginActivity";
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    //private Button btnSignOut;
    EditText email;
    EditText password;
    Button signup;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_basic_login);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signup=findViewById(R.id.signup);
        login=findViewById(R.id.login);

        loadingBar = new ProgressDialog(this);

        signInButton = (SignInButton) findViewById(R.id.gsign_in_button);
        mAuth =FirebaseAuth.getInstance();
        //btnSignOut= findViewById(R.id.sign_out_button);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar.setTitle("Creating New Account");
                loadingBar.setMessage("Please wait while your account is being created...");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(false);

                mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(BasicLoginActivity.this,"sign up successful",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(BasicLoginActivity.this, SetupActivity.class));
                            loadingBar.dismiss();
                        }
                        else {
                            Toast.makeText(BasicLoginActivity.this,"failed to sign up",Toast.LENGTH_LONG).show();
                            loadingBar.dismiss();
                        }
                    }
                });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar.setTitle("Logging In");
                loadingBar.setMessage("Please wait while you are being logged in...");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(false);                mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(BasicLoginActivity.this,"log in successful",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(BasicLoginActivity.this, MainActivity.class));
                            loadingBar.dismiss();
                        }
                        else {
                            Toast.makeText(BasicLoginActivity.this,"failed to log in",Toast.LENGTH_LONG).show();
                            loadingBar.dismiss();
                        }
                    }
                });
            }
        });



        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signIn();
            }
        });

       /* btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut();
                Toast.makeText(BasicLoginActivity.this, "You are Logged Out", Toast.LENGTH_SHORT).show();
                btnSignOut.setVisibility(View.INVISIBLE);
                //Should be Invisible at the beginning(Unsolved)
            }
        });
*/

    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult (Task<GoogleSignInAccount> completeTask){
        try{
            GoogleSignInAccount acc= completeTask.getResult(ApiException.class);
            Toast.makeText(BasicLoginActivity.this, "Signed In Successfully", Toast.LENGTH_SHORT).show();
            firebaseAuthWithGoogle(acc);

        }
        catch(ApiException e){
            Toast.makeText(BasicLoginActivity.this, "Failed to Signed In", Toast.LENGTH_SHORT).show();
            firebaseAuthWithGoogle(null);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acc) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acc.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                    if (isNew) {
                        startActivity(new Intent(BasicLoginActivity.this, SetupActivity.class));
                    } else {
                        startActivity(new Intent(BasicLoginActivity.this, MainActivity.class));
                    }
                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                    updateUI(null);
                }
            }
        });
    }

    //updateUI for Later use
    private void updateUI(FirebaseUser fuser){
        //btnSignOut.setVisibility(View.VISIBLE);
        Toast.makeText(BasicLoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
    }


}