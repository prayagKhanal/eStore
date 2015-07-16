package com.jeenya.jeenyastore.cart;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.jeenya.jeenyastore.R;
import com.jeenya.jeenyastore.db.DatabaseHandler;
import com.jeenya.jeenyastore.db.SqlDataHolder;
import com.jeenya.jeenyastore.volley.RetriveMyApplicationContext;

import java.util.ArrayList;

public class ProceedToCheckOut extends AppCompatActivity {

    Toolbar toolbar_checkout;
    EditText fName, lName, address, phone, specialNotes;
    TextView totalAmount;
    TextView order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed_to_check_out);

        fName = (EditText) findViewById(R.id.billing_fName);
        lName = (EditText) findViewById(R.id.billing_lName);
        address = (EditText) findViewById(R.id.billing_street);
        phone = (EditText) findViewById(R.id.billing_phone);
        specialNotes = (EditText) findViewById(R.id.billing_notes);
        totalAmount = (TextView) findViewById(R.id.billing_total);

        int total = calculateTotal();
        totalAmount.setText(String.valueOf(total));
        order = (TextView) findViewById(R.id.bill_order_place);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();

            }
        });

        try {
            //Changing status bar color programatically
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.statusBarBilling));
            }
        } catch (Exception e) {
            Log.d("Status color error", "What a Error");
        }


        //Adding Custom Tool Bar
        toolbar_checkout = (Toolbar) findViewById(R.id.app_bar_checkOut); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar_checkout);  // Setting toolbar as the ActionBar with setSupportActionBar() call

        //Up Navigation
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private int calculateTotal() {

        int amtTotal = 0;

         /* PAss DATA in Adapter */
        DatabaseHandler d = new DatabaseHandler(RetriveMyApplicationContext.getAppContext());
        ArrayList<SqlDataHolder> m = d.retriveCartData();

        if (!m.isEmpty()) {

            for (int i = 0; i < m.size(); i++) {
                SqlDataHolder currentProduct = m.get(i);
                int t = Integer.parseInt(currentProduct.getTotal());
                amtTotal = amtTotal + t;
            }
        }
        return amtTotal;
    }


    public void placeOrder() {


        String firstNAme, lastName, location, ph, notes, tAmt;
        firstNAme = fName.getText().toString();
        lastName = lName.getText().toString();
        location = address.getText().toString();
        ph = phone.getText().toString();
        notes = specialNotes.getText().toString();
        tAmt = totalAmount.getText().toString();

        Log.d("order", firstNAme + "" + lastName + "" + location + "" + ph + "" + notes + "" + tAmt);

        if (firstNAme != null && !firstNAme.isEmpty()) {

        } else {
            fName.setHint("Required Field Missing **");
            fName.setHintTextColor(getResources().getColor(R.color.accentColor));
        }
        if (lastName != null && !lastName.isEmpty()) {

        } else {
            lName.setHint("Required Field Missing **");
            lName.setHintTextColor(getResources().getColor(R.color.accentColor));
        }

        if (location != null && !location.isEmpty()) {

        } else {
            address.setHint("Required Field Missing **");
            address.setHintTextColor(getResources().getColor(R.color.accentColor));
        }

        if (ph != null && !ph.isEmpty()) {

        } else {
            phone.setHint("Required Field Missing **");
            phone.setHintTextColor(getResources().getColor(R.color.accentColor));
        }

        //Sending To web For CheckOut
        if (!firstNAme.isEmpty() && !lastName.isEmpty() && !location.isEmpty() && !ph.isEmpty()) {
            new VolleyPostPayment().checkOut(firstNAme, lastName, location, ph, notes,getFragmentManager(),this);

        }

    }
}
