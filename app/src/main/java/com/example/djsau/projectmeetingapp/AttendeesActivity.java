package com.example.djsau.projectmeetingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class AttendeesActivity extends AppCompatActivity {
    AttendeeDBHelper myDB;
    TextView attendee_ID;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        updateTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendees);

        //create ActionBar with a back button
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);

        fillListView();
    }

    @Override
    //add functionality to ActionBar
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void onResume() {
        super.onResume();
        fillListView();
    }

    public void addAttendee(View view) {
        Intent newIntent = new Intent(this, AddAttendeeActivity.class);
        startActivity(newIntent);
    }

    //fills listview with data and sets up listeners
    public void fillListView() {
        lv = findViewById(R.id.listView);
        myDB = new AttendeeDBHelper(this);

        ArrayList<HashMap<String, String>> attendeeList = myDB.getAttendeeList();
        if (attendeeList.size() != 0) {

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    attendee_ID = view.findViewById(R.id.textID);
                    String attendeeID = attendee_ID.getText().toString();

                    Intent objIntent = new Intent(getApplicationContext(), ViewAttendeeActivity.class);
                    objIntent.putExtra("attendee_Id", Integer.parseInt(attendeeID));
                    startActivity(objIntent);
                }
            });

            ListAdapter adapter = new SimpleAdapter(AttendeesActivity.this, attendeeList,
                    R.layout.view_attendee_details,
                    new String[]{"name", "id"},
                    new int[]{R.id.textName, R.id.textID});

            lv.setAdapter(adapter);
        }
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
