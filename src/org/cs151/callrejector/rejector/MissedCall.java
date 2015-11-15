import android.app.Activity;
import android.database.cursorsor;
import android.provider.CallLog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.sql.Date;
import java.util.ArrayList;

//watson 

public class MissedCall extends Activity {
	Cursor  cursor;
    ArrayList<String> list = new ArrayList<>();
    
   public MissedCalls  (Cursor  c ) {
        cursor = c;
        textView = t;
    }
   /*
    * 
    * gets the most recent 10 call logs
    */
   
   public void get() {
       
       String strOrder = android.provider.CallLog.Calls.DATE + " DESC";

       //getContentResolver().query(
       int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
       int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
       int date = cursor.getColumnIndex(CallLog.Calls.DATE);
       int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
       
       int i = 0;



      while (cursor.moveToNext() && i < 10) {
       String phoneNum = cursor.getString(number);
       
         list.add(phoneNum);
         list.add();
           String callTypeCode = cursor.getString(type);
           String strcallDate = cursor.getString(date);
           Date callDate = new Date(Long.valueOf(strcallDate));
           String callDuration = cursor.getString(duration);
           String callType = null;
           int callcode = Integer.parseInt(callTypeCode);
           
          
           i++;
      }
       cursor.close();
   }
	
	
}

/*MissedCall() {

Uri allCalls = Uri.parse("content://call_log/calls");
cursorsor c = managedQuery(allCalls, null, null, null, null);

String num = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));
most_recent = num;
}*/
