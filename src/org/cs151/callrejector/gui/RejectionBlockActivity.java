package org.cs151.callrejector.gui;

import org.cs151.callrejector.schedule.HourMinuteTime;
import org.cs151.callrejector.schedule.HourMinuteTime;
import org.cs151.callrejector.schedule.RejectionBlock;
import org.cs151.callrejector.schedule.exceptions.HourOutOfBoundsException;
import org.cs151.callrejector.schedule.exceptions.MinuteOutOfBoundsException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

/**
 * The rejection block activity, where user picks the start time and end time
 * Editing a rejection block also beings user here
 * 
 * @author Victor Li
 */
public class RejectionBlockActivity extends Activity {

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
		r = (RejectionBlock) intent
				.getSerializableExtra("Editting Rejection Block");
		if (r != null) {	//when editing a rejection block, the timepickers is set to the start and end time
			startTime.setCurrentMinute(((HourMinuteTime) r.getStartTime())
					.getMinute());
			startTime.setCurrentHour(r.getStartTime().getHour());
			endTime.setCurrentHour(r.getEndTime().getHour());
			endTime.setCurrentMinute(((HourMinuteTime) r.getEndTime())
					.getMinute());
		}

	}

	/**
	 * When user does not actually want to create a rejection block. Hits cancel
	 * button to go previous activity
	 * 
	 * @param view
	 */
	public void CancelMakingRejectionBlock(View view) {
		finish();
	}

	/**
	 * When user hits the next button. The start time and end time is passed to the next activity 
	 * for making a rejection block
	 * @param view
	 * @throws MinuteOutOfBoundsException 
	 * @throws HourOutOfBoundsException
	 */
	public void next(View view) throws MinuteOutOfBoundsException,
			HourOutOfBoundsException {

		HourMinuteTime Start_Time = new HourMinuteTime(
				startTime.getCurrentHour(), startTime.getCurrentMinute());
		HourMinuteTime End_Time = new HourMinuteTime(endTime.getCurrentHour(),
				endTime.getCurrentMinute());

		Intent GoToEndTimeActivity = new Intent(this, EndTimeActivity.class);

		GoToEndTimeActivity.putExtra(START_TIME, Start_Time);
		GoToEndTimeActivity.putExtra(END_TIME, End_Time);

		if (r != null) {
			GoToEndTimeActivity.putExtra("RejectionBlock", r);
		}
		startActivityForResult(GoToEndTimeActivity, 1);

	}
}
