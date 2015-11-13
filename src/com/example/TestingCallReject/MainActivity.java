package com.example.TestingCallReject;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.cs151.callrejector.gui.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
//import com.android.dex.ClassData.Method;

public class MainActivity extends Activity {

	
	
	Calendar c;
	SimpleDateFormat df;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		c = Calendar.getInstance();

		df = new SimpleDateFormat("h:mm a");

		final TextView Time = (TextView) findViewById(R.id.Time_Getting_Rejected_To);

		Time.setText(getCurrentTime(c));

		final NumberPicker hours = (NumberPicker) findViewById(R.id.HOURS);

		hours.setMinValue(0);
		hours.setMaxValue(24);

		final NumberPicker minutes = (NumberPicker) findViewById(R.id.MINUTES);

		minutes.setMinValue(0);
		minutes.setMaxValue(59);

		Button SaveButton = (Button) findViewById(R.id.SAVE_BUTTON);

		// By pressing the save button it automatically rejects calls for the
		// number of hours and minutes the user picks
		SaveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(){
					
					public void run(){
						while(true){
							disconnectCall();
						}
					}
				}.start();
				c = Calendar.getInstance();
				
				int hoursToReject = hours.getValue();
				int minutesToReject = minutes.getValue();

				Calendar temp = (Calendar) c.clone();

				temp.add(Calendar.HOUR, hoursToReject);
				temp.add(Calendar.MINUTE, minutesToReject);

				Time.setText(getCurrentTime(temp));
				Time.setTextColor(Color.RED);

				Toast.makeText(MainActivity.this, getCurrentTime(temp),
						Toast.LENGTH_SHORT).show();

			}
		});
	}

	// formats the time
	private String getCurrentTime(Calendar c) {
		return df.format(c.getTime());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
