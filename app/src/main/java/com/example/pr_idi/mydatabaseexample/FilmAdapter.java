package com.example.pr_idi.mydatabaseexample;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by quim on 10/01/17.
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder>{

    private List<Film> filmlist;
    private Context context;
    private FragmentActivity mActivity;
    private callback mCallback;
    private GestureDetector mGestureDetector;


    public FilmAdapter(Context cont, List<Film> list){
        this.filmlist = list;
        this.context = cont;
    }

    public void setListener(callback callBack){
        mCallback=callBack;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filmlistitem, parent, false);
        ViewHolder viewh = new ViewHolder(v);
        return viewh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String title = filmlist.get(position).getTitle();
        holder.textview.setText(title);
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mCallback.onListClick(position);
                return true;
            }
        });
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onDoubleTap(MotionEvent e) {
                mCallback.onDoubleClickList(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return filmlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textview;
        public View view;

        public ViewHolder(View itemView){
            super(itemView);
            textview = (TextView) itemView.findViewById(R.id.name);
            view = itemView;

        }
    }

    public void updatedata(List<Film> list){
        this.filmlist = list;
        notifyDataSetChanged();
    }
}
