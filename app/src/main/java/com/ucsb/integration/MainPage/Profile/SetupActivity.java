package com.ucsb.integration.MainPage.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ucsb.integration.MainPage.FindActivity;
import com.ucsb.integration.R;

import java.util.HashMap;

public class SetupActivity extends AppCompatActivity {

    private EditText UserName, FullName, Email, PhoneNumber;
    private Button SaveInformationbutton;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    private FirebaseDatabase database;

    String currentUserID;

    private static final String TAG = "your activity name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        Log.d(TAG, "User ID= "+ currentUserID);
//        UsersRef = FirebaseDatabase.getInstance().getReference("Users");
        database = FirebaseDatabase.getInstance();
        UsersRef = database.getReference().child("Users").child(currentUserID);

        UserName = (EditText) findViewById(R.id.setup_username);
        FullName = (EditText) findViewById(R.id.setup_full_name);
        Email = (EditText) findViewById(R.id.setup_email);
        PhoneNumber = (EditText) findViewById(R.id.setup_phone_number);
        SaveInformationbutton = (Button) findViewById(R.id.setup_information_button);

        SaveInformationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveAccountSetupInformation();
            }
        });
    }

    private void SaveAccountSetupInformation() {
        String username = UserName.getText().toString();
        String fullname = FullName.getText().toString();
        String email = Email.getText().toString();
        String phonenumber = PhoneNumber.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please provide your username...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(fullname)) {
            Toast.makeText(this, "Please provide your Full Name...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please provide your Email Address...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(phonenumber)) {
            Toast.makeText(this, "Please provide your Phone Number...", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, Object> userMap = new HashMap<>();
            userMap.put("username", username);
            userMap.put("fullname", fullname);
            userMap.put("email", email);
            userMap.put("phonenumber", phonenumber);
            UsersRef.updateChildren(userMap).addOnCompleteListener((new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        SendUserToMainActivity();
                        Toast.makeText(SetupActivity.this, "Your account has been successfully created..", Toast.LENGTH_LONG).show();
                    } else {

                        String message = task.getException().getMessage();
                        Toast.makeText(SetupActivity.this, "Error Occurred: " + message, Toast.LENGTH_SHORT).show();
                    }
                }
            }));
        }

    }

    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(SetupActivity.this, FindActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
