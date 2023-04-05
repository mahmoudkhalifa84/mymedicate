package com.medicate_int.mymedicate.ui.main_screen.booking.booking_doctor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.medicate_int.mymedicate.ApiClient;
import com.medicate_int.mymedicate.module.LoadingDialog;
import com.medicate_int.mymedicate.module.Moudle;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.adapter.BookingDoctorAdapter;
import com.medicate_int.mymedicate.models.BookingDoctorDataModel;
import com.medicate_int.mymedicate.ui.Login;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchByName extends Fragment implements BookingDoctorAdapter.MyHandler {
    EditText ser;
    TextView orderby;
    LinearLayout pd, p_a, r_d, r_a;
    ImageView img_pd, img_p_a, img_r_d, img_r_a;
    int order_case = 1; // 1 > BY PRICE DESC - 2 > BY PRICE ASC  - 3 BY RATE DESC - 4 > BY RATE ASC
    RecyclerView recyclerView;
    View view;
    Context context;
    LoadingDialog loadingDialog;
    BookingDoctorAdapter adapter;
    Call<List<BookingDoctorDataModel>> listCall;
    private static final String TAG = "BookingDocByName";
    Dialog viewDoctorDet;
    CacheHelper statics;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.booking_ser_doc_name, container, false);
        ser = view.findViewById(R.id.searchView_doc);
        context = getActivity();
        recyclerView = view.findViewById(R.id.rec_view_ser_doc_name);
        loadingDialog = new LoadingDialog(context);
        listCall = new ApiClient(ApiClient.BASE_URL).getDoctorsData();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new BookingDoctorAdapter(context, this);
        recyclerView.setAdapter(adapter);
        statics = new CacheHelper(context);
        viewDoctorDet = new Dialog(context);
        viewDoctorDet.setContentView(R.layout.booking_dotor_view_row_ui);
        viewDoctorDet.setCanceledOnTouchOutside(false);
        viewDoctorDet.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        viewDoctorDet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ((ImageView) viewDoctorDet.findViewById(R.id.imageView81)).setOnClickListener((a) -> viewDoctorDet.dismiss());

        view.findViewById(R.id.imageView49).setOnClickListener(new View.OnClickListener() {//^_^
            @Override//^_^
            public void onClick(View view) {//^_^
                getActivity().onBackPressed();//^_^
            }//^_^
        });//^_^
        view.findViewById(R.id.textView94).setOnClickListener(new View.OnClickListener() {//^_^
            @Override//^_^
            public void onClick(View view) {//^_^
                getActivity().onBackPressed();//^_^
            }//^_^
        });//^_^
        ser.addTextChangedListener(new TextWatcher() {//^_^ Bada
            @Override//^_^
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {//^_^

            }//^_^

            @Override//^_^
            public void onTextChanged(CharSequence s, int start, int before, int count) {//^_^
               /* if (s.length() > 0)//^_^
                    view.findViewById(R.id.ll_no_doc).setVisibility(View.VISIBLE);//^_^
                else//^_^
                    view.findViewById(R.id.ll_no_doc).setVisibility(View.GONE);//^_^*/
                adapter.getFilter().filter(s);

            }//^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^

            @Override//^_^
            public void afterTextChanged(Editable s) {//^_^
            }//^_^
        });//^_^
        orderby = view.findViewById(R.id.order_click);//^_^
        orderby.setOnClickListener(new View.OnClickListener() {//^_^
            @Override//^_^
            public void onClick(View v) {
                Dialog dialog = new BottomSheetDialog(getActivity());
                dialog.setContentView(R.layout.doc_sort_by_lay);
                dialog.setCancelable(true);
                pd = dialog.findViewById(R.id.ll_m_p);
                p_a = dialog.findViewById(R.id.ll_l_p);
                r_a = dialog.findViewById(R.id.ll_l_rate);
                r_d = dialog.findViewById(R.id.ll_bast_rate);
                img_pd = dialog.findViewById(R.id.od_img_1);
                img_p_a = dialog.findViewById(R.id.od_img3);
                img_r_a = dialog.findViewById(R.id.od_img4);
                img_r_d = dialog.findViewById(R.id.od_img2);
                pd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        order_case = 1;
                        dialog.dismiss();
                    }
                });
                p_a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        order_case = 2;
                        dialog.dismiss();
                    }
                });
                r_a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        order_case = 4;
                        dialog.dismiss();
                    }
                });
                r_d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        order_case = 3;
                        dialog.dismiss();
                    }
                });
                change();
                dialog.show();
            }
        });
        loadData();

        return view;
    }

    private void loadData() {
        loadingDialog.show();
        listCall.clone().enqueue(new Callback<List<BookingDoctorDataModel>>() {
            @Override
            public void onResponse(Call<List<BookingDoctorDataModel>> call, Response<List<BookingDoctorDataModel>> response) {
                loadingDialog.dismiss();
                Log.d(TAG, "onResponse: > " + response.message());
                Log.d(TAG, "onResponse: > " + new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().size() > 0) {
                        adapter = new BookingDoctorAdapter(context, response.body(), SearchByName.this);
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                    } else {
                        noData();
                    }
                } else Login.Message(getString(R.string.cheack_int_con), context);
            }

            @Override
            public void onFailure(Call<List<BookingDoctorDataModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                if (null != getActivity()) {
                    loadingDialog.dismiss();
                    Login.Message(getString(R.string.cheack_int_con), context);
                    noData();
                }

            }
        });
    }

    private void noData() {
        Snackbar.make(view, getString(R.string.noDocData), Snackbar.LENGTH_LONG).show();
    }

    public void change() {
        switch (order_case) {
            case 1:
                img_pd.setVisibility(View.VISIBLE);
                img_p_a.setVisibility(View.INVISIBLE);
                img_r_a.setVisibility(View.INVISIBLE);
                img_r_d.setVisibility(View.INVISIBLE);
                break;
            case 2:
                img_pd.setVisibility(View.INVISIBLE);
                img_p_a.setVisibility(View.VISIBLE);
                img_r_a.setVisibility(View.INVISIBLE);
                img_r_d.setVisibility(View.INVISIBLE);
                break;
            case 3:
                img_pd.setVisibility(View.INVISIBLE);
                img_p_a.setVisibility(View.INVISIBLE);
                img_r_a.setVisibility(View.INVISIBLE);
                img_r_d.setVisibility(View.VISIBLE);
                break;
            case 4:
                img_pd.setVisibility(View.INVISIBLE);
                img_p_a.setVisibility(View.INVISIBLE);
                img_r_a.setVisibility(View.VISIBLE);
                img_r_d.setVisibility(View.INVISIBLE);
                break;
        }

    }

    @Override
    public void onCLickView(int p) {
        BookingDoctorDataModel selected = adapter.getIm().get(p);
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

    public void go(int p) {
        BookingDoctorDataModel selected = adapter.getIm().get(p);
        Intent intent = new Intent(context, ViewDoctorAppointments.class);
        intent.putExtra("selectedDoc", selected.getDoctorID())
                .putExtra("docImage", ApiClient.WEBSITE_URL + "/ci_sereen/upload/" + selected.getImage())
                .putExtra("docSp", selected.getSpecialization()).putExtra("docName", selected.getDoctorName())
                .putExtra("sp", selected.getSpecialization())
                .putExtra("dg", selected.getDegree(context))
                .putExtra("phone", selected.getPhoneNumber());
        startActivity(intent);
    }
}