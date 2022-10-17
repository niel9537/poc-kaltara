package com.kartala.poc.network;

import com.kartala.poc.model.Login;
import com.kartala.poc.model.Product;
import com.kartala.poc.model.ProductModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    //Login
    @POST("auth/login")
    @FormUrlEncoded
    Call<Login> login(@Field("username") String username,
                      @Field("password") String password);
    @GET("product/getallproduct")
    Call<Product> getAllProduct(@Header("Authorization") String access_token);
    @GET("product/gettableproduct")
    Call<Product> gettableproduct(@Header("Authorization") String access_token);
    @GET("product/getchairproduct")
    Call<Product> getchairproduct(@Header("Authorization") String access_token);
//    //Get Profile
//    @POST("user/get_data_profile")
//    @FormUrlEncoded
//    Call<Profile> getProfile(@Header("id_user") String id_user2, @Header("access_token") String access_token, @Field("id_user") String id_user);

}
