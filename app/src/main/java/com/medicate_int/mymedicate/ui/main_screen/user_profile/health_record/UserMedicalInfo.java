package com.medicate_int.mymedicate.ui.main_screen.user_profile.health_record;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;

public class UserMedicalInfo extends Fragment {
    View view;
    CacheHelper statics;
    TextView ed_drink ,ed_smoke, ed_bload,ed_state, ed_wight,ed_height;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_personal_info, container, false);
        statics = new CacheHelper(getActivity());
        view.findViewById(R.id.imageView73).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_PLACE("السجل الطبي/بيانات الشخصية");
                Save();
                getActivity().onBackPressed();
            }
        });
        view.findViewById(R.id.ed_blod).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlodType();
            }
        });
        view.findViewById(R.id.ed_h).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edHeight();
            }
        });
        view.findViewById(R.id.ed_w).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edWidth();
            }
        });
        view.findViewById(R.id.ed_smo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mathod(1);
            }
        });
        view.findViewById(R.id.ed_drok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mathod(2);
            }
        });
        view.findViewById(R.id.ed_st).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                State();
            }
        });
        view.findViewById(R.id.b123).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setMY_PLACE("السجل الطبي/بيانات الشخصية");
                Save();
               getActivity().onBackPressed();

            }
        });
        ed_drink = view.findViewById(R.id.ed_drink_txt);
        ed_smoke = view.findViewById(R.id.ed_smoke_txt);
        ed_bload = view.findViewById(R.id.ed_blod_txt);
        ed_state = view.findViewById(R.id.ed_stst_txt);
        ed_wight = view.findViewById(R.id.ed_w_txt);
        ed_height = view.findViewById(R.id.ed_h_txt);
        Load();
        return view;
    }
    private void Load(){
        if (!statics.getUserBlodType().equals("null")) ed_bload.setText(statics.getUserBlodType());
        if (!statics.getUserDrink().equals("null")) ed_drink.setText(statics.getUserDrink());
        if (!statics.getUserSmoke().equals("null")) ed_smoke.setText(statics.getUserSmoke());
        if (!statics.getUserState().equals("null")) ed_state.setText(statics.getUserState());
        if (!statics.getUserWight().equals("null")) ed_wight.setText(statics.getUserWight());
        if (!statics.getUserHight().equals("null")) ed_height.setText(statics.getUserHight());
    }
    private void Save() {
        statics.UserState(ed_state.getText().toString());
        statics.UserSmoke(ed_smoke.getText().toString());
        statics.UserDrink(ed_drink.getText().toString());
        statics.UserHight(ed_height.getText().toString());
        statics.UserWight(ed_wight.getText().toString());
        statics.UserBlodType(ed_bload.getText().toString());
    }

    private void Mathod(int i) {
        Dialog dialog = new Dialog(getActivity(), R.style.PauseDialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.fragment_dailog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView textView = dialog.findViewById(R.id.text_dig);
        TextView ok = dialog.findViewById(R.id.di_ok);
        TextView no = dialog.findViewById(R.id.di_but_cancal);
        if (i == 1)
            textView.setText(getResources().getString(R.string.smoking));
        else
            textView.setText(getResources().getString(R.string.drinking));

        ok.setText(getResources().getString(R.string.ok_ok));
        no.setText(getResources().getString(R.string.no));
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 1)
                    ed_smoke.setText(getResources().getString(R.string.ok_ok));
                else
                    ed_drink.setText(getResources().getString(R.string.ok_ok));
                dialog.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 1)
                    ed_smoke.setText(getResources().getString(R.string.no));
                else
                    ed_drink.setText(getResources().getString(R.string.no));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void BlodType() {
        Dialog dialog = new Dialog(getActivity(), R.style.PauseDialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.ed_blod_type_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.findViewById(R.id.a_p).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                 ed_bload.setText("+A");
            }
        });
        dialog.findViewById(R.id.a_m).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ed_bload.setText("-A");
            }
        });
        dialog.findViewById(R.id.b_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                 ed_bload.setText("+B");
            }
        });
        dialog.findViewById(R.id.b_m).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ed_bload.setText("-B");
            }
        });
        dialog.findViewById(R.id.o_p).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ed_bload.setText("+O");
            }
        });
        dialog.findViewById(R.id.o_m).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ed_bload.setText("-O");
            }
        });
        dialog.findViewById(R.id.ab_p).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ed_bload.setText("+AB");
            }
        });
        dialog.findViewById(R.id.ab_m).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ed_bload.setText("-AB");
            }
        });
        dialog.show();

    }

    public void State() {
        Dialog dialog = new Dialog(getActivity(), R.style.PauseDialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.ed_state);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.findViewById(R.id.s_marid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                ed_state.setText(getResources().getString(R.string.stat_marid));
            }
        });
        dialog.findViewById(R.id.s_single).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                ed_state.setText(getResources().getString(R.string.stat_single));
            }
        });
        dialog.findViewById(R.id.s_devo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                ed_state.setText(getResources().getString(R.string.stat_devorc));
            }
        });
        dialog.findViewById(R.id.s_arml).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                ed_state.setText(getResources().getString(R.string.stat_wi));
            }
        });
        dialog.show();

    }

    public void edWidth() {
        EditText editText;
        Dialog dialog = new Dialog(getActivity(), R.style.PauseDialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.ed_height);
        editText = dialog.findViewById(R.id.ed_dailog_he);
        TextView textView2 = dialog.findViewById(R.id.text_dig);
        textView2.setText(getResources().getString(R.string.width));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.findViewById(R.id.button123).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().isEmpty())
                    ed_wight.setText(getResources().getString(R.string.known));
                else
                    ed_wight.setText(editText.getText().toString().trim());

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void edHeight() {
        EditText editText;
        Dialog dialog = new Dialog(getActivity(), R.style.PauseDialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.ed_height);
        editText = dialog.findViewById(R.id.ed_dailog_he);
        TextView textView2 = dialog.findViewById(R.id.text_dig);
        textView2.setText(getResources().getString(R.string.height));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.findViewById(R.id.button123).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().isEmpty())
                    ed_height.setText(getResources().getString(R.string.known));
                else
                    ed_height.setText(editText.getText().toString().trim());

                dialog.dismiss();
            }
        });
        dialog.show();
    }
}