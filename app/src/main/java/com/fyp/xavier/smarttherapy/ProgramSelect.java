package com.fyp.xavier.smarttherapy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Xavier on 16/1/2016.
 */
public class ProgramSelect extends Activity {
    private ImageButton iButton1;
    private ImageButton iButton2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    /**/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_select);
        iButton1 = (ImageButton) findViewById(R.id.imageButton1);
        iButton2 = (ImageButton) findViewById(R.id.imageButton2);

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