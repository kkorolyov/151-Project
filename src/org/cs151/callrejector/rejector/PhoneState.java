package org.cs151.callrejector.rejector;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class PhoneState extends BroadcastReceiver {
	
	private String phoneNum;
	private boolean goThru = false;
    @Override
    public void onReceive(Context context, Intent intent) {

        TelephonyManager tm = (TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE); 
        switch (tm.getCallState()) {
            case TelephonyManager.CALL_STATE_RINGING:
            		goThru = true;
                    phoneNum= intent.getStringExtra("incoming_number");
                    Rejector tester = new Rejector();
                    tester.disconnectCall();
                    //phoneNum = phoneNr;
                   // Toast.makeText(context, phoneNum,Toast.LENGTH_LONG).show();
                    break;
        } 
        try {
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(phoneNum, null, "TESTING SMS", null, null);
			Toast.makeText(context, "SMS Sent to "+ phoneNum,
						Toast.LENGTH_LONG).show();
		  } catch (Exception e) {
			Toast.makeText(context,
				"SMS failed, please try again later "+goThru,
				Toast.LENGTH_LONG).show();
			e.printStackTrace();
		  }
    }
}

	