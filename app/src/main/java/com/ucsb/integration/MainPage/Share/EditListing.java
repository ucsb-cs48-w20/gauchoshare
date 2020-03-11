package com.ucsb.integration.MainPage.Share;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.ucsb.integration.MainActivity;
import com.ucsb.integration.R;

import java.util.HashMap;

public class EditListing extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int PICK_IMAGE_REQUEST = 1;

    private String title, price, description, listingImageURL, prev_category, new_category, listingID;

    private EditText listingTitle, listingPrice, listingDescription;

    private ImageView listingImageView;

    private Uri mImageUri;

    private Spinner mSpinnerCategory;

    private Button Save, MarkSold, mButtonChooseImage;

    private int prevCategoryIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_listing);

        Bundle extras = getIntent().getExtras();
        title = extras.getString("title");
        price = extras.getString("price");
        description = extras.getString("description");
        listingImageURL = extras.getString("listingImageURL");
        prev_category = extras.getString("category");
        listingID = extras.getString("listingID");

        listingTitle = findViewById(R.id.listing_title);
        listingPrice = findViewById(R.id.listing_price);
        listingDescription = findViewById(R.id.listing_description);
        listingImageView = findViewById(R.id.listing_image_view);

        Save = findViewById(R.id.button_save);
        MarkSold = findViewById(R.id.btn_sold);
        mButtonChooseImage = findViewById(R.id.button_choose_image);

        mSpinnerCategory = findViewById(R.id.spinner_category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCategory.setAdapter(adapter);
        mSpinnerCategory.setSelection(adapter.getPosition(prev_category));
        mSpinnerCategory.setOnItemSelectedListener(this);

        listingTitle.setText(title);
        listingPrice.setText(price);
        listingDescription.setText(description);

        if (!listingImageURL.equals("Not provided")) {
            Picasso.get().load(listingImageURL).fit().into(listingImageView);
        } else {
            Picasso.get().load(R.drawable.default_listing).fit().into(listingImageView);
        }

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    SaveListingInformation();
            }
        });

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        MarkSold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference listingRef = FirebaseDatabase.getInstance().getReference().child("Listings").child(listingID);
                final HashMap<String, Object> listingMap = new HashMap<>();
                listingMap.put("sold", true);
                listingRef.updateChildren(listingMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            SendUserToMainActivity();
                            Toast.makeText(EditListing.this, "Your listing has been successfully updated..", Toast.LENGTH_LONG).show();
                        } else {

                            String message = task.getException().getMessage();
                            Toast.makeText(EditListing.this, "Error Occurred: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(EditListing.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).fit().into(listingImageView);
        }
    }

    private void SaveListingInformation() {

        final DatabaseReference listingRef = FirebaseDatabase.getInstance().getReference().child("Listings").child(listingID);
        String newTitle = listingTitle.getText().toString();
        String newPrice = listingPrice.getText().toString();
        String newDescription = listingDescription.getText().toString();
        if (TextUtils.isEmpty(newTitle)) {
            Toast.makeText(this, "Please provide a title for your listing", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(newPrice)) {
            Toast.makeText(this, "Please provide a price for your listing", Toast.LENGTH_SHORT).show();
        }
        else if (new_category.equals("None")) {
            Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show();
        }
        else {
            if (TextUtils.isEmpty(newDescription)) {
                listingRef.child("description").setValue("Not provided");
            } else {
                listingRef.child("description").setValue(newDescription);
            }
            listingRef.child("title").setValue(newTitle);
            listingRef.child("price").setValue(newPrice);
            listingRef.child("category").setValue(new_category);

            if (mImageUri != null) {
                String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("listingPhotos/" + currentUserID);
                if (!listingImageURL.equals("Not provided")) {
                    FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();
                    StorageReference photoRef = mFirebaseStorage.getReferenceFromUrl(listingImageURL);

                    photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("delete old listing picture", "SUCCESS");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("delete old listing picture", "FAILURE");
                        }
                    });
                }
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
                            listingRef.child("imageURL").setValue(downloadUri.toString());
                        } else {
                            Toast.makeText(EditListing.this, "Upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                listingRef.child("imageURL").setValue(listingImageURL);
            }
            Toast.makeText(this, "Listing successfully created!", Toast.LENGTH_SHORT).show();
            SendUserToMainActivity();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        new_category = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
