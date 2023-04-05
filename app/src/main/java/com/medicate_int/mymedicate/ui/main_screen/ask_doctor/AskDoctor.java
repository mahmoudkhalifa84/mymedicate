package com.medicate_int.mymedicate.ui.main_screen.ask_doctor;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.medicate_int.mymedicate.adapter.BookingAdvanseItemAdabter;
import com.medicate_int.mymedicate.models.BookingSpecializationModel;
import com.medicate_int.mymedicate.module.Moudle;
import com.medicate_int.mymedicate.trash.MyDialogsHandluer;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.SetLocal;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.HomeActivity;
import com.medicate_int.mymedicate.ui.Login;

import java.util.ArrayList;
import java.util.List;

public class AskDoctor extends Fragment implements BookingAdvanseItemAdabter.onCLickLis {
    View view;
    CacheHelper statics;
    TextView country, spa;
    List<BookingSpecializationModel> item;
    RecyclerView recyclerView;
    ConstraintLayout main, sec;
    String spha_selected, country_selected, TAG = "ASKDOCTAG";
    BookingAdvanseItemAdabter sph_adapter;
    Dialog bottomSheetDialog2, bottomSheetDialog, show_info;
    TextView textView;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ask_doctor, container, false);
        statics = new CacheHelper(getActivity());
        sec = view.findViewById(R.id.os_lay_main);
        main = view.findViewById(R.id.os_lay);
        textView = view.findViewById(R.id.id2154);
        context = getActivity();
        changeFreeColor();

        main.setVisibility(View.VISIBLE);
        sec.setVisibility(View.GONE);

        view.findViewById(R.id.ask_d_show_adv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.setVisibility(View.GONE);
                sec.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.popout));
                sec.setVisibility(View.VISIBLE);
            }
        });
        view.findViewById(R.id.imageView72).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        view.findViewById(R.id.imageView76).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        view.findViewById(R.id.ask_d_show_med).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (statics.getID().equals("null")) {
                    Moudle.YesCancelDailog(getActivity(), getString(R.string.login_to_contune), new Moudle.OkCancelInterface() {
                        @Override
                        public void OK() {
                            statics.setMY_PLACE("تسجيل الدخول");
                            startActivity(new Intent(context, HomeActivity.class));
                        }

                        @Override
                        public void Cancel() {

                        }
                    });
                } else
                    startActivity(new Intent(getActivity(), ChatWithMedicateDoctor.class));
            }
        });
        recyclerView = new RecyclerView(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        spa = view.findViewById(R.id.dd_spha);
        country = view.findViewById(R.id.dd_country);
        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(getActivity());
                bottomSheetDialog.setContentView(R.layout.ask_doc_country_choaser_layout);
                bottomSheetDialog.setCancelable(true);
                bottomSheetDialog.findViewById(R.id.dd_country_tuis).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        country.setText(getString(R.string.county_tunis));
                        bottomSheetDialog.dismiss();
                        spa.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.network_bob));
                        spa.setVisibility(View.VISIBLE);
                    }
                });
                bottomSheetDialog.findViewById(R.id.dd_country_libya).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        country.setText(getString(R.string.county_libya));
                        bottomSheetDialog.dismiss();
                        spa.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.network_bob));
                        spa.setVisibility(View.VISIBLE);
                    }
                });
                bottomSheetDialog.findViewById(R.id.dd_country_egy).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        country.setText(getString(R.string.county_egy));
                        bottomSheetDialog.dismiss();
                        spa.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.network_bob));
                        spa.setVisibility(View.VISIBLE);
                    }
                });
                bottomSheetDialog.findViewById(R.id.dd_country_tukya).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        country.setText(getString(R.string.county_turkia));
                        bottomSheetDialog.dismiss();
                        spa.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.network_bob));
                        spa.setVisibility(View.VISIBLE);
                    }
                });
                bottomSheetDialog.findViewById(R.id.dd_country_germany).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        country.setText(getString(R.string.county_gaemny));
                        bottomSheetDialog.dismiss();
                        spa.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.network_bob));
                        spa.setVisibility(View.VISIBLE);
                    }
                });
                bottomSheetDialog.findViewById(R.id.dd_country_itly).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        country.setText(getString(R.string.county_italya));
                        bottomSheetDialog.dismiss();
                        spa.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.network_bob));
                        spa.setVisibility(View.VISIBLE);
                    }
                });
                bottomSheetDialog.findViewById(R.id.dd_country_ukranya).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        country.setText(getString(R.string.county_ukranya));
                        bottomSheetDialog.dismiss();
                        spa.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.network_bob));
                        spa.setVisibility(View.VISIBLE);
                    }
                });
                bottomSheetDialog.findViewById(R.id.dd_country_france).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        country.setText(getString(R.string.county_franc));
                        bottomSheetDialog.dismiss();
                        spa.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.network_bob));
                        spa.setVisibility(View.VISIBLE);
                    }
                });
                bottomSheetDialog.findViewById(R.id.dd_country_canada).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        country.setText(getString(R.string.county_canada));
                        bottomSheetDialog.dismiss();
                        spa.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.network_bob));
                        spa.setVisibility(View.VISIBLE);
                    }
                });
                bottomSheetDialog.findViewById(R.id.dd_country_amrecia).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        country.setText(getString(R.string.county_amrecia));
                        bottomSheetDialog.dismiss();
                        spa.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.network_bob));
                        spa.setVisibility(View.VISIBLE);
                    }
                });
                bottomSheetDialog.show();
            }
        });
        spa.setVisibility(View.GONE);
        addItems();
        show_info = new BottomSheetDialog(getActivity());
        show_info.setContentView(R.layout.ask_doc_info_dig);
        show_info.setCancelable(true);
        show_info.setCanceledOnTouchOutside(true);
        show_info.findViewById(R.id.imageView23).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_info.dismiss();
            }
        });
        show_info.findViewById(R.id.imageView43).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_info.dismiss();
            }
        });
        spa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog2 = new BottomSheetDialog(getActivity());
                recyclerView.setAdapter(sph_adapter);
                if (recyclerView.getParent() != null) {
                    ((ViewGroup) recyclerView.getParent()).removeView(recyclerView); // <- fix
                }
                bottomSheetDialog2.setContentView(recyclerView);
                bottomSheetDialog2.setCancelable(true);
                bottomSheetDialog2.show();
                bottomSheetDialog2.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dialog.cancel();
                        dialog.dismiss();

                    }
                });
            }
        });
        view.findViewById(R.id.dd_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (country.getText().equals(getString(R.string.chose_contry)))
                    Login.Message(getString(R.string.plz_shose_country_first), getActivity());
                else if (spa.getText().equals(getString(R.string.sph)))
                    Login.Message(getString(R.string.plz_chose_spha_first), getActivity());
                else
                    Login.Message(getString(R.string.wil_be_av_soon), getActivity());
            }
        });
       /* view.findViewById(R.id.show_info_nromal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ConstraintLayout)show_info.findViewById(R.id.ask_doc_info_spa)).setVisibility(View.GONE);
                ((ConstraintLayout) show_info.findViewById(R.id.ask_doc_info_normal)).setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.network_bob));
                ((ConstraintLayout)show_info.findViewById(R.id.ask_doc_info_normal)).setVisibility(View.VISIBLE);
                show_info.show();
            }
        });
        view.findViewById(R.id.show_info_spa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ConstraintLayout) show_info.findViewById(R.id.ask_doc_info_normal)).setVisibility(View.GONE);
                ((ConstraintLayout) show_info.findViewById(R.id.ask_doc_info_spa)).setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.network_bob));
                ((ConstraintLayout) show_info.findViewById(R.id.ask_doc_info_spa)).setVisibility(View.VISIBLE);
                show_info.show();
            }
        });*/
        return view;
    }

    private void changeFreeColor() {
        try {
            int start = 0;
            SpannableString string = new SpannableString(textView.getText().toString().trim());
            if (SetLocal.getLong(getActivity()).equals("ar")) {
                start = string.toString().lastIndexOf("م");
            } else if (SetLocal.getLong(getActivity()).equals("en")) {
                start = string.toString().toLowerCase().indexOf("f");
                string.setSpan(new StyleSpan(Typeface.BOLD), start, 4, 0);

            } else if (SetLocal.getLong(getActivity()).equals("fr")) {
                start = string.toString().toLowerCase().lastIndexOf("g");
                string.setSpan(new StyleSpan(Typeface.BOLD), start, 8, 0);
            }
            string.setSpan(new ForegroundColorSpan(Color.parseColor("#128587")), start, string.length(), 0);
            textView.setText(string);
        } catch (Exception e) {
            Log.d(TAG, "changeFreeColor: " + e.getMessage());
            return;
        }
    }

    private void load() {
        statics.setMY_PLACE("اسال طبيب2");
        startActivity(new Intent(getActivity(), HomeActivity.class));
    }

    public void Temp() {
        MyDialogsHandluer.YouAreNotInFamiily(getActivity());
    }

    private void addItems() {
        item = new ArrayList<>();
        item.add(new BookingSpecializationModel(getString(R.string.sp_1), R.drawable.s_1));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_2), R.drawable.s_2));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_3), R.drawable.s_3));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_19), R.drawable.s_19));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_4), R.drawable.s_4));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_5), R.drawable.s_5));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_6), R.drawable.s_6));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_7), R.drawable.s_7));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_8), R.drawable.s_8));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_9), R.drawable.s_9));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_10), R.drawable.s_10));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_11), R.drawable.s_11));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_12), R.drawable.s_12));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_13), R.drawable.s_13));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_14), R.drawable.s_14));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_15), R.drawable.s_15));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_16), R.drawable.s_16));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_17), R.drawable.s_17));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_18), R.drawable.s_18));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_20), R.drawable.s_20));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_21), R.drawable.s_21));
        // item.add(new BookingSpItem(getResources().getString(R.string.sp_22), R.drawable.s_22));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_23), R.drawable.s_23));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_24), R.drawable.s_24));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_26), R.drawable.s_26));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_27), R.drawable.s_27));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_28), R.drawable.s_28));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_29), R.drawable.s_29));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_30), R.drawable.s_30));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_31), R.drawable.s_31));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_32), R.drawable.s_32));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_33), R.drawable.s_33));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_34), R.drawable.s_34));
        // item.add(new BookingSpItem(getResources().getString(R.string.sp_35), R.drawable.s_35));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_36), R.drawable.s_36));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_37), R.drawable.s_37));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_38), R.drawable.s_38));
        // item.add(new BookingSpItem(getResources().getString(R.string.sp_39), R.drawable.s_39));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_41), R.drawable.s_41));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_40), R.drawable.s_40));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_42), R.drawable.s_42));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_43), R.drawable.s_43));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_44), R.drawable.s_44));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_45), R.drawable.s_45));
        /////////////////////////////////////////////////////
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_46), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_47), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_48), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_49), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_50), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_51), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_52), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_53), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_54), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_55), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_56), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_57), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_56), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_57), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_58), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_59), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_60), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_61), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_62), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_63), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_64), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_65), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_66), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_67), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_68), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_69), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_70), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_71), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_72), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_73), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_74), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_75), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_76), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_77), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_78), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_79), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_80), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_81), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_82), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_83), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_84), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_85), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_86), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_87), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_88), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_89), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_90), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_91), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_92), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_93), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_94), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_95), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_96), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_97), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_98), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_99), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_100), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_101), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_102), R.drawable.s_45));
        sph_adapter = new BookingAdvanseItemAdabter(getActivity(), item, this);

    }

    @Override
    public void onCLick(int p) {
        bottomSheetDialog2.dismiss();
        spha_selected = item.get(p).getCity_name();
        spa.setText(item.get(p).getCity_name());

    }
}