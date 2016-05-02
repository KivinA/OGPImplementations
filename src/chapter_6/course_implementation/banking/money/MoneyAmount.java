package chapter_6.course_implementation.banking.money;

import java.math.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of money amounts expressing an amount of money
 * in units (cents) of a given currency.
 *   Objects of this class are actually containers in which
 *   an amount of money can be registered (mutable objects).
 * 	
 * @invar   The amount in cents of each money amount must be a
 *          valid amount in cents for any money amount.
 *        | isValidAmountInCents(getAmountInCents())
 * @invar   The currency of each money amount must be a valid
 *          currency for any money amount.
 *        | isValidCurrency(getCurrency())
 * 
 * @version 2.0
 * @author  Eric Steegmans
 */
public class MoneyAmount {

	/**
	 * Initialize this new money amount with given amount in cents
	 * and given currency.
	 * 
	 * @param  amountInCents
	 *         The amount in cents for this new money amount.
	 * @param  currency
	 *         The currency for this new money amount.
	 * @effect The amount in cents of this new money amount is
	 *         set to the given amount in cents.
	 *       | setAmountInCents(amountInCents)
	 * @effect The currency of this new money amount is set
	 *         to the given currency.
	 *       | setCurrency(currency)
	 */
	@Raw
	public MoneyAmount(BigInteger amountInCents, Currency currency)
			throws IllegalArgumentException {
		setAmountInCents(amountInCents);
		setCurrency(currency);
	}

	/**
	 * Initialize this new money amount to 0 Eurocents.
	 * 
	 * @effect This new money amount is initialized with 0 as its
	 *         amount in cents, and with Currency.EUR as its
	 *         currency.
	 *       | this(BigInteger.ZERO,Currency.EUR)
	 */
	@Raw
	public MoneyAmount() {
		this(BigInteger.ZERO, Currency.EUR);
	}

	/**
	 * Return the amount in cents of this money amount.
	 * 
	 * @Note   We use a big integer at this point, to avoid problems
	 *         of overflow in computations with money amounts.
	 */
	@Basic
	@Raw
	public BigInteger getAmountInCents() {
		return this.amountInCents;
	}

	/**
	 * Check whether the given amount is a valid amount in cents
	 * for any money amount.
	 * 
	 * @param  amount
	 *         The amount to be checked.
	 * @return True if the given amount is effective; false otherwise.
	 *       | result == (amount != null)
	 */
	public static boolean isValidAmountInCents(BigInteger amount) {
		return amount != null;
	}

	/**
	 * Set the amount in cents for this money amount to the given amount
	 * in cents.
	 * 
	 * @param  amountInCents
	 *         The new amount in cents for this money amount.
	 * @post   The amount in cents for this money amount is equal to the
	 *         given amount in cents.
	 *       | new.getAmountInCents().equals(amountInCents)
	 * @throws IllegalArgumentException
	 *         The given amount in cents is not a valid amount in
	 *         cents for any money amount.
	 *       | ! isValidAmountInCents(amountInCents)
	 */
	@Raw
	public void setAmountInCents(BigInteger amountInCents)
			throws IllegalArgumentException {
		if (!isValidAmountInCents(amountInCents))
			throw new IllegalArgumentException("Invalid amount in cents!");
		this.amountInCents = amountInCents;
	}

	/**
	 * Variable referencing the amount in cents involved in
	 * this money amount.
	 */
	private BigInteger amountInCents;

	/**
	 * Return the currency of this money amount.
	 */
	@Basic
	@Raw
	public Currency getCurrency() {
		return this.currency;
	}

	/**
	 * Check whether the given currency is a valid currency
	 * for any money amount.
	 * 
	 * @param  currency
	 *         The currency to be checked.
	 * @return True if and only if the given currency is effective.
	 *       | result == (currency != null)
	 */
	public static boolean isValidCurrency(Currency currency) {
		return currency != null;
	}

	/**
	 * Set the currency for this money amount to the given currency.
	 * 
	 * @param  currency
	 *         The new currency for this money amount.
	 * @post   The currency for this money amount is the same as the
	 *         given currency.
	 *       | new.getCurrency() == currency
	 * @throws IllegalArgumentException("Invalid currency!")
	 *         The given currency is not a valid currency for any
	 *         money amount.
	 * 	     | ! isValidCurrency(currency)
	 */
	@Raw
	public void setCurrency(Currency currency) throws IllegalArgumentException {
		if (!isValidCurrency(currency))
			throw new IllegalArgumentException("Invalid currency!");
		this.currency = currency;
	}

	/**
	 * Variable referencing the currency of this money amount.
	 */
	private Currency currency;

	/**
	 * Change the value of this money amount to the given money
	 * amount.
	 * 
	 * @param  target
	 *         The money amount to be registered. 
	 * @effect The amount in cents for this money amount is set to the
	 *         amount in cents of the given money amount.
	 *       | setAmountInCents(target.getAmountInCents())
	 * @effect The currency of this money amount is set to the
	 *         currency of the given money amount.
	 *       | setCurrency(target.getCurrency())
	 * @throws IllegalArgumentException
	 *         The given money amount is not effective.
	 *       | target == null
	 */
	public void changeValue(MoneyAmount target) throws IllegalArgumentException {
		if (target == null)
			throw new IllegalArgumentException("Non-effective money amount!");
		setAmountInCents(target.getAmountInCents());
		setCurrency(target.getCurrency());
	}

	/**
	 * Return a new money amount expressed in the given currency and
	 * with the same value as this money amount.
	 * 
	 * @param  currency
	 *         The currency to convert to.
	 * @return The resulting money amount is effective and different from
	 *         this money amount.
	 *       | (result != null) && (result != this)
	 * @return The currency of the resulting money amount is the same as the
	 *         given currency.
	 *       | result.getCurrency() == currency
	 * @return The amount in cents of the resulting money amount is equal to
	 *         the amount in cents of this money amount, multiplied with the
	 *         conversion rate of the currency of this money amount into the
	 *         given currency.
	 *       | let
	 *       |   conversion = BigInteger.valueOf(getCurrency().toCurrency(currency))
	 *       | in
	 *       |   result.getAmountInCents().equals(
	 *       |     getAmountInCents().multiply(conversion).
	 *       |           divide(BigInteger.valueOf(10000000)))
	 * @throws IllegalArgumentException
	 *         The given currency is not a valid currency for
	 *         a money amount.
	 *       | ! isValidCurrency(currency)
	 */
	public MoneyAmount toCurrency(Currency currency)
			throws IllegalArgumentException {
		if (!isValidCurrency(currency))
			throw new IllegalArgumentException("Illegal currency!");
		BigDecimal conversion = getCurrency().toCurrency(currency);
		BigDecimal amount = new BigDecimal(getAmountInCents());
		amount = amount.multiply(conversion);
		return new MoneyAmount(amount.toBigInteger(), currency);
	}

	/**
	 * Check whether this money amount is negative.
	 * 
	 * @return True if and only if the amount in cents of this
	 *         money amount is negative.
	 *       | result == (getAmountInCents().signum() == -1)
	 */
	public boolean isNegative() {
		return (getAmountInCents().signum() == -1);
	}

	/**
	 * Add the given money amount to this money amount.
	 * 
	 * @param  other
	 *         The money amount to add.
	 * @effect If the other money amount is expressed in another currency
	 *         than this money amount, the other money amount expressed
	 *         in the currency of this money amount is added to this
	 *         money amount.
	 *       | if (this.getCurrency() != other.getCurrency())
	 *       |   then this.add(other.toCurrency(this.getCurrency())))
	 * @effect If the other money amount is expressed in the same currency
	 *         as this money amount, the amount in cents of this money
	 *         amount is set to the old amount in cents of this money
	 *         amount incremented with the amount in cents of the
	 *         other money amount
	 *       | if (this.getCurrency() == other.getCurrency())
	 *       |  then this.setAmountInCents
	 *       |         (getAmountInCents().add(other.getAmountInCents()))
	 * @throws IllegalArgumentException
	 *         The other money amount is not effective.
	 *       | other == null
	 */
	public void add(MoneyAmount other) throws IllegalArgumentException {
		if (other == null)
			throw new IllegalArgumentException("Non-effective money amount!");
		if (this.getCurrency() != other.getCurrency())
			add(other.toCurrency(this.getCurrency()));
		else {
			BigInteger otherAmount = other.getAmountInCents();
			BigInteger newAmount = getAmountInCents().add(otherAmount);
			setAmountInCents(newAmount);
		}
	}

	/**
	 * Return a new money amount that is identical to this money amount.
	 * 
	 * @return The resulting money amount is a new effective money amount,
	 *         with the same amount in cents and the same currency as this
	 *         money amount.
	 *       | (result != null) && (result != this) &&
	 *       | (result.getAmountInCents() == this.getAmountInCents()) &&
	 *       | (result.getCurrency() == this.getCurrency())
	 * @note   In a later version, this method is best replaced by a redefinition
	 *         of the predefined method 'clone', inherited from the root class
	 *         Object. That redefinition requires knowledge about inheritance
	 *         from classes and interfaces, that has not been covered at this
	 *         point.
	 */
	public MoneyAmount getCopy() {
		return new MoneyAmount(getAmountInCents(), getCurrency());
	}

	/**
	 * Check whether this money amount is identical to the
	 * given money amount.
	 * 
	 * @param  other
	 *         The money amount to compare with.
	 * @return True if and only if (1) the given money amount is
	 *         effective, (2) the amount in cents of this money
	 *         amount is equal to the amount in cents of the other
	 *         money amount, and (3) the currency of this money
	 *         amount is the same as the currency of the other
	 *         money amount.
	 *       | result ==
	 *       |   (other != null) &&
	 *       |   (getAmountInCents().equals(other.getAmountInCents())) &&
	 *       |   (getCurrency() == other.getCurrency())
	 */
	public boolean isIdenticalTo(MoneyAmount other) {
		return (other != null)
				&& (getAmountInCents().equals(other.getAmountInCents()))
				&& (getCurrency() == other.getCurrency());
	}

	/**
	 * Return a textual representation of this money amount.
	 * 
	 * @return An effective string consisting of the textual representation
	 *         of the amount in cents of this money, followed by the textual
	 *         representation of the currency of this money amount, separated
	 *         by a space and enclosed in square brackets.
	 *       | result.equals("[" + getAmountInCents() + " " + getCurrency() + "]")
	 */
	public String toString() {
		return "[" + getAmountInCents() + " " + getCurrency() + "]";
	}

}