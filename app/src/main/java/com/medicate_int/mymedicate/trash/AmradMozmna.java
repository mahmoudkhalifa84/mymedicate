package com.medicate_int.mymedicate.trash;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;

import pl.droidsonroids.gif.GifImageView;


public class AmradMozmna extends Fragment {

   View view;
   CacheHelper statics;
   Dialog dialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_amrad_mozmna, container, false);
        statics = new CacheHelper(getActivity());
        view.findViewById(R.id.mz_but_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go();
            }
        });
        view.findViewById(R.id.mz_img_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go();
            }
        });
        view.findViewById(R.id.imageView53).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }

    private void go() {
        dialog = new Dialog(getActivity() , R.style.PauseDialog);
        dialog.setContentView(R.layout.add_mzmn_dialog);
        TextView button = dialog.findViewById(R.id.feed_back_ok_byt);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ConstraintLayout main = dialog.findViewById(R.id.feed_back_main);
        main.setVisibility(View.VISIBLE);
        ConstraintLayout thanki = dialog.findViewById(R.id.feed_back_thank);
        GifImageView gifImageView = dialog.findViewById(R.id.gifImageView);
        thanki.setVisibility(View.GONE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.setVisibility(View.GONE);
                thanki.setVisibility(View.VISIBLE);
                gifImageView.setEnabled(true);
                waait();
            }
        });
        dialog.show();
    }
    private void waait() {
        final Handler handler = new Handler();
        final Runnable doNextActivity = new Runnable() {
            @Override
            public void run() {
                // Intent to jump to the next activity.
                dialog.dismiss(); // so the splash activity goes away
            }
        };

        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                handler.post(doNextActivity);
            }
        }.start();

    }
    
}