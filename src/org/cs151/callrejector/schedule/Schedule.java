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
	private static final Logger log = Logger.getLogger(Filter.class.getName());
	
	private List<Filter> filterList = new LinkedList<>();
	
	public Schedule() {
		log.info("New " + Schedule.class.getName() + " instantiated successfully");
	}
	
	/**
	 * Adds a new {@code Filter} with the specified start and end times.
	 * @param start start time of filter activity, where int[0] = hour, int[1] = minute
	 * @param end end time of filter activity, where int[0] = hour, int[1] = minute
	 */
	public void addFilter(int[] start, int[] end) {
		try {
			addFilter(new Time(start[0], start[1]), new Time(end[0], end[1]));
		} catch (TimeOutOfBoundsException e) {
			log.severe(e.getMessage());
		}
	}
	/**
	 * Adds a new {@code Filter} with the specified start and end times.
	 * @param start start time of filter activity as a {@code Time} object
	 * @param end end time of filter activity as a {@code Time} object
	 */
	public void addFilter(Time start, Time end) {
		try {
			filterList.add(new Filter(start, end));
		} catch (InvalidTimeRangeException e) {
			// TODO remove this faulty filter
			log.severe(e.getMessage());
		}
	}
	
	/**
	 * Removes the {@code Filter} at the specified index and returns the updated filter list.
	 * @param index index of filter to remove
	 * @return updated filter list after removal
	 */
	public List<Filter> removeFilter(int index) {
		filterList.remove(index);
		return getFilterList();
	}
	
	public List<Filter> getFilterList() {
		return new LinkedList<Filter>(filterList);	// Return copy
	}
}
