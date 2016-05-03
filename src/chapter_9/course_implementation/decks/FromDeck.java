package chapter_9.course_implementation.decks;

import be.kuleuven.cs.som.annotate.*;
import chapter_9.course_implementation.cards.*;

/**
 * A class of from decks containing cards.
 *   A from deck serves to take cards from.
 * 
 * @version 2.0
 * @author  Eric Steegmans
 */
public abstract class FromDeck extends CardDeck {

	/**
	 * Initialize this new from deck with given cards.
	 *
	 * @param  cards
	 *         The cards to be loaded on this new from deck.
	 * @effect This new from deck is initialized as a card deck with
	 *         0 as its minimum number of cards and with the length
	 *         of the given array of cards as its maximum number of cards.
	 *       | super(0,cards.length)
	 * @post   The number of cards on this new from deck is set
	 *         to the length of the given array of cards.
	 *       | new.getNbCards() == cards.length
	 * @post   This new from deck stores each of the given cards
	 *         in the same order.
	 *       | for each I in 1..cards.length:
	 *       |   new.getCardAt[I] == cards[I-1]
	 * @throws IllegalArgumentException
	 *         This new from deck cannot have at least one of the given
	 *         cards as its card.
	 *       | for some card in cards:
	 *       |   !canHaveAsCard(card)
	 */
	@Raw @Model
	protected FromDeck(Card... cards) throws IllegalArgumentException {
		super(0, cards.length);
		for (int pos = cards.length-1; pos >= 0; pos--) {
			if (!canHaveAsCard(cards[pos]))
				throw new IllegalArgumentException("Illegal card!");
			pushCard(cards[pos]);
		}
	}

	/**
	 * Return the minimum number of cards that must be on this from deck.
	 *
	 * @return Always 0.
	 *       | result == 0
	 */
	@Override
	public final int getMinimumNbCards() {
		return 0;
	}

	/**
	 * Check whether this from deck can have the given capacity as
	 * its capacity.
	 * 
	 * @return False if the given minimum number of cards is not 0,
	 *         or if the given maximum number of cards is not at
	 *         least 5.
	 *       | if ( (minNbCards != 0) || (maxNbCards < 5) )
	 *       |   then result == false
	 */
	@Override
	public boolean canHaveAsCapacity(int minNbCards, int maxNbCards) {
		return (minNbCards == 0) && (maxNbCards >= 5);
	}

	/**
	 * Check whether the card on top of this from deck can be added on
	 * top of the given target deck.
	 *
	 * @param  target
	 *         The deck upon which the top card should fit.
	 * @return False if this from deck has reached its minimum size.
	 *       | if (hasMinimumSize())
	 *       |   then result == false
	 *         Otherwise, false if the given target deck is not
	 *         effective.
	 *       | else if (target == null)
	 *       |   then result == false
	 *         Otherwise, false if the given target deck has reached
	 *         its maximum size.
	 *       | else if (target.hasMaximumSize())
	 *       |   then result == false
	 */
	public abstract boolean topCardMatchesOn(TargetDeck target);

	/**
	 * Move the card on top of this from deck on top of the given
	 * target deck.
	 *
	 * @param  target
	 *         The target deck for the transfer.
	 * @post   The new number of cards on this from deck is equal
	 *         to its old number of cards minus 1.
	 *       | new.getNbCards() = getNbCards() � 1
	 * @post   The new number of cards on the given target deck is
	 *         equal to its old number of cards + 1.
	 *       | (new target).getNbCards() == target.getNbCards() + 1
	 * @post   The new card on top of this target deck is the same as
	 *         the card that was on top of this from deck.
	 *       | (new target).getCardAtTop() == getCardAtTop()
	 * @throws IllegalStateException
	 *         The card on top of this from deck does not match on top
	 *         of the given target deck.
	 *       | ! topCardMatchesOn(target)
	 */
	public void moveTop(TargetDeck target) throws IllegalStateException {
		if (!topCardMatchesOn(target))
			throw new IllegalStateException("Illegal move!");
		target.pushCard(getCardAtTop());
		popCard();
	}

	/**
	 * Move the card on top of this from deck on top of the given
	 * target deck [Alternative version]
	 *
	 * @param  target
	 *         The target deck for the transfer.
	 * @post   The new number of cards on this from deck is equal
	 *         to its old number of cards minus 1.
	 *       | new.getNbCards() = getNbCards() - 1
	 * @post   The new number of cards on the given target deck is
	 *         equal to its old number of cards + 1.
	 *       | (new target).getNbCards() == target.getNbCards() + 1
	 * @post   The new card on top of this target deck is the same as
	 *         the card that was on top of this from deck.
	 *       | (new target).getCardAtTop() == getCardAtTop()
	 * @throws IllegalStateException
	 *         Definitely if this from deck has reached its minimum size,
	 *         if the given target deck is not effective, or if the given
	 *         target deck has reached its maximum size.
	 *         Possibly if the card on top of this from deck does not
	 *         match on the card on top of the given target deck.
	 *       | this.hasMinimumSize() || (target == null) ||
	 *       | (! target.hasMaximumSize()) ?
	 *       | (! this.getCardAtTop().matchesOn(target.getCardOnTop())
	 */
	public abstract void moveTopAlt(TargetDeck target) throws IllegalStateException;

}