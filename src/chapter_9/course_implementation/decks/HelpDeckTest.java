package chapter_9.course_implementation.decks;

import static org.junit.Assert.*;

import org.junit.*;

import chapter_9.course_implementation.cards.*;

public class HelpDeckTest {
    private HelpDeck helpDeck;
    private TargetDeck targetDeck;

    @Before
    public void setUpBeforeClass() throws Exception {
        helpDeck = new HelpDeck(new JokerCard(), new NumberedCard(4), new NumberedCard(7),
        		                new JackCard(), new NumberedCard(1), new JokerCard(),
        		                new NumberedCard(2), new NumberedCard(10), new NumberedCard(8),
        		                new NumberedCard(6));
        targetDeck = new TargetDeck(46,new NumberedCard(7));
    }

    @Test
    public void constructor_LegalCase() {
        Card cards[] = { new JokerCard(), new NumberedCard(4), new NumberedCard(7),
                new JackCard(), new NumberedCard(1), new JokerCard(),
                new NumberedCard(2), new JokerCard(), new NumberedCard(7), new JackCard() };
        HelpDeck theDeck = new HelpDeck(cards);
        assertEquals(0, theDeck.getMinimumNbCards());
        assertEquals(10, theDeck.getMaximumNbCards());
        assertEquals(10, theDeck.getNbCards());
        assertSame(cards[0], theDeck.getCardAt(1));
        assertSame(cards[1], theDeck.getCardAt(2));
        assertSame(cards[2], theDeck.getCardAt(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_IllegalCard() throws Exception {
        new HelpDeck(new JokerCard(), null);
    }

    @Test
    public void canHaveAsCapacity_TrueCase() {
        assertTrue(helpDeck.canHaveAsCapacity(0, 22));
    }

    @Test
    public void canHaveAsCapacity_IllegalMinimum() {
        assertFalse(helpDeck.canHaveAsCapacity(2, 22));
    }

    @Test
    public void canHaveAsCapacity_IllegalMaximum() {
        assertFalse(helpDeck.canHaveAsCapacity(0, -1));
    }
    
    @Test public void topCardMatchesOn_TrueCase() {
        assertTrue(helpDeck.topCardMatchesOn(targetDeck));
    }
    
    @Test public void topCardMatchesOn_EmptyhelpDeck() {
        helpDeck.clear();
        assertFalse(helpDeck.topCardMatchesOn(targetDeck));
    }
    
    @Test public void topCardMatchesOn_NonEffectiveTargetDeck() {
        assertFalse(helpDeck.topCardMatchesOn(null));
    }   

    @Test public void topCardMatchesOn_FullTargetDeck() {
        targetDeck = new TargetDeck(46,new NumberedCard(7));
        for (int i=2; i<= targetDeck.getMaximumNbCards(); i++)
        	targetDeck.pushCard(new NumberedCard(1+i%10));
        assertFalse(helpDeck.topCardMatchesOn(targetDeck));
    }   


	@Test
	public void moveTopAlt_LegalCase() {
		Card topCard = helpDeck.getCardAtTop();
		int nbFromCards = helpDeck.getNbCards();
		int nbTargetCards = targetDeck.getNbCards();
		helpDeck.moveTop(targetDeck);
		assertEquals(nbFromCards - 1, helpDeck.getNbCards());
		assertEquals(nbTargetCards + 1, targetDeck.getNbCards());
		assertSame(topCard, targetDeck.getCardAtTop());
	}

	@Test(expected = IllegalStateException.class)
	public void moveTopAlt_EmptyFromDeck() throws Exception {
		helpDeck.clear();
		helpDeck.moveTop(targetDeck);
	}

	@Test(expected = IllegalStateException.class)
	public void moveTopAlt_NonEffectiveTarget() throws Exception {
		helpDeck.moveTop(null);
	}

	@Test(expected = IllegalStateException.class)
	public void moveTopAlt_FullTargetDeck() throws Exception {
		for (int i = 2; i <= targetDeck.getMaximumNbCards(); i++)
			targetDeck.pushCard(new NumberedCard(7));
		helpDeck.moveTop(targetDeck);
	}

	@Test
	public void moveTopAlt_NonMatchingCard() throws Exception {
		helpDeck.popCard();
		helpDeck.pushCard(new NumberedCard(4));
		Card topCard = helpDeck.getCardAtTop();
		int nbFromCards = helpDeck.getNbCards();
		int nbTargetCards = targetDeck.getNbCards();
		helpDeck.moveTop(targetDeck);
		assertEquals(nbFromCards - 1, helpDeck.getNbCards());
		assertEquals(nbTargetCards + 1, targetDeck.getNbCards());
		assertSame(topCard, targetDeck.getCardAtTop());
	}

}
