package com.example.quizapp;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    private Context context;
    private List<Services> services;
    ProgressDialog pDialog;
    ConnectivityManager conMgr;
    String statuss = "";
    final String url_delete = Server.URL + "del_quiz.php";
    public ServiceAdapter(Context context, List<Services> services) {
        this.context = context;
        this.services = services;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardmcservices, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText( services.get(position).getname());
        holder.items.setText( "Items: "+services.get(position).getitems());
        if(services.get(position).getstatus().equals("1")){
            holder.status.setText("Activated");
        }else{
            holder.status.setText("Deactivated");
        }
    }


    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CardView cv;
        public TextView name;
        public TextView items;
        public TextView status;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            status = (TextView) itemView.findViewById(R.id.status);
            items = (TextView) itemView.findViewById(R.id.items);
            cv = itemView.findViewById(R.id.card_view);
            cv.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
                showPopupMenu(v, position);

        }
    }

    private void showPopupMenu(View view, int poaition) {
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        if(services.get(poaition).getstatus().equals("1")){
            inflater.inflate(R.menu.menu_myservices1, popup.getMenu());
        }else{
            inflater.inflate(R.menu.menu_myservices, popup.getMenu());
        }


        popup.setOnMenuItemClickListener(new MenuClickListener(poaition));
        popup.show();
    }


    class MenuClickListener implements PopupMenu.OnMenuItemClickListener {
        Integer pos;

        public MenuClickListener(int pos) {
            this.pos = pos;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.action_result:
                    String serviceID = String.valueOf(services.get(pos).getid());
                    resultsFragment.qid = serviceID;

                    ((profmainform) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new resultsFragment()).commit();

                    return true;

                case R.id.action_delete:
                    String str = "";

                    if(services.get(pos).getstatus().equals("1")){
                        str = "Deactivate";
                        statuss= "0";
                    }else{
                        str = "Activate";
                        statuss= "1";
                    }
                    new AlertDialog.Builder(context)
                            .setTitle("Delete")
                            .setMessage("Are you sure you want to "+str+" "+services.get(pos).getname()+" Quiz?")
                            // .setIcon(android.R.drawable.ic_dialog_info)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                                    if (conMgr.getActiveNetworkInfo() != null
                                            && conMgr.getActiveNetworkInfo().isAvailable()
                                            && conMgr.getActiveNetworkInfo().isConnected()) {
                                        String serviceID = String.valueOf(services.get(pos).getid());
                                        pDialog = new ProgressDialog(context);
                                        pDialog.setCancelable(false);
                                        pDialog.setMessage("Deleting quiz...");
                                        showDialog();
                                        new DeleteService().execute(serviceID,statuss);
                                    }else{
                                        new AlertDialog.Builder(context)
                                                .setTitle("Error")
                                                .setMessage("No Internet Connection")
                                                .setPositiveButton(android.R.string.ok, null).show();
                                    }
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();

                    return true;
                default:
            }
            return false;

        }

    }

    public FragmentManager f_manager;

    //in your constructor add FragmentManager
    public ServiceAdapter(Context context, FragmentManager f_manager) {
        this.context = context;
        this.f_manager = f_manager;
    }

    public class DeleteService extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String pid = strings[0];
            String status = strings[1];

            String finalURL = url_delete +
                    "?id=" + pid +
                    "&status=" + status ;
            Log.e("", finalURL);
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
                            ((profmainform) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    new examsFragment()).commit();
                        }else{
                            showDialogs();
                        }
                    }
                } catch (Exception e) {
                    showDialogs();
                    e.printStackTrace();
                }
            } catch (Exception e) {
                showDialogs();
                e.printStackTrace();
            }

            hideDialog();

            return null;
        }

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();

    }

    public void showToast(final String Text) {
        ((profmainform) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showDialogs() {
        ((profmainform) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(context)
                        .setTitle("Success")
                        .setMessage("Quiz successfully deleted...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }

}