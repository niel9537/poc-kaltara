package com.kartala.poc;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ProfileFragment extends Fragment {
    ImageView imgView2;
    TextView txtLogout,txtName,txtPhone,txtRole,txtEmail;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // getActivity().setTitle("SURVEY KOMPETITOR IDM");
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#64a4d4\">" + getString(R.string.app_name) + "</font>")));
        SharedPref.init(getActivity());
        imgView2 = view.findViewById(R.id.imgProfil);
        txtLogout = view.findViewById(R.id.txtLogout);
        txtName = view.findViewById(R.id.txtNames);
        txtPhone = view.findViewById(R.id.txtPhone);
        txtRole = view.findViewById(R.id.txtRole);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtName.setText(SharedPref.getString(SharedPref.KEY_NAME,null));
        txtPhone.setText(SharedPref.getString(SharedPref.KEY_PHONE,null));
        txtEmail.setText(SharedPref.getString(SharedPref.KEY_EMAIL,null));
        String role = SharedPref.getString(SharedPref.KEY_ROLE,null);
        if(role.equals("4")){
            txtRole.setText("Customer");
        }else if(role.equals("3")){
            txtRole.setText("Driver");
        }else if(role.equals("2")){
            txtRole.setText("Admin");
        }else if(role.equals("1")){
            txtRole.setText("Vendor");
        }else{

        }

        Glide.with(getActivity())
                .load("https://i.pinimg.com/originals/99/c2/c6/99c2c68a41fb5df062a8cbac15dc2ddc.png")
                .apply(RequestOptions.circleCropTransform())
                .into(imgView2);
//
        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPref.clearPreference(getActivity());
                startActivity(new Intent(getActivity(),LoginActivity.class));
            }
        });
        return view;
    }
}
