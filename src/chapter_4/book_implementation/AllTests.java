package chapter_4.book_implementation;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BankAccountTest.class, CurrencyTest.class, IllegalAmountExceptionTest.class,
		IllegalNumberExceptionTest.class, MoneyAmountTest.class })
public class AllTests {
	
	@Test
	public void overallTest()
	{
		assertTrue(true);
	}
}
