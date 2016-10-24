package com.example.varun.fix_it;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;


public class Home extends ActionBarActivity {
    private static Button btn_create;
    private Button btn_join;

    Button generateRandom;
    TextView randomResult;
    Random myRandom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        generateRandom = (Button)findViewById(R.id.generate);
        randomResult = (TextView)findViewById(R.id.randomresult);
        generateRandom.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String result = "";
                myRandom = new Random();


                    result= String.valueOf(myRandom.nextInt(10000));


                randomResult.setText(result);
            }});
        btn_create=(Button)findViewById(R.id.button);

        btn_create.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.varun.create.creatematch");
                        startActivity(intent);


                    }
                }
        );
btn_join=(Button)findViewById(R.id.button2);
        btn_join.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent("com.example.varun.fix_it.joinmatch");
                        startActivity(in);

                }}
        );



    }

}

