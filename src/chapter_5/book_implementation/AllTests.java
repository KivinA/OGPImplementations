package chapter_5.book_implementation;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({chapter_5.book_implementation.exceptions.IllegalAccountExceptionTest.class, 
	chapter_5.book_implementation.exceptions.IllegalAmountExceptionTest.class, 
	chapter_5.book_implementation.exceptions.IllegalHolderExceptionTest.class,
	chapter_5.book_implementation.exceptions.IllegalNumberExceptionTest.class,
	chapter_5.book_implementation.exceptions.IllegalPinCodeExceptionTest.class,
	chapter_5.book_implementation.banking.BankAccountTest.class,
	chapter_5.book_implementation.banking.BankCardTest.class,
	chapter_5.book_implementation.state.PersonTest.class,
	chapter_5.book_implementation.money.CurrencyTest.class,
	chapter_5.book_implementation.money.MoneyAmountTest.class})
public class AllTests {
	
	@Test
	public void overallTest()
	{
		assertTrue(true);
	}
}
