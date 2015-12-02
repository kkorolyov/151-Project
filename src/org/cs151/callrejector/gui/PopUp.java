package org.cs151.callrejector.gui;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.util.TimerTask;

/**
 *   watson     11/20/2015 - mostly working
 *              11/25 - added cycle of 5 images, random
 *
 add the following in mainactivity to start:
 Intent i = new Intent(this, PopUp.class);
 startActivity(i);
 *
 */
public class PopUp extends Activity  {
    //-------------
    //Watson 11-20 mostly working pop up codes outside of mainactivity
    // 11-26 added cycle of 5 images

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // t = (TextView)findViewById(R.id.debug);
        setup();
    }

    // field var for pop up
    Button popUpButton;
    Button dismiss;
    PopupWindow current;
    View pv;

    //multiple image stuff
    PopupWindow p2, p3 , p4 , p5;
    View pv2, pv3, pv4 ,pv5;

    //debug
    TextView t ;

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
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }


    //this pops up once
    public void pop() {
        double d = imageChooser ();

 //chenge view here works
        String s = "";
        //= "" + this.current.isShowing();
        //t.setText(s);

      //  if (!this.current.isShowing() )
        //    this.current.showAsDropDown(popUpButton , 200, 100);
       // current.showAsDropDown((View)findViewById(R.id.activity_main));
    }
    
    /*
     * it randomly picks 1 out of the 5 images
     */

    double imageChooser () {
        double d = Math.random();

        double limit = 0.2 ;

        if ( d <= limit ){
            if (!this.current.isShowing() && !this.p2.isShowing() &&!this.p3.isShowing() && !this.p4.isShowing()&& !this.p5.isShowing()  )
                this.current.showAsDropDown(popUpButton , 200, 100);

            Button dismiss = (Button) PopUp.this.pv.findViewById(R.id.dismiss);
            if ( dismiss != null  )
                dismiss.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopUp.this.current.dismiss();
                    }
                });
         }
        else if ( d <= 2 * limit ) {
            //popup2.xml
            if (!this.current.isShowing() && !this.p2.isShowing()
                    &&!this.p3.isShowing() && !this.p4.isShowing()&& !this.p5.isShowing()  )
                this.p2.showAsDropDown(popUpButton , 200, 100);

            Button dismiss = (Button) PopUp.this.pv2.findViewById(R.id.dismiss2);
            if ( dismiss != null  )
                dismiss.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopUp.this.p2.dismiss();
                    }
                });
        }

        else if ( d <= 3 * limit ) {
            //popup3.xml
            if (!this.current.isShowing() && !this.p2.isShowing()
                    &&!this.p3.isShowing() && !this.p4.isShowing()&& !this.p5.isShowing()  )
                this.p3.showAsDropDown(popUpButton , 200, 100);

            Button dismiss = (Button) PopUp.this.pv3.findViewById(R.id.dismiss3);
            if ( dismiss != null  )
                dismiss.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopUp.this.p3.dismiss();
                    }
                });
        }

        else if ( d <= 4 * limit ) {
            //popup4.xml
            if (!this.current.isShowing() && !this.p2.isShowing()
                    &&!this.p3.isShowing() && !this.p4.isShowing()&& !this.p5.isShowing()  )
                this.p4.showAsDropDown(popUpButton , 200, 100);

            Button dismiss = (Button) PopUp.this.pv4.findViewById(R.id.dismiss4);
            if ( dismiss != null  )
                dismiss.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopUp.this.p4.dismiss();
                    }
                });
        }

        else  {
            //popup5.xml
            if (!this.current.isShowing() && !this.p2.isShowing()
                    &&!this.p3.isShowing() && !this.p4.isShowing()&& !this.p5.isShowing()  )
                this.p5.showAsDropDown(popUpButton , 200, 100);

            Button dismiss = (Button) PopUp.this.pv5.findViewById(R.id.dismiss5);
            if ( dismiss != null  )
                dismiss.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopUp.this.p5.dismiss();
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

        Button dismiss = (Button) PopUp.this.pv.findViewById(R.id.dismiss);
        if ( dismiss != null  )
            dismiss.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopUp.this.current.dismiss();
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

        Button dismiss = (Button) PopUp.this.pv2.findViewById(R.id.dismiss2);
        if ( dismiss != null  )
            dismiss.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopUp.this.p2.dismiss();
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

        Button dismiss = (Button) PopUp.this.pv3.findViewById(R.id.dismiss3);
        if ( dismiss != null  )
            dismiss.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopUp.this.p3.dismiss();
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
        Button dismiss = (Button) PopUp.this.pv4.findViewById(R.id.dismiss4);
        if ( dismiss != null  )
            dismiss.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopUp.this.p4.dismiss();
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
        Button dismiss = (Button) PopUp.this.pv5.findViewById(R.id.dismiss5);
        if ( dismiss != null  )
            dismiss.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopUp.this.p5.dismiss();
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

}