package org.cs151.callrejector.schedule;

import java.io.Serializable;

import org.cs151.callrejector.schedule.exceptions.InvalidTimeRangeException;

import android.util.Log;

/**
 * Provides tracking of time within a specified time frame.
 * @author Kirill Korolyov
 * @author Brandon Feist
 */
public class RejectionBlock implements Comparable<RejectionBlock>, Serializable {
	private static final long serialVersionUID = -2969684380343526177L;
	private static final String TAG = RejectionBlock.class.getName();
	
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
		Log.i(TAG, "Successfully constructed new " + getClass().getName() + " with startTime = " + getStartTime() + ", endTime = " + getEndTime() + ", SMS = " + getSMS() + ", enabled = " + isEnabled());
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((sms == null) ? 0 : sms.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RejectionBlock))
			return false;
		
		RejectionBlock other = (RejectionBlock) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (sms == null) {
			if (other.sms != null)
				return false;
		} else if (!sms.equals(other.sms))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return getStartTime() + "-" + getEndTime() + " rejectionBlock";
	}
	
	void updateTime(HourTime testTime) {	// Test if ready to activate/deactivate
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
