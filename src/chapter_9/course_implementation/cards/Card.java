package chapter_9.course_implementation.cards;

/**
 * A class of cards used in the game of Thieves.
 * 
 * @version  2.0
 * @author   Eric Steegmans
 */
public abstract class Card implements Cloneable {

    /**
     * Check whether this card matches on the given card.
     *
     * @param  other
     *         The card to match with.
     * @return False if the given card is not an effective card.
     *       | if (other == null)
     *       |   then result == false
     */
    public abstract boolean matchesOn(Card other);
        
    /**
     * Return a clone of this card.
     * 
     * @return The resulting card is effective and not the same
     *         as this card.
     *       | (result != null) && (result != this)
     * @return The resulting card belongs to the same class as
     *         this card.
     *       | result.getClass() == this.getClass()
     */
    @Override
    public Card clone() {
    	try {
    		return (Card) super.clone();
    	}
    	catch (CloneNotSupportedException exc) {
    		assert false;
    		return null;
    	}
    }
    
}