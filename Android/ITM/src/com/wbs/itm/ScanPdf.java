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

package com.wbs.itm;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.analytics.tracking.android.EasyTracker;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;

 
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
 
public class ScanPdf extends ListActivity {
    // Songs list
    public ArrayList<HashMap<String, String>> fileList = new ArrayList<HashMap<String, String>>();
    boolean success = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library);
        if (AppStatus.getInstance(this).isOnline(this)) {
            /*Toast t = Toast.makeText(this,"", Toast.LENGTH_LONG);
            t.show();*/
        } else {
            Toast t = Toast.makeText(this,"File Manager opened for offline view!", Toast.LENGTH_LONG);
            t.show();
        }
        
        File folder = new File(Environment.getExternalStorageDirectory() + "/itmpdf");
        if (!folder.exists()) {
    	    success = folder.mkdirs();
    	    if (success) {
       		 Log.i("directory created", "directory created");
       	    // Do something on success
       	} else {
       		 Log.i("directory not created", "directory not created");
       	    // Do something else on failure 
       	}
        }
        else
        {
        	Log.i("Directorylog","Directory ALready Exists");
        	File file = new File(Environment.getExternalStorageDirectory() + "/itmpdf");
        	if(file.isDirectory()){
        		 
        		if(file.list().length>0){
         
        			Log.i("Directory Errors","Directory is not empty!");
         
        		}else{
        			Toast t = Toast.makeText(this,"No files were found!", Toast.LENGTH_LONG);
                    t.show();
        		}
        	}
        	else{
        		Log.i("Directory Errors","This is not a Directory");
        	}
        }
        ArrayList<HashMap<String, String>> filesListData = new ArrayList<HashMap<String, String>>();
        PdfManager plm = new PdfManager();
        this.fileList = plm.getPlayList();
        for (int i = 0; i < fileList.size(); i++) {
            // creating new HashMap
            HashMap<String, String> pdf = fileList.get(i);
            // adding HashList to ArrayList
            filesListData.add(pdf);
        }
        // Adding menuItems to ListView
        ListAdapter adapter = new SimpleAdapter(this, filesListData,
                R.layout.list_item_lib, new String[] { "fileTitle" }, new int[] {
                        R.id.songTitle });
 
        setListAdapter(adapter);
        // selecting single ListView item
        ListView lv = getListView();
        // listening to single listitem click
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // getting listitem index
                //int songIndex = position;
                String pid = ((TextView) view.findViewById(R.id.songTitle)).getText()
                        .toString();
                // Starting new intent
                String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
                String path = baseDir+"/itmpdf/"+pid+".pdf";
                Intent in = new Intent(getApplicationContext(),
                           PdfViewActivity.class);
                in.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, path);
                startActivity(in);
                // Closing PlayListView
                finish();
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