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
 * Welcome screen for Talky.
 */
package com.wbs.itm;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class TalkyLaunchActivity extends Activity{
	Button btnviewallq;
    Button btnaskq;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintalky);
 
        // Buttons
    
        btnviewallq = (Button) findViewById(R.id.btnviewq);
        btnaskq = (Button) findViewById(R.id.btnaskq);    
        btnviewallq.setOnClickListener(new View.OnClickListener() {
          	 
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AllQuestionActivity.class);
                startActivity(i);
 
            }
        });
        
        btnaskq.setOnClickListener(new View.OnClickListener() {
         	 
            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), NewQuestionActivity.class);
                startActivity(i);
 
            }
        });
 
        
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