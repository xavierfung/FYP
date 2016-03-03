package com.fyp.xavier.smarttherapy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fyp.xavier.smarttherapy.app.AppConfig;
import com.fyp.xavier.smarttherapy.helper.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xavier on 26/2/2016.
 */


public class Add_Record_Test extends Activity {

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static String url_upload = AppConfig.URL_UPLOAD;
    JSONParser jsonParser = new JSONParser();
    int score;
    // Progress Dialog
    private ProgressDialog pDialog;
    private TextView tw_score, tw_username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    /**/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_record);

        // String score = intent.getExtras().getString("TAG_SCORE");
        Bundle bundle = this.getIntent().getExtras();
        int score = bundle.getInt("TAG_SCORE");
        //Bundle bundlea = this.getIntent().getExtras();
        //String username = bundlea.getString("IN_username");

        tw_score = (TextView) findViewById(R.id.finalscore);
        tw_score.setText(String.valueOf(score));
        tw_username = (TextView) findViewById(R.id.finalusername);
        //tw_username.setText(String.valueOf(username));

        Button btnUploadScore = (Button) findViewById(R.id.btnUpload);

        // button click event
        btnUploadScore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                new UploadNewScore().execute();
            }
        });

    }

    /**
     * Background Async Task to Create new product
     */
    class UploadNewScore extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Add_Record_Test.this);
            pDialog.setMessage("Uploading Results..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            String username = "xavierfung";
            String score = "50";
            //inputPrice.getText().toString();
            String remarks = "GOOD";
            //inputDesc.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("score", score));
            params.add(new BasicNameValuePair("remarks", remarks));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_upload,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), ViewRecord.class);
                    startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
}
