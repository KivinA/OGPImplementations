package chapter_3.book_implementation;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * A class collection tests for the class of Bank Accounts.
 * 
 * @author	Kevin Algoet & Eric Steegmans
 * @version	3.0
 * 
 * @Note	Based on the code found in the book Object Oriented Programming with Java by Eric Steegmans.
 */
public class BankAccountTest {
	
	private static BankAccount accountBalance0, accountBalanceMAX;

	private BankAccount accountBalance100, accountBalance500;
	
	/**
	 * Set up a mutable test fixture.
	 * 
	 * @post	The variable accountBalance100 references a new Bank Account with a balance of 100.
	 * @post	The variable accountBalance500	references a new Bank Account with a balance of 500.
	 */
	@Before
	public void setUpMutableFixture()
	{
		accountBalance100 = new BankAccount(1111111, 100, 0, false);
		accountBalance500 = new BankAccount(2222222, 500, 0, false);
	}
	
	@After
	public void tearDownMutableFixture()
	{
		accountBalance100.terminate();
		accountBalance500.terminate();
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
		accountBalance0 = new BankAccount(1, 0, -2000L, false);
		accountBalanceMAX = new BankAccount(3, Long.MAX_VALUE, 0, false);
	}
	
	@Test
	public void extendedConstructor_LegalCase()
	{
		BankAccount theAccount = new BankAccount(1234, 1L, 0L, false);
		assertEquals(1234, theAccount.getNumber());
		assertEquals(1L, theAccount.getBalance());
		assertEquals(0L, theAccount.getCreditLimit());
		assertFalse(theAccount.isBlocked());
		theAccount.terminate();
	}
	
	@Test (expected = IllegalAmountException.class)
	public void extendedConstructor_IllegalCreditLimit() throws Exception
	{
		new BankAccount(12345, 100L, 1L, false);
	}
	
	@Test (expected = IllegalAmountException.class)
	public void extendedConstructor_IllegalBalance() throws Exception
	{
		new BankAccount(12345, Long.MIN_VALUE, Long.MIN_VALUE, false);
	}
	
	@Test (expected = IllegalAmountException.class)
	public void extendedConstructor_NonMatchingBalanceCreditLimit() throws Exception
	{
		new BankAccount(12345, -200, -100, false);
	}
	
	@Test (expected = IllegalAmountException.class)
	public void extendedConstructor_NonMatchingBalanceCreditLimit2() throws Exception
	{
		new BankAccount(12345, 0, 0, false);
	}
	
	@Test
	public void mediumConstructor_SingleCase()
	{
		BankAccount theAccount = new BankAccount(12345);
		assertEquals(12345, theAccount.getNumber());
		assertEquals(0L, theAccount.getBalance());
		assertEquals(-1000L, theAccount.getCreditLimit());
		assertFalse(theAccount.isBlocked());
		theAccount.terminate();
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
		assertTrue(accountBalance0.canHaveAsNumber(123));
	}
	
	@Test
	public void canHaveAsNumber_OwnNumber()
	{
		assertTrue(accountBalance0.canHaveAsNumber(accountBalance0.getNumber()));
	}
	
	@Test
	public void canHaveAsNumber_NonPositiveValue()
	{
		assertFalse(accountBalance0.canHaveAsNumber(0));
	}
	
	@Test
	public void canHaveAsNumber_DuplicateValue()
	{
		assertFalse(accountBalance0.canHaveAsNumber(accountBalance500.getNumber()));
	}
	
	@Test
	public void isPossibleCreditLimit_TrueCase()
	{
		assertTrue(BankAccount.isPossibleCreditLimit(-12));
	}
	
	@Test
	public void isPossibleCreditLimit_FalseCase()
	{
		assertFalse(BankAccount.isPossibleCreditLimit(1));
	}
	
	@Test
	public void canHaveAsCreditLimit_TrueCase()
	{
		assertTrue(accountBalance0.canHaveAsCreditLimit(-12));
	}
	
	@Test
	public void canHaveAsCreditLimit_ImpossibleCreditLimit()
	{
		assertFalse(accountBalance0.canHaveAsCreditLimit(100));
	}
	
	@Test
	public void canHaveAsCreditLimit_ConflictWithBalance()
	{
		assertFalse(accountBalance0.canHaveAsCreditLimit(0));
	}
	
	@Test
	public void setCreditLimit_LegalCase()
	{
		accountBalance0.setCreditLimit(-23);
		assertEquals(-23L, accountBalance0.getCreditLimit());
	}
	
	@Test (expected = IllegalAmountException.class)
	public void setCreditLimit_IllegalCase() throws Exception
	{
		accountBalance0.setCreditLimit(31);
	}
	
	@Test
	public void isValidBalanceLimit_TrueCase()
	{
		assertTrue(BankAccount.isValidBalanceLimit(1));
	}
	
	@Test
	public void isValidBalanceLimit_FalseCase()
	{
		assertFalse(BankAccount.isValidBalanceLimit(0));
	}
	
	@Test
	public void isPossibleBalance_TrueCase()
	{
		assertTrue(BankAccount.isPossibleBalance(BankAccount.getBalanceLimit()));
	}
	
	@Test
	public void isPossibleBalance_FalsCase()
	{
		if(BankAccount.getBalanceLimit() < Long.MAX_VALUE)
			assertFalse(BankAccount.isPossibleBalance(BankAccount.getBalanceLimit() + 1));
	}
	
	@Test
	public void canHaveAsBalance_TrueCase()
	{
		assertTrue(accountBalance0.canHaveAsBalance(89));
	}
	
	@Test
	public void canHaveAsBalance_ImpossibleBalance()
	{
		if (BankAccount.getBalanceLimit() < Long.MAX_VALUE)
			assertFalse(accountBalance0.canHaveAsBalance(Long.MAX_VALUE));
	}
	
	@Test
	public void canHaveAsBalance__ConflictWithCreditLimit()
	{
		assertFalse(accountBalance0.canHaveAsBalance(-2000));
	}
	
	@Test
	public void matchesBalanceCreditLimit_TrueCase()
	{
		assertTrue(BankAccount.matchesBalanceCreditLimit(100, 50));
	}
	
	@Test
	public void matchesBalanceCreditLimit_BalanceBelowLimit()
	{
		assertFalse(BankAccount.matchesBalanceCreditLimit(10, 50));
	}
	
	@Test
	public void matchesBalanceCreditLimit_EqualValues()
	{
		assertFalse(BankAccount.matchesBalanceCreditLimit(50, 50));
	}
	
	@Test
	public void hasHigherBalanceThanLong_TrueCase()
	{
		assertTrue(accountBalance0.hasHigherBalanceThan(-200));
	}
	
	@Test
	public void hasHigherBalanceThanLong_FalseCase()
	{
		assertFalse(accountBalance0.hasHigherBalanceThan(500));
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
		accountBalance100.hasHigherBalanceThan(null);
	}
	
	@Test
	public void canAcceptForDeposit_TrueCase()
	{
		assertTrue(accountBalance0.canAcceptForDeposit(200));
	}
	
	@Test
	public void canAcceptForDeposit_NegativeAmount()
	{
		assertFalse(accountBalance0.canAcceptForDeposit(-200));
	}
	
	@Test
	public void canAcceptForDeposit_LongOverflow()
	{
		assertFalse(accountBalance100.canAcceptForDeposit(Long.MAX_VALUE));
	}
	
	@Test
	public void canAcceptForDeposit_ValueAboveBalanceLimit()
	{
		if (BankAccount.getBalanceLimit() < Long.MAX_VALUE)
		{
			accountBalance100.deposit(BankAccount.getBalanceLimit() - accountBalance100.getBalance());
			assertFalse(accountBalance100.canAcceptForDeposit(1));
		}
	}
	
	@Test
	public void deposit_LegalCase()
	{
		accountBalance100.deposit(1);
		assertEquals(101L, accountBalance100.getBalance());
	}
	
	@Test (expected = IllegalAmountException.class)
	public void deposit_UnacceptableAmount() throws Exception
	{
		accountBalance0.deposit(-6);
	}
	
	@Test
	public void canAcceptForWithdraw_TrueCase()
	{
		assertTrue(accountBalance100.canAcceptForWithdraw(50));
	}
	
	@Test
	public void canAcceptForWithdraw__NegativeAmount()
	{
		assertFalse(accountBalance100.canAcceptForWithdraw(-200));
	}
	
	@Test
	public void canAcceptForWithdraw_BlockedAccount()
	{
		accountBalance100.block();
		assertFalse(accountBalance100.canAcceptForWithdraw(50));
	}
	
	@Test
	public void canAcceptForWithdraw_LongOverflow()
	{
		BankAccount theAccount = new BankAccount(123, -100, -1000, false);
		assertFalse(theAccount.canAcceptForWithdraw(Long.MAX_VALUE));
		theAccount.terminate();
	}
	
	@Test
	public void canAcceptForWithdraw_ValueBelowCreditLimit()
	{
		accountBalance0.setCreditLimit(-100);
		assertFalse(accountBalance0.canAcceptForWithdraw(101));
	}
	
	@Test
	public void withdraw_LegalCase()
	{
		accountBalance100.withdraw(50);
		assertEquals(50L, accountBalance100.getBalance());
	}
	
	@Test (expected = IllegalAmountException.class)
	public void withdraw_UnacceptableAmount() throws Exception
	{
		accountBalance0.withdraw(-6);
	}
	
	@Test
	public void transferTo_LegalCase()
	{
		accountBalance100.transferTo(50, accountBalance500);
		assertEquals(50L, accountBalance100.getBalance());
		assertEquals(550L, accountBalance500.getBalance());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void transferTo_NonEffectiveDestination() throws Exception
	{
		accountBalance100.transferTo(50, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void transferTo_SameDestination() throws Exception
	{
		accountBalance100.transferTo(50, accountBalance100);
	}
	
	@Test (expected = IllegalAmountException.class)
	public void transferTo_NonWithdrawableAmount() throws Exception
	{
		try
		{
			accountBalance0.setCreditLimit(-50);
			accountBalance0.transferTo(100, accountBalance100);
			fail(); // If we reach this piece of code, the test will automatically fail. We expect an exception to be thrown in this try part.
		}
		catch (IllegalAmountException exc)
		{
			assertEquals(100, accountBalance100.getBalance());
			assertEquals(0, accountBalance0.getBalance());
			throw exc;
		}
	}
	
	@Test (expected = IllegalAmountException.class)
	public void transferTo_NonDepositableAmount() throws Exception
	{
		try
		{
			accountBalance100.transferTo(50, accountBalanceMAX);
			fail();
		}
		catch (IllegalAmountException exc)
		{
			assertEquals(100, accountBalance100.getBalance());
			assertEquals(Long.MAX_VALUE, accountBalanceMAX.getBalance());
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
}