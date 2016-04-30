package chapter_5.book_implementation.exceptions;

import chapter_5.book_implementation.state.*;
import be.kuleuven.cs.som.annotate.*;
import chapter_5.book_implementation.banking.*;

/**
 * A class of exceptions signaling illegal holders for {@link BankAccount}s.
 * 
 * @author 	Kevin Algoet & Eric Steegmans
 * @version	5.0
 * 
 * @note	Based on the code found in the book Object Oriented Programming with Java by Eric Steegmans.
 * @note	Each Illegal Holder Exception involves the illegal holder and the {@link BankAccount}.
 */
@SuppressWarnings("serial")
public class IllegalHolderException extends RuntimeException{
	
	/**
	 * Initialize this new Illegal Holder Exception with the given holder and given {@link BankAccount}.
	 * 
	 * @param 	holder
	 * 			The holder for this new Illegal Holder Exception.
	 * @param 	account
	 * 			The {@link BankAccount} for this new Illegal Holder Exception.
	 * @post	The holder of this new Illegal Holder Exception is the same as the given holder.
	 * 			| new.getHolder() == holder
	 * @post	The {@link BankAccount} of this new Illegal Holder Exception is the same as the given {@link BankAccount}.
	 * 			| new.getAccount() == account
	 * @effect	This new Illegal Holder Exception is further initialized as a new {@link RuntimeException} involving no diagnostic message
	 * 			and no cause.
	 * 			| super()
	 */
	public IllegalHolderException(Person holder, BankAccount account) 
	{
		this.holder = holder;
		this.account = account;
	}
	
	/**
	 * Return the holder of this Illegal Holder Exception.
	 */
	@Basic @Immutable
	public Person getHolder()
	{
		return this.holder;
	}
	
	/**
	 * Variable referencing the holder of this Illegal Holder Exception.
	 */
	private final Person holder;
	
	/**
	 * Return the {@link BankAccount} of this Illegal Holder Exception.
	 */
	@Basic @Immutable
	public BankAccount getAccount()
	{
		return this.account;
	}
	
	/**
	 * Variable referencing the {@link BankAccount} of this Illegal Holder Exception.
	 */
	private final BankAccount account;
}
