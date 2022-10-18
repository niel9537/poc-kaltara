package com.kartala.poc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kartala.poc.model.Transaksi;
import com.kartala.poc.network.ApiHelper;
import com.kartala.poc.network.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTransactionActivity extends AppCompatActivity {
    String id_transaction,status,created_at,product_name,product_image,customer_name,driver_name,customer_address;
    TextView txtStatus, txtCreateAt, txtIdTransaction,txtProductName,txtCustomerName,txtAddress,txtDriverName,txtBack,txtBarcode, txtDetail;
    ImageView imgProduct;
    Config config;
    Button btnScan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction);
        init();
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        txtDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailTransactionActivity.this,StatusTransactionActivity.class);
                intent.putExtra("status",status);
                startActivity(intent);
            }
        });
    }

    private void init(){
        SharedPref.init(getApplicationContext());
        Intent mIntent = getIntent();
        id_transaction = mIntent.getStringExtra("id_transaction");
        status = mIntent.getStringExtra("status");
        created_at = mIntent.getStringExtra("created_at");
        product_name = mIntent.getStringExtra("product_name");
        product_image = mIntent.getStringExtra("product_image");
        customer_name = mIntent.getStringExtra("customer_name");
        driver_name = mIntent.getStringExtra("driver_name");
        customer_address = mIntent.getStringExtra("customer_address");
        imgProduct = findViewById(R.id.imgProduct);
        txtStatus = findViewById(R.id.txtStatus);
        txtCreateAt = findViewById(R.id.txtCreateAt);
        txtBack = findViewById(R.id.txtBack);
        txtIdTransaction = findViewById(R.id.txtIdTransaction);
        txtProductName = findViewById(R.id.txtProductName);
        txtBarcode =findViewById(R.id.txtBarcode);
        txtDetail =findViewById(R.id.txtDetail);
        txtCustomerName = findViewById(R.id.txtCustomerName);
        txtAddress = findViewById(R.id.txtAddress);
        txtDriverName = findViewById(R.id.txtDriverName);
        txtStatus.setText(Config.setStatus(status));
        txtIdTransaction.setText("TRX-"+id_transaction);
        txtCreateAt.setText(created_at);
        txtProductName.setText(product_name);
        txtCustomerName.setText("Penerima : "+customer_name);
        txtDriverName.setText(driver_name);
        txtAddress.setText(customer_name+"\n"+""+customer_address);
        Glide.with(DetailTransactionActivity.this).load(product_image).into(imgProduct);
        btnScan = findViewById(R.id.btnScan);
        String role = SharedPref.getString(SharedPref.KEY_ROLE,"");
        btnScan.setVisibility(View.INVISIBLE);
        if(role.equals("3")){
            if(status.equals("8")){
                btnScan.setVisibility(View.VISIBLE);
                scan(id_transaction,"7");
            }else if(status.equals("7")){
                btnScan.setVisibility(View.VISIBLE);
                scan(id_transaction,"6");
            }else if(status.equals("5")){
                btnScan.setVisibility(View.VISIBLE);
                scan(id_transaction,"4");
            }else if(status.equals("3")){
                btnScan.setVisibility(View.VISIBLE);
                scan(id_transaction,"2");
            }else if(status.equals("1")){
                btnScan.setVisibility(View.VISIBLE);
                scan(id_transaction,"0");
            }

        }
        if(role.equals("2")){
            if(status.equals("6")){
                btnScan.setVisibility(View.VISIBLE);
                scan(id_transaction,"5");
            }
        }
        if(role.equals("1")){
            if(status.equals("4")) {
                btnScan.setVisibility(View.VISIBLE);
                scan(id_transaction, "3");
            }
        }
        if(role.equals("4")){
            if(status.equals("2")) {
                btnScan.setVisibility(View.VISIBLE);
                scan(id_transaction, "1");
            }
        }
        txtBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barcode(id_transaction);
            }
        });

    }
    private void barcode(String id){
        AlertDialog.Builder alert = new AlertDialog.Builder(DetailTransactionActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.barcode_layout, null);
        ImageView imgBarcode = view.findViewById(R.id.imgBarcode);
        Glide.with(DetailTransactionActivity.this).load("https://api.qrserver.com/v1/create-qr-code/?size=300x300&data="+id).into(imgBarcode);
        alert.setView(view);
        alert.show();
    }
    private void scan(String id, String status){
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailTransactionActivity.this,ActivityScan.class);
                intent.putExtra("id_transaction",id);
                intent.putExtra("status",status);
                startActivity(intent);
            }
        });
    }





}
