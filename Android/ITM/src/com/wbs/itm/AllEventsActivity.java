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
 * This activity is used to fetch all important Notices.
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
 
public class AllEventsActivity extends ListActivity {
    private ProgressDialog pDialog;
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> NoticesList;
    private static String url_all_notices = "http://yourdomain.com/app/fetchnotice.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_NOTICES = "notices";
    private static final String TAG_PID = "nid";
    private static final String TAG_NAME = "notice";
    private static final String TAG_INFO = "noticeinfo";
    private static final String TAG_DATE = "date";
    // Notices JSONArray
    JSONArray allnotices = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_events);
        // Hashmap for ListView
        NoticesList = new ArrayList<HashMap<String, String>>();
        // Loading notices in Background Thread
        new LoadAllNotices().execute();
        // Get listview
        ListView lv = getListView();
        // on seleting single item
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // getting values from selected ListItem
                String pid = ((TextView) view.findViewById(R.id.einfo)).getText()
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
 
    }
 
    /**
     * Background Async Task to Load all notices by making HTTP Request
     * */
    class LoadAllNotices extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AllEventsActivity.this);
            pDialog.setMessage("Fetching Notices. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * getting JSON data from url
         * */
        @Override
		protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_notices, "GET", params);
 
            // Check your log cat for JSON reponse
            Log.d("All Notices: ", json.toString());
 
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                	allnotices = json.getJSONArray(TAG_NOTICES);
                    for (int i = 0; i < allnotices.length(); i++) {
                        JSONObject c = allnotices.getJSONObject(i);
                        // Storing each json item in variable
                        String id = c.getString(TAG_PID);
                        String name = c.getString(TAG_NAME);
                        String info = c.getString(TAG_INFO);
                        String date = c.getString(TAG_DATE);
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
                        // adding each child node to HashMap key => value
                        map.put(TAG_PID, id);
                        map.put(TAG_NAME, name);
                        map.put(TAG_INFO, info);
                        map.put(TAG_DATE, date);
                        // adding HashList to ArrayList
                        NoticesList.add(map);
                    }
                } else {
                    // nothing found exception to be implemented.
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
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
				public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                    		AllEventsActivity.this, NoticesList,
                            R.layout.list_item_eve, new String[] { TAG_PID,
                                    TAG_NAME, TAG_INFO, TAG_DATE },
                            new int[] { R.id.eid, R.id.ename, R.id.einfo, R.id.date});
                    // updating listview
                    setListAdapter(adapter);
                }
            });
        }
    }
    
    @Override
    protected void onStart() {
       super.onStart();
       EasyTracker.getInstance().activityStart(this); // Add this method
    }
  
    @Override
    protected void onStop() {
       super.onStop();
       EasyTracker.getInstance().activityStop(this); // Add this method
    }
}
