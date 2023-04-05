package com.medicate_int.mymedicate.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.medicate_int.mymedicate.HomeActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FirebaseMessageHandler extends FirebaseMessagingService {
    String TAG  ="FFIIINNN";


    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");

      FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.d(TAG, token);
                    }
                });
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
              //  handleNow();
        }
        if (remoteMessage.getNotification() != null) {
            String formattedDate = new SimpleDateFormat("dd-MM-yyyy h:mm a", Locale.getDefault()).format(Calendar.getInstance().getTime());
         //   Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        //    Log.d(TAG, "Message Notification Title: " + remoteMessage.getNotification().getTitle());
         //   Log.d(TAG, "onMessageReceived: ADD > " + new OtherNotificationsDatabase(FirebaseMessageHandler.this.getApplicationContext()).insertData(remoteMessage.getNotification().getBody().toString().trim(),formattedDate));
          //  new OtherNotificationsDatabase(this).insertData(remoteMessage.getNotification().getBody(),formattedDate);
            HomeActivity.pushFirebaseNotifications(this,remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
          //  Map<String, String> data = remoteMessage.getData();
        }
    }
}
