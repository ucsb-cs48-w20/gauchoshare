package com.ucsb.integration.MainPage.Share;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.ucsb.integration.R;

public class ListHistory extends AppCompatActivity {
    String[] hitem = {"Lamp","Desk","Stabler"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_item);
        setTitle("Share History");

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_history_text, hitem);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
    }
}
