package chapter_9.course_implementation.cards;


import static org.junit.Assert.*;

import org.junit.*;


public class JokerCardTest {
    
    private static JokerCard theJokerCard;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        theJokerCard = new JokerCard();
    }
    
    @Test public void matchesOn_TrueCase() {
        Card otherCard = new NumberedCard(7);
        assertTrue(theJokerCard.matchesOn(otherCard));
    }
    
    @Test public void matchesOn_NonEffectiveCard() {
        assertFalse(theJokerCard.matchesOn(null));
    }
    
    @Test public void toString_SingleCase() {
        assertEquals("Joker", theJokerCard.toString());
    }

    @Test public void clone_SingleCase() {
    	JokerCard clone = theJokerCard.clone();
    	assertNotNull(clone);
    	assertNotSame(clone,theJokerCard);
    	assertSame(JokerCard.class,clone.getClass());
    }

}
