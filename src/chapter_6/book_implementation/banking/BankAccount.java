package chapter_6.book_implementation.banking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		this.grantees.clear();
		for (SavingsAccount saving: savings)
		{
			if (!saving.isTerminated())
			{
				removeAsSavingsAccount(saving);
			}
		}
		this.isTerminated = true;
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
		return this.grantees.size();
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
		return grantees.get(index);
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
		return grantees.indexOf(person);
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
		return ((person != null) && (!this.isTerminated()) && (!person.isTerminated()) && person.isAdult());
	}
	
	/**
	 * Check whether this Bank Account can have the given {@link Person} as one of its grantees at the given index.
	 * 
	 * @param 	person
	 * 			The {@link Person} to check.
	 * @param 	index
	 * 			The index to check.
	 * @return	False if this Bank Account cannot have the given Person as a grantee at any index.
	 * 			Otherwise, false if the given index isn't positive, or if it exceeds the number of grantees.
	 * 			Otherwise, true if and only if the given person isn't registered as a agrantee at any other index.
	 * 			| if (!canHaveAsGrantee(person))
	 * 			|	then result == false
	 * 			| else if ( (index < 1) || (index > getNbGrantees()) )
	 * 			|	then result == false
	 * 			| else
	 * 			|	result == for each i in 1..getNbGrantees():
	 * 			|				( (i == index) || (getGranteeAt(i) != person) )
	 */
	@Raw
	public boolean canHaveAsGranteeAt(Person person, int index)
	{
		if (!canHaveAsGrantee(person))
			return false;
		if ((index < 1) || (index > getNbGrantees()))
			return false;
		for (int i = 0; i < getNbGrantees(); i++)
		{
			if ((i != index) && (getGranteeAt(i) == person))
				return false;
		}
		return true;
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
		for (int i = 0; i < getNbGrantees(); i++)
		{
			if (!canHaveAsGranteeAt(getGranteeAt(i), i))
				return false;
		}
		return true;
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
		return this.grantees.contains(person);
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
		return new ArrayList<Person>(this.grantees);
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
		if (!canHaveAsGranteeAt(person, index))
			throw new IllegalArgumentException("Invalid grantee.");
		if (index < grantees.size())
		{
			Person oldGrantee = grantees.get(index);
			if ((oldGrantee == null) || (grantees.indexOf(oldGrantee) < index))
				grantees.set(index, person);
			else
				grantees.add(index, person);
		}
		else
			addAsGrantee(person);
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
		Person granteeToRemove = grantees.get(index);
		for (int pos = index; pos < getNbGrantees(); pos++)
		{
			if (grantees.get(pos) == granteeToRemove)
				grantees.set(pos, null);
		}
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
		if (!canHaveAsGranteeAt(person, getNbGrantees()))
			throw new IllegalArgumentException("Invalid person!");
		if ((!grantees.isEmpty()) && (grantees.get(getNbGrantees() - 1) == null))
			grantees.set(getNbGrantees() - 1, person);
		else
			grantees.add(person);
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
		int firstIndexOfPerson = getIndexOfGrantee(person);
		while (firstIndexOfPerson != -1)
		{
			grantees.set(firstIndexOfPerson, null);
			firstIndexOfPerson = getIndexOfGrantee(person);
		}
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
	
	/**
	 * Check whether this Bank Account can have the given {@link SavingsAccount} as one of the {@link SavingsAccount}s attached to it.
	 * 
	 * @param 	savings
	 * 			The {@link SavingsAccount} to check.
	 */
	@Basic @Raw
	public boolean hasAsSavingsAccount(SavingsAccount savings)
	{
		return this.savings.contains(savings);
	}
	
	/**
	 * Check whether this Bank Account can have the given {@link SavingsAccount} as one of its {@link SavingsAccount}s.
	 * 
	 * @param 	savings
	 * 			The {@link SavingsAccount} to check.
	 * @return	False if the given Savings Account is not effective.
	 * 			Otherwise, true if and only if this Bank Account is not yet terminated or the given Savings Account is also terminated.
	 * 			| if (savings == null)
	 * 			|	then result == false
	 * 			| else
	 * 			|	result == ( (!this.isTerminated()) || (savings.isTerminated()) ) 
	 */
	@Raw
	public boolean canHaveAsSavingsAccount(SavingsAccount savings)
	{
		return ((savings != null) && (!this.isTerminated() || savings.isTerminated()));
	}
	
	/**
	 * Check whether this Bank Account has proper {@link SavingsAccount}s attached to it.
	 * 
	 * @return	True if and only if this Bank Account can have each of its Savings Accounts as a Savings Account attached to it,
	 * 			and if each of these Savings Accounts references this Bank Account as their Bank Account.
	 * 			| result == for each savings in SavingsAccount:
	 * 			|				( if (this.hasAsSavingsAccount(savings))
	 * 			|					then canHaveAsSavingsAccount(savings) && savings.getBankAccount() == this )
	 */
	@Raw
	public boolean hasProperSavingsAccounts()
	{
		for (SavingsAccount savings: this.savings)
		{
			if (!canHaveAsSavingsAccount(savings))
				return false;
			if (savings.getBankAccount() != this)
				return false;
		}
		return true;
	}
	
	/**
	 * Return the number of {@link SavingsAccount}s attached to this Bank Account.
	 * 
	 * @return	The number of Savings Accounts that are attached to this Bank Account.
	 * 			| let i = 0
	 * 			| in for each savings in SavingsAccount:
	 * 			|		i = i + 1
	 * 			| result == i
	 */
	public int getNbSavingsAccounts()
	{
		return this.savings.size();
	}
	
	/**
	 * Return a set collecting all {@link SavingsAccount}s associated with this Bank Account.
	 * 
	 * @return	The resulting set is effective.
	 * 			| result != null
	 * @return 	Each Savings Account in the resulting set is attached to this Bank Account, and vice versa.
	 * 			| for each savings in SavingsAccount:
	 * 			| result.contains(savings) == this.hasSavingsAccount(savings)
	 */
	public Set<SavingsAccount> getAllSavingsAccounts()
	{
		return new HashSet<SavingsAccount>(this.savings);
	}
	
	/**
	 * Add the given {@link SavingsAccount} to the set of {@link SavingsAccount}s attached to this Bank Account.
	 * 
	 * @param 	savings
	 * 			The {@link SavingsAccount} to be added.
	 * @post	This Bank Account has the given Savings Account as one of its Savings Accounts.
	 * 			| new.hasAsSavingsAccount(savings)
	 * @post	The given Savings Account references this Bank Account as the account to which it is attached.
	 * 			| (new savings).getBankAccount() == this
	 * @throws	IllegalArgumentException
	 * 			This Bank Account cannot have the given Savings Account as one of its Savings Accounts.
	 * 			| !canHaveAsSavingsAccount(savings)
	 * @throws 	IllegalArgumentException
	 * 			The given Savings Account is already attachged to some Bank Account.
	 * 			| ( (savings != null) && (savings.getBankAccount() != null) )
	 */
	public void addAsSavingsAccount(SavingsAccount savings) throws IllegalArgumentException
	{
		if (!canHaveAsSavingsAccount(savings))
			throw new IllegalArgumentException();
		if (savings.getBankAccount() != null)
			throw new IllegalArgumentException();
		this.savings.add(savings);
		savings.setBankAccount(this);
	}
	
	/**
	 * Remove the given {@link SavingsAccount} from the set of {@link SavingsAccount}s attached to this Bank Account.
	 * 
	 * @param 	savings
	 * 			The {@link SavingsAccount} to be removed.
	 * @post	This Bank Account doesn't have the given Savings Account as one of its Savings Accounts.
	 * 			| !new.hasAsSavingsAccount(savings)
	 * @post	If this Bank Account has the given Savings Account as one of its Savings Accounts, the given Savings Account
	 * 			is no longer attached to any Bank Account.
	 * 			| if (hasAsSavingsAcccount(savbings))
	 * 			|	then ((new savings).getBankAccount() == null)
	 */
	public void removeAsSavingsAccount(SavingsAccount savings)
	{
		if (hasAsSavingsAccount(savings))
		{
			this.savings.remove(savings);
			savings.setBankAccount(null);
		}
	}
	
	/**
	 * Set collecting references to {@link SavingsAccount}s attached to this Bank Account.
	 * 
	 * @invar	The set of Savings Accounts is effective.
	 * 			| savings != null
	 * @invar	Each element in the set of Savings Accounts references a Savings Account that is an acceptable Savings
	 * 			Account for this Bank Account.
	 * 			| for each savins in savings:
	 * 			|	canHaveAsSavingsAccount(savings)
	 * @invar	Each Savings Account in the set of Savings Accounts references this Bank Account to which it is attached.
	 * 			| for each savings in savings:
	 * 			|	(savings.getBankAccount() == this)
	 */
	private final Set<SavingsAccount> savings = new HashSet<SavingsAccount>();
}
