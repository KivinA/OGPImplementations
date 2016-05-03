package chapter_9.course_implementation.decks;

import be.kuleuven.cs.som.annotate.Raw;
import chapter_9.course_implementation.cards.*;

/**
 * A class of source stacks containing cards.
 * 
 * @version 2.0
 * @author  Eric Steegmans
 */
public class SourceDeck extends FromDeck {

	/**
	 * Initialize this new source deck with given cards.
	 *
	 * @param  cards
	 *         The cards for this new source deck.
	 * @effect This new source deck is initialized as a new from
	 *         deck with the given cards.
	 *       | super(cards)
	 */
	@Raw public SourceDeck(Card... cards) throws IllegalArgumentException {
		super(cards);
	}

	/**
	 * Check whether this source deck can have the given capacity as
	 * its capacity.
	 * 
	 * @return True if and only if the given minimum number of cards is 0,
	 *         and if the given maximum number of cards is at least 5.
	 *       | result == (minNbCards == 0) && (maxNbCards >= 5)
	 */
	@Override
	public boolean canHaveAsCapacity(int minNbCards, int maxNbCards) {
		return super.canHaveAsCapacity(minNbCards, maxNbCards);
	}

	/**
	 * Check whether the card on top of this source deck can be added
	 * on top of the given target deck.
	 *
	 * @return True if and only if this source deck has not reached its
	 *         minimal size, if the the given target deck is effective,
	 *         if the given target deck has not reached its maximum size,
	 *         and if the card on top of this source deck matches on the
	 *         card on top of the given target deck.
	 *       | result ==
	 *       |   (! hasMinimumSize()) &&
	 *       |   (target != null) &&
	 *       |   (! target.hasMaximumSize()) &&
	 *       |   (getCardAtTop().matchesOn(target.getCardAtTop()))
	 */
	@Override public boolean topCardMatchesOn(TargetDeck target) {
		return (!hasMinimumSize()) && (target != null)
			&& (! target.hasMaximumSize())
			&& (getCardAtTop().matchesOn(target.getCardAtTop()));
	}
	
	/**
	 * Move the card on top of this source deck on top of the given
	 * target deck [Alternative version]
	 *
	 * @throws IllegalStateException
	 *         Always if this from deck has reached its minimum size,
	 *         if the given target deck is not effective, if the given
	 *         target deck has reached its maximum size, or if the card
	 *         on top of this from deck does not match on the card on
	 *         top of the given target deck.
	 *       | this.hasMinimumSize() || (target == null) ||
	 *       | (! target.hasMaximumSize()) ||
	 *       | (! this.getCardAtTop().matchesOn(target.getCardOnTop())
	 */
	@Override
	public void moveTopAlt(TargetDeck target) throws IllegalStateException {
		if (this.hasMinimumSize() || (target == null) || target.hasMaximumSize() ||
		 	(! this.getCardAtTop().matchesOn(target.getCardAtTop())) )
		 	throw new IllegalStateException();
		target.pushCard(this.getCardAtTop());
		this.popCard();
	}

}