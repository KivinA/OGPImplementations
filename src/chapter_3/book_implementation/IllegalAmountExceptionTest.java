package chapter_3.book_implementation;

import static org.junit.Assert.*;
import org.junit.*;

public class IllegalAmountExceptionTest {
	@Test
	public void constructor_SingleCase()
	{
		BankAccount theAccount = new BankAccount(222);
		IllegalAmountException exception = new IllegalAmountException(1000L, theAccount);
		assertEquals(1000, exception.getAmount());
		assertSame(theAccount, exception.getAccount());
		assertNull(exception.getMessage());
		assertNull(exception.getCause());
	}
}
