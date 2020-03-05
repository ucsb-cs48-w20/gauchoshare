package com.ucsb.integration.MainPage.Profile;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
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

import java.util.HashMap;

public class SetupActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText UserName, FullName, PhoneNumber, Venmo;
    private Button SaveInformationbutton;
    private Button mButtonChooseImage;
    private StorageTask mUploadTask;
    private Uri mImageUri;
    private ImageView mImageView;

    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private DatabaseReference UsersRef;
    private FirebaseDatabase database;

    String currentUserID;
    String currentUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        setTitle("Please complete your profile first");

        mButtonChooseImage = findViewById(R.id.button_choose_image);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        currentUserEmail = mAuth.getCurrentUser().getEmail();
        database = FirebaseDatabase.getInstance();
        UsersRef = database.getReference().child("Users").child(currentUserID);
        mStorageRef = FirebaseStorage.getInstance().getReference("profilePics/" + currentUserID);
        mImageView = findViewById(R.id.setup_profile_image);


        UserName = (EditText) findViewById(R.id.setup_username);
        FullName = (EditText) findViewById(R.id.setup_full_name);
        PhoneNumber = (EditText) findViewById(R.id.setup_phone_number);
        PhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        Venmo = (EditText) findViewById(R.id.setup_venmo);

        SaveInformationbutton = (Button) findViewById(R.id.setup_information_button);

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
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
            userMap.put("imageURL", "Not provided");
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
                mStorageRef.putFile(mImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return mStorageRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            UsersRef.child("imageURL").setValue(downloadUri.toString());
                        } else {
                            Toast.makeText(SetupActivity.this, "Upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                UsersRef.child("imageURL").setValue("Not provided");
            }
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
        Intent mainIntent = new Intent(SetupActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
