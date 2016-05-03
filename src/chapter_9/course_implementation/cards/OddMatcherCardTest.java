package chapter_9.course_implementation.cards;

import static org.junit.Assert.*;

import org.junit.*;

public class OddMatcherCardTest {

    private static OddMatcherCard theCard;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        theCard = new OddMatcherCard();
    }

    @Test
    public void matchesOn_TrueCase() {
        assertTrue(theCard.matchesOn(new NumberedCard(7)));
    }

    @Test
    public void matchesOn_NonEffectiveCard() {
        assertFalse(theCard.matchesOn(null));
    }

    @Test
    public void matchesOn_NonMatchingNumberedCard() {
        assertFalse(theCard.matchesOn(new NumberedCard(6)));
    }

    @Test
    public void matchesOn_JackCard() {
        assertFalse(theCard.matchesOn(new JackCard()));
    }

    @Test
    public void matchesOn_OtherTypeOfCard() {
        assertTrue(theCard.matchesOn(new JokerCard()));
    }

    @Test
    public void toString_SingleCase() {
        assertEquals("Odd Matcher", theCard.toString());
    }

    @Test public void clone_SingleCase() {
    	OddMatcherCard clone = theCard.clone();
    	assertNotNull(clone);
    	assertNotSame(clone,theCard);
    	assertSame(OddMatcherCard.class,clone.getClass());
    }

}
