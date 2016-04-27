package chapter_3.book_implementation;

/**
 * A class involving experiments with the semantics of try-catch-finally clauses.
 * 
 * @version	3.0
 * @author 	Kevin Algoet & Eric Steegmans
 * @note	Based on the code found in the book Object Oriented Programming with Java by Eric Steegmans.
 */
public class SomeClass {
	
	private SomeClass(int value)
	{
		this.value = value;
	}
	
	public void someMethod(int arg)
	{
		try
		{
			if (arg == 0)
				throw new IllegalArgumentException();
			value *= 2;
			if (arg < 0)
				throw new ArithmeticException();
			value += arg;
		}
		catch (ArithmeticException exc)
		{
			if (arg >= -100)
				value += 100;
			else
				throw exc;
		}
		finally 
		{
			value--;
		}
	}
	
	private int value = 0;
	
	public static void main(String[] args)
	{
		SomeClass theObject;
		
		// No exceptions thrown:
		theObject = new SomeClass(12);
		theObject.someMethod(54);
		assert theObject.value == (12*2) + 54 - 1;
		
		// Illegal Argument Exception thrown because of 0 argument.
		try 
		{
			theObject = new SomeClass(12);
			theObject.someMethod(0);
		}
		catch (IllegalArgumentException exc)
		{
			assert theObject.value == 12 - 1;
		}
		
		// Arithemtic Exception thrown and re-thrown because of too negative argument.
		try
		{
			theObject = new SomeClass(12);
			theObject.someMethod(-100);
		}
		catch (IllegalArgumentException exc)
		{
			assert theObject.value == 12 * 2 - 1;
		}
		
		// Arithemetic exception thrown and caught because of slightly negative argument.
		theObject = new SomeClass(12);
		theObject.someMethod(-44);
		assert theObject.value == 12 * 2 + 100 - 1;
	}
}
