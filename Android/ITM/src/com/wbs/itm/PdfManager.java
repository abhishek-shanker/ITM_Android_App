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
 * A very important file that finds pdf, doc, docx files in particular folder.
 */
package com.wbs.itm;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Environment;

public class PdfManager {
    String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
    final String MEDIA_PATH = new String(baseDir+"/itmpdf/");
    private ArrayList<HashMap<String, String>> fileList = new ArrayList<HashMap<String, String>>();
    // Constructor
    public PdfManager(){ 
    }

    /**
     * Function to read all described files from sdcard
     * and store the details in ArrayList
     * */
    public ArrayList<HashMap<String, String>> getPlayList(){
        File home = new File(MEDIA_PATH); 
        if (home.listFiles(new FileExtensionFilter()).length > 0) {
            for (File file : home.listFiles(new FileExtensionFilter())) {
                HashMap<String, String> song = new HashMap<String, String>();
                song.put("fileTitle", file.getName().substring(0, (file.getName().length() - 4)));
                song.put("filePath", file.getPath());
                fileList.add(song);
            }
        }
        else {        	
        //Log.i("No Files Found", "No files were found in directory");	
        }
        return fileList;
    }
 
    class FileExtensionFilter implements FilenameFilter {
        @Override
		public boolean accept(File dir, String name) {
            return (name.endsWith(".pdf") || name.endsWith(".doc") || name.endsWith(".docx"));
        }
    }
}