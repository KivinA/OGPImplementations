package chapter_9.course_implementation.decks;

import static org.junit.Assert.*;

import org.junit.*;

import chapter_9.course_implementation.cards.*;

public class SourceDeckTest {
    
    private SourceDeck sourceDeck;
    private TargetDeck targetDeck;

    @Before
    public void setUp() throws Exception {
        sourceDeck = new SourceDeck(new JokerCard(), new JackCard(), new NumberedCard(4), new NumberedCard(6), new NumberedCard(6));
        targetDeck = new TargetDeck(46,new NumberedCard(7));
    }

    @Test
    public void constructor_LegalCase() {
        Card cards[] = { new JokerCard(), new NumberedCard(7), new JackCard(), new NumberedCard(10), new JokerCard() };
        SourceDeck theDeck = new SourceDeck(cards);
        assertEquals(0, theDeck.getMinimumNbCards());
        assertEquals(5, theDeck.getMaximumNbCards());
        assertEquals(5, theDeck.getNbCards());
        assertSame(cards[0], theDeck.getCardAt(1));
        assertSame(cards[1], theDeck.getCardAt(2));
        assertSame(cards[2], theDeck.getCardAt(3));
        assertSame(cards[3], theDeck.getCardAt(4));
        assertSame(cards[4], theDeck.getCardAt(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_IllegalCard() throws Exception {
        new SourceDeck(new JokerCard(), new NumberedCard(7), new JackCard(), new JokerCard(), null);
    }

    @Test
    public void canHaveAsCapacity_TrueCase() {
        assertTrue(sourceDeck.canHaveAsCapacity(0, 22));
    }

    @Test
    public void canHaveAsCapacity_IllegalMinimum() {
        assertFalse(sourceDeck.canHaveAsCapacity(2, 22));
    }

    @Test
    public void canHaveAsCapacity_IllegalMaximum() {
        assertFalse(sourceDeck.canHaveAsCapacity(0, 4));
    }
    
    @Test public void topCardMatchesOn_TrueCase() {
        assertTrue(sourceDeck.topCardMatchesOn(targetDeck));
    }
    
    @Test public void topCardMatchesOn_EmptySourceDeck() {
        sourceDeck.clear();
        assertFalse(sourceDeck.topCardMatchesOn(targetDeck));
    }
    
    @Test public void topCardMatchesOn_NonEffectiveTargetDeck() {
        assertFalse(sourceDeck.topCardMatchesOn(null));
    }   

    @Test public void topCardMatchesOn_FullTargetDeck() {
        targetDeck = new TargetDeck(46,new NumberedCard(7));
        for (int i=2; i<= targetDeck.getMaximumNbCards(); i++)
        	targetDeck.pushCard(new NumberedCard(7));
        assertFalse(sourceDeck.topCardMatchesOn(targetDeck));
    }   
    
    @Test public void topCardMatchesOn_NonMatchingCard() {
        sourceDeck.popCard();
        sourceDeck.pushCard(new NumberedCard(5));
        assertFalse(sourceDeck.topCardMatchesOn(targetDeck));
    }   

	@Test
	public void moveTopAlt_LegalCase() {
		Card topCard = sourceDeck.getCardAtTop();
		int nbFromCards = sourceDeck.getNbCards();
		int nbTargetCards = targetDeck.getNbCards();
		sourceDeck.moveTop(targetDeck);
		assertEquals(nbFromCards - 1, sourceDeck.getNbCards());
		assertEquals(nbTargetCards + 1, targetDeck.getNbCards());
		assertSame(topCard, targetDeck.getCardAtTop());
	}

	@Test(expected = IllegalStateException.class)
	public void moveTopAlt_EmptyFromDeck() throws Exception {
		sourceDeck.clear();
		sourceDeck.moveTop(targetDeck);
	}

	@Test(expected = IllegalStateException.class)
	public void moveTopAlt_NonEffectiveTarget() throws Exception {
		sourceDeck.moveTop(null);
	}

	@Test(expected = IllegalStateException.class)
	public void moveTopAlt_FullTargetDeck() throws Exception {
		for (int i = 2; i <= targetDeck.getMaximumNbCards(); i++)
			targetDeck.pushCard(new NumberedCard(7));
		sourceDeck.moveTop(targetDeck);
	}

	@Test(expected = IllegalStateException.class)
	public void moveTopAlt_NonMatchingCard() throws Exception {
		sourceDeck.popCard();
		sourceDeck.pushCard(new NumberedCard(4));
		sourceDeck.moveTop(targetDeck);
	}

}
