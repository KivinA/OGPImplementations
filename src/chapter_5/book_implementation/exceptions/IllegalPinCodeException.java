package chapter_5.book_implementation.exceptions;

import be.kuleuven.cs.som.annotate.*;
import chapter_5.book_implementation.banking.BankCard;

/**
 * A class of exceptions signaling illegal pin codes for {@link BankCard}s.
 * 
 * @author 	Kevin Algoet & Eric Steegmans
 * @version	5.0
 * 
 * @note	Based on the code found in the book Object Oriented Programming with Java by Eric Steegmans.
 * @note	Each Illegal PinCode Exception involves the illegal pin code and the {@link BankCard}.
 */
@SuppressWarnings("serial")
public class IllegalPinCodeException extends RuntimeException {
	
	/**
	 * Initialize this new Illegal PinCode Exception with given pin code and given {@link BankCard}.
	 * 
	 * @param 	code
	 * 			The pin code for this new Illegal PinCode Exception.
	 * @param 	bankCard
	 * 			The {@link BankCard} for this Illegal PinCode Exception.
	 * @post	The pin code fo this new Illegal PinCode Exception is equal to the given code.
	 * 			| new.getPinCode() == code
	 * @post	The {@link BankCard} of this new Illegal PinCode Exception is equal to the given {@link BankCard}.
	 * 			| new.getBankCard() == card
	 * @effect	This new Illegal PinCode Exception is further initialized as a new {@link RuntimeException} involving
	 * 			no diagnostic message and no cause.
	 * 			| super()
	 */
	public IllegalPinCodeException(int code, BankCard bankCard) 
	{
		this.pinCode = code;
		this.card = bankCard;
	}
	
	/**
	 * Return the pin code of this Illegal PinCode Exception.
	 */
	@Basic @Immutable
	public int getPinCode()
	{
		return this.pinCode;
	}
	
	/**
	 * Variable registering the pin code of this Illegal PinCode Exception.
	 */
	private final int pinCode;
	
	/**
	 * Return the {@link BankCard} of this Illegal PinCode Exception.
	 */
	@Basic @Immutable
	public BankCard getBankCard()
	{
		return this.card;
	}
	
	/**
	 * Variable referencing the Bank Card of this Illegal PinCode Exception.
	 */
	private final BankCard card;
}
