package com.kartala.poc;

import static android.content.Context.MODE_PRIVATE;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.kartala.poc.model.Product;
import com.kartala.poc.model.ProductModel;
import com.kartala.poc.network.ApiHelper;
import com.kartala.poc.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    ImageSlider imageSlider;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    String ID = "";
    String TOKEN = "";
    TextView txtWelcome, txtAll, txtChair, txtTable;
    String tes = "";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // getActivity().setTitle("SURVEY KOMPETITOR IDM");
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#64a4d4\">" + getString(R.string.app_name) + "</font>")));
        init(view);
        getProducts();
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://expatliving.sg/wp-content/uploads/2019/01/Originals-sofa-4-rs.jpg", ScaleTypes.CENTER_INSIDE));
        slideModels.add(new SlideModel("https://d3p0bla3numw14.cloudfront.net/news-content/img/2021/10/27211303/Furniture-Unik-untuk-Rumah-Kecil.jpg",ScaleTypes.CENTER_INSIDE));
        slideModels.add(new SlideModel("https://i.ibb.co/C9qgLtV/upload1.jpg",ScaleTypes.CENTER_INSIDE));
        imageSlider.setImageList(slideModels);
        txtAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getProducts();
            }
        });
        txtChair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getChair();
            }
        });
        txtTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTable();
            }
        });
        return view;
        //return inflater.inflate(R.layout.fragment_home, container, false);
    }
    private void init(View view){
        imageSlider = view.findViewById(R.id.slider);
        txtWelcome = view.findViewById(R.id.txtWelcome);
        txtAll = view.findViewById(R.id.txtAll);
        txtChair = view.findViewById(R.id.txtChair);
        txtTable = view.findViewById(R.id.txtTable);
        SharedPref.init(getActivity());
        ID = SharedPref.getString(SharedPref.KEY_ID,null);
        TOKEN = SharedPref.getString(SharedPref.KEY_TOKEN,null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvProduct);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }
    public void getProducts() {
        ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Product> productCall = apiInterface.getAllProduct(
                TOKEN);
        productCall.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    List<ProductModel> products = response.body().getProduct();
                    mAdapter = new ProductAdapter(products);
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    Toast.makeText(getActivity(), "Error : "+response.message().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(getActivity(), "Error : "+t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getChair() {
        ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Product> productCall = apiInterface.getchairproduct(
                TOKEN);
        productCall.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    List<ProductModel> products = response.body().getProduct();
                    mAdapter = new ProductAdapter(products);
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    Toast.makeText(getActivity(), "Error : "+response.message().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(getActivity(), "Error : "+t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getTable() {
        ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Product> productCall = apiInterface.gettableproduct(
                TOKEN);
        productCall.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    List<ProductModel> products = response.body().getProduct();
                    mAdapter = new ProductAdapter(products);
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    Toast.makeText(getActivity(), "Error : "+response.message().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(getActivity(), "Error : "+t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void callParentMethod(){
        getActivity().onBackPressed();
    }

    public void onBackPressed() {
    }
}