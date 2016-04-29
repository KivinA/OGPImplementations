package chapter_5.book_implementation.exceptions;

import be.kuleuven.cs.som.annotate.*;
import chapter_5.book_implementation.banking.*;
import chapter_5.book_implementation.money.*;

/**
 * A class of exceptions signaling illegal amounts in transactions for Bank Accounts.
 * 
 * @author 	Kevin Algoet & Eric Steegmans
 * @version	3.0
 * 
 * @note	Each Illegal Amount Exception involves the illegal amount and the Bank Account.
 * @note	Based on the code found in the book Object Oriented Programming with Java by Eric Steegmans.
 */
public class IllegalAmountException extends RuntimeException {
	
	/**
	 * @note	This serialVersionUID was created automatically and isn't included in this course. If you want more information
	 * 			look it up.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Initialize this new Illegal Amount Exception with the given amount and given Bank Account.
	 * 
	 * @param 	amount
	 * 			The amount for this new Illegal Amount Exception.
	 * @param 	account
	 * 			The Bank Account for this new Illegal Amount Exception.
	 * @post	The amount of this new Illegal Amount Exception is equal to the given amount.
	 * 			| new.getAmount() == amount
	 * @post	The Bank Account of this new Illegal Amount Exception is the same as the given Bank Account.
	 * 			| new.getAccount == account
	 * @effect	This new Illegal Amount Exception is further initialized as a new Runtime Exception involving no diagnostic message
	 * 			and no cause.
	 * 			| super()
	 */
	public IllegalAmountException(MoneyAmount amount, BankAccount account)
	{
		this.amount = amount;
		this.account = account;
	}
	
	/**
	 * Return the amount of this Illegal Amount Exception.
	 */
	@Basic @Raw @Immutable
	public MoneyAmount getAmount()
	{
		return amount;
	}
	
	/**
	 * Variable registering the amount of this Illegal Amount Exception.
	 */
	private final MoneyAmount amount;
	
	/**
	 * Return the Bank account of this Illegal Amount Exception.
	 */
	@Basic @Raw @Immutable
	public BankAccount getAccount()
	{
		return account;
	}
	
	/**
	 * Variable referencing the Bank Account of this illegal Amount Exception.
	 */
	private final BankAccount account;
}
