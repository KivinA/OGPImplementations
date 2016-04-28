package chapter_4.book_implementation;

import static org.junit.Assert.*;
import org.junit.Test;

public class IllegalNumberExceptionTest {
	@Test
	public void constructor_SingleCase()
	{
		BankAccount theAccount = new BankAccount(2222222);
		int number = 312;
		IllegalNumberException exception = new IllegalNumberException(number, theAccount);
		assertEquals(312, exception.getNumber());
		assertSame(theAccount, exception.getAccount());
		assertNull(exception.getMessage());
		assertNull(exception.getCause());
		theAccount.terminate();
	}
}
