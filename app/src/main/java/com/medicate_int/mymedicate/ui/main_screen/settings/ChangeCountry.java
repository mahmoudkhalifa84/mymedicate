package com.medicate_int.mymedicate.ui.main_screen.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.HomeActivity;


public class ChangeCountry extends Fragment {
    View view;
    CacheHelper statics;
    TextView libya, egy, tunis, aljaz, espain, germany, etaly, ukranya, turkia;
    String mycountry;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        statics = new CacheHelper(context);
        mycountry = statics.getMY_CONTRY().trim();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings_change_country, container, false);
        libya = view.findViewById(R.id.sl_libya);
        egy = view.findViewById(R.id.sl_egy);
        tunis = view.findViewById(R.id.sl_tunis);
        aljaz = view.findViewById(R.id.sl_aljaz);
        espain = view.findViewById(R.id.sl_espain);
        germany = view.findViewById(R.id.sl_germany);
        etaly = view.findViewById(R.id.sl_italy);
        ukranya = view.findViewById(R.id.sl_ukranya);
        turkia = view.findViewById(R.id.sl_turkia);
        view.findViewById(R.id.set_lan_ok2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
getActivity().onBackPressed();
            }
        });
        chackMyCountry();
        libya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                statics.setMY_CONTRY("1");
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        egy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_CONTRY("4");

                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        tunis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_CONTRY("2");

                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        aljaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_CONTRY("3");

                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        etaly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_CONTRY("17"); // todo
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        germany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_CONTRY("6");

                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        ukranya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_CONTRY("9");

                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        turkia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_CONTRY("5");

                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        espain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_CONTRY("7");

                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        return view;
    }

    public void chackMyCountry() {
        mycountry = statics.getMY_CONTRY().trim();
        if (mycountry.equals("1")) {
            libya.setTextColor(getResources().getColor(R.color.black));
            libya.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getActivity() ,R.drawable.ok_arraw), null);
            egy.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            aljaz.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            tunis.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            espain.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            germany.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            etaly.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            ukranya.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            turkia.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        } else if (mycountry.equals("2")) {
            tunis.setTextColor(getResources().getColor(R.color.black));
            tunis.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getActivity() ,R.drawable.ok_arraw), null);
            egy.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            aljaz.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            libya.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            espain.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            germany.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            etaly.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            ukranya.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            turkia.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        } else if (mycountry.equals("4")) {
            egy.setTextColor(getResources().getColor(R.color.black));
            egy.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getActivity() ,R.drawable.ok_arraw), null);
            tunis.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            aljaz.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            libya.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            espain.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            germany.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            etaly.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            ukranya.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            turkia.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        } else if (mycountry.equals("3")) {
            aljaz.setTextColor(getResources().getColor(R.color.black));
            aljaz.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getActivity() ,R.drawable.ok_arraw), null);
            tunis.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            egy.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            libya.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            espain.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            germany.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            etaly.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            ukranya.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            turkia.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        } else if (mycountry.equals("7")) {
            espain.setTextColor(getResources().getColor(R.color.black));
            espain.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getActivity() ,R.drawable.ok_arraw), null);
            tunis.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            egy.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            libya.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            aljaz.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            germany.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            etaly.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            ukranya.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            turkia.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        } else if (mycountry.equals("6")) {
            germany.setTextColor(getResources().getColor(R.color.black));
            germany.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getActivity() ,R.drawable.ok_arraw), null);
            tunis.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            egy.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            libya.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            aljaz.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            espain.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            etaly.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            ukranya.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            turkia.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        } else if (mycountry.equals("17")) {
            etaly.setTextColor(getResources().getColor(R.color.black));
            etaly.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getActivity() ,R.drawable.ok_arraw), null);
            tunis.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            egy.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            libya.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            aljaz.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            germany.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            espain.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            ukranya.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            turkia.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        } else if (mycountry.equals("9")) {
            ukranya.setTextColor(getResources().getColor(R.color.black));
            ukranya.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getActivity() ,R.drawable.ok_arraw), null);
            tunis.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            egy.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            libya.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            aljaz.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            germany.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            espain.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            etaly.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            turkia.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        } else if (mycountry.equals("5")) {
            turkia.setTextColor(getResources().getColor(R.color.black));
            turkia.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getActivity() ,R.drawable.ok_arraw), null);
            tunis.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            egy.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            libya.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            aljaz.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            germany.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            espain.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            etaly.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            ukranya.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        }
        //
    }
}