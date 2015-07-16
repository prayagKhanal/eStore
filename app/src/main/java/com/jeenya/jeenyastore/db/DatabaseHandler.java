package com.jeenya.jeenyastore.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by hp on 6/19/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {


    private Context context;
    private static final String DATABASE_NAME = "ProductCart";
    private static final String TABLE_NAME = "CartStack";
    private static final int DATABASE_VERSION = 6;

    // Tables in the Databse
    private static final String UID = "_id";
    private static final String SIZE = "SIZE";
    private static final String COLOR = "COLOR";
    private static final String QUANTITY = "QUANTITY";
    private static final String TITLE = "TITLE";
    private static final String PRICE = "PRICE";
    private static final String URL = "URL";
    private static final String PID = "PID";


    //Query to create Database
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + SIZE + " TEXT,"
            + COLOR + " TEXT,"
            + QUANTITY + " TEXT,"
            + PRICE + " TEXT,"
            + URL + " TEXT,"
            // + OPER + " TEXT,"
            + PID + " TEXT,"
            + TITLE + " TEXT);";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_TABLE);
            //Toast.makeText(context, "OnCreate in DatabaseCreater", Toast.LENGTH_SHORT).show();

        } catch (SQLiteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    //Saving Data To Database
    public void saveToCart(String size, String color, String quantity, String title, String price, String url, String p_id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SIZE, size);
        values.put(COLOR, color);
        values.put(QUANTITY, quantity);
        values.put(PRICE, price);
        values.put(URL, url);
        values.put(PID, p_id);
        values.put(TITLE, title);


        // values.put(TIME, time);

        // Inserting Row
        db.insert(TABLE_NAME, null, values);

        Log.d("Inserting to database", "Successfull");
        db.close(); // Closing database connection
    }

    public Cursor getCursor() {

        Cursor cursor = null;
        try {
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + UID + " DESC";
            SQLiteDatabase db = this.getWritableDatabase();
            cursor = db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }


    public void clearLogs() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from " + TABLE_NAME);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ArrayList<SqlDataHolder> retriveCartData() {

        ArrayList<SqlDataHolder> contactList = new ArrayList<SqlDataHolder>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + UID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {
                // looping through all rows and adding to list
                // Log.d("Checking Id in Receiving section", "before move to first");

                // Log.d("Checking Id in Receiving section", "after move to first");
                do {
                    SqlDataHolder sd = new SqlDataHolder();
                    sd.setId_database_row(cursor.getInt(0));
                    Log.d("Receiving section", cursor.getString(0));

                    sd.setSize(cursor.getString(1));
                    Log.d("Receiving section", cursor.getString(1));

                    sd.setColor(cursor.getString(2));
                    Log.d(" Receiving section", cursor.getString(2));

                    sd.setQuantity(cursor.getString(3));
                    Log.d(" Receiving section", cursor.getString(3));

                    sd.setPrice(cursor.getString(4));
                    Log.d(" Receiving section", cursor.getString(4));

                    sd.setThumbNailUrl(cursor.getString(5));
                    Log.d(" Receiving section", cursor.getString(5));

                    sd.setProduct_Id(cursor.getString(6));
                    Log.d(" Receiving section", cursor.getString(6));

                    sd.setTitle(cursor.getString(7));
                    Log.d(" Receiving section", cursor.getString(7));


                    // Adding contact to list
                    contactList.add(sd);

                } while (cursor.moveToNext());
            }
        }

        // return contact list
        return contactList;
    }


    // Deleting single Location
    public void deleteItem(int iD) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, UID + " = ?",
                new String[]{String.valueOf(iD)});
        db.close();
    }


    public long getNoOfItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        Long numRows = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        Log.d("No of Rows", String.valueOf(numRows));
        return numRows;
    }

}
