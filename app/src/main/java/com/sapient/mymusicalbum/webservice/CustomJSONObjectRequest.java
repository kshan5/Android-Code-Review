package com.sapient.mymusicalbum.webservice;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kshan5 on 1/11/2017.
 */

public class CustomJSONObjectRequest extends JsonObjectRequest {


    public CustomJSONObjectRequest(int methodType, String url, JSONObject jsonReq,
                                   Response.Listener resListener, Response.ErrorListener errorListener)
    {
        super(methodType, url, jsonReq, resListener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        return headers;
    }
}
