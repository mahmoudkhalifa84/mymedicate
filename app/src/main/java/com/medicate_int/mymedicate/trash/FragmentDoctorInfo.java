package com.medicate_int.mymedicate.trash;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medicate_int.mymedicate.R;


public class FragmentDoctorInfo extends Fragment {

   View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_doctor_info, container, false);
        view.findViewById(R.id.textView55).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity(), R.style.PauseDialog);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.fragment_dailog);
                TextView textView = dialog.findViewById(R.id.text_dig);
                textView.setText("سيتم حجز قيمة الاستشارة 30 د.ل هل تريد تأكيد العملية");
                dialog.findViewById(R.id.di_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.di_but_cancal).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        return view;
    }
}