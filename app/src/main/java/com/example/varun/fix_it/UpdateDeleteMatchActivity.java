package com.example.varun.fix_it;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateDeleteMatchActivity extends Activity implements OnClickListener {


    private EditText LocationEditText;
    private EditText TeamEditText;
    private EditText DetailsEditText;
    private EditText DateEditText;
    private EditText TimeEditText;
    private Button cancelButton;

    private Button confirmButton;
    private String bundledLocation;
    private String bundledDetails;
    private String bundledTeam;
    private String bundledDate;
    private String bundledtime;

    private String LocationEditTextValue;
    private String TeamEditTextValue;
    private String DetailsEditTextValue;
    private String DateEditTextValue;
    private String TimeEditTextValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_match);

        LocationEditText = (EditText) findViewById(R.id.updateDeleteLocationEditText);
        TeamEditText= (EditText) findViewById(R.id.updateDeleteTeamEditText);
        DetailsEditText= (EditText) findViewById(R.id.updateDeleteDetailsEditText);
        DateEditText = (EditText) findViewById(R.id.updateDeleteDateEditText);
        TimeEditText = (EditText) findViewById(R.id.updateDeleteTimeEditText);

        cancelButton = (Button) findViewById(R.id.updateDelete_cancel_button);
        cancelButton.setOnClickListener(this);
        confirmButton = (Button) findViewById(R.id.updateDelete_confirm_button);
        confirmButton.setOnClickListener(this);




        Bundle takeBundledData = getIntent().getExtras();

        // First we need to get the bundle data that pass from the UndergraduateListActivity
        bundledLocation = takeBundledData.getString("clickedLocation");
        bundledTeam =takeBundledData.getString("clickedTeam");
        bundledDetails=takeBundledData.getString("clickedDetails");
        bundledDate = takeBundledData.getString("clickeddate");
        bundledtime = takeBundledData.getString("clickedtime");
        // setText method ask for a String value
        //But getDouble method returns a Double value


        // Set the values that we extracted from the Bundle in the EditText fields
        LocationEditText.setText(bundledLocation);
        TeamEditText.setText(bundledTeam);
        DetailsEditText.setText(bundledDetails);

        DateEditText.setText(bundledDate);
        TimeEditText.setText(bundledtime);
    }

    @Override
    public void onClick(View v) {

        // We need to update or delete details which is in the EditText fields after user edit the values
        // These values are the ContentValues that we are going to use in future
        LocationEditTextValue = LocationEditText.getText().toString();
        TeamEditTextValue = TeamEditText.getText().toString();
        DetailsEditTextValue=DetailsEditText.getText().toString();
        DateEditTextValue=DateEditText.getText().toString();
        TimeEditTextValue=TimeEditText.getText().toString();



        // It is easy to set values to the POJO class and pass the class instance to the updateUgraduateDetails() method
        matchDetailsPojo matchDetailsPojo = new matchDetailsPojo();

        matchDetailsPojo.setLocation(bundledLocation);
        matchDetailsPojo.setTeam(bundledTeam);
        matchDetailsPojo.setDetails(bundledDetails);
        matchDetailsPojo.setDate(bundledDate);
        matchDetailsPojo.setTime(bundledtime);



        if(v.getId() == R.id.updateDelete_cancel_button){
            Intent iii = new Intent("com.example.varun.fix_it.joinmatch");
            startActivity(iii);

        }else if(v.getId() == R.id.updateDelete_confirm_button){
            Toast.makeText(this, "The match is fixed", Toast.LENGTH_LONG).show();
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO,
                    Uri.parse("sms:9677221292"));
            smsIntent.putExtra("sms_body", "Your match has been fixed. Enjoy your game");
            startActivity(smsIntent);
        }
    }

    private void updateMatchDetails(matchDetailsPojo matchDetailsPojo) {

        AndroidOpenDbHelper androidOpenDbHelper = new AndroidOpenDbHelper(this);
        SQLiteDatabase sqliteDatabase = androidOpenDbHelper.getWritableDatabase();

        // ContentValues class is used to store a set of values
        //It is like name-value pairs
        // "value" part contains the values that we are going to UPDATE
        ContentValues contentValues = new ContentValues();
        contentValues.put(AndroidOpenDbHelper.COLUMN_NAME_LOCATION, LocationEditTextValue);
        contentValues.put(AndroidOpenDbHelper.COLUMN_NAME_TEAM, TeamEditTextValue);
        contentValues.put(AndroidOpenDbHelper.COLUMN_NAME_DETAILS, DetailsEditTextValue);
        contentValues.put(AndroidOpenDbHelper.COLUMN_NAME_DATE, DateEditTextValue);
        contentValues.put(AndroidOpenDbHelper.COLLUMN_NAME_TIME, TimeEditTextValue);

        // If we are using multiple whereClauseArguments, array size should have to change
        String[] whereClauseArgument = new String[1];
        whereClauseArgument[0] = matchDetailsPojo.getLocation();

        System.out.println("whereClauseArgument[0] is :" + whereClauseArgument[0]);

        /**
         * This is the normal SQL query for UPDATE
         UPDATE table_name
         SET column1=value, column2=value2,...
         WHERE some_column=some_value
         */

        sqliteDatabase.update(AndroidOpenDbHelper.TABLE_NAME_MATCH, contentValues, AndroidOpenDbHelper.COLUMN_NAME_LOCATION+"=?", whereClauseArgument);
        // For two whereClauseArguments
        //sqliteDatabase.update(AndroidOpenDbHelper.TABLE_NAME_GPA, contentValues, BaseColumns._ID+"=? AND name=?", whereClauseArgument);

        sqliteDatabase.close();
        finish();
    }

    private void deleteMatchDetails(matchDetailsPojo deleteMatchDetailsPojo) {

        AndroidOpenDbHelper androidOpenDbHelper = new AndroidOpenDbHelper(this);
        SQLiteDatabase sqliteDatabase = androidOpenDbHelper.getWritableDatabase();

        String[] whereClauseArgument = new String[1];
        whereClauseArgument[0] = deleteMatchDetailsPojo.getLocation();

        // Only difference between UPDATE and DELETE is
        //DELETE does not have ContentValues part
        sqliteDatabase.delete(AndroidOpenDbHelper.TABLE_NAME_MATCH, AndroidOpenDbHelper.COLUMN_NAME_LOCATION+"=?", whereClauseArgument);

        sqliteDatabase.close();
        finish();
    }
}