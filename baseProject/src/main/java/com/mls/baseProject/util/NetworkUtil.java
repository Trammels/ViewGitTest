package com.mls.baseProject.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkUtil {

    public static boolean hasNetwork(Context context) {
        if (context == null)
            return false;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (null == activeNetInfo || !activeNetInfo.isConnected()) {
            return false;
        }
        return true;
    }
}
