package com.sapient.mymusicalbum.fragment;


import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sapient.mymusicalbum.activity.R;

import java.util.ArrayList;

public class JobDetailListFragment extends Fragment{

    TextView jobTitle, companyName;

    public JobDetailListFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_job_detail_list, container, false);
        jobTitle = (TextView)view.findViewById(R.id.jobTitle);
        companyName = (TextView)view.findViewById(R.id.companyName);
        return view;
    }


    public void setValues(String jobDetails){

        jobTitle.setText(jobDetails);

    }

}
