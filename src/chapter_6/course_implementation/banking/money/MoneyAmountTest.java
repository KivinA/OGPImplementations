package chapter_6.course_implementation.banking.money;

import static org.junit.Assert.*;
import java.math.BigInteger;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class MoneyAmountTest {

	private MoneyAmount someAmount, negativeAmount, amount_100_EUR,
			otherAmount_100_EUR, amount_200_EUR, amount_100_USD,
			amount_200_USD;

	@Before
	public void setUp() throws Exception {
		someAmount = new MoneyAmount(BigInteger.valueOf(1005), Currency.USD);
		negativeAmount = new MoneyAmount(BigInteger.valueOf(-100), Currency.EUR);
		amount_100_EUR = new MoneyAmount(BigInteger.valueOf(100), Currency.EUR);
		otherAmount_100_EUR = new MoneyAmount(BigInteger.valueOf(100),
				Currency.EUR);
		amount_200_EUR = new MoneyAmount(BigInteger.valueOf(200), Currency.EUR);
		amount_100_USD = new MoneyAmount(BigInteger.valueOf(100), Currency.USD);
		amount_200_USD = new MoneyAmount(BigInteger.valueOf(200), Currency.USD);
	}

	@Test
	public void extendedConstructor$LegalCase() {
		BigInteger amountInCents = BigInteger.valueOf(100);
		MoneyAmount amount = new MoneyAmount(amountInCents, Currency.EUR);
		assertEquals(amountInCents, amount.getAmountInCents());
		assertSame(Currency.EUR, amount.getCurrency());
	}

	@Test(expected = IllegalArgumentException.class)
	public void extendedConstructor$IllegalAmountInCents()
			throws IllegalArgumentException {
		new MoneyAmount(null, Currency.EUR);
		fail("Exception Expected!");
	}

	@Test(expected = IllegalArgumentException.class)
	public void extendedConstructor$IllegalCurrency()
			throws IllegalArgumentException {
		new MoneyAmount(BigInteger.valueOf(100), null);
		fail("Exception Expected!");
	}

	@Test
	public void defaultConstructor$SingleCase() {
		MoneyAmount amount = new MoneyAmount();
		assertEquals(BigInteger.ZERO, amount.getAmountInCents());
		assertSame(Currency.EUR, amount.getCurrency());
	}

	@Test
	public void isValidAmountInCents$TrueCase() {
		assertTrue(MoneyAmount.isValidAmountInCents(BigInteger.valueOf(0)));
	}

	@Test
	public void isValidAmountInCents$FalseCase() {
		assertFalse(MoneyAmount.isValidAmountInCents(null));
	}

	@Test
	public void setAmountInCents$LegalCase() {
		BigInteger newAmount = new BigInteger("100");
		someAmount.setAmountInCents(newAmount);
		assertEquals(newAmount, someAmount.getAmountInCents());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setAmountInCents$IllegalCase() throws IllegalArgumentException {
		someAmount.setAmountInCents(null);
		fail("Exception Expected!");
	}

	@Test
	public void isValidCurrency$TrueCase() {
		assertTrue(MoneyAmount.isValidCurrency(Currency.EUR));
	}

	@Test
	public void isValidCurrency$FalseCase() {
		assertFalse(MoneyAmount.isValidCurrency(null));
	}

	@Test
	public void setCurrency$LegalCase() {
		someAmount.setCurrency(Currency.EUR);
		assertEquals(Currency.EUR, someAmount.getCurrency());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setCurrency$IllegalCase() throws IllegalArgumentException {
		someAmount.setCurrency(null);
	}

	@Test
	public void changeValue$LegalCase() {
		someAmount.changeValue(amount_200_USD);
		assertEquals(amount_200_USD.getAmountInCents(), someAmount
				.getAmountInCents());
		assertEquals(amount_200_USD.getCurrency(), someAmount.getCurrency());
	}

	@Test(expected = IllegalArgumentException.class)
	public void changeValue$NonEffectiveAmount()
			throws IllegalArgumentException {
		someAmount.changeValue(null);
	}

	@Test
	public void toCurrency$Euro2USD() {
		MoneyAmount amountInUSD = amount_100_EUR.toCurrency(Currency.USD);
		assertNotNull(amountInUSD);
		TestCase.assertNotSame(amount_100_EUR, amountInUSD);
		assertEquals(141, amountInUSD.getAmountInCents().intValue());
		assertEquals(Currency.USD, amountInUSD.getCurrency());
	}

	@Test
	public void toCurrency$USD2Euro() {
		MoneyAmount amountInEuro = amount_100_USD.toCurrency(Currency.EUR);
		assertNotNull(amountInEuro);
		TestCase.assertNotSame(amountInEuro, amount_100_USD);
		assertEquals(70, amountInEuro.getAmountInCents().intValue());
		assertEquals(Currency.EUR, amountInEuro.getCurrency());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalCase() throws IllegalArgumentException {
		someAmount.toCurrency(null);
	}

	@Test
	public void isNegative$TrueCase() {
		assertTrue(negativeAmount.isNegative());
	}

	@Test
	public void isNegative$FalseCase() {
		assertFalse(amount_100_EUR.isNegative());
	}

	@Test
	public void add$SameCurrencies() {
		MoneyAmount theAmount = amount_100_EUR;
		theAmount.add(amount_200_EUR);
		assertEquals(BigInteger.valueOf(300), theAmount.getAmountInCents());
	}

	@Test
	public void add$DifferentCurrencies() {
		MoneyAmount theAmount = amount_100_EUR;
		MoneyAmount amount_100USD_EUR = amount_100_USD.toCurrency(Currency.EUR);
		BigInteger expecteAmountInCents = theAmount.getAmountInCents().add(
				amount_100USD_EUR.getAmountInCents());
		amount_100_EUR.add(amount_100_USD);
		assertEquals(expecteAmountInCents, amount_100_EUR.getAmountInCents());
	}

	@Test(expected = IllegalArgumentException.class)
	public void add$IllegalCase() throws IllegalArgumentException {
		someAmount.add(null);
	}

	@Test
	public void getCopy$SingleCase() {
		MoneyAmount clone = amount_100_EUR.getCopy();
		assertNotNull(clone);
		assertNotSame(clone, amount_100_EUR);
		assertSame(amount_100_EUR.getAmountInCents(), clone.getAmountInCents());
		assertSame(amount_100_EUR.getCurrency(), clone.getCurrency());
	}

	@Test
	public void isIdenticalTo$TrueCase() {
		assertTrue(amount_100_EUR.isIdenticalTo(otherAmount_100_EUR));
	}

	@Test
	public void isIdenticalTo$NonEffectiveAmount() {
		assertFalse(amount_100_EUR.isIdenticalTo(null));
	}

	@Test
	public void isIdenticalTo$DifferentAmountInCents() {
		assertFalse(amount_100_EUR.isIdenticalTo(amount_200_EUR));
	}

	@Test
	public void isIdenticalTo$DifferentCurrencies() {
		assertFalse(amount_100_EUR.isIdenticalTo(amount_100_USD));
	}

	@Test
	public void testSingleCase() {
		assertEquals("[100 EUR]", amount_100_EUR.toString());
	}

}
