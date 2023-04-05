package com.medicate_int.mymedicate.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicate_int.mymedicate.models.BillsNotificationModel;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.SetLocal;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.database.NotificationsDatabase;
import com.medicate_int.mymedicate.database.OtherNotificationsDatabase;
import com.medicate_int.mymedicate.HomeActivity;
import com.medicate_int.mymedicate.module.CheckConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class NotificationsService extends Service {
    List<BillsNotificationModel> items;
    CacheHelper statics;
    Handler handler;
    private OtherNotificationsDatabase otherNotificationsDatabase;

    static String phar = "https://www.medicateint.com/data2/getClaimsPhar/";
    static String dental = "https://www.medicateint.com/data2/getClaimsDental/";
    static String clinics = "https://www.medicateint.com/data2/getClaimsClinic/";
    static String ohter = "https://www.medicateint.com/data2/getNotification/";
    DatabaseReference chatdatabase;
    ValueEventListener eventListener;
    FirebaseDatabase firebaseDatabase;
    private static final String TAG = "NotificationsServiceTAG";
    int i = 1;
    Context context;
    NotificationsDatabase database;
    boolean internet = false;
    private DatabaseReference chatdatabaseAskDoctor;
    private ValueEventListener eventListenerAskDoctor;

    public NotificationsService() {
        statics = new CacheHelper(this);
        context = this;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
        //     getJSON(;"http://www.medicateint.com/index.php/data2/Notifications/" + statics.getID());
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "OnDestroy: ");
        if (null != chatdatabase && null != eventListener) {
            chatdatabase.removeEventListener(eventListener);
            if (!statics.getID().equals("null"))
            chatdatabaseAskDoctor.removeEventListener(eventListenerAskDoctor);
        }
        super.onDestroy();
        onCreate();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "UNB: ");
        return super.onUnbind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d(TAG, "REMOVED: ");
        Intent restartService = new Intent(getApplicationContext(),
                this.getClass());
        try {
            Log.d(TAG, "restart: ");
            if (null != chatdatabase && null != eventListener) {
                chatdatabase.removeEventListener(eventListener);
                if (!statics.getID().equals("null"))
                chatdatabaseAskDoctor.removeEventListener(eventListenerAskDoctor);
            }
            startService(restartService);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "catcg: " + e.getMessage());
        }

        //onCreate();

        // startService(new Intent(getApplicationContext(),this.getClass()));

        //
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        getNoificaions();
        return START_STICKY;


    }

    public void getNoificaions() {
        Log.d(TAG, "getNoificaions: ");
        database = new NotificationsDatabase(this);
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "OnRUNSTART: ");
                if (CheckConnection.isNetworkConnected(NotificationsService.this)) {
                    internet = true;
                    checkNewChat();
                    checkNewChatAskDoctor();
                    getJSON(ohter);
                    if (!statics.getID().equals("null")) {
                        getJSON(clinics.concat(statics.getID()));
                        getJSON(dental.concat(statics.getID()));
                        getJSON(phar.concat(statics.getID()));

                    } else
                        Log.d(TAG, "NULL USER: ");
                    Log.d(TAG, "JASON: ");
                }
                handler.postDelayed(this::run, 6000);
            }
        };
        handler.postDelayed(runnable, 500);
    }

    private void getCli(String json) throws JSONException {
        Cursor cursor = database.getData();
        boolean found = false;
        // Log.d(TAG, "CHAKE: ");
        if (!(json == null)) {
            //    Log.d(TAG, "JASON: NOTNULL " + json);
            String arabic, english, francs, ClaimID,
                    ProductionCode, ClinicClaimAmount, ClaimDate;
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                found = false;
                JSONObject obj = jsonArray.getJSONObject(i);
                arabic = obj.getString("CompanyArabicName");
                english = obj.getString("CompanyEnglishName");
                francs = obj.getString("CompanyEnglishName");
                ProductionCode = obj.getString("ProductionCode");
                ClaimID = obj.getString("ClaimID");
                ClinicClaimAmount = obj.getString("ClinicClaimAmount");
                ClaimDate = obj.getString("ClaimDate");
                if (cursor.getCount() > 0) {
                    if (database.Serach("c", ClaimID) > 0) {
                        found = true;
                    }
                }
                if (!found) {
                    database.insertData(arabic, english, francs, ProductionCode, ClaimID, ClinicClaimAmount, ClaimDate, "1", "c");
                    if (statics.getPricesIns().equals("true")) {
                        if (SetLocal.getLong(this).equals("ar")) {
                            HomeActivity.push(this, getResources().getString(R.string.new_bill_from).concat(arabic), getResources().getString(R.string.total_bail).concat(ClinicClaimAmount),
                                    database.getData().getCount());
                        } else if (SetLocal.getLong(this).equals("fr")) {
                            HomeActivity.push(this, getResources().getString(R.string.new_bill_from).concat(francs), getResources().getString(R.string.total_bail).concat(ClinicClaimAmount),
                                    database.getData().getCount());
                        } else {
                            HomeActivity.push(this, getResources().getString(R.string.new_bill_from).concat(english), getResources().getString(R.string.total_bail).concat(ClinicClaimAmount),
                                    database.getData().getCount());
                        }
                    } else {
                        if (SetLocal.getLong(this).equals("ar")) {
                            HomeActivity.push(this, "MyMedicate", getResources().getString(R.string.new_bill_from).concat(arabic),
                                    database.getData().getCount());
                        } else if (SetLocal.getLong(this).equals("fr")) {
                            HomeActivity.push(this, "MyMedicate", getResources().getString(R.string.new_bill_from).concat(francs),
                                    database.getData().getCount());
                        } else {
                            HomeActivity.push(this, "MyMedicate", getResources().getString(R.string.new_bill_from).concat(english),
                                    database.getData().getCount());
                        }
                    }
                    //       Log.d(TAG, "added + notification ");
                }
            }
        }
    }

    private void getPhar(String json) throws JSONException {
        Cursor cursor = database.getData();
        boolean found = false;
        // Log.d(TAG, "CHAKE: ");
        if (!(json == null)) {
            //     Log.d(TAG, "JASON: NOTNULL " + json);
            String arabic, english, francs, ClaimID, PharmaceClaimAmount,
                    ProductionCode, ClaimDate;
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                found = false;
                JSONObject obj = jsonArray.getJSONObject(i);
                arabic = obj.getString("CompanyArabicName");
                english = obj.getString("CompanyEnglishName");
                francs = obj.getString("CompanyEnglishName");
                ProductionCode = obj.getString("ProductionCode");
                ClaimID = obj.getString("ClaimID");
                PharmaceClaimAmount = obj.getString("PharmaceClaimAmount");
                ClaimDate = obj.getString("ClaimDate");
                if (cursor.getCount() > 0) {
                    if (database.Serach("p", ClaimID) > 0) {
                        found = true;
                    }
                }
                if (!found) {
                    database.insertData(arabic, english, francs, ProductionCode, ClaimID, PharmaceClaimAmount, ClaimDate, "1", "p");
                    if (statics.getPricesIns().equals("true")) {
                        if (SetLocal.getLong(this).equals("ar")) {
                            HomeActivity.push(this, getResources().getString(R.string.new_bill_from).concat(arabic), getResources().getString(R.string.total_bail).concat(PharmaceClaimAmount),
                                    database.getData().getCount());
                        } else if (SetLocal.getLong(this).equals("fr")) {
                            HomeActivity.push(this, getResources().getString(R.string.new_bill_from).concat(francs), getResources().getString(R.string.total_bail).concat(PharmaceClaimAmount),
                                    database.getData().getCount());
                        } else {
                            HomeActivity.push(this, getResources().getString(R.string.new_bill_from).concat(english), getResources().getString(R.string.total_bail).concat(PharmaceClaimAmount),
                                    database.getData().getCount());
                        }
                    } else {
                        if (SetLocal.getLong(this).equals("ar")) {
                            HomeActivity.push(this, "MyMedicate", getResources().getString(R.string.new_bill_from).concat(arabic),
                                    database.getData().getCount());
                        } else if (SetLocal.getLong(this).equals("fr")) {
                            HomeActivity.push(this, "MyMedicate", getResources().getString(R.string.new_bill_from).concat(francs),
                                    database.getData().getCount());
                        } else {
                            HomeActivity.push(this, "MyMedicate", getResources().getString(R.string.new_bill_from).concat(english),
                                    database.getData().getCount());
                        }
                    }
                    //       Log.d(TAG, "added + notification ");
                }
            }
        }
    }

    private void getDental(String json) throws JSONException {
        Cursor cursor = database.getData();
        boolean found = false;
        //      Log.d(TAG, "CHAKE: ");
        if (!(json == null)) {
            //       Log.d(TAG, "JASON: NOTNULL " + json);
            String arabic, english, francs, ClaimID, DentalClaimAmount,
                    ProductionCode, ClaimDate;
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                found = false;
                JSONObject obj = jsonArray.getJSONObject(i);
                arabic = obj.getString("CompanyArabicName");
                english = obj.getString("CompanyEnglishName");
                francs = obj.getString("CompanyEnglishName");
                ProductionCode = obj.getString("ProductionCode");
                ClaimID = obj.getString("ClaimID");
                DentalClaimAmount = obj.getString("DentalClaimAmount");
                ClaimDate = obj.getString("ClaimDate");
                if (cursor.getCount() > 0) {
                    if (database.Serach("d", ClaimID) > 0) {
                        found = true;
                    }
                }
                if (!found) {
                    database.insertData(arabic, english, francs, ProductionCode, ClaimID, DentalClaimAmount, ClaimDate, "1", "d");
                    if (statics.getPricesIns().equals("true")) {
                        if (SetLocal.getLong(this).equals("ar")) {
                            HomeActivity.push(this, getResources().getString(R.string.new_bill_from).concat(arabic), getResources().getString(R.string.total_bail).concat(DentalClaimAmount),
                                    database.getData().getCount());
                        } else if (SetLocal.getLong(this).equals("fr")) {
                            HomeActivity.push(this, getResources().getString(R.string.new_bill_from).concat(francs), getResources().getString(R.string.total_bail).concat(DentalClaimAmount),
                                    database.getData().getCount());
                        } else {
                            HomeActivity.push(this, getResources().getString(R.string.new_bill_from).concat(english), getResources().getString(R.string.total_bail).concat(DentalClaimAmount),
                                    database.getData().getCount());
                        }
                    } else {
                        if (SetLocal.getLong(this).equals("ar")) {
                            HomeActivity.push(this, "MyMedicate", getResources().getString(R.string.new_bill_from).concat(arabic),
                                    database.getData().getCount());
                        } else if (SetLocal.getLong(this).equals("fr")) {
                            HomeActivity.push(this, "MyMedicate", getResources().getString(R.string.new_bill_from).concat(francs),
                                    database.getData().getCount());
                        } else {
                            HomeActivity.push(this, "MyMedicate", getResources().getString(R.string.new_bill_from).concat(english),
                                    database.getData().getCount());
                        }
                    }
                    Log.d(TAG, "added + notification ");
                }
            }
        }
    }

    private void getJSON(final String urlWebService) {
        {
            class GetJSON_Hospital extends AsyncTask<Void, Void, String> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    try {
                        if (null != s) {
                            if (s.length() > 10) {
                                if (urlWebService.contains(clinics))
                                    getCli(s);
                                else if (urlWebService.contains(phar))
                                    getPhar(s);
                                else if (urlWebService.contains(dental))
                                    getDental(s);
                                else if (urlWebService.contains(ohter))
                                    setOther(s);
                            }
                            else Log.d(TAG, "onPostExecute: ERR > " + urlWebService + " | " + s);
                        }
                    } catch (JSONException e) {
                        //       Log.d("TAG", "onPostExecute4123: " + e.getMessage());
                    }

                }

                @Override
                protected String doInBackground(Void... voids) {
                    try {
                        URL url = new URL(urlWebService);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setConnectTimeout(30000);
                        con.setReadTimeout(30000);
                        StringBuilder sb = new StringBuilder();
                        BufferedReader bufferedReader =
                                new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String json;
                        while ((json = bufferedReader.readLine()) != null) {
                            sb.append(json).append("\n");
                        }
                        return sb.toString().trim();
                    } catch (Exception e) {
                        return null;
                    }
                }
            }
            GetJSON_Hospital getJSON = new GetJSON_Hospital();
            getJSON.execute();
        }


    }

    private void setOther(String json) {
        boolean isNew = false;
        otherNotificationsDatabase = new OtherNotificationsDatabase(this);
        Log.d(TAG, "GET OTHER NOTIFICATIONS : ");
        String body = "",date ="",id ="";
        try {
            if (!(json == null)) {
                JSONArray jsonArray = new JSONArray(json);
                ///   otherNotificationsDatabase.delOldData();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    id = obj.getString("id");
                    body = obj.getString("body");
                    date = obj.getString("date");
                    if (otherNotificationsDatabase.Search(id).getCount() <= 0) {
                        Log.d(TAG, "getOtherNotiADDED" + id);
                       isNew =true;
                        //    other_items.add(new OtherNotificationsItem(body,date,"1"));
                    }
                }
            if (isNew){
                HomeActivity.pushFirebaseNotifications(context, getString(R.string.app_name), body);
                otherNotificationsDatabase.insertData(body, date, id);
            }
            }
        } catch (JSONException e) {
            Log.d(TAG, "setOtherERROR" + e.getMessage());
        }
    }

    private void checkNewChat() {
        //Log.d(TAG, "checkNewChat: START");
        firebaseDatabase = FirebaseDatabase.getInstance();
        chatdatabase = firebaseDatabase.getReference().getRoot();
        if (statics.getID().equals("null")) {
            chatdatabase = chatdatabase.child("MedicateApp").child("Chat").child("visitor".concat(statics.getVisitorNumber()));
        } else {
            chatdatabase = chatdatabase.child("MedicateApp").child("Chat").child("user".concat(statics.getID()));
        }
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    int count = 0;
                    if (snapshot.exists()) {
                        count = ((int) snapshot.getChildrenCount()) - 2;
                        if (count > 0) {
                            if (count > Integer.parseInt(statics.getChatCount())) {
                                // chatCount.newCount(count);

                               Log.d(TAG, "onDataChange: new Count > " + count);
                                if (Integer.parseInt(statics.getReadedChatCount()) >= count) {
                            //        Log.d(TAG, "Readed is beiger");
                                    statics.setChatCount(statics.getReadedChatCount());
                                } else {
                             //       Log.d(TAG, ">> Push chat Notifications ");
                                    if (!statics.getMY_PLACE().equals("المحادثة الفورية")) {
                                        HomeActivity.pushChat(context, context.getString(R.string.min_chat), context.getString(R.string.there_is_new_chat_meg));
                                    } else {
                             //           Log.d(TAG, "stop notification");
                                    }
                                    statics.setChatCount("" + count);
                                }

                            }
                         //   else Log.d(TAG, "No New chat > " + count);
                        } else {
                      //      Log.d(TAG, "Chat Deleted");
                            statics.setChatCount("0");
                            statics.setReadedChatCount("0", "case 12");
                        }

                        //   Log.d(TAG, "chat count : > " + (count));
                    } else {
                        statics.setChatCount("0");
                        statics.setReadedChatCount("0", "case 13");
                    //    Log.d(TAG, "Not Exist: ");
                    }
                } catch (Exception e) {
                    Log.d(TAG, "ERROR 23 > " + e.getMessage());
                    return;
                } finally {
               //     Log.d(TAG, "chat finally count: > " + statics.getChatCount());
               //     Log.d(TAG, "chat finally REaded count: > " + statics.getReadedChatCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: ERROR > " + error.getMessage());
            }
        };
        chatdatabase.addValueEventListener(eventListener);
    }

    private void checkNewChatAskDoctor() {
        Log.d(TAG, "checkNewChatAskDoctor: START");
        chatdatabaseAskDoctor = FirebaseDatabase.getInstance().getReference().getRoot().child("MedicateApp").child("AskDocMedicate").child("Users").child("user".concat(statics.getID()));
        eventListenerAskDoctor = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    int count = 0;
                    if (snapshot.exists()) {
                        count = ((int) snapshot.getChildrenCount()) - 1;
                        if (count > 0) {
                            if (count > Integer.parseInt(statics.getAskDoctorChatCount())) {
                                Log.d(TAG, "onDataChange: new Count > " + count);
                                if (Integer.parseInt(statics.getAskDoctorReadedChatCount()) >= count) {
                                    Log.d(TAG, "Readed is beiger");
                                    statics.setAskDoctorChatCount(statics.getAskDoctorReadedChatCount());
                                } else {
                                    Log.d(TAG, ">> Push chat Notifications ");
                                    if (!statics.getMY_PLACE().equals("طبيب ميديكيت")) {
                                        HomeActivity.pushChatAskDoctor(context, context.getString(R.string.there_is_new_chat_meg));
                                    } else {
                                        Log.d(TAG, "stop notification");
                                    }
                                    statics.setAskDoctorChatCount("" + count);
                                }

                            } else Log.d(TAG, "No New chat > " + count);
                        } else {
                            Log.d(TAG, "Chat Deleted");
                            statics.setAskDoctorReadedChatCount("0");
                            statics.setAskDoctorChatCount("0");
                        }

                        //   Log.d(TAG, "chat count : > " + (count));
                    } else {
                        statics.setAskDoctorReadedChatCount("0");
                        statics.setAskDoctorChatCount("0");
                        Log.d(TAG, "Not Exist: ");
                    }
                } catch (Exception e) {


                    Log.d(TAG, "chat finally count: > " + statics.getAskDoctorChatCount());
                    Log.d(TAG, "chat finally Readed count: > " + statics.getAskDoctorReadedChatCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: ERROR > " + error.getMessage());
            }
        };
        chatdatabaseAskDoctor.addValueEventListener(eventListenerAskDoctor);
    }
}
