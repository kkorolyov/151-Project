package com.example.callrejector;

import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import org.cs151.callrejector.schedule.Time;
import org.cs151.callrejector.schedule.exceptions.InvalidTimeRangeException;
import org.cs151.callrejector.schedule.exceptions.TimeOutOfBoundsException;

/**
 * Contains and manages a collection of {@code Filter} objects.
 * @author Kirill
 */
public class Schedule {
	private static final Logger log = Logger.getLogger(Schedule.class.getName());
	
	private Set<RejectionBlock> rejectionBlocks = new TreeSet<RejectionBlock>();	// rejectionBlocks always sorted
	
	public Schedule() {
		log.info("New " + Schedule.class.getName() + " instantiated successfully");
	}
	
	/**
	 * Adds a new {@code RejectionBlock} with the specified start and end times.
	 * @param start start time of rejection activity, where int[0] = hour, int[1] = minute
	 * @param end end time of rejection activity, where int[0] = hour, int[1] = minute
	 */
	public void addRejectionBlock(int[] start, int[] end) {
		try {
			addRejectionBlock(new Time(start[0], start[1]), new Time(end[0], end[1]));
		} catch (TimeOutOfBoundsException e) {
			log.severe(e.getMessage());
		}
	}
	/**
	 * Adds a new {@code RejectionBlock} with the specified start and end times.
	 * @param start start time of rejection activity as a {@code Time} object
	 * @param end end time of rejection activity as a {@code Time} object
	 */
	public void addRejectionBlock(Time start, Time end) {
		try {
			rejectionBlocks.add(new RejectionBlock(start, end));
		} catch (InvalidTimeRangeException e) {
			log.severe(e.getMessage());
		}
	}
	
	/**
	 * Removes the specified {@code RejectionBlock} from the list.
	 * @param toRemove reference of rejectionBlock to remove
	 */
	public void removeRejectionBlock(RejectionBlock toRemove) {
		rejectionBlocks.remove(toRemove);
	}
	
	/**
	 * @return all rejectionBlocks stored in this schedule
	 */
	public RejectionBlock[] getAllRejectionBlocks() {
		return rejectionBlocks.toArray(new RejectionBlock[rejectionBlocks.size()]);
	}
}
