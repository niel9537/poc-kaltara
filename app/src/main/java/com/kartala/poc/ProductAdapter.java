package com.kartala.poc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kartala.poc.model.Product;
import com.kartala.poc.model.ProductModel;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    List<ProductModel> productList;
    Context context;
//    public ProductAdapter(List<ProductModel> products) {
//        productList = products;
//    }

    public ProductAdapter(List<ProductModel> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtPrice;
        public ImageView imgProduct;
        public MyViewHolder(View itemView) {
            super(itemView);
          //  txtName = (TextView) itemView.findViewById(R.id.txtName);
            //txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
       // holder.txtName.setText(productList.get(position).getName());
//        DecimalFormat decim = new DecimalFormat("#,###.##");
//        holder.txtPrice.setText("Rp " +decim.format(Integer.valueOf(productList.get(position).getPrice())));
        Glide.with(holder.itemView.getContext())
                .load(productList.get(position).getImage())
                .into(holder.imgProduct);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("S","Masuk ");
                Intent intent = new Intent(context,ProductActivity.class);
                intent.putExtra("id",productList.get(position).getId());
                intent.putExtra("name",productList.get(position).getName());
                intent.putExtra("category",productList.get(position).getCategory());
                intent.putExtra("description",productList.get(position).getDescription());
                intent.putExtra("price",productList.get(position).getPrice());
                intent.putExtra("vendor",productList.get(position).getVendor());
                intent.putExtra("image",productList.get(position).getImage());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        /*     Log.d("Jumlah ",""+brandKompetitorList.size());*/
        return productList.size();
    }

    //public String getIddelete(int position){
    // return kendaraanList.get(position).getId_diskon();
    // }


}
