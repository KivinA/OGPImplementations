package chapter_5.book_implementation.exceptions;

import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.*;

import chapter_5.book_implementation.banking.BankAccount;
import chapter_5.book_implementation.banking.BankCard;
import chapter_5.book_implementation.state.Person;

public class IllegalPinCodeExceptionTest {
	
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
		int code = 1234;
		BankCard theCard = new BankCard(theAccount, code);
		IllegalPinCodeException exception = new IllegalPinCodeException(code, theCard);
		assertSame(theCard, exception.getBankCard());
		assertEquals(code, exception.getPinCode());
		assertNull(exception.getMessage());
		assertNull(exception.getCause());
		theAccount.terminate();
	}
}
