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
 * The title of this file says it all. 
 * The Main activity for all the buttons to navigate to other activities. 
 * Also has GCM code. So be careful while modifying it.
 */
package com.wbs.itm;

import static com.wbs.itm.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.wbs.itm.CommonUtilities.EXTRA_MESSAGE;
import static com.wbs.itm.CommonUtilities.SENDER_ID;

import java.io.File;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;

public class MainScreenActivity extends Activity{
	
	boolean success = true;
	ImageButton btnViewResults;
	ImageButton btnViewEvents;
	ImageButton btnViewAnnounce;
	ImageButton btnimg;
	ImageButton btnabt;
	ImageButton btntt;
	ImageButton btntalky;
	ImageButton dwnmanager;
    TextView lblMessage;
    
    // Asyntask
    AsyncTask<Void, Void, Void> mRegisterTask;
 
    // Alert dialog manager
    AlertDialogManager alert = new AlertDialogManager();
 
    // Connection detector
    ConnectionDetector cd;
 
    public static String name;
    public static String email;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        
        cd = new ConnectionDetector(getApplicationContext());
        
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(MainScreenActivity.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
           
        }        
            
        Intent i = getIntent();
    	name = i.getStringExtra("name");
        email = i.getStringExtra("email");	
    
        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(this);
 
        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(this);
 
        lblMessage = (TextView) findViewById(R.id.lblMessage);
 
        registerReceiver(mHandleMessageReceiver, new IntentFilter(
                DISPLAY_MESSAGE_ACTION));
 
        // Get GCM registration id
        final String regId = GCMRegistrar.getRegistrationId(this);
 
        // Check if regid already presents
        if (regId.equals("")) {
            // Registration is not present, register now with GCM
            GCMRegistrar.register(this, SENDER_ID);  
        } 
        else 
        {	
            // Device is already registered on GCM
            if (GCMRegistrar.isRegisteredOnServer(this)) {
                // Skips registration.
                Toast.makeText(getApplicationContext(), "Registration with server is verified!", Toast.LENGTH_LONG).show();
            }
            else 
            {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                final Context context = this;
                mRegisterTask = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        // Register on our server
                        // On server creates a new user
                        ServerUtilities.register(context, name, email, regId);
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {
                        mRegisterTask = null;
                    }
                };
                mRegisterTask.execute(null, null, null);
            }
        }

        File folder = new File(Environment.getExternalStorageDirectory() + "/itmpdf");
                
        if (!folder.exists()) {
    	    success = folder.mkdirs();
    	    if (success) {
       		 Log.i("directory created", "directory created");
       	} else {
       		 Log.i("directory not created", "directory not created"); 
       	}
        }
        else
        {
        	Log.i("Directorylog","Directory ALready Exists");
        }
    	

        // Buttons
        btnViewResults = (ImageButton) findViewById(R.id.btnViewResults);
        btnViewEvents = (ImageButton) findViewById(R.id.btnViewNotices);
        btnViewAnnounce = (ImageButton) findViewById(R.id.btnViewAnnounce);
        btntt = (ImageButton) findViewById(R.id.btntt);
        btntalky = (ImageButton) findViewById(R.id.btntalky);
        btnabt = (ImageButton) findViewById(R.id.btnabt);
        dwnmanager = (ImageButton) findViewById(R.id.dwnmanager);
        ImageButton ratemyapp = (ImageButton) findViewById(R.id.ratemyapp);
        
        btnViewResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AllResultsActivity.class);
                startActivity(i);
            }
        });
        
        btnViewEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AllEventsActivity.class);
                startActivity(i);
            }
        });
        
        btnViewAnnounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AllAnnActivity.class);
                startActivity(i);
            }
        });
        
        btntt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TimetableActivity.class);
                startActivity(i);
            }
        });
 
        btntalky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TalkyLaunchActivity.class);
                startActivity(i);
            }
        });
 
        
        btnabt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AllAbtActivity.class);
                startActivity(i);
            }
        });
        
        dwnmanager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ScanPdf.class);
                startActivity(i);
            }
        });
 
        
        ratemyapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				Uri uri = Uri.parse("market://details?id=" + getPackageName());
				Log.i("URI",uri.toString());
            	Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            	try {
            	  startActivity(goToMarket);
            	} catch (ActivityNotFoundException e) {
            	  Toast.makeText(getBaseContext(), "Couldn't launch the market", Toast.LENGTH_LONG).show();
            	}
            }
        });
    }
    
    /**
     * Receiving push messages
     * */
    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            // Waking up mobile if it is sleeping
            WakeLocker.acquire(getApplicationContext());
 
            /**
             * Take appropriate action on this message
             * depending upon your app requirement
             * I am just displaying it on the screen
             * */
 
            // Showing received message
            //lblMessage.append(newMessage + "\n");
            Toast.makeText(getApplicationContext(), "Hey DROIDS: " + newMessage, Toast.LENGTH_LONG).show();
 
            // Releasing wake lock
            WakeLocker.release();
        }
    };
 
    @Override
    protected void onDestroy() {
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
        try {
            unregisterReceiver(mHandleMessageReceiver);
            GCMRegistrar.onDestroy(this);
        } catch (Exception e) {
            Log.e("UnRegister Receiver Error", "> " + e.getMessage());
        }
        super.onDestroy();
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