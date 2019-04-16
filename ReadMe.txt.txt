----------------------------------------
--- Project Meeting App  ReadMe.txt ----
----------------------------------------
----------------------------------------

Version 1.0

Author: David Saunders
Student Number:	910995
Date: 07/12/2018

Contents of ReadMe
------------------

* Introduction
* SQLite
* Intructions 
* Functionality 
* Installation


Introduction
------------

ProjectMeetingApp is an application that primarily allows users to create and view meetings.
Meetings are made up of a title, notes, date, time, location, and attendees. 
Attendees can be created so that when a user is entering attendees for a meeting
the attendees name will be suggested and autocompleted. Users can also change the 
font size and colour to suit their individual preferences.

SQLite
------
The created meetings and attendees are stored by the application using SQLite.
This is a very lightweight database which comes with Android OS.
It combines a clean SQL interface with a very small memoryfootprint and decent speed.
This made it a perfect choice for this application.  

Once created the database can be found in data/data//databases/  

Instructions
------------
When Project Meeting App is first opened it will show the default app activity.
This will show all meetings that have been created in a list view. 

-- Create Meeting --
From the default app activity select the floating action button identidiable by
the "plus" located in the bottom right of the screen. This will take you to the Add
Meeting activity, from here you can enter the title, notes, date, and time of the meeting.
	To add a location of the meeting press the "map" button located by the location
field. This will open a maps activity to select a location simply select on the map 
the desired location. If you initially select the wrong location do not worry as this can
be reselected. Press the app bar "back" button at the top left of the screen or use
your phones built in back navigation.  
	To add attendees to the meeting start typing their name, if they are already on
the system then their name will be autocompleted and then added to the list of meeting
attendees by selecting the "add" button located to the right. If a user is added that
is not already on the system they will be added.
	To add the meeting to the list of all meetings once again select the "plus" floating
 action button.  

- View meeting -
Selecting a meeting from the list view will open the Meeting Activity. This shows the  
title, notes, date, and time, location, and attendees of the selected meeting. To view 
the location on a map select the "map" button. 
To get directions to the meeting location
select the meeting location marker then the directions icon in the bottom right.

- Delete meeting -
While viewing a meeting select the "bin" floating action button to delete it.

- Viewing Attendees -
From the default app activity press the options menu in the top right of the screen in
the app bar and then press Manage Attendees. This will show a list view of all attendees.
To view just a single attendee select one from the list view.

- Add Attendee -
While viewing all attendees select the floating action button identidiable by
the "plus" located in the bottom right of the screen. This will take you to the Add
attendees activity, from here you can enter the name of the attendee.
	To add the attendee to the list of all meetings once again select the "plus"
floating action button. The user can now be selected as an attendee when creating a new
meeting.

- Delete Attendee -
While viewing a single attendee select the "bin" floating action button to delete it.

-View Settings -
From the default app activity press the options menu in the top right of the screen in
the app bar and then navigate to Settings, this will open the settings activity.

- Change Font Size -
From the settings activity select the desired font size from the spinner. There are three
possible values Small, Medium, and Large. It will be Medium by default. To save the new
font size select the "save" floating action button.

- Change Text Colour -
From the settings activity select the desired text colour size from the spinner. There
are four possible values Black, Red, Green, and Blue. It will be Black by default.To 
save the new font size select the "save" floating action button.


Functionality
-------------

Here all the classes used in the application are listed, along with their main functionality and 
what components they use. 

- MeetingsActivity - 
The default app activity and shown when the application is launched.
Uses a list view to display all meetings.
 
- ViewMeetingActivity -
Shows details about a single meeting. 
Uses a floating action button for deleting a meeting.

	This is how users will view details about a meeting as the title, notes, date, time,
 location, and attendees are all shown here. In order to see the location of the meeting on a 
map then user must select the map button which is located to the side of where the location is shown.
 Users are able to  
	Allows deletion of meetings by pressing the bin floating action button. Will then return to MeetingsActivity

AddMeetingActivity
	Map Button launches a maps interface. User slecets a place then goes back. Have to select a place before going back. They can change the location as many times as they want.

- SettingsActivity -
	Allows users to change the font size from small medium or large, change text colour. Both will update and take effect when the save button is pressed.

- AttendeesActivity -
Can launch ViewAttendeeActivity or AddAttendeeActivity
	Listview
Adding attendees will make them come up as suggested attendees when on AddMeetingActivity.

- ViewAttendeeActivity - 
	Allows deletion of meetings by pressing the bin floating action button. Will then return to MeetingsActivity

- AddAttendeeActivity -
	Map Button launches a maps interface. User slecets a place then goes back. Have to select a place before going back. They can change the location as many times as they want.

- AttendeeDBHelper -
Define a DBManager class to perform all database CRUD operations.

- MeetingDBHelper -
Define a DBManager class to perform all database CRUD operations.
