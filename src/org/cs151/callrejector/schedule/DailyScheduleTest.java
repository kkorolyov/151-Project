package org.cs151.callrejector.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.cs151.callrejector.schedule.exceptions.HourOutOfBoundsException;
import org.cs151.callrejector.schedule.exceptions.InvalidTimeRangeException;
import org.cs151.callrejector.schedule.exceptions.MinuteOutOfBoundsException;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link DailySchedule}.
 * @author Kirill Korolyov
 */
public class DailyScheduleTest {

	@Before
	public void clearSchedule() {
		DailySchedule.getSchedule().clear();
	}
	
	@Test
	public void testRemove() throws HourOutOfBoundsException, MinuteOutOfBoundsException, InvalidTimeRangeException {
		HourTime testStart = new HourMinuteTime(4, 16), testEnd = new HourMinuteTime(5, 20);
		String testSMS = "Test";
		
		DailySchedule.getSchedule().addRejectionBlock(testStart, testEnd, testSMS);
		RejectionBlock toRemove = DailySchedule.getSchedule().getAllRejectionBlocksList().get(0);
		assertTrue(DailySchedule.getSchedule().getAllRejectionBlocksList().contains(toRemove));	// Test if currently exists
		
		DailySchedule.getSchedule().removeRejectionBlock(toRemove);
		assertTrue(!DailySchedule.getSchedule().getAllRejectionBlocksList().contains(toRemove));	// Test if was removed
	}
	
	@Test
	public void testAdd() throws InvalidTimeRangeException, HourOutOfBoundsException, MinuteOutOfBoundsException {
		HourTime testStart = new HourMinuteTime(1,1), testEnd = new HourMinuteTime(1, 2);
		String testSMS = "Test SMS";
		
		assertTrue(DailySchedule.getSchedule().getAllRejectionBlocksList().isEmpty());	// Initially empty list
		DailySchedule.getSchedule().addRejectionBlock(testStart, testEnd, testSMS);
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
