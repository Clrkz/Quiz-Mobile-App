package com.example.quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class stdmainform extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stdmainform);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.std_navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        loadFragment(new examsFragment1());
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

            case R.id.navigation_exam:
                fragment = new examsFragment1();
                break;
            case R.id.navigation_results:
                fragment = new stdresultsFragment();
                break;
            case R.id.navigation_logout:
             //   fragment = new profileFragment();
                SaveSharedPreference.setLoggedIn(getApplicationContext(), false);

                SharedPreferences pref = getApplicationContext().getSharedPreferences("USER_INFO", MODE_PRIVATE);
                SharedPreferences.Editor edt = pref.edit();
                edt.putString("id", null);
                edt.putString("fname", null);
                edt.putString("mname", null);
                edt.putString("lname", null);
                edt.putString("email", null);
                edt.commit();
                Intent i = new Intent(stdmainform.this,
                        MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return loadFragment(fragment);
    }
}
