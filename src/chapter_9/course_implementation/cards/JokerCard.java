package chapter_9.course_implementation.cards;

/**
 * A class of joker cards used in a game of Thieves.
 * 
 * @version 2.0
 * @author  Eric Steegmans
 */
public class JokerCard extends Card {

	/**
	 * Initialize this new joker card.
	 */
	public JokerCard() {
	}

	/**
	 * Check whether this joker card matches on the given card.
	 *
	 * @return True if and only if the given card is an effective
	 *         card.
	 *       | result == (other != null)
	 */
	@Override
	public final boolean matchesOn(Card other) {
		return other != null;
	}

	/**
	 * Return a textual representation of this joker card.
	 * 
	 * @return The string "Joker".
	 *       | result.equals("Joker")
	 */
	@Override
	public String toString() {
		return "Joker";
	}
	
	/**
	 * Return a clone of this joker card.
	 */
	@Override
	public JokerCard clone() {
		return (JokerCard) super.clone();
	}

}