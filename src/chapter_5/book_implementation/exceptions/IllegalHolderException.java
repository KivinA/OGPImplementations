package chapter_5.book_implementation.exceptions;

import chapter_5.book_implementation.state.*;
import chapter_5.book_implementation.banking.*;

public class IllegalHolderException extends RuntimeException{

	public IllegalHolderException(Person holder, BankAccount bankAccount) {
	}
	
}
