package chapter_6.book_implementation.state;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import be.kuleuven.cs.som.annotate.*;

public class Person {
	
	/**
	 * Initialize thgis new Person with given birth date.
	 * 
	 * @param 	birthDate
	 * 			The birth date for this new Person.
	 * @post	The birth date of this new person is equal to the given birth date.
	 * 			| new.getBirthDate().equals(birthDate)
	 * @throws 	IllegalArgumentException
	 * 			The given birth date isn't a valid birth date for any Person.
	 * 			| (!isValidBirthDate(birthDate))
	 */
	public Person(Date birthDate) throws IllegalArgumentException
	{
		if (!isValidBirthDate(birthDate))
			throw new IllegalArgumentException();
		this.birthDate = birthDate;
	}
	
	/**
	 * Terminate this Person.
	 * 
	 * @post	This Person is terminated.
	 * 			| new.isTerminated()
	 */
	public void terminate()
	{
		this.isTerminated = true;
	}
	
	/**
	 * Check whether this Person is terminated.
	 */
	public boolean isTerminated()
	{
		return this.isTerminated;
	}
	
	/**
	 * Variable referencing whether or not this Person is terminated.
	 */
	private boolean isTerminated = false;
	
	/**
	 * Return the birth date of this Person.
	 */
	@Basic @Immutable
	public Date getBirthDate()
	{
		return (Date)(birthDate.clone());
	}
	
	/**
	 * Check whether this Person is an adult.
	 * 
	 * @return	True if and only if this Person is at least 18 years old.
	 * 			| result == getBirthDate().before(new Date(System.currentTimeMillis()-18*365*24*60*60*1000))
	 */
	public boolean isAdult()
	{
		theCalendar.setTimeInMillis(System.currentTimeMillis());
		theCalendar.add(Calendar.YEAR, -18);
		return getBirthDate().before(theCalendar.getTime());
	}
	
	/**
	 * Check whether the given date is a valid birth date for a Person.
	 * 
	 * @param 	date
	 * 			The date to check.
	 * @return	True if and only if the given date is effective and not in the future.
	 * 			| result == (date != null) && (!date.after(new Date()))
	 */
	public static boolean isValidBirthDate(Date date)
	{
		theDate.setTime(System.currentTimeMillis());
		return (date != null) && (!date.after(theDate));
	}
	
	/**
	 * Variable referencing the birth date of this Person.
	 */
	private final Date birthDate;
	
	/**
	 * Variable referencing some effective Gregorian calendar to work with.
	 */
	private static final GregorianCalendar theCalendar = new GregorianCalendar();
	
	/**
	 * Variable referencing some effective date to work with.
	 */
	private static final Date theDate = new Date();
}
