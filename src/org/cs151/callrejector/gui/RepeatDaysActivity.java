package org.cs151.callrejector.gui;

import org.cs151.callrejector.gui.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Victor on 10/30/2015.
 */
public class RepeatDaysActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.days_repeat);
	}

	public void BackToRejectionBlockActivity(View view) {
		finish();
	}
}
