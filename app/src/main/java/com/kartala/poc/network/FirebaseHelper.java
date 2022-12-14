package com.kartala.poc.network;

import com.kartala.poc.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirebaseHelper {
    private static String BASE_URL = "https://fcm.googleapis.com/fcm/";

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
