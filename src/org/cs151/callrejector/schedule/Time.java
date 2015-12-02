package org.cs151.callrejector.schedule;

import java.io.Serializable;

import org.cs151.callrejector.schedule.exceptions.TimeOutOfBoundsException;

/**
 * Represents an hour and minute of a time of day.
 * @author Kirill Korolyov
 */
public class Time extends AbstractTime implements Serializable {
	private static final long serialVersionUID = 527179700241102165L;
	
	public static final String MERIDIAN_AM = "am", MERIDIAN_PM = "pm";
	public static final int HOUR_BOUND_START = 0, HOUR_BOUND_END = 23, MINUTE_BOUND_START = 0, MINUTE_BOUND_END = 59;

	private int hour, minute;
	
	/**
	 * Constructs a new {@code Time} object with a specified time of day.
	 * @param hour int from 0 to 23
	 * @param minute int from 0 to 59
	 * @throws TimeOutOfBoundsException when specified hour or minute out of bounds
	 */
	public Time(int hour, int minute) throws TimeOutOfBoundsException {
		setTime(hour, minute);
	}
	/**
	 * Constructs a new {@code Time} object with a specified time of day using 12-hour time format.
	 * @param hour int from 1 to 12
	 * @param minute int from 0 to 59
	 * @param pm if time is after noon
	 * @throws TimeOutOfBoundsException when specified hour or minute out of bounds
	 */
	public Time(int hour, int minute, boolean pm) throws TimeOutOfBoundsException {
		setTime(hour, minute, pm);
	}
	
	/**
	 * @return hour from 1 to 24
	 */
	public int getHour() {
		return hour;
	}
	/**
	 * @return minute from 0 to 59
	 */
	public int getMinute() {
		return minute;
	}
	
	private void setTime(int hour, int minute) throws TimeOutOfBoundsException {	// Validates specified time
		if (hour < HOUR_BOUND_START || hour > HOUR_BOUND_END)
			throw new TimeOutOfBoundsException(hour, HOUR_BOUND_START, HOUR_BOUND_END);
		if (minute < MINUTE_BOUND_START || minute > MINUTE_BOUND_END)
			throw new TimeOutOfBoundsException(minute, MINUTE_BOUND_START, MINUTE_BOUND_END);
		
		this.hour = hour;
		this.minute = minute;
	}
	private void setTime(int hour, int minute, boolean pm) throws TimeOutOfBoundsException {	// Converts 12-hour to 24-hour format
		if (hour == 12)
			hour = 0;
		int converter = 0;
		if (pm)
			converter = 12;
		setTime(hour + converter, minute);
	}
	
	/**
	 * @return time in the 24-hour format
	 */
	@Override
	public String toString() {
		return toString(false);
	}
	/**
	 * @param standard whether to return time in 12-hour format
	 * @return time in the format "HH:MM" for 24-hour, or "(H)H:MMam/pm" for 12-hour
	 */
	public String toString(boolean standard) {
		if (standard) {
			String time = "", meridian = MERIDIAN_AM;
			int hour = getHour(), minute = getMinute(), converter = 0;
			if (hour == 0)
				hour = 12;
			else if (hour >= 12) {
				meridian = MERIDIAN_PM;
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
	
	@Override
	public int compareTo(AbstractTime otherTime) {
		return compareTo((Time) otherTime);
	}
	private int compareTo(Time compared) {
		int thisMinutes = (hour * 60) + minute, thatMinutes = (compared.hour * 60) + compared.minute;	// Easier to compare
		if (thisMinutes < thatMinutes)
			return -1;
		if (thisMinutes > thatMinutes)
			return 1;
		return 0;	// If equal
	}
	@Override
	protected boolean equals(AbstractTime aTime) {
		// TODO Auto-generated method stub
		return false;
	}
}
