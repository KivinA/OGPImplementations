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
	public void canHaveAsNumber_OwnValue()
	{
		assertFalse(accountBalance300.canHaveAsNumber(accountBalance300.getNumber()));
	}
	
	@Test
	public void deposit_LegalCase()
	{
		accountBalance1000.deposit(200);
		assertEquals(1200L, accountBalance1000.getBalance());
	}
	
	@Test
	public void deposit_NegativeAmount()
	{
		accountBalance1000.deposit(-1200);
		assertEquals(1000L, accountBalance1000.getBalance());
	}
	
	@Test
	public void deposit_BalanceOverflow()
	{
		accountBalance1000.deposit(Long.MAX_VALUE);
		assertEquals(1000L, accountBalance1000.getBalance());
	}
	
	@Test
	public void withdraw_LegalCase()
	{
		accountBalance1000.withdraw(200);
		assertEquals(800L, accountBalance1000.getBalance());
	}
	
	@Test
	public void withdraw_NegativeAmount()
	{
		accountBalance1000.withdraw(-1200);
		assertEquals(1000L, accountBalance1000.getBalance());
	}
	
	@Test
	public void withdraw_BalanceOverflow()
	{
		accountBalance1000.withdraw(Long.MAX_VALUE);
		assertEquals(1000L, accountBalance1000.getBalance());
	}
	
	@Test
	public void withdraw_BlockedAccount()
	{
		long oldBalance = blockedAccount.getBalance();
		blockedAccount.withdraw(200);
		assertEquals(oldBalance, blockedAccount.getBalance());
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
		assertFalse(accountBalance300.hasHigherBalanceThan(300));
	}
	
	@Test
	public void hasHigherBalanceThanAccount_EffectiveCase()
	{
		assertTrue(accountBalance500.hasHigherBalanceThan(accountBalance300));
	}
	
	@Test
	public void hasHigherBalanceThanAccount_NonEffectiveCase()
	{
		assertFalse(accountBalance500.hasHigherBalanceThan(null));
	}
	
	@Test
	public void transferTo_LegalCase()
	{
		accountBalance1000.transferTo(100, accountBalance300);
		assertEquals(900L, accountBalance1000.getBalance());
		assertEquals(400L, accountBalance300.getBalance());
	}
	
	@Test
	public void transferTo_NonEffectiveDestination()
	{
		accountBalance1000.transferTo(100, null);
		assertEquals(1000L, accountBalance1000.getBalance());
	}
	
	@Test
	public void transferTo_SameAccount()
	{
		accountBalance1000.transferTo(100, accountBalance1000);
		assertEquals(1000L, accountBalance1000.getBalance());
	}
	
	@Test
	public void transferTo_NegativeAmount()
	{
		accountBalance1000.transferTo(-100, accountBalance300);
		assertEquals(1000L, accountBalance1000.getBalance());
		assertEquals(300L, accountBalance300.getBalance());
	}
	
	@Test
	public void transferTo_BlockedAccount()
	{
		long oldBalance = blockedAccount.getBalance();
		blockedAccount.transferTo(100, accountBalance300);
		assertEquals(oldBalance, blockedAccount.getBalance());
		assertEquals(300L, accountBalance300.getBalance());
	}
	
	@Test
	public void transferTo_BalanceOverflowOnSource()
	{
		accountBalance300.transferTo(500, accountBalance500);
		assertEquals(300L, accountBalance300.getBalance());
		assertEquals(500L, accountBalance500.getBalance());
	}
	
	@Test
	public void transferTo_BalanceOverflownDestination()
	{
		BankAccount accountHighestBalance = new BankAccount(1234, BankAccount.getBalanceLimit());
		accountBalance1000.transferTo(100, accountHighestBalance);
		assertEquals(1000L, accountBalance1000.getBalance());
		assertEquals(BankAccount.getBalanceLimit(), accountHighestBalance.getBalance());
	}
	
	@Test
	public void setCreditLimit_LegalCase()
	{
		BankAccount.setCreditLimit(-1000);
		assertEquals(-1000L, BankAccount.getCreditLimit());
	}
	
	@Test
	public void setCreditLimit_IllegalCase()
	{
		long oldLimit = BankAccount.getCreditLimit();
		BankAccount.setCreditLimit(100);
		assertEquals(oldLimit, BankAccount.getCreditLimit());
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
}
