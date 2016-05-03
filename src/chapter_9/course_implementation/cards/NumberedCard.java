package chapter_9.course_implementation.cards;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of numbered cards used in the game of Thieves.
 *
 * @invar  The value of each numbered card must be a valid
 *         value for a numbered card.
 *       | isValidValue(getValue())
 *
 * @version  2.0
 * @author   Eric Steegmans
 */
public class NumberedCard extends Card {

	/**
	 * Initialize this new numbered card with given value.
	 *
	 * @param  value
	 *         The value for this new numbered card.
	 * @post   The value of this new numbered card is equal to the
	 *         given value.
	 *       | new.getValue() == value
	 * @throws IllegalArgumentException
	 *         The given value is not a valid value for a numbered card.
	 *       | ! isValidValue(value)
	 */
	public NumberedCard(int value) throws IllegalArgumentException {
		if (!isValidValue(value))
			throw new IllegalArgumentException(
					"Invalid value for numbered card!");
		this.value = value;
	}

	/**
	 * Return the value of this numbered card.
	 */
	@Basic
	@Immutable
	public int getValue() {
		return this.value;
	}

	/**
	 * Check whether the given value is a valid value
	 * for a numbered card.
	 * 
	 * @return True if and only if the given value is positive
	 *         and does not exceed 10.
	 *       | result == (value >= 1) && (value <= 10)
	 */
	public static boolean isValidValue(int value) {
		return (value >= 1) && (value <= 10);
	}

	/**
	 * Variable registering the value of this numbered card.
	 */
	private final int value;

	/**
	 * Check whether this numbered card matches on the given card.
	 *
	 * @return If the given card is not effective, always false.
	 *       | if (other == null)
	 *       |   then result == false
	 * @return If the other card is an effective numbered card,
	 *         true if and only if the value of this numbered card
	 *         is one higher or one lower than the value of the
	 *         other numbered card.
	 *       | if (other instanceof NumberedCard)
	 *       |   then result ==
	 *       |     (Math.abs(getValue()-
	 *       |         ((NumberedCard)other).getValue()) == 1)
	 * @return If the other card is an effective card of some other
	 *         type, true if and only if this numbered card matches on
	 *         the other card.
	 *       | if ( (other != null) && ( ! (other instanceof NumberedCard))
	 *       |   then result == other.matchesOn(this)
	 */
	@Override
	public boolean matchesOn(Card other) {
		if (other == null)
			return false;
		if (other instanceof NumberedCard)
			return (Math.abs(getValue() - ((NumberedCard) other).getValue()) == 1);
		return other.matchesOn(this);
	}

	/**
	 * Return a textual representation of this numbered card.
	 * 
	 * @return The string "Card: " followed by the value of this
	 *         numbered card.
	 *       | result.equals("Card:" + getValue())
	 */
	@Override
	public String toString() {
		return "Card: " + getValue();
	}

	/**
	 * Return a clone of this numbered card.
	 * 
	 * @return The value of the resulting card is equal to the value
	 *         of this numbered card.
	 *       | result.getValue() == this.getValue()
	 */
	@Override
	public NumberedCard clone() {
		return (NumberedCard) super.clone();
	}

}