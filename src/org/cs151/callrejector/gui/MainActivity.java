package org.cs151.callrejector.gui;


import org.cs151.callrejector.gui.PopUp;
import org.cs151.callrejector.schedule.Schedule;

import org.cs151.callrejector.schedule.DailySchedule;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Edited by Brandon Feist
 * @author Victor Li
 * 
 * added PopUp stuff - watson
 */
public class MainActivity extends Activity {

	private ListView schedule;
	private RejectionBlockAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		adapter = new RejectionBlockAdapter(this, R.layout.rejection_row, DailySchedule.getSchedule().getAllRejectionBlocksList());
		
		schedule = (ListView) findViewById(R.id.ListViewRejectionBlock);
		schedule.setAdapter(adapter);
		
		setup();
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
	
	/*
	 * ______________________________________
	 * POP UP STUFF BEGINS HERE
	 * ______________________________________
	 *
	 */


    // watson 12/01 added to mainactivity
    Button popUpButton;
    Button dismiss;
    PopupWindow current;
    View pv;

    //multiple image stuff
    PopupWindow p2, p3 , p4 , p5;
    View pv2, pv3, pv4 ,pv5;

    //debug
    //TextView t ;

    //setup the view and button for popup
    public void setup () {
        setupView1();
        setupView2();
        setupView3();
        setupView4();
        setupView5();
        popUpButton = (Button)findViewById(R.id.popup);

        repeatPop();
    }


    /*
     * this repeates popup of 5 images
     */
    void repeatPop() {
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pop();
                handler.postDelayed(this, 60000);
            }
        }, 1000);
    }


    //this pops up once
    public void pop() {
        double d = imageChooser ();

        //chenge view here works
        String s = "";
        
    }

    /*
     * it randomly picks 1 out of the 5 images
     */

    double imageChooser () {
        double d = Math.random();

        double limit = 0.2 ;
        int x = 150, y = 100;// popup position
        
        if ( d <= limit ){
            if (!this.current.isShowing() && !this.p2.isShowing() &&!this.p3.isShowing() && !this.p4.isShowing()&& !this.p5.isShowing()  )
                this.current.showAsDropDown(popUpButton , x , y );

            Button dismiss = (Button) MainActivity.this.pv.findViewById(R.id.dismiss);
            if ( dismiss != null  )
                dismiss.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.this.current.dismiss();
                    }
                });
        }
        else if ( d <= 2 * limit ) {
            //popup2.xml
            if (!this.current.isShowing() && !this.p2.isShowing()
                    &&!this.p3.isShowing() && !this.p4.isShowing()&& !this.p5.isShowing()  )
                this.p2.showAsDropDown(popUpButton , x , y );

            Button dismiss = (Button) MainActivity.this.pv2.findViewById(R.id.dismiss2);
            if ( dismiss != null  )
                dismiss.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.this.p2.dismiss();
                    }
                });
        }

        else if ( d <= 3 * limit ) {
            //popup3.xml
            if (!this.current.isShowing() && !this.p2.isShowing()
                    &&!this.p3.isShowing() && !this.p4.isShowing()&& !this.p5.isShowing()  )
                this.p3.showAsDropDown(popUpButton , x , y );

            Button dismiss = (Button) MainActivity.this.pv3.findViewById(R.id.dismiss3);
            if ( dismiss != null  )
                dismiss.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.this.p3.dismiss();
                    }
                });
        }

        else if ( d <= 4 * limit ) {
            //popup4.xml
            if (!this.current.isShowing() && !this.p2.isShowing()
                    &&!this.p3.isShowing() && !this.p4.isShowing()&& !this.p5.isShowing()  )
                this.p4.showAsDropDown(popUpButton , x , y );

            Button dismiss = (Button) MainActivity.this.pv4.findViewById(R.id.dismiss4);
            if ( dismiss != null  )
                dismiss.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.this.p4.dismiss();
                    }
                });
        }

        else  {
            //popup5.xml
            if (!this.current.isShowing() && !this.p2.isShowing()
                    &&!this.p3.isShowing() && !this.p4.isShowing()&& !this.p5.isShowing()  )
                this.p5.showAsDropDown(popUpButton , x , y );

            Button dismiss = (Button) MainActivity.this.pv5.findViewById(R.id.dismiss5);
            if ( dismiss != null  )
                dismiss.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.this.p5.dismiss();
                    }
                });
        }

        return d;

    }

    /*
     * setup the popup window and layoutmanager for popup.xml
     */

    void setupView1 () {
        LayoutInflater inflater  = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        pv = inflater.inflate(R.layout.popup,null);
        final PopupWindow window1 = new PopupWindow(
                pv, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        current = window1;

        Button dismiss = (Button) MainActivity.this.pv.findViewById(R.id.dismiss);
        if ( dismiss != null  )
            dismiss.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.this.current.dismiss();
                }
            });
    }

    /*
     * setup the popup window and layoutmanager for popup2.xml
     */

    void setupView2 () {
        LayoutInflater inflater  = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        pv2 = inflater.inflate(R.layout.popup2, null);
        final PopupWindow window1 = new PopupWindow(
                pv2, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p2 = window1;

        Button dismiss = (Button) MainActivity.this.pv2.findViewById(R.id.dismiss2);
        if ( dismiss != null  )
            dismiss.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.this.p2.dismiss();
                }
            });
    }

    /*
     * setup the popup window and layoutmanager for popup3.xml
     */


    void setupView3 () {
        LayoutInflater inflater  = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        pv3 = inflater.inflate(R.layout.popup3, null);
        final PopupWindow window1 = new PopupWindow(
                pv3, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p3 = window1;

        Button dismiss = (Button) MainActivity.this.pv3.findViewById(R.id.dismiss3);
        if ( dismiss != null  )
            dismiss.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.this.p3.dismiss();
                }
            });
    }

    /*
     * setup the popup window and layoutmanager for popup4.xml
     */


    void setupView4 () {
        LayoutInflater inflater  = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        pv4 = inflater.inflate(R.layout.popup4, null);
        final PopupWindow window1 = new PopupWindow(
                pv4, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p4 = window1;
        Button dismiss = (Button) MainActivity.this.pv4.findViewById(R.id.dismiss4);
        if ( dismiss != null  )
            dismiss.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.this.p4.dismiss();
                }
            });
    }

    /*
     * setup the popup window and layoutmanager for popup5.xml
     */


    void setupView5 () {
        LayoutInflater inflater  = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        pv5 = inflater.inflate(R.layout.popup5, null);
        final PopupWindow window1 = new PopupWindow(
                pv5, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p5 = window1;
        Button dismiss = (Button) MainActivity.this.pv5.findViewById(R.id.dismiss5);
        if ( dismiss != null  )
            dismiss.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.this.p5.dismiss();
                }
            });
    }

    /*
     * checks to see if there are any other xmls showing,
     * to prevent multiple popups at the same time
     */

    public boolean isShow () {
        return (!this.current.isShowing() && !this.p2.isShowing()
                &&!this.p3.isShowing() && !this.p4.isShowing()&& !this.p5.isShowing() );
    }
    //PopUp stuff ends


	
}
