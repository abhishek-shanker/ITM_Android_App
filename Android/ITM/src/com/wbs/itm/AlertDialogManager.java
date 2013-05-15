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
  * Function to display simple Alert Dialog
  * @param context - application context
  * @param title - alert dialog title
  * @param message - alert message
  * @param status - success/failure (used to set icon)
  *               - pass null if you don't want icon
  * */
package com.wbs.itm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
 
public class AlertDialogManager {
 
    @SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message,
            Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
 
        // Setting Dialog Title
        alertDialog.setTitle(title);
 
        // Setting Dialog Message
        alertDialog.setMessage(message);
 
        if(status != null)
            // Setting alert dialog icon
            alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
 
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
			public void onClick(DialogInterface dialog, int which) {
            	
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
    }
}