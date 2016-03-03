package com.fyp.xavier.smarttherapy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fyp.xavier.smarttherapy.helper.BTConnetion;
import com.fyp.xavier.smarttherapy.helper.SQLiteHandler;
import com.fyp.xavier.smarttherapy.helper.SessionManager;

import java.util.HashMap;

/**
 * Created by Xavier on 16/1/2016.
 */
public class ProgramSelect extends Activity {
    private ImageButton iButton1, iButton2;
    private TextView twname, twplace;
    private SQLiteHandler db;
    private SessionManager session;
    private String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    /**/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_select);

        Bundle bundlec = this.getIntent().getExtras();
        username = bundlec.getString("IN_username");

        iButton1 = (ImageButton) findViewById(R.id.imageButton1);
        iButton2 = (ImageButton) findViewById(R.id.imageButton2);
        twname = (TextView) findViewById(R.id.name);
        twplace = (TextView) findViewById(R.id.place);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

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
                Intent intent = new Intent(getApplicationContext(), BTConnetion.class);
                Bundle bundle = new Bundle();
                bundle.putString("IN_username", username);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        iButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FunMode.class);
                startActivity(intent);
                finish();
            }
        });
    }
}