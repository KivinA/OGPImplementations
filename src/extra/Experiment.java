package extra;
import extra.trees.*;
import extra.trees.exceptions.*;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

public class Experiment {
	
	public static void main(String args[]) {
		externalIteration();
		
		internalIteration();
		
//		experimentIterators();
	}
	
	public static void externalIteration() {
		try {
			// Create a search tree with some elements.
			BinaryTree myTree = new ComposedSearchTree(1,1,2,3,5,8,13,21,34,55);
			
			System.out.println("Enter factor: ");
			Scanner scanner = new Scanner(System.in);
			int factor = scanner.nextInt();
			
			// Compute the sum of all the Fibonacci numbers in the tree that can be
			// divided by the given factor.
			Iterator<Object> myTreeEnumerator = myTree.iterator();
			Integer total = 0;
			while (myTreeEnumerator.hasNext()) {
			    Integer currentElement = (Integer) myTreeEnumerator.next();
			    if (currentElement % factor == 0)
			    	total += currentElement*currentElement;
			}
			System.out.println
				("Total of the squares of the first 10 Fibonacci numbers that are a multiple of " +
					factor + ": " + total);
			scanner.close();
		}
		catch (IllegalElementException exc) {
			// Should not be thrown!
			System.out.println("Problem in constructing search tree!");
		}
		catch (Throwable exc) {
			System.out.println("Unexpected exception during experiment!");
		}
		System.out.println();
	}
	
	public static void internalIteration() {
		try {
			// Create a search tree with some elements.
			BinaryTree myTree = new ComposedSearchTree(1,1,2,3,5,8,13,21,34,55);
			
			System.out.println("Enter factor: ");
			Scanner scanner = new Scanner(System.in);
			final int factor = scanner.nextInt();
			
			// Compute the sum of all the Fibonacci numbers in the tree that can be
			// divided by the given factor.
//			Object total = myTree.stream().
//				filter(x -> ((Integer)x)%factor == 0).
//				map(x -> ((Integer)x)*((Integer)x)).
//				reduce((x,y) -> ((Integer)x)+((Integer)y));
			Object total = myTree.stream().
					filter(new Predicate<Object>() { 
						public boolean test(Object o) {
							return (Integer)o % factor == 0;
						}
					}).
					map(new Function<Object,Integer>() {
						public Integer apply(Object o) {
							return ((Integer)o) * ((Integer)o);
						}
					}).
					reduce( new BinaryOperator<Integer>() {
						public Integer apply(Integer left, Integer right) {
							return left + right;
						}
					});
			System.out.println("Total of odd Fibonacci numbers: " + total);
			scanner.close();
		}
		catch (IllegalElementException exc) {
			// Should not be thrown!
			System.out.println("Problem in constructing search tree!");
		}
		catch (Throwable exc) {
			System.out.println("Unexpected exception during experiment!");
		}
		System.out.println();
	}

	public static void experimentIterators() {
		List<Integer> theList = new ArrayList<Integer>();
		for (int i=1; i<=10; i++)
			theList.add(i*10);
		Iterator<Integer> listIterator = theList.iterator();
		for (int i=1; i<=5; i++)
			System.out.println(listIterator.next());
		theList.add(110);
		try {
			listIterator.next();
		} catch (ConcurrentModificationException exc) {
			System.out.println("Concurrent modification exception thrown!");
		}		
	}

}