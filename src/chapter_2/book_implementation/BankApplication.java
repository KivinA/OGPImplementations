package chapter_2.book_implementation;

import java.util.Scanner;

public class BankApplication {
	public static void main(String args[])
	{
		BankAccount myAccount;
		Scanner inputStreamScanner = new Scanner(System.in);
		
		// Create a Bank Account with given initial balance:
		System.out.println("Enter initial balance: ");
		long balance = inputStreamScanner.nextInt();
		myAccount = new BankAccount(1234567, balance);
		
		// Deposit a given amount of money: 
		System.out.println("Enter deposit amount: ");
		long amount = inputStreamScanner.nextInt();
		myAccount.deposit(amount);
		
		// Withdraw a given amount of money:
		System.out.println("Enter withdraw amount: ");
		amount = inputStreamScanner.nextInt();
		myAccount.withdraw(amount);
		
		// Display the balance:
		System.out.println("Final balance: " + myAccount.getBalance());
		inputStreamScanner.close();
	}
}
