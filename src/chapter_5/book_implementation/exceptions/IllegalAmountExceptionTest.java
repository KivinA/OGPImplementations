package chapter_5.book_implementation.exceptions;

import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.*;
import chapter_5.book_implementation.banking.*;
import chapter_5.book_implementation.money.*;
import chapter_5.book_implementation.state.Person;

public class IllegalAmountExceptionTest {
	
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
		IllegalAmountException exception = new IllegalAmountException(MoneyAmount.EUR_1, theAccount);
		assertEquals(MoneyAmount.EUR_1, exception.getAmount());
		assertSame(theAccount, exception.getAccount());
		assertNull(exception.getMessage());
		assertNull(exception.getCause());
		theAccount.terminate();
	}
}
