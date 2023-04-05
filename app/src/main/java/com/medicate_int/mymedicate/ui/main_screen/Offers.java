package com.medicate_int.mymedicate.ui.main_screen;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.medicate_int.mymedicate.module.CheckConnection;
import com.medicate_int.mymedicate.module.Moudle;
import com.medicate_int.mymedicate.ui.MapsActivity;
import com.medicate_int.mymedicate.adapter.OffersAdapter;
import com.medicate_int.mymedicate.database.OffersDatabase;
import com.medicate_int.mymedicate.models.OffersModel;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.SetLocal;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.HomeActivity;
import com.medicate_int.mymedicate.ui.Login;
import com.medicate_int.mymedicate.ui.main_screen.medical_network.MedicalNetwork;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;


public class Offers extends Fragment implements OffersAdapter.onCLickLis {
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    Query query;
    View view;
    RecyclerView recyclerView;
    String lang;
    Dialog dailog;
    Snackbar snack;
    List<OffersModel> list;
    String TAG = "FRAGMENT-OFFERS";
    OffersAdapter adapter;
    CacheHelper statics;
    boolean internet;
    Context context;
    int pos, count;
    OffersDatabase databaseOffers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_offers, container, false);
        context = getActivity();
        view.findViewById(R.id.imageView60).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        lang = SetLocal.getLong(view.getContext());
        recyclerView = view.findViewById(R.id.offers_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != snack) {
            if (snack.isShown())
                snack.dismiss();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!internet)
            viewOfLineData();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseOffers = new OffersDatabase(getActivity().getApplicationContext());
        list = new ArrayList<>();
        dailog = new Dialog(getActivity(), R.style.PauseDialog);
        dailog.setContentView(R.layout.loading_layout);
        dailog.setCancelable(true);
        dailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dailog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                getActivity().onBackPressed();
            }
        });
        if (internet) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference().getRoot().child("MedicateApp").child("Offers");

            getData();
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        internet = false;
        statics = new CacheHelper(context);
        if (CheckConnection.isNetworkConnected(requireActivity())) {
            internet = true;
        }
    }

    private void viewOfLineData() {
        if (null != databaseOffers.getData())
            if (databaseOffers.getData().getCount() > 0) {
                snack = Snackbar.make(view, getString(R.string.you_are_ofline), Snackbar.LENGTH_INDEFINITE);
                snack.setAction(R.string.try_agin, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        statics.setMY_PLACE("العروض");
                        startActivity(new Intent(getActivity(), HomeActivity.class));
                    }
                }).show();
                Finally();
            } else {
                NoInternet();

            }
    }

    public void NoInternet() {
        Dialog dialog = new Dialog(getActivity(), R.style.PauseDialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.no_internet_con);
        dialog.findViewById(R.id.di_try).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                statics.setMY_PLACE("العروض");
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        dialog.findViewById(R.id.di_but_cancal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                getActivity().onBackPressed();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void getData() {
        dailog.show();
        list.clear();
        try {

            databaseReference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        count = (int) dataSnapshot.getChildrenCount();
                        pos = count;
                        FillData();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } catch (Exception e) {
            Log.d(TAG, "ERROR83" + e.getMessage());
        }
    }

    private void FillData() {
        if (pos > 0) {
            query = databaseReference.child(String.valueOf(pos));
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        pos -= 1;
                        list.add(new OffersModel(
                                dataSnapshot.child("title-ar").getValue(String.class).toString().trim(),
                                dataSnapshot.child("title-en").getValue(String.class).toString().trim(),
                                dataSnapshot.child("title-fr").getValue(String.class).toString().trim(),
                                dataSnapshot.child("cont-ar").getValue(String.class).toString().trim(),
                                dataSnapshot.child("cont-en").getValue(String.class).toString().trim(),
                                dataSnapshot.child("cont-fr").getValue(String.class).toString().trim(),
                                dataSnapshot.child("date").getValue(String.class).toString().trim(),
                                dataSnapshot.child("img").getValue(String.class).toString().trim(),
                                dataSnapshot.child("lat").getValue(String.class).toString().trim(),
                                dataSnapshot.child("log").getValue(String.class).toString().trim())
                        );
                        FillData();
                    } else {
                        dailog.dismiss();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    dailog.dismiss();
                    Login.Message(getActivity().getString(R.string.cheack_int_con), getActivity());
                }
            });
        } else {
            if (list.size() >= 1) {
                dailog.dismiss();
                addData();
            } else {
                dailog.dismiss();
                if (null != context)
                    Login.Message(getString(R.string.no_offers), getActivity());
            }
        }
    }

    @Override
    public void onCLick(int p) {
        Dialog dialog = new Dialog(getActivity(), R.style.PauseDialog);
        dialog.setContentView(R.layout.fragment_inside_offers);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        GifImageView imageView = dialog.findViewById(R.id.ins_img);
        TextView title = dialog.findViewById(R.id.ins_title);
        TextView cont = dialog.findViewById(R.id.ins_cont);
        title.setText(list.get(p).getOFFERS_1_TITLE_AR());
        cont.setText(list.get(p).getOFFERS_4_COUNT_AR());
        Picasso.with(context).load(list.get(p).getOFFERS_8_IMG()).fit().placeholder(ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.new_loading, getActivity().getTheme())).into(imageView);
        switch (lang) {
            case "en":
                title.setText(list.get(p).getOFFERS_2_TITLE_EN());
                cont.setText(list.get(p).getOFFERS_5_COUNT_EN());
                break;
            case "fr":
                title.setText(list.get(p).getOFFERS_3_TITLE_FR());
                cont.setText(list.get(p).getOFFERS_6_COUNT_FR());
                break;
            default: {
                title.setText(list.get(p).getOFFERS_1_TITLE_AR());
                cont.setText(list.get(p).getOFFERS_4_COUNT_AR());
            }
        }
        LinearLayout linearLayout = dialog.findViewById(R.id.of_loc);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Moudle.checkFromLocationData(Float.parseFloat(list.get(p).getOFFERS_9_LAT()), Float.parseFloat(list.get(p).getOFFERS_10_LOG()))) {
                    Intent map = new Intent(getActivity(), MapsActivity.class);
                    map.putExtra("title", title.getText().toString().trim());
                    map.putExtra("lat", Float.parseFloat(list.get(p).getOFFERS_9_LAT()));
                    map.putExtra("log", Float.parseFloat(list.get(p).getOFFERS_10_LOG()));
                    Log.d(TAG, "onClick: LAT > " + list.get(p).OFFERS_9_LAT);
                    Log.d(TAG, "onClick: log > " + list.get(p).OFFERS_10_LOG);
                    startActivity(map);
                } else Login.Message(getString(R.string.no_loac), context);
            }
        });
        dialog.show();
    }

    public void addData() {
        try {
            databaseOffers.delOldData();
            for (int i = 0; i < list.size(); i++) {
                databaseOffers.insertData(
                        list.get(i).OFFERS_1_TITLE_AR,
                        list.get(i).OFFERS_2_TITLE_EN,
                        list.get(i).OFFERS_3_TITLE_FR,
                        list.get(i).OFFERS_4_COUNT_AR,
                        list.get(i).OFFERS_5_COUNT_EN,
                        list.get(i).OFFERS_6_COUNT_FR,
                        list.get(i).OFFERS_7_DATE,
                        list.get(i).OFFERS_8_IMG,
                        list.get(i).OFFERS_9_LAT,
                        list.get(i).OFFERS_10_LOG);
            }
            list.clear();
            Finally();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, getString(R.string.uknown_error), Toast.LENGTH_SHORT).show();
            return;
        }
    }


    private void Finally() {
        Cursor cursor = databaseOffers.getData();
        if (null != cursor && cursor.getCount() > 0)
            while (cursor.moveToNext()) {
                list.add(new OffersModel(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10)
                ));
            }
        if (null != getActivity()) {
            adapter = new OffersAdapter(getActivity(), list, Offers.this);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}