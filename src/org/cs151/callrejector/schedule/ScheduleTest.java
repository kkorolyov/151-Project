package org.cs151.callrejector.schedule;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.cs151.callrejector.schedule.exceptions.InvalidTimeRangeException;
import org.cs151.callrejector.schedule.exceptions.TimeOutOfBoundsException;
import org.junit.Test;

public class ScheduleTest {

	@Test
	public void testGetActive() throws TimeOutOfBoundsException, InvalidTimeRangeException, InterruptedException {
		int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY), currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
		int nextHour = currentHour + 1, nextMinute = currentMinute;
		if (nextHour > Time.HOUR_BOUND_END) {
			nextHour = currentHour;
			nextMinute = currentMinute + 1;
			if (nextMinute > Time.MINUTE_BOUND_END) {
				nextMinute = currentMinute;
				currentMinute--;	// Can't move end time forward? Move start time back
			}
		}
		String testString = "Test String";	// Will test with this
		DailySchedule.getSchedule().addRejectionBlock(new Time(currentHour, currentMinute), new Time(nextHour, nextMinute), testString, true);
		Thread.sleep(100);	// Give block enough time to check
		assertEquals(testString, DailySchedule.getSchedule().getCurrentActiveBlock().getSMS());
	}
	@Test
	// TODO Fix?
	public void testNoActive() throws InvalidTimeRangeException, TimeOutOfBoundsException, InterruptedException {
		int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY), currentMinute = Calendar.getInstance().get(Calendar.MINUTE) - 1;
		int prevHour = currentHour - 1, prevMinute = currentMinute;
		if (prevHour < Time.HOUR_BOUND_START) {
			prevHour = currentHour;
			prevMinute = currentMinute - 1;
			if (prevMinute < Time.MINUTE_BOUND_END) {
				prevMinute = currentMinute++;	// Can't move start time back? Move both times forward
				prevHour = currentHour++;
			}
		}
		String testString = "Test String";	// Will test with this
		DailySchedule.getSchedule().addRejectionBlock(new Time(prevHour, prevMinute), new Time(currentHour, currentMinute), testString, true);
		Thread.sleep(100);	// Give block enough time to check
		assertTrue(DailySchedule.getSchedule().getCurrentActiveBlock() == null);
	}

}
