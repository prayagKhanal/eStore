package com.jeenya.jeenyastore;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.daimajia.slider.library.SliderLayout;
import com.jeenya.jeenyastore.cart.Cart;
import com.jeenya.jeenyastore.cart.CartNotification;
import com.jeenya.jeenyastore.db.DatabaseHandler;
import com.jeenya.jeenyastore.listView.ListViewItem;
import com.jeenya.jeenyastore.navigationDrawerActivities.AboutUs;
import com.jeenya.jeenyastore.slider.SliderImage;
import com.jeenya.jeenyastore.volley.RetriveMyApplicationContext;
import com.jeenya.jeenyastore.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Home extends AppCompatActivity {

    Toolbar toolbar_home;
    public NavigationView navigationView;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private SliderLayout sliderShow;
    ImageView clickCart;
    ArrayList<String> homeslider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sliderShow = (SliderLayout) findViewById(R.id.slider);
        setUpSlider();

        //Adding Custom Tool Bar
        toolbar_home = (Toolbar) findViewById(R.id.app_bar_home); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar_home); // Setting toolbar as the ActionBar with setSupportActionBar() call
        configureNavigationDrawer(toolbar_home);

    }

    private void setUpSlider() {
        getMeJson("http://store.jeenya.com/api/slider.php");


    }

    private void getMeJson(String url) {

        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response", response.toString());
                        homeslider = parseSliderResponse(response);
                        if (homeslider != null && !homeslider.isEmpty()) {
                            SliderImage.showSlider(homeslider, Home.this, sliderShow);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Response", "Error: " + error.getMessage());
                Toast.makeText(RetriveMyApplicationContext.getAppContext(),
                        "Error Connecting To Server", Toast.LENGTH_LONG).show();

            }
        }) /*{

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("categoryId","clothing");
                return params;
            }

        }*/;

        requestQueue.add(req);

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        View menu_hotList = menu.findItem(R.id.badge).getActionView();
        TextView notifCount = (TextView) menu_hotList.findViewById(R.id.actionbar_notifcation_textview);
        notifCount.setText(String.valueOf(CartNotification.getCount()));

        final MenuItem item = menu.findItem(R.id.badge);
        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, Cart.class);
                startActivity(i);
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.badge:
                Intent i = new Intent(this, Cart.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onStop() {
        sliderShow.stopAutoCycle();
        super.onStop();
    }


    protected void configureNavigationDrawer(Toolbar toolbar_home) {
        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar_home, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    public void goToCart(MenuItem item) {
        Intent i = new Intent(this, Cart.class);
        startActivity(i);

    }

    public void goToContactUs(MenuItem item) {
        Intent i = new Intent(this, AboutUs.class);
        startActivity(i);

    }

    public void goToHome(MenuItem item) {
        Intent i = new Intent(this, Home.class);
        startActivity(i);

    }


    public void goToCellPhones(View view) {
        Intent i = new Intent(this, ListViewItem.class);
        i.putExtra("cat", "2");
        startActivity(i);
    }

    public void goToPc(View view) {
        Intent i = new Intent(this, ListViewItem.class);
        i.putExtra("cat", "3");
        startActivity(i);
    }


    public void goToAccessories(View view) {
        Intent i = new Intent(this, ListViewItem.class);
        i.putExtra("cat", "4");
        startActivity(i);
    }

    public void showClothing(View view) {
        Intent i = new Intent(this, ListViewItem.class);
        i.putExtra("cat", "1");
        startActivity(i);
    }

    @Override
    protected void onResume() {
        DatabaseHandler d = new DatabaseHandler(RetriveMyApplicationContext.getAppContext());
        Long rows = d.getNoOfItems();

        CartNotification.setNotifCount(rows);//NOTIFICATION IN CART
        invalidateOptionsMenu();// For Updating ToolBar For Notification When Back Button Pressed.
        super.onResume();
    }


    public ArrayList<String> parseSliderResponse(JSONArray response) {

        ArrayList<String> sliderData = new ArrayList<>();

        try {
            // Parsing json array response
            // loop through each json object
            for (int i = 0; i < response.length(); i++) {
                JSONObject snyopsis = (JSONObject) response.get(i);
                //For Image URL in SLIDER FOR PRODUCT DETAILS
                //CSV To Array
                String iList = snyopsis.getString("image");
                if (iList != null && !iList.isEmpty()) {
                    sliderData.add(iList);
                    // sliderData = CSV.csvToArray(iList);
                    //Log.d("Slider...i..List", iList);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(RetriveMyApplicationContext.getAppContext(),
                    "Error: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        Log.d("SLIDER", sliderData.toString());

        return sliderData;
    }

}
