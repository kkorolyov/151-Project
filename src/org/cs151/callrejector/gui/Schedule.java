package org.cs151.callrejector.gui;

import java.io.Serializable;

public class Schedule implements Serializable{

	private String SMS;
	public Schedule(String s) {
		SMS = s;
	}
	
	public String getSMS() {
		return SMS;
	}
	
	public void setSMS(String sMS) {
		SMS = sMS;
	}
}
