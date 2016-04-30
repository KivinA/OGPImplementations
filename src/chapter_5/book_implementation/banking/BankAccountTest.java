package chapter_5.book_implementation.banking;

import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.*;
import chapter_5.book_implementation.exceptions.*;
import chapter_5.book_implementation.money.*;
import chapter_5.book_implementation.state.Person;


/**
 * A class collecting tests for {@link BankAccount}.
 * 
 * @author	Kevin Algoet & Eric Steegmans
 * @version	5.0
 * 
 * @Note	Based on the code found in the book Object Oriented Programming with Java by Eric Steegmans.
 */
public class BankAccountTest {
	
	private BankAccount accountBalance0, accountBalance100, accountBalance500, accountUSD, accountWithBankCard;

	private static Person someAdult, someOtherAdult, someNonAdult;
	
	private static MoneyAmount EUR_50, EUR_100, EUR_M1000, USD_0;
	
	@Before
	public void setUpMutableFixture()
	{
		accountBalance0 = new BankAccount(1, someAdult, EUR_M1000, MoneyAmount.EUR_0, false);
		accountBalance100 = new BankAccount(1111111, someAdult, MoneyAmount.EUR_0, EUR_100, false);
		accountBalance500 = new BankAccount(2222222, someAdult, MoneyAmount.EUR_0, new MoneyAmount(new BigDecimal(500)), false);
		accountUSD = new BankAccount(2345, someAdult, USD_0, new MoneyAmount(BigDecimal.valueOf(200, 2), Currency.USD), false);
		accountWithBankCard = new BankAccount(3456, someAdult);
		new BankCard(accountWithBankCard, 1234);
	}
	
	@After
	public void tearDownMutableFixture()
	{
		accountBalance0.terminate();
		accountBalance100.terminate();
		accountBalance500.terminate();
		accountUSD.terminate();
		accountWithBankCard.terminate();
	}
	
	@BeforeClass
	public static void setUpImmutableFixture()
	{
		EUR_50 = new MoneyAmount(new BigDecimal(BigInteger.valueOf(50)));
		EUR_100 = new MoneyAmount(new BigDecimal(BigInteger.valueOf(100)));
		EUR_M1000 = new MoneyAmount(new BigDecimal(BigInteger.valueOf(-1000)));
		USD_0 = new MoneyAmount(BigDecimal.ZERO, Currency.USD);
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(1980, Calendar.MARCH, 4);
		someAdult = new Person(cal.getTime());
		cal.set(1978, Calendar.JANUARY, 28);
		someOtherAdult = new Person(cal.getTime());
		cal.set(2003, Calendar.SEPTEMBER, 11);
		someNonAdult = new Person(cal.getTime());
	}
	
	@Test
	public void extendedConstructor_LegalCase()
	{
		BankAccount theAccount = new BankAccount(12345, someAdult, MoneyAmount.EUR_0, MoneyAmount.EUR_1, false);
		assertEquals(12345, theAccount.getNumber());
		assertEquals(MoneyAmount.EUR_1, theAccount.getBalance());
		assertEquals(MoneyAmount.EUR_0, theAccount.getCreditLimit());
		assertFalse(theAccount.isBlocked());
		assertSame(someAdult, theAccount.getHolder());
		assertFalse(theAccount.hasBankCard());
		assertFalse(theAccount.isTerminated());
		theAccount.terminate();
	}
	
	@Test (expected = IllegalAmountException.class)
	public void extendedConstructor_IllegalCreditLimit() throws Exception
	{
		new BankAccount(12345, someAdult, MoneyAmount.EUR_1, EUR_100, false);
	}
	
	@Test (expected = IllegalAmountException.class)
	public void extendedConstructor_IllegalBalance() throws Exception
	{
		new BankAccount(12345, someAdult, MoneyAmount.EUR_0, null, false);
	}
	
	@Test (expected = IllegalAmountException.class)
	public void extendedConstructor_NonMatchingBalanceCreditLimit() throws Exception
	{
		new BankAccount(12345, someAdult, USD_0, EUR_100, false);
	}
	
	@Test (expected = IllegalAmountException.class)
	public void extendedConstructor_NonMatchingBalanceCreditLimit2() throws Exception
	{
		new BankAccount(12345, someAdult, MoneyAmount.EUR_0, MoneyAmount.EUR_0, false);
	}
	
	@Test (expected = IllegalNumberException.class)
	public void extendedConsructor_InvalidNumber() throws Exception
	{
		new BankAccount(-10, someAdult, MoneyAmount.EUR_0, EUR_100, false);
	}
	
	@Test (expected = IllegalHolderException.class)
	public void extendedConstructor_InvalidHolder() throws Exception
	{
		new BankAccount(1100, null, MoneyAmount.EUR_0, EUR_100, false);
	}
	
	@Test
	public void mediumConstructor_SingleCase()
	{
		BankAccount theAccount = new BankAccount(12345, someAdult);
		assertEquals(12345, theAccount.getNumber());
		assertEquals(MoneyAmount.EUR_0, theAccount.getBalance());
		assertEquals(EUR_M1000, theAccount.getCreditLimit());
		assertFalse(theAccount.isBlocked());
		assertSame(someAdult, theAccount.getHolder());
		assertFalse(theAccount.hasBankCard());
		assertFalse(theAccount.isTerminated());
		theAccount.terminate();
	}
	
	@Test
	public void terminate_NonTerminatedAccountWithBankCard()
	{
		BankCard oldCard = accountWithBankCard.getBankCard();
		accountWithBankCard.terminate();
		assertTrue(accountWithBankCard.isTerminated());
		assertFalse(accountWithBankCard.hasBankCard());
		assertTrue(oldCard.isTerminated());
	}
	
	@Test
	public void terminate_NonTerminatedAccountWithoutBankCard()
	{
		accountBalance0.terminate();
		assertTrue(accountBalance0.isTerminated());
		assertFalse(accountBalance0.hasBankCard());
	}
	
	@Test
	public void terminate_TerminatedAccount()
	{
		accountWithBankCard.terminate();
		accountWithBankCard.terminate();
		assertTrue(accountWithBankCard.isTerminated());
	}
	
	@Test
	public void isValidBankCode_LegalValue()
	{
		assertTrue(BankAccount.isValidBankCode(100));
	}
	
	@Test
	public void isValidBankCode_IllegalValue()
	{
		assertFalse(BankAccount.isValidBankCode(-1));
	}
	
	@Test
	public void canHaveAsNumber_LegalValue()
	{
		assertTrue(accountBalance100.canHaveAsNumber(123));
	}
	
	@Test
	public void canHaveAsNumber_OwnNumber()
	{
		assertTrue(accountBalance100.canHaveAsNumber(accountBalance100.getNumber()));
	}
	
	@Test
	public void canHaveAsNumber_NonPositiveValue()
	{
		assertFalse(accountBalance100.canHaveAsNumber(0));
	}
	
	@Test
	public void canHaveAsNumber_DuplicateValue()
	{
		assertFalse(accountBalance100.canHaveAsNumber(accountBalance500.getNumber()));
	}
	
	@Test
	public void isValidHolder_TrueCase()
	{
		assertTrue(BankAccount.isValidHolder(someAdult));
	}
	
	@Test
	public void isValidHolder_NonEffectiveHolder()
	{
		assertFalse(BankAccount.isValidHolder(null));
	}
	
	@Test
	public void isValidHolder_NonAdultHolder()
	{
		assertFalse(BankAccount.isValidHolder(someNonAdult));
	}
	
	@Test
	public void setHolder_LegalCase()
	{
		accountBalance0.setHolder(someOtherAdult);
		assertSame(someOtherAdult, accountBalance0.getHolder());
	}
	
	@Test (expected = IllegalHolderException.class)
	public void setHolder_IllegalCase() throws Exception
	{
		accountBalance0.setHolder(someNonAdult);
	}
	
	@Test
	public void isPossibleCreditLimit_TrueCase()
	{
		assertTrue(BankAccount.isPossibleCreditLimit(EUR_M1000));
	}
	
	@Test
	public void isPossibleCreditLimit_NonEffectiveAmount()
	{
		assertFalse(BankAccount.isPossibleCreditLimit(null));
	}
	
	@Test
	public void isPossibleCreditLimit_NegativeAmount()
	{
		assertFalse(BankAccount.isPossibleCreditLimit(MoneyAmount.EUR_1));
	}
	
	@Test
	public void canHaveAsCreditLimit_TrueCase()
	{
		assertTrue(accountBalance0.canHaveAsCreditLimit(EUR_M1000));
	}
	
	@Test
	public void canHaveAsCreditLimit_ImpossibleCreditLimit()
	{
		assertFalse(accountBalance0.canHaveAsCreditLimit(EUR_100));
	}
	
	@Test
	public void canHaveAsCreditLimit_ConflictWithBalance()
	{
		assertFalse(accountBalance0.canHaveAsCreditLimit(MoneyAmount.EUR_0));
	}
	
	@Test
	public void setCreditLimit_LegalCase()
	{
		MoneyAmount EUR_M23 = new MoneyAmount(BigDecimal.valueOf(-2350, 2));
		accountBalance0.setCreditLimit(EUR_M23);
		assertEquals(EUR_M23, accountBalance0.getCreditLimit());
	}
	
	@Test (expected = IllegalAmountException.class)
	public void setCreditLimit_IllegalCase() throws Exception
	{
		accountBalance0.setCreditLimit(EUR_100);
	}
	
	@Test
	public void isPossibleBalance_TrueCase()
	{
		assertTrue(BankAccount.isPossibleBalance(MoneyAmount.EUR_1));
	}
	
	@Test
	public void isPossibleBalance_FalsCase()
	{
		assertFalse(BankAccount.isPossibleBalance(null));
	}
	
	@Test
	public void canHaveAsBalance_TrueCase()
	{
		assertTrue(accountBalance0.canHaveAsBalance(EUR_100));
	}
	
	@Test
	public void canHaveAsBalance_ImpossibleBalance()
	{
		assertFalse(accountBalance0.canHaveAsBalance(null));
	}
	
	@Test
	public void canHaveAsBalance__ConflictWithCreditLimit()
	{
		accountBalance0.setCreditLimit(EUR_M1000);
		assertFalse(accountBalance0.canHaveAsBalance(new MoneyAmount(BigDecimal.valueOf(-200000, 2))));
	}
	
	@Test
	public void matchesBalanceCreditLimit_TrueCase()
	{
		assertTrue(BankAccount.matchesBalanceCreditLimit(EUR_100, EUR_50));
	}
	
	@Test
	public void matchesBalanceCreditLimit_NonEffectiveBalance()
	{
		assertFalse(BankAccount.matchesBalanceCreditLimit(null, EUR_M1000));
	}
	
	@Test
	public void matchesBalanceCreditLimit_NonEffectiveCreditLimit()
	{
		assertFalse(BankAccount.matchesBalanceCreditLimit(EUR_100, null));
	}
	
	@Test
	public void matchesBalanceCreditLimit_BalanceBelowLimit()
	{
		assertFalse(BankAccount.matchesBalanceCreditLimit(EUR_50, EUR_100));
	}
	
	@Test
	public void matchesBalanceCreditLimit_EqualValues()
	{
		assertFalse(BankAccount.matchesBalanceCreditLimit(EUR_50, EUR_50));
	}
	
	@Test
	public void hasHigherBalanceThanValue_TrueCase()
	{
		assertTrue(accountBalance0.hasHigherBalanceThan(EUR_M1000));
	}
	
	@Test
	public void hasHigherBalanceThanValue_BalanceLower()
	{
		assertFalse(accountBalance0.hasHigherBalanceThan(EUR_100));
	}
	
	@Test
	public void hasHigherBalanceThanValue_BalanceEqual()
	{
		assertFalse(accountBalance0.hasHigherBalanceThan(MoneyAmount.EUR_0));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void hasHigherBalanceThanValue_NonEffectiveAmount() throws Exception
	{
		accountBalance0.hasHigherBalanceThan((MoneyAmount) null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void hasHigherBalanceThanValue_DifferentCurrencies() throws Exception
	{
		accountBalance0.hasHigherBalanceThan(USD_0);
	}
	
	@Test
	public void hasHigherBalanceThanAccount_TrueCase()
	{
		assertTrue(accountBalance100.hasHigherBalanceThan(accountBalance0));
	}
	
	@Test
	public void hasHigherBalanceThanAccount_FalseCase()
	{
		assertFalse(accountBalance100.hasHigherBalanceThan(accountBalance500));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void hasHigherBalanceThanAccount_NonEffectiveCase() throws Exception
	{
		accountBalance100.hasHigherBalanceThan((BankAccount) null);
	}
	
	@Test
	public void canAcceptForDeposit_TrueCase()
	{
		assertTrue(accountBalance0.canAcceptForDeposit(EUR_100));
	}
	
	@Test
	public void canAcceptForDeposit_NonEffectiveAmount()
	{
		assertFalse(accountBalance0.canAcceptForDeposit(null));
	}
	
	@Test
	public void canAcceptForDeposit_NegativeAmount()
	{
		assertFalse(accountBalance0.canAcceptForDeposit(EUR_M1000));
	}
	
	@Test
	public void canAcceptForDeposit_ZeroAmount()
	{
		assertFalse(accountBalance100.canAcceptForDeposit(MoneyAmount.EUR_0));
	}
	
	@Test
	public void deposit_LegalCase()
	{
		accountBalance100.deposit(EUR_50);
		assertEquals(new MoneyAmount(new BigDecimal(150)), accountBalance100.getBalance());
	}
	
	@Test (expected = IllegalAmountException.class)
	public void deposit_UnacceptableAmount() throws Exception
	{
		accountBalance0.deposit(EUR_M1000);
	}
	
	@Test
	public void canAcceptForWithdraw_TrueCase()
	{
		assertTrue(accountBalance100.canAcceptForWithdraw(EUR_50));
	}
	
	@Test
	public void canAcceptForWithdraw_NonEffectiveAmount()
	{
		assertFalse(accountBalance100.canAcceptForWithdraw(null));
	}
	
	@Test
	public void canAcceptForWithdraw__NegativeAmount()
	{
		assertFalse(accountBalance0.canAcceptForWithdraw(EUR_M1000));
	}
	
	@Test
	public void canAcceptForWithdraw_ZeroAmount()
	{
		assertFalse(accountBalance100.canAcceptForWithdraw(MoneyAmount.EUR_0));
	}
	
	@Test
	public void canAcceptForWithdraw_BlockedAccount()
	{
		accountBalance100.block();
		assertFalse(accountBalance100.canAcceptForWithdraw(EUR_50));
	}
	
	@Test
	public void canAcceptForWithdraw_ValueBelowCreditLimit()
	{
		accountBalance0.setCreditLimit(EUR_M1000);
		assertFalse(accountBalance0.canAcceptForWithdraw(new MoneyAmount(new BigDecimal(1001))));
	}
	
	@Test
	public void withdraw_LegalCase()
	{
		accountBalance100.withdraw(EUR_50);
		assertEquals(EUR_50, accountBalance100.getBalance());
	}
	
	@Test (expected = IllegalAmountException.class)
	public void withdraw_UnacceptableAmount() throws Exception
	{
		accountBalance0.withdraw(null);
	}
	
	@Test
	public void transferTo_LegalCase()
	{
		accountBalance100.transferTo(EUR_50, accountBalance500);
		assertEquals(EUR_50, accountBalance100.getBalance());
		assertEquals(new MoneyAmount(new BigDecimal(550)), accountBalance500.getBalance());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void transferTo_NonEffectiveDestination() throws Exception
	{
		accountBalance100.transferTo(EUR_50, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void transferTo_SameDestination() throws Exception
	{
		accountBalance100.transferTo(EUR_50, accountBalance100);
	}
	
	@Test (expected = IllegalAmountException.class)
	public void transferTo_NonWithdrawableAmount() throws Exception
	{
		try
		{
			accountBalance0.setCreditLimit(EUR_M1000);
			accountBalance100.transferTo(new MoneyAmount(new BigDecimal(1500)), accountBalance500);
			fail(); // If we reach this piece of code, the test will automatically fail. We expect an exception to be thrown in this try part.
		}
		catch (IllegalAmountException exc)
		{
			assertEquals(EUR_100, accountBalance100.getBalance());
			assertEquals(new MoneyAmount(new BigDecimal(500)), accountBalance500.getBalance());
			throw exc;
		}
	}
	
	@Test
	public void setBlocked_SingleCase()
	{
		accountBalance0.setBlocked(true);
		assertTrue(accountBalance0.isBlocked());
	}
	
	@Test
	public void block_SingleCase()
	{
		accountBalance0.block();
		assertTrue(accountBalance0.isBlocked());
	}
	
	@Test
	public void unblock_SingleCase()
	{
		accountBalance0.unblock();
		assertFalse(accountBalance0.isBlocked());
	}
	
	@Test
	public void toString_SingleCase()
	{
		String result = accountBalance100.toString();
		assertTrue(result.contains("Bank Account"));
		assertTrue(result.contains(String.valueOf(accountBalance100.getNumber())));
	}
	
	@Test (expected = CloneNotSupportedException.class)
	public void clone_SingleCase() throws Exception
	{
		accountBalance0.clone();
	}
}