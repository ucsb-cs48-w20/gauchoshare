package com.ucsb.integration.MainPage.Profile;




import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ucsb.integration.MainActivity;
import com.ucsb.integration.MainPage.Find.FindActivity;
import com.ucsb.integration.R;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    private FirebaseDatabase database;
    String currentUserID;
    Map<String, Object> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        data = new HashMap<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        UsersRef = database.getReference().child("Users").child(currentUserID);

        final TextView userNameView = (TextView) findViewById(R.id.username);
        final TextView fullNameView = (TextView) findViewById(R.id.full_name);
        final TextView emailView = (TextView) findViewById(R.id.email);
        final TextView phoneNumberView = (TextView) findViewById(R.id.phone_number);

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data = (Map<String, Object>) dataSnapshot.getValue();
                    userNameView.setText(data.get("username").toString());
                    fullNameView.setText(data.get("fullname").toString());
                    emailView.setText(data.get("email").toString());
                    phoneNumberView.setText(data.get("phonenumber").toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
