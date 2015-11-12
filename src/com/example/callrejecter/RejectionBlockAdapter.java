package com.example.callrejecter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Adapter for the ListView. How the a single RejectionBlock row will look and
 * function
 * Edited By Brandon Feist
 * @author Victor Li
 */
public class RejectionBlockAdapter extends ArrayAdapter<RejectionBlock> {

	private ArrayList<RejectionBlock> list;
	private Context context;
	private int layoutResourceId;

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
		// TODO: set onClick Button for, holder?
		
		//Sets the text for a SMS Preview
		TextView date = (TextView) view.findViewById(R.id.TextView01);
		TextView SMSMessage = (TextView) view.findViewById(R.id.TextView02);
		
		final RejectionBlock r = list.get(position);
		
		date.setText("");
		SMSMessage.setText(r.getSMS());
		
		//When toggle button is off, sets the background black. If not, the background is white
		final LinearLayout layout = (LinearLayout) view.findViewById(R.id.RejectionRow);
		
		final ToggleButton toggleButton = (ToggleButton) view.findViewById(R.id.ToggleButton);
		if(r.isOnOrOff()) {
			layout.setBackgroundColor(Color.WHITE);
			toggleButton.setText("On");
		} else {
			layout.setBackgroundColor(Color.GRAY);
			toggleButton.setText("Off");
		}
		
		toggleButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				r.SwitchState();
				if(r.isOnOrOff()) {
					layout.setBackgroundColor(Color.WHITE);
					toggleButton.setText("On");
				}
				else {
					layout.setBackgroundColor(Color.GRAY);
					toggleButton.setText("Off");
				}
				
			}
		});

		return view;
	}
}
