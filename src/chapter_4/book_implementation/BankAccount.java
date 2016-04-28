package chapter_4.book_implementation;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of Bank Account involving a bank code, a number, a credit limit, a balance limit, a balance and a blocking facility.
 * 
 * @author 	Kevin Algoet & Eric Steegmans
 * @version	4.0
 *
 * @invar	The bank code that applies to all Bank Accounts must be a valid bank code.
 * 			| isValidBankCode(getBankCode())
 * @invar	Each Bank Account can have its number as its number.
 * 			| canHaveAsNumber(getNumber())
 * @invar	Each Bank Account can have its credit limit as its credit limit.
 * 			| canHaveAsCreditLimit(getCreditLimit())
 * @invar	Each Bank Account can have its balance as its balance.
 * 			| canHaveAsBalance(getBalance())
 * @note	Based on the code found in the book Object Oriented Programming with Java by Eric Steegmans.
 */
class BankAccount {
	
	/**
	 * Initialize this new Bank Account with given number, given credit limit, given balance and a given blocked state.
	 * 
	 * @param 	number
	 * 			The number for this new Bank Account.
	 * @param	creditLimit
	 * 			The credit limit for this new Bank Account.
	 * @param 	balance
	 * 			The balance for this new Bank Account.
	 * @param 	isBlocked
	 * 			The blocked state for this new Bank Account.
	 * @post	The new number of this new Bank Account is equal to the given number.
	 * 			| new.getNumber() == number
	 * @post	The new credit limit of this new Bank Account is equal to the given credit limit.
	 * 			| new.getrCreditLimit() == creditLimit
	 * @post	The new balance of this new Bank Account is equal to the given balance.
	 * 			| new.getBalance().equals(balance)
	 * @post	The new blocked state of this new Bank Account is equal to the given flag.
	 * 			| new.isBlocked() == isBlocked
	 * @throws	IllegalNumberException(credit, this)
	 * 			The given credit limit is not a valid credit limit for any Bank Account, or it doesn't match with the given balance.
	 * 			| ( (!isPossibleCreditLimit(creditLimit)) || (!matchesBalanceCreditLimit(balance, creditLimit)) )
	 * @throws	IllegalAmountException(balance, this)
	 * 			The given initial balance is not a valid balance for any Bank Account or it doesn't match with the given
	 * 			credit limit.
	 * 			| ( (!isPossibleBalance(balance)) || (!matchesBalanceCreditLimit(balance, creditLimit)) )
	 */
	@Raw
	public BankAccount(int number, MoneyAmount creditLimit, MoneyAmount balance, boolean isBlocked) 
			throws IllegalAmountException, IllegalNumberException
	{
		if (!canHaveAsNumber(number))
			throw new IllegalNumberException(number, this);
		this.number = number;
		if (!isPossibleCreditLimit(creditLimit))
			throw new IllegalAmountException(creditLimit, this);
		/*
		 * We could use the setter in this case to set the credit limit, but because of the dependency between balance and credit limit,
		 * we must initialise credit limit more directly, because otherwise the checker of canHaveAsCreditLimit might fail, because the
		 * matchesBalanceCreditLimit checker might fail because balance hasn't been initialised yet.
		 */
		this.creditLimit = creditLimit;
		setBalance(balance);
		setBlocked(isBlocked);
		allAccountNumbers.add(number);
	}
	
	/**
	 * Initialize this new Bank Account as an unblocked account with given number with a credit limit of -1000.00 EUR and 
	 * with a balance of 0.00 EUR.
	 * 
	 * @param 	number
	 * 			The number for this new Bank Account.
	 * @effect	This new Bank Account is initialized with the given number as its number, with -1000.00 EUR as its credit limit,
	 * 			with 0 EUR as its balance, and false as its initial blocked state.
	 * 			| this(number, new {@link MoneyAmount}(new BigDecimal(1000)), {@link MoneyAmount}.EUR_0, false)
	 */
	public BankAccount(int number)
	{
		this(number, new MoneyAmount(new BigDecimal(-1000)), MoneyAmount.EUR_0, false);
	}
	
	/**
	 * Terminate this bank account.
	 * 
	 * @note    We need a terminator to recuperate account numbers that are no longer in use. The semantics of this type
	 *          of method is discussed in more detail in part II (chapter 5).
	 */
	public void terminate()
	{
		allAccountNumbers.remove(this.getNumber());
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
		return (number > 0) && ( (this.getNumber() == number) || (! allAccountNumbers.contains(number)));
	}
	
	/**
	 * Variable registering the number of this Bank Account.
	 */
	private final int number;
	
	/**
	 * Variable referencing a set collecting all account numbers that have been used so far.
	 * 
	 * @note	More information on sets and other data structures is given starting from chapter 5.
	 */
	private static final Set<Integer> allAccountNumbers = new HashSet<Integer>();
	
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
	public MoneyAmount getBalance()
	{
		return this.balance;
	}
	
	/**
	 * Check whether this Bank Account has a higher balance than the given amount of money.
	 * 
	 * @param 	amount
	 * 			The amount of money to compare with.
	 * @return	True if and only if the balance of this Bank Account is greater than the given amount.
	 * 			| result == (this.getBalance().compareTo(amount) > 0)
	 * @throws	IllegalArgumentException
	 * 			The given amount isn't effective or its currency differs from the currency of this Bank Account.
	 * 			| ( (amount == null)  || (amount.getCurrency() != this.getBalance().getCurrency()) )
	 */
	public boolean hasHigherBalanceThan(MoneyAmount amount) throws IllegalArgumentException
	{
		if (amount == null)
			throw new IllegalArgumentException("Non-effective amount!");
		if (amount.getCurrency() != getBalance().getCurrency())
			throw new IllegalArgumentException("Different Currencies!");
		return getBalance().compareTo(amount) > 0;
	}
	
	/**
	 * Check whether this Bank Account has a higher balance than the other Bank Account.
	 * 
	 * @param 	other
	 * 			The Bank Account to compare with.
	 * @return	True if and only if this Bank Account has a higher balance than the balance of the other Bank Account.
	 * 			| result == this.hasHigherBalanceThan(other.getBalance())
	 * @throws	IllegalArgumentException
	 * 			The other Bank Account isn't effective.
	 * 			| other == null
	 * @throws	IllegalArgumentException
	 * 			A condition was violated or an error was thrown.
	 */
	public boolean hasHigherBalanceThan(BankAccount other) throws IllegalArgumentException
	{
		if (other == null)
			throw new IllegalArgumentException("Non effective Bank Account!");
		return hasHigherBalanceThan(other.getBalance());
	}
	
	/**
	 * Return a boolean reflecting whether this Bank Account can accept the given amount for deposit.
	 * 
	 * @param 	amount
	 * 			The amount to be checked.
	 * @return	True if and only if the given amount is effective and positive.
	 * 			| result == ( (amount != null) && (amount.signum() > 0) )  
	 */
	public boolean canAcceptForDeposit(MoneyAmount amount)
	{
		return ((amount != null) && (amount.signum() > 0));
	}
	
	/**
	 * Deposit the given amount of money to this Bank Account.
	 * 
	 * @param	amount
	 * 			The amount of money to be deposited.
	 * @post	The new balance of this Bank Account is equal to the old balance of this Bank Account incremented with the given
	 * 			amount of money.
	 * 			| new.getBalance() == this.getBalance().add(amount)
	 * @throws	IllegalAmountException(amount, this)
	 * 			This Bank Account cannot accept the given amount for deposit.
	 * 			| !canAcceptForDeposit(amount)
	 * 
	 * @note	Even though we have specified a post condition, it would be better to specify an effect clause,
	 * 			because we actually invoke another method to change the balance of this Bank Account. See below for effect clause:
	 * @effect	The new balance of this Bank Account is set to the old balance of this Bank Account incremented with the given amount of money.
	 * 			| this.setBalance(getBalance().add(amount))
	 */
	public void deposit(MoneyAmount amount) throws IllegalAmountException
	{
		if (!canAcceptForDeposit(amount))
			throw new IllegalAmountException(amount, this);
		setBalance(getBalance().add(amount));
	}
	
	/**
	 * Return a boolean reflecting whether this Bank Account can accept the given amount for withdraw.
	 * 
	 * @param 	amount
	 * 			The amount to be checked.
	 * @return	True if and only if the given amount is effective and positive, if this Bank Account isn't blocked, and if this
	 * 			Bank Account can have its current balance decremented with the given amount as its balance.
	 * 			| result == ( (amount != null) && (amount.signum() > 0)&& !isBlocked() && canHaveAsBalance(getBalance().subtract(amount)) )
	 */
	public boolean canAcceptForWithdraw(MoneyAmount amount)
	{
		return ((amount != null) && (amount.signum() > 0)&& !isBlocked() && canHaveAsBalance(getBalance().subtract(amount)));
		
	}
	
	/**
	 * Withdraw the given amount of money from this Bank Account.
	 * 
	 * @param 	amount
	 * 			The amount of money to be withdrawn
	 * @post	The new balance of this Bank Account is equal to the old balance of this Bank Account decremented with the given amount of money.
	 * 			| new.getBalance() == this.getBalance().subtract(amount)
	 * @throws	IllegalAmountException
	 * 			This Bank Account cannot accept the given amount for withdraw.
	 * 			| !canAcceptForWithdraw(amount)
	 * 
	 * @note	Even though we have specified a post condition, it would be better to specify an effect clause,
	 * 			because we actually invoke another method to change the balance of this Bank Account. See below for effect clause:
	 * @effect	The new balance of this Bank Account is set to the old balance of this Bank Account decremented with the given amount of money.
	 * 			| this.setBalance(getBalance().subtract(amount))
	 */
	public void withdraw(MoneyAmount amount) throws IllegalAmountException
	{
		if (!canAcceptForWithdraw(amount))
			throw new IllegalAmountException(amount, this);
		setBalance(getBalance().subtract(amount)); 
	}
	
	/**
	 * Transfer the given amount of money from this Bank Account to the given destination account.
	 * 
	 * @param 	amount
	 * 			The amount of money to be transferred.
	 * @param 	destination
	 * 			The Bank Account to transfer the money to.
	 * @effect	The given amount of money is withdrawn from this Bank Account, and deposited to the given destination account.
	 * 			| this.withdraw(amount) && destination.deposit(amount)
	 * @throws	IllegalArgumentException
	 * 			The given destination account isn't effective or it is the same as this Bank Account.
	 * 			| (destination == null) || (destination == this)
	 * @throws	IllegalAmountException
	 * 			A condition was violated or an exception was thrown.
	 */
	public void transferTo(MoneyAmount amount, BankAccount destination) throws IllegalAmountException, IllegalArgumentException
	{
		boolean partialTransfer = false;
		try 
		{
			if ((destination == null) || (destination == this))
				throw new IllegalArgumentException("Illegal destination!");
			withdraw(amount);
			partialTransfer = true;
			destination.deposit(amount);
			partialTransfer = false;
		}
		finally 
		{
			if (partialTransfer)
				deposit(amount);
		}
	}
	
	/**
	 * Check whether the given balance is a possible balance for any Bank Account.
	 * 
	 * @param 	balance
	 * 			The balance to check.
	 * @return	True if and only if the given balance is effective.
	 * 			| result == (balance != null)
	 */
	public static boolean isPossibleBalance(MoneyAmount balance)
	{
		return balance != null;
	}
	
	/**
	 * Check whether the given balance is a valid balance for any Bank Account.
	 * 
	 * @param 	balance
	 * 			The balance to check.
	 * @return	True if and only if the given balance is a possible balance for any Bank Account, and if the given balance
	 * 			matches with the credit limit of this account.
	 * 			| result == (isPossibleBalance(balance) && matchesBalanceCreditLimit(balance, getCreditLimit()))
	 */
	@Raw
	public boolean canHaveAsBalance(MoneyAmount balance)
	{
		return isPossibleBalance(balance) && matchesBalanceCreditLimit(balance, getCreditLimit());
	}
	
	/**
	 * Check whether the given balance matches with the given credit limit.
	 * 
	 * @param 	balance
	 * 			The balance to check.
	 * @param 	creditLimit
	 * 			The credit limit to check.
	 * @return	True if and only if the given balance and the given credit limit are both effective, if they use the same currency
	 * 			and if the given balance exceeds the given credit limit.
	 * 			| result == (balance > creditLimit)
	 */
	public static boolean matchesBalanceCreditLimit(MoneyAmount balance, MoneyAmount creditLimit)
	{
		return (isPossibleBalance(balance) && (creditLimit != null) && (balance.getCurrency() == creditLimit.getCurrency())
				&& (balance.compareTo(creditLimit) > 0));
	}
	
	/**
	 * Set the balance of this Bank Account to the given balance.
	 * 
	 * @param 	balance
	 * 			The new balance for this Bank Account.
	 * @post	The new balance of this Bank Account is equal to the given balance.
	 * 			| new.getBalance() == balance 
	 * @throws	IllegalAmountException
	 * 			This Bank Account cannot have the given balance as its balance.
	 * 			| !canHaveAsBalance(balance)
	 * 
	 * @note	We make this method private, because deposit and withdraw control this function. We also add a @Model tag to this method to 
	 * 			specify it is used in the specifications of other methods.
	 */
	@Raw @Model
	private void setBalance(MoneyAmount balance)
	{
		if (!canHaveAsBalance(balance))
			throw new IllegalAmountException(balance, this);
		this.balance = balance;
	}
	
	/**
	 * Variable registering the balance of this Bank Account;
	 */
	private MoneyAmount balance;
	
	/**
	 * Return the credit limit for this Bank Account.
	 * 
	 * @note	The credit limit expresses the lowest possible value for the balance of any Bank Account.
	 */
	@Basic @Raw
	public MoneyAmount getCreditLimit()
	{
		return creditLimit;
	}
	
	/**
	 * Check whether the given credit limit is a possible credit limit for any Bank Account.
	 * 
	 * @param 	creditLimit
	 * 			The credit limit to check.
	 * @return	True if and only if the given credit limit is effective and not positive.
	 * 			| result == ( (creditLimit != null) && (creditLimit.signum() <= 0) )
	 */
	public static boolean isPossibleCreditLimit(MoneyAmount creditLimit)
	{
		return ((creditLimit != null) && (creditLimit.signum() <= 0));
	}
	
	/**
	 * Check whether this Bank Account can have the given credit limit as its credit limit.
	 * 
	 * @param 	creditLimit
	 * 			The credit limit to check.
	 * @return	True if and only if the given credit limit is a possible credit limit for this Bank Account, and if the given 
	 * 			credit limit matches with the balance of this Bank Account.
	 * 			| result == ( isPossibleCreditLimit(creditLimit) && matchesBalanceCreditLimit(getBalance(), creditLimit) )
	 */
	@Raw
	public boolean canHaveAsCreditLimit(MoneyAmount creditLimit)
	{
		return isPossibleCreditLimit(creditLimit) && matchesBalanceCreditLimit(getBalance(), creditLimit);
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
	 * @throws	IllegalAmountException
	 * 			This Bank Account cannot have the given credit limit as its credit limit.
	 * 			| !canHaveAsCreditLimit(creditLimit)
	 */
	@Raw
	public void setCreditLimit(MoneyAmount creditLimit) throws IllegalAmountException
	{
		if (!canHaveAsCreditLimit(creditLimit))
			throw new IllegalAmountException(creditLimit, this);
		this.creditLimit = creditLimit;
	}
	
	/**
	 * Variable registerting the credit limit for this Bank Account.
	 */
	private MoneyAmount creditLimit = MoneyAmount.EUR_0;
	
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
	
	/**
	 * Return a textual representation of this Bank Account.
	 * 
	 * @return	The resulting string mentions "Bank Account" as the name of the class of this Bank Account.
	 * 			| result.contains("Bank Account")
	 * @return	The resulting string contains the number of this Bank Account.
	 * 			| result.contains(String.valueOf(getNumber())
	 */
	@Override
	public String toString()
	{
		return "Bank Account\n" + " number: " + getNumber() + "\n";
	}
	
	/**
	 * Return a clone of this Bank Account.
	 * 
	 * @throws	CloneNotSupportedException
	 * 			Always.
	 * 			| true
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		throw new CloneNotSupportedException("Bank Accounts cannot be cloned.");
	}
}
