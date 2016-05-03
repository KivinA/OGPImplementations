package chapter_9.course_implementation.decks;

import be.kuleuven.cs.som.annotate.Raw;
import chapter_9.course_implementation.cards.*;

/**
 * A class of help decks containing cards.
 * 
 * @version 2.0
 * @author  Eric Steegmans
 */
public class HelpDeck extends FromDeck {

	/**
	 * Initialize this new help deck with given cards.
	 *
	 * @param  cards
	 *         The cards for this new help deck.
	 * @effect This new help deck is initialized as a new from
	 *         deck with the given cards.
	 *       | super(cards)
	 */
	@Raw public HelpDeck(Card... cards) throws IllegalArgumentException {
		super(cards);
	}

	/**
	 * Check whether this help deck can have the given capacity as
	 * its capacity.
	 * 
	 * @return True if and only if the given minimum number of cards is 0,
	 *         and if the given maximum number of cards is at least 10.
	 *       | result == (minNbCards == 0) && (maxNbCards >= 10)
	 */
	@Override
	public boolean canHaveAsCapacity(int minNbCards, int maxNbCards) {
		return (minNbCards == 0) && (maxNbCards >= 10);
	}

	/**
	 * Check whether the card on top of this help deck can be added on top
	 * of the given target deck.
	 *
	 * @return True if and only if this help deck has not reached its
	 *         minimal size, if the given target deck is effective, and
	 *         if the given target has not reached its maximum size.
	 *       | result ==
	 *       |   (! hasMinimumSize()) &&
	 *       |  (target != null) &&
	 *       |  (! target.hasMaximumSize())
	 */
	@Override public boolean topCardMatchesOn(TargetDeck target) {
		return (!hasMinimumSize()) && (target != null)
			&& (! target.hasMaximumSize());
	}
	
	/**
	 * Move the card on top of this help deck on top of the given
	 * target deck [Alternative version]
	 *
	 * @throws IllegalStateException
	 *         Always if this from deck has reached its minimum size,
	 *         if the given target deck is not effective, or if the given
	 *         target deck has reached its maximum size.
	 *       | this.hasMinimumSize() || (target == null) ||
	 *       | (! target.hasMaximumSize())
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