package com.example.expenses;


import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

/**
 *  a ViewModel that access the scoreDatabase.  This could actually replace the ScoreDatabase class,
 *  but in this place, it just calls scoreDatabase class.
 */

public class CursorViewModel extends AndroidViewModel {
    myDatabase db;
    MutableLiveData<Cursor> myCursor = new MutableLiveData<Cursor>();

    public CursorViewModel(@NonNull Application application) {
        super(application);
        db = new myDatabase(application);
        db.open();
        myCursor.setValue(db.getAllNames());
        db.close();
    }

    public MutableLiveData<Cursor> getData() {
        return myCursor;
    }

    // this uses the Convenience method to add something to the database and then update the cursor.
    public void add(String Name, String Cate, String Date, String Amot, String Note) {
        db.open();
        db.insertName(Name, Cate, Date, Amot, Note);
        myCursor.setValue(db.getAllNames());
        db.close();
    }

    // this uses the Convenience method to update something from the database and then update the cursor.
    public int Update(String TableName, ContentValues values, String selection, String[] selectionArgs) {
        db.open();
        int ret = db.Update(TableName, values, selection, selectionArgs);
        myCursor.setValue(db.getAllNames());
        db.close();
        return ret;
    }
    public int mUpdate(ContentValues values, String selection){
        db.open();
        int ret = db.Update("Expenses", values, selection, null);
        myCursor.setValue(db.getAllNames());
        db.close();
        return ret;
    }

    // this uses the Convenience method to delete something in the database and then update the cursor.
    public int Delete(String TableName, String selection, String[] selectionArgs) {
        db.open();
        int ret = db.Delete(TableName, selection, selectionArgs);
        myCursor.setValue(db.getAllNames());
        db.close();
        return ret;
    }
}