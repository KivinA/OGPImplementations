package chapter_9.course_implementation.cards;


import static org.junit.Assert.*;

import org.junit.*;


public class JackCardTest {
    
    private static JackCard theJackCard;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        theJackCard = new JackCard();
    }

    @Test public void matchesOn_NumberedCard10() {
        assertTrue(theJackCard.matchesOn(new NumberedCard(10)));
    }
    
    @Test public void matchesOn_NonEffectiveCard() {
        assertFalse(theJackCard.matchesOn(null));
    }
    
    @Test public void matchesOn_NumberedCardNon10() {
        assertFalse(theJackCard.matchesOn(new NumberedCard(5)));
    }
    
    @Test public void matchesOn_JackCard() {
    	assertFalse(theJackCard.matchesOn(new JackCard()));
    }
    
    @Test public void matchesOn_CardOfOtherType() {
    	assertTrue(theJackCard.matchesOn(new JokerCard()));
    }

    @Test public void toString_SingleCase() {
        assertEquals("Jack", theJackCard.toString());
    }
    
    @Test public void clone_SingleCase() {
    	JackCard clone = theJackCard.clone();
    	assertNotNull(clone);
    	assertNotSame(clone,theJackCard);
    	assertSame(JackCard.class,clone.getClass());
    }

}
