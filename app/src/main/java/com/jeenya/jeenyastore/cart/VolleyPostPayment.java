package com.jeenya.jeenyastore.cart;

import android.app.Activity;
import android.app.FragmentManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jeenya.jeenyastore.R;
import com.jeenya.jeenyastore.db.DatabaseHandler;
import com.jeenya.jeenyastore.volley.RetriveMyApplicationContext;
import com.jeenya.jeenyastore.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hp on 6/23/2015.
 */
public class VolleyPostPayment {

    public static FragmentManager f;
    public Activity c;


    public void checkOut(final String fn, final String ln, final String address, final String ph, final String note, FragmentManager fragmentManager, Activity c) {

        f = fragmentManager;
        this.c = c;

        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                "http://store.jeenya.com/api/user.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("REsponse...", response);
                handleResponse(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("CHECK", "Checkout Error: " + error.getMessage());

                Toast.makeText(RetriveMyApplicationContext.getAppContext(), "ERROR CONNECTING TO SERVER", Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                String productArray = String.valueOf(new JsonArrayConversion().convertToJson());
                if (productArray != null) {
                    params.put("orders", productArray);
                }

                String userJson = String.valueOf(StringToJson(fn, ln, address, ph, note));
                if (userJson != null) {
                    params.put("userInfo", userJson);
                }

                return params;
            }

        };

        requestQueue.add(strReq);

    }


    private JSONObject StringToJson(String fN, String lN, String address, String phone, String notes) {

        String userNotes = notes;

        if (userNotes != null && !userNotes.isEmpty()) {

        } else {

            userNotes = "No Notes Available";
        }

        JSONObject obj = new JSONObject();

        try {
            obj.put("firstName", fN);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            obj.put("lastName", lN);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            obj.put("address", address);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            obj.put("phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            obj.put("notes", userNotes);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("My userJson", obj.toString());

        return obj;
    }

    private void handleResponse(String r) {
        String s = "sucesssucess";
        Log.d("RESPONCE ON..", r);
        if (r != null) {
            Log.d("RESPONCE ON..", "Not Null");

            if (r.trim().equals(s)) {
                //ALERT MESSAGE
                Log.d("RESPONCE ON..", "INSIDE success");
                SuccessfulMessage tc = new SuccessfulMessage();
                tc.setter("JEENYA Store", "Thank Your For Your Order. Your Order is Under Processing...");
                tc.show(f, "successful");

                //Clearing TextView
                EditText fName, lName, address, phone, specialNotes;
                TextView totalAmount;
                fName = (EditText) c.findViewById(R.id.billing_fName);
                lName = (EditText) c.findViewById(R.id.billing_lName);
                address = (EditText) c.findViewById(R.id.billing_street);
                phone = (EditText) c.findViewById(R.id.billing_phone);
                specialNotes = (EditText) c.findViewById(R.id.billing_notes);
                totalAmount = (TextView) c.findViewById(R.id.billing_total);
                fName.setText("");
                lName.setText("");
                address.setText("");
                phone.setText("");
                specialNotes.setText("");
                totalAmount.setText("");

                DatabaseHandler dd = new DatabaseHandler(RetriveMyApplicationContext.getAppContext());
                dd.clearLogs();

            }
        }
    }

}
