package chapter_3.course_implementation;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ExtMathTest.class, RationalTest.class })
public class AllTests {
	@Test
	public void overallTest()
	{
		assertTrue(true);
	}
}
