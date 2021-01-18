package com.example.quizapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.conn.ClientConnectionManager;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class createFragment extends Fragment {
    RadioGroup rdGroupQuestion;
    RadioButton rd_a,rd_b,rd_c,rd_d;
    Button btn_submit,button1;
    RadioButton radioButton;
    EditText et_question,et_a,et_b,et_c,et_d;
    ConnectivityManager conMgr;
    String url_data = Server.URL+"add_quiz.php";
    final String url_quiz = Server.URL + "get_quiz.php";
    JSONObject json;
    String lastQuizID = "0";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_create, null);
        rdGroupQuestion = v.findViewById(R.id.rdGroupQuestion);
        rd_a = v.findViewById(R.id.rd_a);
        rd_b = v.findViewById(R.id.rd_b);
        rd_c = v.findViewById(R.id.rd_c);
        rd_d = v.findViewById(R.id.rd_d);
        et_question = v.findViewById(R.id.et_question);
        et_a = v.findViewById(R.id.et_a);
        et_b = v.findViewById(R.id.et_b);
        et_c = v.findViewById(R.id.et_c);
        et_d = v.findViewById(R.id.et_d);
        button1=v.findViewById(R.id.button1);

        btn_submit = v.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        if(et_question.getText().equals("")){
            et_question.setError("Enter Quiz Question");
            et_question.requestFocus();
        }else {
            String a = et_a.getText().toString();
            String b = et_b.getText().toString();
            String c = et_c.getText().toString();
            String d = et_d.getText().toString();
            conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
                int selectedId = rdGroupQuestion.getCheckedRadioButtonId();
                int abs = Math.abs(selectedId);
                if (abs == 1) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Failed")
                            .setMessage("Select Answer...")
                            .setPositiveButton(android.R.string.ok, null).show();
                } else {
                    radioButton = (RadioButton) getActivity().findViewById(selectedId);
                    AddQuizHandler.quizAnswer = radioButton.getText().toString();

                    new AddProf().execute(AddQuizHandler.quizName, et_question.getText().toString(), a, b, c, d, AddQuizHandler.quizAnswer,lastQuizID);
                }
            } else {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Error")
                        .setMessage("Internet connection required...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new examsFragment()).commit();
            }
        });
        return v;

    } public class AddProf extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String qname = strings[0];
            String question = strings[1];
            String a = strings[2];
            String b = strings[3];
            String c = strings[4];
            String d = strings[5];
            String ans = strings[6];
            String lid = strings[7];


            String finalURL = url_data +
                    "?name=" + qname +
                    "&question=" + question +
                    "&A=" + a +
                    "&B=" + b +
                    "&C=" + c +
                    "&D=" + d +
                    "&lid=" + lid+
            "&correct=" + ans;
            Log.e("URL INSERT QUIZ: ",finalURL +"-----"+lid);
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
                            showToast("Quiz question successfully added");
                            et_question.setText("");
                            et_a.setText("");
                            et_b.setText("");
                            et_c.setText("");
                            et_d.setText("");
                            rd_a.setChecked(false);
                            rd_b.setChecked(false);
                            rd_c.setChecked(false);
                            rd_d.setChecked(false);
                            new LoginUser().execute(AddQuizHandler.quizName);
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Error")
                        .setMessage("Quiz already exists...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }
    public void showError() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Error")
                        .setMessage("Error has occured, Try again...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }

    public void showToast(final String Text){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(),
                        Text, Toast.LENGTH_LONG).show();

            }
        });
    }




    public class LoginUser extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            String qname = strings[0];
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("quiz_name", qname)
                    .build();
            Request request = new Request.Builder()
                    .url(url_quiz)
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

                        lastQuizID = json.getString("id");
                        Log.e("LAST QUIZ ID: ",lastQuizID);
                    }else if(success==0){
                        showError();
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


}




