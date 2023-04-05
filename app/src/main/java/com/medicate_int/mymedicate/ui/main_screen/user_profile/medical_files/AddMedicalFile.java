package com.medicate_int.mymedicate.ui.main_screen.user_profile.medical_files;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.medicate_int.mymedicate.adapter.BookingCityItemAdabter;
import com.medicate_int.mymedicate.models.BookingSpecializationModel;
import com.medicate_int.mymedicate.module.CheckConnection;
import com.medicate_int.mymedicate.module.Moudle;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.ui.Login;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class AddMedicalFile extends AppCompatActivity implements BookingCityItemAdabter.onCLickLis2 {
    BookingCityItemAdabter adabter;
    private ArrayList<BookingSpecializationModel> items;
    BottomSheetDialog sheetBehavior, dialog;
    TextView doc_type, add_doc_text, doc_name,docment_body;
    Context context;
    private Uri imgUrl;
    CacheHelper statics;
    DatabaseReference databaseReference;
    Snackbar snackbar;
    String formattedDate;
    private static final String TAG = "AddMedicalFileClassTAG";
    private SimpleDateFormat df;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medical_file_class);
        rootView = findViewById(android.R.id.content);
        getSupportActionBar().hide();
        doc_type = findViewById(R.id.new_city_pic2);
        context = this;
        statics = new CacheHelper(context);
        df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        add_doc_text = findViewById(R.id.textView84);
        docment_body = findViewById(R.id.docment_body);
        doc_name = findViewById(R.id.doc_name);
        items = new ArrayList<>();
        items.add(new BookingSpecializationModel("تحليل طبي", 1));
        items.add(new BookingSpecializationModel("وصفة طبية", 2));
        items.add(new BookingSpecializationModel("إجراء طبي", 3));
        items.add(new BookingSpecializationModel("شهادة طبية", 4));
        items.add(new BookingSpecializationModel("تقرير طبي", 5));
        items.add(new BookingSpecializationModel("الإحالة", 6));
        items.add(new BookingSpecializationModel("أخرى", 7));
        adabter = new BookingCityItemAdabter(this, items, this);
        databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("MedicateApp").child("UserUploadedFiles").child("user"+statics.getID());

    }

    public void TypePiker(View view) {
        showCityPeker();
    }

    private void showCityPeker() {
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adabter);
        sheetBehavior = new BottomSheetDialog(this);
        sheetBehavior.setContentView(recyclerView);
        sheetBehavior.setCancelable(true);
        sheetBehavior.show();
    }

    @Override
    public void onCLick2(int p) {
        sheetBehavior.dismiss();
        doc_type.setText(items.get(p).getCity_name());

    }

    public void AddDoc(View view) {
        if (Moudle.checkPermissionForReadExtertalStorage(context))
        choase_by_pic();
        else Moudle.requestPermissionForReadExtertalStorage(context);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (Moudle.checkPermissionForReadExtertalStorage(context)) {
                choase_by_pic();
                Moudle.createAppFolder();
            } else {
                Login.Message(context.getString(R.string.plz_perm_read), context);
            }
    }
    @Override
    protected void onStop() {
        try {
            if (null != snackbar) {
                if (snackbar.isShown())
                    snackbar.dismiss();
            }
        }catch (Exception e){
            super.onStop();
            return;
        }
        super.onStop();
    }

    public void choase_by_pic() {
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.camera_or_galary);
        dialog.findViewById(R.id.c_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 123);
            }
        });
        dialog.findViewById(R.id.c_galry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 100);
            }
        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dialog.dismiss();
        if (requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {
                add_doc_text.setText("تم أضافة مستند بنجاح");
                Login.Message("تمت إضافة الصورة بنجاح", context);
                imgUrl = data.getData();
            } else {
                Login.Message("لم يتم ألتقاط الصورة بنجاح", context);
            }
        }
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                if (null != data) {
                    add_doc_text.setText("تم أضافة مستند بنجاح");
                    Login.Message("تمت إضافة الصورة بنجاح", context);
                    imgUrl = data.getData();
                } else
                    Login.Message("لم يتم أختيار الصورة بنجاح", context);
            } else
                Login.Message("لم يتم ألتقاط الصورة بنجاح", context);
        }
    }

    public void upload(View view) {
        Log.d(TAG, "upload: ");
        if (doc_type.getText().toString().trim().equals(getString(R.string.docment_type))) {
            Login.Message(getString(R.string.plz_shose_docment_type), context);
           // showCityPeker();
            Log.d(TAG, "upload: 1");
            return;
        }
        if (doc_name.getText().toString().trim().isEmpty()) {
            Login.Message(getString(R.string.plz_write_file_name), context);
            doc_name.requestFocus();
            Log.d(TAG, "upload: 2");
            return;
        }
        if (null == imgUrl) {
            Login.Message(getString(R.string.plz_shose_pic), context);
            Log.d(TAG, "upload: 3");
            return;
        }
        upLoadImg(imgUrl);
    }

    private void upLoadImg(Uri file) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().getRoot().child("UserUploadedFiles").child("user" + statics.getID());
        UploadTask uploadTask;
        String temp_name = "img" +
                String.valueOf(Math.random() * 999)
                        .replace(".", "")
                        .replace(",", "")
                        .concat(".jpg");

        // Create the file metadata
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpg")
                .build();


        uploadTask = storageRef.child(temp_name).putFile(file, metadata);
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                showSnackBar(String.valueOf(progress + 1));
                Log.d(TAG, "Upload is " + progress + "% done");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.d(TAG, "onFailure: " + exception.getMessage());
                if (null != snackbar) {
                    if (snackbar.isShown())
                        snackbar.dismiss();
                }
                Login.Message(getString(R.string.cheack_int_con), context);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if (taskSnapshot.getTask().isSuccessful()) {
                    if (null != snackbar) {
                        if (snackbar.isShown())
                            snackbar.dismiss();
                    }
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            saveToDatabase(uri.toString());

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Login.Message(getString(R.string.cheack_int_con), context);
                        }
                    });
                }
            }
        });

    }
    public void saveToDatabase(String utl) {
        Log.d(TAG, "saveToDatabase: ");
        if (CheckConnection.isNetworkConnected(this)) {
            HashMap<Object,Object> map = new HashMap<>();
            formattedDate = df.format(Calendar.getInstance().getTime());
            map.put("title",doc_name.getText().toString().trim());
            map.put("body",docment_body.getText().toString().trim());
            map.put("date",formattedDate);
            map.put("doc_type",doc_type.getText().toString());
            map.put("img",utl);
            map.put("deleted",false);
            databaseReference.push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "onSuccess: ");
                    Moudle.DialogOkLesener(getString(R.string.add_secc),context,()-> finish());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: " + e.getMessage());
                    Login.Message(getString(R.string.cheack_int_con), context);
                }
            });

        } else {
            Login.Message(getString(R.string.no_internet_con), context);
        }
    }
    private void showSnackBar(String value){
        if (null == snackbar || !snackbar.isShown()) {
            snackbar = Snackbar.make(rootView,getString(R.string.uploading).concat(value).concat("%"),Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        }else
            snackbar.setText(getString(R.string.uploading).concat(value.substring(0,2)).concat("%"));

    }

    public void Back(View view) {
        finish();
    }
}