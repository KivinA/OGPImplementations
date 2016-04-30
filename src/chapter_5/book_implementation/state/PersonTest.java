package chapter_5.book_implementation.state;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.*;

public class PersonTest {
	
	private static Date date80s, date00s;
	
	private static Calendar theCalendar;
	
	private static Person someAdult, someNonAdult;
	
	@BeforeClass
	public static void setUpImmutableFixture() throws Exception
	{
		theCalendar = new GregorianCalendar();
		theCalendar.set(1980, Calendar.MARCH, 4);
		date80s = theCalendar.getTime();
		theCalendar.set(2005, Calendar.SEPTEMBER, 11);
		date00s = theCalendar.getTime();
		someAdult = new Person(date80s);
		someNonAdult = new Person(date00s);
	}
	
	@Test
	public void extendedConstructor_LegalCase()
	{
		Person thePerson = new Person(date80s);
		assertEquals(date80s, thePerson.getBirthDate());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void extendedConstructor_IllegalBirthDate() throws Exception
	{
		new Person(null);
	}
	
	@Test
	public void isAdult_TrueCase()
	{
		assertTrue(someAdult.isAdult());
	}
	
	@Test
	public void isAdult_FalseCase()
	{
		assertFalse(someNonAdult.isAdult());
	}
	
	@Test
	public void isValidBirthDate_LegalCase()
	{
		assertTrue(Person.isValidBirthDate(date80s));
	}
	
	@Test
	public void isValidBirthDate_NonEffectiveDate()
	{
		assertFalse(Person.isValidBirthDate(null));
	}
	
	@Test
	public void isValidBirthDate_FutureDate()
	{
		theCalendar.set(2025, Calendar.JANUARY, 12);
		assertFalse(Person.isValidBirthDate(theCalendar.getTime()));
	}
}
