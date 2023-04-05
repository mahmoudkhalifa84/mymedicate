package com.medicate_int.mymedicate.ui.main_screen.user_profile.medical_files;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.medicate_int.mymedicate.module.CheckConnection;
import com.medicate_int.mymedicate.module.LoadingDialog;
import com.medicate_int.mymedicate.module.Moudle;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.adapter.MedicalDataAdapter;
import com.medicate_int.mymedicate.models.MedicalFilesModel;
import com.medicate_int.mymedicate.module.Statics;
import com.medicate_int.mymedicate.ui.Login;


public class MyMedicalFiles extends Fragment implements View.OnClickListener, MedicalDataAdapter.MyHandler {
    View view;
    private Context context;
    ConstraintLayout noData;
    RecyclerView recyclerView;
    //  List<MedicalFilesModel> list;
    MedicalDataAdapter adapter;
    LoadingDialog loadingDialog;
    CacheHelper statics;
    EditText search;
    private static final String TAG = "FragmentMyFilesTAG";
    DatabaseReference databaseReference;
    int lastSelected = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_files, container, false);
        view.findViewById(R.id.ser_but_add).setOnClickListener(this);
        view.findViewById(R.id.backid13).setOnClickListener(this);
        context = getActivity();

        AndroidNetworking.initialize(context);
        loadingDialog = new LoadingDialog(context, R.style.PauseDialog);
        noData = view.findViewById(R.id.md_file_no_files);
        recyclerView = view.findViewById(R.id.md_file_rec);

        view.findViewById(R.id.add_more_files).setOnClickListener(this::onClick);
        search = view.findViewById(R.id.etSearchBar3);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        statics = new CacheHelper(context);
        adapter = new MedicalDataAdapter(context, MyMedicalFiles.this);
        recyclerView.setAdapter(adapter);
        //  list = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("MedicateApp").child("UserUploadedFiles").child("user" + statics.getID());
        if (CheckConnection.isNetworkConnected(context)) {
            loadData();
        } else {
            Moudle.DialogOkLesener(getString(R.string.no_internet_con), context, new Moudle.OkClicked() {
                @Override
                public void isClicked() {
                    getActivity().onBackPressed();
                }
            });
        }

        return view;
    }

    private void loadData() {
        Log.d(TAG, "loadData: ");
        ValueEventListener cev = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adapter.getIm().clear();
                if (snapshot.hasChildren()) {
                    Log.d(TAG, "onDataChange: ");
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Log.d(TAG, "onDataChange: >> " + snap.getKey() + " - ");
                        if (snap.child("deleted").getValue().equals(false))
                            adapter.addNew(new Gson().fromJson(new Gson().toJson(snap.getValue()), MedicalFilesModel.class));
                        //  list.add(new Gson().fromJson(new Gson().toJson(snap.getValue()), MedicalFilesModel.class));
                    }
                    assert adapter.getIm() != null;
                    if (adapter.getIm().size() > 0) hasData();
                } else {
                    Log.d(TAG, "ELSE: ");
                    noData();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(cev);
        //  databaseReference.addValueEventListener(cev);
      /*  databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
             *//*   if (snapshot.hasChildren()) {
                    Log.d(TAG, "onChildAdded: ");
                    adapter.addNew(new Gson().fromJson(new Gson().toJson(snapshot.getValue()), MedicalFilesModel.class));
                    //  adapter.getIm().get(adapter.getItemCount()-1).setKey(snapshot.getKey());
                } else {
                    Log.d(TAG, "ELSE: ");
                    noData();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               *//* if (snapshot.hasChildren()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Log.d(TAG, "onDataChange: >> " + snap.getKey() + " - " + snap.getValue());
                        if (snap.child("deleted").getValue().equals(false))
                            adapter.addNew(new Gson().fromJson(new Gson().toJson(snap.getValue()), MedicalFilesModel.class));
                            adapter.getIm().get(adapter.getItemCount()-1).setKey(snap.getKey());
                        //  list.add(new Gson().fromJson(new Gson().toJson(snap.getValue()), MedicalFilesModel.class));
                    }
                } else {
                    Log.d(TAG, "ELSE: ");
                    noData();
                }*//*
                if (snapshot.hasChildren()) {
                    Log.d(TAG, "onChildAdded: ");
                    adapter.addNew(new Gson().fromJson(new Gson().toJson(snapshot.getValue()), MedicalFilesModel.class));
                    //  adapter.getIm().get(adapter.getItemCount()-1).setKey(snapshot.getKey());
                } else {
                    Log.d(TAG, "ELSE: ");
                    noData();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }

    private void noData() {
        recyclerView.setVisibility(View.GONE);
        noData.setVisibility(View.VISIBLE);
        search.setVisibility(View.GONE);
    }

    private void hasData() {
        noData.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        search.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ser_but_add:
            case R.id.add_more_files:
                //  FragmentLogin.Message(getString(R.string.wil_be_av_soon),getActivity());
                startActivity(new Intent(context, AddMedicalFile.class));
                break;
            case R.id.backid13:
                getActivity().onBackPressed();
                break;
        }

    }

    @Override
    public void onCLickView(int p) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(adapter.getIm().get(p).getImg()), "image/*");
        startActivity(intent);
        // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(p).getImg()))); /** replace with your own uri */

    }

    @Override
    public void onCLickDownload(int p) {
        Log.d(TAG, "onCLickDownload: ");
        lastSelected = p;
        if (!Moudle.checkPermissionForReadExtertalStorage(context)) {
            Moudle.DialogOkLesener(getString(R.string.plz_perm_read), context, () -> Moudle.requestPermissionForReadExtertalStorage(context));
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (!Moudle.checkPermissionForAccessMedia(context)) {
                Moudle.requestPermissionForAccessMedia(context);
                return;
            }
        } else
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1011);
     /*   if (Moudle.createAppFolder()) {
            String temp_name = "img" +
                    String.valueOf(Math.random() * 999)
                            .replace(".", "")
                            .replace(",", "")
                            .concat(".jpg");
            AndroidNetworking.download(adapter.getIm().get(p).getImg(), Environment.getExternalStorageDirectory() +
                    File.separator + "MyMedicate" + File.separator + "ملفاتي الطبية", temp_name)
                    .setPriority(Priority.HIGH)
                    .build()
                    .setDownloadProgressListener(new DownloadProgressListener() {
                        @Override
                        public void onProgress(long bytesDownloaded, long totalBytes) {
                            // do anything with progress
                            double progress = (100.0 * bytesDownloaded) / totalBytes;
                            Log.d(TAG, "onProgress: " + progress);
                        }
                    })
                    .startDownload(new DownloadListener() {
                        @Override
                        public void onDownloadComplete() {
                            // do anything after completion
                            Log.d(TAG, "onDownloadComplete: ");
                        }

                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.d(TAG, "onError: " + error.getMessage() + " - " + error.getErrorDetail());
                        }
                    });
        }*/

        //   Moudle.downloadImage(context,adapter.getIm().get(p).getImg());
        Log.d(TAG, "onCLickDownload: START");
        Toast.makeText(context, getString(R.string.downloading), Toast.LENGTH_SHORT).show();
        Moudle.downloadImage(context, adapter.getIm().get(p).getImg(), Statics.FILES_MY_MEDICAL_FILES);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 103) {
            if (Moudle.checkPermissionForAccessMedia(context)) {
                onCLickDownload(lastSelected);

            } else {
                Login.Message(context.getString(R.string.plz_perm_save), context);
            }
        }
    }

    @Override
    public void onLongClick(int p) {
        Log.d(TAG, "onLongClick: > " + adapter.getIm().get(p).getKey());
        Moudle.okCancelDailog(context, new Moudle.MoudleInterface() {
            @Override
            public void onBack(boolean bool) {

            }

            @Override
            public void okClicked(boolean bool) {
                if (bool) {
                    databaseReference.child(adapter.getIm().get(p).getKey()).child("deleted").setValue(true);
                    adapter.removeAt(p);
                }
            }
        });

    }
}