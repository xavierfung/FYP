package com.fyp.xavier.smarttherapy;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by Xavier on 17/1/2016.
 */
public class FunMode extends Activity {

    private WebView Gifview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    /**/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_mode);
        Gifview = (WebView) findViewById(R.id.web_view);
        Gifview.loadUrl("file:///android_asset/finger_1.GIF");
        Gifview.getSettings().setLoadWithOverviewMode(true);
        Gifview.getSettings().setUseWideViewPort(true);
    }
}
