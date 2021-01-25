package com.example.pocedex.domain;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class Utils {

    private static final String APP_PREFERENCES = "commandfav";

    public static String getPreferenses() {
        return APP_PREFERENCES;
    }

    private static LocalSave localSave;

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public static void setLocalSave(LocalSave newLocalSave) {
        localSave = newLocalSave;
    }

    public static LocalSave getLocalSave() {
        return localSave;
    }
}
