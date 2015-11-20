package org.cs151.callrejector.gui;

import org.cs151.callrejector.schedule.Schedule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * Edited by Brandon Feist
 * @author Victor Li
 *
 */
public class MainActivity extends Activity {

	private ListView schedule;
	private RejectionBlockAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		adapter = new RejectionBlockAdapter(this, R.layout.rejection_row, Schedule.getSchedule().getAllRejectionBlocksList());
		
		schedule = (ListView) findViewById(R.id.ListViewRejectionBlock);
		schedule.setAdapter(adapter);
	}

	// Don't think we need this

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.menu_main, menu);
	// return true;
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Handle action bar item clicks here. The action bar will
	// // automatically handle clicks on the Home/Up button, so long
	// // as you specify a parent activity in AndroidManifest.xml.
	// int id = item.getItemId();
	//
	// //noinspection SimplifiableIfStatement
	// if (id == R.id.action_settings) {
	// return true;
	// }
	//
	// return super.onOptionsItemSelected(item);
	// }

	/*
	 * When user clicks on the "+" button, then RejectionBlockActivity opens up
	 */
	public void GoToRejectionBlockActivity(View view) {
		Intent GoToRejectionBlockActivity = new Intent(this,
				RejectionBlockActivity.class);

		startActivityForResult(GoToRejectionBlockActivity, 1);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}
}
