package com.ucsb.integration.MainPage.Profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.ucsb.integration.R;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    private FirebaseDatabase database;
    String currentUserID;
    Map<String, Object> data;
    private ImageView mImageView;
    private Button mEditProfile;

    boolean onProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        data = new HashMap<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        onProfile = false;

        mEditProfile = (Button) findViewById(R.id.btn_edit_profile);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        UsersRef = database.getReference().child("Users").child(currentUserID);
        mImageView = findViewById(R.id.profile_image);

        final TextView userNameView = (TextView) findViewById(R.id.username);
        final TextView fullNameView = (TextView) findViewById(R.id.full_name);
        final TextView emailView = (TextView) findViewById(R.id.email);
        final TextView phoneNumberView = (TextView) findViewById(R.id.phone_number);
        final TextView venmo = (TextView) findViewById(R.id.venmo);

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data = (Map<String, Object>) dataSnapshot.getValue();
                if (!data.get("id").equals(currentUserID) || onProfile)
                    return;
                onProfile = true;
                userNameView.setText(getIntent().getStringExtra("username"));
                fullNameView.setText(getIntent().getStringExtra("fullname"));
                emailView.setText(getIntent().getStringExtra("email"));
                phoneNumberView.setText(getIntent().getStringExtra("phonenumber"));
                venmo.setText(getIntent().getStringExtra("venmo"));
                String imageURL = getIntent().getStringExtra("imageURL");
                Log.d("IMAGE URL LINK: ", imageURL);
                if (!imageURL.equals("Not provided")) {
                    Log.d("IT WORKED: ", imageURL);
                    Picasso.get().load(imageURL).transform(new CircleTransform()).into(mImageView);
                }
                if (currentUserID.equals(getIntent().getStringExtra("id"))) {
                    mEditProfile.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToEditProfileActivity();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void SendUserToEditProfileActivity() {
        Intent mainIntent = new Intent(ProfileActivity.this, EditProfileActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        onProfile = false;
        Intent returnIntent = new Intent();
        returnIntent.putExtra("hasBackPressed", true);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
