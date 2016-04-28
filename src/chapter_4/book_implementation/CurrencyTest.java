package chapter_4.book_implementation;

import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.*;

/**
 * A class collecting tests for the class {@link Currency};
 * 
 * @author 	Kevin Algoet & Eric Steegmans
 * @version	4.0
 * 
 * @note	Based on the code found in the book Object Oriented Programming with Java by Eric Steegmans.
 */
public class CurrencyTest {
	
	@Test
	public void toCurrency_SameCurrency()
	{
		BigDecimal result = Currency.EUR.toCurrency(Currency.EUR);
		assertEquals(BigDecimal.ONE, result);
	}
	
	@Test
	public void toCurrency_DifferentCurrency()
	{
		BigDecimal result = Currency.EUR.toCurrency(Currency.USD);
		assertTrue(result.signum() == 1);
		assertSame(Currency.currencyContext.getPrecision(), result.precision());
		BigDecimal inverse = Currency.USD.toCurrency(Currency.EUR);
		assertEquals(BigDecimal.ONE.divide(inverse, Currency.currencyContext), result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void toCurrency_NonEffectiveCurrency() throws Exception
	{
		Currency.EUR.toCurrency(null);
	}
	
	@Test
	public void currencyContext_SingleCase()
	{
		assertEquals(6, Currency.currencyContext.getPrecision());
		assertSame(RoundingMode.HALF_DOWN, Currency.currencyContext.getRoundingMode());
	}
}
