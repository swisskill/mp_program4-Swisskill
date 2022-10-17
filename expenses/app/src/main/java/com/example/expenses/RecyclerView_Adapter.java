package com.example.expenses;
//@author Will Brant with help from
// https://www.c-sharpcorner.com/article/recyclerview-in-andriod-with-java/
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.util.List;

public class RecyclerView_Adapter extends RecyclerView.Adapter<View_Holder> {

    expend exp;
    private onItemClickListener listener;

    List<exData> list = Collections.emptyList();
    Context context;
    //private Cursor mCursor;
    Cursor pCursor;
    public RecyclerView_Adapter(List<exData> data, Application application, Cursor c) {
        this.list = data;
        this.context = application;
        this.pCursor = c;
    }

    public interface onItemClickListener {
        void onItemClick(String ID);
    }
    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        View_Holder holder = new View_Holder(v, listener);
        return holder;
    }

    @SuppressLint("Range")
    @Override
    public void onBindViewHolder(@NonNull View_Holder holder, int position) {
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        pCursor.moveToPosition(position);
        holder.name.setText(pCursor.getString(pCursor.getColumnIndex(mySQLiteHelper.KEY_NAME)));
        holder.cate.setText(pCursor.getString(pCursor.getColumnIndex(mySQLiteHelper.KEY_CATE)));
        holder.date.setText(pCursor.getString(pCursor.getColumnIndex(mySQLiteHelper.KEY_DATE)));
        holder.amot.setText(pCursor.getString(pCursor.getColumnIndex(mySQLiteHelper.KEY_AMOT)));
        holder.note.setText(pCursor.getString(pCursor.getColumnIndex(mySQLiteHelper.KEY_NOTE)));
        //holder.
        //holder.cate.setText(list.get(position).cate);
//        holder.date.setText(list.get(position).date);
//        holder.amot.setText(list.get(position).amot);
//        holder.note.setText(list.get(position).note);
        holder.name.setTag(position);
        //pCursor.getString(pCursor.getColumnIndex(mySQLiteHelper.KEY_NAME))



    }
    public void setpCursor(Cursor c){
        pCursor = c;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}