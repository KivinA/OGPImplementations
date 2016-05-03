package chapter_9.course_implementation;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import chapter_9.course_implementation.decks.*;
import chapter_9.course_implementation.cards.*;

@RunWith(Suite.class)
@Suite.SuiteClasses( { CardDeckTest.class, TargetDeckTest.class,
    FromDeckTest.class, HelpDeckTest.class, SourceDeckTest.class,
    CardTest.class, JokerCardTest.class, JackCardTest.class, NumberedCardTest.class,
    OddMatcherCardTest.class})
public class AllTests {
}