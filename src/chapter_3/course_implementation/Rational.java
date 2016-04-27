package chapter_3.course_implementation;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of rational numbers with integer numerator and denominator.
 *
 * @invar   The denominator of each rational number must be a legal
 *          denominator for a rational number.
 *        | isValidDenominator(getDenominator())
 * 
 * @version 2.0
 * @author  Eric Steegmans
 */
public class Rational {

	/**
	 * Initialize this new rational number with given numerator and
	 * given denominator.
	 *
	 * @param  numerator
	 *		   The numerator for this new rational number.
	 * @param  denominator
	 *		   The denominator for this new rational number.
	 * @post   The numerator of this new rational number is equal to
	 *		   the given numerator.
	 *       | new.getNumerator() == numerator
	 * @post   The denominator of this new rational number is equal to
	 *		   the given denominator.
	 *       | new.getDenominator() == denominator
	 * @throws IllegalDenominatorException
	 * 		   The given denominator is not a valid denominator for
	 * 		   a rational number.
	 *       | ! isValidDenominator(denominator)
	 */
	public Rational(long numerator, long denominator)
			throws IllegalDenominatorException {
		if (!isValidDenominator(denominator))
			throw new IllegalDenominatorException(denominator);
		this.numerator = numerator;
		this.denominator = denominator;
	}

	/**
	 * Return the numerator (Dutch: 'teller') of this rational number.
	 */
	@Basic
	@Raw
	@Immutable
	public long getNumerator() {
		return numerator;
	}

	/**
	 * Variable registering the numerator of this rational
	 * number.
	 */
	private final long numerator;

	/**
	 * Return the denominator (Dutch: 'noemer') of this rational number.
	 */
	@Basic
	@Raw
	@Immutable
	public long getDenominator() {
		return denominator;
	}

	/**
	 * Check whether the given denominator is a valid denominator for
	 * a rational number.
	 * 
	 * @param  denominator
	 *         The denominator to check.
	 * @return True if and only if the given denominator is positive.
	 *       | result == denominator > 0
	 */
	public static boolean isValidDenominator(long denominator) {
		return denominator > 0;
	}

	/**
	 * Variable registering the denominator of this rational
	 * number.
	 */
	private final long denominator;

	/**
	 * Return a boolean reflecting whether this rational number is
	 * identical to the given rational number.
	 *
	 * @param  other
	 *         The rational number to compare with.
	 * @return True if and only if the numerator and the denominator of
	 *         this rational number are equal to the numerator,
	 *         respectively the denominator of the other rational
	 *         number.
	 *       | result ==
	 *       |      (getNumerator() == other.getNumerator())
	 *       |   && (getDenominator() == other.getDenominator())
	 * @throws NullPointerException
	 *         The other rational number is not effective.
	 *       | other == null
	 * @note   This method is better worked out as a redefinition 
	 *         of the method equals inherited from the class Object. At this
	 *         point in the course, however, not all ingredients to work out
	 *         such a redefinition have been covered. 
	 */
	public boolean isIdenticalTo(Rational other) throws NullPointerException {
		return (getNumerator() == other.getNumerator())
				&& (getDenominator() == other.getDenominator());
	}

	/**
	 * Check whether this rational number has the same value as the given
	 * rational number.
	 *
	 * @param  other
	 *         The rational number to compare with.
	 * @return True if and only if this rational number in normalized
	 *         form is identical to the other rational number in normalized
	 *         form.
	 *       | result ==
	 *       |   this.normalize().isIdenticalTo(other.normalize())
	 * @throws NullPointerException
	 *         The other rational number is not effective.
	 *       | other == null
	 */
	public boolean hasSameValueAs(Rational other) throws NullPointerException {
		Rational first = this.normalize();
		Rational second = other.normalize();
		return first.isIdenticalTo(second);
	}

	/**
	 * Return the common factor of the numerator and the denominator
	 * of this rational number.
	 * 
	 * @return The greatest common divisor of the absolute value of the
	 *         numerator and the denominator of this rational number.
	 *       | result ==
	 *       |   if (getNumerator() > Long.MIN_VALUE)
	 *       |     then ExtMath.gcd(Math.abs(getNumerator()),getDenominator())
	 *       |     else ExtMath.gcd(Math.abs(getNumerator()+getDenominator()),
	 *       |                      getDenominator())
	 * @note   Because the absolute value of Long.MIN_VALUE is that same value,
	 *         we must distinguish this special case in the definition of
	 *         this method. It is easy to show that in mathematics, gcd(|-A|,B)
	 *         is equal to gcd(|-A+B|,B).
	 * @note   In the current version of the class, it is possible that the
	 *         common factor of a rational number is computed many times.
	 *         Because this is a time-consuming operation, it might be useful
	 *         to store the common factor as part of the state of a rational
	 *         number, and to apply lazy evaluation on it (meaning that the
	 *         common factor is only computed the first time it is explicitly
	 *         asked, and not in the constructor).
	 */
	public long getCommonFactor() {
		if (getNumerator() > Long.MIN_VALUE)
			return ExtMath.gcd(Math.abs(getNumerator()), getDenominator());
		else
			return ExtMath.gcd(Math.abs(getNumerator() + getDenominator()),
					getDenominator());
	}

	/**
	 * Return a new rational number in normalized form with the same value
	 * as this rational number.
	 *
	 * @return Numerator and denominator of the resulting rational number
	 *         are equal to the numerator, respectively the denominator
	 *         of this rational number divided by the common factor of
	 *         this rational number.
	 *       | (result.getNumerator() == (getNumerator()/getCommonFactor())) &&
	 *       | (result.getDenominator() == getDenominator()/getCommonFactor())
	 * @note   The postcondition implies that the resulting rational number
	 *         must be effective.
	 */
	public Rational normalize() {
		try {
			long commonFactor = getCommonFactor();
			if (commonFactor > 1)
				return new Rational(getNumerator() / commonFactor,
						getDenominator() / commonFactor);
			else
				return this;
		} catch (IllegalDenominatorException exc) {
			// At this point we must add a catcher to the method. The
			// compiler thinks the constructor may throw an exception.
			// We know that in this particular case, that exception will
			// not be thrown. If we would not add a catcher, the method
			// would have to include IllegalDenominatorException in its
			// throws list. Then all clients of the method would have to
			// deal with the exception.
			// This phenomenon is the main reason why will not use checked
			// exceptions any further.
			assert (false);
			return null;
		}
	}

	/**
	 * Check whether this rational number is normalized.
	 * 
	 * @return True if and only if the common factor of this rational
	 *         number is 1.
	 *       | result == (getCommonFactor() == 1)
	 */
	public boolean isNormalized() {
		return (getCommonFactor() == 1);
	}

	/**
	 *  Return a rational number obtained by multiplying this rational
	 *  number with the given integer number.
	 *
	 * @param  factor
	 *         The integer number to multiply with.
	 * @return The resulting rational number has the same value as a rational number
	 *         whose denominator is equal to the denominator of this rational
	 *         number in normalized form, and whose numerator is equal to the
	 *         numerator of this rational number in normalized form multiplied
	 *         with the given factor divided by the greatest common divisor of
	 *         the absolute value of that factor and the denominator of this
	 *         rational number in normalized form.
	 *        | let
	 *        |   reducedFactor = factor / ExtMath.gcd
	 *        |     (Math.abs(factor),this.normalize().getDenominator())
	 *        | in
	 *        |   result.hasSameValueAs(
	 *        |     new Rational(
	 *        |       this.normalize().getNumerator()*reducedFactor,
	 *        |       this.normalize().getDenominator()))
	 * @throws TimesOverflowException
	 *         The product of the numerator of this rational number in
	 *         normalized form with the given factor divided by the greatest
	 *         common divisor of the absolute value of that factor and the
	 *         denominator of this rational number in normalized form is
	 *         outside the range of the type long.
	 *       | let
	 *       |   reducedFactor = factor / ExtMath.gcd
	 *       |     (Math.abs(factor),this.normalize().getDenominator())
	 *       | in
	 *       |   ! ExtMath.areMulipliable
	 *       |     (this.normalize().getNumerator(),reducedFactor)
	 */
	public Rational times(long factor) throws TimesOverflowException {
		try {
			try {
				// In a first attempt, we simply try to multiply the numerator
				// of this rational number with the given factor. This yields
				// efficient computations for small rational numbers and
				// factors.
				long newNumerator = ExtMath.times(getNumerator(), factor);
				return new Rational(newNumerator, getDenominator());
			} catch (TimesOverflowException exc) {
				assert !ExtMath.areMultipliable(getNumerator(), factor);
				// If we can reduce this rational number by normalization,
				// we invoke the method on the normalized version of this
				// rational number.
				// We do not want to invoke the method isNormalized at this
				// point, because it would require the computation of
				// the greatest common divisor, which is an expensive operation,
				// and which would be needed again in subsequently normalizing
				// this rational number.
				Rational thisNormalized = this.normalize();
				if (thisNormalized.getNumerator() != this.getNumerator())
					return normalize().times(factor);
				// If the given factor and the denominator of this rational
				// number (which must be a normalized rational number at this
				// point), have some factor in common, we invoke the method again
				// reversing the roles of the factor and the numerator of this
				// rational number.
				Rational toReduce = new Rational(factor, this.getDenominator())
						.normalize();
				if (toReduce.getNumerator() != factor)
					return toReduce.times(this.getNumerator());
				// At this point we have done everything we can to compute
				// the product. Apparently there is no way to get the product
				// in the range of the type long.
				// We give uo and throw the TimesOverflowException again.
				throw exc;
			}
		} catch (IllegalDenominatorException exc) {
			assert false;
			return null;
		}
	}

	/** HOMEWORK */

	/**
	 * Initialize this new rational number with numerator 0 and
	 * arbitrary denominator.
	 *
	 * @post   The numerator of this new rational number is equal to 0.
	 *       | new.getNumerator() == 0
	 * @post   The denominator of this new rational number has an arbitrary
	 *         valid value.
	 *       | isValidDenominator(new.getDenominator())
	 */
	public Rational() {
		// Another constructor cannot be invoked at this point,
		// because Java offers no abilities to catch exceptions
		// they might throw. This is one of the reasons not to
		// use checked exceptions.
		this.numerator = 0;
		this.denominator = 1;
	}

	/**
	 * Initialize this new rational number with numerator 1 and given
	 * denominator.
	 *
	 * @param  denominator
	 *         The denominator for this new rational number.
	 * @effect This new rational number is initialized with 1 as its
	 *         numerator, and with the given denominator as its denominator.
	 *       | this(1,denominator) 
	 */
	public Rational(long denominator) throws IllegalDenominatorException {
		this(1, denominator);
	}

	/**
	 * Return a textual representation of this rational number.
	 *
	 * @return A textual representation of this rational number in the form
	 *         [numerator/denominator].
	 *       | result.equals("["+getNumerator()+"/"+getDenominator()+"]"
	 */
	public String toString() {
		return "[" + getNumerator() + "/" + getDenominator() + "]";
	}

	/**
	 * Return a copy of this rational number.
	 *
	 * @return A rational number that is identical to this rational number,
	 *         but not the same as this rational number.
	 *       | this.isIdentical(result) &&
	 *       | (result != this)
	 * @note   This method is best defined as a redefinition of
	 *         the method clone inherited from Object. At this point
	 *         in the course, however, not all the elements to work out
	 *         such a redefinition have been covered.
	 * @note   The specification of the method clone at the level of
	 *         the class Object stipulates that the resulting object
	 *         may not be the same as the object to be cloned. We follow
	 *         that convention in the definition below.
	 */
	public Rational getCopy() {
		try {
			return new Rational(this.getNumerator(), this.getDenominator());
		} catch (IllegalDenominatorException e) {
			assert false;
			return null;
		}
	}

	/**
	 * Return a new rational number obtained by adding the given rational
	 * number to this rational number.
	 *    Only a simple version is worked out here.
	 *
	 * @param  other
	 *         The right-hand side of the addition.
	 * @return The numerator of the resulting rational number is the sum
	 *         of the cross-products of numerators and denominators of
	 *         this rational number and the other rational number.
	 *       | result.getNumerator() ==
	 *       |     getNumerator()*other.getDenominator()
	 *       |   + getDenominator()*other.getNumerator() 
	 * @return The denominator of the resulting rational number is the
	 *         product of both denominators.
	 *       | result.getDenominator() ==
	 *       |   getDenominator()*other.getDenominator()
	 * @throws NullPointerException
	 *         The other rational number is not effective.
	 *       | other == null
	 * @throws OverflowException
	 *         The numerator of the one rational number cannot be
	 *         multiplied with the denominator of the other rational
	 *         number.
	 *       |    (! ExtMath.areMultipliable
	 *       |      (getNumerator(),other.getDenominator()) ) ||
	 *       | || (! ExtMath.areMultipliable
	 *       |      (getDenominator(),other.getNumerator()) )
	 * @throws OverflowException
	 *         The cross-products of the numerator of the one rational
	 *         rational number with the denominator of the other rational
	 *         number cannot be added.
	 *       | ! ExtMath.areAddable
	 *       |   (getNumerator() * other.getDenominator(),
	 *       |    getDenominator() * other.getNumerator())
	 * @throws OverflowException
	 *         The denominators of both rational numbers cannot be
	 *         multiplied.
	 *       | ! ExtMath.areMultipliable
	 *       |   (getDenominator(),other.getDenominator())
	 */
	public Rational add(Rational other) throws OverflowException,
			NullPointerException {
		try {
			long crossProduct1 = ExtMath.times(getNumerator(),
					other.getDenominator());
			long crossProduct2 = ExtMath.times(getDenominator(),
					other.getNumerator());
			long resultNumerator = ExtMath.add(crossProduct1, crossProduct2);
			long resultDenominator = ExtMath.times(getDenominator(),
					other.getDenominator());
			return new Rational(resultNumerator, resultDenominator);
		} catch (IllegalDenominatorException exc) {
			assert false;
			return null;
		}
	}

}