package org.cs151.callrejector.schedule;

import java.io.Serializable;

import org.cs151.callrejector.schedule.exceptions.HourOutOfBoundsException;

/**
 * A single hour of the day from 0 to 23.
 * @author Kirill Korolyov
 */
public class HourTime implements Comparable<HourTime>, Serializable {
	private static final long serialVersionUID = 4113925352020659368L;
	// TODO Decide on some AbstractTime abstract class
	public static final int HOUR_BOUND_START = 0, HOUR_BOUND_END = 23;
	public static final String MERIDIEM_AM = "am", MERIDIEM_PM = "pm";

	private int hour;
	
	/**
	 * Constructs a new {@code HourTime} of the specified hour.
	 * @param hour hour of the day from {@value #HOUR_BOUND_START} to {@value #HOUR_BOUND_END}
	 * @throws HourOutOfBoundsException if specified hour not between {@value #HOUR_BOUND_START} and {@value #HOUR_BOUND_END}
	 */
	public HourTime(int hour) throws HourOutOfBoundsException {
		setHour(hour);
	}
	/**
	 * Constructs a new {@code HourTime} of the specified hour using 12-hour notation.
	 * @param hour hour of the day from 1 to 12
	 * @param pm if specified hour occurs in pm
	 * @throws HourOutOfBoundsException
	 */
	public HourTime(int hour, boolean pm) throws HourOutOfBoundsException {
		setHour(hour, pm);
	}
	
	/**
	 * @return hour from 0 to 23
	 */
	public int getHour() {
		return hour;
	}
	
	/**
	 * @param newHour new hour to set
	 * @throws HourOutOfBoundsException if specified hour not between {@value #HOUR_BOUND_START} and {@value #HOUR_BOUND_END}
	 */
	public void setHour(int newHour) throws HourOutOfBoundsException {
		if (newHour < HOUR_BOUND_START || newHour > HOUR_BOUND_END)
			throw new HourOutOfBoundsException();

		hour = newHour;
	}
	/**
	 * @param newHour new hour to set
	 * @param pm if specified hour occurs in pm
	 * @throws HourOutOfBoundsException
	 */
	public void setHour(int newHour, boolean pm) throws HourOutOfBoundsException {
		int converter = 0;
		if (newHour == 12)	// Makes 12am -> 0, 12pm -> 0 + 12
			newHour = 0;
		if (pm)
			converter = 12;
		setHour(newHour + converter);
	}
	
	@Override
	public int compareTo(HourTime o) {
		if (getHour() < o.getHour())
			return -1;
		if (getHour() > o.getHour())
			return 1;
		else
			return 0;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getHour();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)	// Same object
			return true;
		if (obj == null)	// Null check
			return false;
		if (!(obj instanceof HourTime))	// Type check (Subclasses allowed in 1 direction)
			return false;
		if (hour != ((HourTime) obj).hour)	// Properties check
			return false;
		
		return true;
	}
	
	@Override
	public String toString() {
		String hourString = (getHour() < 10) ? "0" + String.valueOf(getHour()) : String.valueOf(getHour());	// If hour < 10, append starting 0
		return hourString + ":00";
	}
	/**
	 * @return String representation of this time in 12 hour format
	 */
	public String toString12Hour() {
		String meridiem = MERIDIEM_AM;
		int displayHour = getHour();
		if (displayHour == 0)
			displayHour = 12;	// 00 == 12am
		else if (displayHour >= 12) {
			meridiem = MERIDIEM_PM;	// pm starts at noon
			if (displayHour > 12)
				displayHour -= 12;	// 12-hour time
		}
		String hourString = (displayHour < 10) ? "0" + String.valueOf(displayHour) : String.valueOf(displayHour);
		return hourString + ":00" + meridiem;
	}
}
