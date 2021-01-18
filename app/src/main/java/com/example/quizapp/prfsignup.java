package com.example.quizapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class prfsignup extends AppCompatActivity {
Button btnRegister;
EditText prof_email,prf_fname,prf_mname,prf_lname,prf_password,prf_cfpassword;
    ConnectivityManager conMgr;

    final String url_data = Server.URL + "reg_prof.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prfsignup);
        btnRegister = findViewById(R.id.btn_signup);
        prof_email = findViewById(R.id.prof_email);
        prf_fname = findViewById(R.id.prf_fname);
        prf_mname = findViewById(R.id.prf_mname);
        prf_lname = findViewById(R.id.prf_lname);
        prf_password = findViewById(R.id.prf_password);
        prf_cfpassword = findViewById(R.id.prf_cfpassword);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prfemail = prof_email.getText().toString();
                String fname = prf_fname.getText().toString();
                String mname = prf_mname.getText().toString();
                String lname = prf_lname.getText().toString();
                String pass = prf_password.getText().toString();
                String cpass = prf_cfpassword.getText().toString();
                if(TextUtils.isEmpty(prfemail)){
                    prof_email.setError("Enter ID");
                    prof_email.requestFocus();
                    return;
                }else if(TextUtils.isEmpty(fname)){
                    prf_fname.setError("Enter Firstname");
                    prf_fname.requestFocus();
                    return;
                } else if(TextUtils.isEmpty(lname)){
                    prf_lname.setError("Enter Lastname");
                    prf_lname.requestFocus();
                    return;
                }
               else if(pass.equals("") || cpass.equals("")){
                    new AlertDialog.Builder(prfsignup.this)
                            .setTitle("Error")
                            .setMessage("Enter your password and confirm password...")
                            .setPositiveButton(android.R.string.ok, null).show();
                    prf_cfpassword.requestFocus();
                }else if(pass.length()<6){
                    new AlertDialog.Builder(prfsignup.this)
                            .setTitle("Error")
                            .setMessage("Password must 6 characters and above...")
                            .setPositiveButton(android.R.string.ok, null).show();
                    prf_password.requestFocus();
                }else if(!pass.equals(cpass)){

                    new AlertDialog.Builder(prfsignup.this)
                            .setTitle("Error")
                            .setMessage("Password not matched...")
                            .setPositiveButton(android.R.string.ok, null).show();
                    prf_cfpassword.requestFocus();
                }
                else {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {

                        new AddProf().execute(prfemail,fname,mname, lname, pass);
                    } else {
                        new AlertDialog.Builder(prfsignup.this)
                                .setTitle("Error")
                                .setMessage("Internet connection required...")
                                .setPositiveButton(android.R.string.ok, null).show();
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

    public class AddProf extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String prfemail = strings[0];
            String fname = strings[1];
            String mname = strings[2];
            String lname = strings[3];
            String pass = strings[4];

            String finalURL = url_data +
                    "?email=" + prfemail +
                    "&fname=" + fname +
                    "&mname=" + mname +
                    "&lname=" + lname +
                    "&password=" + pass;

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(finalURL)
                        .get()
                        .build();
                Response response = null;

                try {
                    response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String result = response.body().string().trim();
                        if (result.equalsIgnoreCase("signup")) {
                            showToast("Register successful");
                            Intent i = new Intent(prfsignup.this,
                                    prflogin.class);
                            startActivity(i);
                            finish();

                        } else if (result.equalsIgnoreCase("exist")) {
                            //   showToast("User already exists");
                            showExist();
                        } else {
                            //  showToast("Error has occured! please try again");
                            showError();
                        }
                    }
                } catch (Exception e) {
                    //showToast("Check your internet connection");
                    showError();
                    e.printStackTrace();
                }
            }catch (Exception e){
                //  showToast("Check your internet connection");
                showError();
                e.printStackTrace();
            }
            return null;
        }
    }
    public void showExist() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(prfsignup.this)
                        .setTitle("Error")
                        .setMessage("User already exists...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }
    public void showError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(prfsignup.this)
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
                Toast.makeText(prfsignup.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }
}




