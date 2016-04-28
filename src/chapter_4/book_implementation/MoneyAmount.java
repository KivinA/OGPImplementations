package chapter_4.book_implementation;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of Money Amounts involving a numeral and a Currency.
 * 
 * @author 	Kevin Algoet & Eric Steegmans
 * @version	4.0
 * 
 * @invar	The numeral of each Money Amount must be a valid numeral.
 * 			| isValidNumeral(getNumeral())
 * @invar	The curreny of each Money Amount must be a valid currency.
 * 			| isValidCurrency(getCurrency())
 * 
 * @note	Based on the code found in the book Object Oriented Programming with Java by Eric Steegmans.
 */
@Value
class MoneyAmount implements Comparable<MoneyAmount>{

	/**
	 * Initialize this new Money Amount with given numeral and given currency.
	 * 
	 * @param 	numeral
	 * 			The numeral for this new Money Amount.
	 * @param 	currency
	 * 			The currency for this new Money Amount.
	 * @post	The numeral for this new Money Amount is equal to the given numeral rounded (using half down) to a 
	 * 			decimal number with 2 fractional digits.
	 * 			| let roundedNumeral = numeral.round(getContextForScale2(numeral))
	 * 			| in new.getNumeral().equals(roundedNumeral)
	 * @post	The currency for this new Money Amount is the same as the given Currency.
	 * 			| new.getCurrency() == currency
	 * @throws	IllegalArgumentException
	 * 			The given numeral is not effective.
	 * 			| numeral == null
	 * @throws	IllegalArgumentException
	 * 			The given Currency isn't a valid Currency for any Money Amount.
	 * 			| ! isValidCurrency()
	 */
	@Raw
	public MoneyAmount(BigDecimal numeral, Currency currency) throws IllegalArgumentException
	{
		if (numeral == null)
			throw new IllegalArgumentException("Non-effective numeral.");
		if (!isValidCurrency(currency))
			throw new IllegalArgumentException("Invalid Currency.");
		if (numeral.scale() != 2)
			numeral = numeral.round(getContextForScale2(numeral));
		this.numeral = numeral;
		this.currency = currency;
	}
	
	/**
	 * Initialize this new Money Amount with the given numeral and Currency EUR.
	 * 
	 * @param 	numeral
	 * 			The numeral for this new Money Amount.
	 * @effect	This new Money Amount is initialized with the given numeral and the Currency EUR.
	 * 			| this(numeral, Currency.EUR)
	 */
	@Raw
	public MoneyAmount(BigDecimal numeral)
	{
		this(numeral, Currency.EUR);
	}
	
	/**
	 * Variable referencing a Money Amount of 0.00 EUR.
	 * 
	 * @return	The Money Amount EUR_0 is equal to a Money Amount initialized with numeral BigDecimal.ZERO and with currency
	 * 			Currency.EUR.
	 * 			| EUR_0.equals(new MoneyAmount(BigDecimal.ZERO, Currency.EUR)
	 */
	public final static MoneyAmount EUR_0 = new MoneyAmount(BigDecimal.ZERO, Currency.EUR);
	
	/**
	 * Variable referencing a Money Amount of 1.00 EUR.
	 * 
	 * @return	The Money Amount EUR_1 is equal to a Money Amount initialized with numeral BigDecimal.ONE and with currency
	 * 			Currency.EUR.
	 * 			| EUR_1.equals(new MoneyAmount(BigDecimal.ONE, Currency.EUR)
	 */
	public final static MoneyAmount EUR_1 = new MoneyAmount(BigDecimal.ONE, Currency.EUR);
	
	/**
	 * Return the numeral of this Money Amount.
	 */
	@Basic @Raw @Immutable
	public BigDecimal getNumeral()
	{
		return this.numeral;
	}
	
	/**
	 * Check whether the given numeral is a valid numeral for any Money Amount.
	 * 
	 * @param 	numeral
	 * 			The numeral to check.
	 * @return	True if and only is the given numeral is effective and if that numeral has a scale of 2.
	 * 			| result == ( (numeral != null) && (numeral.scale() == 2) )
	 */
	public static boolean isValidNumeral(BigDecimal numeral)
	{
		return (numeral != null) && (numeral.scale() == 2);
	}
	
	/**
	 * Variable referencing the numeral of this Money Amount.
	 */
	private final BigDecimal numeral;
	
	/**
	 * Return the Currency of this Money Amount.
	 */
	@Basic @Raw @Immutable
	public Currency getCurrency()
	{
		return this.currency;
	}
	
	/**
	 * Check whether the given Currency is a valid Currency for any Money Amount.
	 * 
	 * @param 	currency
	 * 			The Currency to check.
	 * @return	True if and only if the given Currency is effective.
	 * 			| result == (currency != null)
	 */
	public static boolean isValidCurrency(Currency currency)
	{
		return currency != null;
	}
	
	/**
	 * Return a Money Amount that has the same value as this Money Amount expressed in the given Currency.
	 * 
	 * @param	currency
	 * 			The Currency to converty to.
	 * @return	The resulting Money Amount has the given Currency as its Currency.
	 * 			| result.getCurrency() == currency
	 * @return	The numeral of the resulting Money Amount is equal to the numeral of this Money Amount multiplied
	 * 			with the exchange rate from the Currency of this Money Amount to the given Currency rounded half-down
	 * 			to a scale of 2.
	 * 			| let 
	 * 			|	exchangeRate = this.getCurrency().toCurrency(currency),
	 * 			|	numeralInCurrency = this.getNumeral().multiply(exchangeRate)
	 * 			| in
	 * 			|	result.getNumeral().equals(numeralInCurrency.round(getContextForScale2(numeralInCurrency))
	 * @throws	IllegalArgumentException
	 * 			The given Currency isn't valid for any Money Amount.
	 * 			| !isValidCurrency(currency)
	 */
	public MoneyAmount toCurrency(Currency currency) throws IllegalArgumentException
	{
		if (!isValidCurrency(currency))
			throw new IllegalArgumentException("Non-effective currency.");
		if (this.getCurrency() == currency)
			return this;
		BigDecimal exchangeRate = getCurrency().toCurrency(currency);
		BigDecimal numeralInCurrency = getNumeral().multiply(exchangeRate);
		numeralInCurrency = numeralInCurrency.round(getContextForScale2(numeralInCurrency));
		return new MoneyAmount(numeralInCurrency, currency);
	}
	
	/**
	 * Variable refencing the Currency of this Money Amount. 
	 */
	private final Currency currency;
	
	/**
	 * Compute the negation of this Money Amount.
	 * 
	 * @return	The resulting Money Amount has the same Currency as this Money Amount.
	 * 			| result.getCurrency() == this.getCurrency()
	 * @return	The numeral of the resulting Money Amount is equal to the negation of the numeral of this Money Amount.
	 * 			| result.getNumeral().equals(this.getNumeral().negate())
	 */
	public MoneyAmount negate()
	{
		return new MoneyAmount(getNumeral().negate(), getCurrency());
	}
	
	/**
	 * Compute the product of this Money Amount with the given factor.
	 * 
	 * @param 	factor
	 * 			The factor to multiply with.
	 * @return	The resulting Money Amount has the same Currency as this Money Amount.
	 * 			| result.getCurrency() == this.getCurrency()
	 * @return	The numeral of the resulting Money Amount is equal to the product of the numeral of this 
	 * 			Money Amount and the given factor.
	 * 			| result.getNumeral().equals(this.getNumeral().multiply(new BigDecimal(factor)))
	 */
	public MoneyAmount times(long factor)
	{
		BigDecimal factorDec = new BigDecimal(factor);
		return new MoneyAmount(getNumeral().multiply(factorDec), getCurrency());
	}
	
	/**
	 * Compute the sum of this Money Amount and the other Money Amount.
	 * 
	 * @param 	other
	 * 			The other Money Amount to add.
	 * @return	The resulting Money Amount has the same Currency as this Money Amount.
	 * 			| result.getCurrenct() == this.getCurrency()
	 * @return	If the two Money Amounts use the same Currency, the numeral of the resulting Money Amount
	 * 			is equal to the sum of the numerals of both Money Amounts.
	 * 			| if (this.getCurrency() == other.getCurrency()
	 * 			|	then result.getNumeral().equals(this.getNumeral().add(other.getNumeral()))
	 * @return	If the two Money Amounts use different Currencies, the resulting Money Amount is equal to the sum
	 * 			of this Money Amount and the other Money Amount expressed in the Currency of this Money Amount.
	 * 			| if (this.getCurrency() != other.getCurrency())
	 * 			|	then result.equals(this.add(other.toCurrency(getCurrency())
	 * @throws 	IllegalArgumentException
	 * 			The other Money Amount isn't effective.
	 * 			| other == null
	 */
	public MoneyAmount add(MoneyAmount other) throws IllegalArgumentException
	{
		if (other == null)
			throw new IllegalArgumentException("Non-effective Money Amount.");
		if (getCurrency() == other.getCurrency())
			return new MoneyAmount(getNumeral().add(other.getNumeral()), getCurrency());
		return add(other.toCurrency(getCurrency()));
	}
	
	/**
	 * Compute the substraction of the other Money Amount from this Money Amount.
	 * 
	 * @param 	other
	 * 			The Money Amount to substract.
	 * @return	The resulting Money amount is equal to the sum of this Money Amount and the negation of the other Money Amount.
	 * 			| result.equals(this.add(other.negate()))
	 * @throws	IllegalArgumentException
	 * 			The other Money Amount isn't effective.
	 * 			| other == null
	 */
	public MoneyAmount subtract(MoneyAmount other) throws IllegalArgumentException
	{
		if (other == null)
			throw new IllegalArgumentException("Non-effective Money Amount");
		if (getCurrency() == other.getCurrency())
			return new MoneyAmount(getNumeral().subtract(other.getNumeral()), getCurrency());
		return subtract(other.toCurrency(getCurrency()));
	}
	
	/**
	 * Compare this Money Amount with the other Money Amount.
	 * 
	 * @param	other
	 * 			The other Money Amount to compare with.
	 * @return	The result is equal to the comparison of the numeral of this Money Amount with the numeral of the other Money Amount.
	 * 			| result == getNumeral().compareTo(other.getNumeral())
	 * @throws	ClassCastException
	 * 			The other Money Amount isn't effective or this Money Amount and the given Money Amount use different Currencies.
	 * 			| ( (other == null) || (this.getCurrency() != other.getCurrency()) )
	 */
	@Override
	public int compareTo(MoneyAmount other) throws ClassCastException {
		if (other == null)
			throw new ClassCastException("Non-effective Money Amount.");
		if (getCurrency() != other.getCurrency())
			throw new ClassCastException("Incompatible Money Amounts.");
		return getNumeral().compareTo(other.getNumeral());
	}
	
	/**
	 * Return the signum of this Money Amount.
	 * 
	 * @return	The signum of the numeral of this Money Amount.
	 * 			| result == getNumeral().signum()
	 */
	public int signum()
	{
		return getNumeral().signum();
	}
	
	/**
	 * Check whether this Money Amount has the same value as the other Money Amount.
	 * 
	 * @param 	other
	 * 			The other Money Amount to compare with.
	 * @return	True if and only if this Money Amount is equals to the other Money Amount expressed in the currency of this Money Amount.
	 * 			| result == this.equals(other.toCurrency(getCurrency()))
	 * @throws	IllegalArgumentException
	 * 			The other Money Amount isn't effective.
	 * 			| other == null
	 */
	public boolean hasSameValueAs(MoneyAmount other) throws IllegalArgumentException
	{
		if (other == null)
			throw new IllegalArgumentException("Non-effective Money Amount");
		return this.equals(other.toCurrency(getCurrency()));
	}
	
	/**
	 * Check whether this Money Amount is equal to the given Object.
	 * 
	 * @return	True if and only if the given Object is effective, if this Money Amount and the given Object belong to the same class,
	 * 			and if this Money Amount and the other Object interpreted as a Money Amount have equal numerals and equal Currencies.
	 * 			| result == ( (other != null) && (this.getClass() == other.getClass()) 
	 * 			|				&& (this.getNumeral().equals((MoneyAmount other).getNumeral()))
	 * 			|				&& (this.getCurrency() == ((MoneyAmount other).getCurrency())) )
	 */
	@Override
	public boolean equals(Object other)
	{
		if (other == null)
			return false;
		if (getClass() != other.getClass())
			return false;
		MoneyAmount otherAmount = (MoneyAmount) other;
		return (this.getNumeral().equals(otherAmount.getNumeral()) && (getCurrency() == otherAmount.getCurrency()));
	}
	
	/**
	 * Return the hash code for this Money Amount.
	 */
	@Override
	public int hashCode()
	{
		return getNumeral().hashCode() + getCurrency().hashCode();
	}
	
	/**
	 * Return a textual representation of this Money Amount.
	 * 
	 * @return	A string consisting of the textual representation of the numeral of this Money Amount, followed by the textual representation
	 * 			of its Currency, seperated by a space and eclosed in square brackets.
	 * 			| results.equals("[" + getNumeral().toString() + " " + getCurrency().toString() + "]")
	 */
	@Override
	public String toString()
	{
		return "[" + getNumeral().toString() + " " + getCurrency().toString() + "]";
	}
	
	/**
	 * Return a mathematical context to round the given big decimal to 2 fractional digits.
	 * 
	 * @param 	value
	 * 			The value to compute a mathematical context for.
	 * @pre		The given value must be effective.
	 * 			| value != null
	 * @return	The precision of the resulting mathematical context is equal to the precision of the given value diminished with its scale and
	 * 			incremented by 2.
	 * 			| result.getPrecision() == value.precision() - value.scale() + 2
	 * @return	The resulting mathematical context uses HALF_DOWN as its rounding mode.
	 * 			| result.getRoundingMode() == RoundingMode.HALF_DOWN
	 */
	@Model
	static MathContext getContextForScale2(BigDecimal value)
	{
		assert value != null : "Value must be effective!";
		return new MathContext(value.precision() - value.scale() + 2, RoundingMode.HALF_DOWN);
	}
}
