package chapter_5.book_implementation.exceptions;

import be.kuleuven.cs.som.annotate.*;
import chapter_5.book_implementation.banking.*;

/**
 * A class of exceptions signaling illegal numbers for Bank Accounts.
 * 
 * @author 	Kevin Algoet & Eric Steegmans
 * @version	3.0
 * 
 * @note	Each Illegal Number Exception involves the illegal number and the Bank Account.
 * @note	Based on the code found in the book Object Oriented Programming with Java by Eric Steegmans.
 */
public class IllegalNumberException extends RuntimeException {

	/**
	 * @note	This serialVersionUID was created automatically and isn't included in this course. If you want more information
	 * 			look it up.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Initialize this new Illegal Number Exception with given number and given Bank Account.
	 * 
	 * @param 	number
	 * 			The number for this new Illegal Number Exception.
	 * @param 	account
	 * 			The Bank Account for this new Illegal Number Exception.
	 * @post	The number for this new Illegal Number Exception is equal to the given number.
	 * 			| new.getNumber() == number
	 * @post	The Bank Account for this new Illegal Number Exception.
	 * 			| new.getAccount() == account
	 * @effect	This new Illegal Number Exception is further initialized as a new Runtime Exception involving no diagnostic message
	 * 			and no cause.
	 * 			| super()
	 */
	public IllegalNumberException(int number, BankAccount account)
	{
		this.number = number;
		this.account = account;
	}
	
	/**
	 * Return the number of this Illegal Number Exception.
	 */
	@Basic @Immutable
	public int getNumber()
	{
		return number;
	}
	
	/**
	 * Variable registering the amount of this Illegal Amount Exception.
	 */
	private final int number;
	
	/**
	 * Return the Bank account of this Illegal Number Exception.
	 */
	@Basic @Raw @Immutable
	public BankAccount getAccount()
	{
		return account;
	}
	
	/**
	 * Variable referencing the Bank Account of this illegal Number Exception.
	 */
	private final BankAccount account;

}
