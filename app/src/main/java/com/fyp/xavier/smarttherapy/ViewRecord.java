package com.fyp.xavier.smarttherapy;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.fyp.xavier.smarttherapy.app.AppConfig;
import com.fyp.xavier.smarttherapy.helper.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Xavier on 16/1/2016.
 */
public class ViewRecord extends ListActivity {

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_RECORDS = "records";
    private static final String TAG_UID = "uid";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_CREATED_AT = "created_at";
    private static final String TAG_SCORE = "score";
    private static String url_get_records = AppConfig.URL_GET;
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> recordsList;
    // products JSONArray
    JSONArray records = null;
    // Progress Dialog
    private ProgressDialog pDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    /**/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_my_record);
        // Hashmap for ListView
        recordsList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        new LoadAllRecords().execute();
        // Get listview
        ListView lv = getListView();

        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String uid = ((TextView) view.findViewById(R.id.uid)).getText()
                        .toString();

                // Starting new intent
                /**      Intent in = new Intent(getApplicationContext(),
                 EditProductActivity.class);
                 // sending pid to next activity
                 in.putExtra(TAG_UID, uid);

                 // starting new activity and expecting some response back
                 startActivityForResult(in, 100);

                 **/
            }
        });

    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     */
    class LoadAllRecords extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ViewRecord.this);
            pDialog.setMessage("Loading records. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_get_records, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Records: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    records = json.getJSONArray(TAG_RECORDS);

                    // looping through All Products
                    for (int i = 0; i < records.length(); i++) {
                        JSONObject c = records.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_UID);
                        String username = c.getString(TAG_USERNAME);
                        String created_at = "           created at" + c.getString(TAG_CREATED_AT);
                        String score = c.getString(TAG_SCORE);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        //  map.put(TAG_UID, id);
                        //  map.put(TAG_USERNAME, username);
                        map.put(TAG_CREATED_AT, created_at);
                        map.put(TAG_SCORE, score);

                        // adding HashList to ArrayList
                        recordsList.add(map);
                    }
                } else {
                    /**     // no products found
                     // Launch Add New product Activity
                     Intent i = new Intent(getApplicationContext(),
                     NewProductActivity.class);
                     // Closing all previous activities
                     i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     startActivity(i);
                     **/
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
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            ViewRecord.this, recordsList,
                            R.layout.list_item, new String[]{
                            //TAG_UID, TAG_USERNAME,
                            TAG_CREATED_AT, TAG_SCORE},
                            new int[]{R.id.created_at, R.id.score});
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }
}