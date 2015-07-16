package com.jeenya.jeenyastore.productDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.jeenya.jeenyastore.R;
import com.jeenya.jeenyastore.cart.SuccessfulMessage;
import com.jeenya.jeenyastore.db.DatabaseHandler;
import com.jeenya.jeenyastore.slider.SliderImage;
import com.jeenya.jeenyastore.volley.RetriveMyApplicationContext;

import java.util.ArrayList;
import java.util.Collections;

public class ProductDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Toolbar toolbar_ProductInfo;
    private SliderLayout sliderShow;
    private TextView title, details, amount;
    Spinner spinner, spinnerColor;
    String header;
    String thumbNailUrl;

    TextView quantity_decrease, quantity_selected, quantity_increase, cart_add;
    ImageButton refresh_Seletion;

    LinearLayout sizeSpinnerLayout;
    LinearLayout colorSpinnerLayout;

    ArrayList<String> sizes;
    ArrayList<String> colors;
    ArrayList<String> sliderUrlList;

    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        c = this;
        title = (TextView) findViewById(R.id.individual_productTitle);
        details = (TextView) findViewById(R.id.individual_productDetails);
        amount = (TextView) findViewById(R.id.individual_productPrice);
        quantity_decrease = (TextView) findViewById(R.id.decreaseQuantity);
        quantity_selected = (TextView) findViewById(R.id.selected_quantity);
        quantity_increase = (TextView) findViewById(R.id.increaseQuantity);
        cart_add = (TextView) findViewById(R.id.addTocart);
        refresh_Seletion = (ImageButton) findViewById(R.id.refreshSeletion);

        configureToolBar();
        setProductInfo();
        setSpinner();
        configureSlider();

        //RESETTING SELECTION ON REFRESH BUTTON CLICKED
        refresh_Seletion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductCartStack.resetCart();
                refreshSpinner();
            }
        });

        //Setting Click Listener For Quantity Selector
        quantity_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(quantity_selected.getText().toString());
                if (n > 1) {
                    n = n - 1;
                    ProductCartStack.setQuantity(n);
                    quantity_selected.setText(String.valueOf(n));
                }
            }
        });

        quantity_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(quantity_selected.getText().toString());
                Log.d("Increas QQ", String.valueOf(n));
                n = n + 1;
                Log.d("Increas QQ", String.valueOf(n));
                ProductCartStack.setQuantity(n);
                quantity_selected.setText(String.valueOf(n));
            }
        });

        //FINAL ADDING TO CART
        cart_add.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String titleProduct = null;
                                            if (header != null) {
                                                titleProduct = header;
                                            }

                                            if (sizes != null && !sizes.isEmpty()) {
                                                if (colors != null && !colors.isEmpty()) { //When Both Color and Sizes are Available

                                                    if (!ProductCartStack.getSize().equals("N/A") && !ProductCartStack.getColorSelected().equals("N/A")) {
                                                        DatabaseHandler d = new DatabaseHandler(RetriveMyApplicationContext.getAppContext());
                                                        d.saveToCart(ProductCartStack.getSize(), ProductCartStack.getColorSelected(), String.valueOf(ProductCartStack.getQuantity()), titleProduct, amount.getText().toString(), thumbNailUrl, ProductCartStack.getProductId());
                                                        ProductCartStack.resetCart();
                                                        refreshSpinner();

                                                        try {

                                                            d.retriveCartData();
                                                            //ALERT MESSAGE
                                                            SuccessfulMessage tc = new SuccessfulMessage();
                                                            tc.setter("JEENYA Store", "Successfully Added to Cart");
                                                            tc.show(getFragmentManager(), "successful");

                                                        } catch (Exception e) {

                                                        }
                                                    }

                                                } else {
                                                    if (!ProductCartStack.getSize().equals("N/A")) {
                                                        DatabaseHandler d = new DatabaseHandler(RetriveMyApplicationContext.getAppContext());
                                                        d.saveToCart(ProductCartStack.getSize(), ProductCartStack.getColorSelected(), String.valueOf(ProductCartStack.getQuantity()), titleProduct, amount.getText().toString(), thumbNailUrl, ProductCartStack.getProductId());
                                                        ProductCartStack.resetCart();
                                                        refreshSpinner();

                                                        try {

                                                            d.retriveCartData();
                                                            //ALERT MESSAGE
                                                            SuccessfulMessage tc = new SuccessfulMessage();
                                                            tc.setter("JEENYA Store", "Successfully Added to Cart");
                                                            tc.show(getFragmentManager(), "successful");

                                                        } catch (Exception e) {

                                                        }
                                                    }


                                                }

                                            } else if (colors != null && !colors.isEmpty()) {

                                                if (!ProductCartStack.getColorSelected().equals("N/A")) {
                                                    DatabaseHandler d = new DatabaseHandler(RetriveMyApplicationContext.getAppContext());
                                                    d.saveToCart(ProductCartStack.getSize(), ProductCartStack.getColorSelected(), String.valueOf(ProductCartStack.getQuantity()), titleProduct, amount.getText().toString(), thumbNailUrl, ProductCartStack.getProductId());
                                                    ProductCartStack.resetCart();
                                                    refreshSpinner();
                                                    try {
                                                        d.retriveCartData();
                                                        //ALERT MESSAGE
                                                        SuccessfulMessage tc = new SuccessfulMessage();
                                                        tc.setter("JEENYA Store", "Successfully Added to Cart");
                                                        tc.show(getFragmentManager(), "successful");
                                                    } catch (Exception e) {
                                                    }
                                                }

                                            } else {

                                                DatabaseHandler d = new DatabaseHandler(RetriveMyApplicationContext.getAppContext());
                                                d.saveToCart(ProductCartStack.getSize(), ProductCartStack.getColorSelected(), String.valueOf(ProductCartStack.getQuantity()), titleProduct, amount.getText().toString(), thumbNailUrl, ProductCartStack.getProductId());
                                                ProductCartStack.resetCart();
                                                refreshSpinner();
                                                try {
                                                    d.retriveCartData();
                                                    //ALERT MESSAGE FOR ACCEPT ALL TERMS AND CONDITIONS
                                                    SuccessfulMessage tc = new SuccessfulMessage();
                                                    tc.setter("JEENYA Store", "Successfully Added to Cart");
                                                    tc.show(getFragmentManager(), "successful");

                                                } catch (Exception e) {

                                                }
                                            }

                                        }
                                    }

        );


    }


    private void refreshSpinner() {

        //Setting Spinner For Size List
        if (sizes != null && !sizes.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sizes);
            spinner.setAdapter(adapter);
        } else {
            sizeSpinnerLayout.setVisibility(View.GONE);
        }

        //Setting Spinner For Color List
        if (colors != null && !colors.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colors);
            spinnerColor.setAdapter(adapter);
        } else {
            colorSpinnerLayout.setVisibility(View.GONE);
        }
        quantity_selected.setText("1");
    }


    // START OF SET SPINNER METHOD

    private void setSpinner() {

        sizes = new ArrayList<>();
        colors = new ArrayList<>();
        Intent intent = getIntent();
        spinner = (Spinner) findViewById(R.id.spinner_size);
        spinnerColor = (Spinner) findViewById(R.id.spinner_color);
        sizeSpinnerLayout = (LinearLayout) findViewById(R.id.linearLayout_sizeSpinner);
        colorSpinnerLayout = (LinearLayout) findViewById(R.id.linearLayout_colorSpinner);

        if (intent != null) {
            sizes = intent.getStringArrayListExtra("SizeList");
            colors = intent.getStringArrayListExtra("ColorList");
        }
        //Setting Spinner For Size List
        if (sizes != null && !sizes.isEmpty()) {
            sizes.add("Choose Size");
            //SORTING ARRAYlIST
            Collections.reverse(sizes);
            // Collections.sort(sizes, Collections.reverseOrder());
            Log.d("Product Details sizes", sizes.toString());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sizes);
            spinner.setAdapter(adapter);
            // Spinner click listener
            spinner.setOnItemSelectedListener(this);
        } else {
            sizeSpinnerLayout.setVisibility(View.GONE);
        }

        //Setting Spinner For Color List
        if (colors != null && !colors.isEmpty()) {
            colors.add("Choose Color");
            //SORTING ARRAYlIST
            Collections.reverse(colors);
            //Collections.sort(colors, Collections.reverseOrder());
            Log.d("Product Details colors", colors.toString());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colors);
            spinnerColor.setAdapter(adapter);
            // Spinner click listener
            spinnerColor.setOnItemSelectedListener(this);
        } else {
            colorSpinnerLayout.setVisibility(View.GONE);
        }
    }

    //END OF SET SPINNER METHOD
    private void setProductInfo() {
        Intent intent = getIntent();
        if (intent != null) {
            thumbNailUrl = intent.getStringExtra("URL");
            header = intent.getStringExtra("Title");
            String amt = intent.getStringExtra("Price");
            String desc = intent.getStringExtra("DESC");
            title.setText(header);
            amount.setText(amt);
            details.setText(desc);
            String pID = intent.getStringExtra("PID");
            ProductCartStack.setProductId(pID);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_details, menu);
        return true;
    }

    // Spinner Size Click Listener
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.spinner_size) {
            // On selecting a spinner item
            String size = parent.getItemAtPosition(position).toString();
            if (size.equals("Choose Size")) {
            } else {
                ProductCartStack.setSize(size);
                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + size, Toast.LENGTH_LONG).show();
            }

        } else if (spinner.getId() == R.id.spinner_color) {
            // On selecting a spinner item
            String color = parent.getItemAtPosition(position).toString();
            if (color.equals("Choose Color")) {
            } else {
                ProductCartStack.setColorSelected(color);
                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + color, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // START OF SLIDER
    private void configureSlider() {
        sliderShow = (SliderLayout) findViewById(R.id.slider_ProductDetails);
        Intent intent = getIntent();

        if (intent != null) {
            sliderUrlList = intent.getStringArrayListExtra("SliderList");
        }

        if (sliderUrlList != null && !sliderUrlList.isEmpty()) {
            // ArrayList<String> imageURL = sliderUrlList;
            //String[] imageURL = CSV.convertToStringArray(sliderUrlList);
            SliderImage.showSlider(sliderUrlList, this, sliderShow);
        }
    }

    private void configureToolBar() {

        //Adding Custom Tool Bar
        toolbar_ProductInfo = (Toolbar) findViewById(R.id.app_bar_productDetails); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar_ProductInfo);  // Setting toolbar as the ActionBar with setSupportActionBar() call


        //UP NAVIGATION
        try {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            //Changing status bar color programatically
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.primaryColorDark));
            }
        } catch (Exception e) {
            Log.d("Status color error", "What a Error");
        }


    }

}
