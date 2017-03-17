package com.dev.irving.imgurapi.utils;

import android.util.Log;

/**
 * Created by Irving on 16/03/2017.
 */
public class Exepcion
{
    public static void w (String TAG, String msg){
        if(Constantes.LOGGING) {
            if (TAG != null && msg != null)
                Log.w(TAG, msg);
        }
    }
}
