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
		
		// Invocating class methods:
		BankAccount.setCreditLimit(-1000);
		long creditLimit = BankAccount.getCreditLimit();
		
		// The class of print streams:
		/*
		 * We have 3 different streams: Input, Print and Error. All 3 are objects from predefined classes:
		 * 		- InputStream: a class offerring few methods for reading data.
		 * 		- PrintStream: a class offerring a large set of methods for displaynig information of different types.
		 * 			(e.g. System.out.println(...))
		 * Since Java 5, you c can simmply use a Scanner object to parse values of primitive types and strings.
		 * See implementation below.
		 */
		
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

}
