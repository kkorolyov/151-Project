package org.cs151.callrejector.gui;

import java.io.Serializable;

/**
 * Created by Victor on 10/30/2015.
 */
public class RejectionBlock implements Serializable {
	private boolean OnOrOff;
	private String SMS;

	public String getSMS() {
		return SMS;
	}

	public void setSMS(String SMS) {
		this.SMS = SMS;
	}

	public RejectionBlock(String s) {
		SMS = s;
		OnOrOff = true;
	}
	
	public void SwitchState() {
		OnOrOff = !OnOrOff;
	}
	
	public boolean isOnOrOff() {
		return OnOrOff;
	}
}
