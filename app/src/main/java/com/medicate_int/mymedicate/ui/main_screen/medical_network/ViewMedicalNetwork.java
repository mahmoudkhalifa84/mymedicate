package com.medicate_int.mymedicate.ui.main_screen.medical_network;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.HomeActivity;
import com.medicate_int.mymedicate.ui.Login;

public class ViewMedicalNetwork extends Fragment {
    CacheHelper statics;
    final String NUMBER = "218910007286";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.show_providers_network, container, false);
        statics = new CacheHelper(v.getContext());
        v.findViewById(R.id.imageView54).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        v.findViewById(R.id.net_hospital).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_PLACE("داخل الشبكات الطبية");
                statics.setMY_NETWORK("1786");
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        v.findViewById(R.id.net_pha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_PLACE("داخل الشبكات الطبية");
                statics.setMY_NETWORK("2");
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        v.findViewById(R.id.net_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                statics.setMY_PLACE("داخل الشبكات الطبية");
                statics.setMY_NETWORK("6");
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        v.findViewById(R.id.net_visical_and_gym).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_PLACE("داخل الشبكات الطبية");
                statics.setMY_NETWORK("1112");
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        v.findViewById(R.id.net_laps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_PLACE("داخل الشبكات الطبية");
                statics.setMY_NETWORK("910");
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        v.findViewById(R.id.net_hearing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_PLACE("داخل الشبكات الطبية");
                statics.setMY_NETWORK("1415");
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
      /*  v.findViewById(R.id.net_empa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dailog = new Dialog(getActivity(), R.style.PauseDialog);
                dailog.setContentView(R.layout.emp_dig);
                dailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dailog.setCancelable(true);
                CardView call = dailog.findViewById(R.id.chat_call);
                CardView vib = dailog.findViewById(R.id.chat_viber);
                CardView wht = dailog.findViewById(R.id.chat_whatspp);
                wht.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dailog.dismiss();
                        Moudle.OpenWhatsapp(getActivity());
                    }
                });
                vib.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dailog.dismiss();
                        Moudle.OpenViber(getActivity());
                    }
                });
                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dailog.dismiss();
                        Moudle.CallCenter(getActivity());
                    }
                });
                dailog.show();

            }
        });*/
        return v;
    }

}
