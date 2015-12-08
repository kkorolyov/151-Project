package org.cs151.callrejector.schedule;

import static org.junit.Assert.*;

import org.cs151.callrejector.schedule.exceptions.HourOutOfBoundsException;
import org.cs151.callrejector.schedule.exceptions.MinuteOutOfBoundsException;
import org.junit.Test;

/**
 * Tests {@link HourTime}.
 * @author Kirill Korolyov
 */
public class HourTimeTest {

	@Test
	public void testEquals() throws HourOutOfBoundsException, MinuteOutOfBoundsException {
		HourTime testTime1, testTime2, testTime3, testTimeUnequal, testTimeOtherType;
		for (int testHour = HourTime.HOUR_BOUND_START; testHour <= HourTime.HOUR_BOUND_END; testHour++) {	// Test for every hour
			testTime1 = new HourTime(testHour);
			testTime2 = new HourTime(testHour);
			testTime3 = new HourTime(testHour);
			testTimeUnequal = new HourTime((testHour < HourTime.HOUR_BOUND_END / 2) ? (testHour + 1) : (testHour - 1));	// Unequal time either earlier or later as appropriate to avoid out of bounds 
			testTimeOtherType = new HourMinuteTime((testHour < HourTime.HOUR_BOUND_END / 2) ? (testHour + 1) : (testHour - 1), 15);
			
			assertTrue(!testTime1.equals(null));	// Test unequal null
			assertTrue(!testTime1.equals(testTimeUnequal));	// Test unequal properties
			assertTrue(!testTime1.equals(testTimeOtherType));	// Test strict type checking
			
			testTime1.equals(testTime1);	// Reflexive
			
			assertTrue(testTime1.equals(testTime2) && testTime2.equals(testTime1));	// Symmetric
			
			assertTrue(testTime1.equals(testTime2) && testTime2.equals(testTime3) && testTime1.equals(testTime3));	// Transitive
		}
	}

	@Test
	public void testCompareTo() throws HourOutOfBoundsException {
		for (int testHour = HourTime.HOUR_BOUND_START; testHour < HourTime.HOUR_BOUND_END; testHour++) {
			HourTime early = new HourTime(testHour), later = new HourTime(testHour + 1);
			assertTrue(early.compareTo(later) < 0);
			assertTrue(later.compareTo(early) > 0);
			assertTrue(early.compareTo(early) == 0);
			assertTrue(later.compareTo(later) == 0);
		}
	}
	
	@Test
	public void testToString() throws HourOutOfBoundsException {
		for (int testHour = HourTime.HOUR_BOUND_START; testHour <= HourTime.HOUR_BOUND_END; testHour++) {
			String buffer = (testHour < 10) ? "0" : "";
			String testString = buffer + String.valueOf(testHour) + ":00";
		
			assertEquals(testString, new HourTime(testHour).toString());
		}
	}
}
