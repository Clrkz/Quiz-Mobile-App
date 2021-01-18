package com.example.quizapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity{
    private Button prfbutton, stdbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button prfbutton = (Button) findViewById(R.id.prfbutton);
        prfbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                    startActivity(new Intent(MainActivity.this, prflogin.class));

            }
        });

        Button stdbutton = (Button) findViewById(R.id.stdbutton);
        stdbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SaveSharedPreference.setLoggedIn(getApplicationContext(), false);
                SharedPreferences pref = getApplicationContext().getSharedPreferences("USER_INFO", MODE_PRIVATE);
                SharedPreferences.Editor edt = pref.edit();
                edt.putString("fname", null);
                edt.putString("mname", null);
                edt.putString("lname", null);
                edt.putString("email", null);
                edt.commit();
                    startActivity(new Intent(MainActivity.this, stdlogin.class));
            }
        });
    }
}