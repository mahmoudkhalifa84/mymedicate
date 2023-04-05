package com.medicate_int.mymedicate.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.medicate_int.mymedicate.module.Statics;

public class UserDrugsDatabase  extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "data.db";
    public static String DRUGS_TABLE = "user_drugs";
    public static String NAME = "name";
    public static String numday = "numday";
    public static String inday = "inday";
    public static String numinday = "numinday";
    private static final String SQL_DRUGS_TABLE =
            "CREATE TABLE IF NOT EXISTS user_drugs ( id INTEGER PRIMARY KEY AUTOINCREMENT , name TEXT , " +
                    "numday TEXT , inday TEXT , numinday TEXT)";
    private static final String SQL_DELETE_OFFERS =
            "DROP TABLE IF EXISTS " + DRUGS_TABLE;
    public UserDrugsDatabase(Context context) {
        super(context, DATABASE_NAME, null, Statics.DATABASE_VERSION);
        Create();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_DRUGS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user_drugs");
        onCreate(db);
    }
    public void Create(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(SQL_DRUGS_TABLE);
    }
    public void delOldData(){
        SQLiteDatabase mydb = this.getWritableDatabase();
        mydb.delete(DRUGS_TABLE,null ,null);
    }
    public Cursor getData(){
        SQLiteDatabase db =  this.getReadableDatabase();
        return db.rawQuery("Select * from " + DRUGS_TABLE, null);
    }
    public boolean insertData(String enter_name,String enter_numday,String enter_inday,String enter_numinday){
        long resa = -1;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(NAME, enter_name);
            contentValues.put(numday, enter_numday);
            contentValues.put(inday, enter_inday);
            contentValues.put(numinday, enter_numinday);
            resa = db.insert(DRUGS_TABLE, null, contentValues);
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
