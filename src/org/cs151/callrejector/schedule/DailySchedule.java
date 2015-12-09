package org.cs151.callrejector.schedule;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

import org.cs151.callrejector.schedule.exceptions.HourOutOfBoundsException;
import org.cs151.callrejector.schedule.exceptions.InvalidTimeRangeException;
import org.cs151.callrejector.schedule.exceptions.MinuteOutOfBoundsException;

import android.util.Log;

/**
 * Contains and manages a collection of {@link RejectionBlock} objects.
 * @author Kirill Korolyov
 * @author Brandon Feist
 */
public class DailySchedule implements Schedule {
	private static final String TAG = DailySchedule.class.getName();
	private static final int updateInterval = 1000;
	private static DailySchedule instance;
	
	private String saveFile;
	private volatile boolean updateRunning;
	private volatile Thread pollThread, updateThread;
	//private volatile Set<RejectionBlock> rejectionBlocks = new TreeSet<RejectionBlock>();	// rejectionBlocks always sorted
	private volatile List<RejectionBlock> rejectionBlocks = new ArrayList<RejectionBlock>();
	private final BlockingQueue<HourTime> currentTimeQueue = new SynchronousQueue<HourTime>();
	
	/**
	 * @return {@code Schedule} instance
	 */
	public static synchronized Schedule getSchedule() {
		if (instance == null)
			 instance = new DailySchedule();
		return instance;
	}
	
	/**
	 * Checks if there is a serialized filed of the rejectionBlock ArrayList.
	 * If the file exists, it is deserialized.
	 */
	private DailySchedule() {
		//Check if Serialize file Exists
		updateRunning = true;
		initUpdateThread();
		initPollTimeThread();
		//Log.i(TAG, "New " + DailySchedule.class.getSimpleName() + " instantiated successfully");
		deSerialize();
	}
	
	@Override
	public void addRejectionBlock(HourTime start, HourTime end, String sms) throws InvalidTimeRangeException {		
		addRejectionBlock(start, end, sms, false);
	}
	
	@Override
	public void addRejectionBlock(HourTime start, HourTime end, String sms, boolean enabled) throws InvalidTimeRangeException {
		killUpdateThread();	// Stop updateThread (to safely edit blocks)
		rejectionBlocks.add(new RejectionBlock(start, end, sms, enabled));	// Edit blocks
		updateRunning = true;
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
		Log.i(TAG, "Trying to remove block " + toRemove);
		Log.i(TAG, String.valueOf(rejectionBlocks.contains(toRemove)));
		Log.i(TAG, String.valueOf(rejectionBlocks.remove(toRemove)));
		updateRunning = true;
		serialize();
	}
	
	@Override
	public void clear() {
		rejectionBlocks.clear();
	}
	
	private void initPollTimeThread() {
		pollThread = new Thread("Time poll") {
			public void run() {
				while (true) {
					try {
						pollTime();
						Thread.sleep(updateInterval);
					} catch (InterruptedException e) {
						Log.e(TAG, e.getMessage(), e);
					} catch (HourOutOfBoundsException e) {
						Log.e(TAG, e.getMessage(), e);
					} catch (MinuteOutOfBoundsException e) {
						Log.e(TAG, e.getMessage(), e);
					}
				}
			}
		};
		pollThread.start();
	}
	
	/**
	 * Initial thread update. Calls updateTime() every second.
	 */
	private void initUpdateThread() {	// TODO Specify in some SelfUpdater interface?
		updateThread = new Thread("Time update") {
			public void run() {
				while (true) {
					updateTime();
					/*try {
						Thread.sleep(updateInterval);	// Avoid perpetually hogging resources
					} catch (InterruptedException e) {
						Log.e(TAG, e.getMessage(), e);
					}*/
				}
			}
		};
		updateThread.start();
	}
	
	private void pollTime() throws HourOutOfBoundsException, MinuteOutOfBoundsException {
		Calendar currentCalendar = Calendar.getInstance();
		HourTime currentTime = new HourMinuteTime(currentCalendar.get(Calendar.HOUR_OF_DAY), currentCalendar.get(Calendar.MINUTE));
		try {
			currentTimeQueue.put(currentTime);
		} catch (InterruptedException e) {
			Log.e(TAG, e.getMessage(), e);
		}
		//Log.i(TAG, "Produced Time: " + currentTime);
	}	
	/**
	 * Updates the schedule clock (Hour & Minute).
	 */
	private void updateTime() {
		try {
			HourTime updateTime = currentTimeQueue.take();
			updateBlocks(updateTime);
			//Log.i(TAG, "Consumed Time: " + updateTime);
		} catch (InterruptedException e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}

	private void updateBlocks(HourTime testTime) {
		if (!updateRunning) {
			//Log.i(TAG, "!updateRunning");
			return;
		}
		
		//Log.i(TAG, "Updating blocks");
		for (RejectionBlock block : rejectionBlocks)
			block.updateTime(testTime);
	}
	
	/**
	 * Ends the thread that updates the schedule time.
	 */
	private void killUpdateThread() {	// Kills updateThread, waits for death
		updateRunning = false;
	}
	
	@Override
	public RejectionBlock getCurrentActiveBlock() {
		for (RejectionBlock block : rejectionBlocks) {
			if (block.isEnabled() && block.isActive())
				return block;
		}
		return null;
	}
	
	public RejectionBlock[] getAllRejectionBlocks() {
		return rejectionBlocks.toArray(new RejectionBlock[rejectionBlocks.size()]);
	}
	@Override
	public ArrayList<RejectionBlock> getAllRejectionBlocksList() {
		return (ArrayList<RejectionBlock>) rejectionBlocks;
	}
	
	@Override
	public String getFileName() {
		return saveFile;
	}
	@Override
	public void setFileName(String fileName) {
		this.saveFile = fileName;
		Log.i(TAG, "Set serialization file to " + fileName);
		deSerialize();	// Try deserializing
	}
	
	/**
	 * Serializes the rejectionBlock ArrayList to file.
	 */
	private void serialize() {
		if (saveFile == null)
			return;
		
		File file = new File(saveFile);
		try {
			ObjectOutputStream obj_stream = new ObjectOutputStream(new FileOutputStream(file));
			obj_stream.writeObject(rejectionBlocks);
			obj_stream.flush();
			obj_stream.close();
		} catch(IOException e) {
			Log.e(TAG, e.getMessage(), e);
		}
		//Log.i(TAG, "Serialized, file is: " + file.exists() );
	}
	
	/**
	 * Deserializes the rejectionBlock ArrayList from file.
	 */
	@SuppressWarnings({ "unchecked"})
	private void deSerialize() {
		if (saveFile == null)
			return;
		
		File file = new File(saveFile);
		if(!file.exists() || file.isDirectory())
			return;
				
		try {
			ObjectInputStream obj_in = new ObjectInputStream(new FileInputStream(file));
			rejectionBlocks = (List<RejectionBlock>) obj_in.readObject();
			obj_in.close();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
		//Log.i(TAG, "Successfully deserialized blocks");
	}
}
