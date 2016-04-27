package chapter_3.book_implementation;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * A test suite class to test all test suites of this package.
 * 
 * @author 	Kevin Algoet & Eric Steegmans
 * @version	3.0
 * 
 * @note	Based on the code found in the book Object Oriented Programming with Java by Eric Steegmans.
 */
@RunWith (Suite.class)
@Suite.SuiteClasses({BankAccountTest.class, IllegalAmountExceptionTest.class, IllegalNumberExceptionTest.class})
public class AllTests {
	@Test
	public void overallTest()
	{
		assertTrue(true);
	}
}
