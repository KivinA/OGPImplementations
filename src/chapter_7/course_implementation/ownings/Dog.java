package chapter_7.course_implementation.ownings;import java.math.BigInteger;import be.kuleuven.cs.som.annotate.*;import chapter_7.course_implementation.exceptions.*;import chapter_7.course_implementation.persons.Person;/** * A class of dogs as a special kind of ownable. In addition to a value * and an owner, dogs have a name and a minimal amount of food they need * every day. *  * @invar  The name of each dog must be a valid name for a dog. *       | isValidName(getName()) * @invar  The daily food amount needed by each dog must be a valid *         daily food amount for a dog. *       | isValidDailyFoodAmount(getDailyFoodAmount()) *  * @version  2.0 * @author   Eric Steegmans */public class Dog extends Ownable {	/**	 * Initialize this new dog with given owner, given value, given name and	 * given daily amount of food.	 *	 * @param  owner	 *         The owner for this new dog.	 * @param  value	 *         The value of this new dog.	 * @param  name	 *         The name of this new dog.	 * @param  dailyFoodAmount	 *         The daily food amount needed by this new dog.	 * @effect This new dog is initialized as a new ownable with	 *         given owner and given value.	 *       | super(owner,value)	 * @effect The name of this new dog is set to the given name.	 *       | setName(name)	 * @effect The daily food amount needed by this new dog is set	 *         to the given daily food amount.	 *       | setDailyFoodAmount(dailyFoodAmount)	 */	@Raw	public Dog(Person owner, BigInteger value, String name, int dailyFoodAmount)			throws IllegalValueException, IllegalOwnerException,			IllegalNameException, IllegalFoodAmountException {		super(owner, value);		setName(name);		setDailyFoodAmount(dailyFoodAmount);	}	/**	 * Initialize this new dog with given name, having no owner,	 * a value of 0, and a daily food amount of 500.	 *	 * @param  name	 *         The name for this new dog.	 * @effect	This new dog is initialized as a new ownable with	 *         no owner and a value of 0.	 *       | super()	 * @effect The name of this new dog is set to the given name.	 *       | setName(name)	 * @post   The daily food amount of this new dog is equal to 500.	 *       | new.getDailyFoodAmount() == 500	 */	@Raw	public Dog(String name) throws IllegalNameException {		setName(name);		setDailyFoodAmount(500);	}	/**	 * Return the name of this dog.	 */	@Basic	public String getName() {		return this.name;	}	/**	 * Check whether this dog can have the given name	 * as its name.	 *	 * @param  name	 *         The name to check.	 * @return True if and only if the given name is effective and	 *         not empty.	 *       | result ==	 *       |   (name != null) && (name.length() > 0)	 */	public static boolean isValidName(String name) {		return (name != null) && (name.length() > 0);	}	/**	 * Set the name of this dog to the given name.	 *	 * @param  name	 *         The new name for this dog.	 * @post   The name of this dog is the same as the	 *         given name.	 *        | new.getName() == name	 * @throws IllegalNameException	 *         The given name is not a legal name for any dog.	 *       | ! isValidName(name)	 * @throws IllegalStateException	 *         This dog is terminated.	 *       | isTerminated()	 */	@Raw	public void setName(String name) throws IllegalNameException {		if (isTerminated())			throw new IllegalStateException("Dog already terminated!");		if (!isValidName(name))			throw new IllegalNameException(name);		this.name = name;	}	/**	 * Variable referencing the name of this dog.	 */	private String name = "Nameless";	/**	 * Return the daily food amount needed by this dog.	 */	@Basic	public int getDailyFoodAmount() {		return this.dailyFoodAmount;	}	/**	 * Check whether the given daily food amount is a valid	 * daily food amount for any dog.	 *	 * @param  dailyFoodAmount	 *         The daily food amount to check.	 * @return True if and only if the given daily food amount	 *         is positive.	 *       | result == (amount > 0)	 */	public static boolean isValidDailyFoodAmount(int dailyFoodAmount) {		return dailyFoodAmount > 0;	}	/**	 * Set the daily food amount needed by this dog to the given	 * daily food amount.	 *	 * @param  dailyFoodAmount	 *         The new daily food amount for this dog.	 * @post   The daily food amount of this dog is equal to the	 *         given daily food amount.	 *       | new.getDailyFoodAmount() == dailyFoodAmount	 * @throws IllegalStateException	 *         This dog is terminated.	 *       | isTerminated()	 * @throws IllegalFoodAmountException	 *         The given daily food amount is not a valid daily food	 *         amount for any dog.	 *       | ! isValidDailyFoodAmount(dailyFoodAmount)	 */	@Raw	public void setDailyFoodAmount(int dailyFoodAmount)			throws IllegalFoodAmountException {		if (isTerminated())			throw new IllegalStateException("Dog already terminated!");		if (!isValidDailyFoodAmount(dailyFoodAmount))			throw new IllegalFoodAmountException(dailyFoodAmount);		this.dailyFoodAmount = dailyFoodAmount;	}	/**	 * Variable registering the daily food amount needed by this dog.	 */	private int dailyFoodAmount = 0;}