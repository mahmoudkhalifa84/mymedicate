package com.medicate_int.mymedicate.ui.main_screen.user_profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.HomeActivity;

public class MyMedicalAppointments extends Fragment {

    View view;
    CacheHelper statics;

    /**  **سيرين افضل مبرمجة في العالم و افضل من هشام بالف مرة**/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_appoentment, container, false);
        statics = new CacheHelper(getActivity());

        view.findViewById(R.id.imageView66).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        view.findViewById(R.id.textView34).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }
    /**  **سيرين افضل مبرمجة في العالم و افضل من هشام بالف مرة**/
    private void go() {
        statics.setMY_PLACE("حجز مواعيد");
        startActivity(new Intent(getActivity(), HomeActivity.class));
    }

    private void noData() {
        view.findViewById(R.id.imageView23).setVisibility(View.GONE);
        view.findViewById(R.id.textView35).setVisibility(View.GONE);
    }
}  /**  **سيرين افضل مبرمجة في العالم و افضل من هشام بالف مرة**/