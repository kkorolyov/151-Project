package org.cs151.callrejector.rejector;

import java.sql.Date;
import java.util.ArrayList;

import android.database.Cursor;
import android.provider.CallLog;

/*
 Ô watson 11-26
1. need to pass in a cursor in mainactivity:

Cursor  c = managedQuery( CallLog.Calls.CONTENT_URI, null, null, null, android.provider.CallLog.Calls.DATE + " DESC");
MissedCall m = new MissedCall(c);

2. this starts the cursor from the most recent call
String order = android.provider.CallLog.Calls.DATE + " DESC";
in last argument of managedQuery()
 */


public class MissedCall  {
    Cursor  cursor;
    ArrayList<String> list = new ArrayList<String>();
    
    

    public MissedCall  (Cursor c ) {
        cursor = c;
    }
  
   // gets the most recent 10 call logs and store it in list 

    public void refresh() {

        //String order = android.provider.CallLog.Calls.DATE + " DESC";

        //getContentResolver().query(
        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);

        int i = 0;



        while (cursor.moveToNext() && i < 10) {
            String phoneNum = cursor.getString(number);

            list.add(phoneNum);

            String callTypeCode = cursor.getString(type);
            String strcallDate = cursor.getString(date);
            Date callDate = new Date(Long.valueOf(strcallDate));
            String callDuration = cursor.getString(duration);
            
            int callcode = Integer.parseInt(callTypeCode);
            //if (Integer.parseInt(callTypeCode) == CallLog.Calls.MISSED_TYPE) {	
            if (callcode == CallLog.Calls.MISSED_TYPE) {
                list.add(phoneNum);
                i++;
            }
        }
        cursor.close();
    }
    
    
    public ArrayList<String> getList() {
    	return this.list;
    }


}

/*MissedCall() {

Uri allCalls = Uri.parse("content://call_log/calls");
cursorsor c = managedQuery(allCalls, null, null, null, null);

String num = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));
most_recent = num;
}*/
