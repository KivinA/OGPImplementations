package chapter_9.course_implementation.decks;

import chapter_9.course_implementation.cards.*;

/**
 * A class of target decks containing cards.
 *   A target deck serves to lay cards on.
 * 
 * @version 2.0
 * @author  Eric Steegmans
 */
public class TargetDeck extends CardDeck {

    /**
     * Initialize this new target deck with given maximum number of
     * cards and loaded with the given card.
     *
     * @param  maxNbCards
     *         The maximum number of cards ever on this new target deck.
     * @param  card
     *         The card to be put on top of this new target deck.
     * @effect This new target deck is initialized as a card deck with
     *         1 as its minimum number of cards and with the given maximum
     *         number of cards as its maximum number of cards.
     *       | super(1,maxNbCards)
     * @post   The given card is the only card on this new target deck.
     *       | (new.getNbCards() == 1) &&
     *       | (new.getCardAtTop() == card)
     * @throws IllegalArgumentException
     *         This new target deck cannot have the given card as one
     *         of its cards.
     *       | ! canHaveAsCard(card)
     */
    public TargetDeck(int maxNbCards, Card card)
            throws IllegalArgumentException {
        super(1,maxNbCards);
        if (!canHaveAsCard(card))
            throw new IllegalArgumentException("Inappropriate first card!");
        pushCard(card);
    }

    /**
     * Return the minimum number of cards that must be on this target deck.
     *
     * @return Always 1.
     *       | result == 1
     */
    @Override
    public final int getMinimumNbCards() {
        return 1;
    }

    /**
     * Check whether this target deck can have the given capacity as
     * its capacity.
     * 
     * @return True if and only if the given minimum number of cards is 1,
     *         and if the given the given maximum number of cards is not
     *         below 46.
     *       | result ==
     *       |   (minNbCards == 1) && (maxNbCards >= 46)
     */
    @Override
    public boolean canHaveAsCapacity(int minNbCards, int maxNbCards) {
        return (minNbCards == 1) && (maxNbCards >= 46);
    }

}