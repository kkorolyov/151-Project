package org.cs151.callrejector.gui;


import org.cs151.callrejector.schedule.RejectionBlock;
import org.cs151.callrejector.schedule.DailySchedule;
import org.cs151.callrejector.schedule.Time;
import org.cs151.callrejector.schedule.exceptions.InvalidTimeRangeException;
import org.cs151.callrejector.schedule.exceptions.TimeOutOfBoundsException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EndTimeActivity extends Activity {

	private Time Start_Time;
	private Time End_Time;
	
	private EditText NewSMSMessage;
	
	private RejectionBlock r;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.end_time);
		
		Intent getTimes = getIntent();
		
		NewSMSMessage = (EditText) findViewById(R.id.SMS_Message);
		
		Start_Time = (Time) getTimes.getSerializableExtra("StartTime");
		End_Time = (Time) getTimes.getSerializableExtra("EndTime");
		
		if(getTimes.getSerializableExtra("RejectionBlock") != null)
		{
			r = (RejectionBlock) getTimes.getSerializableExtra("RejectionBlock");
			
			NewSMSMessage.setText(r.getSMS());
			
		}
		
	}
	
	public void CancelEndTime(View view){
		finish();
	}
	
	public void MakeRejectionBlock(View view) throws TimeOutOfBoundsException, InvalidTimeRangeException{
//		TimePicker EndTimePicker = (TimePicker) findViewById(R.id.end_time_picker);
//		@SuppressWarnings("deprecation")
//		Time End_Time = new Time(EndTimePicker.getCurrentHour(), EndTimePicker.getCurrentMinute());
		
//		if(r != null){
//			String SMS = NewSMSMessage.getText().toString();
//			
//			//Schedule.getSchedule().updateRejectionBlock(r, Start_Time, End_Time, SMS);
//			
//			Intent finish = new Intent(this, MainActivity.class);
//			
//			startActivityForResult(finish, RESULT_OK);
//			
//		}
//		else {			
//			String SMS = NewSMSMessage.getText().toString();
//			
//			Schedule.getSchedule().addRejectionBlock(Start_Time, End_Time, SMS, true);
//			
//			Intent finish = new Intent(this, MainActivity.class);
//			
//			startActivityForResult(finish, RESULT_OK);
//		}
		
		if(r != null) {
			
			DailySchedule.getSchedule().removeRejectionBlock(r);
			//Toast.makeText(this, r.getStartTime().toString() + " " + r.getEndTime().toString(), Toast.LENGTH_LONG).show();

		}
		
		String SMS = NewSMSMessage.getText().toString();		
			
		DailySchedule.getSchedule().addRejectionBlock(Start_Time, End_Time, SMS, true);

		Intent finish = new Intent(this, MainActivity.class);
			
		startActivity(finish);
		
		
		
		//Intent SendEndTime = new Intent();
		
		//SendEndTime.putExtra("New Time", time);
		
		//setResult(RESULT_OK, SendEndTime);
		
		//finish();
	}
}
