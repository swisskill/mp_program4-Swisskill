package com.example.expenses;


import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.sqlite.db.SupportSQLiteQueryBuilder;

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

    // this uses the Convenience method to delete something in the database and then update the cursor.
    public int Delete(String TableName, String selection, String[] selectionArgs) {
        db.open();
        int ret = db.Delete(TableName, selection, selectionArgs);
        myCursor.setValue(db.getAllNames());
        db.close();
        return ret;
    }
    @SuppressLint("Range")
    public TextView Query(String selection){
        String[] projection = {mySQLiteHelper.KEY_ROWID, mySQLiteHelper.KEY_NAME, mySQLiteHelper.KEY_CATE,
                mySQLiteHelper.KEY_DATE, mySQLiteHelper.KEY_AMOT, mySQLiteHelper.KEY_NOTE};
        String helpSelect = mySQLiteHelper.KEY_ROWID + "=" + selection;
        db.open();
        //myCursor.setValue(db.qbQuery("Expenses", projection, helpSelect, null, null));
        Cursor newCursor = db.qbQuery("Expenses", projection, helpSelect, null, null);
        TextView name = null;
        name.setText(newCursor.getString(newCursor.getColumnIndex(mySQLiteHelper.KEY_NAME))); //todo: maybe put this in?
        db.close();
        return name;
    }
    //    public int Query(){
//        myCursor.moveToPosition(position);
//        holder.name.setText(myCursor.getString(myCursor.getColumnIndex(mySQLiteHelper.KEY_NAME)));
//        holder.cate.setText(myCursor.getString(myCursor.getColumnIndex(mySQLiteHelper.KEY_CATE)));
//        holder.date.setText(myCursor.getString(myCursor.getColumnIndex(mySQLiteHelper.KEY_DATE)));
//        holder.amot.setText(myCursor.getString(myCursor.getColumnIndex(mySQLiteHelper.KEY_AMOT)));
//        holder.note.setText(myCursor.getString(myCursor.getColumnIndex(mySQLiteHelper.KEY_NOTE)));
//    }
}

//-----------------------------BIN------------------------------------------------


//    public Cursor getAllNames() {
//        //SELECT KEY_NAME, KEY_SCORE FROM DATABASE_TABLE SORTBY KEY_NAME;
//        Cursor mCursor = qbQuery(mySQLiteHelper.TABLE_NAME,   //table name
//                new String[]{mySQLiteHelper.KEY_ROWID, mySQLiteHelper.KEY_NAME, mySQLiteHelper.KEY_CATE,
//                        mySQLiteHelper.KEY_DATE, mySQLiteHelper.KEY_AMOT, mySQLiteHelper.KEY_NOTE},  //projection, ie columns.
//                null,  //selection,  we want everything.
//                null, // String[] selectionArgs,  again, we want everything.
//                null//mySQLiteHelper.KEY_NAME// String sortOrder  by name as the sort.
//        );
//        if (mCursor != null)  //make sure cursor is not empty!
//            mCursor.moveToFirst();
//        return mCursor;
//    }
//    public Cursor qbQuery(String TableName, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
//        SupportSQLiteQueryBuilder qb = SupportSQLiteQueryBuilder.builder(TableName);
//        qb.columns(projection);
//        qb.selection(selection, selectionArgs);
//        qb.orderBy(sortOrder);
//        //using the query builder to manage the actual query at this point.
//        return db.query(qb.create());
//    }