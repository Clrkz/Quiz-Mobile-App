package com.example.quizapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class stdresultsFragment extends Fragment {
    static List<Result> services;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayout;
    private ResultAdapter adapter;
    String ownerID;
    String clinicID;
    ConnectivityManager conMgr;
    MainActivity ma;
    ProgressBar progressBar;
    Button tbBtn;
    String studentNumber;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_stdresults, null);
        ///////FRAGMENT CUSTOM TOOLBAR START ////////////////////
        Toolbar toolbar = v.findViewById(R.id.toolbars);
        //  toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ManageClinicFragment()).commit();
            }
        });
        ///////FRAGMENT CUSTOM TOOLBAR STOP ////////////////////

        ///////BACK BUTTON HANDLER START //////////////////////
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == event.ACTION_DOWN) {
                    if (keyCode == event.KEYCODE_BACK) {
                        // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ManageClinicFragment()).commit();
                        return true;
                    }
                }
                return false;
            }
        });
        tbBtn = v.findViewById(R.id.btn_tbaction);


        SharedPreferences pref = getActivity().getSharedPreferences("USER_INFO", MODE_PRIVATE);
        studentNumber = pref.getString("id", null);




        tbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AddClinicServicesFragment()).commit();
            }
        });
        conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        ownerID = pref.getString("uid", null);

        // clinicID = String.valueOf(mh.cid);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
            services = new ArrayList<>();
            getClinicServicesFromDB(0);

            gridLayout = new GridLayoutManager(getActivity(), 1);
            recyclerView.setLayoutManager(gridLayout);

            adapter = new ResultAdapter(getActivity(), services);
            recyclerView.setAdapter(adapter);

        } else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("No Internet Connection")
                    // .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton(android.R.string.ok, null).show();
            progressBar.setVisibility(View.GONE);
        }
        return v;
    }


    private void getClinicServicesFromDB(int id) {
        AsyncTask<Integer, Void, Void> asyncTask = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... movieIds) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Server.URL + "result_list.php?sid="+studentNumber)
                        .build();
                Log.e("TAG", Server.URL + "result_list.php?sid="+studentNumber);
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        Result services = new Result(
                                object.getString("stdno"),
                                object.getString("quiz"),
                                object.getString("score")
                        );

                        stdresultsFragment.this.services.add(services);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        };

        asyncTask.execute(id);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
}
