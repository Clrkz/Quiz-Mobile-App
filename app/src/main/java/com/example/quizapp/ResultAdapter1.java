package com.example.quizapp;

import android.app.FragmentManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ResultAdapter1 extends RecyclerView.Adapter<ResultAdapter1.ViewHolder> {
    private Context context;
    private List<Result1> services;
    ConnectivityManager conMgr;
    public ResultAdapter1(Context context, List<Result1> services) {
        this.context = context;
        this.services = services;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardprofresult, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText( services.get(position).getname());
        holder.sid.setText( "Student No.: "+services.get(position).getid());
        holder.score.setText( services.get(position).getscore());
    }


    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CardView cv;
        public TextView name;
        public TextView sid;
        public TextView score;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            score = (TextView) itemView.findViewById(R.id.score);
            sid = (TextView) itemView.findViewById(R.id.sid);
            cv = itemView.findViewById(R.id.card_view);
            cv.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
              //  showPopupMenu(v, position);

        }
    }

    private void showPopupMenu(View view, int poaition) {
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_myservices1, popup.getMenu());


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


            return false;

        }

    }

    public FragmentManager f_manager;

    //in your constructor add FragmentManager
    public ResultAdapter1(Context context, FragmentManager f_manager) {
        this.context = context;
        this.f_manager = f_manager;
    }


}