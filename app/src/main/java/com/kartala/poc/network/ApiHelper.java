package com.kartala.poc.network;

import android.util.Base64;

import com.kartala.poc.Config;

import java.io.UnsupportedEncodingException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHelper {
    private static String BASE_URL = Config.BASE_URL;

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        }
        return retrofit;
    }
}
