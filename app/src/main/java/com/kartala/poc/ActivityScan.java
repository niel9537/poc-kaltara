package com.kartala.poc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.kartala.poc.model.Transaksi;
import com.kartala.poc.network.ApiHelper;
import com.kartala.poc.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityScan extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    Button btnBack;
    String TOKEN, ID,ROLE,TRANSACTION_ID,STATUS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr_activity);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            TRANSACTION_ID= extras.getString("id_transaction");
            STATUS= extras.getString("status");
            //The key argument here must match that used in the other activity
        }
        btnBack = findViewById(R.id.btnBack);
        SharedPref.init(getApplicationContext());
        TOKEN = SharedPref.getString(SharedPref.KEY_TOKEN,null);
        ID = SharedPref.getString(SharedPref.KEY_ID,null);
        ROLE = SharedPref.getString(SharedPref.KEY_ROLE,null);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setTransactionStatus(TRANSACTION_ID,ROLE,ID,STATUS);

//                        intent.putExtra("TRXID",result.getText().toString());
//                        intent.putExtra("STATUS", Config.SCAN);
//                        Log.d("POST ID SCAN",""+result.getText().toString());

                        //Toast.makeText(ActivityScan.this, result.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        mCodeScanner.startPreview();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityScan.this,DetailTransactionActivity.class);
                startActivity(intent);
            }
        });
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION ;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void setTransactionStatus(String id_transaction,String role,String id_user, String status){
        ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Transaksi> transaksiCall = apiInterface.settransaction(TOKEN,TRANSACTION_ID,ROLE,ID,STATUS);
        transaksiCall.enqueue(new Callback<Transaksi>() {
            @Override
            public void onResponse(Call<Transaksi> call, Response<Transaksi> response) {
                if(response.isSuccessful()){
                    Intent intent = new Intent(ActivityScan.this,MainActivity.class);
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
}
