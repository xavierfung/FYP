package com.fyp.xavier.smarttherapy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fyp.xavier.smarttherapy.Add_Record_Test;
import com.fyp.xavier.smarttherapy.helper.BTConnetion;
import com.fyp.xavier.smarttherapy.ProgramSelect;
import com.fyp.xavier.smarttherapy.R;
import com.fyp.xavier.smarttherapy.ViewRecord;
import com.fyp.xavier.smarttherapy.helper.SQLiteHandler;
import com.fyp.xavier.smarttherapy.helper.SessionManager;

import java.util.HashMap;

public class MainActivity extends Activity {
    private static final String TAG_USERNAME = "username";
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
        setContentView(R.layout.activity_main);
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

        String authority = user.get("authority");
        final String username = user.get("username");

        // Displaying the user details on the screen
        twname.setText(authority);
        twplace.setText(username);

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
                Intent intent = new Intent(getApplicationContext(), ProgramSelect.class);
                Bundle bundle = new Bundle();
                bundle.putString("IN_username", username);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        iButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewRecord.class);
                Bundle bundle = new Bundle();
                bundle.putString("IN_username", username);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        image_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BTConnetion.class);
                ;
                startActivity(intent);
            }
        });

        tw_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BTConnetion.class);
                startActivity(intent);
            }
        });

        image_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        tw_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });


    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}