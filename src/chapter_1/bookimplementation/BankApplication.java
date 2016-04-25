package chapter_1.bookimplementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class BankApplication {
	
	public static void main(String[] args) throws IOException 
	{
		BankAccount myAccount, yourAccount, hisAccount, herAccount;
		
		// Initialization:
		myAccount = new BankAccount(1234567, 1000, false);
		yourAccount = new BankAccount(1111111, -30000);
		hisAccount = new BankAccount(9876543);
		// herAccount = new BankAccount(true); // There is no constructor like this in BankAccount.
		herAccount = null;
		
		// Invocating instance methods:
		myAccount.deposit(10000);
		myAccount.withdraw(5000);
		
		long myBudget = myAccount.getBalance();
		
		myAccount.transferTo(myBudget,hisAccount);
		hisAccount.transferTo(100, herAccount); // Won't have any effect, because destination is ineffective.
		//herAccount.transferTo(100, myAccount); // Will throw a NullPointerException, since herAccount == null.
		yourAccount.transferTo(0, myAccount);
		
		// Invocating class methods:
		BankAccount.setCreditLimit(-1000);
		long creditLimit = BankAccount.getCreditLimit();
		System.out.println(creditLimit);
	}
	
	/**
	 * An example of Input and Output Streams.
	 * 
	 * @note	We have 3 different streams: Input, Print and Error. All 3 are objects from predefined classes:
	 * 				- InputStream: a class offerring few methods for reading data.
	 * 				- PrintStream: a class offerring a large set of methods for displaynig information of different types.
	 * 					(e.g. System.out.println(...))
	 * 			Since Java 5, you c can simmply use a Scanner object to parse values of primitive types and strings.
	 */
	public void Streams() throws IOException
	{
		// Old-fashioned code to read an integer from the standard input stream.
		BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(System.in));
		String firstLine = inputStreamReader.readLine();
		int myValue = Integer.parseInt(firstLine);
		System.out.println("Integer parsed using an InPutStreamReader: " + myValue);
		
		// Recommended code to read an integer from the standard input streamer since Java 5.
		Scanner inputStreamScanner = new Scanner(System.in);
		int yourValue = inputStreamScanner.nextInt();
		System.out.println("Integer parsed using an InputStreamScanner: " + yourValue);
		inputStreamScanner.close();
	}
	
	/**
	 * An example of value semantics.
	 * 
	 * @note	A primitive type stores the value itself, not a reference. 
	 * 			Comparing between these types uses their values instead of references.
	 */
	public void valueSemantics()
	{
		int myVal = 10;
		int yourVal = 15;
		int hisValue = 20;
		
		myVal = hisValue;
		hisValue = hisValue + yourVal;
		yourVal = 2*yourVal + 5;
		
		System.out.println("Expecting true on condition yourVal == hisValue: " + (yourVal == hisValue));
		System.out.println("Expecting false on condition myVal == hisValue: " + (myVal == hisValue));
	}
	
	/**
	 * An example of reference semantics.
	 * 
	 * @note	An object adapting reference semantics will simply store a pointer to the object, not the entire object value.
	 * 			Comparing between these objects uses their references instead of values.
	 */
	public void referenceSemantics()
	{
		BankAccount myAccount = null;
		final BankAccount yourAccount = new BankAccount(1234567, 15);
		BankAccount hisAccount = new BankAccount(7654321, 20);
		
		myAccount = hisAccount;
		hisAccount.setBalance(hisAccount.getBalance() + yourAccount.getBalance());
		
		System.out.println("Expecting true on condition myAccount == hisAccount: " + (myAccount == hisAccount));
		System.out.println("Expecting false on condition myAccount == yourAccont: " + (myAccount == yourAccount));
	}

	/**
	 * An example of boxing and unboxing.
	 */
	public void boxingAndUnboxing()
	{
		int myValue = 10;
		Integer myWrappedValue = new Integer(20);
		
		// Boxing
		Integer yourWrappedValue = myValue + 5;
		System.out.println(yourWrappedValue);
		// Unboxing
		int yourValue = myWrappedValue;
		System.out.println(yourValue);
	}

	/**
	 * An example of call by value semantics.
	 */
	public void callByValue()
	{
		BankAccount myAccount = new BankAccount(1234567, 1500);
		BankAccount yourAccount = new BankAccount(7654321, 2000);
		long myWeeklyBudget = 600;
		
		// myWeeklyBudget = value, yourAccount = reference.
		myAccount.transferTo(myWeeklyBudget, yourAccount);
	}
}
