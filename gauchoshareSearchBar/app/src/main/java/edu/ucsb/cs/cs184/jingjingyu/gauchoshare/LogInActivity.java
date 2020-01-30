package edu.ucsb.cs.cs184.jingjingyu.gauchoshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LogInActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button signup;
    Button login;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signup=findViewById(R.id.sign_up);
        login=findViewById(R.id.log_in);




        firebaseAuth=FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LogInActivity.this,"sign up successful",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LogInActivity.this,MainActivity.class));
                        }
                        else{
                            Toast.makeText(LogInActivity.this,"failed to sign up",Toast.LENGTH_LONG).show();
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
                            Toast.makeText(LogInActivity.this,"log in successful",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LogInActivity.this,MainActivity.class));
                        }
                        else{
                            Toast.makeText(LogInActivity.this,"failed to log in",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}
