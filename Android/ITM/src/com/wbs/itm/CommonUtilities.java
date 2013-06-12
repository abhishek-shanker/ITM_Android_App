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
 * Common Utilities for GCM. Special Thanks to androidhive.
 */
package com.wbs.itm;

import android.content.Context;
import android.content.Intent;
 
public final class CommonUtilities {
 
    // give your server registration url here
    static final String SERVER_URL = "http://thewbs.getfreehosting.co.uk/gcm/register.php";
 
    // Google project id
    static final String SENDER_ID = "724507480306";
 
    /**
     * Tag used on log messages.
     */
    static final String TAG = "GCM";
 
    static final String DISPLAY_MESSAGE_ACTION =
            "com.androidhive.pushnotifications.DISPLAY_MESSAGE";
 
    static final String EXTRA_MESSAGE = "message";
 
    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}