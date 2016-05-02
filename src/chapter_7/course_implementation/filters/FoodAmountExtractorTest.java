package chapter_7.course_implementation.filters;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.BeforeClass;
import org.junit.Test;

import chapter_7.course_implementation.ownings.*;
import chapter_7.course_implementation.persons.Person;

public class FoodAmountExtractorTest {

	private static FoodAmountExtractor theExtractor;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		theExtractor = new FoodAmountExtractor();
	}

	@Test
	public void getValueFor_Dog() throws Exception {
		Dog theDog = new Dog(new Person(), BigInteger.ONE, "Bobby", 300);
		assertEquals(BigInteger.valueOf(300), theExtractor.getValueFor(theDog));
	}

	@Test
	public void getValueFor_NonDog() throws Exception {
		Ownable nonDog = new Car();
		assertEquals(BigInteger.ZERO, theExtractor.getValueFor(nonDog));
	}

	@Test
	public void getValueFor_NonEffectiveObject() {
		assertEquals(BigInteger.ZERO, theExtractor.getValueFor(null));
	}

}
