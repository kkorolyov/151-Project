package org.cs151.callrejector.schedule;

/**
 * Represents a single point in time.
 * @author Kirill Korolyov
 */
public abstract class AbstractTime implements Comparable<AbstractTime> {
	/**
	 * Compares the value of this {@code AbstractTime} to another {@code AbstractTime}
	 * @param otherTime other {@code AbstractTime} to compare to
	 * @return -1 if this {@code AbstractTime} is less than the other, 1 if greater than, 0 if equal to
	 */
	@Override
	public abstract int compareTo(AbstractTime otherTime);
	
	@Override
	public final boolean equals(Object anObject) {
		if  (anObject == this)	// No need to test further
			return true;
		else if ((anObject instanceof AbstractTime) && equals((AbstractTime) anObject))	// Test for type and equal properties
			return true;
		else
			return false;
	}
	protected abstract boolean equals(AbstractTime aTime);	// Overridden by subclasses for equality appropriate tests
}
