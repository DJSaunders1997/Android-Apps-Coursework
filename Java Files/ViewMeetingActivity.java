package com.example.djsau.projectmeetingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ViewMeetingActivity extends AppCompatActivity {
    MeetingDBHelper myDB;
    EditText editTitle, editNotes, editDate, editTime, editLocation, editAttendees;
    int _Meeting_Id;
    Location meetingLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        updateTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meeting);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);

        meetingLocation = new Location("");

        myDB = new MeetingDBHelper(this);
        editTitle = findViewById(R.id.editTextTitle);
        editNotes = findViewById(R.id.editTextNotes);
        editDate = findViewById(R.id.editTextDate);
        editTime = findViewById(R.id.editTextTime);
        editLocation = findViewById(R.id.editTextLocation);
        editAttendees = findViewById(R.id.editTextAttendees);

        updateEditTextColor();

        String title = "";
        String notes = "";
        String date = "";
        String time = "";
        String location = "";
        String attendees = "";
        String longitude = "";
        String latitude = "";

        //when listview selects a booking
        //get value of _Meeting_Id from previous activity
        Intent intent = getIntent();
        _Meeting_Id = 1;
        _Meeting_Id = intent.getIntExtra("meeting_Id", 0);
        //Fill cursor with data using _Meeting_Id
        Cursor cursor = myDB.getDataFromID(_Meeting_Id);

        if (cursor.moveToFirst()) {
            do {
                title = cursor.getString(cursor.getColumnIndex("TITLE"));
                notes = cursor.getString(cursor.getColumnIndex("NOTES"));
                date = cursor.getString(cursor.getColumnIndex("DATE"));
                time = cursor.getString(cursor.getColumnIndex("TIME"));
                latitude = cursor.getString(cursor.getColumnIndex("LATITUDE"));
                longitude = cursor.getString(cursor.getColumnIndex("LONGITUDE"));
                attendees = cursor.getString(cursor.getColumnIndex("ATTENDEES"));
            } while (cursor.moveToNext());
        }

        //if there is a location
        if (!(latitude.equals("0.0") && longitude.equals("0.0"))) {
            meetingLocation.setLatitude(Double.parseDouble(latitude));
            meetingLocation.setLongitude(Double.parseDouble(longitude));
            try {
                location = getAddressFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //show data
        editTitle.setText(title);
        editNotes.setText(notes);
        editDate.setText(date);
        editTime.setText(time);
        editLocation.setText(location);
        editAttendees.setText(attendees);
    }

    @Override
    //add functionality to ActionBar
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void DeleteData(View v) {
        Integer deletedRows = myDB.deleteData(Integer.toString(_Meeting_Id));
        if (deletedRows > 0)
            Toast.makeText(ViewMeetingActivity.this, getString(R.string.toast_data_deleted), Toast.LENGTH_LONG).show();
        else
            Toast.makeText(ViewMeetingActivity.this, getString(R.string.toast_data_not_deleted), Toast.LENGTH_LONG).show();

        finish();
    }

    public void AddLocation(View view) {
        Intent intent = new Intent(this, MapsActivity.class);

        intent.putExtra("latitude", meetingLocation.getLatitude());
        intent.putExtra("longitude", meetingLocation.getLongitude());
        intent.putExtra("viewing", true);
        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == 10) {

            Double latitude = data.getExtras().getDouble("latitude");
            Double longitude = data.getExtras().getDouble("longitude");

            meetingLocation.setLatitude(latitude);
            meetingLocation.setLongitude(longitude);

            try {
                String address = getAddressFromLocation(latitude, longitude);
                editLocation.setText(getAddressFromLocation(latitude, longitude));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getAddressFromLocation(double latitude, double longitude) throws IOException {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        addresses = geocoder.getFromLocation(latitude, longitude, 1);
        String address = addresses.get(0).getAddressLine(0);

        return address;
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

        editTitle.setTextColor(ContextCompat.getColor(getApplicationContext(), colourRef));
        editNotes.setTextColor(ContextCompat.getColor(this, colourRef));
        editDate.setTextColor(ContextCompat.getColor(this, colourRef));
        editTime.setTextColor(ContextCompat.getColor(this, colourRef));
        editLocation.setTextColor(ContextCompat.getColor(this, colourRef));
        editAttendees.setTextColor(ContextCompat.getColor(this, colourRef));
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
