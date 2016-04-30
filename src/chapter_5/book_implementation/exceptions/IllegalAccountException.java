package chapter_5.book_implementation.exceptions;

import be.kuleuven.cs.som.annotate.*;
import chapter_5.book_implementation.banking.*;

/**
 * A class of exceptions signaling illegal {@link BankAccount}s for {@link BankCard}s.
 * 
 * @author 	Kevin Algoet & Eric Steegmans
 * @version	5.0
 * 
 * @note	Based on the code found in the book Object Oriented Programming with Java by Eric Steegmans.
 * @note	Each Illegal Account Exception involves the Illegal {@link BankAccount} and the {@link BankCard}.
 */
@SuppressWarnings("serial") // Supress the serial UID warning.
public class IllegalAccountException extends RuntimeException{
	
	/**
	 * Initialize this new Illegal Account Exception with given {@link BankAccount} and given {@link BankCard}.
	 * 
	 * @param 	account
	 * 			The {@link BankAccount} for this new Illegal Account Exception.
	 * @param 	card
	 * 			The {@link BankCard} for this new Illegal Account Exception.
	 * @post	The account of this new Illegal Account Exception is the same as the given account.
	 * 			| new.getAccount() == account
	 * @post	The {@link BankCard} of this new Illegal Account Exception is the same as the given {@link BankCard}.
	 * 			| new.getBankCard() == card
	 * @effect	This new Illegal Account Exception is further initialized as a new {@link RuntimeException} involving
	 * 			no diagnostic message and no cause.
	 * 			| super()
	 */
	public IllegalAccountException(BankAccount account, BankCard card) 
	{
		this.account = account;
		this.card = card;
	}
	
	/**
	 * Return the account of this Illegal Account Exception.
	 */
	@Basic @Immutable
	public BankAccount getAccount()
	{
		return this.account;
	}
	
	/**
	 * Variable referencing the account of this Illegal Account Exception.
	 */
	private final BankAccount account;
	
	/**
	 * Return the {@link BankCard} of this Illegal Acount Exception.
	 */
	public BankCard getBankCard()
	{
		return this.card;
	}
	
	/**
	 * Variable referencing the account of this Illegal Account Exception.
	 */
	private BankCard card;
}
