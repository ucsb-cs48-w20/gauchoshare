package com.ucsb.integration.MainPage.ShowList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ucsb.integration.R;

public class ItemDetail extends AppCompatActivity {
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        Intent intent = getIntent();
        String itemName = intent.getExtras().getString("itemName");

        TextView item_name = findViewById(R.id.item_h);
        item_name.setText(itemName);
    }*/
    private Button ProButton;
    private EditText ProName, ProInfo, ProType, ProPrice;
    private String PName, PInfo, PType, PPrice;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;
    String currentUserID;
    DatabaseReference myRef = database.getReference().child("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        ProButton=findViewById(R.id.button);
        ProName=findViewById(R.id.Name);
        ProInfo=findViewById(R.id.Info);
        ProType=findViewById(R.id.Type);
        ProPrice=findViewById(R.id.Price);
        mAuth = FirebaseAuth.getInstance();

        currentUserID = mAuth.getCurrentUser().getUid();


        ProButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PName=ProName.getText().toString();
                PPrice=ProPrice.getText().toString();
                PType=ProType.getText().toString();
                PInfo =ProInfo.getText().toString();

                item p =new item(PName,PInfo,PType,PPrice);


                //myRef = database.getReference().child("Users").child(currentUserID).child("Items");
                //myRef.setValue(p);
                myRef.child(currentUserID).child(PName).setValue(p);
            }
        });


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //Product pro=dataSnapshot.getChildren();

                //item Pro=dataSnapshot.getValue(item.class);
                //System.out.println("Price: "+Pro.Price);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}