package com.ucsb.integration.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.ucsb.integration.R;


public class GoogleLoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN=9001;
    private GoogleSignInClient mGoogleSignInClient;
    private String TAG ="GoogleLoginActivity";
    private FirebaseAuth mAuth;
    //private Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_login);

        mAuth =FirebaseAuth.getInstance();
        //btnSignOut= findViewById(R.id.sign_out_button);


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signIn();

        /*btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut();
                Toast.makeText(MainActivity.this, "You are Logged Out", Toast.LENGTH_SHORT).show();
                btnSignOut.setVisibility(View.INVISIBLE);
                //Should be Invisible at the beginning(Unsolved)
            }
        });*/


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
            Toast.makeText(GoogleLoginActivity.this, "Signed In Successfully", Toast.LENGTH_SHORT).show();

            firebaseAuthWithGoogle(acc);

        }
        catch(ApiException e){
            Toast.makeText(GoogleLoginActivity.this, "Failed to Signed In", Toast.LENGTH_SHORT).show();
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
                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                    updateUI(null);
                }
            }
        });
    }


    private void updateUI(FirebaseUser fuser){
        //btnSignOut.setVisibility(View.VISIBLE);
        Toast.makeText(GoogleLoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
    }


}
