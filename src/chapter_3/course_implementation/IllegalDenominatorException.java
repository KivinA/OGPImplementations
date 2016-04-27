package chapter_3.course_implementation;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class for signaling illegal denominators for rational numbers.
 * 
 * @note     In this session, we use checked exceptions to illustrate
 *           the problems they bring in Java. Starting from the next
 *           session, we will only work with unchecked exceptions.
 * @version  2.0
 * @author   Eric Steegmans
 */
public class IllegalDenominatorException extends Exception {

	/**
	 * Initialize this new illegal denominator exception with given value.
	 * 
	 * @param  value
	 *         The value for this new illegal denominator exception.
	 * @post   The value of this new illegal denominator exception is equal
	 *         to the given value.
	 *       | new.getValue() == value
	 */
	public IllegalDenominatorException(long value) {
		this.value = value;
	}

	/**
	 * Return the value registered for this illegal denominator exception.
	 */
	@Basic @Immutable
	public long getValue() {
		return this.value;
	}

	/**
	 * Variable registering the value involved in this illegal denominator
	 * exception.
	 */
	private final long value;

	/**
	 * The Java API strongly recommends to explicitly define a version
	 * number for classes that implement the interface Serializable.
	 * At this stage, that aspect is of no concern to us. 
	 */
	private static final long serialVersionUID = 2003001L;

}