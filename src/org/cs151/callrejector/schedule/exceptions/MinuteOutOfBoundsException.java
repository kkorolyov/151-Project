package org.cs151.callrejector.schedule.exceptions;

public class MinuteOutOfBoundsException extends Exception {
	private static final long serialVersionUID = -4333032634736368283L;

	public MinuteOutOfBoundsException() {
		super();
	}
	public MinuteOutOfBoundsException(int minute) {
		super(String.valueOf(minute));
	}
	public MinuteOutOfBoundsException(int minute, int startBound, int endBound) {
		super(minute + "; Must be between " + startBound + " and " + endBound);
	}
}
