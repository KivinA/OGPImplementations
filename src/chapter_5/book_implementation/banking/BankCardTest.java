package chapter_5.book_implementation.banking;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.*;
import chapter_5.book_implementation.exceptions.*;
import chapter_5.book_implementation.money.MoneyAmount;
import chapter_5.book_implementation.state.Person;

public class BankCardTest {
	
	private BankCard someCard, terminateCard;
	
	private BankAccount someAccount, otherAccount;
	
	private static Person someAdult;
	
	@BeforeClass
	public static void setUpImmutableFixture() throws Exception
	{
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(1980, Calendar.MARCH, 4);
		someAdult = new Person(cal.getTime());
	}
	
	@Before
	public void setUpMutableFixture() throws Exception
	{
		someAccount = new BankAccount(2345, someAdult);
		otherAccount = new BankAccount(4567, someAdult);
		terminateCard = new BankCard(someAccount, 2468);
		terminateCard.terminate();
		someCard = new BankCard(someAccount, 7531);
	}
	
	@After
	public void tearDown() throws Exception
	{
		someAccount.terminate();
		otherAccount.terminate();
	}
	
	@Test
	public void constructor_SingleCase()
	{
		BankAccount theAccount = new BankAccount(56789, someAdult);
		BankCard theCard = new BankCard(theAccount, 3680);
		assertSame(theAccount, theCard.getAccount());
		assertSame(theCard, theAccount.getBankCard());
		assertTrue(theCard.hasAsPinCode(3680));
		assertFalse(theCard.isTerminated());
		theAccount.terminate();
	}
	
	@Test
	public void terminate_NonTerminatedCard()
	{
		someCard.terminate();
		assertTrue(someCard.isTerminated());
		assertNull(someCard.getAccount());
		assertFalse(someAccount.hasBankCard());
	}
	
	@Test
	public void terminate_TerminatedCard()
	{
		terminateCard.terminate();
		assertTrue(terminateCard.isTerminated());
		assertNull(terminateCard.getAccount());
	}
	
	@Test
	public void canHaveAsAccount_TerminatedCardTrueCase()
	{
		assertTrue(terminateCard.canHaveAsAccount(null));
	}
	
	@Test
	public void canHaveAsAccount_TerminatedCardEffectiveAccount()
	{
		assertFalse(terminateCard.canHaveAsAccount(someAccount));
	}
	
	@Test
	public void canHaveAsAccount_NonTerminatedCardTrueCase()
	{
		assertTrue(someCard.canHaveAsAccount(someAccount));
	}
	
	@Test
	public void canHaveAsAccount_NonTerminatedCardNonEffectiveAccount()
	{
		assertTrue(someCard.canHaveAsAccount(null));
	}
	
	@Test
	public void canHaveAsAccount_NonTerminatedCardTerminatedAccount()
	{
		BankAccount theAccount = new BankAccount(56789, someAdult);
		theAccount.terminate();
		assertFalse(someCard.canHaveAsAccount(theAccount));
	}
	
	@Test
	public void hasProperAccount_NonTerminatedCard()
	{
		assertTrue(someCard.hasProperAccount());
	}
	
	@Test
	public void hasProperAccount_TerminatedCard()
	{
		assertTrue(terminateCard.hasProperAccount());
	}
	
	@Test
	public void transferTo_CardAttachedToOtherAccount()
	{
		BankAccount formerAccount = someCard.getAccount();
		someCard.transferTo(otherAccount);
		assertSame(otherAccount,someCard.getAccount());
		assertSame(someCard,otherAccount.getBankCard());
		assertFalse(formerAccount.hasBankCard());
		formerAccount.terminate();
	}
	
	@Test
	public void transferTo_CardAttachedToSameAccount()
	{
		BankAccount formerAccount = someCard.getAccount();
		someCard.transferTo(formerAccount);
		assertSame(formerAccount,someCard.getAccount());
		assertSame(someCard,formerAccount.getBankCard());
	}
	
	@Test (expected = IllegalStateException.class)
	public void transferTo_TerminatedCard() throws Exception
	{
		terminateCard.transferTo(otherAccount);
	}
	
	@Test (expected = IllegalAccountException.class)
	public void transferTo_AccountWithOtherBankCard() throws Exception
	{
		new BankCard(otherAccount, 9876);
		someCard.transferTo(otherAccount);
	}
	
	@Test
	public void isValidPinCode_TrueCase()
	{
		assertTrue(BankCard.isValidPinCode(1));
	}
	
	@Test
	public void isValidPincode_NegativeCode()
	{
		assertFalse(BankCard.isValidPinCode(-1));
	}
	
	@Test
	public void isValidPinCode_CodeTooLarge()
	{
		assertFalse(BankCard.isValidPinCode(10000));
	}
	
	@Test
	public void setPinCode_LegalCase()
	{
		someCard.setPinCode(5678);
		someCard.setPinCode(9999);
		assertTrue(someCard.hasAsPinCode(9999));
	}
	
	@Test (expected = IllegalPinCodeException.class)
	public void setPinCode_IllegalCase() throws Exception
	{
		someCard.setPinCode(10000);
	}
	
	@Test
	public void withdraw_LegalCase()
	{
		someCard.getAccount().deposit(MoneyAmount.EUR_1);
		MoneyAmount oldBalance = someCard.getAccount().getBalance();
		MoneyAmount amount = new MoneyAmount(new BigDecimal(500));
		someCard.setPinCode(2345);
		someCard.getAccount().deposit(amount);
		someCard.withdraw(amount, 2345);
		assertEquals(oldBalance, someCard.getAccount().getBalance());
	}
	
	@Test (expected = IllegalAmountException.class)
	public void withdraw_IllegalAmount() throws Exception
	{
		someCard.setPinCode(2345);
		someCard.withdraw(null, 2345);
	}
	
	@Test (expected = IllegalStateException.class)
	public void withdraw_TerminatedCard() throws Exception
	{
		terminateCard.withdraw(MoneyAmount.EUR_1, 2468);
	}
	
	@Test (expected = IllegalPinCodeException.class)
	public void withdraw_IllegalPinCode() throws Exception
	{
		someCard.getAccount().deposit(MoneyAmount.EUR_1);
		someCard.setPinCode(8642);
		someCard.withdraw(MoneyAmount.EUR_1, 2468);
	}
}
