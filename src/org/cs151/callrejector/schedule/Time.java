package org.cs151.callrejector.schedule;

import org.cs151.callrejector.schedule.exceptions.TimeOutOfBoundsException;

/**
 * Represents an hour and minute of a time of day using 24-hour time format.
 * @author Kirill
 */
public class Time implements Comparable<Time> {
	public static final int HOUR_BOUND_START = 1, HOUR_BOUND_END = 24, MINUTE_BOUND_START = 0, MINUTE_BOUND_END = 59;

	private int hour, minute;
	
	/**
	 * Constructs a new {@code Time} object with a specified time of day.
	 * @param hour {@code int} from 1 to 24
	 * @param minute {@code int} from 0 to 59
	 * @throws TimeOutOfBoundsException
	 */
	public Time(int hour, int minute) throws TimeOutOfBoundsException {
		setTime(hour, minute);
	}
	
	/**
	 * Returns the difference in ms between this time and the specified time. May be negative if the specified time occurs before this time
	 * @param time time to calculate to
	 * @return ms between this time and specified time
	 */
	public long timeTo(Time time) {
		long thisTimeInMinutes = (hour * 60) + minute;	// Minutes from 24:00
		long otherTimeInMinutes = (time.getHour() * 60) + time.getMinute();
		
		return (otherTimeInMinutes - thisTimeInMinutes) / (60 * 1000);	// Difference in milliseconds
	}
	
	/**
	 * @return {@code String} in the format "HH:MM"
	 */
	public String displayTime() {
		String time = "";
		if (hour < 10)	// Append starting 0 to hour
			time += "0" + hour;
		else
			time += hour;
		
		time += ":";
		if (minute < 10)	// Append starting 0 to minute
			time += "0" + minute;
		else
			time += minute;
		
		return time;
	}
	
	/**
	 * Compares value of this {@code Time} object and another {@code Time} object
	 * @param compared {@code Time} to compare to
	 * @return -1 if this {@code Time} is earlier, 1 if greater, 0 if equal
	 */
	public int compareTo(Time compared) {
		if (hour < compared.getHour() || (hour == compared.getHour() && minute < compared.getMinute()))
			return -1;
		if (hour > compared.getHour() || (hour == compared.getHour() && minute > compared.getMinute()))
			return 1;
		return 0;	// If equal
	}
	
	/**
	 * @return time in 24-hour format where int[0] = hour, int[1] = minute
	 */
	public int[] getTime() {
		return new int[]{getHour(), getMinute()};
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
}
