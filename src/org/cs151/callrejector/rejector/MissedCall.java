import android.provider.CallLog.Calls;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.widget.TextView;

//watson

public class MissedCall  implements Runnable{
	ArrayList list = new ArrayList ();
	String most_recent = ""; //most recent incoming
	final 
	
	MissedCall ( ) {
		
		 Uri allCalls = Uri.parse("content://call_log/calls");
		 Cursor c = managedQuery(allCalls, null, null, null, null);

		String num = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));
		most_recent = num;
	}
	
	

}
