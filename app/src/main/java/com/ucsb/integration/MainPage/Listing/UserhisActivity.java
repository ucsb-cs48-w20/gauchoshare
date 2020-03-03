package com.ucsb.integration.MainPage.Listing;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ucsb.integration.R;

import java.util.List;

public class UserhisActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhis);
        mRecyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        new FDHelper().readProduct(new FDHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Product> products, List<String> keys) {
                new RecyclerView_Config().setConfig(mRecyclerView, UserhisActivity.this, products, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

    }
}