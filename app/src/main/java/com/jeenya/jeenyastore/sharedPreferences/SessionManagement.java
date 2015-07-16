package com.jeenya.jeenyastore.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hp on 6/18/2015.
 */
public class SessionManagement {

    public static final String DEFAULT = "N/A";

    public static void SaveProductsSelectedForCheckout(Context context, String color, String size, int quantity, String title) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("shopping", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("color", color);
        editor.putString("size", size);
        editor.putInt("quantity", quantity);
        editor.putString("title", title);
        editor.commit();
    }


    public static SharedPreferences GetProductsSelectedForCheckout(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shopping", Context.MODE_PRIVATE);
        //String apkId = sharedPreferences.getString("apkId", DEFAULT);
        return sharedPreferences;
    }


}
