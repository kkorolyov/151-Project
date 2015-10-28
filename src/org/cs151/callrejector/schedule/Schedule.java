package org.cs151.callrejector.schedule;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.cs151.callrejector.schedule.exceptions.InvalidTimeRangeException;
import org.cs151.callrejector.schedule.exceptions.TimeOutOfBoundsException;

/**
 * Contains and manages a collection of {@code Filter} objects.
 * @author Kirill
 */
public class Schedule {
	private static final Logger log = Logger.getLogger(Schedule.class.getName());
	
	private List<RejectionBlock> rejectionBlockList = new LinkedList<>();
	
	public Schedule() {
		log.info("New " + Schedule.class.getName() + " instantiated successfully");
	}
	
	/**
	 * Adds a new {@code RejectionBlock} with the specified start and end times.
	 * @param start start time of filter activity, where int[0] = hour, int[1] = minute
	 * @param end end time of filter activity, where int[0] = hour, int[1] = minute
	 */
	public void addRejectionBlock(int[] start, int[] end) {
		try {
			addRejectionBlock(new Time(start[0], start[1]), new Time(end[0], end[1]));
		} catch (TimeOutOfBoundsException e) {
			rejectionBlockList.remove(rejectionBlockList.size() - 1);
			log.severe(e.getMessage());
		}
	}
	/**
	 * Adds a new {@code RejectionBlock} with the specified start and end times.
	 * @param start start time of filter activity as a {@code Time} object
	 * @param end end time of filter activity as a {@code Time} object
	 */
	public void addRejectionBlock(Time start, Time end) {
		try {
			rejectionBlockList.add(new RejectionBlock(start, end));
		} catch (InvalidTimeRangeException e) {
			rejectionBlockList.remove(rejectionBlockList.size() - 1);
			log.severe(e.getMessage());
		}
	}
	
	/**
	 * Removes the {@code RejectionBlock} at the specified index and returns the updated filter list.
	 * @param index index of filter to remove
	 * @return updated filter list after removal
	 */
	public List<RejectionBlock> removeFilter(int index) {
		rejectionBlockList.remove(index);
		return getRejectionBlockList();
	}
	
	public List<RejectionBlock> getRejectionBlockList() {
		return new LinkedList<RejectionBlock>(rejectionBlockList);	// Return copy
	}
}
