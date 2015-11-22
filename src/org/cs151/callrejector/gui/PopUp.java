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
 *   Watson   11/20/2015 - mostly working
 *   
    add the following in mainactivity to start:
    
    Intent i = new Intent(this, PopUp.class);
        startActivity(i);
        
        or
        
    startActivity(new Intent(this, PopUp.class));
 *   
 */
public class PopUp extends Activity  {

    // field var for pop up
    Button popUpButton;
    Button dismiss; 
    PopupWindow current;
    View pv;

    //debug
    TextView t ;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();
    }


    
    public void setup () {
        popUpButton = (Button)findViewById(R.id.popup);

        LayoutInflater inflater  = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        pv = inflater.inflate(R.layout.popup, null);

        final PopupWindow window = new PopupWindow(
                pv, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        current = window;

        popUpButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                PopUp.this.dismiss = (Button) PopUp.this.pv.findViewById(R.id.dismiss);
                dismiss.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        PopUp.this.current.dismiss();
                    }
                });
                PopUp.this.current.showAsDropDown(popUpButton, 50, -30);
            }
        });

        repeatPop();
    }

    
    //repeat pop up

    void repeatPop() {
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // t.setText("I am here delayed");
                pop();
                handler.postDelayed(this, 3000);
                ;
            }
        }, 1000);
    }


    //this pops up once
    public void pop() {
    	//Could not add a dismiss button to the field, 
    	//had to define it inside all method that calls it
    	// not a big deal, just more codes
        Button dismiss = (Button)PopUp.this.pv.findViewById(R.id.dismiss);
      dismiss.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                PopUp.this.current.dismiss();
            }
        });

        if (!this.current.isShowing())
            this.current.showAsDropDown(popUpButton, 100, 100);
    }


    
}