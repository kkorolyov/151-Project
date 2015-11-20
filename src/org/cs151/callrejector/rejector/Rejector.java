/** 
 * This class is implemented and edited by Bao Pham
 */
package org.cs151.callrejector.rejector;

import java.lang.reflect.Method;

import org.cs151.callrejector.schedule.Schedule;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class Rejector extends BroadcastReceiver {
	
	
	/**
	 * Incoming phone number
	 */
	private String phoneNum;
	/**
	 * String to send text
	 */
	private String content;
	
	/**
	 * This method is used to check if there is an incoming call (One ring might pop up)
	 */
    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE); 
        switch (tm.getCallState()) {
            case TelephonyManager.CALL_STATE_RINGING:
            	if (Schedule.getSchedule().existsActiveRejectionBlock()) {
                    	phoneNum= intent.getStringExtra("incoming_number");
                    	disconnectCall();
                    	break;
            	}
        } 	
        try {
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(phoneNum, null, content, null, null);
			Toast.makeText(context, "SMS SENT TO "+ phoneNum,
						Toast.LENGTH_LONG).show();
		  } catch (Exception e) {
			Toast.makeText(context,	"SMS failed, please try again later!",Toast.LENGTH_LONG).show();
			e.printStackTrace();
		  }
    }
    
    /**
     * This method is used to set the content of the text
     * @param content is the content
     */
    public void setStringContent(String content){
    	this.content = content;
    }
    
    /**
     * This method is used to reject the call
     */
	public void disconnectCall(){
		 try {			 
		    String serviceManagerName = "android.os.ServiceManager";
		    String serviceManagerNativeName = "android.os.ServiceManagerNative";
		    String telephonyName = "com.android.internal.telephony.ITelephony";
		    Class<?> telephonyClass;
		    Class<?> telephonyStubClass;
		    Class<?> serviceManagerClass;
		    Class<?> serviceManagerNativeClass;
		    Method telephonyEndCall;
		    Object telephonyObject;
		    Object serviceManagerObject;
		    telephonyClass = Class.forName(telephonyName);
		    telephonyStubClass = telephonyClass.getClasses()[0];
		    serviceManagerClass = Class.forName(serviceManagerName);
		    serviceManagerNativeClass = Class.forName(serviceManagerNativeName);
		    Method getService = serviceManagerClass.getMethod("getService", String.class);
		    Method tempInterfaceMethod = serviceManagerNativeClass.getMethod("asInterface", IBinder.class);
		    Binder tmpBinder = new Binder();
		    tmpBinder.attachInterface(null, "fake");
		    serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
		    IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
		    Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
		    telephonyObject = serviceMethod.invoke(null, retbinder);
		    telephonyEndCall = telephonyClass.getMethod("endCall");
		    telephonyEndCall.invoke(telephonyObject);

		  } catch (Exception e) {
		    e.printStackTrace();
		    Log.d(null,
		            "FATAL ERROR: Could not connect to telephony subsystem");
		    Log.d(null, "Exception object: " + e); 
		 }
		}
}

	