/**
 * Copyright 2013 Bhavyanshu Parasher   
 * This file is part of "ITM University Android Application".
 * "ITM University Android Application" is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 * "ITM University Android Application" is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with "ITM University Android Application". 
 * If not, see http://www.gnu.org/licenses/.
 */

/**
 * This Activity fetches everything displayed in the academic calendar section.
 */
package com.wbs.itm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.analytics.tracking.android.EasyTracker;
 
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
 
public class AllAnnActivity extends ListActivity {
    
    private ProgressDialog pDialog; 
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> AnouncementList;
    private static String url_academic_calendar = "http://yourdomain.com/app/fetchimp.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ANN = "impa";
    private static final String TAG_PID = "impid";
    private static final String TAG_NAME = "impname";
    private static final String TAG_INFO = "impinfo";
    private static final String TAG_DATE= "impdate";
    // Announcement Notices JSONArray
    JSONArray notices = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_ann);
        // Hashmap for ListView
        AnouncementList = new ArrayList<HashMap<String, String>>();
        // Loading announcements in Background Thread
        new LoadAllAnns().execute();
        // Get listview
        ListView lv = getListView();
        // on seleting single item
        lv.setOnItemClickListener(new OnItemClickListener() {
        	 @Override
             public void onItemClick(AdapterView<?> parent, View view,
                     int position, long id) {
                 // getting values from selected ListItem
                 String pid = ((TextView) view.findViewById(R.id.impinfo)).getText()
                         .toString();
                 // Starting new intent
                 Intent in = new Intent(getApplicationContext(),
                         DownloadManager.class);
                 // sending pid to next activity
                 in.putExtra("pathid", pid);
                 // starting new activity and expecting some response back
                 startActivityForResult(in, 100);
             }
         });
    }

    //Check response
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }
 
    /**
     * Background Async Task to Load all Announcements by making HTTP Request
     * */
    class LoadAllAnns extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AllAnnActivity.this);
            pDialog.setMessage("Loading details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * getting All Anns from url
         * */
        @Override
		protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_academic_calendar, "GET", params);
 
            // Check your log cat for JSON reponse
            Log.d("All Anns: ", json.toString());
 
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {

                    // Getting Array of Anns
                	notices = json.getJSONArray(TAG_ANN);
 
                    // looping through All Anns
                    for (int i = 0; i < notices.length(); i++) {
                        JSONObject c = notices.getJSONObject(i); 
                        // Storing each json item in variable
                        String id = c.getString(TAG_PID); 
                        String name = c.getString(TAG_NAME);
                        String info = c.getString(TAG_INFO); //contains the URL
                        String date = c.getString(TAG_DATE);
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
                        // Mapping HashMap key => value
                        map.put(TAG_PID, id);
                        map.put(TAG_NAME, name);
                        map.put(TAG_INFO, info);
                        map.put(TAG_DATE, date);
                        // adding HashList to ArrayList
                        AnouncementList.add(map);
                    }
                } else {
                    /** nothing found
                  	 * Need to implement an error message if nothing was found.
                  	 **/
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
		protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting JSON data
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
				public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                    		AllAnnActivity.this, AnouncementList,
                            R.layout.list_item_ann, new String[] { TAG_PID,
                                    TAG_NAME, TAG_INFO, TAG_DATE },
                            new int[] { R.id.impid, R.id.impame, R.id.impinfo, R.id.impdate});
                    setListAdapter(adapter);
                }
            });
        }
    }
    
    @Override
    protected void onStart() {
       super.onStart();
       EasyTracker.getInstance().activityStart(this); 
    }
  
    @Override
    protected void onStop() {
       super.onStop();
       EasyTracker.getInstance().activityStop(this); 
    }
} 
