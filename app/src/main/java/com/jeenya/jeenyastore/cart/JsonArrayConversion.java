package com.jeenya.jeenyastore.cart;

import android.database.Cursor;
import android.util.Log;

import com.jeenya.jeenyastore.db.DatabaseHandler;
import com.jeenya.jeenyastore.volley.RetriveMyApplicationContext;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by hp on 6/23/2015.
 */
public class JsonArrayConversion {

    Cursor cursor;
    JSONArray resultSet = new JSONArray();

    public JSONArray convertToJson() {

        try {
            DatabaseHandler databaseHandler = new DatabaseHandler(RetriveMyApplicationContext.getAppContext());
            cursor = databaseHandler.getCursor();

        } catch (Exception e) {
            e.printStackTrace();
        }


        if (cursor != null) {

            cursor.moveToFirst();

            while (cursor.isAfterLast() == false) {

                int totalColumn = cursor.getColumnCount();
                JSONObject rowObject = new JSONObject();

                for (int i = 0; i < totalColumn; i++) {
                    if (cursor.getColumnName(i) != null) {
                        try {
                            if (cursor.getString(i) != null) {
                                Log.d("TAG_NAME", cursor.getString(i));
                                rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                            } else {
                                rowObject.put(cursor.getColumnName(i), "");
                            }
                        } catch (Exception e) {
                            Log.d("TAG_NAME", e.getMessage());
                        }
                    }
                }
                resultSet.put(rowObject);
                cursor.moveToNext();
            }
            cursor.close();
        }

        Log.d("TAG_NAME", resultSet.toString());
        return resultSet;
    }

}