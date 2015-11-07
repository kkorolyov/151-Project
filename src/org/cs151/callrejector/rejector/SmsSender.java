package org.cs151.callrejector.rejector;
import android.telephony.SmsManager;
import android.widget.Toast;
/*
 * author: Watson Chang
 * 
 */
public class SmsSender {
	String message = "";
	
	SmsSender ( String s ) {
	message = s;
	}
	/*
	 * @param String message is the message being send
	 * 
	 */
	public void send (  String incomingNumber , String message ) {
		try{
			
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(incomingNumber, null, message, null, null);
		 	android.widget.Toast.makeText(getApplicationContext(), "SMS Sent!",
					Toast.LENGTH_LONG).show();
		}

       catch(Exception e){//
    	   
       }                        

	}
	
	String getSMS () {
		return message;
	}
	
	void setSMS ( String s ) {
		message = s;
	}
	

}
