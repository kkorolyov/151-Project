package org.cs151.callrejector.schedule;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cs151.callrejector.schedule.exceptions.InvalidTimeRangeException;
import org.cs151.callrejector.schedule.exceptions.TimeOutOfBoundsException;

/**
 * Contains and manages a collection of {@link RejectionBlock} objects.
 * @author Kirill
 */
public class Schedule {
	private static final Logger log = Logger.getLogger(Schedule.class.getName());
	private static final int updateInterval = 1000;
	private static Schedule instance;
	
	private volatile boolean updateRunning;
	private volatile Thread updateThread;
	//private volatile Set<RejectionBlock> rejectionBlocks = new TreeSet<RejectionBlock>();	// rejectionBlocks always sorted
	private volatile List<RejectionBlock> rejectionBlocks = new ArrayList<RejectionBlock>();	// TODO Temp workaround
	
	/**
	 * @return {@code Schedule} instance
	 */
	public static synchronized Schedule getSchedule() {
		if (instance == null)
			 instance = new Schedule();
		return instance;
	}
	
	private Schedule() {
		initUpdateThread();
		log.info("New " + Schedule.class.getName() + " instantiated successfully");
	}
	
	/**
	 * Adds a new {@code RejectionBlock} with the specified start and end times and sms.
	 * Starts disabled.
	 * @param start start time of rejection activity as a {@code Time} object
	 * @param end end time of rejection activity as a {@code Time} object
	 * @param sms sms to send when rejecting
	 * @throws InvalidTimeRangeException 
	 */
	public void addRejectionBlock(Time start, Time end, String sms) throws InvalidTimeRangeException {		
		addRejectionBlock(start, end, sms, false);
	}
	/**
	 * Adds a new {@code RejectionBlock} with the specified start and end times, sms, and enabled status.
	 * @param start start time of rejection activity as a {@code Time} object
	 * @param end end time of rejection activity as a {@code Time} object
	 * @param sms sms to send when rejecting
	 * @param enabled whether block initially enabled
	 * @throws InvalidTimeRangeException 
	 */
	public void addRejectionBlock(Time start, Time end, String sms, boolean enabled) throws InvalidTimeRangeException {
		killUpdateThread();	// Stop updateThread (to safely edit blocks)
		rejectionBlocks.add(new RejectionBlock(start, end, sms, enabled));	// Edit blocks
		initUpdateThread();	// Restart updateThread
	}
	
	public void updateRejectionBlock(RejectionBlock toUpdate, Time newStart, Time newEnd, String sms) throws InvalidTimeRangeException, TimeOutOfBoundsException {
		if (!rejectionBlocks.contains(toUpdate))
			addRejectionBlock(new Time(0, 0), new Time(1, 1), null);
		else {
			toUpdate.setStartTime(newStart);
			toUpdate.setEndTime(newEnd);
			toUpdate.setSMS(sms);
		}
	}
	
	/**
	 * Removes the specified {@code RejectionBlock} from the list.
	 * @param toRemove reference of rejectionBlock to remove
	 */
	public void removeRejectionBlock(RejectionBlock toRemove) {
		killUpdateThread();
		rejectionBlocks.remove(toRemove);
		initUpdateThread();
	}
	
	private void initUpdateThread() {
		updateRunning = true;
		updateThread = new Thread("Time update") {
			public void run() {
				while (updateRunning) {
					updateTime();
					try {
						Thread.sleep(updateInterval);
					} catch (InterruptedException e) {
						log.log(Level.SEVERE, e.getMessage(), e);
					}
				}
			}
		};
		updateThread.start();
	}
	private void updateTime() {
		Calendar currentCalendar = Calendar.getInstance();
		int currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY), currentMinute = currentCalendar.get(Calendar.MINUTE);
		try {
			updateBlocks(new Time(currentHour, currentMinute));
		} catch (TimeOutOfBoundsException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	private void updateBlocks(Time testTime) {
		for (RejectionBlock block : rejectionBlocks)
			block.updateTime(testTime);
	}
	
	private void killUpdateThread() {	// Kills updateThread, waits for death
		updateRunning = false;
		while (updateThread.isAlive()) {
			try {
				Thread.sleep(updateInterval / 2);
			} catch (InterruptedException e) {
				log.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}
	
	/**
	 * @return currently enabled and active rejectionBlock, or {@code null} if no such block exists
	 */
	public RejectionBlock getCurrentActiveBlock() {
		for (RejectionBlock block : rejectionBlocks) {
			if (block.isEnabled() && block.isActive())
				return block;
		}
		return null;
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
	public ArrayList<RejectionBlock> getAllRejectionBlocksList() {	// TODO Temp method
		return (ArrayList<RejectionBlock>) rejectionBlocks;
	}
}
