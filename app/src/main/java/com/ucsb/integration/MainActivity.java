package com.ucsb.integration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ucsb.integration.MainPage.Find.FindActivity;
import com.ucsb.integration.MainPage.Listing.DirectionActivity;
import com.ucsb.integration.MainPage.Message.MessageActivity;
import com.ucsb.integration.MainPage.Profile.ProfileActivity;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button find;
    Button share;
    Button message;
    Button profile;
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    private FirebaseDatabase database;

    String currentUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        UsersRef = database.getReference().child("Users");

        find = findViewById(R.id.find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FindActivity.class));
            }
        });

        share = findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DirectionActivity.class));
            }
        });

        message = findViewById(R.id.message);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MessageActivity.class));
            }
        });

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference tempReference = UsersRef.child(currentUserID);
                tempReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map <String, Object> data = (Map<String, Object>) dataSnapshot.getValue();
                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                        intent.putExtra("username", data.get("username").toString());
                        intent.putExtra("fullname", data.get("fullname").toString());
                        intent.putExtra("email", data.get("email").toString());
                        intent.putExtra("phonenumber", data.get("phonenumber").toString());
                        intent.putExtra("id", data.get("id").toString());
                        intent.putExtra("imageURL", data.get("imageURL").toString());
                        intent.putExtra("venmo", data.get("venmo").toString());
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    //getSupportActionBar().hide();

}
