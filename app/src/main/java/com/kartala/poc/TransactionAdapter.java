package com.kartala.poc;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kartala.poc.model.ListTransaction;
import com.kartala.poc.model.ProductModel;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {
    List<ListTransaction> transactionList;
    Context context;
//    public ProductAdapter(List<ProductModel> products) {
//        productList = products;
//    }

    public TransactionAdapter(List<ListTransaction> transactions, Context context) {
        this.transactionList = transactions;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtCreateAt, txtStatus, txtProductName,txtVendorName,txtCustomerName;
        public ImageView imgProduct;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtCreateAt = (TextView) itemView.findViewById(R.id.txtCreateAt);
            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
            txtProductName = (TextView) itemView.findViewById(R.id.txtProductName);
            txtVendorName = (TextView) itemView.findViewById(R.id.txtVendorName);
            txtCustomerName = (TextView) itemView.findViewById(R.id.txtCustomerName);
            //txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaksi, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.txtCreateAt.setText(transactionList.get(position).getCreatedAt());
        holder.txtStatus.setText(transactionList.get(position).getStatus());
        holder.txtProductName.setText(transactionList.get(position).getProductName());
        holder.txtVendorName.setText(transactionList.get(position).getVendorName());
        holder.txtCustomerName.setText("Penerima : "+transactionList.get(position).getCustomerName());
//        DecimalFormat decim = new DecimalFormat("#,###.##");
//        holder.txtPrice.setText("Rp " +decim.format(Integer.valueOf(productList.get(position).getPrice())));
        Glide.with(holder.itemView.getContext())
                .load(transactionList.get(position).getProductImage())
                .into(holder.imgProduct);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("S","Masuk "+transactionList.get(position).getStatus());
                Intent intent = new Intent(context,DetailTransactionActivity.class);
                intent.putExtra("id_transaction",transactionList.get(position).getIdTransaction());
                intent.putExtra("status",transactionList.get(position).getStatus());
                intent.putExtra("created_at",transactionList.get(position).getCreatedAt());
                intent.putExtra("product_name",transactionList.get(position).getProductName());
                intent.putExtra("product_image",transactionList.get(position).getProductImage());
                intent.putExtra("customer_name",transactionList.get(position).getCustomerName());
                intent.putExtra("driver_name",transactionList.get(position).getDriverName());
                intent.putExtra("customer_address",transactionList.get(position).getAddress());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        /*     Log.d("Jumlah ",""+brandKompetitorList.size());*/
        return transactionList.size();
    }

    //public String getIddelete(int position){
    // return kendaraanList.get(position).getId_diskon();
    // }


}
