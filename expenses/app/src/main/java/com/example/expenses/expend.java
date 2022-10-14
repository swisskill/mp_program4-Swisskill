package com.example.expenses;

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

//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.firebase.ui.database.SnapshotParser;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class expend extends Fragment {

    final static String TAG = "Expend Fragment";
    // DatabaseReference myChildRef; //ATTN:

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ((MainActivity) requireActivity()).getSupportActionBar().hide();
        View myView = inflater.inflate(R.layout.fragment_expend, container, false);
        FloatingActionButton fab = myView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Add");
            }
        });
        return myView;
    }
    void updateDialog(final Note note) {
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        final View textenter = inflater.inflate(R.layout.fragment_my_dialog, null);
        final EditText et_note = textenter.findViewById(R.id.et_note);
        et_note.setText(note.getNote());
        final EditText et_title = textenter.findViewById(R.id.et_title);
        et_title.setText(note.getTitle());
        //final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(requireContext(), R.style.ThemeOverlay_AppCompat_Dialog)); //ATTN: WHAT THEME? WHERE
        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(requireContext(), R.style.Theme_Expenses)); //ATTN: WHAT THEME? WHERE
        builder.setView(textenter).setTitle("Update Note");
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        logthis("update title is " + et_title.getText().toString());
                        logthis("update note is " + et_note.getText().toString());
                        //myChildRef.push().setValue(new Note(et_title.getText().toString(), et_note.getText().toString()));
                        Note mynote = new Note(et_title.getText().toString(), et_note.getText().toString());

                        // mFirebaseDatabaseReference.child("messages").child(note.getId()).setValue(mynote);
                        //OR since it's partially there already, use myChildRef
                        //myChildRef.child(note.getId()).setValue(mynote); //ATTN: WHY IS DB NOT IMPORTING
                        //Toast.makeText(getBaseContext(), userinput.getText().toString(), Toast.LENGTH_LONG).show();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        logthis("dialog canceled");
                        dialog.cancel();

                    }
                });
        //you can create the dialog or just use the now method in the builder.
        //AlertDialog dialog = builder.create();
        //dialog.show();
        builder.show();
    }

    /**
     * Add dialog, with blanks.  then adds the data into the database.
     */
    void showDialog(String title) {
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        final View textenter = inflater.inflate(R.layout.fragment_my_dialog, null);
        final EditText et_note = textenter.findViewById(R.id.et_note);
        final EditText et_title = textenter.findViewById(R.id.et_title);
        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(requireContext(), R.style.Theme_Expenses)); //ATTN: HOW TO MAKE ThemeOverlay_AppCompat_Dialog
        builder.setView(textenter).setTitle(title);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        logthis("title is " + et_title.getText().toString());
                        logthis("note is " + et_note.getText().toString());
                        //Note push is used only when added new data for child "notes".  ie needes a unique id.
                        //myChildRef.push().setValue(new Note(et_title.getText().toString(), et_note.getText().toString())); //WHY IS DB NOT IMPORTING
                        //Toast.makeText(getBaseContext(), userinput.getText().toString(), Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        logthis("dialog canceled");
                        dialog.cancel();

                    }
                });
        //you can create the dialog or just use the now method in the builder.
        //AlertDialog dialog = builder.create();
        //dialog.show();
        builder.show();
    }


    void logthis(String item) {
        Log.d(TAG, "Value is: " + item);
    }

}