package org.cs151.callrejector.gui;

import org.cs151.callrejector.schedule.RejectionBlock;
import org.cs151.callrejector.schedule.Time;
import org.cs151.callrejector.schedule.exceptions.TimeOutOfBoundsException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

/**
 * The rejection block activity.
 */
public class RejectionBlockActivity extends Activity {
	
//	private Time EndTime;

	private TimePicker startTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.rejection_block_activity);
		
		startTime = (TimePicker) findViewById(R.id.start_time_picker);
		Intent intent = getIntent();
		RejectionBlock r = (RejectionBlock) intent.getSerializableExtra("Editting Rejection Block");
		if(r != null)
		{
			startTime.setCurrentMinute(r.getStartTime().getMinute());
			startTime.setCurrentHour(r.getStartTime().getHour());
		}

	}

	/*
	 * The cancel button
	 */
	public void CancelMakingRejectionBlock(View view) {
		finish();
	}

//	public void AddingNewRejectionBlock(View view) throws TimeOutOfBoundsException, InvalidTimeRangeException {
//		
//		if(EndTime != null) {
//		
//			EditText New_SMS_Message = (EditText) findViewById(R.id.SMS_Message);
//			
//			TimePicker startTimePicker = (TimePicker) findViewById(R.id.start_time_picker);
//	
//			@SuppressWarnings("deprecation")
//			Time Start_Time = new Time(startTimePicker.getCurrentHour(), startTimePicker.getCurrentMinute());
//			
//			Schedule.getSchedule().addRejectionBlock(Start_Time, EndTime, String.valueOf(New_SMS_Message.getText()));
//		}
//
//		finish();
//
//	}

	public void RepeatDaysActivity(View view) {
		Intent GoToRepeatDaysActivity = new Intent(this,
				RepeatDaysActivity.class);

		startActivityForResult(GoToRepeatDaysActivity, 1);
	}

//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (data != null) {
//			if (data.getExtras().containsKey("New Time")) {
//
//				Time End_Time = (Time) data.getSerializableExtra("New Time");
//				
//				EndTime = End_Time;
//
//			}
//		} else {
//			// when user does not make a rejection block
//			Toast.makeText(this, "Edited Rejection Block", Toast.LENGTH_SHORT).show();
//		}
//	}
	
	public void EndTime(View view) throws TimeOutOfBoundsException{
		Intent GoToEndTimeActivity = new Intent(this, EndTimeActivity.class);
		
		@SuppressWarnings("deprecation")
		Time Start_Time = new Time(startTime.getCurrentHour(), startTime.getCurrentMinute());
		
		GoToEndTimeActivity.putExtra("Start Time", Start_Time);
		
		EditText New_SMS_Message = (EditText) findViewById(R.id.SMS_Message);
		GoToEndTimeActivity.putExtra("SMS", String.valueOf(New_SMS_Message.getText()) );
		
		startActivityForResult(GoToEndTimeActivity, 1);
	}
}
