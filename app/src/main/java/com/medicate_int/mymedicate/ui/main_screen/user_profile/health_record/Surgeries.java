package com.medicate_int.mymedicate.ui.main_screen.user_profile.health_record;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.SystemClock;
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
import com.medicate_int.mymedicate.module.Endpoint;
import com.medicate_int.mymedicate.module.Statics;
import com.medicate_int.mymedicate.ui.Login;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Surgeries extends Fragment implements HelthRecordsDataAdapter.MyHandler {

    View view;
    Dialog dialog;
    CacheHelper statics;
    RecyclerView recyclerView;
    Context context;
    ImageView noDataImg;
    TextView noDataTxt;
    String type;
    LoadingDialog loadingDialog;
    Call<List<HealthRecordsModel>> call;
    HelthRecordsDataAdapter adapter;
    private static final String TAG = "FragmentSorgens";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sorgens, container, false);
        context = getActivity();
        statics = new CacheHelper(getActivity());
        type = statics.getHelthRecordType();
        call = new ApiClient(ApiClient.MAIN_SYSTEM_URL).getHelthRecordsData(statics.getID());
        recyclerView = view.findViewById(R.id.rec_view_serg);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        noDataImg = view.findViewById(R.id.imageView24);
        noDataTxt = view.findViewById(R.id.textView35);
        loadingDialog = new LoadingDialog(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        view.findViewById(R.id.ser_but_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go();
            }
        });
        view.findViewById(R.id.ser_img_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go();
            }
        });
        view.findViewById(R.id.imageView68).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        view.findViewById(R.id.textView34).setOnClickListener(new View.OnClickListener() {
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
        Log.d(TAG, "loadData: " + ApiClient.MAIN_SYSTEM_URL + Endpoint.HelthRecords + "/" + statics.getID());
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
            adapter = new HelthRecordsDataAdapter(context, temp, Surgeries.this);
            recyclerView.setAdapter(adapter);
        } else noData();
    }

    private void go() {
        dialog = new Dialog(getActivity(), R.style.PauseDialog);
        dialog.setContentView(R.layout.add_sergeon_dailog);
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

    private void noData() {
        if (loadingDialog.isShowing())
            loadingDialog.dismiss();
        Log.d(TAG, "noData: No Data");
        noDataImg.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_bob));
        noDataTxt.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_bob));
        noDataImg.setVisibility(View.VISIBLE);
        noDataTxt.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCLickView(int p) {
        setUpDetDig(adapter.getIm().get(p));
    }

    public void setUpDetDig(HealthRecordsModel healthRecordsModel) {
        Dialog det_dig = new Dialog(context);
        det_dig.setContentView(R.layout.helth_records_view_details);
        det_dig.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        det_dig.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RecyclerView recyclerView = det_dig.findViewById(R.id.rec_view_det);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        List<String> stringList = new ArrayList<>();
        if (healthRecordsModel.getImageValue0() != null)
            stringList.add(healthRecordsModel.getImageValue0());
        if (healthRecordsModel.getImageValue1() != null)
            stringList.add(healthRecordsModel.getImageValue1());
        if (healthRecordsModel.getImageValue2() != null)
            stringList.add(healthRecordsModel.getImageValue2());
        if (healthRecordsModel.getImageValue3() != null)
            stringList.add(healthRecordsModel.getImageValue3());
        if (healthRecordsModel.getImageValue4() != null)
            stringList.add(healthRecordsModel.getImageValue4());

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
                Moudle.downloadImage(context, stringList.get(p), Statics.FILES_MY_MEDICAL_RECORDS);
            }

            @Override
            public void onLongClick(int p) {

            }
        }));

        if (stringList.isEmpty()) det_dig.findViewById(R.id.nodata).setVisibility(View.VISIBLE);
        det_dig.show();
    }

    @Override
    public void onStop() {
        if (loadingDialog.isShowing())
            loadingDialog.dismiss();
        call.cancel();
        super.onStop();
    }
}