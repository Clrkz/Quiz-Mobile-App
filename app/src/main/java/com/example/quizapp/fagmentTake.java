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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class fagmentTake extends Fragment {
    RadioGroup rdGroupQuestion;
    RadioButton rd_a,rd_b,rd_c,rd_d;
    Button btn_submit,button1;
    RadioButton radioButton;
    TextView et_question,et_a,et_b,et_c,et_d;
    ConnectivityManager conMgr;
    String url_data = Server.URL+"add_quiz.php";
    String url_take = Server.URL+"add_student_quiz.php";
    String url_score = Server.URL+"add_score.php";
    String url_add = Server.URL+"add_quiz_record.php";
    String url_data0 = Server.URL+"chaeck_already.php";
    final String url_quiz = Server.URL + "get_quiz.php";
    JSONObject json;
    String lastQuizID = "0";
    String studentNumber;
    final String url_data2 = Server.URL + "quiz_question.php";
    final String url_data3 = Server.URL + "quiz_answer.php";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  v=  inflater.inflate(R.layout.fragment_take, null);
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

        SharedPreferences pref = getActivity().getSharedPreferences("USER_INFO", MODE_PRIVATE);
         studentNumber = pref.getString("id", null);




      // new getQuiz().execute();
        new AddTake().execute();


        btn_submit = v.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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




                            new AddTake().execute();
                          //  new  AddProf().execute(AddQuizHandler.quizName, et_question.getText().toString(), a, b, c, d, AddQuizHandler.quizAnswer,lastQuizID);
                            new AddScore().execute();
                        }
                    } else {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error")
                                .setMessage("Internet connection required...")
                                .setPositiveButton(android.R.string.ok, null).show();
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

    }



    public class AddTake extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String finalURL = url_take +
                    "?sid=" + studentNumber +
                    "&qid=" + Quizhandler.questionID;

            Log.e("URL",finalURL);
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
                        if (result.equalsIgnoreCase("success")) {
                           // new AddScore().execute();
                            new getQuiz().execute();
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
    public class AddScore extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
String ans = "";
            if(rd_a.isChecked()){
                ans  =    et_a.getText().toString();
            }else if(rd_b.isChecked()){
                ans  =    et_b.getText().toString();
            }else if(rd_c.isChecked()){
                ans  =    et_c.getText().toString();
            }else if(rd_d.isChecked()){
                ans  =    et_d.getText().toString();
            }



            String finalURL = url_score+
                    "?sid=" + studentNumber +
                    "&qtnid=" + Quizhandler.questionID +
                    "&qid=" + Quizhandler.qid +
                    "&ans=" + ans;

            Log.e("URL",finalURL);
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
                        if (result.equalsIgnoreCase("success")) {

                            //new getQuiz().execute();
                        } else {
                            //  showToast("Error has occured! please try again");
                          //  showError();
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

    public class AddQuizRecord extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String finalURL = url_add +
                    "?sid=" + studentNumber +
                    "&qid=" + Quizhandler.qid+
                 "&score=" + "0";

            Log.e("URL",finalURL);
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
                        if (result.equalsIgnoreCase("success")) {
                          //  new getQuiz().execute();
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

    public class getQuiz extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("sid", studentNumber)
                    .add("qid", Quizhandler.qid)
                    .build();
            Request request = new Request.Builder()
                    .url(url_data2)
                    .post(formBody)
                    .build();
            Response response = null;
            try{
                response = okHttpClient.newCall(request).execute();
                if(response.isSuccessful()){
                    String result = response.body().string();
                    json = new JSONObject(result);
                    int success = json.getInt("success");
                    if(success==1){
                        rdGroupQuestion.clearCheck();
                        rd_a.setChecked(false);
                        rd_b.setChecked(false);
                        rd_c.setChecked(false);
                        rd_d.setChecked(false);
                        Quizhandler.questionID = json.getString("id");
                        Quizhandler.questionName = json.getString("question");
                        et_question.setText(Quizhandler.questionName);
                        new getAnsA().execute();
                        new getAnsB().execute();
                        new getAnsC().execute();
                        new getAnsD().execute();
                    }else if(success==0){
                        new AddQuizRecord().execute();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new  stdresultsFragment()).commit();
                    }
                }else{
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new  stdresultsFragment()).commit();
                }
                // hideDialog();
            }catch (Exception e){
                //   hideDialog();
                //     showError();
                e.printStackTrace();
            }
            return null;
        }

    }

    public class getAnsA extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("qtnid", Quizhandler.questionID)
                    .add("letter", "0")
                    .build();
            Request request = new Request.Builder()
                    .url(url_data3)
                    .post(formBody)
                    .build();
            Response response = null;
            try{
                response = okHttpClient.newCall(request).execute();
                if(response.isSuccessful()){
                    String result = response.body().string();

                    json = new JSONObject(result);
                    int success = json.getInt("success");
                    if(success==1){
                        String ans = json.getString("answer");
                        et_a.setText(ans);
                    }else if(success==0){
                        //          showError();
                    }
                }else{
                    //      showError();
                }
                // hideDialog();
            }catch (Exception e){
                //   hideDialog();
                //     showError();
                e.printStackTrace();
            }
            return null;
        }

    }

    public class getAnsB extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("qtnid", Quizhandler.questionID)
                    .add("letter", "1")
                    .build();
            Request request = new Request.Builder()
                    .url(url_data3)
                    .post(formBody)
                    .build();
            Response response = null;
            try{
                response = okHttpClient.newCall(request).execute();
                if(response.isSuccessful()){
                    String result = response.body().string();

                    json = new JSONObject(result);
                    int success = json.getInt("success");
                    if(success==1){
                        String ans = json.getString("answer");
                        et_b.setText(ans);
                    }else if(success==0){
                        //          showError();
                    }
                }else{
                    //      showError();
                }
                // hideDialog();
            }catch (Exception e){
                //   hideDialog();
                //     showError();
                e.printStackTrace();
            }
            return null;
        }

    }

    public class getAnsC extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("qtnid", Quizhandler.questionID)
                    .add("letter", "2")
                    .build();
            Request request = new Request.Builder()
                    .url(url_data3)
                    .post(formBody)
                    .build();
            Response response = null;
            try{
                response = okHttpClient.newCall(request).execute();
                if(response.isSuccessful()){
                    String result = response.body().string();

                    json = new JSONObject(result);
                    int success = json.getInt("success");
                    if(success==1){
                        String ans = json.getString("answer");
                        et_c.setText(ans);
                    }else if(success==0){
                        //          showError();
                    }
                }else{
                    //      showError();
                }
                // hideDialog();
            }catch (Exception e){
                //   hideDialog();
                //     showError();
                e.printStackTrace();
            }
            return null;
        }

    }
    public class getAnsD extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("qtnid", Quizhandler.questionID)
                    .add("letter", "3")
                    .build();
            Request request = new Request.Builder()
                    .url(url_data3)
                    .post(formBody)
                    .build();
            Response response = null;
            try{
                response = okHttpClient.newCall(request).execute();
                if(response.isSuccessful()){
                    String result = response.body().string();

                    json = new JSONObject(result);
                    int success = json.getInt("success");
                    if(success==1){
                        String ans = json.getString("answer");
                        et_d.setText(ans);
                    }else if(success==0){
                        //          showError();
                    }
                }else{
                    //      showError();
                }
                // hideDialog();
            }catch (Exception e){
                //   hideDialog();
                //     showError();
                e.printStackTrace();
            }
            return null;
        }

    }


    public void showDialogs1() {
       getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Success")
                        .setMessage("Invalid  Quiz...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
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





}




