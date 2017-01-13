package com.sapient.mymusicalbum.fragment;


import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sapient.mymusicalbum.activity.R;
import com.sapient.mymusicalbum.webservice.CustomJSONObjectRequest;
import com.sapient.mymusicalbum.webservice.CustomRequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobCategoryFragment extends ListFragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    public static final String URL = "http://oilpeople.talenetic.com/api/SearchJob?limit=5&jobkeyword=oil&countrytext=USA&location=NewYork&apikey=1111111111&siteid=1";
    private RequestQueue reqQueue;
    private static String TAG = "JobSearchActivity";
    CustomJSONObjectRequest jsonObjectRequest;
    ArrayList companyList;

    public JobCategoryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_category, container, false);
        initializeRequestQueue();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, companyList);
        setListAdapter(adapter);
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        JobDetailListFragment detailListFragment = (JobDetailListFragment)getFragmentManager().findFragmentById(R.id.jobDetailFragment);
        detailListFragment.setValues(companyList.get(position).toString());

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //Toast.makeText(this, "Error Response :"+error.getMessage(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray jobListArray;
        try{
            jobListArray = response.getJSONArray("Indeedjobslist");
            getCompanyList(jobListArray);
        }catch (JSONException e){
            Log.e("Response Exception - ", e.getMessage());
        }
    }


    public void initializeRequestQueue(){
        reqQueue = CustomRequestQueue.getInstance(getActivity().getApplication()).getRequestQueue();
        jsonObjectRequest = new CustomJSONObjectRequest(Request.Method.GET, URL, new JSONObject(), this, this);
        jsonObjectRequest.setTag(TAG);
        reqQueue.add(jsonObjectRequest);
    }

    public void getCompanyList(JSONArray jsonArray){

       companyList = new ArrayList<String>();
        for(int i=0; i<jsonArray.length();i++)
        {
            try {
                companyList.add(jsonArray.getString(1));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}
