package com.jeenya.jeenyastore.cart;

/**
 * Created by hp on 6/30/2015.
 */
public class CartNotification {
    static Long mNotifCount;

    public static void setNotifCount(Long i) {
        mNotifCount = i;
    }

    public static Long getCount() {
        return mNotifCount;
    }

}
