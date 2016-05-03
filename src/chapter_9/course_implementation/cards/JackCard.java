package chapter_9.course_implementation.cards;

/**
 * A class of jack cards used in a game of Thieves.
 * 
 * @version  2.0
 * @author   Eric Steegmans
 */
public class JackCard extends Card {

	/**
	 * Initialize this new jack card.
	 */
	public JackCard() {
	}

	/**
	 * Check whether this jack card matches on the given card.
	 *
	 * @return If the given card is not effective, always false.
	 *       | if (other == null)
	 *       |   then result == false
	 * @return If the other card is an effective numbered card,
	 *         true if and only if that numbered card has value 10.
	 *       | if (other instanceof NumberedCard)
	 *       |   then result ==
	 *       |         (((NumberedCard)other).getValue()) == 10)
	 * @return If the other card is an effective jack card,
	 *         always false.
	 *       | if (other instanceof JackCard)
	 *       |   then result == false
	 * @return If the other card is an effective card of some other
	 *         type, true if and only if this jack card matches on
	 *         the other card.
	 *       | if ( (other != null)
	 *       |   && ( ! (other instanceof NumberedCard))
	 *       |   && ( ! (other instanceof JackCard)) ) 
	 *       |     then result == other.matchesOn(this)
	 */
	@Override
	public boolean matchesOn(Card other) {
		if (other == null)
			return false;
		if (other instanceof NumberedCard)
			return ((NumberedCard) other).getValue() == 10;
		if (other instanceof JackCard)
			return false;
		return other.matchesOn(this);
	}

	/**
	 * Return a textual representation of this jack card.
	 * 
	 * @return The string "Jack".
	 *       | result.equals("Jack")
	 */
	@Override
	public String toString() {
		return "Jack";
	}
	
	/**
	 * Return a clone of this jack card.
	 */
	@Override
	public JackCard clone() {
		return (JackCard) super.clone();
	}

}