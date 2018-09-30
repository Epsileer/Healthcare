package com.example.amittiwari.epilux.services;

/**
 * Created by Amit Tiwari on 30-09-2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.amittiwari.epilux.model.Prob;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "problemManager";
    private static final String TABLE_PROBLEM = "problem";
    private static final String KEY_ID = "id";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROBLEM_TABLE = "CREATE TABLE " + TABLE_PROBLEM + "("
                + KEY_ID + " TEXT PRIMARY KEY)";
        db.execSQL(CREATE_PROBLEM_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROBLEM);

        // Create tables again
        onCreate(db);
    }

    //add visited problem
    public void addProblem(Prob prob){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_ID, prob.getId());
            db.insert(TABLE_PROBLEM, null, values);
            db.close();
        } catch (SQLException s) {
            new Exception("Error with DB Open");
        }

    }

    // get visited problem


    // code to get the single contact
    public Boolean getProb(String id) {
        Boolean viewed = false;
        try {

            String selectQuery = "SELECT  * FROM " + TABLE_PROBLEM;

            SQLiteDatabase db = this.getWritableDatabase();

            Cursor cursor = null;
            try {
                cursor = db.rawQuery(selectQuery, null);
                // do some work with the cursor here.
                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        if (cursor.getString(0).equals(id))
                            viewed = true;
                        Log.d("problem_db", cursor.getString(0));
                    } while (cursor.moveToNext());
                }
            } finally {
                // this gets called even if there is an exception somewhere above
                if(cursor != null)
                    cursor.close();
            }

        }catch (SQLException s) {
            new Exception("Error with DB Open");
        }

        // return contact list
        return viewed;
    }

}
