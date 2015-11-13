package org.cs151.callrejector.gui;

import java.util.ArrayList;

/**
 * Created by Victor on 10/30/2015.
 */
public class Schedule {

	ArrayList<RejectionBlock> listOfBlocks;

	public Schedule() {
		listOfBlocks = new ArrayList<RejectionBlock>();
	}
	
	public Schedule(ArrayList<RejectionBlock> list) {
		listOfBlocks = list;
	}

	public void addRejectionBlock(RejectionBlock r) {
		listOfBlocks.add(r);
	}
}
