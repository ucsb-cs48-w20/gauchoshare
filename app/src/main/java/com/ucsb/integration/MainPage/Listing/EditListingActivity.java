package com.ucsb.integration.MainPage.Listing;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.ucsb.integration.MainActivity;
import com.ucsb.integration.MainPage.Message.MessagePersonActivity;
import com.ucsb.integration.MainPage.Profile.CircleTransform;
import com.ucsb.integration.MainPage.Profile.EditProfileActivity;
import com.ucsb.integration.R;

import java.util.HashMap;
import java.util.Map;

public class EditListingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseStorage mFirebaseStorage;
    private DatabaseReference UserRef;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private DatabaseReference ItemRef;
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;

    private boolean imageUploaded;
    private Button SetAsSold;
    private Button SetAsUnSold;

    private Uri mImageUri;
    private TextView NameView;
    private EditText TitleView, PriceView, DescriptionView;
    private ImageView ProfileImageView, ListingImageView;

    private Map<String, Object> userData;

    private Button mButtonChooseImage;
    private Spinner mSpinnerCategory;
    private Button EDITITEM;

    private String userID, currentUserID, category,title,listingId, price, description, listingImageURL, profileImageURL, name;

    private Boolean soldby;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_listing);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        category=extras.getString("category");
        soldby=extras.getBoolean("sold");
        userID = extras.getString("creatorID");
        title = extras.getString("title");
        price = extras.getString("price");
        description = extras.getString("description");
        listingImageURL = extras.getString("listingImageURL");
        listingId=extras.getString("listingId");
        mButtonChooseImage = findViewById(R.id.button_choose_image);
        SetAsSold=findViewById(R.id.SETSOLD);
        SetAsUnSold=findViewById(R.id.SETASUNSOLD);


        mSpinnerCategory = findViewById(R.id.spinner_category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCategory.setAdapter(adapter);
        mSpinnerCategory.setOnItemSelectedListener(this);


        EDITITEM = findViewById(R.id.btn_edit_item);

        database = FirebaseDatabase.getInstance();
        UserRef = database.getReference().child("Users").child(userID);
        ItemRef=database.getReference().child("Listings").child(listingId);
        mFirebaseStorage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        mStorageRef = FirebaseStorage.getInstance().getReference("listingPhotos/" + currentUserID);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Listings");

        imageUploaded = false;

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        SetAsSold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSold();
            }
        });
        SetAsUnSold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUnSold();
            }
        });



        if (soldby.equals(false)) {
            //EDITITEM.setVisibility(View.VISIBLE);
            SetAsSold.setVisibility(View.VISIBLE);
            SetAsUnSold.setVisibility(View.INVISIBLE);
        } else {
            //EDITITEM.setVisibility(View.INVISIBLE);
            SetAsSold.setVisibility(View.INVISIBLE);
            SetAsUnSold.setVisibility(View.VISIBLE);
        }

        //ProfileImageView = findViewById(R.id.listing_creator_pictu);
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


        EDITITEM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditYourStuff();
            }
        });
    }
    private void EditYourStuff() {
        String tit = TitleView.getText().toString();
        String pri = PriceView.getText().toString();
        String des = DescriptionView.getText().toString();


        if (TextUtils.isEmpty(tit)) {
            Toast.makeText(this, "Please provide your item title", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pri)) {
            Toast.makeText(this, "Please provide your item price", Toast.LENGTH_SHORT).show();
        }
        else if (category.equals("None")) {
            Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show();
        }
        else{
            if (mImageUri != null) {
                final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
                fileReference.putFile(mImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return fileReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            mDatabaseRef.child(listingId).child("imageURL").setValue(downloadUri.toString());
                        } else {
                            Toast.makeText(EditListingActivity.this, "Upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                mDatabaseRef.child(listingId).child("imageURL").setValue("Not provided");
            }
            final HashMap<String, Object> userMap = new HashMap<>();
            userMap.put("title", tit);
            userMap.put("price", pri);
            userMap.put("description", des);
            //userMap.put("createdBy", userID);
            userMap.put("imageURL", listingImageURL);
            userMap.put("category",category);
            userMap.put("sold",soldby);

            ItemRef.updateChildren(userMap).addOnCompleteListener((new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        SendUserToMainActivity();
                        Toast.makeText(EditListingActivity.this, "Your Item is updated", Toast.LENGTH_LONG).show();
                    } else {

                        String message = task.getException().getMessage();
                        Toast.makeText(EditListingActivity.this, "Error Occurred: " + message, Toast.LENGTH_SHORT).show();
                    }
                }
            }));


        }


        //Intent mainIntent = new Intent(EditListingActivity.this, EditYourItemActivity.class);
       // mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
       // startActivity(mainIntent);
       // finish();
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void updateSold(){
        soldby=true;
        Toast.makeText(EditListingActivity.this, "Your Item will be shown as sold.", Toast.LENGTH_LONG).show();

    }
    private void updateUnSold(){
        soldby=false;
        Toast.makeText(EditListingActivity.this, "Your item will be shown as unsold.", Toast.LENGTH_LONG).show();
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(EditListingActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).fit().centerCrop().into(ListingImageView);
            imageUploaded = true;
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
