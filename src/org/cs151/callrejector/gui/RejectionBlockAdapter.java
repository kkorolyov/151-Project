package org.cs151.callrejector.gui;

import java.util.ArrayList;

import org.cs151.callrejector.schedule.DailySchedule;
import org.cs151.callrejector.schedule.HourMinuteTime;
import org.cs151.callrejector.schedule.RejectionBlock;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

/**
 * Adapter for the ListView. How a single RejectionBlock row will look and
 * function
 * Edited By Brandon Feist
 * @author Victor Li
 */
public class RejectionBlockAdapter extends ArrayAdapter<RejectionBlock> {
	private ArrayList<RejectionBlock> list;
	private Context context;
	private int layoutResourceId;
	
	private static String EDITTING_REJECTION_BLOCK = "Editting Rejection Block";

	public RejectionBlockAdapter(Context context, int resource,
			ArrayList<RejectionBlock> list) {
		super(context, resource, list);
		this.context = context;
		this.list = list;
		this.layoutResourceId = resource;
	}
	
	public void addRejectionBlock(RejectionBlock block) {
		list.add(block);
	}

	/*
	 * Edited by Brandon Feist
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(getContext());

		View view = inflater.inflate(R.layout.rejection_row, parent, false);
		
		//Sets the text for a SMS Preview
//		TextView TimeBeingRejected = (TextView) view.findViewById(R.id.TextView01);
//		TextView SMSMessage = (TextView) view.findViewById(R.id.TextView02);
		
		final RejectionBlock r = list.get(position);
		
		HourMinuteTime startTime = (HourMinuteTime) r.getStartTime();
		HourMinuteTime endTime = (HourMinuteTime) r.getEndTime();
		
//		TimeBeingRejected.setText(startTime.toString(true) + " - " + endTime.toString(true));
//		
		//if SMS is too long
		String pass;
		r.getSMS();
		if(r.getSMS().length() >= 15)
			{pass = r.getSMS().substring(0, 14) + "...";}
		else { pass = r.getSMS();}
		
		Button EditRejectionBlock = (Button) view.findViewById(R.id.Time_And_SMS);
		EditRejectionBlock.setText(startTime.toString12Hour() + " - " + endTime.toString12Hour()
				+ "\n" + pass);
		
		EditRejectionBlock.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent Edit = new Intent(context, RejectionBlockActivity.class);
				Edit.putExtra(EDITTING_REJECTION_BLOCK, r);
				
				context.startActivity(Edit);
			}
			
		});
		
		//When toggle button is off, sets the background black. If not, the background is white
		final LinearLayout layout = (LinearLayout) view.findViewById(R.id.RejectionRow);
		
		final ToggleButton toggleButton = (ToggleButton) view.findViewById(R.id.ToggleButton);
		if(r.isEnabled()) {
			layout.setBackgroundColor(Color.WHITE);
			toggleButton.setText("On");
		} else {
			layout.setBackgroundColor(Color.GRAY);
			toggleButton.setText("Off");
		}
		
		toggleButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				r.switchState();
				if(r.isEnabled()) {
					layout.setBackgroundColor(Color.WHITE);
					toggleButton.setText("On");
				}
				else {
					layout.setBackgroundColor(Color.GRAY);
					toggleButton.setText("Off");
				}
				
			}
		});

		Button DeleteButton = (Button) view.findViewById(R.id.Delete_Button);
		
		DeleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DailySchedule.getSchedule().removeRejectionBlock(r);
				notifyDataSetChanged();
			}
		});
		return view;
	}
	
	
}
