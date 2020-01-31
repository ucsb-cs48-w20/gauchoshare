package com.ucsb.integration.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ucsb.integration.MainActivity;
import com.ucsb.integration.R;

public class BasicLoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button signup;
    Button login;
    Button gsign_in_button;
    Button fbsign_in_button;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_login);


        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signup=findViewById(R.id.signup);
        login=findViewById(R.id.login);
        gsign_in_button=findViewById(R.id.gsign_in_button);
        fbsign_in_button=findViewById(R.id.fbsign_in_button);



        firebaseAuth=FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(BasicLoginActivity.this,"sign up successful",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(BasicLoginActivity.this, MainActivity.class));
                        }
                        else{
                            Toast.makeText(BasicLoginActivity.this,"failed to sign up",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(BasicLoginActivity.this,"log in successful",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(BasicLoginActivity.this,MainActivity.class));
                        }
                        else{
                            Toast.makeText(BasicLoginActivity.this,"failed to log in",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        gsign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BasicLoginActivity.this, GoogleLoginActivity.class));
            }
        });

        fbsign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BasicLoginActivity.this, FacebookLoginActivity.class));
            }
        });

    }
}
