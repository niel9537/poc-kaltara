package com.kartala.poc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransaksiNotif {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("transaksi")
    @Expose
    private String transaksi;

    public TransaksiNotif(String status, String transaksi) {
        this.status = status;
        this.transaksi = transaksi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(String transaksi) {
        this.transaksi = transaksi;
    }
}
