package com.ucsb.integration.MainPage.Profile;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.ucsb.integration.MainActivity;
import com.ucsb.integration.R;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText UserName, FullName, PhoneNumber, Venmo;
    private Button SaveInformationbutton;
    private Button mButtonChooseImage;
    private Button mButtonCancel;
    private StorageTask mUploadTask;
    private Uri mImageUri;
    private ImageView mImageView;

    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference photoRef;
    private DatabaseReference UsersRef;
    private FirebaseDatabase database;

    Map<String, Object> data;

    String currentUserID;
    String currentUserEmail;
    String currentImageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().hide();

        mButtonChooseImage = findViewById(R.id.edit_choose_image);
        mButtonCancel = findViewById(R.id.edit_cancel);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        currentUserEmail = mAuth.getCurrentUser().getEmail();
        database = FirebaseDatabase.getInstance();
        UsersRef = database.getReference().child("Users").child(currentUserID);
        mStorageRef = FirebaseStorage.getInstance().getReference("profilePics/" + currentUserID);
        mFirebaseStorage = FirebaseStorage.getInstance();
        mImageView = findViewById(R.id.edit_profile_image);

        UserName = (EditText) findViewById(R.id.edit_username);
        FullName = (EditText) findViewById(R.id.edit_full_name);
        PhoneNumber = (EditText) findViewById(R.id.edit_phone_number);
        PhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        Venmo = (EditText) findViewById(R.id.edit_venmo);

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data = (Map<String, Object>) dataSnapshot.getValue();

                UserName.setText(data.get("username").toString());
                FullName.setText(data.get("fullname").toString());
                if (!data.get("phonenumber").toString().equals("Not provided")) {
                    PhoneNumber.setText(data.get("phonenumber").toString());
                }
                if (!data.get("venmo").toString().equals("Not provided")) {
                    Venmo.setText(data.get("venmo").toString());
                }
                currentImageURL = data.get("imageURL").toString();

                if (!currentImageURL.equals("Not provided")) {
                    Picasso.get().load(currentImageURL).transform(new CircleTransform()).into(mImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        SaveInformationbutton = (Button) findViewById(R.id.edit_save);

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(EditProfileActivity.this, MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                finish();
            }
        });

        SaveInformationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveAccountSetupInformation();
            }
        });
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
            Picasso.get().load(mImageUri).transform(new CircleTransform()).into(mImageView);
        }
    }

    private void SaveAccountSetupInformation() {
        String username = UserName.getText().toString();
        String fullname = FullName.getText().toString();
        String phonenumber = PhoneNumber.getText().toString();
        String venmo = Venmo.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please provide your username...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(fullname)) {
            Toast.makeText(this, "Please provide your Full Name...", Toast.LENGTH_SHORT).show();
        } else {
            final HashMap<String, Object> userMap = new HashMap<>();
            userMap.put("username", username);
            userMap.put("fullname", fullname);
            userMap.put("email", currentUserEmail);
            userMap.put("id", currentUserID);
            userMap.put("imageURL", currentImageURL);
            if (TextUtils.isEmpty(venmo)) {
                userMap.put("venmo", "Not provided");
            } else {
                userMap.put("venmo", venmo);
            }
            if (TextUtils.isEmpty(phonenumber)) {
                userMap.put("phonenumber", "Not provided");
            } else {
                userMap.put("phonenumber", phonenumber);
            }

            if (mImageUri != null) {
                if (!currentImageURL.equals("Not provided")) {
                    StorageReference photoRef = mFirebaseStorage.getReferenceFromUrl(currentImageURL);

                    photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("delete old profile picture", "SUCCESS");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("delete old profile picture", "FAILURE");
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
                            UsersRef.child("imageURL").setValue(downloadUri.toString());
                        } else {
                            Toast.makeText(EditProfileActivity.this, "Upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                UsersRef.child("imageURL").setValue(currentImageURL);
            }
            UsersRef.updateChildren(userMap).addOnCompleteListener((new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        SendUserToMainActivity();
                        Toast.makeText(EditProfileActivity.this, "Your account has been successfully updated..", Toast.LENGTH_LONG).show();
                    } else {

                        String message = task.getException().getMessage();
                        Toast.makeText(EditProfileActivity.this, "Error Occurred: " + message, Toast.LENGTH_SHORT).show();
                    }
                }
            }));
        }
    }
    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(EditProfileActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
