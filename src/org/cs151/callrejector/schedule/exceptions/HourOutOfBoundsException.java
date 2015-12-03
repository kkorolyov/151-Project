package org.cs151.callrejector.schedule.exceptions;

public class HourOutOfBoundsException extends Exception {
	private static final long serialVersionUID = -439286073271097110L;
	
	public HourOutOfBoundsException() {
		super();
	}
	public HourOutOfBoundsException(int hour) {
		super(String.valueOf(hour));
	}
	public HourOutOfBoundsException(int hour, int hourBoundStart, int hourBoundEnd) {
		super(String.valueOf(hour) + " out of bounds; must be between " + String.valueOf(hourBoundStart) + " and " + String.valueOf(hourBoundEnd));
	}
}
