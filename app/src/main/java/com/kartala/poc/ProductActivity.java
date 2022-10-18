package com.kartala.poc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kartala.poc.model.Login;
import com.kartala.poc.model.Transaksi;
import com.kartala.poc.network.ApiHelper;
import com.kartala.poc.network.ApiInterface;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {
    TextView txtBack, txtName, txtDescription, txtCategory;
    ImageView imgProduct;
    Button btnOrder;
    EditText edtAddress,edtPhone;
    String phone;
    String name;
    String id, vendor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        init();
        Intent mIntent = getIntent();
        id = mIntent.getStringExtra("id");
        vendor = mIntent.getStringExtra("vendor");
        txtName.setText(mIntent.getStringExtra("name"));
        txtDescription.setText(mIntent.getStringExtra("description"));
        txtCategory.setText(mIntent.getStringExtra("category"));
        name = mIntent.getStringExtra("name");
        Glide.with(ProductActivity.this).load(mIntent.getStringExtra("image")).into(imgProduct);
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductActivity.this,MainActivity.class));
            }
        });
        String role = SharedPref.getString(SharedPref.KEY_ROLE,null);
        if(role.equals("4")){
            btnOrder.setVisibility(View.VISIBLE);
            btnOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showBottomSheetDialog();
                }
            });
        }

    }

    private void init(){
        SharedPref.init(getApplicationContext());
        txtBack = findViewById(R.id.txtBack);
        txtName = findViewById(R.id.txtName);
        txtDescription = findViewById(R.id.txtDescription);
        txtCategory = findViewById(R.id.txtCategory);
        btnOrder = findViewById(R.id.btnOrder);
        imgProduct = findViewById(R.id.imgProduct);
        btnOrder.setVisibility(View.INVISIBLE);
    }
    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_confirm_order);
        EditText edtPhone = bottomSheetDialog.findViewById(R.id.edtPhone);
        TextView txtName = bottomSheetDialog.findViewById(R.id.txtName);
        Button btnPurchase = bottomSheetDialog.findViewById(R.id.btnPurchase);
        EditText edtAddress = bottomSheetDialog.findViewById(R.id.edtAddress);
        txtName.setText(name);
        edtPhone.setText(SharedPref.getString(SharedPref.KEY_PHONE,null));
        bottomSheetDialog.show();
        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                purchase(edtPhone.getText().toString(),edtAddress.getText().toString());
            }
        });


    }

    private void purchase(String phone, String address) {
        Date cDate = new Date();
        String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Transaksi> purchaseCall = apiInterface.purchase(
                SharedPref.getString(SharedPref.KEY_TOKEN,null),
                SharedPref.getString(SharedPref.KEY_ID,null),
                "",
                "",
                id,
                vendor,
                SharedPref.getString(SharedPref.KEY_NAME,null),
                phone,
                fDate,
                "",
                "8",
                address
        );
        purchaseCall.enqueue(new Callback<Transaksi>() {
            @Override
            public void onResponse(Call<Transaksi> call, Response<Transaksi> response) {
                if(response.isSuccessful()){
                    startActivity(new Intent(ProductActivity.this,MainActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(),"Error : "+response.message().toString(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Transaksi> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error : "+t.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }


}
