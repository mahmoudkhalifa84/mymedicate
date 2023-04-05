package com.medicate_int.mymedicate.ui.main_screen.card_request;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.HomeActivity;

public class CardRequestIntro extends Fragment implements View.OnClickListener {


    View view;
    CacheHelper statics;
    ScrollView mScrollView;
    TextView mTextStatus;
    boolean bottom = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_family_card_intro, container, false);
        view.findViewById(R.id.imageView58).setOnClickListener(this);
        view.findViewById(R.id.textView59).setOnClickListener(this);
        view.findViewById(R.id.textView57).setOnClickListener(this);

        statics = new CacheHelper(getActivity());

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView58:
                getActivity().onBackPressed();
                break;
            case R.id.textView59:
                statics.setMY_PLACE("طلب بطاقة");
                startActivity(new Intent(getActivity(), HomeActivity.class));
                break;
        }
    }
}