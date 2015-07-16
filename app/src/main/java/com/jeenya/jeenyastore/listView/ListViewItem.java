package com.jeenya.jeenyastore.listView;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.jeenya.jeenyastore.Home;
import com.jeenya.jeenyastore.R;
import com.jeenya.jeenyastore.productDetails.ProductDetails;
import com.jeenya.jeenyastore.volley.ProductDataHolder;
import com.jeenya.jeenyastore.volley.RetriveMyApplicationContext;
import com.jeenya.jeenyastore.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListViewItem extends Home implements CustomListAdapter.ClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView viewAsList;
    private Toolbar toolbar_items;
    private CustomListAdapter customListAdapter;
    ArrayList<ProductDataHolder> productDatas = new ArrayList<>();
    ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;

    private static int viewManagerForList = 1;
    String cat = "";

    //While Setting Up Navigation Don't Forget to mention in Manifests file
    //For Details Go To Link :http://developer.android.com/training/implementing-navigation/ancestral.html
    //Don't Forget to use android:launchMode="singleTop" in parent class so when returning back it wont reload again.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_item);

        handleIntent(getIntent()); //For Search Widget

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Items...");
        progressDialog.setCanceledOnTouchOutside(false);

        //PULL TO REFRESH
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        //Adding Custom Tool Bar
        toolbar_items = (Toolbar) findViewById(R.id.app_bar_items); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar_items);  // Setting toolbar as the ActionBar with setSupportActionBar() call

        viewAsList = (RecyclerView) findViewById(R.id.viewList);

        //viewAsList.setLayoutManager(new LinearLayoutManager(this));
        viewAsList.setLayoutManager(new GridLayoutManager(this, 2));
        customListAdapter = new CustomListAdapter(getApplicationContext(), 0);
        customListAdapter.setClickListener(this);//For Item Click of RecyclerView


        Intent intent = getIntent();
        if (intent != null) {
            cat = intent.getStringExtra("cat");
        }
        Log.d("category", cat);
        getMeJson("http://store.jeenya.com/api/newproducts.php?categoryId=" + cat);
        viewAsList.setAdapter(customListAdapter);
        configureNavigationDrawer(toolbar_items);

    }


    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    /**
     * Handling intent data
     */
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            getMeJson("http://store.jeenya.com/api/search.php?keyword=" + query);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_view_item, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_list_search)
                .getActionView();
        // Don't forget to mention Component Name i.e Activity where Search Results are Displayed others wise it will throw
        //Null Pointer Exception
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(new ComponentName(this, ListViewItem.class)));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_list_view:
                //Change RecyclerView Layout to List View
                setLayoutAsListView();
                invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void setLayoutAsListView() {

        if (viewManagerForList == 1) {
            viewAsList.setLayoutManager(new LinearLayoutManager(this));

        } else {
            viewAsList.setLayoutManager(new GridLayoutManager(this, 2));
        }
        customListAdapter = new CustomListAdapter(getApplicationContext(), viewManagerForList);
        customListAdapter.setClickListener(this);//For Item Click of RecyclerView
        customListAdapter.setProductData(productDatas);
        viewAsList.setAdapter(customListAdapter);
        if (viewManagerForList == 1) {
            viewManagerForList = 0;
        } else {
            viewManagerForList = 1;
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (viewManagerForList == 0) {
            //SHOW GRID VIEW ICON
            menu.getItem(1).setIcon(R.mipmap.ic_action_view_as_grid);
        } else {
            menu.getItem(1).setIcon(R.mipmap.ic_action_view_as_list);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public void getMeJson(String url) {

        if (swipeRefreshLayout.isRefreshing()) {

        } else {
            progressDialog.show();
        }


        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response", response.toString());
                        productDatas = parseJsonResponse(response);
                        if (productDatas != null && !productDatas.isEmpty()) {
                            customListAdapter.setProductData(productDatas);
                        } else {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Items Not Found", Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Response", "Error: " + error.getMessage());
                Toast.makeText(RetriveMyApplicationContext.getAppContext(),
                        "Error Connecting To Server", Toast.LENGTH_LONG).show();

                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    progressDialog.dismiss();
                }

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


    public ArrayList<ProductDataHolder> parseJsonResponse(JSONArray response) {

        ArrayList<ProductDataHolder> productData = new ArrayList<>();

        try {

            // Parsing json array response
            // loop through each json object

            for (int i = 0; i < response.length(); i++) {

                JSONObject snyopsis = (JSONObject) response.get(i);

                ProductDataHolder productDataHolder = new ProductDataHolder();

                String id = snyopsis.getString("id");
                productDataHolder.setProductId(id);

                String image = snyopsis.getString("image");
                productDataHolder.setThumbNailUrl(image);

                String price = snyopsis.getString("price");
                productDataHolder.setPrice(price);
                Log.d("Product price", price);

                String name = snyopsis.getString("name");
                productDataHolder.setName(name);
                Log.d("Product Title", name);

                //CSV To Array
                String pSizes = snyopsis.getString("size");

                if (pSizes != null && !pSizes.isEmpty()) {
                    ArrayList<String> sizeList = CSV.csvToArray(pSizes);
                    productDataHolder.setProductSizeList(sizeList);
                    Log.d("Product SizeList", sizeList.toString());
                }

                //CSV To Array
                String colors = snyopsis.getString("color");

                if (colors != null && !colors.isEmpty()) {
                    ArrayList<String> colorList = CSV.csvToArray(colors);
                    productDataHolder.setProductColorList(colorList);
                    Log.d("Product SizeList", colorList.toString());
                }

                String description = snyopsis.getString("pdesc");
                productDataHolder.setProductDescription(description);

                //For Image URL in SLIDER FOR PRODUCT DETAILS
                //CSV To Array
                String iList = snyopsis.getString("slider");
                if (iList != null && !iList.isEmpty()) {
                    ArrayList<String> sliderList = CSV.csvToArray(iList);
                    productDataHolder.setImageUrlList(sliderList);
                    Log.d("Slider...i..List", iList);
                }

                productData.add(productDataHolder);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(RetriveMyApplicationContext.getAppContext(),
                    "Error: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }


        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        } else {
            progressDialog.dismiss();
        }

        return productData;
    }


    @Override
    public void itemClicked(View view, int position, ArrayList<ProductDataHolder> product) {

        ProductDataHolder currentProduct = product.get(position);
        String productTile = currentProduct.getName();
        String productPrice = currentProduct.getPrice();
        String productDes = currentProduct.getProductDescription();
        String productUrl = currentProduct.getThumbNailUrl();

        //Product ID
        String pId = currentProduct.getProductId();

        Intent i = new Intent(this, ProductDetails.class);
        i.putExtra("Title", productTile);
        i.putExtra("Price", productPrice);
        i.putExtra("DESC", productDes);
        i.putExtra("URL", productUrl);
        i.putExtra("PID", pId);

        ArrayList<String> productSize = currentProduct.getProductSizeList();
        if (productSize != null && !productSize.isEmpty()) {
            Log.d("ListViewItem", productSize.toString());
            i.putExtra("SizeList", productSize);
        }

        ArrayList<String> productColors = currentProduct.getProductColorList();
        if (productColors != null && !productColors.isEmpty()) {
            Log.d("ListViewItem", productColors.toString());
            i.putExtra("ColorList", productColors);
        }

        ArrayList<String> sliderUrls = currentProduct.getImageUrlList();
        if (sliderUrls != null) {
            Log.d("ListViewItem", sliderUrls.toString());
            i.putExtra("SliderList", sliderUrls);
        }

        startActivity(i);
    }

    @Override
    public void onRefresh() {

        swipeRefreshLayout.setRefreshing(true);
        getMeJson("http://store.jeenya.com/api/newproducts.php?categoryId=" + cat);

    }
}
