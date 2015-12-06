package org.cs151.callrejector.schedule;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cs151.callrejector.schedule.exceptions.HourOutOfBoundsException;
import org.cs151.callrejector.schedule.exceptions.InvalidTimeRangeException;
import org.cs151.callrejector.schedule.exceptions.MinuteOutOfBoundsException;

/**
 * Contains and manages a collection of {@link RejectionBlock} objects.
<<<<<<< HEAD
 * Edited by Brandon Feist
 * @author Kirill
=======
 * @author Kirill Korolyov
>>>>>>> 7cd1ad1a1e67f816fa678e43b0cce7e91c11df58
 */
public class DailySchedule implements Schedule {
	private static final String SAVE_FILE = "blocks.ser";
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
		//Check if Serialize file Exists
		File file = new File(SAVE_FILE);
		if(file.exists() && !file.isDirectory()) {
			deSerialize();
		}
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
		serialize();
	}
	
	/**
	 * Updates a rejectionBlock.
	 * @param toUpdate block to update
	 * @param newStart new start time
	 * @param newEnd new end time
	 * @param sms new SMS
	 * @throws InvalidTimeRangeException
	 */
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
		serialize();
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
	
	private void serialize() {
		try {
			ObjectOutputStream obj_stream = new ObjectOutputStream(new FileOutputStream(new File(SAVE_FILE)));
			obj_stream.writeObject(rejectionBlocks);
			obj_stream.flush();
			obj_stream.close();
		} catch(IOException i) {
			i.printStackTrace();
		}
		File file = new File(SAVE_FILE);
		//log.info("Serialize, file is: " + file.exists() );
	}
	
	@SuppressWarnings({ "unchecked"})
	private void deSerialize() {
		//log.info("Deserialize");
		try {
			ObjectInputStream obj_in = new ObjectInputStream(new FileInputStream(new File(SAVE_FILE)));
			rejectionBlocks = (List<RejectionBlock>) obj_in.readObject();
			obj_in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
