package chapter_2.course_implementation;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of tanks for storing oil, involving a capacity and a content.
 * 
 * @invar  The capacity of each oil tank must be a valid capacity for an
 *         oil tank.
 *         | isValidCapacity(getCapacity())
 * @invar  The contents of each oil tank must be a valid contents for an
 *         oil tank in view of its capacity.
 *         | isValidContents(getContents(),getCapacity())
 * 
 * @version  2.1
 * @author   Eric Steegmans
 */
public class OilTank {

    /**
     * Initialize this new oil tank with given capacity and given contents.
     * 
     * @param  capacity
     * 		   The capacity for this new oil tank.
     * @param  contents
     * 		   The contents for this new oil tank.
     * @pre	   The given capacity must be a valid capacity for an oil tank.
     *       | isValidCapacity(capacity)
     * @pre    The given contents must be a valid contents for an oil tank
     *         in view of the given capacity.
     *       | isValidContents(contents,capacity)
     * @post   The capacity of this new oil tank is equal to the given
     * 		   capacity.
     *       | new.getCapacity() == capacity
     * @post   The contents of this new oil tank is equal to the given
     * 		   contents.
     *       | new.getContents() == contents
     * @note   Any new oil tank initialized with this constructor
     * 		   will satisfy all its class invariants.
     * @note   In the specification of this constructor, we cannot
     *         use the model method setContents. The precondition for
     *         that method would expand to
     *            isValidContents(contents,getCapacity())
     *         and the capacity of this new oil tank upon entry to
     *         the constructor is 0 , which means that the precondition
     *         can be simplified to.
     *            isValidContents(contents,0)
     */
	@Raw
    public OilTank(int capacity, int contents) {
        assert isValidCapacity(capacity);
        this.capacity = capacity;
        // setContents will also assert the second precondition imposed
        // on this constructor.
        setContents(contents);
    }

    /**
     * Return the capacity of this oil tank.
     *   The capacity expresses the maximum amount of oil that
     *   can be stored in this oil tank.
     */
    @Basic @Raw @Immutable
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Check whether the given capacity is a valid capacity for
     * any oil tank.
     *  
     * @param  capacity
     * 		   The capacity to check.
     * @return True if and only if the given capacity is positive.
     *       | result == (capacity > 0)	
     * @note   Raw annotations do not apply to static methods.
     *         Classes are assumed to satisfy invariants imposed on
     *         their characteristics at all times.
     */
    public static boolean isValidCapacity(int capacity) {
        return capacity > 0;
    }

    /**
     * Variable registering the capacity of this oil tank.
     * 
     * @note   Final instance variables are not initialized to
     *         the default value of their type upon entry to a
     *         constructor.
     */
    private final int capacity;

    /**
     * Return the contents of this oil tank.
     *   The contents expresses the actual amount of oil stored in
     *   this oil tank.
     */
    @Basic @Raw
    public int getContents() {
        return this.contents;
    }

    /**
     * Check whether the given contents is a valid contents for
     * any oil tank under the given capacity.
     *  
     * @param  contents
     * 		   The contents to check.
     * @param  capacity
     * 		   The capacity to check the contents against.
     * @return True if and only if the given contents is not
     *         negative and does not exceed the given capacity.
     *       | result ==
     *       |   (contents >= 0) && (contents <= capacity)	
     * @note   The capacity is also explicitly supplied as an
     * 		   argument, such that the method can also be used
     * 		   at times oil tanks have not been initialized properly.
     *         This might mean that the capacity is not yet
     *         registered, such as upon entry to a constructor.
     */
    public static boolean isValidContents(int contents, int capacity) {
        return (contents >= 0) && (contents <= capacity);
    }

    /**
     * Set the contents of this oil tank to the given amount.
     * 
     * @param  contents
     * 		   The new contents for this oil tank.
     * @pre    The given contents must be a valid contents for an
     * 		   oil tank, in view of the capacity of this oil tank.
     *       | isValidContents(contents,getCapacity())
     * @post   The contents of this oil tank is equal to the given
     * 		   contents.
     *       | new.getContents() == contents
     * @note   This method is qualified @Raw, such that it can be
     *         used in initializing new oil tanks, even at times
     *         at which they do not yet satisfy all their class
     *         invariants.
     * @note   This method is qualified @Model, such that it can
     *         be used in specifications of public methods.
     *         However, instead of using this method in specifications
     *         of some of the mutators, we could just as well have
     *         worked out postconditions for those methods. The
     *         annotation is introduced here just for purposes of
     *         illustration.
     */
    @Raw
    @Model
    private void setContents(int contents) {
        assert isValidContents(contents, getCapacity());
        this.contents = contents;
    }

    /**
     * Variable registering the amount of oil currently stored in
     * this oil tank.
     */
    private int contents;

    /**
     * Return the free space of this oil tank.
     *	 The free space is the maximum amount of oil that can
     *	 still be added to this oil tank.
     *
     * @return The difference between the capacity of this oil tank
     * 		   and its contents.
     *       | result == getCapacity() - getContents()
     * @note   This method could have been qualified 'raw'. However,
     *		   in the current definition, there is no need for that.
     */
    public int getFree() {
        return getCapacity() - getContents();
    }

    /**
     * Check whether this oil tank is completely filled with oil.
     * 
     * @return True if and only if the free space in this oil tank is 0.
     *       | result == (getFree() == 0)
     * @note   This method can only be qualified 'raw', if the
     *		   method getFree() is also qualified 'raw'.
     */
    public boolean isFull() {
        return getFree() == 0;
    }

    /**
     * Check whether this oil tank is empty.
     * 
     * @return True if and only if the contents of this oil tank is 0.
     *       | result == (getContents() == 0)
     */
    public boolean isEmpty() {
        return getContents() == 0;
    }

    /**
     * Fill this oil tank with the given amount of oil.
     * 
     * @param  amount
     *         The amount to be added to this oil tank.
     * @pre    The given amount must be positive.
     *       | amount > 0
     * @effect The contents of this oil tank is set to its current contents
     *         incremented with the given amount of oil.
     *       | setContents(getContents() + amount)
     * @note   Because we use the method setContents in the specification,
     *         this method has another precondition, namely
     *            isValidContents(getContents()+amount,getCapacity())
     *         If the sum of the contents and the amount would overflow,
     *         the resulting value is guaranteed to be negative, and
     *         will therefore not be accepted.
     * @note   Because this is not a 'raw' method, this oil tank must
     * 		   satisfy its class invariants upon entry to this method.
     * 		   Upon exit, it is easy to see that this oil tank then still
     * 		   satisfies its class invariants.
     */
    public void fill(int amount) {
        assert amount > 0;
        setContents(getContents() + amount);
    }

    /**
     * Fill this oil tank to its full capacity.
     * 
     * @post   This oil tank is completely filled with oil.
     *       | new.isFull()
     * @note   We prefer not to specify this method in terms of
     *         other mutators. We feel that such a specification
     *         would impose too much restrictions on a possible
     *         implementation.
     * @note   This method cannot be qualified 'raw', even if the
     * 		   method 'fill(int)' would be qualified 'raw'. Indeed,
     * 		   an oil tank with capacity 2000 and contents 3000 (which
     * 		   obviously does not satisfy its invariants), would invoke
     * 		   the method 'fill(int)' with a negative value, which
     * 		   would violate one of the preconditions for that method.
     */
    public void fill() {
        // We must check whether this oil tank is not already full,
        // because otherwise the method 'fill(int) would be invoked
        // with an amount of 0, which would violate the first precondition
        // of that method.
        if (!isFull())
            fill(getFree());
    }

    /**
     * Fill this oil tank with the given amounts of oil.
     * 
     * @param  amounts
     *         The amounts to be added to this oil tank.
     * @pre    The given sequence of amounts must be effective.
     *       | amounts != null
     * @pre    Each of the supplied amounts must be positive.
     *       | for each amount in amounts:
     *       |   (amount > 0)
     * @pre    Each of the amounts incremented with the sum of all
     *         previous amounts must fit in this oil tank.
     *       | for each index in 0..amounts.length-1:
     *       |   (isValidContents(getContents() +
     *       |       sum({i in 0..index: amounts[i]})))
     * @effect This oil tank is filled with the sum of given amounts
     *         of oil.
     *       | fill(sum(amounts))
     * @note   The formal specification of the second preconditions uses
     *         the universal quantifier.
     * @note   The third precondition cannot simply be written as
     *         the total sum of all amounts must still fit in the
     *         oil tank, because the computation of that total sum
     *         may overflow several times.
     * @note   The third precondition appeals to set comprehension
     *         to describe a set of numbers.
     */
    public void fill(int... amounts) {
    	assert amounts != null;
        for (int amount : amounts)
        	fill(amount);
    }

    /**
     * Extract the given amount of oil from this oil tank.
     * 
     * @param  amount
     * 		   The amount to be extracted from this oil tank.
     * @pre	   The given amount must be positive.
     *       | amount > 0
     * @effect The contents of this oil tank is set to its current contents
     *         decremented with the given amount of oil.
     *       | setContents(getContents() - amount)
     */
    public void extract(int amount) {
        assert amount > 0;
        setContents(getContents() - amount);
    }

    /**
     * Extract the entire contents from this oil tank.
     * 
     * @post   This oil tank is empty.
     *       | new.isEmpty()
     * @note   It is easy to see that this method could have
     * 		   been qualified 'raw'.
     */
    public void extract() {
        setContents(0);
    }

    /**
     * Extract the given amounts of oil from this oil tank with.
     * 
     * @param  amounts
     *         The amounts to be extracted from this oil tank.
     * @pre    The given sequence of amounts must be effective.
     *       | amounts != null
     * @pre    Each of the supplied amounts must be positive.
     *       | for each amount in amounts:
     *       |   (amount > 0)
     * @pre    Each of the amounts incremented with the sum of all
     *         previous amounts must be extractable from this oil tank.
     *       | for each index in 0..amounts.length-1:
     *       |   (isValidContents(getContents() -
     *       |       sum{i in 0..index: amounts[i]}))
     * @effect The sum of all the given amounts of oil is extracted from this
     *         oil tank.
     *       | extract(sum{amounts})
     */
    public void extract(int... amounts) {
    	assert amounts!= null;
        for (int amount : amounts)
            extract(amount);
    }

    /**
     * Return the percentage of the capacity of this oil tank is
     * currently filled with oil.
     *
     * @return The contents of this oil tank divided by its capacity,
     * 		   returned as a percentage.
     *       | result == (100.0F * getContents()) / getCapacity()
     */
    public float getPercentageFilled() {
        // The multiplication of the (integer) contents with the floating
        // point literal 100.0F yields a floating point value. The
        // subsequent division is therefore a floating point division
        // and not an integer division.
        return (100.0F * getContents()) / getCapacity();
    }

    /**
     * Check whether this oil tank is relatively more filled than
     * the other oil tank.
     * 
     * @param  other
     *         The oil tank to compare with.
     * @pre    The other oil tank must be effective.
     *       | other != null
     * @return True if and only if the filled percentage of this oil
     *         tank exceeds the filled percentage of the other oil tank.
     *       | result ==
     *       |   this.getPercentageFilled() > other.getPercentageFilled()
     */
    public boolean isRelativelyFullerThan(OilTank other) {
        assert other != null;
        return this.getPercentageFilled() > other.getPercentageFilled();
    }
	

    /** HOMEWORK **/	
	
    /**
     * Initialize this new oil tank with given capacity and no contents.
     * 
     * @param  capacity
     *         The capacity for this new oil tank.
     * @effect This new oil tank is initialized with the given capacity
     *         and with no contents.
     *       | this(capacity,0) 
     */
    @Raw
    public OilTank(int capacity) {
        this(capacity, 0);
    }

    /**
     * Initialize this new oil tank with a capacity of 5000 and no contents.
     *
     * @effect This new oil tank is initialized with a capacity of 5000
     *         and with no contents.
     *       | this(5000) 
     */
    @Raw
    public OilTank() {
        this(5000);
    }

    /**
     * Transfer the entire contents from the other oil tank into
     * this oil tank.
     * 
     * @param  other
     * 		   The oil tank to transfer oil from.
     * @pre	   The other oil tank must be effective.
     *       | other != null
     * @pre	   The other oil tank must not be the same as this oil tank.
     *       | other != this
     * @effect This oil tank is filled with the contents of the
     *         other oil tank.
     *       | fill(other.getContents())
     * @effect All oil is extracted from the other oil tank.
     *       | other.extract()
     */
    public void transferFrom(OilTank other) {
        assert other != null;
        assert other != this;
        this.fill(other.getContents());
        other.extract();
    }

    /**
     * Return a textual representation of this oil tank.
     * 
     * @return An effective string of the following form
     *		   "Oil tank with capacity xxx and contents yyy".
     *       | result.equals
     *       |    ("Oil tank with capacity " + getCapacity() +
     *       |     " and contents " + getContents())
     * @note   A default version of this method is inherited
     * 		   from the root class Object. The method is invoked
     * 		   at points where a string is expected and an oil tank
     * 		   is passed (Example: System.out.println(myTank)) 
     */
    public String toString() {
        return "Oil tank with capacity " + getCapacity() + " and contents "
            + getContents();
    }

    /**
     * Check whether this oil tank is identical to the other oil tank.
     * 
     * @param  other
     * 		   The other oil tank to compare with.
     * @pre	   The other oil tank must be effective.
     *       | other != null
     * @return True if and only if the capacity and the contents of
     *         this oil tank are equal to the capacity, respectively
     * 		   the contents of the other oil tank.
     *        | result ==
     *        |     (getCapacity() == other.getCapacity())
     *        |  && (getContents() == other.getContents())
     * @note   This method stands next to the method equals(Object)
     *         inherited from the root class Object, which performs
     *         a referential comparison. For mutable objects, such
     *         as oil tanks, the method equals must not be overridden
     *         to compare objects property by property. Mutable
     *         objects are equal, if and only if both objects are
     *         the same object.
     */
    public boolean isIdenticalTo(OilTank other) {
        assert other != null;
        return (this.getCapacity() == other.getCapacity())
            && (this.getContents() == other.getContents());
    }

    /**
     * Return a new oil tank as an exact copy of this oil tank.
     * 
     * @return A new effective oil tank, whose capacity and contents
     * 		   are identical to the capacity and the contents of
     * 		   this oil tank.
     *       |    (result != null)
     *       | && (result != this)
     *       | && result.isIdenticalTo(this)
     * @note   It would be better to use the clone() method,
     * 		   inherited from the root class Object.
     *		   At this point in the course, we have not covered
     *		   all concepts to work out such a redefinition.
     */
    public OilTank getCopy() {
        return new OilTank(getCapacity(), getContents());
    }

}