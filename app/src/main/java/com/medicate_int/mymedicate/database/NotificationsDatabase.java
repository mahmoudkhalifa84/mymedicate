package com.medicate_int.mymedicate.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.module.Statics;

public class NotificationsDatabase extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "data.db";
    public static String NOTIFICATIONS_TABLE = "NOTI";
    public static String CompanyArabicName = "company_arabic_name";
    public static String CompanyEnglishName = "company_english_name";
    public static String CompanyFrenshName = "company_frensh_name";
    public static String ProductionCode = "production_code";
    public static String ClaimID = "claim_id";
    public static String ClinicClaimAmount = "clinic_claim_amount";
    public static String ClaimDate = "claim_date";
    public static String State = "state";
    public static String Type = "type";
    Context context;
    private static final String SQL_NOTIFICATIONS_TABLE =
            "CREATE TABLE IF NOT EXISTS NOTI ( id INTEGER PRIMARY KEY AUTOINCREMENT , company_arabic_name TEXT , " +
                    "company_english_name TEXT , company_frensh_name TEXT , production_code TEXT , claim_id TEXT ," +
                    "clinic_claim_amount TEXT , claim_date TEXT , state TEXT , type TEXT )";

    CacheHelper statics;
    public NotificationsDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, Statics.DATABASE_VERSION);
        this.context =  context;
        Create();
        statics = new CacheHelper(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_NOTIFICATIONS_TABLE);
    }
    public void Create(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(SQL_NOTIFICATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS NOTI");
        onCreate(db);
    }
    public void delOldData(){
        SQLiteDatabase mydb = this.getWritableDatabase();
        mydb.delete("NOTI",null ,null);
    }
    public Cursor getData(){
        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor res = db.rawQuery("Select * from NOTI",null);
        return res;
    }
    public void SetStateToOld(String type, String claim_id){
        SQLiteDatabase db =  this.getReadableDatabase();
        // 0 old
        // 1 new
        /*ContentValues cv = new ContentValues();
        cv.put("state","0");
       ;*/
     //   int  i = db.update("NOTI",cv,null,null);
        db.execSQL(("UPDATE NOTI SET state = '0' WHERE type = '" + type + "' and claim_id = '" + claim_id + "'"));


    }

    public int Serach(String type , String claim_id){
        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor res = db.rawQuery("Select * from NOTI where type = '" + type + "' and claim_id = '" + claim_id + "' ",null);
        return res.getCount();
    }
    public int getNewCount(){
        if (statics.getID().equals("null"))
            return 0;
        try{
            SQLiteDatabase db =  this.getReadableDatabase();
            Cursor res = db.rawQuery("Select * from NOTI where state = '1'",null);
            return res.getCount();
        }catch (Exception e){
            return 0;
        }

    }
    public boolean insertData(String t1,String t2,String t3,String t4,String t5,String t6 ,String t7,String t8,String t9){
        long resa = -1;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(CompanyArabicName, t1);
            contentValues.put(CompanyEnglishName, t2);
            contentValues.put(CompanyFrenshName, t3);
            contentValues.put(ProductionCode, t4);
            contentValues.put(ClaimID, t5);
            contentValues.put(ClinicClaimAmount, t6);
            contentValues.put(ClaimDate, t7);
            contentValues.put(State, t8);
            contentValues.put(Type, t9);/// c - p - d
            resa = db.insert("NOTI", null, contentValues);
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
