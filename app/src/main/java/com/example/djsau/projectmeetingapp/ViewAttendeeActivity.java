package com.example.djsau.projectmeetingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ViewAttendeeActivity extends AppCompatActivity {
    AttendeeDBHelper myDB;
    EditText editName;
    int _Attendee_Id;

    //@SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        updateTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendee);

        //create ActionBar with a back button
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);

        myDB = new AttendeeDBHelper(this);
        editName = findViewById(R.id.editTextName);
        updateEditTextColor();

        //when listview selects a attendee
        //get value of _Attendee_Id from previous activity
        Intent intent = getIntent();
        _Attendee_Id =0;
        _Attendee_Id = intent.getIntExtra("attendee_Id", 0);

        //Fill cursor with data using _Meeting_Id
        Cursor cursor = myDB.getDataFromID(_Attendee_Id);

        String name = "";

        if (cursor.moveToFirst()) {
            do {
                name =cursor.getString(cursor.getColumnIndex("NAME"));
            } while (cursor.moveToNext());
        }

        //show data
        editName.setText(name);
    }

    @Override
    //add functionality to ActionBar
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void DeleteData(View v) {
        Integer deletedRows = myDB.deleteData(Integer.toString(_Attendee_Id));
        if(deletedRows > 0)
            Toast.makeText(ViewAttendeeActivity.this,getString(R.string.toast_data_deleted), Toast.LENGTH_LONG).show();
        else
            Toast.makeText(ViewAttendeeActivity.this,getString(R.string.toast_data_not_deleted), Toast.LENGTH_LONG).show();

        finish();
    }

    public void updateEditTextColor(){  //edittexts colour will not be updated with updateTheme so need to change them manually
        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String textColourPref = pref.getString("text_colour", "Black");

        //set colourRef to the numerical reference of color Black, Red, Green, or Blue using switch statement
        int colourRef = 0;
        switch (textColourPref) {
            case "Black":
                colourRef = R.color.Black;
                break;
            case "Red":
                colourRef = R.color.Red;
                break;
            case "Green":
                colourRef = R.color.Green;
                break;
            case "Blue":
                colourRef = R.color.Blue;
                break;
        }

        editName.setTextColor(ContextCompat.getColor(this, colourRef));
    }

    public void updateTheme(){    //change font size and text colour

        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String fontSizePref = pref.getString("font_size", "Medium");
        String textColourPref = pref.getString("text_colour", "Black");

        //set fontSizeRef to the numerical reference of Small, Medium, or Large using switch statement
        int fontSizeRef = 0;
        switch (fontSizePref) {
            case "Small":
                fontSizeRef = R.style.FontSizeSmall;
                break;
            case "Medium":
                fontSizeRef = R.style.FontSizeMedium;
                break;
            case "Large":
                fontSizeRef = R.style.FontSizeLarge;
                break;
        }

        //set textColourRef to the numerical reference of Black, Red, Green, or Blue using switch statement
        int textColourRef = 0;
        switch (textColourPref) {
            case "Black":
                textColourRef = R.style.TextColorBlack;
                break;
            case "Red":
                textColourRef = R.style.TextColorRed;
                break;
            case "Green":
                textColourRef = R.style.TextColorGreen;
                break;
            case "Blue":
                textColourRef = R.style.TextColorBlue;
                break;
        }

        setTheme(fontSizeRef);
        setTheme(textColourRef);
    }
}
