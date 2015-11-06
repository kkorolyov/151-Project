package org.cs151.callrejector.schedule;

import static org.junit.Assert.*;

import org.cs151.callrejector.schedule.exceptions.TimeOutOfBoundsException;
import org.junit.Test;

public class TimeTest {

	@Test
	public void testConstruct24hr() throws TimeOutOfBoundsException {
		int testHour = 10, testMinute = 15;
		
		Time time = construct24hr(testHour, testMinute);
		assertEquals(10, time.getHour());
		assertEquals(15, time.getMinute());
	}

	@Test
	public void testConstruct12hrAM() throws TimeOutOfBoundsException {
		int testHour = 10, testMinute = 15;
		boolean testPM = false;
		
		Time time = construct12hr(testHour, testMinute, testPM);		
		assertEquals(testHour, time.getHour());
		assertEquals(testMinute, time.getMinute());
	}
	@Test
	public void testConstruct12hrPM() throws TimeOutOfBoundsException {
		int testHour = 10, testMinute = 15;
		boolean testPM = true;
		
		Time time = construct12hr(testHour, testMinute, testPM);
		assertEquals(testHour + 12, time.getHour());
		assertEquals(testMinute, time.getMinute());
	}
	
	private static Time construct24hr(int hour, int minute) throws TimeOutOfBoundsException {
		return new Time(hour, minute);
	}
	private static Time construct12hr(int hour, int minute, boolean pm) throws TimeOutOfBoundsException {
		return new Time(hour, minute, pm);
	}
}
