package com.kartala.poc;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.kartala.poc.helpers.FetchURL;
import com.kartala.poc.helpers.TaskLoadedCallback;


public class ActivityMap extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    private GoogleMap gmap;
    Button btnBack;
    String latitude = "";
    MarkerOptions place1, place2;
    String longitude = "";
    private String lat = "";
    private String lon = "";
    FusedLocationProviderClient client;
    Polyline currentPolyline;
    String customer_lat = "";
    String customer_lon = "";
    String status_lat = "";
    String status_lon = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            customer_lat= extras.getString("customer_lat");
            customer_lon= extras.getString("customer_lon");
            status_lat= extras.getString("status_lat");
            status_lon= extras.getString("status_lon");
            Log.d("Lat Map ", customer_lat);
            //The key argument here must match that used in the other activity
        }
        client = LocationServices.getFusedLocationProviderClient(ActivityMap.this);
        btnBack = findViewById(R.id.btnBack);




        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(this);
        place1 = new MarkerOptions().position(new LatLng(Double.parseDouble(customer_lat), Double.parseDouble(customer_lon))).title("Location 1");
        if(status_lat.equals("") || status_lon.equals("")){
            String s = "-76.58409666583178";
            String l = "21.152490006618578";
            place2 = new MarkerOptions().position(new LatLng(Double.parseDouble(s), Double.parseDouble(l))).title("Location 2");
        }else{
            place2 = new MarkerOptions().position(new LatLng(Double.parseDouble(status_lat), Double.parseDouble(status_lon))).title("Location 2");

        }
        new FetchURL(ActivityMap.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
        getCurrentLocation();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        //origin route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        //destination route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        //mode
        String mode = "mode=" + directionMode;
        //build the param to ws
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        //output format
        String output = "json";
        //build url
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.map_key);
        return url;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }else{
            gmap.setMyLocationEnabled(true);
        }

        Log.d("mylog", "Added Markers");
        gmap.addMarker(place1);
        gmap.addMarker(place2);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = gmap.addPolyline((PolylineOptions) values[0]);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        lon = String.valueOf(location.getLongitude());
                        lat = String.valueOf(location.getLatitude());
                        LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                        gmap.animateCamera(cameraUpdate);
                    } else {
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                lon = String.valueOf(location1.getLongitude());
                                lat = String.valueOf(location1.getLatitude());
                                LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,15);
                                gmap.animateCamera(cameraUpdate);
                            }
                        };

                        client.requestLocationUpdates(locationRequest
                                , locationCallback, Looper.myLooper());
                    }
                }
            });
        }else{
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        lon = String.valueOf(location.getLongitude());
                        lat = String.valueOf(location.getLatitude());
                        LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                        gmap.animateCamera(cameraUpdate);
                    } else {
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                lon = String.valueOf(location1.getLongitude());
                                lat = String.valueOf(location1.getLatitude());
                                LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,15);
                                gmap.animateCamera(cameraUpdate);
                            }
                        };

                        client.requestLocationUpdates(locationRequest
                                , locationCallback, Looper.myLooper());
                    }
                }
            });
        }
    }




}
