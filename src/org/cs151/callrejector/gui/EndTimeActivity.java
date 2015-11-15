package org.cs151.callrejector.gui;

import org.cs151.callrejector.schedule.Time;
import org.cs151.callrejector.schedule.exceptions.TimeOutOfBoundsException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

public class EndTimeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.end_time);
	}
	
	public void CancelEndTime(View view){
		finish();
	}
	
	public void SaveEndTime(View view) throws TimeOutOfBoundsException{
		TimePicker timePicker = (TimePicker) findViewById(R.id.end_time_picker);
		Time time = new Time(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
		
		Intent SendEndTime = new Intent();
		
		SendEndTime.putExtra("New Time", time);
		
		setResult(RESULT_OK, SendEndTime);
		
		finish();
	}
}
