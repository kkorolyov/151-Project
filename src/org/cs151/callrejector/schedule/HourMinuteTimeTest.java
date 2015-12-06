package org.cs151.callrejector.schedule;

import static org.junit.Assert.*;

import org.cs151.callrejector.schedule.exceptions.HourOutOfBoundsException;
import org.cs151.callrejector.schedule.exceptions.MinuteOutOfBoundsException;
import org.junit.Test;

/**
 * Tests {@link HourMinuteTime}.
 * @author Kirill Korolyov
 */
public class HourMinuteTimeTest {
	@Test
	public void testConstruct24hr() throws HourOutOfBoundsException, MinuteOutOfBoundsException {
		int testHour = 10, testMinute = 15;
		
		HourMinuteTime time = new HourMinuteTime(testHour, testMinute);
		assertEquals(testHour, time.getHour());
		assertEquals(testMinute, time.getMinute());
	}
	@Test
	public void testConstruct12hrAM() throws HourOutOfBoundsException, MinuteOutOfBoundsException {
		int testHour = 10, testMinute = 15;
		boolean testPM = false;
		
		HourMinuteTime time = new HourMinuteTime(testHour, testMinute, testPM);		
		assertEquals(testHour, time.getHour());
		assertEquals(testMinute, time.getMinute());
	}
	@Test
	public void testConstruct12hrPM() throws HourOutOfBoundsException, MinuteOutOfBoundsException {
		int testHour = 10, testMinute = 15;
		boolean testPM = true;
		
		HourMinuteTime time = new HourMinuteTime(testHour, testMinute, testPM);
		assertEquals(testHour + 12, time.getHour());	// PM, so +12hr
		assertEquals(testMinute, time.getMinute());
	}
	
	@Test
	public void testConstruct12am() throws HourOutOfBoundsException, MinuteOutOfBoundsException {	// Interesting scenario
		int testHour = 12, testMinute = 10;
		boolean testPM = false;
		
		HourMinuteTime time = new HourMinuteTime(testHour, testMinute, testPM);
		assertEquals(0, time.getHour());
		assertEquals(testMinute, time.getMinute());
	}
	@Test
	public void testConstruct12pm() throws HourOutOfBoundsException, MinuteOutOfBoundsException {	// Interesting scenario
		int testHour = 12, testMinute = 30;
		boolean testPM = true;
		
		HourMinuteTime time = new HourMinuteTime(testHour, testMinute, testPM);
		assertEquals(12, time.getHour());
		assertEquals(testMinute, time.getMinute());
	}
	
	@Test
	public void testTimeOutOfBoundsHourSmall() throws MinuteOutOfBoundsException {
		int smallHour = -1, testMinute = 5;
		try {
			new HourMinuteTime(smallHour, testMinute);
		} catch (HourOutOfBoundsException e) {
			return;
		}
		fail("No exception thrown for hour = " + smallHour);
	}
	@Test
	public void testTimeOutOfBoundsMinuteSmall() throws HourOutOfBoundsException {
		int testHour = 20, smallMinute = -1;
		try {
			new HourMinuteTime(testHour, smallMinute);
		} catch (MinuteOutOfBoundsException e) {
			return;
		}
		fail("No exception thrown for minute = " + smallMinute);
	}
	@Test
	public void testTimeOutOfBoundsHourLarge() throws MinuteOutOfBoundsException {
		int largeHour = 24, testMinute = 5;
		try {
			new HourMinuteTime(largeHour, testMinute);
		} catch (HourOutOfBoundsException e) {
			return;
		}
		fail("No exception thrown for hour = " + largeHour);
	}
	@Test
	public void testTimeOutOfBoundsMinuteLarge() throws HourOutOfBoundsException {
		int testHour = 20, largeMinute = 60;
		try {
			new HourMinuteTime(testHour, largeMinute);
		} catch (MinuteOutOfBoundsException e) {
			return;
		}
		fail("No exception thrown for minute = " + largeMinute);
	}
	
	@Test
	public void testCompareHours() throws HourOutOfBoundsException, MinuteOutOfBoundsException {
		int testHourEarlier = 13, testMinuteEarlier = 57;
		int testHourLater = 14, testMinuteLater = 0;
		HourMinuteTime earlier = new HourMinuteTime(testHourEarlier, testMinuteEarlier), later = new HourMinuteTime(testHourLater, testMinuteLater);
		assertTrue(earlier.compareTo(later) < 0);
		assertTrue(later.compareTo(earlier) > 0);	// Test for symmetry
	}
	@Test
	public void testCompareMinutes() throws HourOutOfBoundsException, MinuteOutOfBoundsException {
		HourMinuteTime earlier = new HourMinuteTime(14, 57), later = new HourMinuteTime(14, 58);
		assertTrue(earlier.compareTo(later) < 0);
		assertTrue(later.compareTo(earlier) > 0);	// Test for symmetry
	}
	
	@Test
	public void testToString12hrAM() throws HourOutOfBoundsException, MinuteOutOfBoundsException {
		int testHour = 3, testMinute = 46;
		String expectedToString = "3:46" + HourMinuteTime.MERIDIEM_AM;
		HourMinuteTime testTime = new HourMinuteTime(testHour, testMinute);
		assertEquals(expectedToString, testTime.toString12Hour());
	}
	@Test
	public void testToString12hrPM() throws HourOutOfBoundsException, MinuteOutOfBoundsException {
		int testHour = 15, testMinute = 46;
		String expectedToString = "3:46" + HourMinuteTime.MERIDIEM_PM;
		HourMinuteTime testTime = new HourMinuteTime(testHour, testMinute);
		assertEquals(expectedToString, testTime.toString12Hour());
	}
	@Test
	public void testToString24hr() throws HourOutOfBoundsException, MinuteOutOfBoundsException {
		int testHour = 15, testMinute = 46;
		String expectedToString = String.valueOf(testHour) + ":" + String.valueOf(testMinute);
		HourMinuteTime testTime = new HourMinuteTime(testHour, testMinute);
		assertEquals(expectedToString, testTime.toString());
	}
}