package com.medicate_int.mymedicate.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;

import com.medicate_int.mymedicate.database.NotificationsDatabase;
import com.medicate_int.mymedicate.database.OtherNotificationsDatabase;
import com.medicate_int.mymedicate.database.UserDrugsDatabase;

public class CacheHelper extends Application {
    private final Context context;

    private static final String TAG = "CacheHelper";

    public CacheHelper(Context context) {
        this.context = context;

    }


    public String getMY_PLACE() {
        return restorePrefData("place");
    }

    public String getHelthRecordType() {
        return restorePrefData("hrt");
    }

    public String getUserPhoneNumber() {
        return restorePrefData("getUserPhoneNumber");
    }
    public String getUserEmail() {
        return restorePrefData("getUseremail");
    }
    public String getChatCount() {
        return !restorePrefData("chatcount").equals("null") ? restorePrefData("chatcount") : "0";
    }

    public String getReadedChatCount() {
        return !restorePrefData("readedchatcount").equals("null") ? restorePrefData("readedchatcount") : "0";
    }

    public String getAskDoctorChatCount() {
        return !restorePrefData("askchatcount").equals("null") ? restorePrefData("askchatcount") : "0";
    }

    public String getAskDoctorReadedChatCount() {
        return !restorePrefData("askreadedchatcount").equals("null") ? restorePrefData("askreadedchatcount") : "0";
    }

    public String getPnoneNummber() {
        return restorePrefData("medicate_phone").equals("null") ? Statics.PNONE_NUMMBER : restorePrefData("medicate_phone");
    }

    public void setPnoneNummber(String pnoneNummber) {
        savePrefsData("medicate_phone", pnoneNummber);
        Statics.PNONE_NUMMBER = pnoneNummber;
    }

    public void setChatCount(String pnoneNummber) {
        savePrefsData("chatcount", pnoneNummber);
    }

    public void setAskDoctorChatCount(String pnoneNummber) {
        savePrefsData("askchatcount", pnoneNummber);
    }

    public void setHelthRecordType(String type) {
        savePrefsData("hrt", type);
    }

    public void setReadedChatCount(String count, String from) {
        Log.d(TAG, "setReadedChatCount: > " + count + "   >>>>> " + from);
        savePrefsData("readedchatcount", count);
    }

    public void setAskDoctorReadedChatCount(String count) {
        savePrefsData("askreadedchatcount", count);
    }

    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "1.9.0";
        }
    }

    public String getUserType() {
        return restorePrefData("user_type");
    }

    public String getPricesIns() {
        return restorePrefData("prices");
    }

    public String getUpdateCheck() {
        return restorePrefData("update");
    }

    public void setChatState(String MY_PLACE) {
        savePrefsData("chat_state", MY_PLACE);
        // ok - null
    }

    public void setUpdateCheck(String txt) {
        savePrefsData("update", txt);
        // ok - null
    }

    public void setPricesIns(String txt) {
        savePrefsData("prices", txt);
        // ok - null
    }

    public void setMY_PLACE(String MY_PLACE) {
        savePrefsData("place", MY_PLACE);
    }

    public void setitemCount(String itemCount) {
        savePrefsData("count", itemCount);
    }
    public String gettitemCount() {
        return restorePrefData("count");
    }


    public void setUserName(String MY_PLACE) {
        savePrefsData("user_name", MY_PLACE);
    }

    public void setUserPhone(String MY_PLACE) {
        savePrefsData("user_phone", MY_PLACE);
    }

    public String getMY_NETWORK() {
        return restorePrefData("network");
    }

    public String getChatState() {
        return restorePrefData("chat_state");
    }

    public String getTxt() {
        return restorePrefData("txt");
    }

    public String getUserName() {
        return restorePrefData("user_name");
    }

    public String getPhone() {
        return restorePrefData("user_phone");
    }

    public String getCardNumber() {
        return restorePrefData("card_number");
    }

    public void setMY_NETWORK(String MY_NETWORK) {
        savePrefsData("network", MY_NETWORK);
    }

    public void setID(String id) {
        savePrefsData("BeneficiaryID", id);
    }

    public void setTxt(String id) {
        savePrefsData("txt", id);
    }

    public void setUserPhoneNumber(String number) {
        savePrefsData("getUserPhoneNumber", number);
    }
    public void setUserEmail(String number) {
        savePrefsData("getUseremail", number);
    }

    public void setCon(String con) {
        savePrefsData("con", con);
    }

    public void setOldUserID(String con) {
        savePrefsData("olduserID", con);
    }

    public String getOldUserID() {
        return restorePrefData("olduserID");
    }

    public void setOldUserCardNumber(String con) {
        savePrefsData("olduserCardNumber", con);
    }

    public String getOldUserCardNumber() {
        return restorePrefData("olduserCardNumber");
    }

    public void setOldUserName(String con) {
        savePrefsData("olduserName", con);
    }

    public String getOldUserPass() {
        return restorePrefData("olduserPass");
    }

    public void setOldUserPass(String con) {
        savePrefsData("olduserPass", con);
    }

    public String getOldUserName() {
        return restorePrefData("olduserName");
    }

    public void setOldUserType(String con) {
        savePrefsData("olduserType", con);
    }

    public String getOldUserType() {
        return restorePrefData("olduserType");
    }

    public String getUserGender() {
        return restorePrefData("user_gender");
    }

    public String getCoName() {
        return restorePrefData("CoName");
    }
    public String getComID() {
        return restorePrefData("ComID");
    }

    public void setCoName(String CoName) {savePrefsData("CoName", CoName);}
    public void setComID(String ComID) {savePrefsData("ComID", ComID);}

    public void setIntro(String con) {
        savePrefsData("intro", con);
    }

    public void setCardNumber(String con) {
        savePrefsData("card_number", con);
    }

    public String getCon() {
        return restorePrefData("con");
    }

    public void Logout() {
        new NotificationsDatabase(context).delOldData();
        new UserDrugsDatabase(context).delOldData();
        new OtherNotificationsDatabase(context).delOldData();
        setOldUserID(getID());
        setOldUserCardNumber(getCardNumber());
        setOldUserName(getUserName());
        setOldUserType(getUserType());
        setID("null");
//        stopService(new Intent(context.getApplicationContext(),NotificationsService.class));
        setChatCount("0");
        setReadedChatCount("0", "status");
        setCardNumber("null");
        setTxt("nul");
        setCoName("nul");
        setComID("nul");
        setVisitorNumber("null");
        setChatState("null");
        UserBlodType("null");
        UserDrink("null");
        UserHight("null");
        UserSmoke("null");
        UserState("null");

    }



    public String getIntro() {
        return restorePrefData("intro");
    }

    public String getMY_CONTRY() {
        return restorePrefData("country");
    }

    public String getID() {
        return restorePrefData("BeneficiaryID");
    }

    public void setMY_CONTRY(String MY_CONTRY) {
        savePrefsData("country", MY_CONTRY);
    }


    public String restorePrefData(String s) {
        SharedPreferences pref = context.getSharedPreferences("Settings", MODE_PRIVATE);
        return pref.getString(s, "null");
    }


    public void savePrefsData(String s, String v) {
        SharedPreferences pref = context.getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(s, v);
        editor.apply();
    }

    public void Remove(String txr) {
        SharedPreferences pref = context.getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(txr);
        editor.apply();
    }

    public void setVisitorNumber(String type) {
        savePrefsData("VisitorNumber", type);
    }

    public void setUserType(String type) {
        savePrefsData("user_type", type);
    }

    public void setUserGender(String type) {
        savePrefsData("user_gender", type);
    }


    public void UserBlodType(String type) {
        savePrefsData("UserBlodType", type);
    }

    public void UserHight(String type) {
        savePrefsData("UserHight", type);
    }

    public void UserState(String type) {
        savePrefsData("UserState", type);
    }

    public void UserSmoke(String type) {
        savePrefsData("UserSmoke", type);
    }

    public void UserDrink(String type) {
        savePrefsData("UserDrink", type);
    }

    public void UserWight(String type) {
        savePrefsData("UserWight", type);
    }

    public String getUserBlodType() {
        return restorePrefData("UserBlodType");
    }

    public String getUserHight() {
        return restorePrefData("UserHight");
    }

    public String getUserWight() {
        return restorePrefData("UserWight");
    }

    public String getUserState() {
        return restorePrefData("UserState");
    }

    public String getUserSmoke() {
        return restorePrefData("UserSmoke");
    }

    public String getUserDrink() {
        return restorePrefData("UserDrink");
    }

    public String getVisitorNumber() {
        return restorePrefData("VisitorNumber");
    }

    public void setOldtoNew() {
        setUserName(getOldUserName());
        setUserType(getOldUserType());
        setCardNumber(getOldUserCardNumber());
        setID(getOldUserID());
    }


}
