package com.medicate_int.mymedicate.ui.main_screen.support_services;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.tubesock.WebSocketException;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.SetLocal;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.adapter.BookingCityItemAdabter;
import com.medicate_int.mymedicate.models.BookingSpecializationModel;
import com.medicate_int.mymedicate.ui.Login;

import java.util.ArrayList;
import java.util.List;

public class Hotels extends Fragment implements BookingCityItemAdabter.onCLickLis2 {
    View view;
    CacheHelper statics;
    String[] citys_tunis_ar = {"سوسة", "الحمامات", "المونستير", "جربة", "طبرقة", "المهدية"};
    String[] citys_tunis_fr = {"Sousse", "Hammamet", "Monastir", "Djerba", "Tabarka", "Mahdia"};
    List<BookingSpecializationModel> bookingSpecializationModels;
    BottomSheetDialog bottomSheetDialog, bottomSheetDialog2;
    BookingCityItemAdabter cityItemAdabter;
    RecyclerView recyclerView;
    ConstraintLayout main;
    TextView city;
    int place;
    Dialog dailog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.os_hotels, container, false);
        main = view.findViewById(R.id.os_lay_main);

        statics = new CacheHelper(getActivity());
        recyclerView = new RecyclerView(getActivity());
        bookingSpecializationModels = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TextView country = view.findViewById(R.id.dd_country);
        city = view.findViewById(R.id.dd_city);
        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingSpecializationModels.clear();
                bottomSheetDialog2 = new BottomSheetDialog(getActivity());
                for (int i = 0; i < 6; i++) {
                    if (SetLocal.getLong(getActivity()).equals("ar"))
                        bookingSpecializationModels.add(new BookingSpecializationModel(citys_tunis_ar[i], 0));
                    else
                        bookingSpecializationModels.add(new BookingSpecializationModel(citys_tunis_fr[i], 0));
                }
                cityItemAdabter =
                        new BookingCityItemAdabter(getActivity(), bookingSpecializationModels, Hotels.this::onCLick2);
                recyclerView.setAdapter(cityItemAdabter);
                if(recyclerView.getParent() != null) {
                    ((ViewGroup)recyclerView.getParent()).removeView(recyclerView); // <- fix
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
        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(getActivity());
                bottomSheetDialog.setContentView(R.layout.os_country_choaser_layout);
                bottomSheetDialog.setCancelable(true);
                LinearLayout linearLayout = bottomSheetDialog.findViewById(R.id.dd_country_tuis);
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        country.setText(getString(R.string.county_tunis));
                        bottomSheetDialog.dismiss();
                        city.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.network_bob));
                        city.setVisibility(View.VISIBLE);
                    }
                });
                bottomSheetDialog.show();
            }
        });
        view.findViewById(R.id.dd_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (country.getText().equals(getString(R.string.chose_contry)))
                    Login.Message(getString(R.string.plz_shose_country_first), getActivity());
                else if (city.getText().equals(getString(R.string.city_prompt)))
                    Login.Message(getString(R.string.plz_chose_city_first), getActivity());
                else
                    load();
            }
        });
        dailog = new Dialog(getActivity(), R.style.PauseDialog);
        dailog.setContentView(R.layout.loading_layout);
        dailog.setCancelable(true);
        dailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return view;
    }

    private void load() {
        try {
            Intent intent =
                    new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra("place", place);
            startActivity(intent);
        } catch (WebSocketException e) {
            Log.d("WWWWWWWWWW", e.getMessage());
        }
    }


    @Override
    public void onCLick2(int p) {
        city.setText(cityItemAdabter.getIm().get(p).getCity_name());
        place = p;
        bottomSheetDialog2.dismiss();
    }
}