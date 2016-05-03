package chapter_9.course_implementation.decks;

import java.util.Deque;
import java.util.ArrayDeque;

import be.kuleuven.cs.som.annotate.*;
import chapter_9.course_implementation.cards.*;

/**
 * A class of decks containing cards.
 *
 * @invar   Each card deck  can have its capacity as its
 *          capacity.
 *        | canHaveAsCapacity(getMinimumNbCards(),getMaximumNbCards())
 * @invar   Each card deck must have a proper sequence of cards.
 *        | hasProperCards()
 * 
 * @version 2.0
 * @author  Eric Steegmans
 */
public abstract class CardDeck {

    /**
     * Initialize this new empty card deck with given capacity.
     *
     * @param  minNbCards
     *         The minimum number of cards ever on this new card deck.
     * @param  maxNbCards
     *         The maximum number of cards ever on this new card deck.
     * @post   The maximum number of cards for this new card deck is equal
     *         to the given maximum number of cards.
     *       | new.getMaximumNbCards() == maxNbCards
     * @throws IllegalArgumentException
     *         This new card deck can not have a capacity ranging
     *         between the given minimum and maximum number of cards.
     *       | ! canHaveAsCapacity(minNbCards,maxNbCards))
     */
    @Raw @Model
    protected CardDeck(int minNbCards, int maxNbCards)
            throws IllegalArgumentException {
        if (!canHaveAsCapacity(minNbCards, maxNbCards))
            throw new IllegalArgumentException("Illegal capacity!");
        setMaximumNbCards(maxNbCards);
    }

    /**
     * Return the maximum number of cards that can be put on this card
     * deck.
     */
    @Basic
    public int getMaximumNbCards() {
        return this.maximumNbCards;
    }

    /**
     * Return the minimum number of cards that must be on this card deck.
     */
    @Basic
    public abstract int getMinimumNbCards();

    /**
     * Check whether this card deck can have the given capacity
     * as its capacity.
     * 
     * @param  minNbCards
     *         The minimum number of cards for the capacity to check.
     * @param  maxNbCards
     *         The maximum number of cards for the capacity to check.
     * @return False if the given minimum number of cards is negative,
     *         if the given maximum number of cards is not positive,
     *         or if the given minimum number of cards exceeds the
     *         given maximum number of cards.
     *       | if ( (minNbCards < 0) || (maxNbCards <= 0) || 
     *       |      (minNbCards > maxNbCards) )
     *       |   then result == false
     */
    @Raw
    public boolean canHaveAsCapacity(int minNbCards, int maxNbCards) {
        return (minNbCards >= 0) && (maxNbCards > 0) && (minNbCards <= maxNbCards);
    }

    /**
     * Set the maximum number of cards for this card deck to the
     * given maximum number of cards.
     * 
     * @param  maximumNbCards
     *         The new maximum number of cards for this card deck.
     * @pre    This card deck can have the given maximum number
     *         of cards as part of its capacity.
     *       | canHaveAsCapacity(getMinimumNbCards(),maximumNbCards)
     * @post   The maximum number of cards for this card deck is equal
     *         to the given maximum number of cards.
     *       | new.getMaximumNbCards() == maximumNbCards 
     */
    @Raw
    protected void setMaximumNbCards(int maximumNbCards) {
        assert canHaveAsCapacity(getMinimumNbCards(),maximumNbCards);
        this.maximumNbCards = maximumNbCards;
    }

    /**
     * Variable registering the maximum number of cards for this card deck.
     */
    private int maximumNbCards;

    /**
     * Check whether this card deck has reached its maximum number
     * of cards.
     *
     * @return True if and only if the number of cards on this
     *         card deck is equal to its maximum number of cards.
     *       | result == (getNbCards() == getMaximumNbCards())
     */
    @Raw
    public boolean hasMaximumSize() {
        return getNbCards() == getMaximumNbCards();
    }

    /**
     * Check whether this card deck has reached its minimum number of
     * cards.
     *
     * @return True if and only if the number of cards on this
     *         card deck is equal to its minimum number of cards.
     *       | result == (getNbCards() == getMinimumNbCards())
     */
    @Raw
    public boolean hasMinimumSize() {
        return getNbCards() == getMinimumNbCards();
    }

    /**
	 * Return all the cards loaded on this card deck, with the card on top
	 * of this card deck at the start of the resulting array.
	 */
	@Basic
	public Card[] getAllCards() {
	    Card[] result = new Card[getNbCards()];
	    cards.toArray(result);
	    return result;
	}

	/**
     * Return the card at the given index in this card deck.
     *
     * @param  index
     *         The index of the card to be returned.
     * @return The card in the array of all cards at index-1.
     * 		 | result == getAllCards()[index-1]
     * @throws IndexOutOfBoundsException
     *         The given index is below 1 or exceeds the number
     *         of cards on this card deck.
     *       | (index < 1) || (index > getNbCards())
     */
    public Card getCardAt(int index) throws IndexOutOfBoundsException {
        return getAllCards()[index - 1];
    }

    /**
	 * Return the number of cards on this card deck.
	 * 
	 * @return The number of cards in the array of all cards.
	 * 		 | result == getAllCards().length
	 */
	public int getNbCards() {
	    return cards.size();
	}

	/**
     * Check whether this card deck can have the given card as one
     * of its cards.
     *
     * @param  card
     *         The card to be checked.
     * @return True if and only if the given card is effective.
     *        | result == (card != null)
     */
    @Raw
    public boolean canHaveAsCard(Card card) {
    	return (card != null);
    }

    /**
     * Check whether this card deck has a proper sequence of cards.
     * 
     * @return True if and only if the numbe of cards on this card deck
     *         is in between the minimum number of cards and the maximum
     *         number of cards for this deck, and if this deck can have
     *         each of its cards as one of its cards.
     *       | result ==
     *       |   (getNbCards() >= getMinimumNbCards()) &&
     *       |   (getNbCards() <= getMaximumNbCards()) &&
     *       |   ( for each card in getAllCards():
     *       |     canHaveAsCard(card)
     */
    @Raw
    public boolean hasProperCards() {
    	if ( (getNbCards() < getMinimumNbCards()) || 
    			(getNbCards() > getMaximumNbCards()) )
    		return false;
    	for (Card card: cards)
    		if (! canHaveAsCard(card) )
    			return false;
    	return true;
    }
    
    /**
     * Return the card on top of this card deck.
     *
     * @return The card on this card deck at the highest index.
     *       | result == getCardAt(1)
     * @throws IllegalStateException
     *         This card deck is empty.
     *       | getNbCards() == 0
     */
    public Card getCardAtTop() throws IllegalStateException {
        if (getNbCards() == 0)
            throw new IllegalStateException("No top card for an empty deck!");
        return cards.peekFirst();
    }

    /**
     * Check whether the given card is on this card deck.
     *
     * @param  card
     *         The card to be checked.
     * @return True if and only if the given card is loaded at some
     *         index on this card deck.
     *       | result ==
     *       |   (for some crd in getAllCards():
     *       |       crd == card) 
     */
    @Raw
    public boolean hasAsCard(Card card) {
    	return cards.contains(card);
    }

    /**
     * Push the given card on top of this card deck.
     *
     * @param  card
     *         The card to be pushed.
     * @pre    This card deck must accept the given card as one of
     *         its cards.
     *       | canHaveAsCard(card)
     * @pre    This card deck has not ye reached its maximum size.
     *       | ! hasMaximumSize()
     * @post   The number of cards on this card deck is incremented
     *         by 1.
     *       | new.getNbCards() == getNbCards() + 1
     * @post   The card on top of this card deck is the same as the
     *         given card.
     *       | new.getCardAtTop() == card
     */
    @Raw
    protected void pushCard(Card card) {
        assert canHaveAsCard(card);
        assert ! hasMaximumSize();
        cards.push(card);
    }

    /**
     * Remove the card on top of this card deck.
     *
     * @pre    This card deck has not reached its minimal size.
     *       | ! hasMinimumSize()
     * @post   The number of cards on this card deck is decremented
     *         by 1.
     *       | new.getNbCards() == getNbCards() - 1
     */
    @Raw
    protected void popCard() {
        assert ! hasMinimumSize();
        cards.pop();
    }

    /**
     * Remove all cards from this card deck.
     * 
     * @pre    The minimum number of cards for this card deck must
     *         be 0.
     *       | getMinimumNbCards() == 0
     * @post   No cards are loaded any more on this card deck.
     *       | new.getNbCards() == 0
     */
    protected void clear() {
        assert getMinimumNbCards() == 0;
        cards.clear();
    }

    /**
     * Variable referencing a stack containing all the cards in this
     * card deck.
     * 
     * @invar  The stack of cards is effective.
     *       | cards != null
     * @invar  This card deck can have each of the cards in the
     *         stack.
     *       | for each card in cards:
     *       |   canHaveAsCard(card)
     */
    private final Deque<Card> cards = new ArrayDeque<>();

    /**
     * Return a textual representation of this card deck.
     * 
     * @return An effective string starting with the simple name of the class
     *         to which this card deck effectively belongs, followed by
     *         the textual representation of each card on this card
     *         deck, separated by comma's and enclosed in square brackets.
     *       | (result != null) &&
     *       | (result.matches(getClass().getSimpleName() + ": [.*]") &&
     *       | (for each I in 1..getNbCards():
     *       |    result.matches(".*[.*"+getCardAt(I)+".*]")
     * @note   The formal specification does not express that the cards
     *         must be displayed in order.
     */
    @Override
    public String toString() {
        String result = this.getClass().getSimpleName();
        String prefix = ": [";
        for (Card card: cards) {
            result += prefix + card;
            prefix = ",";
        }
        return result + "]";
    }

}