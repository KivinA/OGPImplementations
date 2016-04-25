package chapter_2.book_implementation;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

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
	 * @post	If the given number is not negative, the initial number of this new Bank Account is equal to the given number. Otherwise, its initial
	 * 			number is equal to zero.
	 * 			| if (number >= 0)
	 * 			|	then new.getNumber() == number
	 * 			| else
	 * 			|	new.getNumber() == 0
	 * @post	If the given balance is not below the credit limit and not above the balance limit, the initial balance of this new Bank Account
	 * 			is equal to the given balance. Otherwise, its initial balance is equal to 0.
	 * 			| if (balance >= getCreditLimit() && balance <= getBalanceLimit())
	 * 			|	then new.getBalance() == balance
	 * 			| else
	 * 			|	new.getBalance() == 0
	 * @post	The initial blocked state of this new Bank Account is equal to the given flag.
	 * 			| new.isBlocked() == isBlocked
	 */
	public BankAccount(int number, long balance, boolean isBlocked)
	{
		if (number < 0)
			number = 0;
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
	@Basic @Immutable
	public int getNumber()
	{
		return this.number;
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
	 * Variable registering the bank code that applies to all Bank Accounts.
	 */
	private static final int bankCode = 123;
	
	/**
	 * Return the balance of this Bank Account.
	 * 
	 * @note	The balance of a Bank Account expresses the amount of money available on that account.
	 */
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
	 * 			| result == getBalance() > amount
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
	 * @return	True if and only if the other Bank Account is effective, and if this Bank Account has a higher balance than 
	 * 			the balance of the other Bank Account.
	 * 			| result == ((other != null) && (getBalance() > other.getBalance()))
	 * 
	 * @note	You don't have to specify a second return clause for stating when the method will return false, but you can do this, 
	 * 			as specified below.
	 * @return	False if the other Bank Account is ineffective.
	 * 			| if (other == null)
	 * 			| 	then result == false
	 */
	public boolean hasHigherBalanceThan(BankAccount other)
	{
		return (other != null) && (getBalance() > other.getBalance());
	}
	
	/**
	 * Deposit the given amount of money to this Bank Account.
	 * 
	 * @param	amount
	 * 			The amount of money to be deposited.
	 * @post	If the given amount of money is positive, and if the old balance of this Bank Account incremented with
	 * 			the given ammount of money isn't above the balance limit, the new balance of this Bank Account is equal to the old balance of this
	 * 			Bank Account incremented with the given amount of money.
	 * 			| if (amount > 0 && (old.getBalance() + amount) < getBalanceLimit())
	 * 			|	then new.getBalance() == old.getBalance() + amount
	 */
	public void deposit(long amount)
	{
		if ((amount > 0) && (getBalance() + amount <= getBalanceLimit()))
			setBalance(getBalance() + amount);
	}
	
	/**
	 * Withdraw the given amount of money from this Bank Account.
	 * 
	 * @param 	amount
	 * 			The amount of money to be withdrawn
	 * @post	If the given amount of money is positive, and if this Bank Account is not blocked, and if the old balance of this 
	 * 			Bank Account decremented with the given amount of money is not below the credit limit, the new balance of this Bank Account
	 * 			is equal to the old balance of this Bank Account decremented with the given amount of money.
	 * 			| if (amount > 0 && !isBlocked() && (old.getBalance - amount) >= getCreditLimit())
	 * 			|	then new.getBalance() == old.getBalance() - amount
	 */
	public void withdraw(long amount)
	{
		if ((amount > 0) && !isBlocked() && (getBalance() - amount >= getCreditLimit()))
			setBalance(getBalance() - amount); 
	}
	
	/**
	 * Transfer the given amount of money from this Bank Account to the given destination account.
	 * 
	 * @param 	amount
	 * 			The amount of money to be transferred.
	 * @param 	destination
	 * 			The Bank Account to transfer the money to.
	 * @effect	If the given amount of money is positive, and if the given destination account is effective and not the same as this Bank Account, 
	 * 			and if this Bank Account isn't blocked, and if the old balance of this Bank Account decremented with the given
	 * 			amount of money isn't below the credit limit, and if the old balance of the given destination account incremented
	 * 			with the given amount of money isn't above the balance limit, the given amount of money is withdrawn from this 
	 * 			Bank Account, and deposited to the given destination account.
	 * 			| if ((amount > 0) && !isBlocked() || !destination.isBlocked()) && (destination != null) && (destination != this) 
	 * 			|			&& ((old.getBalance() - amount) >= getCreditLimit()) && ((old.destination.getBalance() + amount) <= getBalanceLimit())
	 * 			|	then this.withdraw(amount)
	 * 			|		 destination.deposit(amount)
	 * 
	 * @note	You can also change the effect clause into multiple post clauses, as specified below.
	 * @post	If the given destination account is effective and not the same as this Bank Account, and if the given amount of money is positive,
	 * 			and if this Bank Account isn't blocked, and if the old balance of this Bank Account decremented with the given amount of 
	 * 			money isn't below the credit limit, and if the old balance of the given destination account incremented with the given 
	 * 			amount of money isn't above the balance limit, the new balance of this Bank Account is equal to the old balance of this
	 * 			Bank Account decremented with the given amount of money.
	 *			| if ((amount > 0) && !isBlocked() || !destination.isBlocked()) && (destination != null) && (destination != this) 
	 * 			|			&& ((old.getBalance() - amount) >= getCreditLimit()) && ((old.destination.getBalance() + amount) <= getBalanceLimit())
	 * 			|	then new.getBalance() == old.getBalance() - amount
	 * @post	If the given destination account is effective and not the same as this Bank Account, and if the given amount of money is positive,
	 * 			and if this Bank Account isn't blocked, and if the old balance of this Bank Account decremented with the given amount of 
	 * 			money isn't below the credit limit, and if the old balance of the given destination account incremented with the given 
	 * 			amount of money isn't above the balance limit, the new balance of the destination account is equal to the old balance of the
	 * 			destination account incremented with the given amount of money.
	 * 			| if ((amount > 0) && !isBlocked() || !destination.isBlocked()) && (destination != null) && (destination != this) 
	 * 			|			&& ((old.getBalance() - amount) >= getCreditLimit()) && ((old.destination.getBalance() + amount) <= getBalanceLimit())
	 * 			|	then new.destination.getBalance() == old.destination.getBalance() + amount
	 * @note	Even though we have specified in the formal specfication that we add or substract the amount from the current Balance, we have 
	 * 			implemented otherwise. This is because sometimes this can cause overflows, which will cancel out these checkers.
	 */
	public void transferTo(long amount, BankAccount destination)
	{
		if (destination != null)
		{
			if ((amount > 0) && (!isBlocked() && !destination.isBlocked()) && (destination != this) && 
					(getBalance() >= getCreditLimit() + amount) && (destination.getBalance() <= getBalanceLimit() - amount))
			{
				withdraw(amount);
				destination.deposit(amount);
			}
		}
	}
	
	/**
	 * Set the balance of this Bank Account to the given balance.
	 * 
	 * @param 	balance
	 * 			The new balance for this Bank Account.
	 * @post	If the given balance isn't below the credit limit and not above the balance limit, the new 
	 * 			balance of this Bank Account is equal to the given balance.
	 * 			| if (balance >= getCreditLimit() && balance <= getBalanceLimit())
	 * 			|	then new.getBalance() == balance
	 */
	public void setBalance(long balance)
	{
		if (balance >= getCreditLimit() && balance <= getBalanceLimit())
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
	 * Variable registering the balance limit that applies to all Bank Accounts.
	 */
	private static final long balanceLimit = Long.MAX_VALUE;
	
	/**
	 * Check whether this Bank Account is blocked.
	 * 
	 * @note	Some methods have no effect when invoked against blocked Bank Accounts.
	 */
	@Basic
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
