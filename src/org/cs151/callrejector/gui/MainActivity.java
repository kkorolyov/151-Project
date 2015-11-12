package org.cs151.callrejector.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import org.cs151.callrejector.gui.R;

/**
 * Edited by Brandon Feist
 * @author Victor Li
 *
 */
public class MainActivity extends Activity {

	ListView Schedule;
	ArrayList<RejectionBlock> list;
	RejectionBlockAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Schedule = (ListView) findViewById(R.id.ListViewRejectionBlock);

		list = new ArrayList<RejectionBlock>();
		
		adapter = new RejectionBlockAdapter(this,
				R.layout.rejection_row, list);

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

	/*
	 * What happens when MainActivity gets resumed. Either adds a rejection
	 * block or does not
	 */
	/*
	 * Edited by Brandon Feist
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (data != null) {
			if (data.getExtras().containsKey("newRejectionBlock")) {

				RejectionBlock newRejectionBlock = (RejectionBlock) data
						.getSerializableExtra("newRejectionBlock");

				Toast.makeText(MainActivity.this, newRejectionBlock.getSMS(),
						Toast.LENGTH_SHORT).show();

				list.add(newRejectionBlock);

				Schedule.setAdapter(adapter);

				adapter.notifyDataSetChanged();
			}
		} else {
			// when user does not make a rejection block
			Toast.makeText(MainActivity.this, "back to main",
					Toast.LENGTH_SHORT).show();
		}

	}
}
