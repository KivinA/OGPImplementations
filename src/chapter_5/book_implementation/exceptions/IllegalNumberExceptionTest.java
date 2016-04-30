package chapter_5.book_implementation.exceptions;

import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.BeforeClass;
import org.junit.Test;
import chapter_5.book_implementation.banking.*;
import chapter_5.book_implementation.state.Person;

public class IllegalNumberExceptionTest {
	
	private static Person someAdult;
	
	@BeforeClass
	public static void setUpImmutableFixture()
	{
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(1980, Calendar.MARCH, 4);
		someAdult = new Person(cal.getTime());
	}
	
	@Test
	public void constructor_SingleCase()
	{
		BankAccount theAccount = new BankAccount(2222222, someAdult);
		int number = 312;
		IllegalNumberException exception = new IllegalNumberException(number, theAccount);
		assertEquals(312, exception.getNumber());
		assertSame(theAccount, exception.getAccount());
		assertNull(exception.getMessage());
		assertNull(exception.getCause());
		theAccount.terminate();
	}
}
