package org.cs151.callrejector.gui;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.cs151.callrejector.schedule.RejectionBlock;

import android.app.Activity;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Edited by Brandon Feist
 * @author Victor Li
 *
 */
public class MainActivity extends Activity {

	ListView Schedule;
	ArrayList<RejectionBlock> list;
	RejectionBlockAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Schedule = (ListView) findViewById(R.id.ListViewRejectionBlock);

		list = new ArrayList<RejectionBlock>();
		
		adapter = new RejectionBlockAdapter(this,
				R.layout.rejection_row, list);

	}

	// Don't think we need this

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.menu_main, menu);
	// return true;
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Handle action bar item clicks here. The action bar will
	// // automatically handle clicks on the Home/Up button, so long
	// // as you specify a parent activity in AndroidManifest.xml.
	// int id = item.getItemId();
	//
	// //noinspection SimplifiableIfStatement
	// if (id == R.id.action_settings) {
	// return true;
	// }
	//
	// return super.onOptionsItemSelected(item);
	// }

	/*
	 * When user clicks on the "+" button, then RejectionBlockActivity opens up
	 */
	public void GoToRejectionBlockActivity(View view) {
		Intent GoToRejectionBlockActivity = new Intent(this,
				RejectionBlockActivity.class);

		startActivityForResult(GoToRejectionBlockActivity, 1);
	}

	/*
	 * What happens when MainActivity gets resumed. Either adds a rejection
	 * block or does not
	 */
	/*
	 * Edited by Brandon Feist
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (data != null) {
			if (data.getExtras().containsKey("newRejectionBlock")) {

				RejectionBlock newRejectionBlock = (RejectionBlock) data
						.getSerializableExtra("newRejectionBlock");

				Toast.makeText(MainActivity.this, newRejectionBlock.getSMS(),
						Toast.LENGTH_SHORT).show();

				list.add(newRejectionBlock);

				Schedule.setAdapter(adapter);

				adapter.notifyDataSetChanged();
			}
		} else {
			// when user does not make a rejection block
			Toast.makeText(MainActivity.this, "Error. Must Put End Time",
					Toast.LENGTH_SHORT).show();
		}

	}
	
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
		    Method getService = // getDefaults[29];
		    serviceManagerClass.getMethod("getService", String.class);
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
		            "FATAL ERROR: could not connect to telephony subsystem");
		    Log.d(null, "Exception object: " + e); 
		 }
		}
}
