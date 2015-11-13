package com.example.TestingCallReject;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class ServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        TelephonyManager tm = (TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE); 

        switch (tm.getCallState()) {

            case TelephonyManager.CALL_STATE_RINGING:
                    String phoneNr= intent.getStringExtra("incoming_number");
                    Log.d("PHONE STATE", phoneNr);
                    Toast.makeText(context, phoneNr,Toast.LENGTH_LONG).show();
                    break;
        } 
    }
}