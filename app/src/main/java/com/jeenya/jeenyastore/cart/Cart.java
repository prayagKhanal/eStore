package com.jeenya.jeenyastore.cart;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jeenya.jeenyastore.R;
import com.jeenya.jeenyastore.db.DatabaseHandler;
import com.jeenya.jeenyastore.db.SqlDataHolder;
import com.jeenya.jeenyastore.volley.RetriveMyApplicationContext;

import java.util.ArrayList;

public class Cart extends AppCompatActivity implements CartItemAdapter.ClickListener {

    private Toolbar toolbar_items;
    private RecyclerView cartList;
    private CartItemAdapter cartItemAdapter;
    TextView billTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        billTotal = (TextView) findViewById(R.id.total_bill);
        int total = calculateTotal();
        billTotal.setText(String.valueOf(total));

        //Adding Custom Tool Bar
        toolbar_items = (Toolbar) findViewById(R.id.app_bar_cart); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar_items);  // Setting toolbar as the ActionBar with setSupportActionBar() call

        cartList = (RecyclerView) findViewById(R.id.recycler_cartList);
        cartList.setLayoutManager(new LinearLayoutManager(this));
        cartItemAdapter = new CartItemAdapter(getApplicationContext(),billTotal);
        cartList.setAdapter(cartItemAdapter);

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

        /* PAss DATA in Adapter */
        DatabaseHandler d = new DatabaseHandler(RetriveMyApplicationContext.getAppContext());
        ArrayList<SqlDataHolder> m = d.retriveCartData();
        if (m != null) {
            cartItemAdapter.setProductData(d.retriveCartData());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void itemClicked(View view, int position, ArrayList<SqlDataHolder> product) {

    }


    public void goBack(MenuItem item) {
        finish();
    }

    public void goToProceed(View view) {
        DatabaseHandler d = new DatabaseHandler(RetriveMyApplicationContext.getAppContext());
        ArrayList<SqlDataHolder> m = d.retriveCartData();

        if (m != null && !m.isEmpty()) {
            Intent i = new Intent(this, ProceedToCheckOut.class);
            startActivity(i);
        } else {
            //SHOW SOME MESSAGES

            EmptyCartMessage ec = new EmptyCartMessage();
            ec.show(getFragmentManager(), "noCart");

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


}
