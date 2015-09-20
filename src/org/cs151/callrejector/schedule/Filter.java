package org.cs151.callrejector.schedule;

import org.cs151.callrejector.schedule.exceptions.HourOutOfBoundsException;
import org.cs151.callrejector.schedule.exceptions.InvalidTimeRangeException;

/**
 * Provides actions within a specified time frame.
 * @author Kirill
 */
public class Filter {
	private int startTime = 0, endTime = 0;
	
	/**
	 * Constructs a new {@code Filter} with the specified start and end times.
	 * @param start start time of activity
	 * @param end end time of activity
	 * @throws HourOutOfBoundsException
	 * @throws InvalidTimeRangeException
	 */
	public Filter(int start, int end) throws HourOutOfBoundsException, InvalidTimeRangeException {
		setStartTime(start);
		setEndTime(end);
	}
	
	/**
	 * Sets the start time of this filter's activity.
	 * @param hour time to start activity
	 * @throws HourOutOfBoundsException
	 * @throws InvalidTimeRangeException
	 */
	public void setStartTime(int hour) throws HourOutOfBoundsException, InvalidTimeRangeException {
		if (isValidHour(hour))	// Check whether hour is in range
			startTime = hour;
		else
			throw new HourOutOfBoundsException(hour, Schedule.HOUR_BOUND_START, Schedule.HOUR_BOUND_END);
		
		if (!isValidRange())	// Check whether start time is before end time
			throw new InvalidTimeRangeException(startTime, endTime);
	}
	/**
	 * Sets the end time of this filter's activity.
	 * @param hour time to end activity
	 * @throws HourOutOfBoundsException
	 * @throws InvalidTimeRangeException
	 */
	public void setEndTime(int hour) throws HourOutOfBoundsException, InvalidTimeRangeException {
		if (isValidHour(hour))	// Check whether hour is in range
			endTime = hour;
		else
			throw new HourOutOfBoundsException(hour, Schedule.HOUR_BOUND_START, Schedule.HOUR_BOUND_END);
		
		if (!isValidRange())	// Check whether start time is before end time
			throw new InvalidTimeRangeException(startTime, endTime);
	}
	
	private boolean isValidHour(int hour) {
		if (hour < Schedule.HOUR_BOUND_START || hour > Schedule.HOUR_BOUND_END)
			return false;
		return true;
	}
	private boolean isValidRange() {
		if (endTime != 0 && (endTime - startTime) < 1)
			return false;
		return true;
	}
}
