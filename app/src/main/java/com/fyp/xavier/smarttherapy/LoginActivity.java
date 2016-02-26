package com.fyp.xavier.smarttherapy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Xavier on 17/2/2016.
 */
public class LoginActivity extends Activity {

    private ImageButton LoginBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    /**/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
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