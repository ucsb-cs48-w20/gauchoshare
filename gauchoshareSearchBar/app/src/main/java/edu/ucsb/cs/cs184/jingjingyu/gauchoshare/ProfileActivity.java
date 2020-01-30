package edu.ucsb.cs.cs184.jingjingyu.gauchoshare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    EditText nickname;
    Button save_profile;

    FirebaseDatabase database;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nickname = findViewById(R.id.nickname);
        save_profile = findViewById(R.id.save_profile);


        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("test");
        dbRef.push().setValue("Hello");

        Log.v("nice","jenny");

        /*
        save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dbRef.child("test").child("data").child("message1").setValue("hello");
            }
        });
         */
    }


}
