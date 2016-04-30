package chapter_5.book_implementation.banking;

import be.kuleuven.cs.som.annotate.*;
import chapter_5.book_implementation.money.*;
import chapter_5.book_implementation.exceptions.*;

/**
 * A class dealing with Bank Cards involving a pin code and a {@link BankAccount} to which they are attached.
 * 
 * @author 	Kevin Algoet & Eric Steegmans
 * @version	5.0
 * 
 * @invar	Each Bank Card must have exactly one valid pin code.
 * 			| for one code in int:
 * 			| (isValidPinCode(code) && hasAsPinCode(code))
 * @invar	Each Bank Card must have a proper Bank Account to which it is attached.
 * 			| hasProperAccount()
 * 
 * @note	Based on the code found in the book Object Oriented Programming with Java by Eric Steegmans.
 */
public class BankCard {
	
	/**
	 * Initialize this new Bank Card with the given pin code and attached to the given {@link BankAccount}.
	 * 
	 * @param 	account
	 * 			The {@link BankAccount} to which this new Bank Card must be attached.
	 * @param 	code
	 * 			The pin code for this new Bank Card.
	 * @effect	This new Bank Card is transferred to the given {@link BankAccount}.
	 * 			| transferTo(account)
	 * @effect	The given pin code is set as the pin code for this new Bank Card.
	 * 			| setPinCode(code)
	 */
	@Raw
	public BankCard(BankAccount account, int code)
	{
		transferTo(account);
		setPinCode(code);
	}
	
	/**
	 * Terminate this Bank Card.
	 * 
	 * @post	This Bank Card is terminated.
	 * 			| new.isTerminated()
	 * @post	This Bank Card no longer references a {@link BankAccount} as the account to which it is attached.
	 * 			| new.getAccount() == null
	 * @post	If this Bank Card wasn't already terminated, the {@link BankAccount} to which this Bank Card was attached no
	 * 			longer has a Bank Card attached to it.
	 * 			| if (!isTerminated())
	 * 			|	then (!(new getAccount()).hasBankCard())
	 */
	public void terminate()
	{
		if (!isTerminated())
		{
			BankAccount formerAccount = getAccount();
			this.isTerminated = true;
			setAccount(null);
			formerAccount.setBankCard(null);
		}
	}
	
	/**
	 * Check whether this Bank Card is terminated.
	 */
	@Basic @Raw
	public boolean isTerminated()
	{
		return this.isTerminated;
	}
	
	/**
	 * Variable registerting whether this Bank Card is terminated.
	 */
	private boolean isTerminated = false;
	
	/**
	 * Return the {@link BankAccount} to which this Bank Card is attached.
	 */
	@Basic @Raw
	public BankAccount getAccount()
	{
		return this.account;
	}
	
	/**
	 * Check whether this Bank Card can have the given {@link BankAccount} as its {@link BankAccount}.
	 * 
	 * @param 	account
	 * 			The {@link BankAccount} to check.
	 * @return	If this Bank Card is terminated, true if and only if the given {@link BankAccount} isn't effective.
	 * 			| if (isTerminated())
	 * 			|	then result == (account == null)
	 * 			Otherwise, true if and only if the given {@link BankAccount} is effective and not yet terminated.
	 * 			| else result == ( (account != null) && (!account.isTerminated()) )
	 */
	@Raw
	public boolean canHaveAsAccount(@Raw BankAccount account)
	{
		if (isTerminated())
			return account == null;
		if (account == null)
			return true;
		return !account.isTerminated();
	}
	
	/**
	 * Check whether this Bank Card has a proper {@link BankAccount} to which it is attached.
	 *  
	 * @return	True if and only if this Bank Card can have the {@link BankAccount} to which it is attached as its {@link BankAccount},
	 * 			and either this Bank Card isn't attached to a {@link BankAccount}, or that {@link BankAccount} references this Bank Card
	 * 			as its Bank Card.
	 * 			| result == ( canHaveAsAccount(getAccount()) && ( (getAccount() == null) || (getAccount().getBankCard() == this) ) )
	 */
	@Raw
	public boolean hasProperAccount()
	{
		return canHaveAsAccount(getAccount()) && ((getAccount() == null) || (getAccount().getBankCard() == this));
	}
	
	/**
	 * Transfer this Bank Card to the given {@link BankAccount}.
	 * 
	 * @param 	account
	 * 			The {@link BankAccount} to which this Bank Card must be attached.
	 * @effect	If the given account isn't equal to the account of this Bank Card, set the account of this Bank Card
	 * 			to the given account.
	 * 			| if (account != this.getAccount())
	 * 			| 	then this.setAccount(account)
	 * @effect	If the given account isn't equal to the account of this Bank Card, set the Bank Card of the given 
	 * 			account to this Bank Card.
	 * 			| if (account != this.getAccount())
	 * 			|	then (new account).setBankCard(this)
	 * @post	If the given account isn't equal to the account of this Bank Card, and if this Bank Card was attached to some other 
	 * 			{@link BankAccount}, that account no longer references this Bank Card as the Bank Card attached to it.
	 * 			| if ( (getAccount() != null) && (getAccount() != account) )
	 * 			|	then ( !(new getAccount()).hasBankCard() )
	 * @throws 	IllegalStateException
	 * 			This Bank Card is already terminated.
	 * 			| this.isTerminated()
	 * @throws 	IllegalAccountException
	 * 			This Bank Card cannot have the give account as the {@link BankAccount} to be attached to,
	 * 			or the given account already has another {@link BankCard} attached to it.
	 * 			| ( (!this.canHaveAsAccount(account)) || (account.hasBankCard() && (account.getBankCard() != this)) )
	 */
	@Raw
	public void transferTo(BankAccount account) throws IllegalStateException, IllegalAccountException
	{
		if (isTerminated())
			throw new IllegalStateException("Terminated card!");
		if (!canHaveAsAccount(account))
			throw new IllegalAccountException(account, this);
		if (account.hasBankCard() && (account.getBankCard() != this))
			throw new IllegalAccountException(account, this);
		if (account != getAccount())
		{
			if (getAccount() != null)
			{
				BankAccount formerAccount = getAccount();
				setAccount(account);
				formerAccount.setBankCard(null);
			}
			if (getAccount() != account)
				setAccount(account);
			account.setBankCard(this);
		}
	}

	/**
	 * Set the {@link BankAccount} to which this Bank Card is attached to this given {@link BankAccount}.
	 * 
	 * @param 	account
	 * 			The {@link BankAccount} to which this Bank Card must be attached.
	 * @post	This Bank Card is attached to the given {@link BankAccount}.
	 * 			| new.getAccount() == account
	 * @throws 	IllegalAccountException
	 * 			This Bank Card cannot have the given account as the {@link BankAccount} to be attached to.
	 * 			| !canHaveAsAccount(account)
	 */
	@Raw @Model
	private void setAccount(@Raw BankAccount account) throws IllegalAccountException
	{
		if (!canHaveAsAccount(account))
			throw new IllegalAccountException(account, this);
		this.account = account;
	}
	
	/**
	 * Variable referencing the {@link BankAccount} to which this Bank Card is attached.
	 */
	private BankAccount account;
	
	/**
	 * Check whether this Bank Card has the given code as its pin code.
	 * 
	 * @param 	code
	 * 			The code to verify.
	 * @return	True if and only if the given code is equal to this Bank Card's pincode.
	 * 			| result == (this.getPincode() == code)
	 */
	@Basic @Raw
	public boolean hasAsPinCode(int code)
	{
		return (getPinCode() == code);
	}
	
	/**
	 * Check whether the given code is a valid pin code for any Bank Card.
	 * 
	 * @param 	code
	 * 			The code to check.
	 * @return	False if the given code is negative or if it exceeds 9999.
	 * 			| if ( (code < 0) || (code > 9999) )
	 * 			|	then result == false
	 */
	public static boolean isValidPinCode(int code)
	{
		return (code >= 0) && (code <= 9999);
	}
	
	/**
	 * Set the given code as the pin code for this Bank Card.
	 * 
	 * @param 	code
	 * 			The new pin code for this Bank Card.
	 * @post	This Bank Card has the given code as its pin code.
	 * 			| new.hasAsPinCode(code)
	 * @throws 	IllegalPinCodeException
	 * 			The given code isn't a valid pin code for any Bank Card.
	 * 			| !isValidPinCode(code)
	 */
	@Raw
	public void setPinCode(int code) throws IllegalPinCodeException
	{
		if (!isValidPinCode(code))
			throw new IllegalPinCodeException(code, this);
		this.pinCode = code;
	}
	
	/**
	 * Return the pin code registered for this Bank Card.
	 */
	private int getPinCode()
	{
		return this.pinCode;
	}
	
	/**
	 * Variable registering the pin code for this Bank Card.
	 */
	private int pinCode;
	
	/**
	 * Withdraw the given amount from the {@link BankAccount} to which this Bank Card is attached.
	 * 
	 * @param 	amount
	 * 			The {@link MoneyAmount} to be withdrawn.
	 * @param 	code
	 * 			The code to be checked against the pin code of this Bank Card.
	 * @effect	The given amount is withdrawn from the {@link BankAccount} to which this Bank Card is attached.
	 * 			| getAccount().withdraw(amount)
	 * @throws 	IllegalPinCodeException
	 * 			This Bank Card doesn't have the given code as its pin code.
	 * 			| !hasAsPinCode(code)
	 * @throws 	IllegalStateException
	 * 			This Bank Card is already terminated.
	 * 			| isTerminated()
	 * @throws 	IllegalAmountException
	 * 			A condition was violated or an error was thrown.
	 */
	public void withdraw(MoneyAmount amount, int code) throws IllegalPinCodeException, IllegalStateException, IllegalAmountException
	{
		if (isTerminated())
			throw new IllegalStateException("Terminated card!");
		if (!hasAsPinCode(code))
			throw new IllegalPinCodeException(code, this);
		getAccount().withdraw(amount);
	}
}
