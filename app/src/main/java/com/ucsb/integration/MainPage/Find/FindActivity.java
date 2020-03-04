package com.ucsb.integration.MainPage.Find;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ucsb.integration.MainPage.Listing.Product;
import com.ucsb.integration.R;

import java.util.ArrayList;
import java.util.List;

public class FindActivity extends AppCompatActivity {

    RecyclerView listShow;
    List<Product> proList = new ArrayList<>();
    SearchView searchView;

    FindActivityAdapter adapter;
    DatabaseReference ref;

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

        ref = FirebaseDatabase.getInstance().getReference().child("Listings");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    proList = new ArrayList<>();
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        proList.add(ds.getValue(Product.class));

                    }
                    adapter =  new FindActivityAdapter(proList, FindActivity.this);
                    listShow.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FindActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


       /* proList.add(new Item("webster dictionary",R.drawable.dic,25));
        proList.add(new Item("road bike used", R.drawable.bikes,100));
        proList.add(new Item("table round",R.drawable.table,80));*/

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
                final  List<Product> filtermodelist= filter(proList,newText);
                adapter.setfilter(filtermodelist);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private List<Product> filter(List<Product> pl,String query)
    {
        query=query.toLowerCase();
        final List<Product> filteredModeList=new ArrayList<>();
        for (Product model:pl)
        {
            final String text=model.getTitle().toLowerCase();
            final String text1 = model.getDescription().toLowerCase();
            if (text.startsWith(query) | text1.contains(query) )
            {
                filteredModeList.add(model);
            }
        }
        return filteredModeList;
    }
    
}
