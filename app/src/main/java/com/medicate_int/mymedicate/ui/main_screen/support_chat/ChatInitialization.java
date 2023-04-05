package com.medicate_int.mymedicate.ui.main_screen.support_chat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.medicate_int.mymedicate.module.Moudle;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.HomeActivity;
import com.medicate_int.mymedicate.module.Statics;
import com.medicate_int.mymedicate.ui.Login;

public class ChatInitialization extends Fragment {
    EditText e_name, e_text, e_phone;
    CardView wht, vib, call;
    CacheHelper statics;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.new_chat_layout, container, false);
        statics = new CacheHelper(getActivity());
        e_name = v.findViewById(R.id.chat_name);
        e_text = v.findViewById(R.id.chat_text);
        e_phone = v.findViewById(R.id.chat_phone);
        call = v.findViewById(R.id.chat_call);
        v.findViewById(R.id.chat_sent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!e_name.getText().toString().isEmpty() &&
                        !e_text.getText().toString().isEmpty() &&
                        !e_phone.getText().toString().isEmpty()) {
                    statics.setUserName(e_name.getText().toString());
                    statics.setUserPhone(e_phone.getText().toString());
                    statics.setTxt(e_text.getText().toString());
                    statics.setMY_PLACE("داخل الدردشة");
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                }
                else {
                    Login.Message(getString(R.string.plz_fill_data),getActivity());
                }
            }
        });
        v.findViewById(R.id.imageView67).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getActivity().onBackPressed();
            }
        });
        vib = v.findViewById(R.id.chat_viber);
        wht = v.findViewById(R.id.chat_whatspp);
        wht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Moudle.OpenWhatsapp(getActivity());
            }
        });
        vib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Moudle.OpenViber(getActivity());
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel: " + Statics.PNONE_NUMMBER));
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    startActivity(i);
                }
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        }
    }
}
