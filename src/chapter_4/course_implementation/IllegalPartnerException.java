package chapter_4.course_implementation;import be.kuleuven.cs.som.annotate.*;/** * A class for signaling illegal partners in marriages. *  * @version  2.0 * @author   Eric Steegmans */public class IllegalPartnerException extends RuntimeException {		/**	 * Initialize this new illegal partner exception involving the given	 * persons.	 *	 * @param   first	 *          The first illegal partner.	 * @param   second	 *          The second illegal partner.	 * @post    The first person involved in this new illegal partner	 *          exception is the same as the given first person.	 *          | new.getFirstPerson() == first	 * @post    The second person involved in this new illegal partner	 *          exception is the same as the given second person.	 *          | new.getSecondPerson() == second	 */	public IllegalPartnerException(Person first, Person second) {		this.first = first;		this.second = second;	}	/**	 * Return the first person involved in this illegal partner exception.	 */	@Basic public Person getFirstPerson() {		return first;	}	/**	 * Return the second person involved in this illegal partner exception.	 */	@Basic public Person getSecondPerson() {		return second;	}

    /**     * Variables referencing the first person, respectively the second     * person involved in this illegal partner exception.     */
    private final Person first;
    private final Person second;    /**     * The Java API strongly recommends to explicitly define a version     * number for classes that implement the interface Serializable.     * At this stage, that aspect is of no concern to us.      */    private static final long serialVersionUID = 2003001L;    
}