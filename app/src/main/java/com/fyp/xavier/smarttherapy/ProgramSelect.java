package com.fyp.xavier.smarttherapy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fyp.xavier.smarttherapy.helper.SQLiteHandler;
import com.fyp.xavier.smarttherapy.helper.SessionManager;

import java.util.HashMap;

/**
 * Created by Xavier on 16/1/2016.
 */
public class ProgramSelect extends Activity {
    private ImageButton iButton1;
    private ImageButton iButton2;
    private TextView twname, twplace;
    private SQLiteHandler db;
    private SessionManager session;


    @Override
    public void onCreate(Bundle savedInstanceState) {
    /**/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_select);
        iButton1 = (ImageButton) findViewById(R.id.imageButton1);
        iButton2 = (ImageButton) findViewById(R.id.imageButton2);
        twname = (TextView) findViewById(R.id.name);
        twname = (TextView) findViewById(R.id.place);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());
/**
 if (!session.isLoggedIn()) {
 logoutUser();
 }
 **/
        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        twname.setText(name);
        twplace.setText(email);


        iButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProgramSelect.this, BTConnetion.class);
                startActivity(intent);
                finish();
            }
        });

        iButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProgramSelect.this, FunMode.class);
                startActivity(intent);
                finish();
            }
        });
    }
}