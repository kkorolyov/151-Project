package org.cs151.callrejector.schedule;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.cs151.callrejector.rejector.Rejector;
import org.cs151.callrejector.schedule.exceptions.InvalidTimeRangeException;

/**
 * Provides call rejection actions within a specified time frame.
 * @author Kirill
 */
public class RejectionBlock implements Comparable<RejectionBlock> {
	private static final Logger log = Logger.getLogger(RejectionBlock.class.getName());
	
	private Time start, end;
	private String sms;	// SMS to send to rejected call
	
	private Rejector rejector;
	
	/**
	 * Constructs a new {@code Filter} with the specified start and end times.
	 * @param start start time of activity
	 * @param end end time of activity
	 * @throws InvalidTimeRangeException when end time is before start time
	 */
	public RejectionBlock(Time start, Time end) throws InvalidTimeRangeException {
		this(start, end, null);
	}
	/**
	 * Constructs a new {@code Filter} with the specified start and end times, and specified SMS.
	 * @param start start time of activity
	 * @param end end time of activity
	 * @param sms message to send to rejected calls
	 * @throws InvalidTimeRangeException when end time is before start time
	 */
	public RejectionBlock(Time start, Time end, String sms) throws InvalidTimeRangeException {
		setStartTime(start);
		setEndTime(end);
		setSMS(sms);
		
		log.info("Constructed new " + getClass().getName() + " with startTime = " + getStartTime() + ", endTime = " + getEndTime() + ", SMS = " + getSMS() + ", instantiated successfully");
		initReject();
	}
	
	/**
	 * @return start time of filter activity
	 */
	public Time getStartTime() {
		return start;
	}
	/**
	 * @return end time of filter activity
	 */
	public Time getEndTime() {
		return end;
	}
	/**
	 * @return SMS this filter will send, or {@code null} if no SMS
	 */
	public String getSMS() {
		return sms;
	}
	
	/**
	 * Sets the start time of this filter's activity.
	 * @param time time to start activity
	 * @throws InvalidTimeRangeException
	 */
	public void setStartTime(Time startTime) throws InvalidTimeRangeException {
		start = startTime;		
		if (!isValidRange())	// Check whether start time is before end time
			throw new InvalidTimeRangeException(start, end);
	}
	/**
	 * Sets the end time of this filter's activity.
	 * @param time time to end activity
	 * @throws HourOutOfBoundsException
	 * @throws InvalidTimeRangeException
	 */
	public void setEndTime(Time endTime) throws InvalidTimeRangeException {
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
	public String toString() {
		return getStartTime() + "-" + getEndTime() + " rejectionBlock";
	}
	
	private void initReject() {
		long currentTimeMillis = System.currentTimeMillis(), startTimeMillis = getStartTime().getTimeInMillis();
		long timeLeft = startTimeMillis - currentTimeMillis;
		
		if (timeLeft > 0) {	// Can't wait backwards
			new Thread(this.toString()) {
				public void run() {
					try {
						Thread.sleep(timeLeft);
						rejector = new Rejector();
						//Rejector.initReject(sms, getEndTime()); TODO Have Rejector's initReject() return at endTime
					} catch (InterruptedException e) {
						log.log(Level.SEVERE, e.getMessage(), e);
					}
				}
			}.start();
			log.info(toString() + " successfully scheduled rejecting at " + timeLeft + "ms from now");
		}
		else
			log.severe(toString() + " failed to initialize rejecting ");
	}
	
	private boolean isValidRange() {	// Start time should be before end time
		if (getStartTime() == null || getEndTime() == null)
			return true;	// Can't compare if both aren't yet instantiated
		
		if (getStartTime().compareTo(getEndTime()) < 0)
			return true;
		return false;
	}
}
