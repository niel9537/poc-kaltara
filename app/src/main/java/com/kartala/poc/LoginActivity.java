package com.kartala.poc;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.kartala.poc.model.Login;
import com.kartala.poc.network.ApiHelper;
import com.kartala.poc.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btnLogin;
    String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.VIBRATE,
            Manifest.permission.RECORD_AUDIO,
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        checkPermissions();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtUsername.getText().toString().equals("")){
                    if(!edtPassword.getText().toString().equals("")){
                        login();
                    }else{
                        Toast.makeText(getApplicationContext(),"Input password terlebih dahulu",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Input username terlebih dahulu",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init(){
        SharedPref.init(getApplicationContext());
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void login(){
        ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Login> loginCall = apiInterface.login(edtUsername.getText().toString(),edtPassword.getText().toString());
        loginCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.isSuccessful()){
                    SharedPref.putString(SharedPref.KEY_ID,response.body().getUser().getId().toString());
                    SharedPref.putString(SharedPref.KEY_TOKEN,"Bearer "+response.body().getToken().getToken().toString());
                    SharedPref.putString(SharedPref.KEY_ROLE,response.body().getUser().getRole().toString());
                    SharedPref.putString(SharedPref.KEY_NAME,response.body().getUser().getName().toString());
                    SharedPref.putString(SharedPref.KEY_PHONE,response.body().getUser().getPhone().toString());
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(),"Error : "+response.message().toString(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error : "+t.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
            return;
        }
    }
    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }


}
