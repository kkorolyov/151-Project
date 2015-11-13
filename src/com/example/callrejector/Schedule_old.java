package com.example.callrejector;

import java.util.ArrayList;

/**
 * Created by Victor on 10/30/2015.
 */
public class Schedule_old {

	ArrayList<RejectionBlock_old> listOfBlocks;

	public Schedule_old() {
		listOfBlocks = new ArrayList<RejectionBlock_old>();
	}
	
	public Schedule_old(ArrayList<RejectionBlock_old> list) {
		listOfBlocks = list;
	}

	public void addRejectionBlock(RejectionBlock_old r) {
		listOfBlocks.add(r);
	}
}
