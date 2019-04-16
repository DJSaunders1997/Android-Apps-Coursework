package com.example.djsau.projectmeetingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    Spinner spinnerFontSize, spinnerTextColour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        updateTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //create ActionBar with a back button
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);

        spinnerFontSize = findViewById(R.id.spinnerFontSize);
        spinnerTextColour = findViewById(R.id.spinnerTextColour);

        //get font size from preferences
        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String fontSizePref = pref.getString("font_size", "Medium");
        String textColourPref = pref.getString("text_colour", "Black");

        //set spinnerFontSize to display correct font size
        for (int i = 0; i < 3; i++) {
            if (spinnerFontSize.getItemAtPosition(i).toString().equals(fontSizePref)) {
                spinnerFontSize.setSelection(i);
            }
        }

        //set spinnerFontSize to display correct text colour
        for (int i = 0; i < 4; i++) {
            if (spinnerTextColour.getItemAtPosition(i).toString().equals(textColourPref)) {
                spinnerTextColour.setSelection(i);
            }
        }
    }

    @Override
    //add functionality to ActionBar
    public boolean onOptionsItemSelected(MenuItem item) {
        //leaving settings page so restart application so settings can take effect
        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void  finish(){
        //leaving settings page so restart application so settings can take effect
        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void SavePreferences(View v) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        String fontSize = spinnerFontSize.getSelectedItem().toString();
        String textColour = spinnerTextColour.getSelectedItem().toString();

        editor.putString("font_size", fontSize);
        editor.putString("text_colour", textColour);
        editor.commit(); // commit changes

        Toast.makeText(SettingsActivity.this, getString(R.string.toast_preference_saved), Toast.LENGTH_LONG).show();

        //restart activity
        finish();
        startActivity(getIntent());
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
