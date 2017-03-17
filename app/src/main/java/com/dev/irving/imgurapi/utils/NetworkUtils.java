package com.dev.irving.imgurapi.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Irving on 16/03/2017.
 */
public class NetworkUtils
{
    public static final String TAG = NetworkUtils.class.getSimpleName();
    public static boolean isConnected(Context mContext) {
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null)
            {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            }
        }catch (Exception ex){
            Exepcion.w(TAG, ex.getMessage());
        }
        return false;
    }

}
