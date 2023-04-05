package com.medicate_int.mymedicate.ui.main_screen;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.medicate_int.mymedicate.module.CheckConnection;
import com.medicate_int.mymedicate.module.JavaMailAPI;
import com.medicate_int.mymedicate.database.NotificationsDatabase;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.database.OtherNotificationsDatabase;
import com.medicate_int.mymedicate.HomeActivity;
import com.medicate_int.mymedicate.ui.main_screen.settings.Settings;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;


public class MainMenu extends Fragment {
    CacheHelper statics;
    View view;
    Dialog dialog3;
    Context context;
    FragmentManager fragmentManager;
    TextView count,chat_count_text;
    OtherNotificationsDatabase other_database;
    LinearLayout count_lay,chat_count_lay;
    boolean ft[] = {true};
    NotificationsDatabase noti_database;
    private String TAG = "MAINMAUNETAG";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_menu2, container, false);
        statics.savePrefsData("qr", "null");
        context = getActivity();
       /*ImageView imgRequest = v.findViewById(R.id.img_request_card);
        LinearLayout llClaims = v.findViewById(R.id.ll_claims);
        LinearLayout llNotify = v.findViewById(R.id.ll_notify);
        LinearLayout lloffer = v.findViewById(R.id.ll_offers);*/
        count = view.findViewById(R.id.count_text);
        chat_count_text = view.findViewById(R.id.chat_comp_txt);
        count_lay = view.findViewById(R.id.noti);
        chat_count_lay = view.findViewById(R.id.chatnoti);
        if (statics.getID().equals("null")) {
            View chattt = view.findViewById(R.id.help_chat);
            chattt.setVisibility(View.INVISIBLE);
        }
        view.findViewById(R.id.ll_notify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                //  login(3);
                statics.setMY_PLACE("الاشعارات");
                if (statics.getID().equals("null"))
                    statics.setMY_PLACE("الاشعارات2");
                startActivity(new Intent(view.getContext(), HomeActivity.class));

            }
        });
        view.findViewById(R.id.help_network).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                statics.setMY_PLACE("الشبكات الطبية");
                //    v.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.fragment_fade_exit));

                startActivity(new Intent(view.getContext(), HomeActivity.class));

            }
        });
        view.findViewById(R.id.main_img_myaccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                login(1);
            }
        });

        view.findViewById(R.id.ll_servics).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                statics.setMY_PLACE("خدماتنا");
                //    v.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.fragment_fade_exit));

                startActivity(new Intent(view.getContext(), HomeActivity.class));
            }
        });
        view.findViewById(R.id.help_ask_dector).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                statics.setMY_PLACE("اسال طبيب");
                //    v.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.fragment_fade_exit));
                startActivity(new Intent(view.getContext(), HomeActivity.class));
            }
        });
        view.findViewById(R.id.ll_claims).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                login(5);

                //    v.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.fragment_fade_exit));

            }
        });
        view.findViewById(R.id.ll_claims).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {

                login(5);

                //    v.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.fragment_fade_exit));


            }
        });
        view.findViewById(R.id.report_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                dialog3 = new Dialog(getActivity(), R.style.PauseDialog);
                dialog3.setContentView(R.layout.rep_prp_main_dailog);
                TextView button = dialog3.findViewById(R.id.feed_back_ok_byt);
                EditText editText = dialog3.findViewById(R.id.report_back_txt);
                EditText phne = dialog3.findViewById(R.id.prp_phone);
                Spinner spinner = dialog3.findViewById(R.id.prp_spinner);
                dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ConstraintLayout main = dialog3.findViewById(R.id.feed_back_main);
                main.setVisibility(View.VISIBLE);
                ConstraintLayout thanki = dialog3.findViewById(R.id.feed_back_thank);
                GifImageView gifImageView = dialog3.findViewById(R.id.gifImageView);
                thanki.setVisibility(View.GONE);

                    button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CheckConnection.isNetworkConnected(getActivity().getApplicationContext())) {
                            if (Settings.chaker(editText.getText().toString().trim(), phne.getText().toString().trim(), spinner.getSelectedItemPosition(), getActivity())) {
                                //  sendPrp(editText.getText().toString().trim(), phne.getText().toString().trim(), spinner.getSelectedItem());
                                new JavaMailAPI(context,phne.getText().toString().trim(),
                                        spinner.getSelectedItem().toString(),
                                        editText.getText().toString().trim()).execute();
                                main.setVisibility(View.GONE);
                                thanki.setVisibility(View.VISIBLE);
                                gifImageView.setEnabled(true);
                                waait();
                            }
                        } else
                            Snackbar.make(v, v.getResources().getString(R.string.cheack_int_con), Snackbar.LENGTH_SHORT).show();
                    }
                });
                dialog3.show();
            }
        });
        view.findViewById(R.id.help_chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChatPeker();

            }
        });
        view.findViewById(R.id.ll_pers_file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                login(2);
            }
        });
        view.findViewById(R.id.help_booking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                statics.setMY_PLACE("حجز مواعيد");
                //   v.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.fragment_fade_exit));
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        view.findViewById(R.id.lay_re_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                statics.setMY_PLACE("مقدمة طلب بطاقة");
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        view.findViewById(R.id.ll_offers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                statics.setMY_PLACE("العروض");
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        chackNotiCount();
        chackChatCount();
        return view;
    }
    private void ChatPeker() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(R.layout.chat_choser_ly);

        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.findViewById(R.id.chat_medic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                statics.setMY_PLACE("محادثة فورية مع ميديكيت");

                startActivity(new Intent(view.getContext(), HomeActivity.class));
            }
        });
        bottomSheetDialog.findViewById(R.id.chat_oco).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                statics.setMY_PLACE("محادثة فورية مع جهة العمل");

                startActivity(new Intent(view.getContext(), HomeActivity.class));
            }
        });
        bottomSheetDialog.show();

    }
    private void waait() {
        final Handler handler = new Handler();
        final Runnable doNextActivity = new Runnable() {
            @Override
            public void run() {
                dialog3.dismiss(); // so the splash activity goes away

            }
        };

        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                handler.post(doNextActivity);
            }
        }.start();

    }



    private void login(int i) {
        if (statics.getID().equals("null")) {
            Dialog dialog = new Dialog(getActivity(), R.style.PauseDialog);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.fragment_dailog);
            TextView textView = dialog.findViewById(R.id.text_dig);
            textView.setText(R.string.login_to_contune);
            dialog.findViewById(R.id.di_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    statics.setMY_PLACE("تسجيل الدخول");
                    Intent i = new Intent(getActivity(), HomeActivity.class);
                    startActivity(i);
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
        } else {
            if (i == 1) {
              statics.setMY_PLACE("حسابي");
                startActivity(new Intent(view.getContext(), HomeActivity.class));
            } else if (i == 2) {
                statics.setMY_PLACE("الملف الشخصي");
                startActivity(new Intent(view.getContext(), HomeActivity.class));
            } else if (i == 5) {
                statics.setMY_PLACE("المطالبات");
                startActivity(new Intent(view.getContext(), HomeActivity.class));
            } else {
                statics.setMY_PLACE("الاشعارات");
                startActivity(new Intent(view.getContext(), HomeActivity.class));
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        statics = new CacheHelper(context);
        noti_database = new NotificationsDatabase(context);
        other_database = new OtherNotificationsDatabase(context);
    }

    public void chackNotiCount() {
        Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (null != getActivity())
                    if (noti_database.getNewCount() > 0 || other_database.getNewCount() > 0) {
                        Log.d(TAG, "Animate");
                        Log.d(TAG, "chackNotiCount: total " + other_database.getData().getCount());
                        Log.d(TAG, "chackNotiCount: new " + other_database.getNewCount());
                        count_lay.setVisibility(View.GONE);
                        count_lay.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.notifications_count));
                        count_lay.setVisibility(View.VISIBLE);
                        count.setText(String.valueOf((noti_database.getNewCount() + other_database.getNewCount())));
                        if (ft[0]) {
                            handler.postDelayed(this, 1000);
                            ft[0] = false;
                        }
                    } else {
                        count_lay.setVisibility(View.GONE);
                        handler.postDelayed(this, 1000);
                    }

            }
        };
        handler.postDelayed(runnable, 100);

    }
    public void chackChatCount() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
              //  Log.d(TAG, "run: CHeck chat");
                if (null != getActivity()){
                    if (Integer.parseInt(statics.getChatCount()) > Integer.parseInt(statics.getReadedChatCount())) {
                        Log.d(TAG, "SHOW NOTI");
                        chat_count_lay.setVisibility(View.GONE);
                        chat_count_lay.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.notifications_count));
                        chat_count_lay.setVisibility(View.VISIBLE);
                     //   chat_count_text.setText("" + (Integer.parseInt(statics.getChatCount()) - Integer.parseInt(statics.getReadedChatCount())));
                    }
                    else{
                        Log.d(TAG, "run: NO NEW CHAT");
                        chat_count_lay.setVisibility(View.GONE);
                    }
                    handler.postDelayed(this::run, 1000);
                }
            }
        };
        handler.postDelayed(runnable, 1000);

    }

    public static void QR_code(String result, Context context) {
        if (!(result.isEmpty()) || !(result.trim().length() == 0)) {
            Dialog dialog = new Dialog(context, R.style.PauseDialog);
            dialog.setContentView(R.layout.qr_responc);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            TextView num = dialog.findViewById(R.id.ar_rs_card_num);
            num.setText(result.trim());
            Spinner spinner = dialog.findViewById(R.id.qr_rs_spinner);
            List<String> list = new ArrayList<>();
            list.add("نوع البطاقة");
            list.add(context.getResources().getString(R.string.mad_insh));
            list.add(context.getResources().getString(R.string.mad_fam));
            list.add(context.getResources().getString(R.string.mad_school));
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                    android.R.layout.simple_spinner_item, list);

            dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
            spinner.setPrompt(context.getString(R.string.card_type));
            spinner.setAdapter(dataAdapter);
            spinner.setSelection(0);
            dialog.findViewById(R.id.feed_back_ok_byt).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}
