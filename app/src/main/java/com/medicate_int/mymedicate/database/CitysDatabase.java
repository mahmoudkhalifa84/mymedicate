package com.medicate_int.mymedicate.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.medicate_int.mymedicate.module.Statics;

public class CitysDatabase extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "data.db";
    public static String CITYS_TABLE = "CITYS";
    public static String _1_CityName = "CityName";
    public static String _2_CityID = "CityID";
    public static String _3_CountryID = "CountryID";
    private static final String SQL_CITYS_TABLE =
            "CREATE TABLE IF NOT EXISTS CITYS ( id INTEGER PRIMARY KEY AUTOINCREMENT , CityName varchar(25) , " +
                    "CityID varchar(10) , CountryID varchar(10) )";
    public CitysDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, Statics.DATABASE_VERSION);
        Create();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CITYS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CITYS");
        onCreate(db);
    }
    public void Create(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(SQL_CITYS_TABLE);
    }
    public void delOldData(){
        SQLiteDatabase mydb = this.getWritableDatabase();
        mydb.delete(CITYS_TABLE,null ,null);
    }
    public Cursor getData(){
        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor res = db.rawQuery("Select * from " + CITYS_TABLE,null);
        return res;
    }


    public boolean insertData(String CityName,String CityID,String CountryID){
        long resa = -1;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(_1_CityName, CityName);
            contentValues.put(_2_CityID, CityID);
            contentValues.put(_3_CountryID, CountryID);
            resa = db.insert(CITYS_TABLE, null, contentValues);
            db.close();
        } catch (SQLException e){
            return false;
        }
        if (resa == -1)
            return false;
        else{
            return true;
    }}
}
