package org.cs151.callrejector.schedule;

import org.cs151.callrejector.schedule.exceptions.HourOutOfBoundsException;
import org.cs151.callrejector.schedule.exceptions.MinuteOutOfBoundsException;

/**
 * A single hour and minute of the day (0 to 23 and 0 to 59).
 * @author Kirill Korolyov
 */
public class HourMinuteTime extends HourTime {	// TODO Make mutable?
	private static final long serialVersionUID = 9084681839983306816L;

	public static final int MINUTE_BOUND_START = 0, MINUTE_BOUND_END = 59;

	private int minute;
	
	/**
	 * Constructs a new {@code HourMinuteTime} object with a specified time of day.
	 * @param hour int from {@value HourTime#HOUR_BOUND_START} to {@value HourTime#HOUR_BOUND_END}
	 * @param minute int from {@value #MINUTE_BOUND_START} to {@value #MINUTE_BOUND_END}
	 * @throws HourOutOfBoundsException if specified hour not between {@value HourTime#HOUR_BOUND_START} and {@value HourTime#HOUR_BOUND_END}
	 * @throws MinuteOutOfBoundsException if specified minute not between {@value #MINUTE_BOUND_START} and {@value #MINUTE_BOUND_END}
	 */
	public HourMinuteTime(int hour, int minute) throws HourOutOfBoundsException, MinuteOutOfBoundsException {
		super(hour);
		setMinute(minute);
	}
	/**
	 * Constructs a new {@code HourMinuteTime} object with a specified time of day using 12-hour time format.
	 * @param hour int from 1 to 12
	 * @param minute int from 0 to 59
	 * @param pm if time is after noon
	 * @throws HourOutOfBoundsException 
	 * @throws MinuteOutOfBoundsException when specified hour or minute out of bounds
	 */
	public HourMinuteTime(int hour, int minute, boolean pm) throws HourOutOfBoundsException, MinuteOutOfBoundsException {
		super(hour);
		setTime(hour, minute, pm);
	}
	
	/**
	 * @return minute from 0 to 59
	 */
	public int getMinute() {
		return minute;
	}
	
	private void setMinute(int minute) throws MinuteOutOfBoundsException {	// Validates specified minute
		if (minute < MINUTE_BOUND_START || minute > MINUTE_BOUND_END)
			throw new MinuteOutOfBoundsException(minute, MINUTE_BOUND_START, MINUTE_BOUND_END);
		
		this.minute = minute;
	}
	private void setTime(int hour, int minute, boolean pm) throws HourOutOfBoundsException, MinuteOutOfBoundsException {	// Converts 12-hour to 24-hour format
		setHour(hour, pm);
		setMinute(minute);
	}
	
	/**
	 * @return time in the 24-hour format
	 */
	@Override
	public String toString() {
		String time = "";
		int hour = getHour(), minute = getMinute();
		if (hour < 10)	// Append starting 0 to hour
			time += '0' + String.valueOf(hour);
		else
			time += hour;
		
		time += ":";
		if (minute < 10)	// Append starting 0 to minute
			time += '0' + String.valueOf(minute);
		else
			time += minute;
		
		return time;
	}
	/**
	 * @param standard whether to return time in 12-hour format
	 * @return time in the format "HH:MM" for 24-hour, or "(H)H:MMam/pm" for 12-hour
	 */
	@Override
	public String toString12Hour() {
		String time = "", meridian = MERIDIEM_AM;
		int hour = getHour(), minute = getMinute(), converter = 0;
		if (hour == 0)
			hour = 12;
		else if (hour >= 12) {
			meridian = MERIDIEM_PM;
			if (hour > 12)
				converter = 12;
		}
		time += (hour - converter) + ":";
		
		if (minute < 10)	// Append starting 0 to minute
			time += '0' + String.valueOf(minute);
		else
			time += String.valueOf(minute);
		
		time += meridian;
		return time;
	}
	
	@Override
	public int compareTo(HourTime otherTime) {	// TODO Implement type restrictions
		if ((super.compareTo(otherTime) != 0))	// Comparing at hour level sufficient
			return super.compareTo(otherTime);
		
		int otherMinute = 0;	// Compare at minute level
		if (otherTime instanceof HourMinuteTime)
			otherMinute = ((HourMinuteTime) otherTime).getMinute();
		
		if (getMinute() < otherMinute)
			return -1;
		if (getMinute() > otherMinute)
			return 1;
		return 0;	// If equal
	}
}
