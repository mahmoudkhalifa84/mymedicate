package com.medicate_int.mymedicate.ui.main_screen.booking;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.HomeActivity;


public class Booking extends Fragment {
    LinearLayout main, sec, book_dec;
    FloatingActionButton floatingActionButton;
    CacheHelper statics;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_booking, container, false);
        main = v.findViewById(R.id.ser_minLay);
        statics = new CacheHelper(v.getContext());
        floatingActionButton = v.findViewById(R.id.floatingActionButton);
        sec = v.findViewById(R.id.doc_doc_lay);
        sec.setVisibility(View.GONE);
        floatingActionButton.setVisibility(View.GONE);
        book_dec = v.findViewById(R.id.book_doc);
        book_dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.popout));
                main.setVisibility(View.GONE);
                sec.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.network_bob));
                sec.setVisibility(View.VISIBLE);
                floatingActionButton.setVisibility(View.VISIBLE);
                Temp();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sec.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.popout));
                sec.setVisibility(View.GONE);
                main.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.network_bob));
                main.setVisibility(View.VISIBLE);
                floatingActionButton.setVisibility(View.GONE);
                Temp();
            }
        });
        v.findViewById(R.id.ll_booking_adv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_PLACE("البحث حسب التخصص");
                startActivity(new Intent(getActivity(), HomeActivity.class));
                Temp();
            }
        });
        v.findViewById(R.id.ll_booking_doc_by_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            statics.setMY_PLACE("البحث حسب الاسم");
            startActivity(new Intent(getActivity(), HomeActivity.class));
                Temp();
            }
        });
        v.findViewById(R.id.ll_booking_drugs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            statics.setMY_PLACE("طلب ادوية");
            startActivity(new Intent(getActivity(), HomeActivity.class));
               // Temp();
            }
        });
        v.findViewById(R.id.ser_hosels).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            statics.setMY_PLACE("طلب خدمة او عملية");
            startActivity(new Intent(getActivity(), HomeActivity.class));
                Temp();
            }
        });
        v.findViewById(R.id.imageView56).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        v.findViewById(R.id.ll_booking_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            statics.setMY_PLACE("حجز زيارة منزلية");
            startActivity(new Intent(getActivity(), HomeActivity.class));
                Temp();
            }
        });

        return v;
    }

    public void Temp() {
      //  MyDialogsHandluer.YouAreNotInFamiily(getActivity());
    }


}
