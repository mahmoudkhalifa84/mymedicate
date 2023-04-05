package com.medicate_int.mymedicate.ui.main_screen.user_profile.health_record;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.medicate_int.mymedicate.ApiClient;
import com.medicate_int.mymedicate.module.LoadingDialog;
import com.medicate_int.mymedicate.module.Moudle;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.adapter.HelthRecordShowdetailsAdapter;
import com.medicate_int.mymedicate.adapter.HelthRecordsDataAdapter;
import com.medicate_int.mymedicate.module.CheckConnection;
import com.medicate_int.mymedicate.module.Statics;
import com.medicate_int.mymedicate.ui.Login;
import com.medicate_int.mymedicate.models.HealthRecordsModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MedicalChecks extends Fragment implements HelthRecordsDataAdapter.MyHandler {
    View view;
    CacheHelper statics;
    Dialog dialog;
    RecyclerView recyclerView;
    Context context;
    ImageView noDataImg;
    TextView noDataTxt;
    LoadingDialog loadingDialog;
    String type;
    Call<List<HealthRecordsModel>> call;
    Dialog det_dig;
    HelthRecordsDataAdapter adapter;
    private static final String TAG = "MedicalCheaks";
    ViewGroup container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_medical_cheaks, container, false);
        statics = new CacheHelper(getActivity());
        context = getActivity();
        this.container = container;
        noDataImg = view.findViewById(R.id.imageView24);
        noDataTxt = view.findViewById(R.id.textView35);
        type = statics.getHelthRecordType();
        loadingDialog = new LoadingDialog(context);
        recyclerView = view.findViewById(R.id.rec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        call = new ApiClient(ApiClient.MAIN_SYSTEM_URL).getHelthRecordsData(statics.getID());
        view.findViewById(R.id.mc_but_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go();
            }
        });
        view.findViewById(R.id.mc_img_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go();
            }
        });
        view.findViewById(R.id.imageView70).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        if (CheckConnection.isNetworkConnected(context)) {
            loadData();
        } else {
            Moudle.DialogOkLesener(getString(R.string.no_internet_con), context, () -> getActivity().onBackPressed());
            noData();
        }

        return view;
    }

    private void loadData() {
       // Log.d(TAG, "loadData: " + ApiClient.MAIN_SYSTEM_URL + ApiClient.HelthRecords + "/" + statics.getID());
        loadingDialog.show();
        call.clone().enqueue(new Callback<List<HealthRecordsModel>>() {
            @Override
            public void onResponse(Call<List<HealthRecordsModel>> call, Response<List<HealthRecordsModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() > 0) {
                        setDataToList(response.body());
                    } else noData();
                } else {
                    Login.Message(getString(R.string.cheack_int_con), context);
                    noData();
                }
            }

            @Override
            public void onFailure(Call<List<HealthRecordsModel>> call, Throwable t) {
                if (context != null && loadingDialog.isShowing()) loadingDialog.dismiss();
                if (getActivity() != null)
                    Login.Message(getString(R.string.cheack_int_con), context);
                Log.d(TAG, "onFailure: > " + t.getMessage());
            }
        });
    }

    private void setDataToList(List<HealthRecordsModel> body) {
        loadingDialog.dismiss();
        Log.d(TAG, "setDataToList: TYPE > " + type);
        List<HealthRecordsModel> temp = new ArrayList<>();
        boolean mult = false;
        if (type.equals("119"))
            mult = true;

        for (HealthRecordsModel item :
                body) {
            if (item.getParentID().trim().equals(type))
                if (!mult)
                    temp.add(item);
                else if (item.getParentID().trim().equals(type) || (item.getParentID().trim().equals("1016689")))
                    temp.add(item);
        }
        if (temp.size() > 0) {
            Collections.reverse(temp);
            adapter = new HelthRecordsDataAdapter(context, temp, MedicalChecks.this);
            recyclerView.setAdapter(adapter);
        } else noData();
    }

   /* private void setDataToList(List<HelthRecordsModel> body) {
        Log.d(TAG, "setDataToList: TYPE > " + type);
        loadingDiag.dismiss();
        List<HelthRecordsModel> temp = new ArrayList<>();

        for (HelthRecordsModel item :
                body) {
            if (item.getParentID().trim().equals(type))
                temp.add(item);
        }
        if (temp.size() > 0) {
            adapter = new HelthRecordsDataAdapter(context, temp, MedicalCheaks.this);
            recyclerView.setAdapter(adapter);
        } else noData();
    }*/


    private void noData() {
        if (loadingDialog.isShowing())
            loadingDialog.dismiss();
        Log.d(TAG, "noData: No Data");
        noDataImg.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_bob));
        noDataTxt.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_bob));
        noDataImg.setVisibility(View.VISIBLE);
        noDataTxt.setVisibility(View.VISIBLE);
    }

    private void go() {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.add_medical_chack_dailog);
        TextView button = dialog.findViewById(R.id.feed_back_ok_byt);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ConstraintLayout main = dialog.findViewById(R.id.feed_back_main);
        main.setVisibility(View.VISIBLE);
        ConstraintLayout thanki = dialog.findViewById(R.id.feed_back_thank);
        GifImageView gifImageView = dialog.findViewById(R.id.gifImageView);
        thanki.setVisibility(View.GONE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.setVisibility(View.GONE);
                thanki.setVisibility(View.VISIBLE);
                gifImageView.setEnabled(true);
                waait();
            }
        });
        dialog.show();
    }

    private void waait() {
        final Handler handler = new Handler();
        final Runnable doNextActivity = new Runnable() {
            @Override
            public void run() {
                // Intent to jump to the next activity.
                dialog.dismiss(); // so the splash activity goes away
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

    @Override
    public void onCLickView(int p) {
        Log.d(TAG, "onCLickView: ");
        setUpDetDig(adapter.getIm().get(p));

    }
    public void setUpDetDig(HealthRecordsModel healthRecordsModel){
        det_dig = new Dialog(context);
        det_dig.setContentView(R.layout.helth_records_view_details);
        det_dig.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        det_dig.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RecyclerView recyclerView = det_dig.findViewById(R.id.rec_view_det);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        List<String> stringList = new ArrayList<>();
        if (healthRecordsModel.getImageValue0() != null) stringList.add(healthRecordsModel.getImageValue0());
        if (healthRecordsModel.getImageValue1() != null) stringList.add(healthRecordsModel.getImageValue1());
        if (healthRecordsModel.getImageValue2() != null) stringList.add(healthRecordsModel.getImageValue2());
        if (healthRecordsModel.getImageValue3() != null) stringList.add(healthRecordsModel.getImageValue3());
        if (healthRecordsModel.getImageValue4() != null) stringList.add(healthRecordsModel.getImageValue4());

        recyclerView.setAdapter(new HelthRecordShowdetailsAdapter(context, stringList, new HelthRecordShowdetailsAdapter.MyHandler() {
            @Override
            public void onCLickView(int p) {

                Log.d(TAG, "onCLickView: >> " + stringList.get(p));
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(stringList.get(p)), "image/*");
                startActivity(intent);
            }

            @Override
            public void onCLickDownload(int p) {
                Moudle.downloadImage(context,stringList.get(p), Statics.FILES_MY_MEDICAL_RECORDS);
            }

            @Override
            public void onLongClick(int p) {

            }
        }));

        if (stringList.isEmpty()) det_dig.findViewById(R.id.nodata).setVisibility(View.VISIBLE);
        TextView Servicen_id = det_dig.findViewById(R.id.Servicen_id);
        TextView totalv_id = det_dig.findViewById(R.id.totalv_id);
        TextView comv_id = det_dig.findViewById(R.id.comv_id);
        TextView custv_id = det_dig.findViewById(R.id.custv_id);

        Servicen_id.setText("اسم الخدمة: "+healthRecordsModel.getServiceArabicName());
        totalv_id.setText("اجمالي القيمة: "+healthRecordsModel.getServicePrice());
        comv_id.setText("قيمة تغطية الشركة: "+healthRecordsModel.getComPrice());
        custv_id.setText("قيمة تغطية المستفيد: "+healthRecordsModel.getCustPrice());
        det_dig.show();
    }


}