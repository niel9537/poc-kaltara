package com.kartala.poc;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import com.baoyachi.stepview.VerticalStepView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class StatusTransactionActivity extends AppCompatActivity {
    VerticalStepView step_view;
    TextView txtBack,txtOrder1,txtWarehouse1,txtDriver1,txtUnboxing1;
    LinearLayout lnOrder2,lnWarehouse2,lnDriver2,lnUnboxing2;
    String STATUS;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_status);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            STATUS= extras.getString("status");
            //The key argument here must match that used in the other activity
        }
        init();
        int Stat = Integer.valueOf(STATUS);
        if(Stat > 4 && Stat <=8){
            txtOrder1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_orders_icon2, 0, 0);
            lnOrder2.setBackgroundColor(getResources().getColor(R.color.purple_500));
        }else if(Stat >2 && Stat <=4){
            txtOrder1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_orders_icon2, 0, 0);
            lnOrder2.setBackgroundColor(getResources().getColor(R.color.purple_500));
            txtWarehouse1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_warehouse_icon1, 0, 0);
            lnWarehouse2.setBackgroundColor(getResources().getColor(R.color.purple_500));
        }else if(Stat >0 && Stat <=2){
            txtOrder1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_orders_icon2, 0, 0);
            lnOrder2.setBackgroundColor(getResources().getColor(R.color.purple_500));
            txtWarehouse1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_warehouse_icon1, 0, 0);
            lnWarehouse2.setBackgroundColor(getResources().getColor(R.color.purple_500));
            txtDriver1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_dispatch_icon2, 0, 0);
            lnDriver2.setBackgroundColor(getResources().getColor(R.color.purple_500));
        }else if(Stat == 0){
            txtOrder1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_orders_icon2, 0, 0);
            lnOrder2.setBackgroundColor(getResources().getColor(R.color.purple_500));
            txtWarehouse1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_warehouse_icon1, 0, 0);
            lnWarehouse2.setBackgroundColor(getResources().getColor(R.color.purple_500));
            txtDriver1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_dispatch_icon2, 0, 0);
            lnDriver2.setBackgroundColor(getResources().getColor(R.color.purple_500));
            txtUnboxing1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_surprise_icon2, 0, 0);
            lnUnboxing2.setBackgroundColor(getResources().getColor(R.color.purple_500));
        }else{
            txtOrder1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_orders_icon, 0, 0);
            lnOrder2.setBackgroundColor(getResources().getColor(R.color.gray));
            txtWarehouse1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_warehouse_icon, 0, 0);
            lnWarehouse2.setBackgroundColor(getResources().getColor(R.color.gray));
            txtDriver1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_dispatch_icon, 0, 0);
            lnDriver2.setBackgroundColor(getResources().getColor(R.color.gray));
            txtUnboxing1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_surprise_icon, 0, 0);
            lnUnboxing2.setBackgroundColor(getResources().getColor(R.color.gray));
        }
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void init(){
        SharedPref.init(getApplicationContext());
        Intent mIntent = getIntent();
        step_view = findViewById(R.id.step_view);
        setStepView();
        txtBack = findViewById(R.id.txtBack);
        txtOrder1 = findViewById(R.id.txtOrder1);
        lnOrder2 = findViewById(R.id.txtOrder2);
        txtWarehouse1 = findViewById(R.id.txtWarehouse1);
        lnWarehouse2 = findViewById(R.id.txtWarehouse2);
        txtDriver1 = findViewById(R.id.txtDriver1);
        lnDriver2 = findViewById(R.id.txtDriver2);
        txtUnboxing1 = findViewById(R.id.txtUnboxing1);
        lnUnboxing2 = findViewById(R.id.txtUnboxing2);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setStepView(){
        step_view.setStepsViewIndicatorComplectingPosition(getList().size())
                .reverseDraw(false)
                .setStepViewTexts(getList())
                .setLinePaddingProportion(1.2f)
                .setStepsViewIndicatorCompletedLineColor(getColor(R.color.purple_500))
                .setStepsViewIndicatorUnCompletedLineColor(getColor(R.color.white))
                .setStepViewComplectedTextColor(getColor(R.color.purple_500))
                .setStepViewUnComplectedTextColor(Color.WHITE)
                .setStepsViewIndicatorCompleteIcon(getDrawable(R.drawable.ic_done))
                .setStepsViewIndicatorAttentionIcon(getDrawable(R.drawable.ic_circle))
                .setStepsViewIndicatorDefaultIcon(getDrawable(R.drawable.ic_circle));
        int st = 0;
        if(Integer.valueOf(STATUS) == 8){
            st = 1;
        }else if(Integer.valueOf(STATUS) == 7){
            st = 2;
        }else if(Integer.valueOf(STATUS) == 6){
            st = 3;
        }else if(Integer.valueOf(STATUS) == 5){
            st = 4;
        }else if(Integer.valueOf(STATUS) == 4){
            st = 5;
        }else if(Integer.valueOf(STATUS) == 3){
            st = 6;
        }else if(Integer.valueOf(STATUS) == 2){
            st = 7;
        }else if(Integer.valueOf(STATUS) == 1){
            st = 8;
        }else if(Integer.valueOf(STATUS) == 0){
            st = 9;
        }else{

        }
        step_view.setStepsViewIndicatorComplectingPosition(st);
    }
    private List<String> getList(){
        List<String> list = new ArrayList<>();
        list.add("Pesanan menunggu di proses");
        list.add("Pesanan sedang di proses oleh driver ke alamat vendor");
        list.add("Pesanan sedang di proses oleh vendor");
        list.add("Pesanan sedang di antar oleh driver ke gudang terdekat");
        list.add("Pesanan telah sampai di gudang terdekat");
        list.add("Pesanan sedang di antar oleh driver ke alamat customer");
        list.add("Pesanan berhasil di terima oleh customer");
        list.add("Pesanan telah terkirim");
        return list;
    }
}
