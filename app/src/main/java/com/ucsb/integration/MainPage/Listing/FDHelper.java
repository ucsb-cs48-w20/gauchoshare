package com.ucsb.integration.MainPage.Listing;

import android.content.Intent;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.ucsb.integration.MainActivity;


import java.util.ArrayList;
import java.util.List;

public class FDHelper {
    private FirebaseAuth mAuth;
    String currentUserID;
    //private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseDatabase mDatabase;
    private List<Product> products = new ArrayList<>();


    public interface DataStatus{
        void DataIsLoaded(List<Product> products, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();

    }
    public FDHelper(){
        mDatabase=FirebaseDatabase.getInstance();
        mDatabaseRef=mDatabase.getReference("Listings");
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
    }


    public void readProduct(final DataStatus dataStatus){
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                products.clear();
                List<String> keys=new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Product pro=keyNode.getValue(Product.class);
                    if(pro.getCreatedBy().startsWith(currentUserID)){
                    products.add(pro);}
                }
                dataStatus.DataIsLoaded(products, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
