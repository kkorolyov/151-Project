package org.cs151.callrejector.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import org.cs151.callrejector.schedule.exceptions.InvalidTimeRangeException;

/**
 * Contains and manages a collection of {@code Filter} objects.
 * @author Kirill
 */
public class Schedule {
	private static final Logger log = Logger.getLogger(Schedule.class.getName());
	private static final Schedule instance = new Schedule();
	
	//private Set<RejectionBlock> rejectionBlocks = new TreeSet<RejectionBlock>();	// rejectionBlocks always sorted
	private List<RejectionBlock> rejectionBlocks = new ArrayList<RejectionBlock>();	// TODO Temp workaround
	
	/**
	 * @return {@code Schedule} instance
	 */
	public static synchronized Schedule getSchedule() {
		return instance;
	}
	
	private Schedule() {
		log.info("New " + Schedule.class.getName() + " instantiated successfully");
	}
	
	/**
	 * Adds a new {@code RejectionBlock} with the specified start and end times.
	 * @param start start time of rejection activity as a {@code Time} object
	 * @param end end time of rejection activity as a {@code Time} object
	 */
	public void addRejectionBlock(Time start, Time end) {
		addRejectionBlock(start, end, null);
	}
	public void addRejectionBlock(Time start, Time end, String sms) {
		try {
			rejectionBlocks.add(new RejectionBlock(start, end, sms));
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
	 * @return {@code true} if at least 1 rejectionBlock is active at the moment, {@code false} if otherwise
	 */
	public boolean existsActiveRejectionBlock() {
		for (RejectionBlock rejectionBlock : rejectionBlocks) {
			if (rejectionBlock.isEnabled() && rejectionBlock.isActive())
				return true;
		}
		return false;
	}
	
	/**
	 * @return all rejectionBlocks stored in this schedule
	 */
	public RejectionBlock[] getAllRejectionBlocks() {
		return rejectionBlocks.toArray(new RejectionBlock[rejectionBlocks.size()]);
	}
	/**
	 * @return all rejectionBlocks stored in this schedule, as an arrayList
	 */
	public ArrayList<RejectionBlock> getAllRejectionBlocksList() {
		return (ArrayList<RejectionBlock>) rejectionBlocks;
	}
}
