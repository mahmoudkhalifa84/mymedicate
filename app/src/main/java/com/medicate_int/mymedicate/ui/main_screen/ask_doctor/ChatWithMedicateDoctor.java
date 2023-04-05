package com.medicate_int.mymedicate.ui.main_screen.ask_doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.medicate_int.mymedicate.models.ChatModel;
import com.medicate_int.mymedicate.module.Moudle;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.adapter.AskMedicateDoctorAdapter;
import com.medicate_int.mymedicate.module.CheckConnection;
import com.medicate_int.mymedicate.module.LoadingDialog;
import com.medicate_int.mymedicate.ui.Login;
import com.medicate_int.mymedicate.models.AskMedicateDoctorModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ChatWithMedicateDoctor extends AppCompatActivity implements Moudle.MoudleInterface, AskMedicateDoctorAdapter.onCLickLis {

    private static final int FILE_SELECT_CODE = 54;
    int count = 1, items_num = 0, mcount =1;;
    CacheHelper statics;
    RecyclerView recyclerView;
    AskMedicateDoctorAdapter adapter;
    List<AskMedicateDoctorModel> items;
    boolean internet = false;
    String user_name = "nil";
    String user_phone = "nil";
    String TAG = "DOC_CHAT";
    DatabaseReference databaseReference, send_database;
    ValueEventListener dataFetchEventListener;
    FirebaseDatabase firebaseDatabase;
    EditText editTextTextMultiLine;
    String formattedDate;
    SimpleDateFormat df;
    LoadingDialog loadingDialog;
    FirebaseStorage firebaseStorage;
    StorageReference storageRef;
    Context context;
    Snackbar snackbar;
    ImageView tempImage;
    View full_view;
    ImageView full_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicate_doc_chat);
        Objects.requireNonNull(getSupportActionBar()).hide();
        statics = new CacheHelper(this);
        statics.setMY_PLACE("طبيب ميديكيت");
        if (getIntent().getStringExtra("img") != null) {
            Log.d(TAG, "SEND IMG DIRECT >> ");
        };
        context = this;
        tempImage = new ImageView(context);
        full_image = findViewById(R.id.view_full_image);
        full_view = findViewById(R.id.view_full_view);
        loadingDialog = new LoadingDialog(this, R.style.PauseDialog);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageRef = firebaseStorage.getReference().getRoot().child("MedicateDoctorsChat").child(statics.getID());
        items = new ArrayList<>();
        databaseReference = firebaseDatabase.getReference().getRoot().child("MedicateApp").child("AskDocMedicate").child("Chat");
        send_database = databaseReference.child("Users");
        recyclerView = findViewById(R.id.chat_recview);
        editTextTextMultiLine = findViewById(R.id.editTextTextMultiLine);
        adapter = new AskMedicateDoctorAdapter(context, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {

            }
        });
        df = new SimpleDateFormat("dd-MM-yyyy h:mm a", Locale.getDefault());
        if (CheckConnection.isNetworkConnected(this)) {
            internet = true;
            getData();
        } else
            Moudle.NoInternet(this, this);

        findViewById(R.id.chat_2_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckConnection.isNetworkConnected(ChatWithMedicateDoctor.this)) {
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    getData();
                } else {
                    Moudle.NoInternet(ChatWithMedicateDoctor.this, ChatWithMedicateDoctor.this);
                }
            }
        });

        findViewById(R.id.doc_chatuploading_medica).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Moudle.checkPermissionForReadExtertalStorage(context)) {
                    if (CheckConnection.isNetworkConnected(ChatWithMedicateDoctor.this)) {
                        showFileChooser();
                    } else {
                        Moudle.NoInternet(ChatWithMedicateDoctor.this, ChatWithMedicateDoctor.this);
                    }
                } else Moudle.requestPermissionForReadExtertalStorage(context);
            }
        });

        findViewById(R.id.send_but).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextTextMultiLine.getText().length() > 0) {
                    if (CheckConnection.isNetworkConnected(ChatWithMedicateDoctor.this)) {
                        sendMessageUser("null", false);
                    }
                } else {
                    Login.Message(getString(R.string.no_internet_con), ChatWithMedicateDoctor.this);
                }
            }
        });
        findViewById(R.id.imageView45).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                statics.setMY_PLACE("اسال طبيب");
            }
        });

    }

    @Override
    protected void onStop() {
        try {
            databaseReference.removeEventListener(dataFetchEventListener);
            statics.setMY_PLACE("اسال طبيب");
            if (null != snackbar) {
                if (snackbar.isShown())
                    snackbar.dismiss();
            }
        } catch (Exception e) {
            super.onStop();
            return;
        }
        super.onStop();
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(
                    Intent.createChooser(intent, getString(R.string.chose_file_to_upload)),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "حدث خطا [45] ",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d(TAG, "File Uri: " + uri.toString());
                    // Get the path
                    Log.d(TAG, "File Path: " + uri.getPath());
                    upLoadImg(uri);


                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showSnackBar(String value) {
        if (null == snackbar || !snackbar.isShown()) {
            snackbar = Snackbar.make(editTextTextMultiLine, getString(R.string.uploading).concat(value).concat("%"), Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        } else
            snackbar.setText(getString(R.string.uploading).concat(value.substring(0, 2)).concat("%"));

    }

    private void upLoadImg(Uri file) {
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
                            sendMessageUser(uri.toString(), true);
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 102) {
            if (Moudle.checkPermissionForReadExtertalStorage(context)) {
                showFileChooser();
                Moudle.createAppFolder();
            } else {
                Login.Message(context.getString(R.string.plz_perm_read), context);
            }
        }
    }

    public void sendMessageUser(String utl, boolean isImage) {
        if (CheckConnection.isNetworkConnected(this)) {
            send_database = databaseReference;
            String txt = editTextTextMultiLine.getText().toString();
            formattedDate = df.format(Calendar.getInstance().getTime()).replace("م", "PM").replace("ص", "AM");
            AskMedicateDoctorModel askMedicateDoctorModel = new AskMedicateDoctorModel();
            askMedicateDoctorModel.setFile(utl);
            askMedicateDoctorModel.setMsg(txt.trim());
            askMedicateDoctorModel.setMsg_date(formattedDate);
            askMedicateDoctorModel.setSend_by("user".concat(statics.getID()));
            askMedicateDoctorModel.setStatus(0);
            if (isImage) {
                askMedicateDoctorModel.setType("image");
            } else askMedicateDoctorModel.setType("text");
            editTextTextMultiLine.getText().clear();
            send_database.child(statics.getID()).child("conversation").push().setValue(askMedicateDoctorModel);
            send_database.child(statics.getID()).child("msg_count").setValue(mcount++);
        } else {
            Login.Message(getString(R.string.no_internet_con), ChatWithMedicateDoctor.this);
        }
    }

    public void getData() {
            loadingDialog.show();

        try {
        dataFetchEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    statics.setChatState("ok");
                    count = ((int) dataSnapshot.getChildrenCount()) - 2;
                    Log.d(TAG, "onDataChange: count > " + count);
                    int test = 0;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (child.child("msg").exists()) {
                            items.add(new AskMedicateDoctorModel(child.child("msg").getValue(String.class),
                                    child.child("msg_date").getValue(String.class),
                                    child.child("type").getValue(String.class),
                                    child.child("send_by").getValue(String.class),
                                    child.child("file").getValue(String.class),
                                    child.child("status").getValue(Integer.class)));
                            Log.d(TAG, "DATAADDED: DATA > " + items.size());
                            adapter.addNew(items.get(items.size() - 1));
                        }
                    }
                    statics.setReadedChatCount("" + count, "case 88");

                    loadingDialog.dismiss();
                }else{
                    send_database = databaseReference ;
                    send_database.child(statics.getID()).child("user_name").setValue(statics.getUserName());
                    send_database.child(statics.getID()).child("card_number").setValue(statics.getCardNumber());
                    adapter.RemoveAll();
                    items.clear();
                    items_num = 0;
                    count = 1;
                    Log.d(TAG, "ERROR: NO DATA > ");
                    loadingDialog.dismiss();
                }

                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (adapter.getItemCount() > 0)
                            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                    }
                });
                // You async code goes here
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                loadingDialog.dismiss();
                Moudle.NoInternet(ChatWithMedicateDoctor.this, ChatWithMedicateDoctor.this);
            }
        };

        databaseReference.child(statics.getID()).child("conversation").addValueEventListener(dataFetchEventListener);

    } catch (Exception e) {
            loadingDialog.dismiss();
        Login.Message(getString(R.string.no_internet_con), context);
        Log.d(TAG, "Exception: DATA > " + e.getMessage());
    }
}






    private void setClipboard(String text) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText(getString(R.string.copy_text_secc), text);
        clipboard.setPrimaryClip(clip);
    }

    @Override
    public void onBack(boolean bool) {
        statics.setMY_PLACE("اسال طبيب");
        ChatWithMedicateDoctor.this.onBackPressed();

    }

    @Override
    public void okClicked(boolean bool) {

    }

    @Override
    public void onCLick(int p) {
        Dialog long_prs = new Dialog(ChatWithMedicateDoctor.this, R.style.PauseDialog);
        long_prs.setCancelable(true);
        long_prs.setCanceledOnTouchOutside(true);
        long_prs.setContentView(R.layout.on_long_click);
        if (items.get(p).getSend_by().contains("admin")) {
            ((TextView) long_prs.findViewById(R.id.comp_name)).setText(getString(R.string.medicate_doc));

        } else {
            ((TextView) long_prs.findViewById(R.id.comp_name)).setText(statics.getUserName());
        }
        ((TextView) long_prs.findViewById(R.id.comp_date)).setText(items.get(p).getMsg_date());

        long_prs.findViewById(R.id.copy_txt_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long_prs.dismiss();
                setClipboard(adapter.getIm().get(p).getMsg());
                Toast.makeText(ChatWithMedicateDoctor.this, getString(R.string.copy_text_secc), Toast.LENGTH_SHORT).show();
            }
        });
        long_prs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        long_prs.show();
    }

    @Override
    public void imageClicked(int p) {
        if (full_image.getVisibility() == View.GONE) {
            full_view.setVisibility(View.VISIBLE);
            Picasso.with(context).load(adapter.getIm().get(p).getFile()).placeholder(R.drawable.new_loading).into(full_image);
            full_image.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_bob));
            full_image.setVisibility(View.VISIBLE);
            return;

        }else if
        (full_image.getVisibility() == View.VISIBLE) {
            full_image.setAnimation(AnimationUtils.loadAnimation(context, R.anim.popout));
            full_image.setVisibility(View.GONE);
            full_view.setVisibility(View.GONE);
            return;
        }
    }

    @Override
    public void onBackPressed() {
        if (full_image.getVisibility() == View.VISIBLE) {
            full_image.setAnimation(AnimationUtils.loadAnimation(context, R.anim.popout));
            full_image.setVisibility(View.GONE);
            full_view.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();

    }
}