package com.example.expenses;

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
    float Amot ;
    String Note ;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).hide();//get rid of toolbar
        View myView = inflater.inflate(R.layout.fragment_expend, container, false);
        FloatingActionButton fab = myView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getContext(), "-wb", Toast.LENGTH_LONG).show();
                showDialog();
            }
        });
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
                Name = fields[0];
                Cate = fields[1];
                Date = fields[2];
                Amot = parseFloat(fields[3]);
                Note = fields[4];
                infoControl();
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
        if (n.getText().toString().isEmpty()){rn = "";}else{rn=n.getText().toString();}
        if (c.getText().toString().isEmpty()){rc = "";}else{rc=c.getText().toString();}
        if (d.getText().toString().isEmpty()){rd = "";}else{rd=d.getText().toString();}
        if (a.getText().toString().isEmpty()){ra = "0";}else{ra=a.getText().toString();}
        if (o.getText().toString().isEmpty()){ro = "";}else{ro=o.getText().toString();}
        return new String[]{rn, rc, rd, ra, ro};
    }
    void logCanceled(){
        Log.d(TAG, "dialog canceled");
    }
    void infoControl() {
        Log.d(TAG, "Name is " + Name);
        Log.d(TAG, "Name is " + Cate);
        Log.d(TAG, "Name is " + Date);
        Log.d(TAG, "Name is " + Amot);
        Log.d(TAG, "Name is " + Note);
        // now update the recycler view I guess. How? who knows
    }
}