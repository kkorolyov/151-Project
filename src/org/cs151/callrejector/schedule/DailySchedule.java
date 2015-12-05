package org.cs151.callrejector.schedule;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cs151.callrejector.schedule.exceptions.HourOutOfBoundsException;
import org.cs151.callrejector.schedule.exceptions.InvalidTimeRangeException;
import org.cs151.callrejector.schedule.exceptions.MinuteOutOfBoundsException;

/**
 * Contains and manages a collection of {@link RejectionBlock} objects.
 * @author Kirill
 */
public class DailySchedule implements Schedule {
	private static final Logger log = Logger.getLogger(DailySchedule.class.getName());
	private static final int updateInterval = 1000;
	private static DailySchedule instance;
	
	private volatile boolean updateRunning;
	private volatile Thread updateThread;
	//private volatile Set<RejectionBlock> rejectionBlocks = new TreeSet<RejectionBlock>();	// rejectionBlocks always sorted
	private volatile List<RejectionBlock> rejectionBlocks = new ArrayList<RejectionBlock>();
	
	/**
	 * @return {@code Schedule} instance
	 */
	public static synchronized Schedule getSchedule() {
		if (instance == null)
			 instance = new DailySchedule();
		return instance;
	}
	
	private DailySchedule() {
		initUpdateThread();
		log.info("New " + DailySchedule.class.getName() + " instantiated successfully");
	}
	
	@Override
	public void addRejectionBlock(HourTime start, HourTime end, String sms) throws InvalidTimeRangeException {		
		addRejectionBlock(start, end, sms, false);
	}
	@Override
	public void addRejectionBlock(HourTime start, HourTime end, String sms, boolean enabled) throws InvalidTimeRangeException {
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
	 * @Deprecated Issues
	 */
	// TODO Fix if possible/necessary
	@Deprecated
	public void updateRejectionBlock(RejectionBlock toUpdate, HourMinuteTime newStart, HourMinuteTime newEnd, String sms) throws InvalidTimeRangeException {
		toUpdate.setStartTime(newStart);
		toUpdate.setEndTime(newEnd);
		toUpdate.setSMS(sms);
	}
	
	@Override
	public void removeRejectionBlock(RejectionBlock toRemove) {
		killUpdateThread();
		rejectionBlocks.remove(toRemove);

		initUpdateThread();
	}
	
	@Override
	public void clear() {
		rejectionBlocks.clear();
	}
	
	private void initUpdateThread() {	// TODO Specify in some SelfUpdater interface?
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
		} catch (HourOutOfBoundsException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		} catch (MinuteOutOfBoundsException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	private void updateBlocks(HourTime testTime) {
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
	
	@Override
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
	@Override
	public ArrayList<RejectionBlock> getAllRejectionBlocksList() {
		return (ArrayList<RejectionBlock>) rejectionBlocks;
	}
}
