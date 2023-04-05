package com.medicate_int.mymedicate.ui.main_screen.support_chat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devlomi.record_view.OnRecordListener;
import com.devlomi.record_view.RecordButton;
import com.devlomi.record_view.RecordView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.medicate_int.mymedicate.ApiClient;
import com.medicate_int.mymedicate.Constants.AllConstants;
import com.medicate_int.mymedicate.Permissions.Permissions;
import com.medicate_int.mymedicate.module.CheckConnection;
import com.medicate_int.mymedicate.module.LoadingDialog;
import com.medicate_int.mymedicate.module.Moudle;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.adapter.ChatAdapter;
import com.medicate_int.mymedicate.HomeActivity;
import com.medicate_int.mymedicate.ui.Login;
import com.medicate_int.mymedicate.models.ChatModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class SupportChat extends Fragment implements ChatAdapter.onCLickLis {

    View view;
    int count = 1, items_num = 0, mcount =1;
    CacheHelper statics;
    RecyclerView recyclerView;
    ChatAdapter adapter;
    List<ChatModel> items;
    LoadingDialog loadingDialog;
    boolean internet = false;
    String user_name = "nil", user_phone = "nil" , card_number = "nil", userID = "nil", receiver_type="nil", receiver_id = "nil",audioPath;
    Dialog no_int_did, dailog;
    static String TAG = "CHATTAG";
    EditText editTextTextMultiLine;
    String formattedDate;
    SimpleDateFormat df;
    Context context;
    DatabaseReference databaseReference, send_database;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    StorageReference storageRef;
    Snackbar snackbar;
    CircleImageView send_but, file_but ;
    RelativeLayout messageLayout;
    ImageView tempImage;
    View full_view;
    ImageView full_image;
    ValueEventListener dataFetchEventListener;
    public static final int CODE_GALARY = 1456;
    Uri filePath ;
    FirebaseFirestore db;
    CollectionReference fdatabaseReference;
    DocumentReference sendfir;
    RecordView recordView;
    RecordButton recordButton ;
    private MediaRecorder mediaRecorder;
    private Runnable recordRunnable, recordTimerRunnable;
    private Handler recordWaitHandler, recordTimerHandler;

    private Permissions permissions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        df = new SimpleDateFormat("dd-MM-yyyy h:mm a", Locale.getDefault());
        items = new ArrayList<>();
        context = getContext();
        tempImage = new ImageView(context);
        statics = new CacheHelper(context);
        Log.d(TAG, "onDataChange: compid > " + statics.getID());
        user_name = statics.getUserName();
        card_number = statics.getCardNumber();
        user_phone = statics.getPhone();
        userID = statics.getID();
        // tempImage = new ImageView(context);
        Log.d(TAG, "getCoName " + statics.getCoName());
        Moudle.cancelNotification(getActivity(), 0);
        permissions = new Permissions();

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        full_image = view.findViewById(R.id.view_full_image);
        full_view = view.findViewById(R.id.view_full_view);
        send_but = view.findViewById(R.id.send_but);
        file_but = view.findViewById(R.id.file_but);
        recordView = view.findViewById(R.id.recordView);
        recordButton = view.findViewById(R.id.recordButton);
        messageLayout = view.findViewById(R.id.messageLayout);
        editTextTextMultiLine = view.findViewById(R.id.editTextTextMultiLine);
        adapter = new ChatAdapter(context, this);

        if (permissions.isRecordingOk(context))
                recordButton.setListenForRecord(true);
        else permissions.requestRecording(getActivity());


        if (internet) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference().getRoot().child("MedicateApp");

            if( statics.getMY_PLACE().equals("محادثة فورية مع ميديكيت")) {
                databaseReference = databaseReference.child("Chat");
            }else {
                databaseReference = databaseReference.child("ChatComp");
            }
            send_database = databaseReference ;

            firebaseStorage = FirebaseStorage.getInstance();
            storageRef = firebaseStorage.getReference().getRoot().child("MedicateChat").child(statics.getID());
            getData();
            initView();

        } else {
            NoInternet();
        }
        recyclerView = view.findViewById(R.id.chat_recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        editTextTextMultiLine.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {

                    send_but.setVisibility(View.GONE);
                    recordButton.setVisibility(View.VISIBLE);

                } else {
                    recordButton.setVisibility(View.GONE);
                    send_but.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        view.findViewById(R.id.send_but).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextTextMultiLine.getText().length() > 0) {

                    if (CheckConnection.isNetworkConnected(getActivity())) {
                        sendMessageUser("null", "text");
                    } else {
                        Login.Message(getString(R.string.no_internet_con), getActivity());
                    }

                }
            }
        });


        view.findViewById(R.id.file_but).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //    getActivity().finish();
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), CODE_GALARY);

            }
        });

        view.findViewById(R.id.imageView45).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        Log.d(TAG, "requestCode" + requestCode);

        switch (requestCode) {
            case AllConstants.RECORDING_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (this.permissions.isStorageOk(context))
                        recordButton.setListenForRecord(true);
                    else this.permissions.requestStorage(getActivity());

                } else
                    Toast.makeText(context, "Recording permission denied", Toast.LENGTH_SHORT).show();
                break;
            case AllConstants.STORAGE_REQUEST_CODE:

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    recordButton.setListenForRecord(true);
                else
                    Toast.makeText(context, "Storage permission denied", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initView() {

        recordButton.setRecordView(recordView);
        recordButton.setOnClickListener(view -> {


        });
        recordView.setOnRecordListener(new OnRecordListener() {
            @Override
            public void onStart() {
                setUpRecording();
                try {
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                    recordTimerStart();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                file_but.setVisibility(View.GONE);

                recordView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancel() {
                mediaRecorder.reset();
                mediaRecorder.release();
                File file = new File(audioPath);
                if (file.exists())
                    file.delete();
              //  recordView.setVisibility(View.GONE);
                file_but.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish(long recordTime) {
                try {
                    mediaRecorder.stop();
                    mediaRecorder.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                recordView.setVisibility(View.GONE);
                file_but.setVisibility(View.VISIBLE);
                sendRecodingMessage(audioPath);
            }



            @Override
            public void onLessThanSecond() {
                mediaRecorder.reset();
                mediaRecorder.release();
                File file = new File(audioPath);
                if (file.exists())
                    file.delete();

                file_but.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setUpRecording() {

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        File file = new File(context.getExternalFilesDir(null).getAbsolutePath(), "MyMedicate/Media/Recording");


        if (!file.exists())
            file.mkdirs();
        audioPath = file.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".mp3";

        mediaRecorder.setOutputFile(audioPath);
    }

    private void sendRecodingMessage(String audioPath) {
         Uri audioFile = Uri.fromFile(new File(audioPath));
        Log.d(TAG, "filefilefile: " + audioPath);

        storageRef.child("/Media/Recording/"+ "/" + System.currentTimeMillis()).putFile(audioFile).addOnSuccessListener(success -> {
            Task<Uri> audioUrl = success.getStorage().getDownloadUrl();

                audioUrl.addOnCompleteListener(path -> {
                    if (path.isSuccessful()) {

                        String url = path.getResult().toString();
                            sendMessageUser(url.toString(), "recording");
                        }

                });
            });

    }
    private void recordTimerStart() {
        try {
            recordTimerRunnable = new Runnable() {
                public void run() {
                    recordTimerHandler.postDelayed(this, 1000);
                }
            };
            if (recordTimerHandler == null)
                recordTimerHandler = new Handler(Looper.getMainLooper());

            recordTimerHandler.post(recordTimerRunnable);
        } catch (Exception ignored) {
        }
    }
    public void LoadingDigSetup() {
        dailog = new Dialog(getActivity());
        dailog.setContentView(R.layout.loading_layout);
        dailog.setCancelable(true);
        dailog.setCanceledOnTouchOutside(false);
        dailog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dailog.dismiss();
                getActivity().onBackPressed();
            }
        });
        dailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void NoInternet() {
        no_int_did = new Dialog(getActivity(), R.style.PauseDialog);
        no_int_did.setCancelable(false);
        no_int_did.setContentView(R.layout.fragment_dailog);
        ((TextView) no_int_did.findViewById(R.id.di_ok)).setText(getString(R.string.try_agin));
        ((TextView) no_int_did.findViewById(R.id.text_dig)).setText(getString(R.string.no_internet_con));
        no_int_did.findViewById(R.id.di_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_int_did.dismiss();
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        no_int_did.findViewById(R.id.di_but_cancal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_int_did.dismiss();
                getActivity().onBackPressed();
            }
        });
        no_int_did.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        no_int_did.show();
    }

    public void getData() {
        dailog.show();
        try {
            dataFetchEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        statics.setChatState("ok");
                        count = ((int) dataSnapshot.getChildrenCount()) - 2;
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            if (dataSnapshot.child(child.getKey()).exists()) {
                                if (child.child("msg").exists()) {
                                    items.add(new ChatModel(child.child("msg").getValue(String.class),
                                            child.child("msg_date").getValue(String.class),
                                            child.child("type").getValue(String.class),
                                            child.child("send_by").getValue(String.class),
                                            child.child("file").getValue(String.class),
                                            child.child("status").getValue(Integer.class)));
                                }
                            }
                        }
                        adapter.addNew(items.get(items.size() - 1));
                        statics.setReadedChatCount("" + count, "case 88");
                        dailog.dismiss();
                    }else{
                        send_database.child(statics.getID()).child("user_name").setValue(statics.getUserName());
                        send_database.child(statics.getID()).child("card_number").setValue(statics.getCardNumber());
                        adapter.RemoveAll();
                        items.clear();
                        count = 1;
                        Log.d(TAG, "ERROR: NO DATA > ");
                        dailog.dismiss();
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
                    dailog.dismiss();
                    NoInternet();
                }
            };
            databaseReference.child(statics.getID()).child("conversation").addValueEventListener(dataFetchEventListener);
        } catch (Exception e) {
            dailog.dismiss();
            Login.Message(getString(R.string.no_internet_con), context);
            Log.d(TAG, "Exception: DATA > " + e.getMessage());
        }
    }





    private void setClipboard(String text) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText(getString(R.string.copy_text_secc), text);
        clipboard.setPrimaryClip(clip);
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop: CHAT FIREBASE");
        try {
            db.terminate();
        } catch (Exception e) {
            Log.d(TAG, "onStop: ERROR 31 >  " + e.getMessage());
            super.onStop();
            return;
        }
        super.onStop();
    }


    public void sendMessageUser(String utl, String type) {
        if (CheckConnection.isNetworkConnected(getActivity())) {
            String txt = editTextTextMultiLine.getText().toString();
            formattedDate = df.format(Calendar.getInstance().getTime()).replace("م", "PM").replace("ص", "AM");
            ChatModel chatModel = new ChatModel();
            chatModel.setFile(utl);
            chatModel.setMsg(txt.trim());
            chatModel.setMsg_date(formattedDate);
            chatModel.setSend_by("user");
            chatModel.setStatus(0);
            chatModel.setType(type);
            editTextTextMultiLine.getText().clear();
            send_database.child(statics.getID()).child("conversation").push().setValue(chatModel);
            send_database.child(statics.getID()).child("msg_count").setValue(mcount++);

        } else {
            Login.Message(getString(R.string.no_internet_con), getActivity());
        }
    }




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        LoadingDigSetup();
        if (CheckConnection.isNetworkConnected(requireActivity())) {
            internet = true;
        }
    }


    @Override
    public void onCLick(int p) {
        Dialog long_prs = new Dialog(getActivity(), R.style.PauseDialog);
        long_prs.setCancelable(true);
        long_prs.setCanceledOnTouchOutside(true);
        long_prs.setContentView(R.layout.on_long_click);
        if (items.get(p).getSend_by().contains("admin")) {
            ((TextView) long_prs.findViewById(R.id.comp_name)).setText(getString(R.string.medicate));

        } else {
            ((TextView) long_prs.findViewById(R.id.comp_name)).setText(getString(R.string.you));
        }
        ((TextView) long_prs.findViewById(R.id.comp_date)).setText(items.get(p).getMsg_date());

        long_prs.findViewById(R.id.copy_txt_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long_prs.dismiss();
                setClipboard(adapter.getIm().get(p).getMsg());
                Toast.makeText(getActivity(), getString(R.string.copy_text_secc), Toast.LENGTH_SHORT).show();
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode  ==  CODE_GALARY && resultCode == Activity.RESULT_OK) {
            filePath = data.getData();
            upLoadImg(filePath);

        }

    }


    private void upLoadImg(Uri file) {
        UploadTask uploadTask;

        String temp_name = "img" +
                String.valueOf(Math.random() * 999)
                        .replace(".", "")
                        .replace(",", "")
                        .concat(".jpg");
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpg")
                .build();
        uploadTask = storageRef.child("/Media/images/"+ "/" + temp_name).putFile(file, metadata);
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
                            sendMessageUser(uri.toString(), "image");
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
    private void showSnackBar(String value) {
        if (null == snackbar || !snackbar.isShown()) {
            snackbar = Snackbar.make(editTextTextMultiLine, getString(R.string.uploading).concat(value).concat("%"), Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        } else
            snackbar.setText(getString(R.string.uploading).concat(value.substring(0, 2)).concat("%"));

    }



}