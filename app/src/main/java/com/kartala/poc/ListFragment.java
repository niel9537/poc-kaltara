package com.kartala.poc;



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
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView mRecyclerView;
//    private RecyclerView.LayoutManager mLayoutManager;
//    SharedPreferences sharedPreferences;
//    private static final String SHARED_PREF_NAME = "mypref";
//    private static final String KEY_NIK = "nik";
//    private static final String KEY_TOKEN = "token";
//    String NIK = "";
//    String TOKEN = "";
//    TextView txtName, txtRole;
//    Button btnProfile;
//    String tes = "";
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public ListFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment HomeFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ListFragment newInstance(String param1, String param2) {
//        ListFragment fragment = new ListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        // getActivity().setTitle("SURVEY KOMPETITOR IDM");
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#64a4d4\">" + getString(R.string.titlehome) + "</font>")));
//        txtName = view.findViewById(R.id.txtName);
//        txtRole = view.findViewById(R.id.txtRole);
//        btnProfile = view.findViewById(R.id.btnProfile);
//        sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
//        NIK = sharedPreferences.getString(KEY_NIK,null);
//        TOKEN = sharedPreferences.getString(KEY_TOKEN,null);
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvBrandkompetitor);
//        mLayoutManager = new LinearLayoutManager(getContext());
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        btnProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), ProfileActivity.class);
//                startActivity(intent);
//
//            }
//        });
//        getProfile();
//        getBrandKompetitor();
//        return view;
//        //return inflater.inflate(R.layout.fragment_home, container, false);
//    }
//
//    public void getBrandKompetitor() {
//        Trace myTrace = FirebasePerformance.getInstance().newTrace("BrandKompetitor - Retrofit");
//        myTrace.start();
//        long startTime = System.currentTimeMillis();
//        ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
//        Call<Brand> brandCall = apiInterface.getBrandKompetitor(
//                NIK,
//                TOKEN);
//        brandCall.enqueue(new Callback<Brand>() {
//            @Override
//            public void onResponse(Call<Brand> call, Response<Brand> response) {
//                if(response.body().getResult().getResultCode().equals(SUCCESS)) {
//                    long elapsedTime = System.currentTimeMillis() - startTime;
//                    Log.d("RETRO LIST BRAND","Total elapsed http request/response time in milliseconds: " + elapsedTime);
//                    myTrace.putAttribute("brandkompetitor_elapsedtime_retrofit", String.valueOf(elapsedTime));
//                    myTrace.stop();
//                    List<BrandKompetitor> brandKompetitorList = response.body().getBrandKompetitor();
//                    mAdapter = new BrandKompetitorAdapter(brandKompetitorList);
//                    mRecyclerView.setAdapter(mAdapter);
//                }
//                else {
//                    Log.d("RETRO", "ON FAIL : " + response.body().getResult().getResultMessage());
//                    Toast.makeText(getActivity(), "Tampil Kompetitor Gagal", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Brand> call, Throwable t) {
//                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
//                Toast.makeText(getActivity(), "Tampil Kompetitor Gagal", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//
//    public void getProfile(){
//        ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
//        Call<Profile> loginCall = apiInterface.getProfile(NIK,TOKEN,NIK);
//        loginCall.enqueue(new Callback<Profile>() {
//            @Override
//            public void onResponse(Call<Profile> call, Response<Profile> response) {
//                if(response.body().getResult().getResultCode().equals(SUCCESS)) {
//                    Log.d("RETRO", "ON SUCCESS : " + response.body().getResult().getResultMessage());
//                    txtName.setText(response.body().getUserDetail().name);
//                    txtRole.setText(response.body().getUserDetail().jabatan);
//
//                }
//                else {
//                    Log.d("RETRO", "ON FAIL : " + response.body().getResult().getResultMessage());
//                    Toast.makeText(getActivity(), "Tampil Profile Gagal", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Profile> call, Throwable t) {
//                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
////                Intent intent = new Intent(getActivity(), LoginActivity.class);
////                startActivity(intent);
//            }
//        });
//    }
//
//    public void callParentMethod(){
//        getActivity().onBackPressed();
//    }
//
//    public void onBackPressed() {
//    }
}