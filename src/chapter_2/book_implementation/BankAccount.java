package chapter_2.book_implementation;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of Bank Account involving a bank code, a number, a credit limit, a balance limit, a balance and a blocking facility.
 * 
 * @author 	Kevin Algoet & Eric Steegmans
 * @version	2.0
 *
 * @invar	The bank code that applies to all Bank Accounts must be a valid bank code.
 * 			| isValidBankCode(getBankCode())
 * @invar	Each Bank Account can have its number as its number.
 * 			| canHaveAsNumber(getNumber())
 * @invar	The credit limit that applies to all Bank Accounts must be a vamlid credit limit.
 * 			| isValidCreditLimit(getCreditLimit())
 * @invar	The balance limit that applies to all Bank Accounts must be a valid balance limit.
 * 			| isValidBalanceLimit(getBalanceLimit())
 * @invar	The balance of each Bank Account must be a valid balance for any Bank Account.
 * 			| isValidBalance(getBalance())
 * 
 * @note	Based on the code found in the book Object Oriented Programming with Java by Eric Steegmans.
 */
public class BankAccount {
	
	/**
	 * Initialize this new Bank Account with given number, given balance and a given blocked state.
	 * 
	 * @param 	number
	 * 			The number for this new Bank Account.
	 * @param 	balance
	 * 			The balance for this new Bank Account.
	 * @param 	isBlocked
	 * 			The blocked state for this new Bank Account.
	 * @pre		This new Bank account can have the given number as its number.
	 * 			| canHaveAsNumber(number)
	 * @pre		The given initial balance must be a valid balance for any Bank Account.
	 * 			| isValidBalance(balance)
	 * @post	The new number of this new Bank Account is equal to the given number.
	 * 			| new.getNumber() == number
	 * @post	The new balance of this new Bank Account is equal to the given initial balance.
	 * 			| new.getBalance() == balance
	 * @post	The new blocked state of this new Bank Account is equal to the given flag.
	 * 			| new.isBlocked() == isBlocked
	 */
	@Raw
	public BankAccount(int number, long balance, boolean isBlocked)
	{
		assert canHaveAsNumber(number) : "Precondition: Bank Account can have its number as its number.";
		this.number = number;
		setBalance(balance);
		setBlocked(isBlocked);
	}
	
	/**
	 * Initialize this new Bank Account as an unblocked account with given number and given balance.
	 * 
	 * @param 	number
	 * 			The number for this new Bank Account.
	 * @param 	balance
	 * 			The balance for this new Bank Account.
	 * @effect	This new Bank Account is initialized with the given number as its number, the given balance as its balance,
	 * 			and false as its blocked state.
	 * 			| this(number, balance, false)
	 */
	public BankAccount(int number, long balance)
	{
		this(number, balance, false);
	}
	
	/**
	 * Initialize this new Bank Account as an unblocked account with given number and zero balance.
	 * 
	 * @param 	number
	 * 			The number for this new Bank Account.
	 * @effect	This new Bank Account is initialized with the given number as its number and zero as its balance.
	 * 			| this(number, 0)
	 */
	public BankAccount(int number)
	{
		this(number, 0);
	}
	
	/**
	 * Return the number of this Bank Account.
	 * 
	 * @note	The number of a Bank Account serves to distinguish it from all other Bank Accounts.
	 */
	@Basic @Immutable @Raw
	public int getNumber()
	{
		return this.number;
	}
	
	/**
	 * Check whether this Bank Account can have the given number as its number.
	 * 
	 * @param 	number
	 * 			The number to check.
	 * @return	True if and only if the given number is strict positive, and if no other Bank Account has the given number
	 * 			as its number.
	 * 			| result == ( (number > 0) && (for each account in BankAccount: ( (account != this) || (account.getNumber() != number) ) ) )
	 */
	@Raw
	public boolean canHaveAsNumber(int number)
	{
		return false;
	}
	
	/**
	 * Variable registering the number of this Bank Account.
	 */
	private final int number;
	
	/**
	 * Return the bank code that applies to all Bank Accounts.
	 * 
	 * @note	The bank code identifies the bank to which all Bank Accounts belong.
	 */
	@Basic @Immutable
	public static int getBankCode()
	{
		return bankCode;
	}
	
	/**
	 * Check whether the given bank code is a valid bank code for all Bank Accounts.
	 * 
	 * @param 	bankCode
	 * 			The bank code to check.
	 * @return	True if and only if the given bank code is strict positive.
	 * 			| result == (bankCode > 0)
	 */
	public static boolean isValidBankCode(int bankCode)
	{
		return bankCode > 0;
	}
	
	/**
	 * Variable registering the bank code that applies to all Bank Accounts.
	 */
	private static final int bankCode = 123;
	
	/**
	 * Return the balance of this Bank Account.
	 * 
	 * @note	The balance of a Bank Account expresses the amount of money available on that account.
	 */
	@Basic @Raw
	public long getBalance()
	{
		return this.balance;
	}
	
	/**
	 * Check whether this Bank Account has a higher balance than the given amount of money.
	 * 
	 * @param 	amount
	 * 			The amount of money to compare with.
	 * @return	True if and only if the balance of this Bank Account is greater than the given amount.
	 * 			| result == (this.getBalance() > amount)
	 */
	public boolean hasHigherBalanceThan(long amount)
	{
		return getBalance() > amount;
	}
	
	/**
	 * Check whether this Bank Account has a higher balance than the other Bank Account.
	 * 
	 * @param 	other
	 * 			The Bank Account to compare with.
	 * @pre		The other Bank Account is effective.
	 * 			| other != null
	 * @return	True if and only if this Bank Account has a higher balance than the balance of the other Bank Account.
	 * 			| result == this.hasHigherBalanceThan(other.getBalance())
	 */
	public boolean hasHigherBalanceThan(BankAccount other)
	{
		assert (other != null) : "Precondition: Effective other account.";
		return hasHigherBalanceThan(other.getBalance());
	}
	
	/**
	 * Return a boolean reflecting whether this Bank Account can accept the given amount for deposit.
	 * 
	 * @param 	amount
	 * 			The amount to be checked.
	 * @return	True if and only if the given amount if positive, if the balance of this Bank Account incremented with the given amount
	 * 			is a valid balance for any Bank Account, and if that sum doesn't cause an overflow.
	 * 			| result == ( (amount > 0) && isValidBalance(getBalance() + amount) && (getBalance() <= Long.MAX_VALUE - amount) )  
	 */
	public boolean canAcceptForDeposit(long amount)
	{
		return ((amount > 0) && isValidBalance(getBalance() + amount) && (getBalance() <= Long.MAX_VALUE - amount));
	}
	
	/**
	 * Deposit the given amount of money to this Bank Account.
	 * 
	 * @param	amount
	 * 			The amount of money to be deposited.
	 * @pre		This Bank Account can accept the given amount for deposit.
	 * 			| canAcceptForDeposit(amount)
	 * @post	The new balance of this Bank Account is equal to the old balance of this Bank Account incremented with the given
	 * 			amount of money.
	 * 			| new.getBalance() == this.getBalance() + amount
	 * 
	 * @note	Even though we have specified a post condition, it would be better to specify an effect clause,
	 * 			because we actually invoke another method to change the balance of this Bank Account. See below for effect clause:
	 * @effect	The new balance of this Bank Account is set to the old balance of this Bank Account incremented with the given amount of money.
	 * 			| this.setBalance(getBalance() + amount)
	 */
	public void deposit(long amount)
	{
		assert canAcceptForDeposit(amount): "Precondition: Acceptable amount for deposit.";
		setBalance(getBalance() + amount);
	}
	
	/**
	 * Return a boolean reflecting whether this Bank Account can accept the given amount for withdraw.
	 * 
	 * @param 	amount
	 * 			The amount to be checked.
	 * @return	True if and only if the given amount is positive, if this Bank Account isn't blocked, if the 
	 * 			balance of this Bank Account decremented with the given amount is a valid balance for any Bank Account, and if
	 * 			that substraction doesn't cause an overflow.
	 * 			| result == ( (amount > 0) && !isBlocked() && isValidBalance(getBalance() - amount) &&
	 * 			|		(getBalance() >= Long.MIN_VALUE + amount) )
	 */
	public boolean canAcceptForWithdraw(long amount)
	{
		return ((amount > 0) && !isBlocked() && isValidBalance(getBalance() - amount) && (getBalance() >= Long.MIN_VALUE + amount));
		
	}
	
	/**
	 * Withdraw the given amount of money from this Bank Account.
	 * 
	 * @param 	amount
	 * 			The amount of money to be withdrawn
	 * @pre		This Bank Account can accept the given amount for withdraw.
	 * 			| canAcceptForWithdraw(amount)
	 * @post	The new balance of this Bank Account is equal to the old balance of this Bank Account decremented with the given amount of money.
	 * 			| new.getBalance() == this.getBalance() - amount
	 * 
	 * @note	Even though we have specified a post condition, it would be better to specify an effect clause,
	 * 			because we actually invoke another method to change the balance of this Bank Account. See below for effect clause:
	 * @effect	The new balance of this Bank Account is set to the old balance of this Bank Account decremented with the given amount of money.
	 * 			| this.setBalance(getBalance() - amount)
	 */
	public void withdraw(long amount)
	{
		assert canAcceptForWithdraw(amount): "Precondition: Acceptable amount for withdraw.";
		setBalance(getBalance() - amount); 
	}
	
	/**
	 * Transfer the given amount of money from this Bank Account to the given destination account.
	 * 
	 * @param 	amount
	 * 			The amount of money to be transferred.
	 * @param 	destination
	 * 			The Bank Account to transfer the money to.
	 * @pre		The given destination account must be effective and different from this Bank Account.
	 * 			| (destination != null) && (destination != this)
	 * @effect	The given amount of money is withdrawn from this Bank Account, and deposited to the given destination account.
	 * 			| this.withdraw(amount) && destination.deposit(amount)
	 */
	public void transferTo(long amount, BankAccount destination)
	{
		assert (destination != null) : "Precondition: Effective destination account.";
		assert (destination != this) : "Precondition: Destination different from source.";
		withdraw(amount);
		destination.deposit(amount);
	}
	
	/**
	 * Check whether the given balance is a valid balance for any Bank Account.
	 * 
	 * @param 	balance
	 * 			The balance to check.
	 * @return	True if and only if ther given balance is not below the credit limit and not aboven the balance limit.
	 * 			| result == ( (balance >= getCreditLimit()) && (balance <= getBalanceLimit()) )
	 */
	public static boolean isValidBalance(long balance)
	{
		return ((balance >= getCreditLimit()) && (balance <= getBalanceLimit()));
	}
	
	/**
	 * Set the balance of this Bank Account to the given balance.
	 * 
	 * @param 	balance
	 * 			The new balance for this Bank Account.
	 * @pre		The given balance must be a valid balance for any Bank Account.
	 * 			| isValidBalance(balance)
	 * @post	The new balance of this Bank Account is equal to the given balance.
	 * 			| new.getBalance() == balance 
	 * 
	 * @note	We make this method private, because deposit and withdraw control this function. We also add a @Model tag to this method to 
	 * 			specify it is used in the specifications of other methods.
	 */
	@Raw @Model
	private void setBalance(long balance)
	{
		assert isValidBalance(balance): "Precondition: Valid balance for any Bank Account.";
			this.balance = balance;
	}
	
	/**
	 * Variable registering the balance of this Bank Account;
	 */
	private long balance = 0L;
	
	/**
	 * Return the credit limit that applies to all Bank Accounts.
	 * 
	 * @note	The credit limit expresses the lowest possible value for the balance of any Bank Account.
	 */
	@Basic
	public static long getCreditLimit()
	{
		return creditLimit;
	}
	
	/**
	 * Check whether the given credit limit is a valid credit limit for all Bank Accounts.
	 * 
	 * @param 	creditLimit
	 * 			The credit limit to check.
	 * @return	True if and only if the given credit limit is not positive.
	 * 			| result == (creditLimit <= 0)
	 */
	public static boolean isValidCreditLimit(long creditLimit)
	{
		return creditLimit <= 0;
	}
	
	/**
	 * Set the credit limit that applies to all Bank Accounts to the given credit limit.
	 * 
	 * @param 	creditLimit
	 * 			The new credit limit for all Bank Accounts.
	 * @post	If the given credit limit is not above the credit limit that currently applies to all Bank Accounts,
	 * 			the new credit limit that applies to all Bank Accounts is equal to the given credit limit.
	 * 			| if (creditLimit <= getCreditLimit())
	 * 			|	then new.getCreditLimit() == creditLimit
	 */
	public static void setCreditLimit(long creditLimit)
	{
		if (creditLimit <= getCreditLimit())
			BankAccount.creditLimit = creditLimit;
	}
	
	/**
	 * Variable registerting the credit limit that applies to all Bank Accounts.
	 */
	private static long creditLimit = 0L;
	
	/**
	 * Return the balance limit that applies to all Bank Accounts.
	 * 
	 * @note	The balance limit expresses the highest possible value for the balance of any Bank Account.
	 */
	@Basic @Immutable
	public static long getBalanceLimit()
	{
		return balanceLimit;
	}
	
	/**
	 * Check whether the given balance limit is a valid balance limit for all Bank Accounts.
	 * 
	 * @param 	balanceLimit
	 * 			The balance limit to check.
	 * @return	True if and only if the given balance limit is strict positive.
	 * 			| result == (balanceLimit > 0)
	 */
	public static boolean isValidBalanceLimit(long balanceLimit)
	{
		return balanceLimit > 0;
	}
	
	/**
	 * Variable registering the balance limit that applies to all Bank Accounts.
	 */
	private static final long balanceLimit = Long.MAX_VALUE;
	
	/**
	 * Check whether this Bank Account is blocked.
	 * 
	 * @note	Some methods have no effect when invoked against blocked Bank Accounts.
	 */
	@Basic @Raw
	public boolean isBlocked()
	{
		return isBlocked;
	}
	
	/**
	 * Set the blocked state of this Bank Account according to the given flag.
	 * 
	 * @param 	flag
	 * 			The new blocked state for this Bank Account.
	 * @post	The new blocked state of this Bank Account is equal to the given flag.
	 * 			| new.isBlocked() == flag
	 */
	@Raw
	public void setBlocked(boolean flag)
	{
		this.isBlocked = flag;
	}
	
	/**
	 * Block this Bank Account.
	 * 
	 * @effect	The blocked state of this Bank Account is set to true.
	 * 			| this.setBlocked(true)
	 */
	public void block()
	{
		setBlocked(true);
	}
	
	/**
	 * Unblock this Bank Account.
	 * 
	 * @effect	The blocked state of this Bank Account is set to false.
	 * 			| this.setBlocked(false)
	 */
	public void unblock()
	{
		setBlocked(false);
	}
	
	/**
	 * Variable registering the blocked state of this Bank Account.
	 */
	private boolean isBlocked = false;
}
