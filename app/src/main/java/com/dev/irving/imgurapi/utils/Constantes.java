package com.dev.irving.imgurapi.utils;

/**
 * Created by Irving on 16/03/2017.
 */
public class Constantes
{

    public static final boolean LOGGING = false;
    public static final String MY_IMGUR_CLIENT_ID = "791f8e3ea31b5de";
    public static final String MY_IMGUR_CLIENT_SECRET = "";
    public static final String MY_IMGUR_REDIRECT_URL = "http://android";

    public static String getClientAuth()
    {
        return "Client-ID " + MY_IMGUR_CLIENT_ID;
    }

}
