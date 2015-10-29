package org.cs151.callrejector.gui;

import java.util.ArrayList;

import com.example.cellreject.R;
import com.example.cellreject.R.id;
import com.example.cellreject.R.layout;
import com.example.cellreject.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ListView listViewOfSchedules;
	ArrayList<Schedule> ArrayListOfSchedule;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listViewOfSchedules = (ListView) findViewById(R.id.ListViewRejectionBlock);
		ArrayListOfSchedule = new ArrayList<Schedule>();
		
		ScheduleAdapter adapter = new ScheduleAdapter(this, R.layout.rejection_row, ArrayListOfSchedule);
		
		listViewOfSchedules.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void AddRejectionBlock(View view) {

		Intent intent = new Intent(this, RejectionBlockActivity.class);

		startActivity(intent);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		Schedule schedule = (Schedule) data.getSerializableExtra("NEWSCHEDULE");
		
		ArrayListOfSchedule.add(schedule);
		
		Toast.makeText(this, "New schedule added", Toast.LENGTH_SHORT).show();
	}
}
