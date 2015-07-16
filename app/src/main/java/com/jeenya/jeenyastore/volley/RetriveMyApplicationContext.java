package com.jeenya.jeenyastore.volley;

import android.app.Application;
import android.content.Context;

/**
 * Created by hp on 6/11/2015.
 */
public class RetriveMyApplicationContext extends Application {


    //Don't forget to mention RetriveMyApplicationContext Class in Manifests File otherwise it will throw NullPointer Exception
    // <application
    // android:name=".volley.RetriveMyApplicationContext"


    private static RetriveMyApplicationContext mRetriveMyApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mRetriveMyApplicationContext = this;
    }

    public static RetriveMyApplicationContext getInstance() {

        return mRetriveMyApplicationContext;
    }

    public static Context getAppContext() {

        return mRetriveMyApplicationContext.getApplicationContext();
    }
}
