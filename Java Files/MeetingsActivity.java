package com.example.djsau.projectmeetingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MeetingsActivity extends AppCompatActivity {
    MeetingDBHelper myDB;
    TextView meeting_ID;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        updateTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        fillListView();
    }

    public void onResume() {
        super.onResume();
        fillListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_settings) {
            Intent newIntent = new Intent(this, SettingsActivity.class);
            startActivity(newIntent);
        }
        else if(item.getItemId() == R.id.action_manage_attendees)
        {
            Intent newIntent = new Intent(this, AttendeesActivity.class);
            startActivity(newIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void addMeeting(View view){
        Intent newIntent = new Intent(this, AddMeetingActivity.class);
        startActivity(newIntent);
    }

    public void fillListView(){
        lv = findViewById(R.id.listView);
        myDB = new MeetingDBHelper(this);

        ArrayList<HashMap<String, String>> meetingList = myDB.getMeetingList();
        if (meetingList.size() != 0) {

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    meeting_ID = view.findViewById(R.id.textID);
                    String meetingID = meeting_ID.getText().toString();

                    Intent objIndent = new Intent(getApplicationContext(),ViewMeetingActivity.class);
                    objIndent.putExtra("meeting_Id", Integer.parseInt( meetingID));
                    startActivity(objIndent);
                }
            });

            ListAdapter adapter = new SimpleAdapter(MeetingsActivity.this, meetingList,
                    R.layout.view_meeting_details,

                    new String[]{"id", "title", "notes"},
                    new int[]{R.id.textID, R.id.textTitle, R.id.textNotes});

            lv.setAdapter(adapter);
        }
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
