package org.cs151.callrejector.schedule;

import java.util.LinkedList;
import java.util.List;

import org.cs151.callrejector.schedule.exceptions.HourOutOfBoundsException;
import org.cs151.callrejector.schedule.exceptions.InvalidTimeRangeException;

/**
 * Contains and manages a collection of {@code Filter} objects.
 * @author Kirill
 */
public class Schedule {
	public static final int HOUR_BOUND_START = 1, HOUR_BOUND_END = 24;
	
	private List<Filter> filterList = new LinkedList<>();
	
	public Schedule() {
		
	}
	
	/**
	 * Adds a new {@code Filter} with the specified start and end times.
	 * @param start start time of filter activity
	 * @param end end time of filter activity
	 * @throws HourOutOfBoundsException
	 * @throws InvalidTimeRangeException
	 */
	public void addFilter(int start, int end) throws HourOutOfBoundsException, InvalidTimeRangeException {
		filterList.add(new Filter(start, end));
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
