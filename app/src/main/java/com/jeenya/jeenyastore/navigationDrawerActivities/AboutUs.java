package com.jeenya.jeenyastore.navigationDrawerActivities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.jeenya.jeenyastore.R;

public class AboutUs extends AppCompatActivity {

    Toolbar toolbar_aboutUs;
    LinearLayout callPhone,callMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        configureToolBar();
        callPhone = (LinearLayout) findViewById(R.id.call_phone);
        callPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDialerPhone();
            }
        });

        callMobile= (LinearLayout) findViewById(R.id.call_mobile);
        callMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9860203102"));
                startActivity(intent);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_us, menu);
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


    private void configureToolBar() {


        //Adding Custom Tool Bar
        toolbar_aboutUs = (Toolbar) findViewById(R.id.app_bar_aboutUS); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar_aboutUs);  // Setting toolbar as the ActionBar with setSupportActionBar() call


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

    public void goToDialerPhone() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:016200169"));
        startActivity(intent);
    }
}


