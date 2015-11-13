/*watson s 
TODO FIX THIS
public class MissedCall  implements Runnable{
	List<> list = new ArrayList<> (); 
	String most_recent = ""; //most recent incoming
	
	MissedCall() {
		
		 Uri allCalls = Uri.parse("content://call_log/calls");
		 Cursor c = managedQuery(allCalls, null, null, null, null);

		String num = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));
		most_recent = num;
	}
}*/
