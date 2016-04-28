package chapter_4.book_implementation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import be.kuleuven.cs.som.annotate.*;

/**
 * An enumeration introducing different currencies used to express amounts of money.
 * 
 * @author 	Kevin Algoet & Eric Steegmans
 * @version	4.0
 * 
 * @note	In its current form, the class only supports the Euro, the USD and the Japanase Yen.
 * @note	Based on the code found in the book Object Oriented Programming with Java by Eric Steegmans.
 */
@Value
enum Currency {
	EUR('€'), USD('$'), JPY('\u00a5');
	
	/**
	 * Initialize this Currency with the given symbol.
	 * 
	 * @param 	symbol
	 * 			The symbol for this new Currency.
	 * @post	The symbol for this new Currency is equal to the given symbol.
	 * 			| new.getSymbol() == symbol
	 */
	private Currency(char symbol)
	{
		this.symbol = symbol;
	}
	
	/**
	 * Return the symbol for this Currency.
	 */
	@Basic @Raw @Immutable
	public char getSymbol()
	{
		return this.symbol;
	}
	
	/**
	 * Variable storing the symbol for this Currency.
	 */
	private final char symbol;
	
	/**
	 * Retuirn the value of 1 unit of this Currency in the other Currency.
	 * 
	 * @param 	other
	 * 			The Currency to convert to.
	 * @return	The resulting exchange rate is positive.
	 * 			| result.signum() == 1
	 * @return	If this Currency is the same as the other Currency, BigDecimal.ONE is returned.
	 * 			| if (this == other)
	 * 			|	then result == BigDecimal.ONE)
	 * @return	If this Currency isn't the same as the other Currency, the resulting exchange rate has the 
	 * 			precision as established by the Currency context.
	 * 			| if (this != other)
	 * 			|	then result.precision() == currencyContext.getPrecision()
	 * @return	The resulting exchange rate is the inverse of the exchange rate from the other Currency to this Currency.
	 * 			| result.equals(BigDecimal.ONE.divide(other.toCurrency(this).currencyContext))
	 * @throws	IllegalArgumentException
	 * 			The given Currency isn't effective.
	 * 			| (other == null)
	 */
	public BigDecimal toCurrency(Currency other) throws IllegalArgumentException
	{
		if (other == null)
			throw new IllegalArgumentException("Non effective currency!");
		if (exchangeRates[ordinal()][other.ordinal()] == null)
			exchangeRates[ordinal()][other.ordinal()] = BigDecimal.ONE.divide(exchangeRates[other.ordinal()][ordinal()], currencyContext);
		return exchangeRates[ordinal()][other.ordinal()];
	}
	
	/**
	 * Variable referencing a two-dimensional array registering exchange rates between Currencies. The first level is indexed by the ordinal number
	 * of the Currency to convert from. The ordinal numver to convert to is used to index the second level.
	 */
	private static BigDecimal[][] exchangeRates = new BigDecimal[3][3];
	
	static
	{
		/*
		 * Initialization of the upper part of the exchange table.
		 * Other rates are computed and registered the first time they are queried.
		 */
		exchangeRates[EUR.ordinal()][EUR.ordinal()] = BigDecimal.ONE;
		exchangeRates[EUR.ordinal()][USD.ordinal()] = new BigDecimal(BigInteger.valueOf(141880), 5);
		exchangeRates[EUR.ordinal()][JPY.ordinal()] = new BigDecimal(BigInteger.valueOf(136712), 3);
		exchangeRates[USD.ordinal()][USD.ordinal()] = BigDecimal.ONE;
		exchangeRates[USD.ordinal()][JPY.ordinal()] = new BigDecimal(BigInteger.valueOf(963577), 4);
		exchangeRates[JPY.ordinal()][JPY.ordinal()] = BigDecimal.ONE;
	}
	
	/**
	 * Variable referencing the mathematical context used in Currency arithemtic.
	 * 
	 * @return	The Currecy context has a precision of 6 digits.
	 * 			| currencyContext.getPrecision() == 6
	 * @return	The Currency context uses the rounding mode HALF_DOWN.
	 * 			| currencyContext.getRoundingMode() == RoundingMode.HALF_DOWN
	 */
	public static final MathContext currencyContext = new MathContext(6, RoundingMode.HALF_DOWN);
}