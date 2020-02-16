package com.ucsb.integration.MainPage.ShowList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ucsb.integration.R;

import java.util.List;

public class item{


    String Name;
    String Info;
    String Type;
    String Price;


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public item(){

    }
    public item(String name, String info, String type, String price) {
        Name = name;
        Info = info;
        Type = type;
        Price = price;
    }
    /*int layout;        //add in the history list
    List<String> mObjects;
    //Context context;
    public item() {
        super(null, 0);
    }
    public item(Context context, int resource,List<String>objects) {
        super(context,resource,objects);
        mObjects=objects;
        layout=resource;
    }

    public item(Context context, int resource) {
        super(context, resource);
    }

    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView textview;
        public Button button;
        public Button button2;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder mainViewholder = null;
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textview = (TextView) convertView.findViewById(R.id.txt);
            viewHolder.button = (Button) convertView.findViewById(R.id.bt);
            viewHolder.button2 = (Button) convertView.findViewById(R.id.bt2);
            convertView.setTag(viewHolder);
        }
        mainViewholder = (ViewHolder) convertView.getTag();

        //context = this;
        mainViewholder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                //context.startActivity(new Intent(context,ItemDetail.class));
                String spass = getItem(position);
                Intent i = new Intent(context, ItemDetail.class);
                i.putExtra("itemName",spass);
                context.startActivity(i);
            }
        });

        mainViewholder.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sDel = getItem(position);
                mObjects.remove(sDel);
                notifyDataSetChanged();
            }
        });
        mainViewholder.textview.setText(getItem(position));

        return convertView;
    }
    //xml file
    <TextView
        android:id="@+id/td"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="25dp"
        android:text="Item Name: "
        android:textColor="#000000"
        />

    <TextView
        android:id="@+id/item_h"
        android:layout_width="401dp"
        android:layout_height="wrap_content"
        android:layout_below="@id/td"
        android:text="enter"
        android:textColor="#00007F"
        android:textSize="25dp" />


    */
}
