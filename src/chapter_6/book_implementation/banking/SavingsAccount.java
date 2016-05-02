package chapter_6.book_implementation.banking;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of Savings Accounts attached to {@link BankAccount}s.
 * 
 * @author 	Kevin Algoet & Eric Steegmans
 * @version	6.0
 * @invar	Each Savings Account must have a proper Bank Account to which it is attached.
 * 			| hasProperBankAccount()
 * 
 * @note	Based on the code found in the book Object Oriented Programming with Java by Eric Steegmans.
 */
public class SavingsAccount {
	
	/**
	 * Initialize this new Savings Account not yet attached to a {@link BankAccount}.
	 */
	@Raw
	public SavingsAccount()
	{
		
	}
	
	/**
	 * Terminate this Savings Account.
	 * 
	 * @post	This Savings Account is terminated.
	 * 			| new.isTerminated()
	 */
	public void terminate()
	{
		this.isTerminated = true;
	}
	
	/**
	 * Check whether this Savings Account is terminated.
	 */
	public boolean isTerminated()
	{
		return this.isTerminated;
	}
	
	/**
	 * Variable registering whether or not this Savings Account is terminated.
	 */
	private boolean isTerminated = false;
	
	/**
	 * Return the Bank Account to which this Savings Account is attached.
	 * 
	 * @note	A null reference is returned if this Savings Account is not attached to any Bank Account.
	 */
	@Basic @Raw
	public BankAccount getBankAccount()
	{
		return this.bankAccount;
	}
	
	/**
	 * Check whether this Savings Account can be attached to the given {@link BankAccount}.
	 * 
	 * @param 	bankAccount
	 * 			The Bank Account to check.
	 * @return	True if and only if the given Bank Account isn't effective or if it can have this Savings Account as one
	 * 			of its Savings Account.
	 * 			| result == ( (bankAccount == null) || bankAccount.canHaveAsSavingsAccount(this) )
	 */
	@Raw
	public boolean canHaveAsBankAccount(BankAccount bankAccount)
	{
		return ((bankAccount == null) || (bankAccount.canHaveAsSavingsAccount(this)));
	}
	
	/**
	 * Check whether this Savings Account has a proper Bank Account to which it is attached.
	 * 
	 * @return	True if and only if this Savings Account can have its Bank Account as the Bank Account to which it 
	 * 			is attached, and if that Bank Account is either not effective or has this Savings Account as one
	 * 			of its Savings Accounts.
	 * 			| result == ( canHaveAsBankAccount(getBankAccount()) && ( (getBankAccount() == null)
	 * 			|				|| getBankAccount().hasAsSavingsAccount(this) ) )
	 */
	@Raw
	public boolean hasProperBankAccount()
	{
		return (canHaveAsBankAccount(getBankAccount()) && ((getBankAccount() == null) || getBankAccount().hasAsSavingsAccount(this)));
	}
	
	/**
	 * Set the {@link BankAccount} to which this Savings Account is attached to the given {@link BankAccount}.
	 * 
	 * @param 	bankAccount
	 * 			The {@link BankAccount} to attach this Savings Account to.
	 * @pre		If the given Bank Account is effective, it must already reference this Savings Account as one of its Savings Accounts.
	 * 			| if (bankAccount != null)
	 * 			|	then bankAccount.hasAsSavingsAccount(this)
	 * @pre		If the given Bank Account isn't effective and this Savings Account references an effective Bank Account, that Bank Account
	 * 			may not references this Savings Account as one of its Savings Accounts.
	 * 			| if ( (bankAccount == null) && (getBankAccount() != null) )
	 * 			|	then !getBankAccount().hasAsSavingsAccount(this)
	 * @post	This Savings Account references the given Bank Account as the Bank Account to which it is attached.
	 * 			| new.getBankAccount() == bankAccount
	 */
	@Raw
	public void setBankAccount(@Raw BankAccount bankAccount)
	{
		assert ((bankAccount == null) || bankAccount.hasAsSavingsAccount(this));
		assert ((bankAccount != null) || (getBankAccount() == null) || (!getBankAccount().hasAsSavingsAccount(this)));
		this.bankAccount = bankAccount;
	}
	
	/**
	 * Variable referencing the Bank Account to which this Savings Account is attached.
	 */
	private BankAccount bankAccount = null;
}
