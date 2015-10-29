package org.cs151.callrejector.gui;

import java.util.ArrayList;
import java.util.List;

import com.example.cellreject.R;
import com.example.cellreject.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ScheduleAdapter extends ArrayAdapter<Schedule> {

	private ArrayList<Schedule> list;
	private Context context;
	private int layoutResourceId;
	
	public ScheduleAdapter(Context context, int resource, List<Schedule> objects) {
		super(context, resource, objects);
		
		this.list = (ArrayList<Schedule>) objects;
		this.context = context;
		this.layoutResourceId = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//return super.getView(position, convertView, parent);
		
		LayoutInflater inflater = LayoutInflater.from(getContext());
		
		View view = inflater.inflate(R.layout.rejection_row, parent, false);
		
		return view;
	}

}
