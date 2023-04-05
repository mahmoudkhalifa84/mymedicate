package com.medicate_int.mymedicate.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.medicate_int.mymedicate.module.Statics;

public class OffersDatabase extends SQLiteOpenHelper {
    private final Context context;
    public String CUR_TABLE;
    SQLiteDatabase mydb;
    public static String DATABASE_NAME = "data.db";
    public static String OFFERS_TABLE = "OFFERS";
    public static String NETWORK_TABLE = "NETWORK";
    public static String COUNTRY_TABLE = "COUNTRY";
    public static String CITY_TABLE = "CITY";
    public static String OFFERS_1_TITLE_AR = "title_ar";
    public static String OFFERS_2_TITLE_EN = "title_en";
    public static String OFFERS_3_TITLE_FR = "title_fr";
    public static String OFFERS_4_COUNT_AR = "cont_ar";
    public static String OFFERS_5_COUNT_EN = "cont_en";
    public static String OFFERS_6_COUNT_FR = "cont_fr";
    public static String OFFERS_7_DATE = "date";
    public static String OFFERS_8_IMG = "img";
    public static String OFFERS_9_LAT = "lat";
    public static String OFFERS_10_LOG = "log";
    private static final String SQL_OFFERS_TABLE =
            "CREATE TABLE IF NOT EXISTS OFFERS ( id INTEGER PRIMARY KEY AUTOINCREMENT , title_ar TEXT , " +
                    "title_en TEXT , title_fr TEXT , cont_ar TEXT , cont_en TEXT ," +
                    "cont_fr TEXT , date TEXT , img TEXT , lat TEXT, log TEXT)";
    private static final String SQL_DELETE_OFFERS =
            "DROP TABLE IF EXISTS " + OFFERS_TABLE;
    public OffersDatabase(Context context) {
        super(context, DATABASE_NAME, null, Statics.DATABASE_VERSION);
        this.context = context;
        Create();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_OFFERS_TABLE);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS OFFERS");
        onCreate(db);
    }
    public void Create(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(SQL_OFFERS_TABLE);
    }
    public void delOldData(){
        SQLiteDatabase mydb = this.getWritableDatabase();
         mydb.delete(OFFERS_TABLE,null ,null);
    }
    public Cursor getData(){
        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor res = db.rawQuery("Select * from " + OFFERS_TABLE,null);
        return res;
    }
    public boolean insertData(String t1,String t2,String t3,String t4,String t5,String t6,String t7,String t8,String lat,String log){
        long resa = -1;
        try {
           SQLiteDatabase db = this.getWritableDatabase();
           ContentValues contentValues = new ContentValues();
           contentValues.put(OFFERS_1_TITLE_AR, t1);
           contentValues.put(OFFERS_2_TITLE_EN, t2);
           contentValues.put(OFFERS_3_TITLE_FR, t3);
           contentValues.put(OFFERS_4_COUNT_AR, t4);
           contentValues.put(OFFERS_5_COUNT_EN, t5);
           contentValues.put(OFFERS_6_COUNT_FR, t6);
           contentValues.put(OFFERS_7_DATE, t7);
           contentValues.put(OFFERS_8_IMG, t8);
           contentValues.put(OFFERS_9_LAT, lat);
           contentValues.put(OFFERS_10_LOG, log);
           resa = db.insert(OFFERS_TABLE, null, contentValues);
           db.close();
        } catch (SQLException e){
            return false;
        }
            if (resa == -1)
                return false;
            else
                return true;
    }
}
