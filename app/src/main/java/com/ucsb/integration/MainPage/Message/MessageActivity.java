package com.ucsb.integration.MainPage.Message;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ucsb.integration.R;

public class MessageActivity extends Activity {

    Context context;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        ListView lstview = (ListView) findViewById(R.id.listv);
        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "An item of the ListView is clicked.", Toast.LENGTH_LONG).show();
            }
        });

        String[] items = {"Kyle", "Stubbs", "Marisa", "Bob", "Naomi"};
        LstViewAdapter adapter = new LstViewAdapter(this, R.layout.list_item, R.id.txt, items);
        // Bind data to the ListView
        lstview.setAdapter(adapter);
    }

    public void clickMe(View view){
        Button bt=(Button)view;
        Toast.makeText(this, "Button "+bt.getText().toString(),Toast.LENGTH_LONG).show();
    }

}
