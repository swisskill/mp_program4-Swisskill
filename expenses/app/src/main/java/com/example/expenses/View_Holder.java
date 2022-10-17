package com.example.expenses;
//@author Will Brant with help from
// https://www.c-sharpcorner.com/article/recyclerview-in-andriod-with-java/

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class View_Holder extends RecyclerView.ViewHolder {

    TextView name;
    TextView cate;
    TextView date;
    TextView amot;
    TextView note;

    View_Holder(View itemView, final RecyclerView_Adapter.onItemClickListener listener) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
        cate = (TextView) itemView.findViewById(R.id.cate);
        date = (TextView) itemView.findViewById(R.id.date);
        amot = (TextView) itemView.findViewById(R.id.amot);
        note = (TextView) itemView.findViewById(R.id.note);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //expend exp = new expend(context, list);
                //exp.updateDialog();
                listener.onItemClick(name.getTag().toString());
                //TODO: These errors are holding me back. Otherwise, I am so close
                //this is most likely due to the constructor. It needs to include everything that is
                //required in updateDialog, which gets pretty nasty
            }
        });

    }

}