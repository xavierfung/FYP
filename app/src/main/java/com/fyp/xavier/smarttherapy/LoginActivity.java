package com.fyp.xavier.smarttherapy;

import android.app.Activity;
import android.os.Bundle;
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
        LoginBtn = (ImageButton) findViewById(R.id.login_button);
    }

}