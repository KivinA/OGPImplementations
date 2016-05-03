package chapter_9.course_implementation.cards;

import static org.junit.Assert.*;

import org.junit.*;

public class NumberedCardTest {

    private static NumberedCard theCardWithNumber7;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        theCardWithNumber7 = new NumberedCard(7);
    }

    @Test
    public void isValidValue_LegalValue() {
        assertTrue(NumberedCard.isValidValue(7));
    }

    @Test
    public void isValidValue_NonPositiveValue() {
        assertFalse(NumberedCard.isValidValue(0));
    }

    @Test
    public void isValidValue_ValueAboveHighestValue() {
        assertFalse(NumberedCard.isValidValue(11));
    }

    @Test
    public void constructor_LegalCase() {
        NumberedCard theCard = new NumberedCard(7);
        assertEquals(7, theCard.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_IllegalCase() throws Exception {
        new NumberedCard(0);
    }

    @Test
    public void matchesOn_TrueCase() {
        assertTrue(theCardWithNumber7.matchesOn(new NumberedCard(theCardWithNumber7.getValue() + 1)));
    }

    @Test
    public void matchesOn_NonEffectiveCard() {
        assertFalse(theCardWithNumber7.matchesOn(null));
    }

    @Test
    public void matchesOn_NonMatchingNumberedCartd() {
        assertFalse(theCardWithNumber7.matchesOn(new NumberedCard(theCardWithNumber7.getValue() - 2)));
    }

    @Test
    public void matchesOn_OtherTypeOfCard() {
        assertFalse(theCardWithNumber7.matchesOn(new JackCard()));
    }

    @Test
    public void toString_SingleCase() {
        assertEquals("Card: 7", theCardWithNumber7.toString());
    }

    @Test public void clone_SingleCase() {
    	NumberedCard clone = theCardWithNumber7.clone();
    	assertNotNull(clone);
    	assertNotSame(clone,theCardWithNumber7);
    	assertSame(NumberedCard.class,clone.getClass());
    	assertEquals(theCardWithNumber7.getValue(),clone.getValue());
    }

}
