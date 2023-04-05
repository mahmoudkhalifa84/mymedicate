package com.medicate_int.mymedicate.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.medicate_int.mymedicate.module.Statics;

public class NetworkDatabase extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "data.db";
    public static String NETWORK_TABLE = "my_networks";
    public static String CompanyArabicName = "CompanyArabicName".toLowerCase();
    public static String CompanyEnglishName = "CompanyEnglishName".toLowerCase();
    public static String CompanyFrenshName = "CompanyFrenshName".toLowerCase();
    public static String GooGleMaps = "GooGleMaps".toLowerCase();
    public static String CompanyID = "CompanyID".toLowerCase();
    public static String CityID = "CityID".toLowerCase();
    public static String CompanyType = "CompanyType".toLowerCase();
    public static String Image = "image".toLowerCase();
    public static String CityName = "CityName".toLowerCase();
    private static final String SQL_NETWORK_TABLE =
            "CREATE TABLE IF NOT EXISTS my_networks ( id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                    " companyarabicName TEXT ," +
                    " companyenglishname TEXT , " +
                    "companyfrenshname TEXT ," +
                    "googlemaps TEXT ," +
                    "companyid TEXT ," +
                    "cityid TEXT ," +
                    "companytype TEXT ," +
                    "lat TEXT ," +
                    "log TEXT ," +
                    "email TEXT ," +
                    "phone TEXT ," +
                    "address TEXT ," +
                    "cityname TEXT ," +
                    "image TEXT , countryid TEXT )";

    public NetworkDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, Statics.DATABASE_VERSION);
        Create();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_NETWORK_TABLE);
    }

    public void Create() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(SQL_NETWORK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS my_networks");
        onCreate(db);
    }

    public void deleteOldClinics() {
        SQLiteDatabase mydb = this.getWritableDatabase();
        mydb.execSQL("DELETE FROM my_networks WHERE companytype = 1 or companytype = 7 or companytype = 8");
    }

    public void deleteOldPharmacies() {
        SQLiteDatabase mydb = this.getWritableDatabase();
        mydb.execSQL("DELETE FROM my_networks WHERE companytype = 2");
    }

    public void deleteOldLaps() {
        SQLiteDatabase mydb = this.getWritableDatabase();
        mydb.execSQL("DELETE FROM my_networks WHERE companytype = 9  or companytype = 10");
    }
    public void deleteOldVisicalAndGym() {
        SQLiteDatabase mydb = this.getWritableDatabase();
        mydb.execSQL("DELETE FROM my_networks WHERE  companytype = 11  or companytype = 12");
    }
    public void deleteOldHearingAndSeying() {
        SQLiteDatabase mydb = this.getWritableDatabase();
        mydb.execSQL("DELETE FROM my_networks WHERE  companytype = 14  or companytype = 15");
    }


    public Cursor getAllClinics() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select * from my_networks WHERE companytype = 1 or companytype = 7 or companytype = 8 or companytype = 6", null);
        return res;
    }

    public Cursor getAllPharmacies() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select * from my_networks WHERE companytype = 2", null);
        return res;
    }
    public Cursor getAllLaps() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select * from my_networks WHERE companytype = 9  or companytype = 10", null);
        return res;
    }
    public Cursor getAllVisicalAndGym() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select * from my_networks WHERE companytype = 11  or companytype = 12", null);
        return res;
    }
    public Cursor getAllHearingAndSeying() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select * from my_networks WHERE companytype = 14  or companytype = 15", null);
        return res;
    }

    /*   public String getArabicname(String id){
           SQLiteDatabase db =  this.getReadableDatabase();
           Cursor res = db.rawQuery("Select * from my_networks",null);
           while (res.moveToNext()){
               if (res.getString(5).trim().equals(id))
                   return res.getString(5).trim();
           }
           return "------";
       }*/
    public boolean insertData(String arabic_name, String english_name, String france_name,
                              String companyId, String cityID, String companyType, String lat,
                              String log, String email, String phonr, String address,
                              String cityName, String image,String countryId) {
        long resa = -1;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(CompanyArabicName, arabic_name);
            contentValues.put(CompanyEnglishName, english_name);
            contentValues.put(CompanyFrenshName, france_name);
            contentValues.put(GooGleMaps, "nothing");
            contentValues.put(CompanyID, companyId);
            contentValues.put(CityID, cityID);
            contentValues.put(CompanyType, companyType);
            contentValues.put("lat", lat);
            contentValues.put("log", log);
            contentValues.put("email", email);
            contentValues.put("phone", phonr);
            contentValues.put("address", address);
            contentValues.put(CityName, cityName);
            contentValues.put(Image, image);
            contentValues.put("countryid", countryId);
            resa = db.insert(NETWORK_TABLE, null, contentValues);
            db.close();
        } catch (SQLException e) {
            return false;
        }
        return resa != -1;
    }
}
