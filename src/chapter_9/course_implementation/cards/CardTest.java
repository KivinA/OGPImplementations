package chapter_9.course_implementation.cards;


import static org.junit.Assert.*;

import org.junit.*;


public class CardTest {
    
    private static Card theCard;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        theCard = new NumberedCard(6);
    }

    @Before
    public void setUp() throws Exception {
    }
    
    @Test public void matchesOn_NonEffectiveCard() {
        Card otherCard = null;
        assertFalse(theCard.matchesOn(otherCard));
    }
  
    @Test public void clone_SingleCase() {
    	Card clone = theCard.clone();
    	assertNotNull(clone);
    	assertNotSame(clone,theCard);
    	assertSame(clone.getClass(),theCard.getClass());
    }

}
