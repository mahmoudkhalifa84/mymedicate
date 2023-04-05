package com.medicate_int.mymedicate.ui.main_screen.support_services;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.medicate_int.mymedicate.adapter.BookingCityItemAdabter;
import com.medicate_int.mymedicate.models.BookingSpecializationModel;
import com.medicate_int.mymedicate.module.Moudle;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class SupportServices
        extends Fragment implements BookingCityItemAdabter.onCLickLis2 {

    View view;
    CacheHelper statics;
    String place;
    TextView ok;
    RecyclerView recyclerView;
    BottomSheetDialog sheetBehavior;
    List<BookingSpecializationModel> bookingSpecializationModels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_our_service, container, false);
        statics = new CacheHelper(getActivity());
        place = "تونس";
        ok = view.findViewById(R.id.dd_ok);
       view.findViewById(R.id.ser_airplane).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            show(1);
           }
       });
       view.findViewById(R.id.imageView65).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
           getActivity().onBackPressed();
           }
       });
        view.findViewById(R.id.ser_hos_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(2);
            }
        });
        view.findViewById(R.id.ser_trav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(3);
            }
        });
       view.findViewById(R.id.ser_hosels).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                statics.setMY_PLACE("الفنادق");
               startActivity(new Intent(getActivity(), HomeActivity.class));
           }
       });
        sheetBehavior = new BottomSheetDialog(getActivity());
        recyclerView = new RecyclerView(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;

    }


    public void show(int i ) {
        bookingSpecializationModels = new ArrayList<>();
        Dialog dialog = new Dialog(getActivity(), R.style.PauseDialog);
        dialog.setContentView(R.layout.ser_hotal);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView imageView = dialog.findViewById(R.id.imageView25);
        if (i == 1)
            imageView.setImageResource(R.drawable.top_serv_air);
        else if (i == 2)
            imageView.setImageResource(R.drawable.top_serv_app);
        else imageView.setImageResource(R.drawable.top_serv_hot);
        dialog.findViewById(R.id.chat_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Moudle.CallCenter(getActivity());
            }
        });
        dialog.findViewById(R.id.chat_whatspp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Moudle.OpenWhatsapp(getActivity());
            }
        });
        dialog.show();
    }


    @Override
    public void onCLick2(int p) {
        place = "تونس";
    }
}