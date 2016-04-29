package chapter_5.book_implementation.exceptions;

import chapter_5.book_implementation.banking.BankCard;

public class IllegalPinCodeException extends RuntimeException {

	public IllegalPinCodeException(int code, BankCard bankCard) 
	{
		
	}

}
