package com.medicate_int.mymedicate.ui.main_screen.booking;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.ChipGroup;
import com.medicate_int.mymedicate.module.Statics;
import com.medicate_int.mymedicate.ui.main_screen.ask_doctor.ChatWithMedicateDoctor;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.ui.Login;

public class OrderDrugs extends Fragment {
    Dialog dialog;
    View view;
    Uri uri;
    RecyclerView recyclerView;
    EditText main, sec;
    ChipGroup chipGroup;
    CardView cardView;
    ImageView imageView;
    CacheHelper statics;
    Context context;
    private static final String TAG = "BookingDrugs";
    public static final int CODE_GALARY = 1456;
    public static final int CODE_CAMERA = 4578;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.booking_drugs, container, false);
        imageView = view.findViewById(R.id.imageView5);
        cardView = view.findViewById(R.id.cardView2);
        chipGroup = view.findViewById(R.id.chipGroup2);
        sec = view.findViewById(R.id.editTextTextPersonName2);
        recyclerView = view.findViewById(R.id.drugs_recview);
        context = getActivity();
        dialog = new BottomSheetDialog(context);
        dialog.setCancelable(true);
        statics = new CacheHelper(context);
        main = view.findViewById(R.id.etSearchBar2);
        main.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    imageView.setVisibility(View.GONE);
                    cardView.setVisibility(View.GONE);
                    chipGroup.setVisibility(View.VISIBLE);
                    sec.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    sec.requestFocus();
                    sec.setText(s);
                    sec.setSelection(s.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sec.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    sec.clearFocus();
                    chipGroup.setVisibility(View.GONE);
                    sec.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    cardView.setVisibility(View.VISIBLE);
                    main.setText("");
                    view.requestFocus();
                    hideKeyboardFrom(view.getContext(), view);
                }
            }
        });
        sec.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_NAVIGATE_NEXT)
                    hideKeyboardFrom(getActivity(), view);
                return true;
            }
        });

        view.findViewById(R.id.imageView47).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        view.findViewById(R.id.textView12346).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_bootom();
            }
        });
        view.findViewById(R.id.drug_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statics.getID().equals("null")) {
                    Login.Message(getString(R.string.login_to_contune), context);
                } else
                    choase_by_pic();
            }
        });
        view.findViewById(R.id.drug_rx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statics.getID().equals("null")) {
                    Login.Message(getString(R.string.login_to_contune), context);
                } else
                    choase_by_pic();
            }
        });
        view.findViewById(R.id.drug_ph).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel: " + Statics.PNONE_NUMMBER));
                if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    startActivity(i);
                }
            }
        });

        return view;
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void choase_by_pic() {
        dialog.setContentView(R.layout.camera_or_galary);
        dialog.findViewById(R.id.c_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //     getActivity().finish();
                startActivityForResult(intent, CODE_CAMERA);
            }
        });
        dialog.findViewById(R.id.c_galry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //    getActivity().finish();
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), CODE_GALARY);

            }
        });
        dialog.show();
    }

    public void show_bootom() {
        dialog.setContentView(R.layout.drug_delevry);
        dialog.findViewById(R.id.imageView6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dialog.dismiss();
        Log.d(TAG, "onActivityResult: START");
        if (requestCode == OrderDrugs.CODE_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Uri uri = data.getData();
                    Log.d(TAG, "onActivityResult: تم التقاط الصورة");
                    //    Snackbar.make(view, "تم التقاط الصورة", Snackbar.LENGTH_SHORT).show();
                    statics.setMY_PLACE("المحادثة الفورية");
                    startActivity(new Intent(context, ChatWithMedicateDoctor.class).putExtra("img", uri.toString()));
                }
            } else Log.d(TAG, "onActivityResult: لم يتم التقاط الصورة");
        } else if (requestCode == OrderDrugs.CODE_GALARY) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Uri uri = data.getData();
                    Log.d(TAG, "onActivityResult: تم أختيار الصورة");
                    statics.setMY_PLACE("المحادثة الفورية");
                    startActivity(new Intent(context, ChatWithMedicateDoctor.class).putExtra("img", uri.toString()));
                    // Snackbar.make(view, getActivity().getString(R.string.app_photo_done), Snackbar.LENGTH_SHORT).show();
                }
            } else
                Log.d(TAG, "onActivityResult: لم يتم أختيار الصورة");
        }

        /*if (requestCode == 123)
            if (resultCode == Activity.RESULT_OK) {
                Snackbar.make(view, "تم التقاط الصورة", Snackbar.LENGTH_SHORT).show();
             //   statics.setMY_PLACE("المحادثة الفورية");
            //    startActivity(new Intent(context, MedicateDocChat.class).putExtra("img", uri.toString()));
            } else Snackbar.make(view, "لم يتم ألتقاط الصورة بنجاح", Snackbar.LENGTH_SHORT).show();
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    uri = data.getData();
                //    statics.setMY_PLACE("المحادثة الفورية");
               //     startActivity(new Intent(context, MedicateDocChat.class).putExtra("img", uri.toString()));
                    // Snackbar.make(view, getActivity().getString(R.string.app_photo_done), Snackbar.LENGTH_SHORT).show();
                } else
                    Snackbar.make(view, getActivity().getString(R.string.error_add_phto), Snackbar.LENGTH_SHORT).show();
            } else
                Snackbar.make(view, getActivity().getString(R.string.error_add_phto), Snackbar.LENGTH_SHORT).show();*/
    }
}