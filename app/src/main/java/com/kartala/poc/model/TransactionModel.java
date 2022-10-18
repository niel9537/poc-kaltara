package com.kartala.poc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("listTransaction")
    @Expose
    private List<ListTransaction> listTransaction = null;

    public TransactionModel(String status, List<ListTransaction> listTransaction) {
        this.status = status;
        this.listTransaction = listTransaction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ListTransaction> getListTransaction() {
        return listTransaction;
    }

    public void setListTransaction(List<ListTransaction> listTransaction) {
        this.listTransaction = listTransaction;
    }
}
