package com.example.varun.fix_it;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class sport extends Activity implements OnClickListener {


    private EditText LocationEditText;
    private EditText TeamEditText;
    private EditText DetailsEditText;
    private EditText DateEditText;
    private EditText TimeEditText;
    private Button cancelButton;
    private Button saveButton;

    private ArrayList matchDetailsPojoObjArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        LocationEditText = (EditText) findViewById(R.id.updateDeleteLocationEditText);

        TeamEditText = (EditText) findViewById(R.id.updateDeleteTeamEditText);
        DetailsEditText=(EditText)findViewById(R.id.updateDeleteDetailsEditText);
        DateEditText = (EditText) findViewById(R.id.updateDeleteDateEditText);
        TimeEditText = (EditText) findViewById(R.id.updateDeleteTimeEditText);

        cancelButton = (Button) findViewById(R.id.button4);
        cancelButton.setOnClickListener(this);
        saveButton = (Button) findViewById(R.id.button3);
        saveButton.setOnClickListener(this);

        matchDetailsPojoObjArrayList = new ArrayList();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button4){
            finish();
        }else if(v.getId() == R.id.button3){
            // Get the values provided by the user via the UI
            String providedlocation = LocationEditText.getText().toString();
            String providedteam= TeamEditText.getText().toString();
            String provideddetails= DetailsEditText.getText().toString();
            String provideddate = DateEditText.getText().toString();
            String providedtime = TimeEditText.getText().toString();

            // Pass above values to the setter methods in POJO class
            matchDetailsPojo matchDetailsPojoObj = new matchDetailsPojo();
            matchDetailsPojoObj.Location(providedlocation);
            matchDetailsPojoObj.Team(providedteam);
            matchDetailsPojoObj.Details(provideddetails);
            matchDetailsPojoObj.setDate(provideddate);
            matchDetailsPojoObj.setTime(providedtime);

            // Add an undergraduate with his all details to a ArrayList
           matchDetailsPojoObjArrayList.add(matchDetailsPojoObj);

            // Inserting undergraduate details to the database is doing in a separate method
            insertMatch(matchDetailsPojoObj);

            // Release from the existing UI and go back to the previous UI
            finish();
        }
    }

    public void insertMatch(matchDetailsPojo paramatchDetailsPojoObj){

        // First we have to open our DbHelper class by creating a new object of that
        AndroidOpenDbHelper androidOpenDbHelperObj = new AndroidOpenDbHelper(this);

        // Then we need to get a writable SQLite database, because we are going to insert some values
        // SQLiteDatabase has methods to create, delete, execute SQL commands, and perform other common database management tasks.
        SQLiteDatabase sqliteDatabase = androidOpenDbHelperObj.getWritableDatabase();

        // ContentValues class is used to store a set of values that the ContentResolver can process.
        ContentValues contentValues = new ContentValues();

        // Get values from the POJO class and passing them to the ContentValues class
        contentValues.put(AndroidOpenDbHelper.COLUMN_NAME_LOCATION, paramatchDetailsPojoObj.getLocation());
        contentValues.put(AndroidOpenDbHelper.COLUMN_NAME_TEAM, paramatchDetailsPojoObj.getTeam());
        contentValues.put(AndroidOpenDbHelper.COLUMN_NAME_DETAILS, paramatchDetailsPojoObj.getDetails());
        contentValues.put(AndroidOpenDbHelper.COLUMN_NAME_DATE, paramatchDetailsPojoObj.getSetDate());
        contentValues.put(AndroidOpenDbHelper.COLLUMN_NAME_TIME, paramatchDetailsPojoObj.getSetTime());

        // Now we can insert the data in to relevant table
        // I am going pass the id value, which is going to change because of our insert method, to a long variable to show in Toast
        long affectedColumnId = sqliteDatabase.insert(AndroidOpenDbHelper.TABLE_NAME_MATCH, null, contentValues);

        // It is a good practice to close the database connections after you have done with it
        sqliteDatabase.close();

        // I am not going to do the retrieve part in this post. So this is just a notification for satisfaction ;-)
        Toast.makeText(this, "The match is created", Toast.LENGTH_LONG).show();

    }
}