package com.ucsb.integration.MainPage.Listing;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;
import com.ucsb.integration.MainPage.Message.MessagePersonActivity;
import com.ucsb.integration.MainPage.Profile.CircleTransform;
import com.ucsb.integration.MainPage.Profile.ProfileActivity;
import com.ucsb.integration.R;

import java.util.Map;

public class ViewListingActivity extends AppCompatActivity {

    private FirebaseStorage mFirebaseStorage;
    private DatabaseReference UserRef;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;

    private TextView NameView, TitleView, PriceView, DescriptionView;
    private ImageView ProfileImageView, ListingImageView;

    private Map<String, Object> userData;

    private Button MessageSellerButton;

    private String userID, currentUserID, title, price, description, listingImageURL, profileImageURL, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listing);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        userID = extras.getString("creatorID");
        title = extras.getString("title");
        price = extras.getString("price");
        description = extras.getString("description");
        listingImageURL = extras.getString("listingImageURL");

        MessageSellerButton = findViewById(R.id.button_message_seller);

        database = FirebaseDatabase.getInstance();
        UserRef = database.getReference().child("Users").child(userID);
        mFirebaseStorage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        if (currentUserID.equals(userID)) {
            MessageSellerButton.setVisibility(View.INVISIBLE);
        } else {
            MessageSellerButton.setVisibility(View.VISIBLE);
        }

        ProfileImageView = findViewById(R.id.listing_creator_picture);
        ListingImageView = findViewById(R.id.listing_image_view);

        if (listingImageURL.equals("Not provided")) {
            Picasso.get().load(R.drawable.default_listing).fit().into(ListingImageView);
        } else {
            Picasso.get().load(listingImageURL).fit().into(ListingImageView);
        }

        TitleView = findViewById(R.id.listing_title);
        PriceView = findViewById(R.id.listing_price);
        DescriptionView = findViewById(R.id.listing_description);
        NameView = findViewById(R.id.listing_creator_name);

        TitleView.setText(title);
        TitleView.setHeight(TitleView.getLineCount() * TitleView.getLineHeight());
        PriceView.setText(price);
        DescriptionView.setText(description);
        DescriptionView.setHeight(DescriptionView.getLineCount() * DescriptionView.getLineHeight());

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userData = (Map<String, Object>) dataSnapshot.getValue();
                profileImageURL = userData.get("imageURL").toString();
                name = userData.get("fullname").toString();
                NameView.setText(name);

                if (profileImageURL.equals("Not provided")) {
                    Picasso.get().load(R.drawable.default_user).transform(new CircleTransform()).into(ProfileImageView);
                } else {
                    Picasso.get().load(profileImageURL).transform(new CircleTransform()).into(ProfileImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        MessageSellerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewListingActivity.this, MessagePersonActivity.class);
                intent.putExtra("userid", userID);
                startActivity(intent);
                finish();
            }
        });

        ProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map <String, Object> data = (Map<String, Object>) dataSnapshot.getValue();
                        Intent intent = new Intent(ViewListingActivity.this, ProfileActivity.class);
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

        NameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map <String, Object> data = (Map<String, Object>) dataSnapshot.getValue();
                        Intent intent = new Intent(ViewListingActivity.this, ProfileActivity.class);
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

}
