package org.cs151.callrejector.gui;

import org.cs151.callrejector.schedule.RejectionBlock;
import org.cs151.callrejector.schedule.HourMinuteTime;
import org.cs151.callrejector.schedule.exceptions.InvalidTimeRangeException;
import org.cs151.callrejector.schedule.exceptions.TimeOutOfBoundsException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * The rejection block activity.
 */
public class RejectionBlockActivity extends Activity {
	
//	private Time EndTime;

	private TimePicker startTime;
	private TimePicker endTime;
	private RejectionBlock r;
	
	private static String START_TIME = "START_TIME";
	private static String END_TIME = "END_TIME";
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.rejection_block_activity);
		
		startTime = (TimePicker) findViewById(R.id.start_time_picker);
		endTime = (TimePicker) findViewById(R.id.end_time_picker);
		
		Intent intent = getIntent();
		r = (RejectionBlock) intent.getSerializableExtra("Editting Rejection Block");
		if(r != null)
		{
			startTime.setCurrentMinute(r.getStartTime().getMinute());
			startTime.setCurrentHour(r.getStartTime().getHour());
			endTime.setCurrentHour(r.getEndTime().getHour());
			endTime.setCurrentMinute(r.getEndTime().getMinute());
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

//	public void RepeatDaysActivity(View view) {
//		Intent GoToRepeatDaysActivity = new Intent(this,
//				RepeatDaysActivity.class);
//
//		startActivityForResult(GoToRepeatDaysActivity, 1);
//	}

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
	
	public void next(View view) throws TimeOutOfBoundsException{

//		Intent GoToEndTimeActivity = new Intent(this, EndTimeActivity.class);
//		
//		@SuppressWarnings("deprecation")
//		Time Start_Time = new Time(startTime.getCurrentHour(), startTime.getCurrentMinute());
//		
//		GoToEndTimeActivity.putExtra("Start Time", Start_Time);	// TODO Construct RejectionBlock from ints, not Time

		
//		Time Start_Time = new Time(startTime.getCurrentHour(), startTime.getCurrentMinute());
//		Time End_Time = new Time(endTime.getCurrentHour(), startTime.getCurrentMinute());
		
//		if(r != null) {
//			Toast.makeText(this, "editting", Toast.LENGTH_SHORT).show();
//			@SuppressWarnings("deprecation")
//			Time Changed_Start_Time = null, Changed_End_Time = null;
//			try {
//				Changed_Start_Time = new Time(startTime.getCurrentHour(), startTime.getCurrentMinute());
//				Changed_End_Time = new Time(endTime.getCurrentHour(), endTime.getCurrentMinute());
//			} catch (TimeOutOfBoundsException e) {
//				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//			}
			
//			try{
//				Toast.makeText(this, "lololo", Toast.LENGTH_SHORT).show();
//				
//				r.setStartTime(Changed_Start_Time);
//				r.setEndTime(Changed_End_Time);
//			}
//			catch(InvalidTimeRangeException e) {
//				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//			}
			
//			Intent GotoEndTimeActivity = new Intent(this, EndTimeActivity.class);
			
//			GotoEndTimeActivity.putExtra("Edit Rejection Block", r);
//			GotoEndTimeActivity.putExtra("Editted Start Time", Start_Time);
//			GotoEndTimeActivity.putExtra("Editted End Time", End_Time);
			
//			startActivityForResult(GotoEndTimeActivity, 1);
//		}
//		else {
//			Intent GoToEndTimeActivity = new Intent(this, EndTimeActivity.class);
			
//			Time Start_Time = null;
//			Time End_Time = null;
//			
//			try{
//			Start_Time = new Time(startTime.getCurrentHour(), startTime.getCurrentMinute());
//			
//			End_Time = new Time(endTime.getCurrentHour(), endTime.getCurrentMinute());
//			} catch(TimeOutOfBoundsException e) {
//				Toast.makeText(this, "hihi", Toast.LENGTH_LONG).show();
//			}
			
//			GoToEndTimeActivity.putExtra("Start Time", Start_Time);
//			GoToEndTimeActivity.putExtra("End Time", End_Time);
			//EditText New_SMS_Message = (EditText) findViewById(R.id.SMS_Message);
			//GoToEndTimeActivity.putExtra("SMS", String.valueOf(New_SMS_Message.getText()) );
			
//			startActivityForResult(GoToEndTimeActivity, 1);
			
			
			

			HourMinuteTime Start_Time = new HourMinuteTime(startTime.getCurrentHour(), startTime.getCurrentMinute());
			HourMinuteTime End_Time = new HourMinuteTime(endTime.getCurrentHour(), endTime.getCurrentMinute());
			
			Intent GoToEndTimeActivity = new Intent(this, EndTimeActivity.class);
			
			GoToEndTimeActivity.putExtra(START_TIME, Start_Time);
			GoToEndTimeActivity.putExtra(END_TIME, End_Time);
			
			if(r != null){
				GoToEndTimeActivity.putExtra("RejectionBlock", r);
//				Toast.makeText(this, r.getStartTime().toString() + " " + r.getEndTime().toString(), Toast.LENGTH_LONG).show();
			}
			startActivityForResult(GoToEndTimeActivity, 1);
			
			
	}
}
