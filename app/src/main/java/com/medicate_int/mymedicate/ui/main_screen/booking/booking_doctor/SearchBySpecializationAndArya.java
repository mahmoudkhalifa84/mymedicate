package com.medicate_int.mymedicate.ui.main_screen.booking.booking_doctor;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.medicate_int.mymedicate.ApiClient;
import com.medicate_int.mymedicate.adapter.BookingAdvanseItemAdabter;
import com.medicate_int.mymedicate.models.BookingSpecializationModel;
import com.medicate_int.mymedicate.module.LoadingDialog;
import com.medicate_int.mymedicate.module.Moudle;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.adapter.BookingDoctorAdapter;
import com.medicate_int.mymedicate.models.BookingDoctorDataModel;
import com.medicate_int.mymedicate.ui.Login;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A fragment representing a list of Items.
 */
public class SearchBySpecializationAndArya extends Fragment implements BookingAdvanseItemAdabter.onCLickLis {
    private static final String TAG = "BookingDecAdvanceTAG";
    CacheHelper statics;


    View view;
    Call<List<BookingDoctorDataModel>> listCall;
    Context context;

    RecyclerView listView, dotorData;
    ArrayList<BookingSpecializationModel> item;
    LinearLayout sec;
    int place;
    BookingDoctorAdapter doctorAdapter;
    LoadingDialog loadingDialog;

    Dialog viewDoctorDet;
    BookingAdvanseItemAdabter adabter;
    EditText searchView;
    List<BookingDoctorDataModel> finalDotorList = new ArrayList<>();
    private String selectedSp;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.booking_ser_sh_loca, container, false);
        listView = view.findViewById(R.id.booking_list_city);
        dotorData = view.findViewById(R.id.booking_list_data);
        context = getActivity();

        statics = new CacheHelper(getActivity());
        place = 1;
        loadingDialog = new LoadingDialog(context);

        searchView = view.findViewById(R.id.searchView22);
        sec = view.findViewById(R.id.sec_lay);
        sec.setVisibility(View.GONE);
        listCall = new ApiClient(ApiClient.BASE_URL).getDoctorsData();

        addItems();
        view.findViewById(R.id.imageView50).setOnClickListener((a) -> getActivity().onBackPressed());
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dotorData.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.setAdapter(adabter);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (place == 1) {
                    adabter.getFilter().filter(s);
                    if (adabter.getIm().size() <= 0) {
                        sec.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.network_bob));
                        sec.setVisibility(View.VISIBLE);
                    } else {
                        sec.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.popout));
                        sec.setVisibility(View.GONE);
                    }
                } else {
                    doctorAdapter.getFilter().filter(s);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewDoctorDet = new Dialog(context);
        viewDoctorDet.setContentView(R.layout.booking_dotor_view_row_ui);
        viewDoctorDet.setCanceledOnTouchOutside(false);
        viewDoctorDet.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        viewDoctorDet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ((ImageView) viewDoctorDet.findViewById(R.id.imageView81)).setOnClickListener((a) -> viewDoctorDet.dismiss());

        return view;
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
        adabter = new BookingAdvanseItemAdabter(getActivity(), item, this::onCLick);

    }

    private void loadData() {
        loadingDialog.show();
        sec.setVisibility(View.GONE);
        listCall.clone().enqueue(new Callback<List<BookingDoctorDataModel>>() {
            @Override
            public void onResponse(Call<List<BookingDoctorDataModel>> call, Response<List<BookingDoctorDataModel>> response) {
                loadingDialog.dismiss();
                Log.d(TAG, "onResponse: > " + response.message());
                Log.d(TAG, "onResponse: > " + new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().size() > 0) {
                        if (filterMySp(response.body()).size() > 0) {
                            doctorAdapter = new BookingDoctorAdapter(context, finalDotorList, new BookingDoctorAdapter.MyHandler() {
                                @Override
                                public void onCLickView(int p) {
                                    BookingDoctorDataModel selected = doctorAdapter.getIm().get(p);
                                    Picasso.with(context).load(ApiClient.WEBSITE_URL + "/ci_sereen/upload/" + selected.getImage()).placeholder(R.drawable.bok_user).into((ImageView) viewDoctorDet.findViewById(R.id.bok_view_img));
                                    ((TextView) viewDoctorDet.findViewById(R.id.bok_view_main_sp)).setText(selected.getSpecialization());
                                    ((TextView) viewDoctorDet.findViewById(R.id.bok_view_degree)).setText(selected.getDegree(context));
                                    ((TextView) viewDoctorDet.findViewById(R.id.bok_view_wait_time)).setText(selected.getWaitTime());
                                    ((TextView) viewDoctorDet.findViewById(R.id.bok_view_gender)).setText(getString(selected.getGender()));
                                    ((TextView) viewDoctorDet.findViewById(R.id.bok_view_about)).setText(selected.getAbout());
                                    ((TextView) viewDoctorDet.findViewById(R.id.bok_view_name)).setText(selected.getDoctorName());
                                    viewDoctorDet.findViewById(R.id.linearLayout25).setOnClickListener((a) -> {
                                        viewDoctorDet.dismiss();
                                        onCLickBook(p);
                                    });
                                    viewDoctorDet.show();
                                }

                                @Override
                                public void onCLickBook(int p) {
                                    if (statics.getUserPhoneNumber().equals("null"))
                                        Moudle.insertEmailPhone(context, new Moudle.PhoneEmailInsert() {
                                            @Override
                                            public void done(String phone, String email) {
                                                statics.setUserPhoneNumber(phone);
                                                if (!email.isEmpty() && email.trim().length() > 5)
                                                    statics.setUserEmail(email);
                                                go(p);

                                            }
                                        });
                                    else go(p);
                                }
                            });
                            doctorAdapter.notifyDataSetChanged();
                            dotorData.setAdapter(doctorAdapter);
                            dotorData.setVisibility(View.VISIBLE);
                        } else {
                            sec.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.network_bob));
                            sec.setVisibility(View.VISIBLE);
                        }
                    } else {
                        sec.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.network_bob));
                        sec.setVisibility(View.VISIBLE);
                    }
                } else Login.Message(getString(R.string.cheack_int_con), context);
            }

            @Override
            public void onFailure(Call<List<BookingDoctorDataModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                if (null != getActivity()) {
                    loadingDialog.dismiss();
                    Login.Message(getString(R.string.cheack_int_con), context);
                    sec.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.network_bob));
                    sec.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private List<BookingDoctorDataModel> filterMySp(List<BookingDoctorDataModel> body) {
        for (BookingDoctorDataModel model :
                body) {
            if (model.getSpecialization().toLowerCase().equals(selectedSp))
                finalDotorList.add(model);
        }
        return finalDotorList;
    }

    @Override
    public void onCLick(int p) {
        listView.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.popout));
        listView.setVisibility(View.GONE);
        searchView.setHint(R.string.doc_name);
        searchView.setText("");
        place = 2;
        selectedSp = adabter.getIm().get(p).getCity_name();
        //    searchView.requestFocus();
        loadData();
        //   Toast.makeText(getActivity(), adabter.getIm().get(p).getCity_name(), Toast.LENGTH_LONG).show();
    }


    public void go(int p) {
        BookingDoctorDataModel selected = doctorAdapter.getIm().get(p);
        Intent intent = new Intent(context, ViewDoctorAppointments.class);
        intent.putExtra("selectedDoc", selected.getDoctorID())
                .putExtra("docImage", ApiClient.WEBSITE_URL + "/ci_sereen/upload/" + selected.getImage())
                .putExtra("docSp", selected.getSpecialization()).putExtra("docName", selected.getDoctorName())
                 .putExtra("dg", selected.getDegree(context))
                .putExtra("phone", selected.getPhoneNumber());
        startActivity(intent);
    }
}