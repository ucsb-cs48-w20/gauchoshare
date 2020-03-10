package com.ucsb.integration.MainPage.Listing;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ucsb.integration.R;

import java.util.List;


public class RecyclerView_Config {
    private Context mContext;
    private ProductsAdapter mProductsAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Product> products, List<String> keys){
        mContext=context;
        mProductsAdapter=new ProductsAdapter(products,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mProductsAdapter);
    }


    class ProductItemView extends RecyclerView.ViewHolder{
        private TextView mID;
        private TextView mName;
        private ImageView mImage;

        private String key;

        public ProductItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.custom_item, parent, false));

            mID =(TextView) itemView.findViewById(R.id.UserID);
            mName=(TextView) itemView.findViewById(R.id.Name);
            mImage=(ImageView) itemView.findViewById(R.id.imageView);

        }
        public void bind(Product product, String key){
            mID.setText("Listing Title: " + product.getTitle());
            mName.setText("Price: $" + product.getPrice());
            if (product.getImageURL().equals("Not provided")) {
                Picasso.get().load(R.drawable.default_listing).fit().into(mImage);
            } else {
                Picasso.get().load(product.getImageURL()).fit().into(mImage);
            }
            this.key=key;
        }
    }
    class ProductsAdapter extends RecyclerView.Adapter<ProductItemView>{
        private List<Product> mProductList;
        private List<String> mKeys;

        public ProductsAdapter(List<Product> mProductList, List<String> mKeys) {
            this.mProductList = mProductList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public ProductItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ProductItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductItemView holder, final int position) {
            holder.bind(mProductList.get(position), mKeys.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    Intent intent = new Intent(view.getContext(), EditListingActivity.class);
                    intent.putExtra("creatorID", mProductList.get(position).getCreatedBy());
                    intent.putExtra("title", mProductList.get(position).getTitle());
                    intent.putExtra("price", mProductList.get(position).getPrice());
                    intent.putExtra("description", mProductList.get(position).getDescription());
                    intent.putExtra("listingImageURL", mProductList.get(position).getImageURL());
                    intent.putExtra("sold",mProductList.get(position).getSold());
                    intent.putExtra("category",mProductList.get(position).getCategory());
                    intent.putExtra("listingId",mProductList.get(position).getListingId());
                    mContext.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mProductList.size();
        }
    }
}