package org.cs151.callrejector.gui;

import org.cs151.callrejector.schedule.DailySchedule;
import org.cs151.callrejector.schedule.HourMinuteTime;
import org.cs151.callrejector.schedule.RejectionBlock;
import org.cs151.callrejector.schedule.exceptions.InvalidTimeRangeException;
import org.cs151.callrejector.schedule.exceptions.MinuteOutOfBoundsException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * The last activity for the user to put in the SMS Message
 * @author Victor Li
 *
 */
public class EndTimeActivity extends Activity {

	private HourMinuteTime Start_Time;
	private HourMinuteTime End_Time;

	private EditText NewSMSMessage;

	private RejectionBlock r;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.end_time);

		Intent getTimes = getIntent();

		NewSMSMessage = (EditText) findViewById(R.id.SMS_Message);

		Start_Time = (HourMinuteTime) getTimes
				.getSerializableExtra("START_TIME");
		End_Time = (HourMinuteTime) getTimes.getSerializableExtra("END_TIME");

		//if a rejection block gets passed
		if (getTimes.getSerializableExtra("RejectionBlock") != null) {
			r = (RejectionBlock) getTimes
					.getSerializableExtra("RejectionBlock");
			
			// when editing rejection, shows the SMS of that rejection block to be edited
			NewSMSMessage.setText(r.getSMS()); 

		}

	}

	/**
	 * Goes back to the last activity, if user wants to change the start and end time
	 * @param view
	 */
	public void CancelEndTime(View view) {
		finish();
	}

	/**
	 * When user hits the save button, this makes the rejection block from
	 * start time, end time, and SMS to the schedule class. Goes to MainAcitivty to
	 * show the rejection block
	 * @param view
	 * @throws MinuteOutOfBoundsException
	 * @throws InvalidTimeRangeException
	 */
	public void MakeRejectionBlock(View view)
			throws MinuteOutOfBoundsException, InvalidTimeRangeException {

		if (r != null) {
			DailySchedule.getSchedule().removeRejectionBlock(r);
		}

		String SMS = NewSMSMessage.getText().toString();

		DailySchedule.getSchedule().addRejectionBlock(Start_Time, End_Time,
				SMS, true);

		Intent finish = new Intent(this, MainActivity.class);

		startActivity(finish);

	}
}
