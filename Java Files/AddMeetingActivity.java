package com.example.djsau.projectmeetingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddMeetingActivity extends AppCompatActivity {
    MeetingDBHelper myDB;
    AttendeeDBHelper myAttendeeDB;
    EditText editTitle,editNotes,editDate,editTime,editLocation,editAttendees;
    AutoCompleteTextView addAttendee;

    Location meetingLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        updateTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

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
        addAttendee = findViewById(R.id.editTextAttendee);  //autocomplete
        addAttendee.setThreshold(1);//will start autocorrect from first character

        updateEditTextColor();
        setupAutocomplete();
    }

    @Override
    //add functionality to ActionBar
    public boolean onOptionsItemSelected(MenuItem item) {
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void AddAttendee(View v) {

        String newAttendee = addAttendee.getText().toString();
        String currentAttendees = editAttendees.getText().toString();

        //access attendee database
        myAttendeeDB = new AttendeeDBHelper(this);
        //get all the attendees names and put them into ArrayList attendeeList
        ArrayList<String> attendeeList ;
        attendeeList = myAttendeeDB.getAttendeesNames();

        //append the new attendee to the current attendees
        //if there are no current attendees then just set the currentAttendees to be the newAttendee
        if (currentAttendees.equals("")) {
            editAttendees.setText(newAttendee);
        }
        else {
            editAttendees.setText(currentAttendees + ", " + newAttendee);
        }

        //if newAttendee is in attendeeList
        //  do nothing
        //else add newAttendee to database
        if(attendeeList.contains(newAttendee)) {
            Toast.makeText(AddMeetingActivity.this,R.string.toast_attendee_added, Toast.LENGTH_LONG).show();
        }
        else {
            boolean isInserted = myAttendeeDB.insertData(newAttendee);
            Toast.makeText(AddMeetingActivity.this,R.string.toast_data_attendee_added_database, Toast.LENGTH_LONG).show();
            setupAutocomplete();
        }
        addAttendee.setText("");
    }

    public void setupAutocomplete(){
        //set up autocomplete for attendee names
        //access attendee database
        myAttendeeDB = new AttendeeDBHelper(this);
        //get all the attendees names and put them into ArrayList attendeeList
        ArrayList<String> attendeeList ;
        attendeeList = myAttendeeDB.getAttendeesNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, attendeeList);
        addAttendee.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
    }

    public void AddLocation(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        //intent.putExtra("viewing", false);
        intent.putExtra("latitude", meetingLocation.getLatitude());
        intent.putExtra("longitude", meetingLocation.getLongitude());
        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == 10){

            Double latitude = data.getExtras().getDouble("latitude");
            Double longitude = data.getExtras().getDouble("longitude");

            meetingLocation.setLatitude(latitude);
            meetingLocation.setLongitude(longitude);

            try {
                String address = getAddressFromLocation(latitude, longitude);
                editLocation.setText(getAddressFromLocation(latitude,longitude));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private String getAddressFromLocation(double latitude, double longitude) throws IOException {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        addresses = geocoder.getFromLocation(latitude, longitude,1);
        String address = addresses.get(0).getAddressLine(0);

        return address;
    }

    public void AddData(View v) {

        String latitude = Double.toString(meetingLocation.getLatitude());
        String longitude = Double.toString(meetingLocation.getLongitude());

        boolean isInserted = myDB.insertData(
                editTitle.getText().toString(),
                editNotes.getText().toString(),
                editDate.getText().toString(),
                editTime.getText().toString(),
                latitude,
                longitude,
                editAttendees.getText().toString()
        );

        if (isInserted == true)
            Toast.makeText(AddMeetingActivity.this,getString(R.string.toast_data_inserted), Toast.LENGTH_LONG).show();
        else
            Toast.makeText(AddMeetingActivity.this,getString(R.string.toast_data_not_inserted), Toast.LENGTH_LONG).show();

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

        editTitle.setTextColor(ContextCompat.getColor(this, colourRef));
        editNotes.setTextColor(ContextCompat.getColor(this, colourRef));
        editDate.setTextColor(ContextCompat.getColor(this, colourRef));
        editTime.setTextColor(ContextCompat.getColor(this, colourRef));
        editLocation.setTextColor(ContextCompat.getColor(this, colourRef));
        editAttendees.setTextColor(ContextCompat.getColor(this, colourRef));
        addAttendee.setTextColor(ContextCompat.getColor(this, colourRef));
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
        int colourRef = 0;
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
