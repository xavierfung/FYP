package com.fyp.xavier.smarttherapy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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


    }
}