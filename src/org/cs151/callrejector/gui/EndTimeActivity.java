package org.cs151.callrejector.gui;

import org.cs151.callrejector.schedule.DailySchedule;
import org.cs151.callrejector.schedule.Time;
import org.cs151.callrejector.schedule.exceptions.InvalidTimeRangeException;
import org.cs151.callrejector.schedule.exceptions.TimeOutOfBoundsException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

public class EndTimeActivity extends Activity {

	private Time Start_Time;
	private Time End_Time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent getTime = getIntent();
		
		Start_Time = (Time) getTime.getSerializableExtra("Start Time");
		End_Time = (Time) getTime.getSerializableExtra("End Time");
		
		setContentView(R.layout.end_time);
	}
	
	public void CancelEndTime(View view){
		finish();
	}
	
	public void MakeRejectionBlock(View view) throws TimeOutOfBoundsException, InvalidTimeRangeException{
//		TimePicker EndTimePicker = (TimePicker) findViewById(R.id.end_time_picker);
//		@SuppressWarnings("deprecation")
//		Time End_Time = new Time(EndTimePicker.getCurrentHour(), EndTimePicker.getCurrentMinute());
		
		EditText NewSMSMessage = (EditText) findViewById(R.id.SMS_Message);
		
		String SMS = NewSMSMessage.getText().toString();
		
		DailySchedule.getSchedule().addRejectionBlock(Start_Time, End_Time, SMS, true);
		
		Intent finish = new Intent(this, MainActivity.class);
		
		startActivityForResult(finish, RESULT_OK);
		
		//Intent SendEndTime = new Intent();
		
		//SendEndTime.putExtra("New Time", time);
		
		//setResult(RESULT_OK, SendEndTime);
		
		//finish();
	}
}
