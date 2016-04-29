package chapter_5.book_implementation.exceptions;

import static org.junit.Assert.*;
import org.junit.*;
import chapter_5.book_implementation.banking.*;
import chapter_5.book_implementation.money.*;

public class IllegalAmountExceptionTest {
	@Test
	public void constructor_SingleCase()
	{
		BankAccount theAccount = new BankAccount(222);
		IllegalAmountException exception = new IllegalAmountException(MoneyAmount.EUR_1, theAccount);
		assertEquals(MoneyAmount.EUR_1, exception.getAmount());
		assertSame(theAccount, exception.getAccount());
		assertNull(exception.getMessage());
		assertNull(exception.getCause());
		theAccount.terminate();
	}
}
