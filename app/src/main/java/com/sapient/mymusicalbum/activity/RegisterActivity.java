package com.sapient.mymusicalbum.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sapient.mymusicalbum.constants.MusicAlbumConstants;
import com.sapient.mymusicalbum.dataobjects.Country;
import com.sapient.mymusicalbum.db.helper.DatabaseConfig;
import com.sapient.mymusicalbum.db.helper.DatabaseHelper;
import com.sapient.mymusicalbum.user.UserDetails;
import com.sapient.mymusicalbum.webservice.CustomJSONObjectRequest;
import com.sapient.mymusicalbum.webservice.CustomRequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Register activity to store user details to database
 */

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
                                                                    View.OnFocusChangeListener,
                                                                    Response.Listener<JSONObject>,
                                                                    Response.ErrorListener{

    private EditText firstName, lastName, email, mobile, password;
    private RadioGroup radioAgeGroup;
    private RadioButton radioButton;
    private Spinner ageSelectorSpinner, countrySpinner, stateSpinner;
    private CheckBox ckBoxTa, ckBoxHi, ckBoxEn;
    private Button regButton;
    private TextView error;
    private StringBuilder stringBuilder;
    private String spiinerText="";
    private boolean validator = false;
    private ArrayList<String> statesArray = new ArrayList<String>();

    DatabaseHelper databaseHelper;
    String[] ageGroup = {"Select Age Group", "Child", "Teen Age", "Middle Age", "Senior Citizen"};
    ArrayList<Country> countryList;
    Country country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // initialize view elements
        initializeViewElements();
    }

    /**
     *  initialize view elements in register page
     */
    public void initializeViewElements()
    {
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastname);

        email = (EditText)findViewById(R.id.emailId);
        password = (EditText)findViewById(R.id.password);
        mobile = (EditText)findViewById(R.id.mobilenumber);
        mobile.setOnFocusChangeListener(this);

        radioAgeGroup = (RadioGroup)findViewById(R.id.genderGroup);

        ckBoxEn = (CheckBox)findViewById(R.id.ln_en);
        ckBoxTa = (CheckBox)findViewById(R.id.ln_ta);
        ckBoxHi = (CheckBox)findViewById(R.id.ln_hi);

        regButton = (Button)findViewById(R.id.register);
        stringBuilder = new StringBuilder();

        error = (TextView)findViewById(R.id.errorView);

        ageSelectorSpinner = (Spinner)findViewById(R.id.age_spinner);
        ageSelectorSpinner.setOnItemSelectedListener(this);
        ArrayAdapter ageAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,ageGroup);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSelectorSpinner.setAdapter(ageAdapter);

        countrySpinner = (Spinner)findViewById(R.id.country);
        countrySpinner.setOnItemSelectedListener(this);
        ArrayAdapter<Country> countryAdapter = new ArrayAdapter<Country>(this,android.R.layout.simple_spinner_item, putCountryDetails());
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryAdapter);

        stateSpinner = (Spinner)findViewById(R.id.states);

        databaseHelper = new DatabaseHelper(DatabaseConfig.getInstance(this.getApplicationContext()));
    }

    /**
     * Clear input fields
     */
    public void clearInputValues(){
        firstName.setText("");
        lastName.setText("");
        email.setText("");
        mobile.setText("");
        password.setText("");
    }

    /**
     * add country list to arrayList
     * @return
     */
    private ArrayList<Country> putCountryDetails()
    {
        countryList = new ArrayList();
        countryList.add(new Country("Unknown", "Select Country"));
        countryList.add(new Country("AUS", "Australia"));
        countryList.add(new Country("CN", "China"));
        countryList.add(new Country("IN", "India"));
        countryList.add(new Country("UK", "United Kingdom"));
        countryList.add(new Country("USA", "United States"));

        return countryList;
    }
    /**
     * validate mandatory fields
     */
    public boolean validation() {

        if (firstName.getText().length() == 0) {
            firstName.requestFocus();
            firstName.setError(getText(R.string.log_first_name));
            firstName.setFocusable(true);
            return validator;
        }

        if (email.getText().length() == 0) {
            email.requestFocus();
            email.setError(getText(R.string.log_email_empty));
            email.setFocusable(true);
            return validator;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
            email.setError(getText(R.string.log_email_valid));
            email.requestFocus();
            email.setFocusable(true);
            return validator;
        }

        if(mobile.getText().length()==0){
            mobile.setFocusable(true);
            mobile.requestFocus();
            mobile.setError(getText(R.string.log_mobile_empty));
            return validator;
        }else if(mobile.getText().length()<10){
            mobile.setFocusable(true);
            mobile.requestFocus();
            mobile.setError(getText(R.string.log_mobile_valid));
            return validator;
        }

        if(password.getText().length() ==0){
            password.setFocusable(true);
            password.requestFocus();
            password.setError(getText(R.string.log_pass_empty));
            return validator;
        }
        return true;
    }

    /**
     * get language selected by the user
     */
    private void getLanguage() {

        if(ckBoxEn.isChecked()){
            stringBuilder.append("English");
        }
        if(ckBoxTa.isChecked()){
            stringBuilder.append(",").append("Tamil");
        }
        if(ckBoxTa.isChecked()){
            stringBuilder.append(",").append("Hindi");
        }
    }

    /**
     *
     * @param view
     * register user details into database
     *
     */
    public void registerUserDetails(View view)
    {
        if(validation()){
            int selectedId = radioAgeGroup.getCheckedRadioButtonId();
            radioButton = (RadioButton) findViewById(selectedId);

            //get language selected by the user
            getLanguage();

            databaseHelper.registerUserData(requestParameters());
            clearInputValues();

            // start login activity with success message
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("loginKey", getText(R.string.login_msg));
            startActivity(intent);
            databaseHelper.dbClose();
            finish();
        }
    }

    /**
     * get user input and assign to user data object
     * @return UserDetails
     */
    public UserDetails requestParameters(){

        UserDetails userDetails = new UserDetails();
        userDetails.setFirstName(firstName.getText().toString());
        userDetails.setLastName(lastName.getText().toString());
        userDetails.setEmailId(email.getText().toString());
        userDetails.setMobileNumber(mobile.getText().toString());
        userDetails.setPassword(password.getText().toString());
        userDetails.setGender(radioButton.getText().toString());
        userDetails.setLanguage(stringBuilder.toString());
        userDetails.setAgeGroup(spiinerText);
        return userDetails;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View arg1, int position,long id) {

        switch(adapterView.getId())
        {
            case R.id.age_spinner:
                spiinerText = ageGroup[position];
                //Toast.makeText(this, "Age Title - "+spiinerText, Toast.LENGTH_LONG).show();
                break;
            case R.id.country:
                country = (Country)adapterView.getSelectedItem();
                getStatesDetails();
               ArrayAdapter stateAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, statesArray);
                stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                stateSpinner.setAdapter(stateAdapter);

                break;
             default:

        }
        spiinerText = ageGroup[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        int flag = databaseHelper.checkUserAvailabilty(email.getText().toString());
        if(flag>0)
        {
            email.requestFocus();
            email.setError(getText(R.string.reg_user_avail));
            email.setFocusable(true);
        }
    }


    @Override
    public void onResponse(JSONObject response) {

        try {
            JSONObject json = response.getJSONObject("RestResponse");
            JSONArray jsonArray = json.getJSONArray("result");

            Log.e("Response JSON Object :",jsonArray.toString());

            for(int i=0; i<jsonArray.length();i++){
                JSONObject countryObject = jsonArray.getJSONObject(i);
                statesArray.add(countryObject.getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(this, "Unknown Source", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void getStatesDetails(){

        RequestQueue requestQueue = CustomRequestQueue.getInstance(this.getApplication()).getRequestQueue();

        String URL = MusicAlbumConstants.GET_STATES_URL;
        Log.e("URL - ",URL);
        CustomJSONObjectRequest jsonObjectRequest = new CustomJSONObjectRequest(Request.Method.GET, URL, new JSONObject(), this, this);
        requestQueue.add(jsonObjectRequest);
    }
}
