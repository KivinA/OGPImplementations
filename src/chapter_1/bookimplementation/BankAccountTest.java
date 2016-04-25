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
	private static BankAccount accountBalance1000;
	
	/**
	 * Variables referencing Bank Accounts with balance 300, respectively with balance 500.
	 */
	private static BankAccount accountBalance300, accountBalance500;
	
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
		accountBalance300 = new BankAccount(1234567, 300);
		accountBalance500 = new BankAccount(7654321, 500);
		blockedAccount = new BankAccount(2121212, 333, true);
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
	public void hasHigherBalanceThanAccount_EffectiveCase()
	{
		assertTrue(accountBalance500.hasHigherBalanceThan(accountBalance300));
	}
	
	@Test
	public void hasHigherBalanceThanAccount_NonEffectiveCase()
	{
		assertFalse(accountBalance500.hasHigherBalanceThan(null));
	}
}
