package org.cs151.callrejector.rejector;
import android.app.Activity;
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
    }
}
