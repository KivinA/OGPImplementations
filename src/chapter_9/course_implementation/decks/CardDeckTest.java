package chapter_9.course_implementation.decks;

import static org.junit.Assert.*;

import org.junit.*;

import chapter_9.course_implementation.cards.*;

public class CardDeckTest {

    private CardDeck theDeck;

    private static CardDeck emptyDeck, minimumDeck, fullDeck, mediumDeck;
    
    private static Card visibleCard = new NumberedCard(6);

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        emptyDeck = new SourceDeck(new JokerCard(), new JackCard(), new NumberedCard(4), new NumberedCard(6), new JokerCard());
        while (! emptyDeck.hasMinimumSize())
        	emptyDeck.popCard();
        minimumDeck = new TargetDeck(48, new JokerCard());
        fullDeck = new SourceDeck(new JokerCard(), new JackCard(), new NumberedCard(6), visibleCard, new JokerCard());
        mediumDeck = new TargetDeck(46, new JokerCard());
        mediumDeck.pushCard(visibleCard);
        for (int i=1; i< mediumDeck.getMaximumNbCards()/2; i++)
        	mediumDeck.pushCard(new JackCard());
    }

    @Before
    public void setUp() throws Exception {
        theDeck = new TargetDeck(46, new JokerCard());
        theDeck.pushCard(new JackCard());
    }

    @Test
    public void canHaveAsCapacity_NegativeMinimum() {
        assertFalse(theDeck.canHaveAsCapacity(-1, 5));
    }

    @Test
    public void canHaveAsCapacity_NonPositiveMaximum() {
        assertFalse(theDeck.canHaveAsCapacity(0,0));
    }

    @Test
    public void canHaveAsCapacity_MinimumExceedsMaximum() {
        assertFalse(theDeck.canHaveAsCapacity(4, 3));
    }

    @Test
    public void setMaximumNbCards_SingleCase() {
        theDeck.setMaximumNbCards(50);
        assertEquals(50, theDeck.getMaximumNbCards());
    }

    @Test
    public void hasMaximumSize_TrueCase() {
        assertTrue(fullDeck.hasMaximumSize());
    }

    @Test
    public void hasMaximumSize_FalseCase() {
        assertFalse(mediumDeck.hasMaximumSize());
    }

    @Test
    public void hasMinimumSize_TrueCase() {
        assertTrue(minimumDeck.hasMinimumSize());
    }

    @Test
    public void hasMinimumSize_FalseCase() {
        assertFalse(mediumDeck.hasMinimumSize());
    }
    
    @Test
    public void getCardAt_LegalCase() {
    	assertEquals(visibleCard,fullDeck.getCardAt(4));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getCardAt_IndexTooLow() throws Exception {
        mediumDeck.getCardAt(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getCardAt_IndexTooHigh() throws Exception {
        mediumDeck.getCardAt(mediumDeck.getNbCards() + 1);
    }
    
    @Test
    public void getNbCards_SingleCase() {
    	assertEquals(5, fullDeck.getNbCards());
    }

    @Test
    public void canHaveAsCardAt_EffectiveCard() {
        assertTrue(mediumDeck.canHaveAsCard(new NumberedCard(8)));
    }

    @Test
    public void canHaveAsCardAt_NonEffectiveCard() {
        assertFalse(mediumDeck.canHaveAsCard(null));
    }

    @Test
    public void hasProperCards_TrueCase() {
    	assertTrue(mediumDeck.hasProperCards());
    }

    @Test
    public void getCardAtTop_LegalCase() {
        Card topCard = new NumberedCard(7);
        theDeck.pushCard(topCard);
        assertSame(topCard, theDeck.getCardAtTop());
    }

    @Test(expected = IllegalStateException.class)
    public void getCardAtTop_IndexTooHigh() throws Exception {
        emptyDeck.getCardAtTop();
    }

    @Test
    public void hasAsCard_TrueCase() {
        Card theCard = mediumDeck.getCardAt(2);
        assertTrue(mediumDeck.hasAsCard(theCard));
    }

    @Test
    public void hasAsCard_FalseCase() {
        assertFalse(mediumDeck.hasAsCard(new NumberedCard(8)));
    }

    @Test
    public void hasAsCard_NonEffectiveCard() {
        assertFalse(mediumDeck.hasAsCard(null));
    }

    @Test public void getAllCards_SingleCase() {
        Card[] theCards = mediumDeck.getAllCards();
        assertNotNull(theCards);
        assertEquals(mediumDeck.getNbCards(),theCards.length);
        for (int i=1; i<=mediumDeck.getNbCards(); i++)
            assertSame(mediumDeck.getCardAt(i),theCards[i-1]);
    }

    @Test public void pushCard_SingleCase() {
        Card newCard = new NumberedCard(7);
        int oldNbCards = theDeck.getNbCards();
        assertTrue(theDeck.getNbCards()<theDeck.getMaximumNbCards());
        theDeck.pushCard(newCard);
        assertEquals(oldNbCards+1,theDeck.getNbCards());
        assertSame(theDeck.getCardAtTop(),newCard);
    }

    @Test public void popCard_SingleCase() {
        int oldNbCards = theDeck.getNbCards();
        assertTrue(theDeck.getNbCards()>theDeck.getMinimumNbCards());
        theDeck.popCard();
        assertEquals(oldNbCards-1,theDeck.getNbCards());
    }

    @Test public void clear_SingleCase() {
        CardDeck theDeck = new SourceDeck(new JokerCard(),new JackCard(),new NumberedCard(4), new JokerCard(), new JackCard());
        theDeck.clear();
        assertEquals(0,theDeck.getNbCards());
    }

    @Test public void toString_SingleCase() {
        assertEquals("TargetDeck: [Jack,Joker]",theDeck.toString());
    }

}
