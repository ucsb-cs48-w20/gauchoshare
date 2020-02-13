package com.ucsb.integration.MainPage.ShowList;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ucsb.integration.R;

public class ItemDetail extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        Intent intent = getIntent();
        String itemName = intent.getExtras().getString("itemName");

        TextView item_name = findViewById(R.id.item_h);
        item_name.setText(itemName);
    }
}