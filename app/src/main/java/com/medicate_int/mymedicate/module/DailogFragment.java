package com.medicate_int.mymedicate.module;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medicate_int.mymedicate.R;


public class DailogFragment extends Fragment {

    View view;
    Fragment nextFrag;
    String title;
    String place;

    public DailogFragment(Fragment nextFrag, String title, String place) {
        this.nextFrag = nextFrag;
        this.title = title;
        this.place = place;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_dailog, container, false);
        return view;
    }
}