package com.example.expenses;

//@author Will Brant with assistance from Jim Ward
import static java.lang.Float.parseFloat;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */


public class expend extends Fragment {

    final static String TAG = "Expend Fragment";
    private RecyclerView mRecyclerView;
    String Name ;
    String Cate ;
    String Date ;
    String Amot ;
    String Note ;
    List<exData> totalData = new ArrayList<>(); //ATTN: will equal whatever is in the db upon startup
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).hide();//get rid of toolbar
        View myView = inflater.inflate(R.layout.fragment_expend, container, false);
        FloatingActionButton fab = myView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        //ATTN: JIM, this is where the root of the problems are. I suppose I need some kind of observation
        // on the submit/update button to do this work and do stuff to the recycler view.
            totalData.add(new exData(Name, Cate, Date, Amot, Note));
            RecyclerView recyclerView = (RecyclerView) myView.findViewById(R.id.recyclerView);
            RecyclerView_Adapter adapter = new RecyclerView_Adapter(totalData, getActivity().getApplication());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return myView;
    }


    void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        final View textenter = inflater.inflate(R.layout.fragment_my_dialog, null);
        final EditText et_name = textenter.findViewById(R.id.et_name);
        final EditText et_cate = textenter.findViewById(R.id.et_cate);
        final EditText et_date = textenter.findViewById(R.id.et_date);
        final EditText et_amot = textenter.findViewById(R.id.et_amot);
        final EditText et_note = textenter.findViewById(R.id.et_note);

        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(requireContext(), androidx.appcompat.R.style.Base_Theme_AppCompat_Dialog));
        builder.setView(textenter).setTitle("Add");
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String[] fields = checkEmpty(et_name,et_cate,et_date,et_amot,et_note);
                Name = "Name:     " + fields[0];
                Cate = "Category: " + fields[1];
                Date = "Date:     " + fields[2];
                Amot = "Amount:   " + fields[3];
                Note = "Note:     " + fields[4];
                Toast.makeText(getContext(), Name + Cate + Date+fields[3]+Note, Toast.LENGTH_LONG).show();
                logControl();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                logCanceled();
                dialog.cancel();
            }
        });
        builder.show();
    }
    String[] checkEmpty(EditText n,EditText c,EditText d,EditText a,EditText o) {
        String rn, rc, rd, ra, ro;
        String nd = String.valueOf(LocalDate.now());
        if (n.getText().toString().isEmpty()){rn = "";}else{rn=n.getText().toString();}
        if (c.getText().toString().isEmpty()){rc = "";}else{rc=c.getText().toString();}
        if (d.getText().toString().isEmpty()){rd = nd;}else{rd=d.getText().toString();}
        if (a.getText().toString().isEmpty()){ra = "0";}else{ra=a.getText().toString();}
        if (o.getText().toString().isEmpty()){ro = "";}else{ro=o.getText().toString();}
        return new String[]{rn, rc, rd, ra, ro};
    }
    void logCanceled(){
        Log.d(TAG, "dialog canceled");
    }
    void logControl() {
        Log.d(TAG, "Name is " + Name);
        Log.d(TAG, "Name is " + Cate);
        Log.d(TAG, "Name is " + Date);
        Log.d(TAG, "Name is " + Amot);
        Log.d(TAG, "Name is " + Note);
    }
}

/*
------------------------BIN------------------------------------
Toast.makeText(getContext(), "-wb", Toast.LENGTH_LONG).show();

 */