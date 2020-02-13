package com.ucsb.integration.MainPage.ShowList;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ucsb.integration.R;

import java.util.ArrayList;
import java.util.HashMap;


public class ShowList extends ListActivity {
    ArrayList list = new ArrayList();
    Context context;
    private FirebaseAuth mAuth;
    private DatabaseReference ItemsRef;
    private FirebaseDatabase database;

    String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        ItemsRef = database.getReference().child("Users").child(currentUserID).child("Items");

        Button btn_add_item = (Button)findViewById(R.id.btnAdd);

//        context=this;

        btn_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveItems();
            }
        });
    }

    private void SaveItems() {
        ListView lstview=(ListView)findViewById(android.R.id.list);
        final ArrayAdapter mAdapter;
        mAdapter =new item(this, R.layout.activity_item, list);
        mAdapter.notifyDataSetChanged();
        lstview.setAdapter(mAdapter);
        final EditText edit = (EditText) findViewById(R.id.item_input);
        HashMap<String, Object> itemsMap = new HashMap<>();
        itemsMap.put(edit.getText().toString(), "Item Description");
        ItemsRef.updateChildren(itemsMap).addOnCompleteListener((new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ShowList.this, "Item successfully added to Firebase", Toast.LENGTH_SHORT).show();
                    list.add(edit.getText().toString());
                    edit.setText("");
                    mAdapter.notifyDataSetChanged();
                } else {
                    String message = task.getException().getMessage();
                    Toast.makeText(ShowList.this, "Error Occurred: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

    public void generate_fb(){

    }

}
