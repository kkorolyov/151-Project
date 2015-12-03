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
public class DailySchedule {
	private static final Logger log = Logger.getLogger(DailySchedule.class.getName());
	private static final int updateInterval = 1000;
	private static DailySchedule instance;
	
	private volatile boolean updateRunning;
	private volatile Thread updateThread;
	//private volatile Set<RejectionBlock> rejectionBlocks = new TreeSet<RejectionBlock>();	// rejectionBlocks always sorted
	private volatile List<RejectionBlock> rejectionBlocks = new ArrayList<RejectionBlock>();	// TODO Temp workaround
	
	/**
	 * @return {@code Schedule} instance
	 */
	public static synchronized DailySchedule getSchedule() {
		if (instance == null)
			 instance = new DailySchedule();
		return instance;
	}
	
	private DailySchedule() {
		initUpdateThread();
		log.info("New " + DailySchedule.class.getName() + " instantiated successfully");
	}
	
	/**
	 * Adds a new {@code RejectionBlock} with the specified start and end times and sms.
	 * Starts disabled.
	 * @param start start time of rejection activity as a {@code Time} object
	 * @param end end time of rejection activity as a {@code Time} object
	 * @param sms sms to send when rejecting
	 * @throws InvalidTimeRangeException 
	 */
	public void addRejectionBlock(HourMinuteTime start, HourMinuteTime end, String sms) throws InvalidTimeRangeException {		
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
	public void addRejectionBlock(HourMinuteTime start, HourMinuteTime end, String sms, boolean enabled) throws InvalidTimeRangeException {
		killUpdateThread();	// Stop updateThread (to safely edit blocks)
		rejectionBlocks.add(new RejectionBlock(start, end, sms, enabled));	// Edit blocks
		initUpdateThread();	// Restart updateThread
	}
	
	/**
	 * Updates a rejectionBlock.
	 * @param toUpdate block to update
	 * @param newStart new start time
	 * @param newEnd new end time
	 * @param sms new SMS
	 * @throws InvalidTimeRangeException
	 * @Deprecated Incomplete
	 */
	@Deprecated
	public void updateRejectionBlock(RejectionBlock toUpdate, HourMinuteTime newStart, HourMinuteTime newEnd, String sms) throws InvalidTimeRangeException {
		toUpdate.setStartTime(newStart);
		toUpdate.setEndTime(newEnd);
		toUpdate.setSMS(sms);
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
						Thread.sleep(updateInterval);	// Avoid perpetually hogging resources
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
			updateBlocks(new HourMinuteTime(currentHour, currentMinute));
		} catch (TimeOutOfBoundsException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	private void updateBlocks(HourMinuteTime testTime) {
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
