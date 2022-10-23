package com.kartala.poc;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    String TOKEN, ID, ROLE, id_transaction, status, created_at, product_name, product_image, customer_name, driver_name, customer_address;
    TextView txtStatus, txtCreateAt, txtIdTransaction, txtProductName, txtCustomerName, txtAddress, txtDriverName, txtBack, txtBarcode, txtDetail,txtLocation;
    ImageView imgProduct;
    Config config;
    Button btnScan;
    ActivityScan activityScan;
    private String lat = "";
    private String lon = "";
    FusedLocationProviderClient client;
    private GoogleMap gmap;

    private LocationRequest locationRequest;
    String customer_lat = "";
    String customer_lon = "";
    String status_lat = "";
    String status_lon = "";
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
                Intent intent = new Intent(DetailTransactionActivity.this, StatusTransactionActivity.class);
                intent.putExtra("status", status);
                startActivity(intent);
            }
        });
        txtLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailTransactionActivity.this, ActivityMap.class);
                intent.putExtra("customer_lat", customer_lat);
                Log.d("Lat Map 2 ", customer_lat);
                intent.putExtra("customer_lon", customer_lon);
                intent.putExtra("status_lat", status_lat);
                intent.putExtra("status_lon", status_lon);

                startActivity(intent);
            }
        });
    }

    private void init() {
        SharedPref.init(getApplicationContext());

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);


        TOKEN = SharedPref.getString(SharedPref.KEY_TOKEN,null);
        ID = SharedPref.getString(SharedPref.KEY_ID,null);
        ROLE = SharedPref.getString(SharedPref.KEY_ROLE,null);
        Intent mIntent = getIntent();
        id_transaction = mIntent.getStringExtra("id_transaction");
        status = mIntent.getStringExtra("status");
        created_at = mIntent.getStringExtra("created_at");
        product_name = mIntent.getStringExtra("product_name");
        product_image = mIntent.getStringExtra("product_image");
        customer_name = mIntent.getStringExtra("customer_name");
        driver_name = mIntent.getStringExtra("driver_name");
        customer_address = mIntent.getStringExtra("customer_address");
         customer_lat =  mIntent.getStringExtra("customer_lat");
         customer_lon =  mIntent.getStringExtra("customer_lon");
         status_lat =  mIntent.getStringExtra("status_lat");
         status_lon =  mIntent.getStringExtra("status_lon");
        Log.d("Lat Map 1", customer_lat);
        imgProduct = findViewById(R.id.imgProduct);
        txtStatus = findViewById(R.id.txtStatus);
        txtCreateAt = findViewById(R.id.txtCreateAt);
        txtBack = findViewById(R.id.txtBack);
        txtLocation = findViewById(R.id.txtLocation);
        txtIdTransaction = findViewById(R.id.txtIdTransaction);
        txtProductName = findViewById(R.id.txtProductName);
        txtBarcode =findViewById(R.id.txtBarcode);
        txtDetail =findViewById(R.id.txtDetail);
        txtCustomerName = findViewById(R.id.txtCustomerName);
        txtAddress = findViewById(R.id.txtAddress);
        txtDriverName = findViewById(R.id.txtDriverName);
        txtStatus.setText(Config.setStatus(status));
        txtIdTransaction.setText("SO-"+id_transaction);
        txtCreateAt.setText(created_at);
        txtProductName.setText(product_name);
        txtCustomerName.setText("Penerima : "+customer_name);
        txtDriverName.setText(driver_name);
        txtAddress.setText(customer_name+"\n"+""+customer_address);
        Glide.with(DetailTransactionActivity.this).load(product_image).into(imgProduct);
        btnScan = findViewById(R.id.btnScan);
        String role = SharedPref.getString(SharedPref.KEY_ROLE,"");
        btnScan.setVisibility(View.INVISIBLE);
        //Vendor
        switch (role){
            case "2" : //Vendor
                if(status.equals("5")){
                    getCurrentLocation();
                    btnScan.setVisibility(View.VISIBLE);
                    btnScan.setText("KONFIRMASI");
                    btnScan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setConfirm(id_transaction,"4");
                        }
                    });

                }
                break;
            case "3" : //Driver
                if(status.equals("4")){
                    getCurrentLocation();
                    btnScan.setVisibility(View.VISIBLE);
                    scan(id_transaction,"3");
                }else if(status.equals("2")){
                    getCurrentLocation();
                    btnScan.setVisibility(View.VISIBLE);
                    scan(id_transaction,"1");
                }else if(status.equals("1")){
                    getCurrentLocation();
                    btnScan.setVisibility(View.VISIBLE);
                    btnScan.setText("SELESAI");
                    btnScan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                            {
                                requestPermissions(new String[]{Manifest.permission.CAMERA}, Config.MY_CAMERA_PERMISSION_CODE);
                            }
                            else
                            {
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, Config.CAMERA_REQUEST);
                            }
                        }
                    });
                    //setConfirm(id_transaction,"0");
//                    scan(id_transaction,"1");
                }
                break;
            case "1" : //Admin
                if(status.equals("3")) {
                    getCurrentLocation();
                    btnScan.setVisibility(View.VISIBLE);
                    scan(id_transaction, "2");
                }
                break;
        }

//        if(role.equals("4")){
//            if(status.equals("2")) {
//                btnScan.setVisibility(View.VISIBLE);
//                scan(id_transaction, "1");
//            }
//        }
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
                intent.putExtra("lat",lat);
                intent.putExtra("lon",lon);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Config.MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, Config.CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Activity","requestCode : "+requestCode+" resultCode : "+resultCode);

        if (requestCode == Config.CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                getCurrentLocation();
                btnScan.setVisibility(View.GONE);
                setConfirm(id_transaction,"0");
        }
        if (requestCode == 100) {
            if(resultCode == Activity.RESULT_OK){
                getCurrentLocation();
                btnScan.setVisibility(View.GONE);
                setConfirm(id_transaction,"0");
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }


    public void setConfirm(String id_transaction, String status){
        ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Transaksi> transaksiCall = apiInterface.settransaction(TOKEN,id_transaction,ROLE,ID,status,lat,lon);
        transaksiCall.enqueue(new Callback<Transaksi>() {
            @Override
            public void onResponse(Call<Transaksi> call, Response<Transaksi> response) {
                if(response.isSuccessful()){
                    Intent intent = new Intent(DetailTransactionActivity.this,MainActivity.class);
                    startActivity(intent);
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
    private void getCurrentLocation() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(DetailTransactionActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(DetailTransactionActivity.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(DetailTransactionActivity.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() >0){

                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();

                                        lat = String.valueOf(latitude);
                                        lon = String.valueOf(longitude);

                                    }
                                }
                            }, Looper.getMainLooper());

                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    private void turnOnGPS() {



        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(DetailTransactionActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(DetailTransactionActivity.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }
}
