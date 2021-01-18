package com.example.quizapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class prflogin extends AppCompatActivity {
    private Button signupbtn, loginbtn;
    Button btnLogin;
    EditText prof_email, prf_password;
    ConnectivityManager conMgr;
    final String url_data = Server.URL + "login_prof.php";
    JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prflogin);
        Button signupbtn = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        prof_email = findViewById(R.id.prof_email);
        prf_password = findViewById(R.id.prf_password);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(prflogin.this, prfsignup.class));
            }
        });


        if(SaveSharedPreference.getLoggedStatus(getApplicationContext())) {
            Intent i = new Intent(prflogin.this,
                    profmainform.class);
            startActivity(i);
            finish();
        } /* else {
            loginForm.setVisibility(View.VISIBLE);
        } */



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = prof_email.getText().toString();
                String Password = prf_password.getText().toString();
                if (TextUtils.isEmpty(Email)) {
                    prof_email.setError("Enter ID");
                    prof_email.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(Password)) {
                    prf_password.setError("Enter Password");
                    prf_password.requestFocus();
                    return;
                } else {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
                       new LoginProf().execute(Email, Password);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public class LoginProf extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String Email = strings[0];
            String Password = strings[1];
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("email", Email)
                    .add("password", Password)
                    .build();
            Request request = new Request.Builder()
                    .url(url_data)
                    .post(formBody)
                    .build();
            Response response = null;
            try {
                response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    String result = response.body().string();

                    json = new JSONObject(result);
                    int success = json.getInt("success");
                    if (success == 1) {
                        SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("USER_INFO", MODE_PRIVATE);
                        SharedPreferences.Editor edt = pref.edit();
                        String fname = json.getString("prf_fname");
                        String mname = json.getString("prf_mname");
                        String lname = json.getString("prf_lname");
                        String email = json.getString("prf_email");

                        edt.putString("fname", fname);
                        edt.putString("mname", mname);
                        edt.putString("lname", lname);
                        edt.putString("email", email);
                        edt.commit();
                        //  showToast(role);
                        Intent i = new Intent(prflogin.this,
                                profmainform.class);
                        startActivity(i);
                        finish();
                    } else if (success == 0) {
                        showFailed();
                    }
                } else {
                    showError();
                }
            } catch (Exception e) {
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
                new AlertDialog.Builder(prflogin.this)
                        .setTitle("Error")


                        .setMessage("ID or Password incorrect...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }

    public void showError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(prflogin.this)
                        .setTitle("Error")
                        .setMessage("Error has occured, Try again...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }


    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(prflogin.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }


}