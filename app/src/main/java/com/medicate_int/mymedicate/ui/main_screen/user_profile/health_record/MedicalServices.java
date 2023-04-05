package com.medicate_int.mymedicate.ui.main_screen.user_profile.health_record;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.medicate_int.mymedicate.ApiClient;
import com.medicate_int.mymedicate.module.CheckConnection;
import com.medicate_int.mymedicate.module.LoadingDialog;
import com.medicate_int.mymedicate.module.Moudle;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.adapter.HelthRecordShowdetailsAdapter;
import com.medicate_int.mymedicate.adapter.HelthRecordsDataAdapter;
import com.medicate_int.mymedicate.models.HealthRecordsModel;
import com.medicate_int.mymedicate.module.Statics;
import com.medicate_int.mymedicate.ui.Login;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalServices extends Fragment implements HelthRecordsDataAdapter.MyHandler {

    View view;
    Context context;
    RecyclerView recyclerView;
    HelthRecordsDataAdapter adapter;
    Call<List<HealthRecordsModel>> call;
    LoadingDialog loadingDialog;
    CacheHelper statics;
    ImageView noDataImg;
    TextView noDataTxt;
    String type;
    private static final String TAG = "HC_Services";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_h_c__services, container, false);

        statics = new CacheHelper(getActivity());
        type = statics.getHelthRecordType();
        context = getActivity();
        call = new ApiClient(ApiClient.MAIN_SYSTEM_URL).getHelthRecordsData(statics.getID());
        recyclerView = view.findViewById(R.id.rec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        noDataImg = view.findViewById(R.id.imageView24);
        noDataTxt = view.findViewById(R.id.textView35);
        loadingDialog = new LoadingDialog(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        view.findViewById(R.id.textView34).setOnClickListener((a)-> getActivity().onBackPressed());
        view.findViewById(R.id.imageView70).setOnClickListener((a)-> getActivity().onBackPressed());
        if (CheckConnection.isNetworkConnected(context)) {
            loadData();
        } else{
            Moudle.DialogOkLesener(getString(R.string.no_internet_con), context, () -> getActivity().onBackPressed());
            noData();
        }

        return view;

    }
    private void loadData() {
    //    Log.d(TAG, "loadData: " + ApiClient.MAIN_SYSTEM_URL + ApiClient.HelthRecords + "/" + statics.getID());
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
        List<HealthRecordsModel> temp = new ArrayList<>();

        for (HealthRecordsModel item :
                body) {
            if (item.getParentID().trim().equals(type))
                temp.add(item);
        }
        if (temp.size() > 0) {
            Collections.reverse(temp);
            adapter = new HelthRecordsDataAdapter(context, temp, MedicalServices.this);
            recyclerView.setAdapter(adapter);
        } else noData();
    }

    private void noData() {
        if (loadingDialog.isShowing())
            loadingDialog.dismiss();
        Log.d(TAG, "noData: No Data");
        noDataImg.setAnimation(AnimationUtils.loadAnimation(context,R.anim.network_bob));
        noDataTxt.setAnimation(AnimationUtils.loadAnimation(context,R.anim.network_bob));
        noDataImg.setVisibility(View.VISIBLE);
        noDataTxt.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCLickView(int p) {
        setUpDetDig(adapter.getIm().get(p));
    }
    public void setUpDetDig(HealthRecordsModel healthRecordsModel){
        Dialog det_dig = new Dialog(context);
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