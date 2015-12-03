package org.cs151.callrejector.schedule;

import static org.junit.Assert.*;

import org.cs151.callrejector.schedule.exceptions.InvalidTimeRangeException;
import org.cs151.callrejector.schedule.exceptions.TimeOutOfBoundsException;
import org.junit.Test;

public class RejectionBlockTest {

	@Test
	public void testConstructValidTimeRange() throws InvalidTimeRangeException, TimeOutOfBoundsException {	// Tests for no exception
		new RejectionBlock(new HourMinuteTime(0, 0), new HourMinuteTime(0, 1));
	}
	@Test
	public void testConstructInvalidTimeRange() {
		try {
			new RejectionBlock(new HourMinuteTime(1, 0), new HourMinuteTime(0, 59));
		} catch (InvalidTimeRangeException e) {
			return;	// Testing this
		} catch (TimeOutOfBoundsException e) {
			// Not testing this
		}
		fail("Did not throw InvalidTimeRangeException");
	}

	@Test
	public void testCompare() throws InvalidTimeRangeException, TimeOutOfBoundsException {
		RejectionBlock earlier = new RejectionBlock(new HourMinuteTime(14, 57), new HourMinuteTime(22, 0));
		RejectionBlock later = new RejectionBlock(new HourMinuteTime(14, 58), new HourMinuteTime(21, 0));
		assertTrue(earlier.compareTo(later) < 0);
		assertTrue(later.compareTo(earlier) > 0);	// Test for symmetry
	}
	
	@Test
	public void testGetTime() throws TimeOutOfBoundsException, InvalidTimeRangeException {
		HourMinuteTime startTime = new HourMinuteTime(4, 17), endTime = new HourMinuteTime(8, 59);
		RejectionBlock testBlock = new RejectionBlock(startTime, endTime);
		assertEquals(startTime, testBlock.getStartTime());
		assertTrue(testBlock.getStartTime().compareTo(startTime) == 0);
		assertEquals(endTime, testBlock.getEndTime());
		assertTrue(testBlock.getEndTime().compareTo(endTime) == 0);
	}
	
	@Test
	public void testGetSMS() throws InvalidTimeRangeException, TimeOutOfBoundsException {
		String testSMS = "Test SMS";
		RejectionBlock testBlock = new RejectionBlock(new HourMinuteTime(3, 7), new HourMinuteTime(15, 14), testSMS);
		assertEquals(testSMS, testBlock.getSMS());
	}
}
