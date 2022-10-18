package com.kartala.poc.network;

import com.kartala.poc.model.Login;
import com.kartala.poc.model.Product;
import com.kartala.poc.model.ProductModel;
import com.kartala.poc.model.TransactionModel;
import com.kartala.poc.model.Transaksi;

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
    @POST("transaction/purchaseorder")
    @FormUrlEncoded
    Call<Transaksi> purchase(@Header("Authorization") String access_token,
                             @Field("id_customer") String id_customer,
                             @Field("id_driver") String id_driver,
                             @Field("id_admin") String id_admin,
                             @Field("id_product") String id_product,
                             @Field("vendor") String vendor,
                             @Field("name") String name,
                             @Field("phone") String phone,
                             @Field("created_at") String created_at,
                             @Field("update_at") String update_at,
                             @Field("status") String status,
                             @Field("address") String address
    );
    @POST("transaction/listTransactionByCustomer")
    @FormUrlEncoded
    Call<TransactionModel> getcustomertransaction(@Header("Authorization") String access_token,
                                                  @Field("id_customer") String id_customer
    );
    @GET("transaction/listTransactionAll")
    Call<TransactionModel> getalltransaction(@Header("Authorization") String access_token
    );
    @POST("transaction/updateTransaction")
    @FormUrlEncoded
    Call<Transaksi> settransaction(@Header("Authorization") String access_token,
                                   @Field("id_transaction") String id_transaction,
                                   @Field("role") String role,
                                   @Field("id_user") String id_user,
                                   @Field("status") String status
    );
}
