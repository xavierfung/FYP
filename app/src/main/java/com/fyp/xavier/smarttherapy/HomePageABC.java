package com.fyp.xavier.smarttherapy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fyp.xavier.smarttherapy.helper.SQLiteHandler;
import com.fyp.xavier.smarttherapy.helper.SessionManager;

import java.util.HashMap;

/**
 * Created by Xavier on 16/1/2016.
 */
public class HomePageABC extends Activity {

    private ImageButton iButton1;
    private ImageButton iButton2;
    private TextView twname;
    private TextView twplace;
    private ImageView image_setting;
    private TextView tw_setting;
    private ImageView image_login;
    private TextView tw_login;
    private SQLiteHandler db;
    private SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    /**/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        iButton1 = (ImageButton) findViewById(R.id.imageButton1);
        iButton2 = (ImageButton) findViewById(R.id.imageButton2);
        twname = (TextView) findViewById(R.id.name);
        twplace = (TextView) findViewById(R.id.place);
        image_setting = (ImageView) findViewById(R.id.ic_settings);
        tw_setting = (TextView) findViewById(R.id.setting);
        image_login = (ImageView) findViewById(R.id.ic_account);
        tw_login = (TextView) findViewById(R.id.login);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        twname.setText(name);
        twplace.setText(email);

        // Logout button click event
        /**  btnLogout.setOnClickListener(new View.OnClickListener() {

        @Override public void onClick(View v) {
        logoutUser();
        }
        });
         **/
        iButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageABC.this, ProgramSelect.class);
                startActivity(intent);
            }
        });

        iButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageABC.this, ViewRecord.class);
                startActivity(intent);
            }
        });

        image_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageABC.this, BTConnetion.class);
                startActivity(intent);
            }
        });

        tw_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageABC.this, BTConnetion.class);
                startActivity(intent);
            }
        });

        image_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageABC.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        tw_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageABC.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(HomePageABC.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}