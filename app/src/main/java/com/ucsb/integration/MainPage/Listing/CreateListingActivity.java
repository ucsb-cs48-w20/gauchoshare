package com.ucsb.integration.MainPage.Listing;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.ucsb.integration.MainActivity;
import com.ucsb.integration.R;

public class CreateListingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private Button mButtonCreate;
    private EditText mEditTextTitle;
    private EditText mEditTextPrice;
    private EditText mEditDescription;
    private ImageView mImageView;
    private Spinner mSpinnerCategory;

    private Uri mImageUri;

    private String category;

    String currentUserID;

    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;
    private boolean imageUploaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);
        setTitle("Create listing");
        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonCreate = findViewById(R.id.button_create_listing);
        mEditTextTitle = findViewById(R.id.listing_title);
        mEditTextPrice = findViewById(R.id.listing_price);
        mEditDescription = findViewById(R.id.listing_description);
        mImageView = findViewById(R.id.listing_image_view);
        mSpinnerCategory = findViewById(R.id.spinner_category);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCategory.setAdapter(adapter);
        mSpinnerCategory.setOnItemSelectedListener(this);

        category = "None";

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

        mButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveListingInformation();
            }
        });
    }

    private void SaveListingInformation() {
        String title = mEditTextTitle.getText().toString();
        String price = mEditTextPrice.getText().toString();
        String description = mEditDescription.getText().toString();



        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "Please provide a title for your listing", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(price)) {
            Toast.makeText(this, "Please provide a price for your listing", Toast.LENGTH_SHORT).show();
        }
        else if (category.equals("None")) {
            Toast.makeText(CreateListingActivity.this, "Please select a category", Toast.LENGTH_SHORT).show();
        }
        else {
            final String listingId = mDatabaseRef.push().getKey(); //generates random key
            if (TextUtils.isEmpty(description)) {
                mDatabaseRef.child(listingId).child("description").setValue("None");
            } else {
                mDatabaseRef.child(listingId).child("description").setValue(description);
            }
            mDatabaseRef.child(listingId).child("title").setValue(title);
            mDatabaseRef.child(listingId).child("price").setValue(price);
            mDatabaseRef.child(listingId).child("createdBy").setValue(currentUserID);
            mDatabaseRef.child(listingId).child("category").setValue(category);
            mDatabaseRef.child(listingId).child("sold").setValue(false);

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
                            Toast.makeText(CreateListingActivity.this, "Upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                mDatabaseRef.child(listingId).child("imageURL").setValue("Not provided");
            }
            Toast.makeText(CreateListingActivity.this, "Listing successfully created!", Toast.LENGTH_SHORT).show();
            SendUserToMainActivity();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(CreateListingActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).fit().centerCrop().into(mImageView);
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
