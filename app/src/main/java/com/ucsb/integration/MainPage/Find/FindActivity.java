package com.ucsb.integration.MainPage.Find;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ucsb.integration.R;

import java.util.ArrayList;
import java.util.List;

import static java.util.Locale.filter;

public class FindActivity extends AppCompatActivity {

    RecyclerView listShow;
    List<Item> proList = new ArrayList<>();
    SearchView searchView;

    FindActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listShow = findViewById(R.id.showList);
        listShow.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listShow.setLayoutManager(linearLayoutManager);

        proList.add(new Item("webster dictionary",R.drawable.dic,25));
        proList.add(new Item("road bike used", R.drawable.bikes,100));
        proList.add(new Item("table round",R.drawable.table,80));

        adapter = new FindActivityAdapter(proList,FindActivity.this);
        listShow.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.searchfile, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.search);

        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.findViewById(R.id.search_src_text);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                myActionMenuItem.collapseActionView();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final  List<Item> filtermodelist= filter(proList,newText);
                adapter.setfilter(filtermodelist);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private List<Item> filter(List<Item> pl,String query)
    {
        query=query.toLowerCase();
        final List<Item> filteredModeList=new ArrayList<>();
        for (Item model:pl)
        {
            final String text=model.getName().toLowerCase();
            if (text.startsWith(query))
            {
                filteredModeList.add(model);
            }
        }
        return filteredModeList;
    }
    
}
