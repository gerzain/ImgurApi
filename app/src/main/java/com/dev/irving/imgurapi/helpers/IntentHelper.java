package com.dev.irving.imgurapi.helpers;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Irving on 16/03/2017.
 *
 *
 */
public class IntentHelper
{
    public static int FILE_PICK = 1001;
    public static void chooseFileIntent(Activity activity)
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activity.startActivityForResult(intent, FILE_PICK);
    }
}
