package com.pappydevelopers.sarcasmfun.databasehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper{


    public static final String DATABASE_NAME = "FavouriteCaluclation.db";
    public static final String TABLE_NAME = "favourite_table";
    public static final String COL_1 = "sno";
    public static final String COL_2 = "url";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS favourite_table(sno INTEGER PRIMARY KEY AUTOINCREMENT, url BLOB(500))");
      }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertImage(String image_url){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, image_url);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public void deleteImage(String image_url){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_2 + "= '" + image_url + "'", null);
    }

    public Cursor getImage(){
        SQLiteDatabase db = this.getWritableDatabase();
        final Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return c;
    }

    public Cursor getParticularImage(String image_url){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cr1 = db.rawQuery("SELECT * FROM " + TABLE_NAME + " where url = '" + image_url + "'", null);
        return cr1;
    }


}
