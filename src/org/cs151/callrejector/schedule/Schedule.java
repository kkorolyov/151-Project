package org.cs151.callrejector.schedule;

import java.util.ArrayList;

import org.cs151.callrejector.schedule.exceptions.InvalidTimeRangeException;

/**
 * Generic interface for a {@link RejectionBlock} manager.
 * @author Kirill Korolyov
 */
public interface Schedule {
	/**
	 * Adds a new {@code RejectionBlock} with the specified start and end times and sms.
	 * Starts disabled.
	 * @param start start time of rejection activity as a {@code Time} object
	 * @param end end time of rejection activity as a {@code Time} object
	 * @param sms sms to send when rejecting
	 * @throws InvalidTimeRangeException 
	 */
	void addRejectionBlock(HourTime start, HourTime end, String sms) throws InvalidTimeRangeException;
	/**
	 * Adds a new {@code RejectionBlock} with the specified start and end times, sms, and enabled status.
	 * @param start start time of rejection activity as a {@code Time} object
	 * @param end end time of rejection activity as a {@code Time} object
	 * @param sms sms to send when rejecting
	 * @param enabled whether block initially enabled
	 * @throws InvalidTimeRangeException 
	 */
	void addRejectionBlock(HourTime start, HourTime end, String sms, boolean enabled) throws InvalidTimeRangeException;

	/**
	 * Removes the specified {@code RejectionBlock} from the list.
	 * @param toRemove reference of rejectionBlock to remove
	 */
	void removeRejectionBlock(RejectionBlock toRemove);
	
	/**
	 * Removes all {@code RejectionBlock}s.
	 */
	void clear();
	/**
	 * @return currently enabled and active rejectionBlock, or {@code null} if no such block exists
	 */
	RejectionBlock getCurrentActiveBlock();
	
	/**
	 * @return all rejectionBlocks stored in this schedule
	 */
	RejectionBlock[] getAllRejectionBlocks();
	/**
	 * @return all rejectionBlocks stored in this schedule, as an arrayList
	 */
	ArrayList<RejectionBlock> getAllRejectionBlocksList();
	
	/**
	 * @return name of file being serialized to
	 */
	String getFileName();
	/**
	 * @param fileName complete file path to serialize to
	 */
	void setFileName(String fileName);
}
