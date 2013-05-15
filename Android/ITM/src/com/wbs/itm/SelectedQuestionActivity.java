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
 * Just fetching the answers related to the selected questions.
 */
package com.wbs.itm;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
 
public class SelectedQuestionActivity extends ListActivity {
 

    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> selquesList;
  
    Intent intent;
    String qid;
    Intent newqintent;
    String newqid;
    TextView errorcode;
    private String url_all_selques = "http://yourdomain.com/app/fetchans.php";
 
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_SELQUES = "ques";
    private static final String TAG_newPID = "qid";
    private static final String TAG_NAME = "aname";
    private static final String TAG_INFO = "answer";
    private static final String TAG_DATE = "date";
    JSONArray selques = null;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_ans);
        selquesList = new ArrayList<HashMap<String, String>>();
  		Intent newqestionintent = getIntent();
        String newquestion = newqestionintent.getExtras().getString("TAG_QUESTION");
        new LoadAllSelques().execute();
        TextView text = (TextView) findViewById(R.id.selectedquestion);
        text.setText("Q. ");
        text.append(newquestion);
        ListView lv = getListView();
        Button gohome = (Button)findViewById(R.id.gohome);        
        		gohome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getApplicationContext(), TalkyLaunchActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                });
        
        Button ansbtn =(Button)findViewById(R.id.ansbtn);
        ansbtn.setOnClickListener(new Button.OnClickListener(){
        	@Override
        	 public void onClick(View v) {
        		newqintent = getIntent();
                newqid = newqintent.getExtras().getString("TAG_PID");
        		/*String newpid = ((TextView) findViewById(R.id.qid)).getText()
                        .toString();*/
     		   Intent mynewIntent = new Intent(SelectedQuestionActivity.this, NewAnswerActivity.class);
     		  mynewIntent.putExtra("TAG_newPID", newqid);
     		   SelectedQuestionActivity.this.startActivity(mynewIntent);
        	}});
        	lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // getting values from selected ListItem
            	// Do nothing! //Later on implement selective Replies.
            }
        });
    }

    class LoadAllSelques extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SelectedQuestionActivity.this);
            pDialog.setMessage("Loading Answers. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
		protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            intent = getIntent();
            qid = intent.getExtras().getString("TAG_PID");
            params.add(new BasicNameValuePair("qid",qid));  //<<<< add here
            JSONObject json = jParser.makeHttpRequest(url_all_selques, "GET", params);

            Log.d("All Answers: ", json.toString());
 
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                	selques = json.getJSONArray(TAG_SELQUES);
                    for (int i = 0; i < selques.length(); i++) {
                        JSONObject c = selques.getJSONObject(i);
                        String id = c.getString(TAG_newPID);
                        String name = c.getString(TAG_NAME);
                        String info = c.getString(TAG_INFO);
                        String date = c.getString(TAG_DATE);
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
                        // adding each child node to HashMap key => value
                        map.put(TAG_newPID, id);
                        map.put(TAG_NAME, name);
                        map.put(TAG_INFO, info);
                        map.put(TAG_DATE, date);
                        // adding HashList to ArrayList
                        selquesList.add(map);
                    }
                } else {
                   // nothing was found.
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
                    		SelectedQuestionActivity.this, selquesList,
                            R.layout.list_selected_ques, new String[] { TAG_newPID,
                                    TAG_NAME, TAG_INFO, TAG_DATE },
                            new int[] { R.id.qid, R.id.aname, R.id.answer, R.id.date});
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
