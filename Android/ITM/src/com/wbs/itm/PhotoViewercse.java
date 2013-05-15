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
 * This is for the timetable activity.
 */
package com.wbs.itm;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebSettings; 

public class PhotoViewercse extends Activity { 
	   private WebView webview;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.photoview);
	        webview = (WebView) findViewById(R.id.my_img);
	        webview.getSettings().setBuiltInZoomControls(true);
	        webview.getSettings().setUseWideViewPort(true);
	        webview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
	        String id = getIntent().getExtras().getString("pathid");
	        webview.loadUrl(id);
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
