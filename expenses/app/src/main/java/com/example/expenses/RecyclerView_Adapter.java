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
    private Cursor mCursor;
    public RecyclerView_Adapter(List<exData> data, Application application, Cursor c) {
        this.list = data;
        this.context = application;
        mCursor = c;
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
        mCursor.moveToPosition(position);
        holder.name.setText(mCursor.getString(mCursor.getColumnIndex(mySQLiteHelper.KEY_NAME))); //todo: maybe put this in?
        holder.cate.setText(mCursor.getString(mCursor.getColumnIndex(mySQLiteHelper.KEY_CATE))); //todo: maybe put this in?
        holder.date.setText(mCursor.getString(mCursor.getColumnIndex(mySQLiteHelper.KEY_DATE))); //todo: maybe put this in?
        holder.amot.setText(mCursor.getString(mCursor.getColumnIndex(mySQLiteHelper.KEY_AMOT))); //todo: maybe put this in?
        holder.note.setText(mCursor.getString(mCursor.getColumnIndex(mySQLiteHelper.KEY_NOTE))); //todo: maybe put this in?


//        holder.name.setText(list.get(position).name);
//        holder.cate.setText(list.get(position).cate);
//        holder.date.setText(list.get(position).date);
//        holder.amot.setText(list.get(position).amot);
//        holder.note.setText(list.get(position).note);
        holder.name.setTag(position);
    }
    public void setCursor(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}