package com.example.expenses;
//@author Will Brant with help from
// https://www.c-sharpcorner.com/article/recyclerview-in-andriod-with-java/
import android.app.Application;
import android.content.Context;
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

    List<exData> list = Collections.emptyList();
    Context context;

    public RecyclerView_Adapter(List<exData> data, Application application) {
        this.list = data;
        this.context = application;
    }

    @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull View_Holder holder, int position) {
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.name.setText(list.get(position).name);
        holder.cate.setText(list.get(position).cate);
        holder.date.setText(list.get(position).date);
        holder.amot.setText(list.get(position).amot);
        holder.note.setText(list.get(position).note);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expend exp = new expend(context, list);
                exp.updateDialog();
                //TODO: THE ISSUE IS THAT WHILE THE LISTENER WORKS, IT CAN'T CALL UPDATE DIALOG
                //this is most likely due to the constructor. It needs to include everything that is
                //required in updateDialog, which gets pretty nasty
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}