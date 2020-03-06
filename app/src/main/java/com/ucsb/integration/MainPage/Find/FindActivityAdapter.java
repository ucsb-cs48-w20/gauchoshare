package com.ucsb.integration.MainPage.Find;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ucsb.integration.MainPage.Listing.Product;
import com.ucsb.integration.MainPage.Listing.ViewListingActivity;
import com.ucsb.integration.R;

import java.util.ArrayList;
import java.util.List;

public class FindActivityAdapter extends RecyclerView.Adapter<FindActivityAdapter.Holderview> {

    private List<Product> proList;
    private Context context;

    public FindActivityAdapter(List<Product> proList, Context context) {
        this.proList = proList;
        this.context = context;
    }



    @NonNull
    @Override
    public Holderview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.customitem, parent,false);
        return new Holderview(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull Holderview holder, final int position) {
        holder.v_name.setText(proList.get(position).getTitle());
        holder.v_price.setText((proList.get(position).getPrice()));
        if (proList.get(position).getImageURL().equals("Not provided")) {
            Picasso.get().load(R.drawable.default_listing).fit().into(holder.v_image);
        } else {
            Picasso.get().load(proList.get(position).getImageURL()).into(holder.v_image);
        }

        if (!proList.get(position).getSold()) {
            holder.v_sold.setVisibility(View.INVISIBLE);
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    Intent intent = new Intent(view.getContext(), ViewListingActivity.class);
                    intent.putExtra("creatorID", proList.get(position).getCreatedBy());
                    intent.putExtra("title", proList.get(position).getTitle());
                    intent.putExtra("price", proList.get(position).getPrice());
                    intent.putExtra("description", proList.get(position).getDescription());
                    intent.putExtra("listingImageURL", proList.get(position).getImageURL());
                    context.startActivity(intent);
                }
            });
        } else {
            holder.v_sold.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return proList.size();
    }

    public void setfilter(List<Product> listitem){
        proList = new ArrayList<>();
        proList.addAll(listitem);
        notifyDataSetChanged();
    }

    class Holderview extends RecyclerView.ViewHolder{
        ImageView v_image;
        TextView v_name;
        TextView v_price;
        ImageView v_sold;

        Holderview(View itemview){
            super(itemview);
            v_image = itemview.findViewById(R.id.pro_image);
            v_name = itemview.findViewById(R.id.pro_title);
            v_price = itemview.findViewById(R.id.pro_price);
            v_sold = itemview.findViewById(R.id.pro_sold);
        }


    }
}
