package org.cs151.callrejector.rejector;

import android.telecom.Call;
import android.telephony.PhoneStateListener;

public class Rejector extends PhoneStateListener {

	public void onCallStateChanged(int state, String incomingNumber)
	{
		if(state == Call.STATE_RINGING)
		{
			
		}
	}
}
