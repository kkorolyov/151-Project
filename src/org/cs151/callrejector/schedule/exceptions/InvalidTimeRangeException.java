package org.cs151.callrejector.schedule.exceptions;

import org.cs151.callrejector.schedule.HourMinuteTime;

public class InvalidTimeRangeException extends Exception {
	private static final long serialVersionUID = 6639473009430190690L;
	private static final String BASE_MESSAGE = "Start time must be less than end time";
	
	public InvalidTimeRangeException() {
		super(BASE_MESSAGE);
	}
	public InvalidTimeRangeException(HourMinuteTime startTime, HourMinuteTime endTime) {
		super("Start time '" + startTime + " is greater than end time '" + endTime + "'; " + BASE_MESSAGE);
	}
}
