package chapter_2.book_implementation;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.*;

/**
 * A class collection tests for the class of Bank Accounts.
 * 
 * @author	Kevin Algoet & Eric Steegmans
 * @version	1.0
 * 
 * @Note	Based on the code found in the book Object Oriented Programming with Java by Eric Steegmans.
 */
public class BankAccountTest {
	
	/**
	 * Variables referencing Bank Accounts with balance 1000, balance 2000, respecitvely one that is blocked
	 * and an account that has tokens.
	 */
	private BankAccount accountBalance1000, accountBalance2000, blockedAccount, unblockedAccount, 
			accountWithTokens;
	
	/**
	 * Variables referencing Bank Accounts with balance 300, respectively with balance 500.
	 * 
	 * @note	These variables don't change during the tests.
	 */
	private static BankAccount accountBalance300, accountBalance500;
	
	/**
	 * Set up a mutable test fixture.
	 * 
	 * @post	The variable accountBalance1000 references a new Bank Account with a balance of 1000.
	 * @post	The variable accountBalance2000	references a new Bank Account with a balance of 2000.
	 * @post	The variable blockedAccount references a new Bank Account that is blocked.
	 * @post	The variable unblockedAccount references a new Bank Account that is unblocked.
	 * @post	The variable accountWithTokens references a new Bank Account that contains the tokens "abcdef" & "123456".
	 */
	@Before
	public void setUpMutableFixture()
	{
		accountBalance1000 = new BankAccount(1111111, 1000);
		accountBalance2000 = new BankAccount(2222222, 2000);
		blockedAccount = new BankAccount(2121212, 333, true);
		unblockedAccount = new BankAccount(1212121);
		accountWithTokens = new BankAccount(135790, 200, false, "abcdef", "123456");
	}
	
	@After
	public void tearDownMutableFixture()
	{
		accountBalance1000.terminate();
		accountBalance2000.terminate();
		blockedAccount.terminate();
		unblockedAccount.terminate();
		accountWithTokens.terminate();
	}
	
	/**
	 * Set up an immutable test fixture.
	 * 
	 * @post	The variable accountBalance300 references a new Bank Account with balance 300.
	 * @post	The variable accountBalance500 references a new Bank Account with balance 500.
	 */
	@BeforeClass
	public static void setUpImmutableFixture()
	{
		accountBalance300 = new BankAccount(1234567, 300);
		accountBalance500 = new BankAccount(7654321, 500);
	}
	
	@Test
	public void extendedConstructor_LegalCase()
	{
		String[] tokens = {"abcdef", "012345"};
		BankAccount theAccount = new BankAccount(1234, 300, false, tokens);
		assertEquals(1234, theAccount.getNumber());
		assertEquals(300L, theAccount.getBalance());
		assertFalse(theAccount.isBlocked());
		assertTrue(Arrays.equals(tokens, theAccount.getTokens()));
		theAccount.terminate();
	}
	
	@Test
	public void mediumConstructor_SingleCase()
	{
		BankAccount theAccount = new BankAccount(1234, 300);
		assertEquals(1234, theAccount.getNumber());
		assertEquals(300L, theAccount.getBalance());
		assertFalse(theAccount.isBlocked());
		assertEquals(0, theAccount.getNbTokens());
		theAccount.terminate();
	}
	
	@Test
	public void shortConstructor_SingleCase()
	{
		BankAccount theAccount = new BankAccount(1234);
		assertEquals(1234, theAccount.getNumber());
		assertEquals(0L, theAccount.getBalance());
		assertFalse(theAccount.isBlocked());
		assertEquals(0, theAccount.getNbTokens());
		theAccount.terminate();
	}

	@Test
	public void isValidBankCode_LegalValue()
	{
		assertTrue(BankAccount.isValidBankCode(200));
	}
	
	@Test
	public void isValidBankCode_IllegalValue()
	{
		assertFalse(BankAccount.isValidBankCode(-1));
	}
	
	@Test
	public void canHaveAsNumber_LegalValue()
	{
		assertTrue(accountBalance300.canHaveAsNumber(123));
	}
	
	@Test
	public void canHaveAsNumber_OwnNumber()
	{
		assertTrue(accountBalance300.canHaveAsNumber(accountBalance300.getNumber()));
	}
	
	@Test
	public void canHaveAsNumber_NonPositiveValue()
	{
		assertFalse(accountBalance300.canHaveAsNumber(-123));
	}
	
	@Test
	public void canHaveAsNumber_DuplicateValue()
	{
		assertFalse(accountBalance300.canHaveAsNumber(accountBalance500.getNumber()));
	}
	
	@Test
	public void isValidCreditLimit_LegalValue()
	{
		assertTrue(BankAccount.isValidCreditLimit(0));
	}
	
	@Test
	public void isValidCreditLimit_IllegalValue()
	{
		assertFalse(BankAccount.isValidCreditLimit(1));
	}
	
	@Test
	public void setCreditLimit_LegalCase()
	{
		BankAccount.setCreditLimit(-1000);
		assertEquals(-1000L, BankAccount.getCreditLimit());
	}
	
	@Test
	public void isValidBalanceLimit_LegalValue()
	{
		assertTrue(BankAccount.isValidBalanceLimit(1));
	}
	
	@Test
	public void isValidBalanceLimit_IllegalValue()
	{
		assertFalse(BankAccount.isValidBalanceLimit(0));
	}
	
	@Test
	public void isValidBalance_LegalValue()
	{
		assertTrue(BankAccount.isValidBalance(0));
	}
	
	@Test
	public void isValidBalane_ValueBelowCreditLimit()
	{
		if(BankAccount.getCreditLimit() > Long.MIN_VALUE)
			assertFalse(BankAccount.isValidBalance(BankAccount.getCreditLimit() - 1));
	}
	
	@Test
	public void isValidBalance_ValueAboveBalanceLimit()
	{
		if (BankAccount.getBalanceLimit() < Long.MAX_VALUE)
			assertFalse(BankAccount.isValidBalance(BankAccount.getBalanceLimit() + 1));
	}
	
	@Test
	public void hasHigherBalanceThanLong_HigherCase()
	{
		assertTrue(accountBalance300.hasHigherBalanceThan(200));
	}
	
	@Test
	public void hasHigherBalanceThanLong_LowerCase()
	{
		assertFalse(accountBalance300.hasHigherBalanceThan(500));
	}
	
	@Test
	public void hasHigherBalanceThanLong_EqualCase()
	{
		assertFalse(accountBalance1000.hasHigherBalanceThan(1000));
	}
	
	@Test
	public void hasHigherBalanceThanAccount_EffectiveCase()
	{
		assertTrue(accountBalance500.hasHigherBalanceThan(accountBalance300));
	}
	
	@Test
	public void canAcceptForDeposit_NegativeAmount()
	{
		assertFalse(accountBalance300.canAcceptForDeposit(-200));
	}
	
	@Test
	public void canAcceptForDeposit_LongOverflow()
	{
		assertFalse(accountBalance300.canAcceptForDeposit(Long.MAX_VALUE));
	}
	
	@Test
	public void canAcceptForDeposit_ValueAboveBalanceLimit()
	{
		if (BankAccount.getBalanceLimit() < Long.MAX_VALUE)
		{
			accountBalance1000.deposit(BankAccount.getBalanceLimit() - accountBalance1000.getBalance());
			assertFalse(accountBalance1000.canAcceptForDeposit(1));
		}
	}
	
	@Test
	public void deposit_LegalCase()
	{
		accountBalance1000.deposit(200);
		assertEquals(1200L, accountBalance1000.getBalance());
	}
	
	@Test
	public void canAcceptForWithdraw__NegativeAmount()
	{
		assertFalse(accountBalance300.canAcceptForWithdraw(-200));
	}
	
	@Test
	public void canAcceptForWithdraw_BlockedAccount()
	{
		assertFalse(blockedAccount.canAcceptForWithdraw(200));
	}
	
	@Test
	public void canAcceptForWithdraw_LongOverflow()
	{
		if (BankAccount.isValidBalance(-1))
		{
			accountBalance1000.withdraw(accountBalance1000.getBalance() + 1);
			assertFalse(accountBalance1000.canAcceptForWithdraw(Long.MIN_VALUE));
		}
	}
	
	@Test
	public void canAcceptForWithdraw_ValueBelowCreditLimit()
	{
		if (BankAccount.getCreditLimit() > Long.MIN_VALUE)
		{
			accountBalance1000.withdraw(BankAccount.getCreditLimit() + accountBalance1000.getBalance());
			assertFalse(accountBalance1000.canAcceptForWithdraw(1));
		}
	}
	
	@Test
	public void withdraw_LegalCase()
	{
		accountBalance1000.withdraw(200);
		assertEquals(800L, accountBalance1000.getBalance());
	}
	
	@Test
	public void transferTo_LegalCase()
	{
		accountBalance1000.transferTo(100, accountBalance300);
		assertEquals(900L, accountBalance1000.getBalance());
		assertEquals(400L, accountBalance300.getBalance());
	}
	
	@Test
	public void setBlocked_TrueCase()
	{
		accountBalance1000.setBlocked(true);
		assertTrue(accountBalance1000.isBlocked());
	}
	
	@Test
	public void setBlocked_FalseCase()
	{
		accountBalance1000.setBlocked(false);
		assertFalse(accountBalance1000.isBlocked());
	}
	
	@Test
	public void block_SingleCase()
	{
		accountBalance1000.block();
		assertTrue(accountBalance1000.isBlocked());
	}
	
	@Test
	public void unblock_SingleCase()
	{
		accountBalance1000.unblock();
		assertFalse(accountBalance1000.isBlocked());
	}
	
	@Test
	public void isValidToken_TrueCase()
	{
		assertTrue(BankAccount.isValidToken("abc123"));
	}
	
	@Test
	public void isValidToken_NonEffectiveToken()
	{
		assertFalse(BankAccount.isValidToken(null));
	}
	
	@Test
	public void isValidToken_InvalidLength()
	{
		assertFalse(BankAccount.isValidToken("abc"));
	}
	
	@Test
	public void canHaveAsTokenAt_TrueCase()
	{
		assertTrue(accountWithTokens.canHaveAsTokenAt("abc123", 2));
	}
	
	@Test
	public void canHaveAsTokenAt_InvalidToken()
	{
		assertFalse(accountWithTokens.canHaveAsTokenAt("Abc", 2));
	}
	
	@Test
	public void canHaveAsTokenAt_NonPositiveIndex()
	{
		assertFalse(accountWithTokens.canHaveAsTokenAt("abc123", -1));
	}
	
	@Test
	public void canHaveAsTokenAt_IndexTooHigh()
	{
		assertFalse(accountWithTokens.canHaveAsTokenAt("Abcdef", 3));
	}
	
	@Test
	public void canHaveAsTokenAt_DuplicateToken()
	{
		assertFalse(accountWithTokens.canHaveAsTokenAt("abcdef", 2));
	}
	
	@Test
	public void setTokenAt_LegalCase()
	{
		accountWithTokens.setTokenAt("zyxwvu", 2);
		assertEquals("zyxwvu", accountWithTokens.getTokenAt(2));
	}
	
	@Test
	public void getTokens_SingleCase()
	{
		String[] tokens = accountWithTokens.getTokens();
		assertEquals(2, tokens.length);
		assertEquals("abcdef", tokens[0]);
		assertEquals("123456", tokens[1]);
	}
}
