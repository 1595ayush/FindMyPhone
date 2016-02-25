package com.se.findmyphone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper {

    private static final String DATABASE_NAME = "FindMyPhone.sqlite";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_EMER= "emergency";
    private static final String COL_NAME = "name";
    private static final String COL_NO = "number";

    private static final String TABLE_BL= "blacklist";


    private static final String CREATE_TABLE_EMER = "create table "+TABLE_EMER+"("+
            COL_NAME+" text not null, "+
            COL_NO+" text not null)";
    private static final String CREATE_TABLE_BL = "create table "+TABLE_BL+"("+
            COL_NAME+" text not null, "+
            COL_NO+" text not null)";
    private SQLiteDatabase db;
    public DbHelper(Context context) {
        MySqliteHelper helper= new MySqliteHelper(context);
        db=helper.getWritableDatabase();
    }
    public ArrayList<ContactList> getEList()
    {
        Cursor cursor;
        int nameColIndex,noColIndex;
        ArrayList<ContactList> all = new ArrayList<ContactList>();
        cursor = db.query(TABLE_EMER, new String[]{COL_NAME, COL_NO}, null, null, null, null, null);
        nameColIndex = cursor.getColumnIndex(COL_NAME);
        noColIndex = cursor.getColumnIndex(COL_NO);
        if(!cursor.moveToFirst())
            return all;
        cursor.moveToFirst();
        do
        {
            all.add(new ContactList(cursor.getString(nameColIndex),
                    cursor.getString(noColIndex)
            ));
        }while(cursor.moveToNext());
        return all;
    }
    public ArrayList<ContactList> getBList()
    {
        Cursor cursor;
        int nameColIndex,noColIndex;
        ArrayList<ContactList> all = new ArrayList<ContactList>();
        cursor = db.query(TABLE_BL, new String[]{COL_NAME, COL_NO}, null, null, null, null, null);
        nameColIndex = cursor.getColumnIndex(COL_NAME);
        noColIndex = cursor.getColumnIndex(COL_NO);
        if(!cursor.moveToFirst())
            return all;
        cursor.moveToFirst();
        do
        {
            all.add(new ContactList(cursor.getString(nameColIndex),
                    cursor.getString(noColIndex)
            ));
        }while(cursor.moveToNext());
        return all;
    }
    public boolean searchBL(String num){
        Cursor cursor;
        ArrayList<ContactList> al = new ArrayList<ContactList>();
        String selection = COL_NO + " LIKE \"" +num+"\"";
        cursor = db.query(TABLE_BL, new String[]{COL_NAME, COL_NO}, selection, null, null, null, null);
        if(cursor.getCount()==0)
            return true;
        else
            return false;
    }
    public boolean searchEmer(String num){
        Cursor cursor;
        ArrayList<ContactList> al = new ArrayList<ContactList>();
        String selection = COL_NO + " LIKE \"" +num+"\"";
        cursor = db.query(TABLE_EMER, new String[]{COL_NAME, COL_NO}, selection, null, null, null, null);
        if(cursor.getCount()==0)
            return true;
        else
            return false;

    }
    public int insertEmer(String name, String no) {
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_NO, no);
        long rowId=db.insert(TABLE_EMER, null, contentValues);
        if(rowId==-1)
            return 0;
        return 1;
    }
    public int deleteEmer(String name,String no)
    {
        String select = COL_NAME + " like \"" + name +"\" AND "+ COL_NO + " like \"" + no+ "\"";
        db.delete(TABLE_EMER, select, null);
        return 0;
    }
    public int insertBL(String name, String no) {
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_NO, no);
        long rowId=db.insert(TABLE_BL, null, contentValues);
        if(rowId==-1)
            return 0;
        return 1;
    }
    public int deleteBL(String name,String no)
    {
        String select = COL_NAME + " like \"" + name +"\" AND "+ COL_NO + " like \"" + no+ "\"";
        db.delete(TABLE_BL, select, null);
        return 0;
    }
    class MySqliteHelper extends SQLiteOpenHelper {

        public MySqliteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_EMER);
            db.execSQL(CREATE_TABLE_BL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}