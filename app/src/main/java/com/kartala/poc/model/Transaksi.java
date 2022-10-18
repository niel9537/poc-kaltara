package com.kartala.poc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaksi {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("transaksi")
    @Expose
    private Boolean transaksi;

    public Transaksi(String status, Boolean transaksi) {
        this.status = status;
        this.transaksi = transaksi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Boolean transaksi) {
        this.transaksi = transaksi;
    }
}
