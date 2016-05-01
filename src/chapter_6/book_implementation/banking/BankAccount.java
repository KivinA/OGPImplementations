package chapter_6.book_implementation.banking;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.som.annotate.*;
import chapter_6.book_implementation.state.*;

/**
 * A class for dealing with Bank accounts involving {@link Person}s as grantees and with {@link SavingsAccount}s.
 * 
 * @author 	Kevin Algoet & Eric Steegmans
 * @version	6.0
 * @invar	The grantees associated with each Bank account must be proper grantees for that Bank Account.
 * 			| hasProperGrantees()
 * @invar	The Savings Accounts attached to each Bank account must be proper Savings Accounts for that Bank Account.
 * 			| hasProperSavingsAccounts()
 * 
 * @note	Based on the code found in the book Object Oriented Programming with Java by Eric Steegmans.
 */
public class BankAccount {
	
	/**
	 * Initialize this new Bank Account with no {@link SavingsAccount}s nor grantees attached to it.
	 * 
	 * @post	No grantees are attached to this new Bank Account.
	 * 			| new.getNbGrantees() == 0
	 * @post	No Savings Accounts are attached to this new Bank Account.
	 * 			| new.getNbSavingsAccounts() == 0
	 */
	public BankAccount()
	{
		
	}
	
	/**
	 * Terminate this Bank Account.
	 * 
	 * @post	This Bank Account is terminated.
	 * 			| new.isTerminated()
	 * @post	No grantees are attached any longer to this Bank Account.
	 * 			| new.getNbGrantees() == 0
	 * @effect	Each non-terminated Savings Account attached to this Bank Account is removed from this Bank Account.
	 * 			| for each savings in getAllSavingsAccounts():
	 * 			|		if (!savings.isTerminated())
	 * 			|			then this.removeAsSavingsAccount(savings)
	 */
	public void terminate()
	{
		
	}
	
	/**
	 * Check whether this Bank Account is terminated.
	 */
	@Basic @Raw
	public boolean isTerminated()
	{
		return this.isTerminated;
	}
	
	/**
	 * Variable registering whether this Bank Account is terminated.
	 */
	private boolean isTerminated = false;
	
	/**
	 * Return the number of grantees associated with this Bank Account.
	 */
	@Basic @Raw
	public int getNbGrantees()
	{
		return 0;
	}
	
	/**
	 * Return the grantee associated with this Bank Account at the given index.
	 * 
	 * @param 	index
	 * 			The index of the grantee to be returned.
	 * @throws 	IndexOutOfBoundsException
	 * 			The given index isn't positive or it exceeds the number of grantees associated with this Bank Acccount.
	 * 			| (index < 0) || (index > getNbGrantees())
	 */
	@Basic @Raw
	public Person getGranteeAt(int index) throws IndexOutOfBoundsException
	{
		return null;
	}
	
	/**
	 * Return the index at which the given {@link Person} is registered as a grantee for this Bank Account.
	 * 
	 * @param 	person
	 * 			The {@link Person} to look for.
	 * @return	The given Person is registered as a grantee for this Bank Account at the resulting index.
	 * 			| getGranteeAt(result) == person
	 * @throws 	IllegalArgumentException
	 * 			The given person isn't a grantee for this Bank Account.
	 * 			| !hasAsGrantee(person)
	 */
	@Raw
	public int getIndexOfGrantee(Person person) throws IllegalArgumentException
	{
		return 0;
	}
	
	/**
	 * Check whether this Bank Account can have the given {@link Person} as one of its grantees.
	 * 
	 * @param 	person
	 * 			The {@link Person} to check.
	 * @return	False if the given person isn't effective. Otherwise, false if this Bank Account is 
	 * 			already terminated or if the given Person is terminated. Otherwise, true if and only 
	 * 			if the given Person is an adult Person.
	 * 			| if (person == null)
	 * 			|	then result == false
	 * 			| else if (this.isTerminated() || person.isTerminated())
	 * 			|	then result == fasle
	 * 			| else
	 * 			|	result == person.isAdult()
	 */
	@Raw
	public boolean canHaveAsGrantee(Person person)
	{
		return false;
	}
	
	/**
	 * Check whether this Bank Account can have the given {@link Person} as one of its grantees at the given index.
	 * 
	 * @param 	person
	 * 			The {@link Person} to check.
	 * @param 	index
	 * 			The index to check.
	 * @return	False if this Bank Account cannot have the given Person as a grantee at any index.
	 * 			Otherwise, false if the given index isn't positive, or if it exceeds the number of grantees with more than one.
	 * 			Otherwise, true if and only if the given person isn't registered as a agrantee at any other index.
	 * 			| if (!canHaveAsGrantee(person))
	 * 			|	then result == false
	 * 			| else if ( (index < 1) || (index > getNbGrantees() + 1) )
	 * 			|	then result == false
	 * 			| else
	 * 			|	result == for each i in 1..getNbGrantees():
	 * 			|				( (i == index) || (getGranteeAt(i) != person) )
	 */
	@Raw
	public boolean canHaveAsGranteeAt(Person person, int index)
	{
		return false;
	}
	
	/**
	 * Check wheter this Bank account has proper grantees associated with it.
	 * 
	 * @return	True if and only if this Bank Account can have each of its grantees as a grantee at their index.
	 * 			| result == for each i in 1..getNbGrantees():
	 * 			|				canHaveAsGranteeAt(getGranteeAt(i), i)
	 */
	@Raw
	public boolean hasProperGrantees()
	{
		return false;
	}
	
	/**
	 * Check whether this given {@link Person} is one of the grantees associated with this Bank Account.
	 * 
	 * @param 	person
	 * 			The {@link Person} to be checked.
	 * @return	True if and only if this Bank account has the given Person as one of its grantees at some index.
	 * 			| result == for some i in 1..getNbGrantees():
	 * 			|				(getGranteeAt(i) == person)
	 */
	public boolean hasAsGrantee(Person person)
	{
		return false;
	}
	
	/**
	 * Return a list of all grantees associated with this Bank Account.
	 * 
	 * @return	The number of elements in the resulting list is equal to the number of grantees associated with this Bank Account.
	 * 			| result.size() == getNbGrantees()
	 * @return	Each element at a given index in the resulting list is the same as the grantee associated with this Bank Account
	 * 			at the corresponding index.
	 * 			| for each i in 0..getNbGrantees()-1:
	 * 			|		(result.get(i) == getGranteeAt(i))
	 */
	public List<Person> getAllGrantees()
	{
		return null;
	}
	
	/**
	 * Add the given {@link Person} as a grantee for this Bank Account at the given index.
	 * 
	 * @param 	person
	 * 			The {@link Person} to be added as grantee.
	 * @param 	index
	 * 			The index of the grantee to be added.
	 * @post	This Bank account has the given Person as one of its grantees at the given index.
	 * 			| new.getGranteeAt(index) == person
	 * @post	The number of grantees for this Bank account is incremented by 1.
	 * 			| new.getNbGrantees() == this.getNbGrantees() + 1
	 * @post	All grantees for this Bank Account at an index exceeding the given index, are registered
	 * 			as a grantee at one index higher.
	 * 			| for each i in index..getNbGrantees():
	 * 			|		(new.getGranteeAt(i+1) == getGranteeAt(i))
	 * @throws 	IllegalArgumentException
	 * 			This Bank Account cannot have the given Person as a grantee at the given index.
	 * 			| !canHaveAsGranteeAt(person, index)
	 */
	public void addGranteeAt(Person person, int index) throws IllegalArgumentException
	{
		
	}
	
	/**
	 * Remove the grantee for this Bank Account at the given index.
	 * 
	 * @param 	index
	 * 			The index of the grantee to be removed.
	 * @post	This Bank Account no longer has the grantee at the given index as one of its grantees.
	 * 			| !new.hasAsGrantee(getGranteeAt(index))
	 * @post	The number of grantees associated with this Bank Account is decremented by 1.
	 * 			| new.getNbGrantees() == this.getNbGrantees() - 1
	 * @post	All grantees associated with this Bank Account at an index exceeding the giving index, 
	 * 			are registered as a grantee at one index lower.
	 * 			| for each i in index..getNbGrantees():
	 * 			|		(new.getGranteeAt(i) == this.getGranteeAt(i+1))
	 * @throws 	IndexOutOfBoundsException
	 * 			The given index isn't positive or it exceeds the number of grantees associated with this Bank Account.
	 * 			| (index < 1) || (index > getNbGrantees())
	 */
	public void removeGranteeAt(int index) throws IndexOutOfBoundsException
	{
		
	}
	
	/**
	 * Add the given {@link Person} as a grantee for this Bank Account.
	 * 
	 * @param 	person
	 * 			The {@link Person} to become grantee for this account.
	 * @post	This Bank Account has the given Person as on of its grantees at the end of the list.
	 * 			| new.getGranteeAt(getNbGrantees()) == person
	 * @post	The number of grantees for this Bank Account is incremented by 1.
	 * 			| new.getNbGrantees() == this.getNbGrantees() + 1
	 * @throws	IllegalArgumentException
	 * 			This Bank Account cannot have the given Person as a grantee at the end.
	 * 			| !canHaveAsGranteeAt(person, getNbGrantees() + 1)
	 */
	public void addAsGrantee(Person person) throws IllegalArgumentException
	{
		
	}
	
	/**
	 * Remove the given {@link Person} as a grantee for this Bank Account.
	 * 
	 * @param 	person
	 * 			The {@link Person} to be remove as grantee.
	 * @effect	If the given Person is a grantee for this Bank Account, it is remove as grantee at the position at which
	 * 			it was registered.
	 * 			| if (hasAsGrantee(person))
	 * 			|	then this.removeGranteeAt(getIndexOfGrantee(person))
	 */
	public void removeAsGrantee(Person person)
	{
		
	}
	
	/**
	 * List collecting references to the grantees of this Bank Account.
	 * 
	 * @invar	The list of grantees is effective.
	 * 			| grantees != null
	 * @invar	Each element in the list of grantees is either not effective, or it references a Person that is acceptable
	 * 			as a grantee for this Bank Account.
	 * 			| for each grantee in grantees:
	 * 			|	( (grantee == null) || canHaveAsGrantee(grantee) )
	 */
	private final List<Person> grantees = new ArrayList<Person>();
}
