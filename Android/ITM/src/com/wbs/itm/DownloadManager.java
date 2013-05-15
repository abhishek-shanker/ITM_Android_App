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
 * Simple implementation of download manager with progress dialog.
 */
package com.wbs.itm;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
 
public class DownloadManager extends Activity {

    Button btnShowProgress;
    Button btnViewOnline;
    
    // Progress Dialog
    private ProgressDialog pDialog;
    ImageView my_image;
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;
 
    // File url to download
    String file_url;
    /*String fileExtenstion = MimeTypeMap.getFileExtensionFromUrl(file_url);
    String name = URLUtil.guessFileName(file_url, null, fileExtenstion);*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dwn_manager);
        file_url = getIntent().getExtras().getString("pathid");
        TextView url = (TextView) findViewById(R.id.url);
        url.setText("Link: ");
        url.append(file_url);
    	String[] values;
    	final String filename;
    	values=file_url.split("/");
    	filename=values[values.length-1];
    	Log.i("filename",filename);
    	if(filename == null)
    	{
    	    Toast.makeText(getApplicationContext(), "View this one online because it is a browser link & not a file.", Toast.LENGTH_LONG).show();
    	}
    	TextView textView=(TextView)findViewById(R.id.textView1);
    	
    	textView.setText("File name: ");
    	textView.append(filename);
        // show progress bar button
        btnShowProgress = (Button) findViewById(R.id.btnProgressBar);
        btnViewOnline = (Button) findViewById(R.id.btnOnlineView);
        /**
         * Show Progress bar click event
         * */
        btnShowProgress.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
                // starting new Async Task
                new DownloadFileFromURL().execute(file_url);
            }
        });
        //in.putExtra("onlinepathid", file_url);
        
        btnViewOnline.setOnClickListener(new View.OnClickListener() {
        	 
            @Override
            public void onClick(View v) {
                
            	  Intent in = new Intent(getApplicationContext(),
                 		 OnlineView.class);
                  // sending pid to next activity
            	  in.putExtra("onlinepathid", file_url);
            	  Log.i("url sent:", file_url);
            	  in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                  // starting new activity 
                  startActivity(in);
            }
        });
    }
 
    /**
     * Showing Dialog
     * */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case progress_bar_type: // we set this to 0
            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Downloading file. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setCancelable(true);
            pDialog.show();
            return pDialog;
        default:
            return null;
        }
    }
 
    /**
     * Background Async Task to download file
     * */
    class DownloadFileFromURL extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @SuppressWarnings("deprecation")
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }
 
        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
        	
            int count;
            try {
                URL url = new URL(f_url[0]);
                Uri u = Uri.parse( url.toURI().toString() );
                File f = new File("" + u);
                String filename=f.getName();
                URLConnection conection = url.openConnection();
                conection.connect();
                int lenghtOfFile = conection.getContentLength();
                Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
                //final String MEDIA_PATH = new String(baseDir+"/itmpdf/");
                // Output stream
                OutputStream output = new FileOutputStream(baseDir+"/itmpdf/"+filename);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return null;
        }
 
        /**
         * Updating progress bar
         * */
        @Override
		protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
       }
 
        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @SuppressWarnings("deprecation")
		@Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            dismissDialog(progress_bar_type);
            //Calling the listview activity to list the downloaded files.
            Intent in = new Intent(getApplicationContext(),
                    ScanPdf.class);        
            startActivity(in);
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