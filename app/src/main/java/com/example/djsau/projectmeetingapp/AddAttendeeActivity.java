package com.example.djsau.projectmeetingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddAttendeeActivity extends AppCompatActivity {
    AttendeeDBHelper myDB;
    EditText editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        updateTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attendee);

        //create ActionBar with a back button
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);

        myDB = new AttendeeDBHelper(this);
        editName = findViewById(R.id.editTextName);

        updateEditTextColor();
    }

    @Override
    //add functionality to ActionBar
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void AddData(View v) {
        boolean isInserted = myDB.insertData(editName.getText().toString());
        if (isInserted == true)
            Toast.makeText(AddAttendeeActivity.this, getString(R.string.toast_data_inserted), Toast.LENGTH_LONG).show();
        else
            Toast.makeText(AddAttendeeActivity.this, getString(R.string.toast_data_not_inserted), Toast.LENGTH_LONG).show();
        finish();
    }

    public void updateEditTextColor() {  //edittexts colour will not be updated with updateTheme so need to change them manually
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

    public void updateTheme() {    //change font size and text colour

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
