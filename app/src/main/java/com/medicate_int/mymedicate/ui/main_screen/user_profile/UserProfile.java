package com.medicate_int.mymedicate.ui.main_screen.user_profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.medicate_int.mymedicate.module.CheckConnection;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.HomeActivity;
import com.medicate_int.mymedicate.ui.Login;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class UserProfile extends Fragment {
    CacheHelper statics;
    View view;
    ImageView qr_img;
    boolean internet;
// hello
    ConstraintLayout qr_lay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        //    dataURL = "http://www.medicateint.com/index.php/data2/getContacts/" + statics.getID();
        qr_lay = view.findViewById(R.id.show_ly_cons);
        qr_lay.setVisibility(View.GONE);
        qr_img = view.findViewById(R.id.img_show_qr);
        TextView hide = view.findViewById(R.id.llaall);
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qr_lay.setVisibility(View.GONE);
            }
        });

        //   getJSON(dataURL);

        view.findViewById(R.id.up_qr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QRGEncoder qrgEncoder =
                        new QRGEncoder(statics.getCardNumber(), null, QRGContents.Type.TEXT, 600);
                qrgEncoder.setColorBlack(view.getResources().getColor(R.color.main));
                qrgEncoder.setColorWhite(Color.WHITE);
                try {
                    // Getting QR-Code as Bitmap
                    Bitmap bitmap = qrgEncoder.getBitmap();
                    // Setting Bitmap to ImageView
                    qr_img.setImageBitmap(bitmap);
                    qr_lay.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    Log.v("QR G :", e.toString());
                }
            }
        });
        view.findViewById(R.id.relativeLayout222).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_PLACE("المعلومات الشخصية");
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        view.findViewById(R.id.up_appo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_PLACE("مواعيدي الطبية");
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        view.findViewById(R.id.up_helth_rec).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_PLACE("السجل الطبي");
                startActivity(new Intent(getActivity(), HomeActivity.class));

            }
        });
        view.findViewById(R.id.up_apple_rec).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login.Message(getString(R.string.wil_be_av_soon), getActivity());
            }
        });
        view.findViewById(R.id.up_files_rec).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_PLACE("ملفاتي الطبية");
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        view.findViewById(R.id.imageView15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_PLACE("المنزل");
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
          /*  view.findViewById(R.id.adv_settings).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    *//*statics.setMY_PLACE("الاعدادات المتقدمة");
                    startActivity(new Intent(getActivity(), home_fragment_activity.class));*//*
                    Temp();


                }
            });*/
        setDataToTextViews();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        statics = new CacheHelper(context);
        internet = CheckConnection.isNetworkConnected(requireActivity());
    }

    private void setDataToTextViews() {
        TextView user_f_name = view.findViewById(R.id.txt_show_name);
        user_f_name.setText(statics.getUserName());
     /*   TextView ph = view.findViewById(R.id.txt_show_phone);
        ph.setText(statics.getUser);*/
        TextView em = view.findViewById(R.id.txt_show_email);
        em.setText(statics.getCardNumber());


    }

}