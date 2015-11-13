/*package org.cs151.callrejector.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.cs151.callrejector.schedule.exceptions.TimeOutOfBoundsException;
import org.junit.Test;

public class TimeTest {
	@Test
	public void testConstruct24hr() throws TimeOutOfBoundsException {
		int testHour = 10, testMinute = 15;
		
		Time time = new Time(testHour, testMinute);
		assertEquals(10, time.getHour());
		assertEquals(15, time.getMinute());
	}
	@Test
	public void testConstruct12hrAM() throws TimeOutOfBoundsException {
		int testHour = 10, testMinute = 15;
		boolean testPM = false;
		
		Time time = new Time(testHour, testMinute, testPM);		
		assertEquals(testHour, time.getHour());
		assertEquals(testMinute, time.getMinute());
	}
	@Test
	public void testConstruct12hrPM() throws TimeOutOfBoundsException {
		int testHour = 10, testMinute = 15;
		boolean testPM = true;
		
		Time time = new Time(testHour, testMinute, testPM);
		assertEquals(testHour + 12, time.getHour());	// PM, so +12hr
		assertEquals(testMinute, time.getMinute());
	}
	
	@Test
	public void testConstruct12am() throws TimeOutOfBoundsException {	// Interesting scenario
		int testHour = 12, testMinute = 10;
		boolean testPM = false;
		
		Time time = new Time(testHour, testMinute, testPM);
		assertEquals(0, time.getHour());
		assertEquals(testMinute, time.getMinute());
	}
	@Test
	public void testConstruct12pm() throws TimeOutOfBoundsException {	// Interesting scenario
		int testHour = 12, testMinute = 30;
		boolean testPM = true;
		
		Time time = new Time(testHour, testMinute, testPM);
		assertEquals(12, time.getHour());
		assertEquals(testMinute, time.getMinute());
	}
	
	@Test
	public void testTimeOutOfBoundsHourSmall() {
		int smallHour = -1, testMinute = 5;
		try {
			new Time(smallHour, testMinute);
		} catch (TimeOutOfBoundsException e) {
			return;
		}
		fail("No exception thrown for hour = " + smallHour);
	}
	@Test
	public void testTimeOutOfBoundsMinuteSmall() {
		int testHour = 20, smallMinute = -1;
		try {
			new Time(testHour, smallMinute);
		} catch (TimeOutOfBoundsException e) {
			return;
		}
		fail("No exception thrown for minute = " + smallMinute);
	}
	@Test
	public void testTimeOutOfBoundsHourLarge() {
		int largeHour = 24, testMinute = 5;
		try {
			new Time(largeHour, testMinute);
		} catch (TimeOutOfBoundsException e) {
			return;
		}
		fail("No exception thrown for hour = " + largeHour);
	}
	@Test
	public void testTimeOutOfBoundsMinuteLarge() {
		int testHour = 20, largeMinute = 60;
		try {
			new Time(testHour, largeMinute);
		} catch (TimeOutOfBoundsException e) {
			return;
		}
		fail("No exception thrown for minute = " + largeMinute);
	}
	
	@Test
	public void testGetTimeInMillis() throws TimeOutOfBoundsException {
		int testHour = 15, testMinute = 27;
		long expectedMS = (testHour * 60 + testMinute) * 60 * 1000;
		Time time = new Time(testHour, testMinute);
		assertEquals(expectedMS, time.getTimeInMillis());
	}
}*/
