package com.example.quizapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class stdlogin extends AppCompatActivity {
    private Button signupbtn;
Button std_login;
EditText std_id,std_password;
    ConnectivityManager conMgr;
    final String url_data = Server.URL + "login_std.php";
    JSONObject json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stdlogin);
        Button signupbtn = (Button) findViewById(R.id.std_signup);
        std_login  = (Button) findViewById(R.id.std_login);
        std_id =   findViewById(R.id.std_id);
        std_password =  findViewById(R.id.std_password);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(stdlogin.this, stdsignup.class));
            }
        });




        SharedPreferences pref = getApplicationContext().getSharedPreferences("USER_INFO", MODE_PRIVATE);
        String ID = pref.getString("id", null);
//        if(!ID.equals("")){
    //        startActivity(new Intent(stdlogin.this, stdsignup.class));
     //   }


        std_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stdID = std_id.getText().toString();
                String Password = std_password.getText().toString();
                if(TextUtils.isEmpty(stdID)){
                    std_id.setError("Enter Student No.");
                    std_id.requestFocus();
                    return;
                }  else if(TextUtils.isEmpty(Password)){
                    std_password.setError("Enter Password");
                    std_password.requestFocus();
                    return;
                }else {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
                        new LoginStd().execute(stdID, Password);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    public boolean emailValidator(String email){
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public class LoginStd extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String Email = strings[0];
            String Password = strings[1];
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("stdno", Email)
                    .add("password", Password)
                    .build();
            Request request = new Request.Builder()
                    .url(url_data)
                    .post(formBody)
                    .build();
            Response response = null;
            try{
                response = okHttpClient.newCall(request).execute();
                if(response.isSuccessful()){
                    String result = response.body().string().trim();

                    json = new JSONObject(result);
                    int success = json.getInt("success");
                    if(success==1){
                        SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("USER_INFO", MODE_PRIVATE);
                        SharedPreferences.Editor edt = pref.edit();
                        /// To add value in preference:
                        String id = json.getString("std_number");
                        String fname = json.getString("std_fname");
                        String mname = json.getString("std_mname");
                        String lname =json.getString("std_lname");

                        edt.putString("id", id);
                        edt.putString("fname", fname);
                        edt.putString("mname", mname);
                        edt.putString("lname", lname);
                        edt.commit();
                        //  showToast(role);
                        Intent i = new Intent(stdlogin.this,
                                stdmainform.class);
                        startActivity(i);
                        finish();
                    }else if(success==0){
                        showFailed();
                    }
                }else{
                    showError();
                }
            }catch (Exception e){
                showError();
                e.printStackTrace();
            }
            return null;
        }

    }
    public void showFailed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(stdlogin.this)
                        .setTitle("Error")


                        .setMessage("Email or Password incorrect...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }

    public void showError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(stdlogin.this)
                        .setTitle("Error")
                        .setMessage("Error has occured, Try again...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }


    public void showToast(final String Text){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(stdlogin.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }


}