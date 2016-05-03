package chapter_9.course_implementation.decks;

import static org.junit.Assert.*;

import org.junit.*;

import chapter_9.course_implementation.cards.*;

public class FromDeckTest {

	private FromDeck theFromDeck;

	private TargetDeck theTargetDeck;

	@Before
	public void setUp() throws Exception {
		theFromDeck = new SourceDeck(new NumberedCard(6), new JackCard(),
				new NumberedCard(4), new NumberedCard(7), new NumberedCard(2));
		theTargetDeck = new TargetDeck(46, new NumberedCard(7));
	}

	@Test
	public void getMinimumNbCards_SingleCase() {
		assertEquals(0, theFromDeck.getMinimumNbCards());
	}

	@Test
	public void canHaveAsCapacity_IllegalMinimum() {
		assertFalse(theFromDeck.canHaveAsCapacity(2, 22));
	}

	@Test
	public void canHaveAsCapacity_IllegalMaximum() {
		assertFalse(theFromDeck.canHaveAsCapacity(0, -1));
	}

	@Test
	public void topCardMatchesOn_EmptyFromDeck() {
		theFromDeck.clear();
		assertFalse(theFromDeck.topCardMatchesOn(theTargetDeck));
	}

	@Test
	public void topCardMatchesOn_NonEffectiveTargetDeck() {
		assertFalse(theFromDeck.topCardMatchesOn(null));
	}

	@Test
	public void topCardMatchesOn_FullTargetDeck() {
		theTargetDeck = new TargetDeck(46, new NumberedCard(10));
		for (int i = 2; i <= theTargetDeck.getMaximumNbCards(); i++)
			theTargetDeck.pushCard(new NumberedCard(7));
		assertFalse(theFromDeck.topCardMatchesOn(theTargetDeck));
	}

	@Test
	public void moveTop_LegalCase() {
		Card topCard = theFromDeck.getCardAtTop();
		int nbFromCards = theFromDeck.getNbCards();
		int nbTargetCards = theTargetDeck.getNbCards();
		theFromDeck.moveTop(theTargetDeck);
		assertEquals(nbFromCards - 1, theFromDeck.getNbCards());
		assertEquals(nbTargetCards + 1, theTargetDeck.getNbCards());
		assertSame(topCard, theTargetDeck.getCardAtTop());
	}

	@Test(expected = IllegalStateException.class)
	public void moveTop_IllegalCase() throws Exception {
		theFromDeck.moveTop(null);
	}

	@Test
	public void moveTopAlt_LegalCase() {
		Card topCard = theFromDeck.getCardAtTop();
		int nbFromCards = theFromDeck.getNbCards();
		int nbTargetCards = theTargetDeck.getNbCards();
		theFromDeck.moveTop(theTargetDeck);
		assertEquals(nbFromCards - 1, theFromDeck.getNbCards());
		assertEquals(nbTargetCards + 1, theTargetDeck.getNbCards());
		assertSame(topCard, theTargetDeck.getCardAtTop());
	}

	@Test(expected = IllegalStateException.class)
	public void moveTopAlt_EmptyFromDeck() throws Exception {
		theFromDeck.clear();
		theFromDeck.moveTop(theTargetDeck);
	}

	@Test(expected = IllegalStateException.class)
	public void moveTopAlt_NonEffectiveTarget() throws Exception {
		theFromDeck.moveTop(null);
	}

	@Test(expected = IllegalStateException.class)
	public void moveTopAlt_FullTargetDeck() throws Exception {
		for (int i = 2; i <= theTargetDeck.getMaximumNbCards(); i++)
			theTargetDeck.pushCard(new NumberedCard(7));
		theFromDeck.moveTop(theTargetDeck);
	}

	@Test
	public void moveTopAlt_NonMatchingCard() throws Exception {
		try {
			Card topCard = theFromDeck.getCardAtTop();
			int nbFromCards = theFromDeck.getNbCards();
			int nbTargetCards = theTargetDeck.getNbCards();
			theFromDeck.moveTop(theTargetDeck);
			assertEquals(nbFromCards - 1, theFromDeck.getNbCards());
			assertEquals(nbTargetCards + 1, theTargetDeck.getNbCards());
			assertSame(topCard, theTargetDeck.getCardAtTop());
		} catch (IllegalStateException exc) {
			assertFalse(theFromDeck.getCardAtTop().matchesOn(
					theTargetDeck.getCardAtTop()));
		}
	}

}
