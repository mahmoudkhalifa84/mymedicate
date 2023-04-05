package com.medicate_int.mymedicate.module;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.medicate_int.mymedicate.HomeActivity;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.ui.main_screen.booking.OrderDrugs;
import com.medicate_int.mymedicate.ui.Login;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class Moudle {
    private static final String TAG = "MoudleTAG";
    private static final int OK = 1;
    private static final int Cancel = 0;

    public static void OpenMyAppStorePage(@NonNull Context c) {
        final String appPackageName = c.getPackageName(); // getPackageName() from Context or Activity object
        try {
            c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static void cancelNotification(Context ctx, int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancel(notifyId);
    }

    public static String getMyCountyName(Context context) {
        switch (new CacheHelper(context).getMY_CONTRY()) {
            case "1":
                return context.getResources().getString(R.string.county_libya);

            case "4":
                return context.getResources().getString(R.string.egy);

            case "2":
                return context.getResources().getString(R.string.county_tunis);

            case "3":
                return context.getResources().getString(R.string.county_aljaz);

            case "7":
                return context.getResources().getString(R.string.county_espain);

            case "6":
                return context.getResources().getString(R.string.county_gaemny);

            case "17":
                return context.getResources().getString(R.string.county_italya);

            case "9":
                return context.getResources().getString(R.string.county_ukranya);

            case "5":
                return context.getResources().getString(R.string.county_turkia);
        }
        return context.getResources().getString(R.string.county_libya);
    }

    public static void downloadImage(Context context, String ImageUrl, String filename) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            downloadForNewVersions(context, ImageUrl, filename);
        else downloadForOldVersions(context, ImageUrl, filename);
    }

    private static void downloadForOldVersions(Context context, String ImageUrl, String filename) {
        Picasso.with(context)
                .load(ImageUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        try {
                            String root = Environment.getExternalStorageDirectory().toString();
                            File myDir = new File(root + "/Pictures/MyMedicate/" + filename);

                            if (!myDir.exists()) {
                                Log.d(TAG, "onBitmapLoaded: CREATE FOLDER > " + myDir.mkdirs());
                            }

                            String name = "ملفاتي_" + getRandomNumber() + ".jpg";
                            myDir = new File(myDir, name);
                            FileOutputStream out = new FileOutputStream(myDir);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                            out.flush();
                            out.close();
                            Toast.makeText(context.getApplicationContext(), context.getApplicationContext().getString(R.string.downlad_file_success), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Log.d(TAG, "onBitmapLoaded: ERROR > " + e.getMessage());
                            Toast.makeText(context.getApplicationContext(), context.getApplicationContext().getString(R.string.error_on_downlad), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Toast.makeText(context.getApplicationContext(), context.getApplicationContext().getString(R.string.error_on_downlad), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        //    Toast.makeText(context.getApplicationContext(), context.getApplicationContext().getString(R.string.error_on_downlad), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @NonNull
    private static void downloadForNewVersions(@NonNull final Context context, @NonNull String imgUrl, String fileName) {
        Picasso.with(context)
                .load(imgUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Log.d(TAG, "onBitmapLoaded: LOADED");
                        final ContentValues values = new ContentValues();
                        values.put(MediaStore.MediaColumns.DISPLAY_NAME, "ملفاتي_" + getRandomNumber() + ".jpg");
                        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
                        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/MyMedicate/" + fileName);

                        final ContentResolver resolver = context.getContentResolver();
                        Uri uri = null;

                        try {
                            final Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                            uri = resolver.insert(contentUri, values);

                            if (uri == null) {
                                Log.d(TAG, "Failed to create new MediaStore record.");
                                return;
                            }

                            try (final OutputStream stream = resolver.openOutputStream(uri)) {
                                if (stream == null) {
                                    Log.d(TAG, "Failed to open output stream.");
                                }

                                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)) {
                                    Log.d(TAG, "Failed to save bitmap.");
                                }
                            }

                            Log.d(TAG, "onBitmapLoaded: FINALY > " + uri);
                            Toast.makeText(context.getApplicationContext(), context.getApplicationContext().getString(R.string.downlad_file_success), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            if (uri != null) {
                                // Don't leave an orphan entry in the MediaStore
                                resolver.delete(uri, null, null);
                            }
                            Log.d(TAG, "onBitmapLoaded: ERROR  > " + e.getMessage());
                        }
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Log.d(TAG, "onBitmapFailed: ");
                        Toast.makeText(context.getApplicationContext(), context.getApplicationContext().getString(R.string.error_on_downlad), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        // Toast.makeText(context.getApplicationContext(), context.getApplicationContext().getString(R.string.error_on_downlad), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onPrepareLoad: ");
                    }
                });
    }

    public static String getRandomNumber() {
        return String.valueOf(Math.random() * 99999).replace(".", "").replace(",", "").substring(0, 6);
    }

    public static void okCancelDailog(Context context, MoudleInterface moudleInterface) {
        Dialog dialog = new Dialog(context, R.style.PauseDialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.fragment_dailog);
        TextView textView = dialog.findViewById(R.id.text_dig);
        textView.setText(R.string.are_you_suer_delete_md_file);
        dialog.findViewById(R.id.di_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                moudleInterface.okClicked(true);
            }
        });
        dialog.findViewById(R.id.di_but_cancal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                moudleInterface.okClicked(false);
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public static boolean createAppFolder() {
        //create folder
        File file = new File(Environment.getExternalStorageDirectory() + "/MyMedicate/الملفات الطبية");
        if (!file.mkdirs()) {
            Log.d(TAG, "createAppFolder: > " + file.mkdirs());
        }
        return file.exists();
    }

    public static void SendFeedbackDialog(Context context) {
        Dialog dialog = new Dialog(context, R.style.PauseDialog);
        dialog.setContentView(R.layout.send_feedback_layout);
        EditText editText = dialog.findViewById(R.id.feed_back_txt);
        TextView button = dialog.findViewById(R.id.feed_back_ok_byt);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ConstraintLayout main = dialog.findViewById(R.id.feed_back_main);
        main.setVisibility(View.VISIBLE);
        ConstraintLayout thanki = dialog.findViewById(R.id.feed_back_thank);
        GifImageView gifImageView = dialog.findViewById(R.id.gifImageView);
        thanki.setVisibility(View.GONE);
        button.setOnClickListener(new View.OnClickListener() {
            /**  **سيرين افضل مبرمجة في العالم و افضل من هشام بالف مرة**/
            @Override
            public void onClick(View v) {
                if (CheckConnection.isNetworkConnected(context)) {
                    if (!editText.getText().toString().trim().equals("")) {
                        send(editText.getText().toString().trim(), context);
                        main.setVisibility(View.GONE);
                        thanki.setVisibility(View.VISIBLE);
                        gifImageView.setEnabled(true);
                        OrderDrugs.hideKeyboardFrom(context, v);
                        waait(dialog);
                    } else {
                        /**  **سيرين افضل مبرمجة في العالم و افضل من هشام بالف مرة**/
                        Toast t = Toast.makeText(context, R.string.plz_fill_feedback, Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.CENTER, 0, 0);
                        t.show();
                    }
                } else
                    Toast.makeText(context, context.getResources().getString(R.string.cheack_int_con), Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    /**
     * *سيرين افضل مبرمجة في العالم و افضل من هشام بالف مرة
     **/
    public static void waait(Dialog dialog) {
        final Handler handler = new Handler();
        final Runnable doNextActivity = new Runnable() {
            @Override
            public void run() {
                dialog.dismiss(); // so the splash activity goes away
            }
        };  /**  **سيرين افضل مبرمجة في العالم و افضل من هشام بالف مرة**/
        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                handler.post(doNextActivity);
            }
        }.start();
    }

    private static void send(String s, Context c) {
        try {
            if (CheckConnection.isNetworkConnected(c)) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef =
                        database.getReference().getRoot().child("MedicateApp").child("FeedBack").child(Calendar.getInstance().getTime().toString());
                Map<String, Object> map = new HashMap<>();
                map.put("android", Build.VERSION.RELEASE);
                map.put("feedBack", s);
                myRef.setValue(map);
            }
        } catch (Exception e) {
            return;
        }
    }

    public static String getDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return df.format(c);
    }

    public static String getTime12() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("h:mm a", Locale.US);
        return df.format(c);
    }

    public static String getTime24() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm", Locale.US);
        return df.format(c);
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getDateFromDay(String day) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            SimpleDateFormat dayFormat = new SimpleDateFormat("EE", Locale.ENGLISH);
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(getDate()));
            while (true) {
                if (dayFormat.format(c.getTime()).equals(day)) {
                    Log.d(TAG, "getDateFromDay: FOUND > " + sdf.format(c.getTime()));
                    return sdf.format(c.getTime());
                }
                c.add(Calendar.DATE, 1);  // number of days to add
            }
            // dt is now the new date
        } catch (ParseException e) {
            Log.d(TAG, "getDateFromDay: ERROR > " + e.getMessage());
            e.printStackTrace();
        }
        return "غير محدد";
    }

    public static String getCurrentDay() {
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return days[day];
    }

    public static void YesCancelDailog(Context context, String string, OkCancelInterface cancelInterface) {
        Dialog dialog = new Dialog(context, R.style.PauseDialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.fragment_dailog);
        TextView textView = dialog.findViewById(R.id.text_dig);
        textView.setText(string);
        dialog.findViewById(R.id.di_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                cancelInterface.OK();
            }
        });
        dialog.findViewById(R.id.di_but_cancal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                cancelInterface.Cancel();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public static void insertEmailPhone(Context context, PhoneEmailInsert phoneEmailInsert) {
        Dialog insertDataDialog = new Dialog(context);
        insertDataDialog.setContentView(R.layout.enter_phone_email_layout);
        insertDataDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        insertDataDialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        insertDataDialog.setCancelable(true);
        insertDataDialog.setCanceledOnTouchOutside(false);
        insertDataDialog.show();
        TextInputEditText ePhone = insertDataDialog.findViewById(R.id.phone_insert);
        TextInputEditText eMail = insertDataDialog.findViewById(R.id.email_insert);
        insertDataDialog.findViewById(R.id.feed_back_ok_byt).setOnClickListener((a) -> {
            if (ePhone.getText().toString().trim().isEmpty() || ePhone.getText().toString().trim().length() < 10) {
                Toast.makeText(context, context.getString(R.string.check_from_phone), Toast.LENGTH_SHORT).show();
            } else {
                phoneEmailInsert.done(ePhone.getText().toString().trim(), eMail.getText().toString().trim());
                //  bookNow(model, ePhone.getText().toString().trim(), eMail.getText().toString().trim());
            }
        });

    }

    public static void OpenWhatsapp(@NonNull Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            Uri uri = Uri.parse("smsto: " + Statics.WHATSAPP_NUMMBER);
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.setPackage("com.whatsapp");
            context.startActivity(Intent.createChooser(i, ""));
        } catch (PackageManager.NameNotFoundException e) {
            Login.Message(context.getString(R.string.you_dont_have_whatsaap), context);
        }
    }

    public static void OpenViber(@NonNull Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo("com.viber.voip", PackageManager.GET_META_DATA);
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("viber://add?number=" + Statics.VIBER_NUMMBER)));
        } catch (PackageManager.NameNotFoundException e) {
            Login.Message(context.getString(R.string.you_dont_have_viber), context);

        }
    }

    public static void ShereApp(@NonNull Context context) {
        String txt = context.getString(R.string.download_medicate_app) + "\n" + " https://play.google.com/store/apps/details?id=" + context.getPackageName();
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, txt);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.shere)));
    }

    public static String convertToEnglishDigits(String value, Context context) {
        if (value == null) return "";
        if (SetLocal.getLong(context).equals("ar")) value.replace("PM", "م").replace("AM", "ص");
        return value.replace("١", "1").replace("٢", "2").replace("٣", "3").replace("٤", "4").replace("٥", "5")
                .replace("٦", "6").replace("٧", "7").replace("٨", "8").replace("٩", "9").replace("٠", "0");
    }

    public static void NoInternet(Context context, MoudleInterface back) {
        Dialog no_int_did = new Dialog(context, R.style.PauseDialog);
        no_int_did.setCancelable(false);
        no_int_did.setContentView(R.layout.no_internet_con);
        no_int_did.findViewById(R.id.di_try).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_int_did.dismiss();
                context.startActivity(new Intent(context, HomeActivity.class));
            }
        });

        no_int_did.findViewById(R.id.di_but_cancal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_int_did.dismiss();
                back.onBack(true);
            }
        });
        no_int_did.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        no_int_did.show();
    }

    public static void CallCenter(Context context) {
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel: " + Statics.PNONE_NUMMBER));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            context.startActivity(i);
        }
    }

    public static boolean checkPermissionForReadExtertalStorage(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    public static boolean checkPermissionForAccessMedia(Context context) {
        int result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            result = context.checkSelfPermission(Manifest.permission.ACCESS_MEDIA_LOCATION);
            return result == PackageManager.PERMISSION_GRANTED;
        } else return true;

    }

    public static void requestPermissionForReadExtertalStorage(Context context) {
        try {

            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    102);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "requestPermissionForReadExtertalStorage: > " + e.getMessage());
            Login.Message(context.getString(R.string.plz_perm_read), context);
            return;

        }
    }

    public static void requestPermissionForAccessMedia(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_MEDIA_LOCATION},
                        103);
            } catch (Exception e) {
                e.printStackTrace();
                Login.Message(context.getString(R.string.plz_perm_save), context);
                return;
            }
        }
    }

    public static boolean checkFromLocationData(float log, float lat) {
        if (lat == 0 || log == 0 || String.valueOf(lat).length() < 6 || String.valueOf(log).length() < 6)
            return false;
        return String.valueOf(lat).contains(".") && String.valueOf(log).contains(".");
    }

    /* public static boolean createAppFolder() {
         try {
             File folder = new File(Environment.getExternalStorageDirectory() +
                     File.separator + "MyMedicate");
             File subFolder = new File(Environment.getExternalStorageDirectory() +
                     File.separator + "MyMedicate" + File.separator + "ملفاتي الطبية");
             boolean success = true;
             if (!folder.exists()) {
                 success = folder.mkdirs();
                 subFolder.mkdir();
             } else return true;
             if (success) {
                 Log.d(TAG, "createAppFolder: DONE");
                 return true;
                 // Do something on success
             } else {
                 Log.d(TAG, "createAppFolder: FALE");
                 return false;
                 // Do something else on failure
             }
         } catch (Exception e) {
             Log.d(TAG, "ERROR: " + e.getMessage());
         }
         return false;
     }*/

    public static Dialog noInternet(Context context, OkCancelInterface okCancelInterface) {
        Dialog no_int_did = new Dialog(context, R.style.PauseDialog);
        no_int_did.setCancelable(false);
        no_int_did.setContentView(R.layout.no_internet_con);
        no_int_did.findViewById(R.id.di_try).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_int_did.dismiss();
                okCancelInterface.OK();
            }
        });
        no_int_did.findViewById(R.id.di_but_cancal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_int_did.dismiss();
                okCancelInterface.Cancel();
            }
        });
        no_int_did.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return no_int_did;
    }

    public static void DialogOkLesener(String txt, Context context, OkClicked okClicked) {
        Dialog dialogsa = new Dialog(context, R.style.PauseDialog);
        dialogsa.setContentView(R.layout.per_requst);
        TextView textView = dialogsa.findViewById(R.id.textView40);
        TextView but = dialogsa.findViewById(R.id.textView42);
        textView.setText(txt);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogsa.dismiss();
                okClicked.isClicked();
            }
        });
        dialogsa.setCancelable(false);
        dialogsa.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogsa.show();
    }

    public interface MoudleInterface {
        void onBack(boolean bool);

        void okClicked(boolean bool);
    }

    public interface OkCancelInterface {
        void OK();

        void Cancel();
    }

    public interface OkClicked {
        void isClicked();
    }

    public interface PhoneEmailInsert {
        void done(String phone, String email);
    }

}
