package com.example.expenses;

//@author Will Brant with assistance from Jim Ward

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 THINGS TO IMPLEMENT:
 * SWIPING
 * REFRESHING
 * DATABASE
 * make sure totalData grabs from db upon startup
 * make sure dollar amounts are valid (no multi decimal)
 */

public class expend extends Fragment {
    Context cont;
    CursorViewModel mCursor;
    //MutableLiveData<Cursor> mCursor = new MutableLiveData<Cursor>();
    List<exData> list;
    public expend(){
    }
    public expend(Context c, List<exData> totalData){
        this.cont = c;
        this.list = totalData;
    }

    final static String TAG = "Expend Fragment";
    private RecyclerView mRecyclerView;
    //myDatabase mydb; //TODO: Need to make sure that this is ok; may need to make "new myDatabase"
    String Name = "";
    String Cate = "";
    String Date = "";
    String Amot = "";
    String Note = "";
    RecyclerView recyclerView;
    RecyclerView_Adapter adapter;
    RecyclerView.ViewHolder viewHolder;
    List<exData> totalData = new ArrayList<>(); //TODO: will equal whatever is in the db upon startup
    Cursor cursor;
    //----------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mCursor = new ViewModelProvider(requireActivity()).get(CursorViewModel.class);

        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).hide();//get rid of toolbar
        View myView = inflater.inflate(R.layout.fragment_expend, container, false);
        recyclerView = (RecyclerView) myView.findViewById(R.id.recyclerView);
        adapter = new RecyclerView_Adapter(totalData, getActivity().getApplication(), cursor);
        recyclerView.setAdapter(adapter);
        mCursor.getData().observe(getActivity(), new Observer<Cursor>() {
            @Override
            public void onChanged(Cursor cursor) {
                adapter.setCursor(cursor);
            }
        });
        //----------------------------Touch to update-------------------------------
        adapter.setOnItemClickListener(new RecyclerView_Adapter.onItemClickListener() {
            @Override
            public void onItemClick(String ID) {
                //Toast.makeText(getContext(), "-wb", Toast.LENGTH_LONG).show();
                updateDialog(Integer.valueOf(ID));
            }
        });
        //------------------------------------------------------------

        if (!totalData.isEmpty()) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        ItemTouchHelper.SimpleCallback toucher = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if(direction==ItemTouchHelper.RIGHT){
                    int item = viewHolder.getAdapterPosition();
                    totalData.remove(item);
                    //Toast.makeText(getContext(), String.valueOf(getId()), Toast.LENGTH_LONG).show();
                    //TODO: I don't think this works
                    mCursor.Delete("Expenses", String.valueOf(getId()), null);
                    adapter.notifyDataSetChanged();
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


                }
            }
        };


        ItemTouchHelper toucherHelper = new ItemTouchHelper(toucher);
        toucherHelper.attachToRecyclerView(recyclerView);

        FloatingActionButton fab = myView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        return myView;
    }









    //----------------------------------------------------------------------------------------------
//    void stupid(Context som){
//        Toast.makeText(som, "-wb", Toast.LENGTH_LONG).show();
//    }
    void updateDialog(int ID) {
        //TODO: When called, Name, Cate etc need to already be called from
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        final View textenter = inflater.inflate(R.layout.fragment_my_dialog, null);
        final EditText et_name = textenter.findViewById(R.id.et_name);et_name.setText(Name);
        final EditText et_cate = textenter.findViewById(R.id.et_cate);et_cate.setText(Cate);
        final EditText et_date = textenter.findViewById(R.id.et_date);et_date.setText(Date);
        final EditText et_amot = textenter.findViewById(R.id.et_amot);et_amot.setText(Amot);
        final EditText et_note = textenter.findViewById(R.id.et_note);et_note.setText(Note);
        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(
                requireContext(), androidx.appcompat.R.style.Base_Theme_AppCompat_Dialog));
        builder.setView(textenter).setTitle("Update");
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id) {
                checkEmpty(et_name,et_cate,et_date,et_amot,et_note);
                logControl();
                mCursor.Update("Expenses", dbControl(), String.valueOf(ID), null);
                totalData.set(ID, new exData (Name, Cate, Date, Amot, Note));
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                logCanceled();
                dialog.cancel();
            }
        });
        builder.show();
    }
    //----------------------------------------------------------------------------------------------
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
                checkEmpty(et_name,et_cate,et_date,et_amot,et_note);
                logControl();
                mCursor.add(Name, Cate, Date, Amot, Note);
                totalData.add(new exData(Name, Cate, Date, Amot, Note));
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                logCanceled();
                dialog.cancel();
            }
        });
        builder.show();
    }
    //----------------------------------------------------------------------------------------------
    void checkEmpty(EditText n,EditText c,EditText d,EditText a,EditText o) {
        //I know this method is hard to look at. It's simple: it takes those edit texts, checks for empty
        //and updates those global variables as a string. Nice and easy.
        String rn, rc, rd, ra, ro; String nd = String.valueOf(LocalDate.now());
        if (n.getText().toString().isEmpty()){Name = "Expense";}else{Name=n.getText().toString();}
        if (c.getText().toString().isEmpty()){Cate = "misc.";}else{Cate=c.getText().toString();}
        if (d.getText().toString().isEmpty()){Date = nd;}else{Date=d.getText().toString();}
        if (a.getText().toString().isEmpty()){Amot = "0";}else{Amot=a.getText().toString();}
        if (o.getText().toString().isEmpty()){Note = "nothing. . .";}else{Note=o.getText().toString();}
    }
    void logCanceled(){
        Log.d(TAG, "dialog canceled");
    }
    //----------------------------------------------------------------------------------------------
    void logControl() {
        Log.d(TAG, "NAME is " + Name);Log.d(TAG, "CATEGORY is " + Cate);
        Log.d(TAG, "DATE is " + Date);Log.d(TAG, "AMOUNT is " + Amot);
        Log.d(TAG, "NOTE is " + Note);
    }
    ContentValues dbControl(){
        ContentValues values = new ContentValues();
        values.put(mySQLiteHelper.KEY_NAME, Name); // create new data for update
        values.put(mySQLiteHelper.KEY_CATE, Cate);
        values.put(mySQLiteHelper.KEY_DATE, Date);
        values.put(mySQLiteHelper.KEY_AMOT, Amot);
        values.put(mySQLiteHelper.KEY_NOTE, Note);
        return values;
    }


}
//--------------------------------------------------------------------------------------------------

/*
------------------------BIN------------------------------------
Toast.makeText(getContext(), "-wb", Toast.LENGTH_LONG).show();
Name = "Name:     " + fields[0];Cate = "Category: " + fields[1];
                Date = "Date:     " + fields[2];Amot = "Amount:   " + fields[3];
                Note = "Note:     " + fields[4];


 void controlInsert(){
        mydb.open();
        mydb.insertName(Name, Cate, Date, Amot, Note);
        mCursor.setValue(mydb.getAllNames());
        mydb.close();
    }
    void controlDelete(){

    }
 */
