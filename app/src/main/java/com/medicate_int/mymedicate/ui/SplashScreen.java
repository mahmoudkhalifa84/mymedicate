package com.medicate_int.mymedicate.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.medicate_int.mymedicate.services.FirebaseMessageHandler;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.SetLocal;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.HomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;


public class SplashScreen extends AppCompatActivity {
    CacheHelper statics;
    TextView next;
    Handler handler;
    private static final String TAG = "MAINACTIVITYCLASS";
    Runnable doNextActivity2;
    public static final String NOTIFICATION_CHANNEL_ID = "44235195";
    public static final String OTHEER = "https://www.medicate.ly/notification.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            if (!(HomeActivity.isMyServiceRunning(FirebaseMessageHandler.class, this))) {
                startService(new Intent(this, FirebaseMessageHandler.class));
            }
        } catch (Exception e) {
            Log.d(TAG, "onCreate: ERROR LUNCH SERVICE : " + e.getMessage());
            return;
        }

        statics = new CacheHelper(this);

        statics.setUpdateCheck("true");
        statics.setPricesIns("false");
        Log.d(TAG, "onCreate: ID NUMBER > " + statics.getID());
        Log.d(TAG, "onCreate: VERSION > " + CacheHelper.getVersionName(this));
        getSupportActionBar().hide();
        if (statics.getVisitorNumber().equals("null"))
            statics.setVisitorNumber(String.valueOf(Math.random() * 9999999).replace(".", ""));
        Log.d(TAG, "Visitor num : " + statics.getVisitorNumber());
        handler = new Handler();
        createNotificationChannel();
        GifImageView LOGO_M = findViewById(R.id.logo_m);
        //  LOGO_M.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo_m));
        next = findViewById(R.id.main_next);
        Spinner spinner = findViewById(R.id.spinner_laguages);
        if (SetLocal.getLong(this.getApplicationContext()).equals("en")) {
            spinner.setSelection(1, true);
            next.setText("Continue");
        } else if (SetLocal.getLong(this.getApplicationContext()).equals("fr")) {
            spinner.setSelection(2, true);
            next.setText("Suivant");
        } else {
            spinner.setSelection(0, true);
            next.setText("استمرار");
        }
        //     Moudle.requestPermissionForAccessMedia(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onCl(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        onCl(spinner.getSelectedItemPosition());
        final Intent intent = new Intent(this, Slider.class);
        //   LOGO_M.animate();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(intent);
            }
        });
        doNextActivity2 = new Runnable() {
            @Override
            public void run() {
                //  // Intent to jump to the next activity.
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                LOGO_M.clearAnimation();
                next.setVisibility(View.VISIBLE);
                next.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_an2));
                spinner.setVisibility(View.VISIBLE);
                spinner.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.network_bob));
            }
        };

        therd();
    }

    private void therd() {
        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                if (statics.getID().equals("null") && statics.getIntro().equals("null"))
                    handler.post(doNextActivity2);
                else {
                    statics.setMY_PLACE("المنزل");
                    startActivity(new Intent(SplashScreen.this, HomeActivity.class));
                    finish();
                }
            }
        }.start();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.Notification);
            String description = getString(R.string.Notification);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel =
                    new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setShowBadge(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                channel.setAllowBubbles(true);
            }
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void onCl(int position) {
        if (position == 0) {
            setLocale("ar", this.getApplicationContext());
            next.setText("استمرار");
        } else if (position == 1) {
            setLocale("en", this.getApplicationContext());
            next.setText("Continue");
        } else {
            setLocale("fr", this.getApplicationContext());
            next.setText("Suivant");
        }
    }

    public void setLocale(String lang, Context context) {
        SharedPreferences pref = context.getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Locale loc = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();
        config.locale = loc;
        config.setLayoutDirection(loc);
        res.updateConfiguration(config, displayMetrics);
        CacheHelper statics = new CacheHelper(context);
        statics.savePrefsData("lang", lang);
        editor.putString("lang", lang);
        editor.apply();
    }


}
