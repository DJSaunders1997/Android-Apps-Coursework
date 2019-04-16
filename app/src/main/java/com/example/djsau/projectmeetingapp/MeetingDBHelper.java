package com.example.djsau.projectmeetingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;

public class MeetingDBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "meeting.db";
    public static final String TABLE_NAME = "meeting_table";

    public MeetingDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "create table " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TITLE TEXT,NOTES TEXT, DATE TEXT, TIME TEXT, LATITUDE TEXT, LONGITUDE TEXT, ATTENDEES TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String title, String notes, String date, String time, String latitude, String longitude, String attendees){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLE",title);
        contentValues.put("NOTES",notes);
        contentValues.put("DATE",date);
        contentValues.put("TIME",time);
        contentValues.put("LATITUDE",latitude);
        contentValues.put("LONGITUDE",longitude);
        contentValues.put("ATTENDEES",attendees);

        long result = db.insert(TABLE_NAME,null ,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {    //Cursor is an interface which represents a 2d table. Stores result from a sql statement
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    //return cursor with data from the meeTing with the given id
    public Cursor getDataFromID(int Id) {    //Cursor is an interface which represents a 2d table. Stores result from a sql statement
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where ID=" +Id , null);  //Cursor is an interface which represents a 2d table. Stores result from a sql statement
        return res;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

    public ArrayList<HashMap<String, String>> getMeetingList() {    //get list of meetings that will be shown on MeetingsActivity

        ArrayList<HashMap<String, String>> meetingList = new ArrayList<>();
        Cursor cursor = this.getAllData();      //Cursor = result of query

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> meeting = new HashMap<>();
                meeting.put("id", cursor.getString(cursor.getColumnIndex("ID")));
                meeting.put("title", cursor.getString(cursor.getColumnIndex("TITLE")));
                meeting.put("notes", cursor.getString(cursor.getColumnIndex("NOTES")));
                meetingList.add(meeting);

            } while (cursor.moveToNext());
        }
        return meetingList;
    }

}
