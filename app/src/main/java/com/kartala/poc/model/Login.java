package com.kartala.poc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("token")
    @Expose
    private Token token;
    @SerializedName("user")
    @Expose
    private User user;

    public Login(String status, Token token, User user) {
        this.status = status;
        this.token = token;
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
