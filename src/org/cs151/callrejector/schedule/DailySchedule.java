package org.cs151.callrejector.schedule;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
		initUpdateThread();
		Log.i(TAG, "New " + DailySchedule.class.getSimpleName() + " instantiated successfully");
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
						Log.e(TAG, e.getMessage(), e);
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
			Log.e(TAG, e.getMessage(), e);
		} catch (MinuteOutOfBoundsException e) {
			Log.e(TAG, e.getMessage(), e);
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
				Log.e(TAG, e.getMessage(), e);
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
		Log.i(TAG, "Serialized, file is: " + file.exists() );
	}
	
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
		Log.i(TAG, "Successfully deserialized blocks");
	}
}
