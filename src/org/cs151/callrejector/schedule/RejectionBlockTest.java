package org.cs151.callrejector.schedule;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;

import org.cs151.callrejector.schedule.exceptions.InvalidTimeRangeException;
import org.cs151.callrejector.schedule.exceptions.TimeOutOfBoundsException;
import org.junit.Test;

public class RejectionBlockTest {

	@Test
	public void testConstructValidTimeRange() throws InvalidTimeRangeException, TimeOutOfBoundsException {	// Tests for no exception
		new RejectionBlock(new Time(0, 0), new Time(0, 1));
	}
	@Test
	public void testConstructInvalidTimeRange() {
		try {
			new RejectionBlock(new Time(1, 0), new Time(0, 59));
		} catch (InvalidTimeRangeException e) {
			return;	// Testing this
		} catch (TimeOutOfBoundsException e) {
			// Not testing this
		}
		fail("Did not throw InvalidTimeRangeException");
	}

	@Test
	public void testCompare() throws InvalidTimeRangeException, TimeOutOfBoundsException {
		RejectionBlock earlier = new RejectionBlock(new Time(14, 57), new Time(22, 0));
		RejectionBlock later = new RejectionBlock(new Time(14, 58), new Time(21, 0));
		assertTrue(earlier.compareTo(later) < 0);
		assertTrue(later.compareTo(earlier) > 0);	// Test for symmetry
	}
	
	@Test
	public void testMillisInterval() throws InvalidTimeRangeException, TimeOutOfBoundsException {
		int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY), currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
		System.out.println(String.valueOf(currentHour) + "-" + String.valueOf(currentMinute));
		System.out.println(String.valueOf(System.currentTimeMillis()));
		int testStartHour = currentHour - 1, testStartMinute = currentMinute - 1;
		int testEndHour = currentHour + 1, testEndMinute = currentMinute + 1;
		RejectionBlock testBlock = new RejectionBlock(new Time(testStartHour, testStartMinute), new Time(testEndHour, testEndMinute));
		assertTrue(testBlock.getStartTime().compareTime(currentHour, currentMinute) < 0);
		assertTrue(testBlock.getEndTime().compareTime(currentHour, currentMinute) > 0);
	}
}
