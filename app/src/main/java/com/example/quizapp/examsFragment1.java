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

public class examsFragment1 extends Fragment {
    static List<Services> services;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayout;
    private ServiceAdapter1 adapter;
    String ownerID;
    String clinicID;
    ConnectivityManager conMgr;
    MainActivity ma;
    ProgressBar progressBar;
    Button tbBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_exams1, null);
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

        tbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AddClinicServicesFragment()).commit();
            }
        });
        conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        SharedPreferences pref = this.getActivity().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
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

            adapter = new ServiceAdapter1(getActivity(), services);
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
                        .url(Server.URL + "quiz_list1.php")
                        .build();
                Log.e("TAG", Server.URL + "read_myclinic_services.php?id=" + clinicID);
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        Services services = new Services(
                                object.getString("id"),
                                object.getString("quiz_name"),
                                object.getString("status"),
                                object.getString("item")
                        );

                        examsFragment1.this.services.add(services);
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
