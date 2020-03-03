package com.ucsb.integration.MainPage.Listing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ucsb.integration.MainPage.Share.ShareActivity;
import com.ucsb.integration.MainPage.ShowList.ShowList;
import com.ucsb.integration.R;

public class DirectionActivity extends AppCompatActivity {

    Button sItem;
    Button sHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        sItem = (Button)findViewById(R.id.button);
        sHistory = (Button)findViewById(R.id.button2);
        sItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DirectionActivity.this, CreateListingActivity.class));
            }
        });
        sHistory.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            startActivity(new Intent(DirectionActivity.this, UserhisActivity.class));
                                        }
                                    }
        );
    }
}