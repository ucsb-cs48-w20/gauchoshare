package com.ucsb.integration.MainPage.Share;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

import com.ucsb.integration.MainActivity;
import com.ucsb.integration.MainPage.ShowList.ItemDetail;
import com.ucsb.integration.MainPage.ShowList.ItemDetail;
import com.ucsb.integration.R;

public class ShareActivity extends AppCompatActivity {

    Button sItem;
    Button sHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Share Activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sItem = (Button)findViewById(R.id.button);
        sItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShareActivity.this, ItemDetail.class));
            }
        });

        sHistory = (Button)findViewById(R.id.button2);
        sHistory.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            startActivity(new Intent(ShareActivity.this, ListHistory.class));
                                        }
                                    }
        );
    }



}
