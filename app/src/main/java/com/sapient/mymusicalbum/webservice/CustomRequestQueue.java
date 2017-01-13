package com.sapient.mymusicalbum.webservice;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by kshan5 on 1/11/2017.
 */

public class CustomRequestQueue {

    private static CustomRequestQueue customRequestQueue;
    private static RequestQueue requestQueue;
    private static Context myContext;


    private CustomRequestQueue(Context context){
        myContext = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized CustomRequestQueue getInstance(Context context)
    {
        if(customRequestQueue == null){
            customRequestQueue = new CustomRequestQueue(context);
        }
        return customRequestQueue;
    }

    public RequestQueue getRequestQueue(){

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(myContext);
        }
        return requestQueue;
    }
}
