package chapter_9.course_implementation.cards;

/**
 * A class of odd-matcher cards used in a game of Thieves.
 * 
 * @version  2.0
 * @author   Eric Steegmans
 */
public class OddMatcherCard extends Card {

	/**
	 * Initialize this new odd matcher card.
	 */
	public OddMatcherCard() {
	}
	
	/**
	 * Check whether this odd matcher card matches on the given card.
	 *
	 * @return If the given card is not effective, always false.
	 *       | if (other == null)
	 *       |   then result == false
	 * @return If the other card is an effective numbered card,
	 *         true if and only if that numbered card has an odd
	 *         value.
	 *       | if (other instanceof NumberedCard)
	 *       |   then result ==
	 *       |         (((NumberedCard)other).getValue()) % 2 == 1)
	 * @return If the other card is an effective jack card,
	 *         always false.
	 *       | if (other instanceof JackCard)
	 *       |   then result == false
	 * @return If the other card is an odd matcher jack card,
	 *         always false.
	 *       | if (other instanceof OddMatcherCard)
	 *       |   then result == false
	 * @return If the other card is an effective card of some other
	 *         type, true if and only if this odd matcher card matches on
	 *         the other card.
	 *       | if ( (other != null)
	 *       |   && ( ! (other instanceof NumberedCard))
	 *       |   && ( ! (other instanceof JackCard)) ) 
	 *       |   && ( ! (other instanceof OddMatcherCard))
	 *       |     then result == other.matchesOn(this)
	 */
	@Override
	public boolean matchesOn(Card other) {
		if (other == null)
			return false;
		if (other instanceof NumberedCard)
			return ((NumberedCard) other).getValue() % 2== 1;
		if (other instanceof JackCard)
			return false;
		if (other instanceof OddMatcherCard)
			return false;
		return other.matchesOn(this);
	}

	/**
	 * Return a textual representation of this odd matcher card.
	 * 
	 * @return The string "Odd Matcher".
	 *       | result.equals("Odd Matcher")
	 */
	@Override
	public String toString() {
		return "Odd Matcher";
	}
	
	/**
	 * Return a clone of this odd matcher card.
	 */
	@Override
	public OddMatcherCard clone() {
		return (OddMatcherCard) super.clone();
	}

}