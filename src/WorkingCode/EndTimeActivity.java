package WorkingCode;

import org.cs151.callrejector.gui.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class EndTimeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.end_time);
	}
	
	public void CancelEndTime(View view){
		finish();
	}
	
	public void SaveEndTime(View view){
		finish();
	}
}
