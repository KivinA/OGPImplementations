package chapter_4.book_implementation;

import java.math.BigDecimal;
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
	public MoneyAmount(BigDecimal numeral, Currency currency) {
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
	public final static public MoneyAmount EUR_0 = new MoneyAmount(BigDecimal.ZERO, Currency.EUR);
	
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
	public MoneyAmount toCurrency(Currency currency)
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
	
	
	
	@Override
	public int compareTo(MoneyAmount o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
