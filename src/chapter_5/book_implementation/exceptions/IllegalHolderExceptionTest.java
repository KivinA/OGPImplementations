package chapter_5.book_implementation.exceptions;

import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.*;
import chapter_5.book_implementation.banking.BankAccount;
import chapter_5.book_implementation.state.Person;

public class IllegalHolderExceptionTest {
	
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
		BankAccount theAccount = new BankAccount(222, someAdult);
		IllegalHolderException exception = new IllegalHolderException(someAdult, theAccount);
		assertSame(theAccount, exception.getAccount());
		assertSame(someAdult, exception.getHolder());
		assertNull(exception.getCause());
		assertNull(exception.getMessage());
		theAccount.terminate();
	}
}
