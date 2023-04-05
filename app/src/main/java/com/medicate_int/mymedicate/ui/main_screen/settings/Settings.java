package com.medicate_int.mymedicate.ui.main_screen.settings;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.medicate_int.mymedicate.module.CheckConnection;
import com.medicate_int.mymedicate.module.Moudle;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.SetLocal;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.HomeActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class Settings extends Fragment {
    View view;
    CacheHelper statics;
    Dialog dialog;
    int img;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        statics = new CacheHelper(getActivity());
        if (SetLocal.getLong(getActivity().getApplicationContext()).equals("ar"))
            img = R.drawable.ic_svg_ar;
        else if (SetLocal.getLong(getActivity().getApplicationContext()).equals("fr"))
            img = R.drawable.ic_svg_f;
        else img = R.drawable.ic_svg_en;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        view.findViewById(R.id.imageView69).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getActivity().onBackPressed();
            }
        });view.findViewById(R.id.ll_set_lang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_PLACE("تغيير اللغة");
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        view.findViewById(R.id.settings_rate_the_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Moudle.OpenMyAppStorePage(getActivity());
            }
        });
        view.findViewById(R.id.ll_set_country).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_PLACE("تغيير البلد");
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        view.findViewById(R.id.settings_send_feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Moudle.SendFeedbackDialog(getActivity());
            }
        });
        ImageView imageView = view.findViewById(R.id.img_lang);
        imageView.setImageDrawable(getResources().getDrawable(img));
        view.findViewById(R.id.settings_report_broblem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(getActivity(), R.style.PauseDialog);
                dialog.setContentView(R.layout.report_layout);
                Button button = dialog.findViewById(R.id.feed_back_ok_byt);
                EditText editText = dialog.findViewById(R.id.report_back_txt);
                EditText phne = dialog.findViewById(R.id.prp_phone);
                Spinner spinner = dialog.findViewById(R.id.prp_spinner);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ConstraintLayout main = dialog.findViewById(R.id.feed_back_main);
                main.setVisibility(View.VISIBLE);
                ConstraintLayout thanki = dialog.findViewById(R.id.feed_back_thank);
                GifImageView gifImageView = dialog.findViewById(R.id.gifImageView);
                thanki.setVisibility(View.GONE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CheckConnection.isNetworkConnected(getActivity().getApplicationContext())) {
                            if (chaker(editText.getText().toString().trim(), phne.getText().toString().trim(), spinner.getSelectedItemPosition(), getActivity())) {
                                sendPrp(editText.getText().toString().trim(), phne.getText().toString().trim(), spinner.getSelectedItem());
                                main.setVisibility(View.GONE);
                                thanki.setVisibility(View.VISIBLE);
                                gifImageView.setEnabled(true);
                                Moudle.waait(dialog);
                            }
                        } else
                            Snackbar.make(view, view.getResources().getString(R.string.cheack_int_con), Snackbar.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });
        view.findViewById(R.id.settings_tech_supprot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_PLACE("المحادثة الفورية");
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        return view;
    }

    private void sendPrp(String trim, String phone, Object selectedItem) {
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef =
                    database.getReference().getRoot().child("MedicateApp").child("FeedBack_PROBLEM").child(Calendar.getInstance().getTime().toString());
            Map<String, Object> map = new HashMap<>();
            map.put("android", Build.VERSION.RELEASE);
            map.put("userID", statics.getID());
            map.put("Prp_type", selectedItem);
            map.put("phone", phone);
            map.put("txt", trim);
            myRef.setValue(map);
        } catch (Exception e) {
            return;
        }
    }

    public static boolean chaker(String s, String b, int d, Context context) {
        Toast t = new Toast(context);
        t.setGravity(Gravity.CENTER, 0, 0);
        t.setDuration(Toast.LENGTH_SHORT);
        if (s.equals("")) {
            t = Toast.makeText(context, R.string.plz_fill_prp_des, t.getDuration());
            t.show();
            return false;
        }
        if (b.equals("")) {

            t = Toast.makeText(context, R.string.plz_fill_prp_phone, t.getDuration());
            t.show();
            return false;
        }
        if (d == 0) {

            t = Toast.makeText(context, R.string.plz_fill_chose_prp, t.getDuration());
            t.show();
            return false;
        }
        return true;
    }
}