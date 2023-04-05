package com.medicate_int.mymedicate.ui.main_screen.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.SetLocal;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.HomeActivity;

import java.util.Locale;

public class ChangeLanguage extends Fragment {
    View view;
    TextView ar,fr,en;
    TextView ok;
    String lang;

    CacheHelper statics;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statics = new CacheHelper(getActivity());
        lang = SetLocal.getLong(getActivity().getApplicationContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_settings_change_language, container, false);
        ar = view.findViewById(R.id.set_lan_arabic);
        fr = view.findViewById(R.id.set_lan_franci);
        en = view.findViewById(R.id.set_lan_english);
        ok = view.findViewById(R.id.set_lan_ok);
        chackLang();
        ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lang.equals("ar")) {
                    setLocale("ar", getActivity().getApplicationContext());
                    chackLang();
                }
            }
        });
        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lang.equals("en")) {
                    setLocale("en", getActivity().getApplicationContext());
                    chackLang();
                }
            }
        });
        fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lang.equals("fr")){
                    setLocale("fr", getActivity().getApplicationContext());
                    chackLang();
                }
            }
        });
        view.findViewById(R.id.set_lan_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }
    public void setLocale(String lang, Context context) {
        SharedPreferences pref = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Locale loc = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();
        config.locale = loc;
        config.setLayoutDirection(loc);
        res.updateConfiguration(config,displayMetrics);
        CacheHelper statics = new CacheHelper(context);
        statics.savePrefsData("lang",lang);
        editor.putString("lang",lang);
        editor.apply();
        startActivity(new Intent(getActivity(), HomeActivity.class));
        getActivity().finish();
    }
    private void chackLang() {
        if (lang.equals("ar")){
            ar.setTextColor(getResources().getColor(R.color.black));
            ar.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,ContextCompat.getDrawable(getActivity(),R.drawable.ok_arraw),null);
            fr.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,null);
            en.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,null);
        }
        else if (lang.equals("fr")){
            fr.setTextColor(getResources().getColor(R.color.black));
            fr.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null, ContextCompat.getDrawable(getActivity(),R.drawable.ok_arraw),null);
            ar.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,null);
            en.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,null);
        }
        else {
            en.setTextColor(getResources().getColor(R.color.black));
            en.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,ContextCompat.getDrawable(getActivity(),R.drawable.ok_arraw),null);
            fr.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,null);
            ar.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,null);
        }

    }


}