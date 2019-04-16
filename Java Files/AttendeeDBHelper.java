package com.example.djsau.projectmeetingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class AttendeeDBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "attendee.db";
    public static final String TABLE_NAME = "attendee_table";

    public AttendeeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME",name);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //return cursor with data from the attendee with the given id
    public Cursor getDataFromID(int Id) {    //Cursor is an interface which represents a 2d table. Stores result from a sql statement
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where ID=" +Id , null);  //Cursor is an interface which represents a 2d table. Stores result from a sql statement
        return res;
    }

    public Cursor getAllData() {    //Cursor is an interface which represents a 2d table. Stores result from a sql statement
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);  //Cursor is an interface which represents a 2d table. Stores result from a sql statement

        return res;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

    //return a list of the attendees name for use in autocorrect
    public ArrayList<String> getAttendeesNames(){

        ArrayList<String> attendeeList = new ArrayList<>();

        Cursor cursor = this.getAllData();      //Cursor = result of query

        if (cursor.moveToFirst()) {
            do {
                attendeeList.add(cursor.getString(cursor.getColumnIndex("NAME")));
            } while (cursor.moveToNext());
        }
        return attendeeList;
    }

    public ArrayList<HashMap<String, String>> getAttendeeList() { //get list of meetings that will be shown on AttendeesActivity

        ArrayList<HashMap<String, String>> attendeeList = new ArrayList<>();

        Cursor cursor = this.getAllData();      //Cursor = result of query
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> attendee = new HashMap<>();
                attendee.put("id", cursor.getString(cursor.getColumnIndex("ID")));
                attendee.put("name", cursor.getString(cursor.getColumnIndex("NAME")));
                attendeeList.add(attendee);

            } while (cursor.moveToNext());
        }
        return attendeeList;
    }

}
