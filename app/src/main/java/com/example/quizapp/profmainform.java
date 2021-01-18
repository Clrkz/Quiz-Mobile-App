package com.example.quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

public class profmainform extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profmainform);



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        loadFragment(new examsFragment());
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {

            case R.id.navigation_create:
                fragment = new createQuiz();

break;
            case R.id.navigation_exam:
                fragment = new examsFragment();
                break;
            case R.id.navigation_results:
                fragment = new resultsFragment();
                break;
            case R.id.navigation_logout:
               // fragment = new profileFragment();
                SaveSharedPreference.setLoggedIn(getApplicationContext(), false);

                SharedPreferences pref = getApplicationContext().getSharedPreferences("USER_INFO", MODE_PRIVATE);
                SharedPreferences.Editor edt = pref.edit();
                edt.putString("id", null);
                edt.putString("fname", null);
                edt.putString("mname", null);
                edt.putString("lname", null);
                edt.putString("email", null);
                edt.commit();
                Intent i = new Intent(profmainform.this,
                        MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return loadFragment(fragment);
    }
}
