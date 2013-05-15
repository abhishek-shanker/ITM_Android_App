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
 * Activity that tells about the application and the developer.
 */
package com.wbs.itm;

import com.google.analytics.tracking.android.EasyTracker;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
 
public class AllAbtActivity extends Activity {

	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.all_abt);
	    
	        if (AppStatus.getInstance(this).isOnline(this)) {

	            Toast t = Toast.makeText(this,"You are online!!!!", Toast.LENGTH_LONG);
	            t.show();

	        } else {

	            Toast t = Toast.makeText(this,"You are not online!!!!", Toast.LENGTH_LONG);
	            t.show();
	            //Log.v("Home", "You are not connected!!!!");    
	        }
	  ImageView img = (ImageView)findViewById(R.id.bttnfb);
      img.setOnClickListener(new View.OnClickListener(){
          @Override
		public void onClick(View v){
              Intent intent = new Intent();
              intent.setAction(Intent.ACTION_VIEW);
              intent.addCategory(Intent.CATEGORY_BROWSABLE);
              intent.setData(Uri.parse("http://facebook.com/ansh.parasher"));
              startActivity(intent);
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
