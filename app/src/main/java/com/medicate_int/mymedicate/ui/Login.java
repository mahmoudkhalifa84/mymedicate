
package com.medicate_int.mymedicate.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.medicate_int.mymedicate.BackgroundWorker;
import com.medicate_int.mymedicate.module.CheckConnection;
import com.medicate_int.mymedicate.module.Statics;
import com.medicate_int.mymedicate.services.NotificationsService;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.database.NotificationsDatabase;
import com.medicate_int.mymedicate.HomeActivity;

public class Login extends Fragment {

    TextInputEditText card_num, PasswordEt;
    TextView skip_btn, card_type_pic;
    CacheHelper statics;
    String card_type;
    Context context;
    FrameLayout buLogin;
    Toolbar toolbar;
    Dialog dailog, card_pic_dig;
    ScrollView scrollView;
    View v;
    BackgroundWorker backgroundWorker;
    ArrayAdapter<String> dataAdapter;
    private static final String TAG = "FragmentLoginTAG";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_fragment_login_layout, container, false);
        toolbar = v.findViewById(R.id.toolbar);
        context = getActivity();

        dailog = new Dialog(getActivity());
        dailog.setContentView(R.layout.loading_layout);
        TextView textView = v.findViewById(R.id.textView61);
        SpannableString content = new SpannableString(getString(R.string.call_us));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);
        textView.setOnClickListener(new View.OnClickListener() {
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
        dailog.setCancelable(true);
        dailog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                backgroundWorker.cancel(true);
                dailog.dismiss();
            }
        });
        dailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        card_num = v.findViewById(R.id.etUserName);

        scrollView = v.findViewById(R.id.log_scrol);
        scrollView.setVisibility(View.VISIBLE);
        PasswordEt = v.findViewById(R.id.etPassword);
        card_type = "1";
        card_type_pic = v.findViewById(R.id.card_type_);
        buLogin = v.findViewById(R.id.btnLogin);
        buLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card_num.getText().toString().trim().equals(statics.getOldUserCardNumber()) &&
                        PasswordEt.getText().toString().trim().equals(statics.getOldUserPass())) {
                    statics.setOldtoNew();


                    new NotificationsDatabase(context).delOldData();
                    if (HomeActivity.isMyServiceRunning(NotificationsService.class, context))
                        context.stopService(new Intent(context, NotificationsService.class));
                    statics.setMY_PLACE("المنزل");
                    context.startActivity(new Intent(context, HomeActivity.class));
                } else {
                    if (CheckConnection.isNetworkConnected(getActivity())) { //returns true if internet available

                            String cardnumber = card_num.getText().toString().trim();
                            String password = PasswordEt.getText().toString().trim();
                            if (notEmpty(cardnumber,password)) {
                                backgroundWorker =
                                        new BackgroundWorker(v.getContext(), v, dailog);
                                backgroundWorker.execute(cardnumber, password, card_type);
                                //  goToUserInfo();
                            }

                    } else {
                        Message(getResources().getString(R.string.cheack_int_con), getActivity());
                    }
                }
            }
        });
        skip_btn = v.findViewById(R.id.skip_but);
        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statics.setID("null");
                statics.setMY_PLACE("المنزل");
                startActivity(new Intent(context, HomeActivity.class));

            }
        });
        return v;

    }

    private boolean notEmpty(String username, String password) {
        if (username.trim().isEmpty()) {
            Message(getResources().getString(R.string.cardname_error), getActivity());
            return false;
        }
        if (password.trim().isEmpty()) {
            Message(getResources().getString(R.string.pass_is_empty), getActivity());
            return false;
        }
        return true;
    }


    private View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (dataAdapter.getCount() > 2) {
                    dataAdapter.remove(dataAdapter.getItem(0));
                    Log.d(TAG, "its work: ");
                }

            }
            return false;
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String login_qr = statics.restorePrefData("login_qr");
        Log.d(TAG, "data: " + login_qr);
        if ((!login_qr.equals("null")) && (!login_qr.isEmpty()) && (login_qr.length() > 4)) {
            card_num.setText(login_qr);
            Log.d(TAG, "data: inside " + login_qr);
            statics.Remove("login_qr");
        }
        view.requestFocus();
    }

    public static void Message(String s, Context context) {
        Dialog dialogsa = new Dialog(context, R.style.PauseDialog);
        dialogsa.setContentView(R.layout.per_requst);
        TextView textView = dialogsa.findViewById(R.id.textView40);
        TextView but = dialogsa.findViewById(R.id.textView42);
        textView.setText(s);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogsa.dismiss();
            }
        });
        dialogsa.setCancelable(false);
        dialogsa.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogsa.show();
    }

    private void showDi() {
        Dialog dialog2 = new Dialog(getActivity(), R.style.PauseDialog);
        dialog2.setCancelable(true);
        dialog2.setContentView(R.layout.per_requst);
        dialog2.findViewById(R.id.textView42).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION}, 3);
            }
        });
        dialog2.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog2.dismiss();
            }
        });
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.show();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        statics = new CacheHelper(context);
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            if (resultCode == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(new Intent(getActivity(), QR_Login_Class.class), 2);
            } else {
                showDi();
            }
        }*/
}
