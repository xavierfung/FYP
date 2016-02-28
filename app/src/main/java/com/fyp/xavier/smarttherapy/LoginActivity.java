package com.fyp.xavier.smarttherapy;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by Xavier on 17/2/2016.
 */
public class LoginActivity extends Activity {

    private EditText editTextUserName;
    private EditText editTextPassword;
    private ImageButton LoginBtn;

    public static final String USER_NAME = "USERNAME";

    String username;
    String password;


    @Override
    public void onCreate(Bundle savedInstanceState) {
    /**/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        editTextUserName = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password);


    LoginBtn = (ImageButton) findViewById(R.id.imageButton1);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Add_Record_Test.class);
                startActivity(intent);
            }
        });

    }

}