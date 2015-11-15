package org.cs151.callrejector.gui;

import org.cs151.callrejector.gui.R;

import OldWorkingCode.EndTimeActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The rejection block activity.
 */
public class RejectionBlockActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.rejection_block_activity);

	}

	/*
	 * The cancel button
	 */
	public void CancelMakingRejectionBlock(View view) {
		finish();
	}

	public void AddingNewRejectionBlock(View view) {

		EditText New_SMS_Message = (EditText) findViewById(R.id.SMS_Message);

		RejectionBlock newRejectionBlock = new RejectionBlock(
				String.valueOf(New_SMS_Message.getText()));

		Intent AddRejectionBlockToMain = new Intent();

		AddRejectionBlockToMain
				.putExtra("newRejectionBlock", newRejectionBlock);

		setResult(RESULT_OK, AddRejectionBlockToMain);

		finish();

	}

	public void RepeatDaysActivity(View view) {
		Intent GoToRepeatDaysActivity = new Intent(this,
				RepeatDaysActivity.class);

		startActivityForResult(GoToRepeatDaysActivity, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Toast.makeText(this, "Edited Rejection Block", Toast.LENGTH_SHORT)
				.show();
	}
}
