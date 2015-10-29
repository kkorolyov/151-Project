package org.cs151.callrejector.gui;

import com.example.cellreject.R;
import com.example.cellreject.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RejectionBlockActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.adding_rejection_block);
    }


    public void CancelRejectionBlock(View view) {
        finish();
    }


    public void AddRejectionBlock(View view) {
    	Intent backToMainActivity = new Intent(this, MainActivity.class);
    	
    	Schedule newSchedule = new Schedule("");
    	
    	backToMainActivity.putExtra("NEWSCHEDULE", newSchedule);
    	
    	final int result = 1;
    	
    	startActivityForResult(backToMainActivity, result);
    }
}