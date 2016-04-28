package chapter_4.course_implementation;
import static org.junit.Assert.*;

import org.junit.Test;

public class GenderTest {

	@Test
	public void getSymbol_MALE() {
		assertEquals('\u2642', Gender.MALE.getSymbol());
	}

	@Test
	public void getSymbol_FEMALE() {
		assertEquals('\u2640', Gender.FEMALE.getSymbol());
	}

	// The method toString for enumerations is defined to
	// return the name of the element against which the
	// method is defined.
	@Test
	public void toString_MALE() {
		assertEquals("MALE", Gender.MALE.toString());
	}

	@Test
	public void toStrin_FEMALE() {
		assertEquals("FEMALE", Gender.FEMALE.toString());
	}
}
