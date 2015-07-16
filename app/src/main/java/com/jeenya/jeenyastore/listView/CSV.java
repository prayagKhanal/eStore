package com.jeenya.jeenyastore.listView;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by hp on 6/16/2015.
 */
public class CSV {
    public static ArrayList<String> csvToArray(String s) {

        ArrayList<String> values = null;

        if (s != null) {
            values = new ArrayList<String>(Arrays.asList(s.split("\\s*,\\s*")));
            Log.e("CSV string to ArrayLsit", values.toString());
        }

        return values;
    }


    public static String[] convertToStringArray(ArrayList<String> s) {
        String[] productData = null;

        if (s != null) {

		/*ArrayList declaration and initialization*/
            ArrayList<String> arrlist = new ArrayList<String>();
            arrlist.add("String1");
            arrlist.add("String2");
            arrlist.add("String3");
            arrlist.add("String4");

		/*ArrayList to Array Conversion */
            String array[] = new String[s.size()];
            for (int j = 0; j < s.size(); j++) {
                array[j] = s.get(j);
            }

		/*Displaying Array elements*/
            for (String k : array) {
                System.out.println(k);
            }

            productData = array;
        }
        try {
            Log.e("CSV To StringArray", Arrays.toString(productData));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productData;

    }
}
