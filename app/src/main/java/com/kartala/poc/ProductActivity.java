package com.kartala.poc;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kartala.poc.model.Login;
import com.kartala.poc.model.Transaksi;
import com.kartala.poc.network.ApiHelper;
import com.kartala.poc.network.ApiInterface;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {
    TextView txtBack, txtName, txtDescription, txtCategory;
    ImageView imgProduct;
    Button btnOrder;
    EditText edtAddress,edtPhone;
    String phone;
    String lat;
    String lon;
    String name;
    String id, vendor;
    LoadingDialogBar loadingDialogBar;
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
        loadingDialogBar = new LoadingDialogBar(ProductActivity.this);

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
        //AutoCompleteTextView autoCompleteTextView = bottomSheetDialog.findViewById(R.id.autocomplete);
        Button btnPurchase = bottomSheetDialog.findViewById(R.id.btnPurchase);
       // EditText edtAddress = bottomSheetDialog.findViewById(R.id.edtAddress);
        txtName.setText(name);
        edtPhone.setText(SharedPref.getString(SharedPref.KEY_PHONE,null));
        AutoCompleteTextView autoCompleteTextView=bottomSheetDialog.findViewById(R.id.autocomplete);
        autoCompleteTextView.setAdapter(new PlaceAutoSuggestAdapter(ProductActivity.this,android.R.layout.simple_list_item_1));

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Address : ",autoCompleteTextView.getText().toString());
                LatLng latLng=getLatLngFromAddress(autoCompleteTextView.getText().toString());
                if(latLng!=null) {
                    Log.d("Lat Lng : ", " " + latLng.latitude + " " + latLng.longitude);
                    Address address=getAddressFromLatLng(latLng);
                    if(address!=null) {
                        Log.d("Address : ", "" + address.toString());
                        Log.d("Address Line : ",""+address.getAddressLine(0));
                        lat = String.valueOf(address.getLatitude());
                        lon = String.valueOf(address.getLongitude());
                    }
                    else {
                        Log.d("Adddress","Address Not Found");
                    }
                }
                else {
                    Log.d("Lat Lng","Lat Lng Not Found");
                }

            }
        });
        bottomSheetDialog.show();
        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                purchase(edtPhone.getText().toString(),autoCompleteTextView.getText().toString());
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
                lat,
                lon,
                "8",
                address,
                SharedPref.getString(SharedPref.KEY_NOTIF_TOKEN,null)
        );
        Log.d("Firebase Token : ",SharedPref.getString(SharedPref.KEY_NOTIF_TOKEN,null));
        purchaseCall.enqueue(new Callback<Transaksi>() {
            @Override
            public void onResponse(Call<Transaksi> call, Response<Transaksi> response) {
                if(response.isSuccessful()){
                    loadingDialogBar.success();
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

    private LatLng getLatLngFromAddress(String address){

        Geocoder geocoder=new Geocoder(ProductActivity.this);
        List<Address> addressList;

        try {
            addressList = geocoder.getFromLocationName(address, 1);
            if(addressList!=null){
                Address singleaddress=addressList.get(0);
                LatLng latLng=new LatLng(singleaddress.getLatitude(),singleaddress.getLongitude());
                return latLng;
            }
            else{
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    private Address getAddressFromLatLng(LatLng latLng){
        Geocoder geocoder=new Geocoder(ProductActivity.this);
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 5);
            if(addresses!=null){
                Address address=addresses.get(0);
                return address;
            }
            else{
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


}
