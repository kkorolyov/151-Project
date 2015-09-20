package org.cs151.callrejector.schedule.exceptions;

public class HourOutOfBoundsException extends Exception {
	private static final long serialVersionUID = -4333032634736368283L;

	public HourOutOfBoundsException() {
		super();
	}
	public HourOutOfBoundsException(int hour) {
		super(String.valueOf(hour));
	}
	public HourOutOfBoundsException(int hour, int startBound, int endBound) {
		super(hour + "; Must be between " + startBound + " and " + endBound);
	}
}
