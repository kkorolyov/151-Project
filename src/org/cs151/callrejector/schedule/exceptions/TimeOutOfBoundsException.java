package org.cs151.callrejector.schedule.exceptions;

public class TimeOutOfBoundsException extends Exception {
	private static final long serialVersionUID = -4333032634736368283L;

	public TimeOutOfBoundsException() {
		super();
	}
	public TimeOutOfBoundsException(int time) {
		super(String.valueOf(time));
	}
	public TimeOutOfBoundsException(int time, int startBound, int endBound) {
		super(time + "; Must be between " + startBound + " and " + endBound);
	}
}
