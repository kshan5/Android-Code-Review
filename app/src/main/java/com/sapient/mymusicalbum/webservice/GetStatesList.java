package com.sapient.mymusicalbum.webservice;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by kshan5 on 1/12/2017.
 */

public class GetStatesList implements Response.Listener<JSONObject>, Response.ErrorListener{

    Context context;

    public GetStatesList(Context context){
        this.context = context;

    }

    @Override
    public void onResponse(JSONObject response) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
