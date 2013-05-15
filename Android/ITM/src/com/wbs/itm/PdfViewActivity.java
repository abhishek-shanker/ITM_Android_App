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
 * This is an initializer for pdf viewer. 
 * Thanks to this github repository -> https://github.com/jblough/Android-Pdf-Viewer-Library
 */
package com.wbs.itm;

import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import net.sf.andpdf.pdfviewer.PdfViewerActivity;

public class PdfViewActivity extends PdfViewerActivity { 	 
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        //setContentView(R.layout.pdfview);
	    }

	    @Override
		public int getPreviousPageImageResource() {
	        return R.drawable.left_arrow;
	    }

	    @Override
		public int getNextPageImageResource() {
	        return R.drawable.right_arrow;
	    }

	    @Override
		public int getZoomInImageResource() {
	        return R.drawable.zoom_in;
	    }

	    @Override
		public int getZoomOutImageResource() {
	        return R.drawable.zoom_out;
	    }

	    @Override
		public int getPdfPasswordLayoutResource() {
	        return R.layout.pdf_file_password;
	    }

	    @Override
		public int getPdfPageNumberResource() {
	        return R.layout.dialog_pagenumber;
	    }

	    @Override
		public int getPdfPasswordEditField() {
	        return R.id.etPassword;
	    }

	    @Override
		public int getPdfPasswordOkButton() {
	        return R.id.btOK;
	    }

	    @Override
		public int getPdfPasswordExitButton() {
	        return R.id.btExit;
	    }

	    @Override
		public int getPdfPageNumberEditField() {
	        return R.id.pagenum_edit;
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