package com.medicate_int.mymedicate.trash;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;


public class AdvSecurity extends Fragment {
    View view;
    CacheHelper statics;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_adv_security, container, false);
        return  view;
     }
}