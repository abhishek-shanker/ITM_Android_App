"ITM University Android Application" is a new and latest ITMU application with better features like inbuilt pdf viewer, download manager, talky, live notifications etc.
You can download the app from here & test it yourself : https://play.google.com/store/apps/details?id=com.wbs.itm

LICENSE: This application is under GNU GPLv3. Please read the COPYING.txt file for further terms and conditions of the license. 

	 Copyright 2013 Bhavyanshu Parasher   
	 This file is part of "ITM University Android Application".
	 "ITM University Android Application" is free software: you can redistribute it and/or modify it 
	 under the terms of the GNU General Public License as published by the Free Software Foundation, 
	 either version 3 of the License, or (at your option) any later version.
	 "ITM University Android Application" is distributed in the hope that it will be useful, 
	 but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
	 or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
	 You should have received a copy of the GNU General Public License along with "ITM University Android Application". 
	 If not, see https://www.gnu.org/licenses/gpl-3.0.txt.


**********************************************

WHY AM I RELEASING THE SOURCE CODE? 
The source code is being released in order to help other students to make an android application based on this concept. With the source code they can easily make an application for their own college which will provide latest announcements, notices, results and timetable. This is an important feature because more university websites in India are not based on responsive design and there is no way students be notified with the latest news. This focuses on pdf because ITM University publishes all the notices in pdf format. So there was no other option. I had to develop a workflow accordingly. This app source code can be a great start up point for other developers.



WHAT IS THE TECHNOLOGY BEHIND THIS?
Anyone with sound knowledge of php, mysql, Java & JSON can set up an app for their own college very easily.



WHY DOESN'T THIS CONTAIN THE SERVER SIDE CODE?
The server side code is very simple. I can email you the source code for server side if you want. Send me an email at bhavyanshu.spl@gmail.com with subject "Request for SERVER code". I will try to send it as soon as possible.
The application mostly requires JSON data which it fetches and maps into the listview of the activity.

Example format for JSON data
```json
{
    "notices": [
        {
            "nid": "134",
            "notice": "Notice Title1",
            "noticeinfo": "http://domainhostingpdfile.pdf",
            "date": "Date of Post: 2013-05-15"
        },
        {
            "nid": "133",
            "notice": "Notice title 2",
            "noticeinfo": "http://domainhostingpdfile.pdf",
            "date": "Date of Post: 2013-05-15"
        }
    ],
    "success": 1
} 
```
**********************************************

OLD LOGS

ITM 1.7 (To Be Released)
-Major update in User Interface

ITM 1.6 (Latest Release)
-Bug fixes in Timetable

ITM 1.5
-Easy and improved interface for timetables. No need to remember your branch's Code anymore. :)
-Bug fixes in Registration Activity

ITM 1.4
-Minor Bug Fixes

ITM 1.3
-Notifications custom layout to go with all themes

ITM 1.2
-Major Bug removed that was causing download manager to crash in ITM 1.1.

ITM 1.1
-Known bugs: NetworkException

ITM 1.0 released on Mar 17th, 2013
Features:
-PDF viewer
-Live Notifications
-Talky
-Download Manager

**********************************************
