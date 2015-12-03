package org.cs151.callrejector.schedule;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.cs151.callrejector.schedule.exceptions.HourOutOfBoundsException;
import org.cs151.callrejector.schedule.exceptions.InvalidTimeRangeException;
import org.cs151.callrejector.schedule.exceptions.MinuteOutOfBoundsException;
import org.junit.Test;

public class ScheduleTest {

	@Test
	public void testRemove() {
		RejectionBlock test;	// TODO Finish
	}
	
	@Test
	public void testAdd() throws InvalidTimeRangeException, HourOutOfBoundsException, MinuteOutOfBoundsException {
		DailySchedule.getSchedule().addRejectionBlock(new HourMinuteTime(1,1), new HourMinuteTime(1, 2), "");
	}
	
	@Test
	public void testGetActive() throws HourOutOfBoundsException, MinuteOutOfBoundsException, InvalidTimeRangeException, InterruptedException {
		int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY), currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
		int nextHour = currentHour + 1, nextMinute = currentMinute;
		if (nextHour > HourMinuteTime.HOUR_BOUND_END) {
			nextHour = currentHour;
			nextMinute = currentMinute + 1;
			if (nextMinute > HourMinuteTime.MINUTE_BOUND_END) {
				nextMinute = currentMinute;
				currentMinute--;	// Can't move end time forward? Move start time back
			}
		}
		String testString = "Test String";	// Will test with this
		DailySchedule.getSchedule().addRejectionBlock(new HourMinuteTime(currentHour, currentMinute), new HourMinuteTime(nextHour, nextMinute), testString, true);
		Thread.sleep(100);	// Give block enough time to check
		assertEquals(testString, DailySchedule.getSchedule().getCurrentActiveBlock().getSMS());
	}
	@Test
	// TODO Fix?
	public void testNoActive() throws InvalidTimeRangeException, HourOutOfBoundsException, MinuteOutOfBoundsException, InterruptedException {
		int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY), currentMinute = Calendar.getInstance().get(Calendar.MINUTE) - 1;
		int prevHour = currentHour - 1, prevMinute = currentMinute;
		if (prevHour < HourMinuteTime.HOUR_BOUND_START) {
			prevHour = currentHour;
			prevMinute = currentMinute - 1;
			if (prevMinute < HourMinuteTime.MINUTE_BOUND_END) {
				prevMinute = currentMinute++;	// Can't move start time back? Move both times forward
				prevHour = currentHour++;
			}
		}
		String testString = "Test String";	// Will test with this
		DailySchedule.getSchedule().addRejectionBlock(new HourMinuteTime(prevHour, prevMinute), new HourMinuteTime(currentHour, currentMinute), testString, true);
		Thread.sleep(100);	// Give block enough time to check
		assertTrue(DailySchedule.getSchedule().getCurrentActiveBlock() == null);
	}

}
