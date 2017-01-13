package com.sapient.mymusicalbum.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sapient.mymusicalbum.db.helper.DatabaseConfig;
import com.sapient.mymusicalbum.db.helper.DatabaseHelper;

/**
 *
 */
public class LoginActivity extends AppCompatActivity {

    EditText emailId, password;
    public static TextView createAccount, errorView;
    Button loginBtn;
    DatabaseHelper databaseHelper;
    String loginKey="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseHelper = new DatabaseHelper(DatabaseConfig.getInstance(this.getApplicationContext()));
        initializeViewComponents();
        if(getIntent()!= null){
            loginKey = getIntent().getStringExtra("loginKey");
            if(null != loginKey){
                errorView.setText(loginKey);
                errorView.setTextColor(Color.GREEN);
            }
        }
    }


    /**
     * Init View elements in login activity
     *
     * */
    private void initializeViewComponents()
    {
        emailId = (EditText)findViewById(R.id.emailId);
        password = (EditText)findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.signIn);
        createAccount = (TextView)findViewById(R.id.creaateAccount);
        errorView = (TextView)findViewById(R.id.errorView);
    }

    /**
     *
     * @param view
     */
    public void loginToMainPage(View view){


        if (emailId.getText().length() == 0) {
            emailId.requestFocus();
            emailId.setError(getText(R.string.log_email_empty));
            emailId.setFocusable(true);
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailId.getText()).matches()) {
            emailId.setError(getText(R.string.log_email_valid));
            emailId.requestFocus();
            emailId.setFocusable(true);
            return;
        }

        if(password.getText().length() ==0){

            password.setFocusable(true);
            password.requestFocus();
            password.setError(getText(R.string.log_pass_empty));
            return;
        }
        boolean success =  databaseHelper.loginUser(emailId.getText().toString(), password.getText().toString());

        if(success)
        {
            Toast.makeText(LoginActivity.this, getText(R.string.log_success), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getBaseContext(), AlbumListViewActivity.class);
            startActivity(intent);
            databaseHelper.dbClose();
            finish();
        }else {
            errorView.setText(getText(R.string.log_fail));
            errorView.setTextColor(Color.RED);
            return;
        }
    }

    /**
     *  start register activity
     */
    public void startRegisterActivity(View view){
        emailId.setText("");
        password.setText("");
        Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
        startActivity(intent);
    }
}
