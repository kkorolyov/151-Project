package org.cs151.callrejector.rejector;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.telephony.*;


public class Main extends BroadcastReceiver {
private static final String TAG = null;
String incommingNumber;
String incno1= "9916090941";

public void onReceive(Context context, Intent intent) {
    Bundle bundle = intent.getExtras();

    if(null == bundle)
            return;
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);       
    try {
        // Java reflection to gain access to TelephonyManager's
        // ITelephony getter
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Log.v(TAG, "Get getTeleService...");
        Class c = Class.forName(tm.getClass().getName());
        Method m = c.getDeclaredMethod("getITelephony");
        m.setAccessible(true);
        com.android.internal.telephony.ITelephony telephonyService = (ITelephony) m.invoke(tm);  
        Bundle b = intent.getExtras();
        incommingNumber = b.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        Log.v(TAG,incommingNumber );
        Log.v(TAG,incno1 );
        if ( incommingNumber.equals(incno1) )
        {
             telephonyService = (ITelephony) m.invoke(tm);
               telephonyService.silenceRinger();
        telephonyService.endCall();
        Log.v(TAG,"BYE BYE BYE" );
        }
        else{

        telephonyService.answerRingingCall();
        Log.v(TAG,"HELLO HELLO HELLO" );
        }


    } catch (Exception e) {
        e.printStackTrace();
        Log.e(TAG,
                "FATAL ERROR: could not connect to telephony subsystem");
        Log.e(TAG, "Exception object: " + e);
    }


    }
}