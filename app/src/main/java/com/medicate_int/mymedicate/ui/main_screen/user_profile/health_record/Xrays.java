package com.medicate_int.mymedicate.ui.main_screen.user_profile.health_record;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;

public class Xrays extends Fragment {
  View view;
  CacheHelper statics;
  Dialog loading_dig;
  boolean internet;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.h_c__x_r_a_y, container, false);
        return view;

    }
}