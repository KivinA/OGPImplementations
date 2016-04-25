package chapter_1.bookimplementation;

import static org.junit.Assert.*;
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
	 * Variable referencing a Bank Account with balance 1000.
	 */
	private BankAccount accountBalance1000;
	
	/**
	 * Variables referencing Bank Accounts with balance 300, respectively with balance 500.
	 */
	private BankAccount accountBalance300, accountBalance500;
	
	/**
	 * Variable referencing a blocked Bank Account.
	 */
	private static BankAccount blockedAccount;
	
	/**
	 * Set up a mutable test fixture.
	 * 
	 * @post	The variable accountBalance1000 references a new Bank Account with a balance of 1000.
	 */
	@Before
	public void setUpMutableFixture()
	{
		accountBalance1000 = new BankAccount(1111111, 1000);
		accountBalance300 = new BankAccount(1234567, 300);
		accountBalance500 = new BankAccount(7654321, 500);
	}
	
	/**
	 * Set up an immutable test fixture.
	 * 
	 * @post	The variable accountBalance300 references a new Bank Account with balance 300.
	 * @post	The variable accountBalance500 references a new Bank Account with balance 500.
	 * @post	The variable blockedAccount references a new blocked Bank Account.
	 */
	@BeforeClass
	public static void setUpImmutableFixture()
	{
		blockedAccount = new BankAccount(2121212, 333, true);
		BankAccount.setCreditLimit(0L);
	}
	
	@Test
	public void extendedConstructor_LegalCase()
	{
		BankAccount theAccount = new BankAccount(1234, 300, false);
		assertEquals(1234, theAccount.getNumber());
		assertEquals(300L, theAccount.getBalance());
		assertFalse(theAccount.isBlocked());
	}
	
	@Test
	public void extendedConstructor_NegativeNumber()
	{
		BankAccount theAccount = new BankAccount(-1, 300, false);
		assertEquals(0, theAccount.getNumber());
		assertEquals(300L, theAccount.getBalance());
		assertFalse(theAccount.isBlocked());
	}
	
	@Test
	public void extendedConstructor_BalanceBelowLimit()
	{
		BankAccount theAccount = new BankAccount(12345, BankAccount.getCreditLimit() - 1, false);
		assertEquals(12345, theAccount.getNumber());
		assertEquals(0L, theAccount.getBalance());
		assertFalse(theAccount.isBlocked());
	}
	
	@Test
	public void extendedConstructor_BalanceAboveLimit()
	{
		BankAccount theAccount = new BankAccount(12345, BankAccount.getBalanceLimit() + 1, false);
		assertEquals(12345, theAccount.getNumber());
		assertEquals(0L, theAccount.getBalance());
		assertFalse(theAccount.isBlocked());
	}
	
	@Test
	public void mediumConstructor_SingleCase()
	{
		BankAccount theAccount = new BankAccount(1234, 300);
		assertEquals(1234, theAccount.getNumber());
		assertEquals(300L, theAccount.getBalance());
		assertFalse(theAccount.isBlocked());
	}
	
	@Test
	public void shortConstructor_SingleCase()
	{
		BankAccount theAccount = new BankAccount(1234);
		assertEquals(1234, theAccount.getNumber());
		assertEquals(0L, theAccount.getBalance());
		assertFalse(theAccount.isBlocked());
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
