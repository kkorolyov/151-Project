package org.cs151.callrejector.schedule;

import java.io.Serializable;
import java.util.logging.Logger;

import org.cs151.callrejector.schedule.exceptions.InvalidTimeRangeException;

/**
 * Provides call rejection actions within a specified time frame.
 * @author Kirill Korolyov
 * @author Brandon Feist
 */
public class RejectionBlock implements Comparable<RejectionBlock>, Serializable {
	private static final long serialVersionUID = -2969684380343526177L;
	private static final Logger log = Logger.getLogger(RejectionBlock.class.getName());
	
	private HourTime start, end;
	private String sms;	// SMS to send to rejected call
	private boolean enabled, active;
		
	/**
	 * Constructs a new {@code RejectionBlock} with the specified start and end times.
	 * Will start inactive.
	 * @param start start time of activity
	 * @param end end time of activity
	 * @throws InvalidTimeRangeException when end time is before start time
	 */
	RejectionBlock(HourTime start, HourTime end) throws InvalidTimeRangeException {
		this(start, end, null, false);
	}
	/**
	 * Constructs a new {@code RejectionBlock} with the specified start and end times, and specified SMS.
	 * Will start inactive.
	 * @param start start time of activity
	 * @param end end time of activity
	 * @param sms message to send to rejected calls
	 * @throws InvalidTimeRangeException when end time is before start time
	 */
	RejectionBlock(HourTime start, HourTime end, String sms) throws InvalidTimeRangeException {
		this(start, end, sms, false);
	}
	/**
	 * Constructs a new {@code RejectionBlock} with the specified start and end times, SMS, and starting state.
	 * @param start start time of activity
	 * @param end end time of activity
	 * @param sms message to send to rejected calls
	 * @param enabled whether to enable this rejectionBlock upon construction
	 * @throws InvalidTimeRangeException when end time is before start time
	 */
	RejectionBlock(HourTime start, HourTime end, String sms, boolean enabled) throws InvalidTimeRangeException {
		setStartTime(start);
		setEndTime(end);
		setSMS(sms);
		this.enabled = enabled;
		// TODO Fix logging
		log.info("Successfully constructed new " + getClass().getName() + " with startTime = " + getStartTime() + ", endTime = " + getEndTime() + ", SMS = " + getSMS() + ", enabled = " + isEnabled());
	}
	
	/**
	 * Switches rejectionBlock enabled state. {@code true} to {@code false} or {@code false} to {@code true}.
	 */
	public void switchState() {
		enabled = !enabled;
	}
	
	/**
	 * @return {@code true} if rejectionBlock is enabled. {@code false} if otherwise
	 */
	public boolean isEnabled() {
		return enabled;
	}
	/**
	 * @return {@code true} if rejectionBlock is currently rejecting, {@code false} if otherwise
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * @return start time of rejectionBlock activity
	 */
	public HourTime getStartTime() {
		return start;
	}
	/**
	 * @return end time of rejectionBlock activity
	 */
	public HourTime getEndTime() {
		return end;
	}
	/**
	 * @return SMS this rejectionBlock will send, or {@code null} if no SMS
	 */
	public String getSMS() {
		return sms;
	}
	
	/**
	 * Sets the start time of this rejectionBlock's activity.
	 * @param startTime time to start activity
	 * @throws InvalidTimeRangeException
	 */
	public void setStartTime(HourTime startTime) throws InvalidTimeRangeException {
		end = null;
		start = startTime;
		if (!isValidRange())	// Check whether start time is before end time
			throw new InvalidTimeRangeException(start, end);
	}
	/**
	 * Sets the end time of this rejectionBlock's activity.
	 * @param endTime time to end activity
	 * @throws InvalidTimeRangeException
	 */
	public void setEndTime(HourTime endTime) throws InvalidTimeRangeException {
		end = endTime;		
		if (!isValidRange())	// Check whether start time is before end time
			throw new InvalidTimeRangeException(start, end);
	}
	
	/**
	 * Sets the SMS this rejectionBlock will send to rejected calls
	 * @param sms message to send
	 */
	public void setSMS(String sms) {
		this.sms = sms;
	}
	
	/**
	 * Compares this rejectionBlock to another rejectionBlock.
	 * @param o rejectionBlock to compare to
	 * @return -1 if this rejectionBlock activates before the other, 1 if after, 0 if at the same time
	 */
	@Override
	public int compareTo(RejectionBlock o) {
		return getStartTime().compareTo(o.getStartTime());
	}
	
	@Override
	public boolean equals(Object anObject) {
		if (anObject instanceof RejectionBlock && ((RejectionBlock) anObject).start.compareTo(start) == 0)
			return true;
		return false;
	}
	/*@Override
	public int hashCode() {
		
	}*/
	
	@Override
	public String toString() {
		return getStartTime() + "-" + getEndTime() + " rejectionBlock";
	}
	
	void updateTime(HourTime testTime) {
		if (enabled) {
			if ((start.compareTo(testTime) <= 0) && (end.compareTo(testTime) >= 0))
				active = true;
			else
				active = false;
		}
	}
	
	private boolean isValidRange() {	// Start time should be before end time
		if (getStartTime() == null || getEndTime() == null)
			return true;	// Can't compare if both aren't yet instantiated
		
		if (getStartTime().compareTo(getEndTime()) <= 0)
			return true;
		return false;
	}
}
