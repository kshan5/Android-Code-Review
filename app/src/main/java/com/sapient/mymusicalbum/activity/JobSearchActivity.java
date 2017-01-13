package com.sapient.mymusicalbum.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sapient.mymusicalbum.webservice.CustomRequestQueue;
import com.sapient.mymusicalbum.webservice.CustomJSONObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class JobSearchActivity extends FragmentActivity implements Response.Listener<JSONObject>, Response.ErrorListener, View.OnClickListener{

    public static final String URL = "http://oilpeople.talenetic.com/api/SearchJob?limit=5&jobkeyword=*&countrytext=USA&location=NewYork&apikey=1111111111&siteid=1";
    //public static final String URL = "http://musicbrainz.org/ws/2/release/59211ea4-ffd2-4ad9-9a4e-941d3148024a?inc=artist-credits+labels+discids+recordings&fmt=json";
    Button jobSearchButton;
    private RequestQueue reqQueue;
    private static String TAG = "JobSearchActivity";
    CustomJSONObjectRequest jsonObjectRequest;
    TextView jobDetails;
    public static ArrayList<String> companyList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search);

        jobSearchButton = (Button) findViewById(R.id.job_search);
        jobDetails = (TextView) findViewById(R.id.job_details);

    }

    @Override
    protected void onStart() {
        super.onStart();
        reqQueue = CustomRequestQueue.getInstance(this.getApplication()).getRequestQueue();
        jsonObjectRequest = new CustomJSONObjectRequest(Request.Method.GET, URL, new JSONObject(), this, this);
        jsonObjectRequest.setTag(TAG);
        jobSearchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        reqQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(reqQueue!=null){
            reqQueue.cancelAll(TAG);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Error Response :"+error.getMessage(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        try {
            JSONArray jobListArray = response.getJSONArray("Indeedjobslist");
            getCompanyList(jobListArray);

           // Toast.makeText(this, jsonObject.getString("jobtitle").toString(),Toast.LENGTH_LONG).show();

            /*for(int i=0; i<jsonArray.length();i++)
            {
                System.out.println("Json Array : "+jsonArray.get(i));
                Log.e("Values :", "Index :"+i+" - "+jsonArray.get(i));
                Toast.makeText(this, jsonArray.get(i).toString(),Toast.LENGTH_LONG).show();
            }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getCompanyList(JSONArray jsonArray){

        companyList = new ArrayList<String>();
        HashMap<String, String> jobDetails = new HashMap<String, String>();
        for(int i=0; i<jsonArray.length();i++)
        {
            try {
                companyList.add(jsonArray.getString(1));
                Toast.makeText(this, "Value in First Index -"+jsonArray.getString(1), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}
