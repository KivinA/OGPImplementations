package chapter_7.course_implementation;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import chapter_7.course_implementation.filters.FoodAmountExtractorTest;
import chapter_7.course_implementation.ownings.*;
import chapter_7.course_implementation.persons.PersonTest;

@RunWith(Suite.class)
@Suite.SuiteClasses( { OwnableTest.class, DogTest.class,
	PaintingTest.class, CarTest.class, PersonTest.class,
	FoodAmountExtractorTest.class })
public class AllTests {
}