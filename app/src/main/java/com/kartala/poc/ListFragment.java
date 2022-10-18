package com.kartala.poc;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.kartala.poc.model.ListTransaction;
import com.kartala.poc.model.Product;
import com.kartala.poc.model.ProductModel;
import com.kartala.poc.model.TransactionModel;
import com.kartala.poc.network.ApiHelper;
import com.kartala.poc.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {
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

    public ListFragment() {
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
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
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
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        // getActivity().setTitle("SURVEY KOMPETITOR IDM");
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#64a4d4\">" + getString(R.string.app_name) + "</font>")));
        init(view);
        String role = SharedPref.getString(SharedPref.KEY_ROLE,null);
        if(role.equals("4")){
            getTransaction();
        }else{
            getTransactionAll();
        }

//
        return view;
        //return inflater.inflate(R.layout.fragment_home, container, false);
    }
    private void init(View view){
        SharedPref.init(getActivity());
        ID = SharedPref.getString(SharedPref.KEY_ID,null);
        String nama = SharedPref.getString(SharedPref.KEY_NAME,null);
        TOKEN = SharedPref.getString(SharedPref.KEY_TOKEN,null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvTransaksi);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }
    public void getTransaction() {
        ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<TransactionModel> transaksiCall = apiInterface.getcustomertransaction(
                TOKEN,
                ID);
        transaksiCall.enqueue(new Callback<TransactionModel>() {
            @Override
            public void onResponse(Call<TransactionModel> call, Response<TransactionModel> response) {
                if(response.isSuccessful()){
                    List<ListTransaction> transactions = response.body().getListTransaction();
                    mAdapter = new TransactionAdapter(transactions,getActivity());
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    Toast.makeText(getActivity(), "Error : "+response.message().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransactionModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Error : "+t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void getTransactionAll() {
        ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<TransactionModel> transaksiCall = apiInterface.getalltransaction(
                TOKEN);
        transaksiCall.enqueue(new Callback<TransactionModel>() {
            @Override
            public void onResponse(Call<TransactionModel> call, Response<TransactionModel> response) {
                if(response.isSuccessful()){
                    List<ListTransaction> transactions = response.body().getListTransaction();
                    mAdapter = new TransactionAdapter(transactions,getActivity());
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    Toast.makeText(getActivity(), "Error : "+response.message().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransactionModel> call, Throwable t) {
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