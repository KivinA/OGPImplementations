package chapter_4.course_implementation;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of persons involving a gender and a
 * marital partner.
 * 
 * @invar   The gender of each person must be a valid gender
 *          for a person.
 *        | isValidGender(getGender())
 * @invar   Each person must have a proper spouse.
 *        | hasProperSpouse()
 * 
 * @version 2.0
 * @author  Eric Steegmans
 */
public class Person {

	/**
	 * Initialize this new person with given gender and given partner.
	 *
	 * @param  gender
	 *         The gender for this new person.
	 * @param  partner
	 *         The partner for this new person.
	 * @post   The gender of this new person is the same as the given gender.
	 *       | new.getGender() == gender
	 * @post   This new person references the given partner as its spouse.
	 *       | new.getSpouse() == partner
	 * @post   If the given partner references a true person, that partner
	 *         references this new person as its spouse.
	 *       | if (partner != null)
	 *       |   then (new partner).getSpouse() == this
	 * @throws IllegalArgumentException
	 *         The given gender is not a valid gender for a person.
	 *       | ! isValidGender(gender)
	 * @throws IllegalPartnerException
	 *         This new person cannot have the given partner as its spouse
	 *         in view of the given gender.
	 *       | ! canHaveAsSpouse(partner,gender)
	 * @throws IllegalPartnerException
	 *         The given partner is effective and already married.
	 *       | (partner != null) && partner.isMarried()
	 * @note   In the specification of this constructor, we cannot use the
	 *         mutator 'marry'. Indeed, upon entry to the constructor, the
	 *         gender of this new person is still null, meaning that that
	 *         new person does not satisfy its class invariants. Because
	 *         the method 'marry' is not a raw method, we cannot invoke it
	 *         against the new person.
	 */
	@Raw
	public Person(Gender gender, Person partner)
			throws IllegalArgumentException, IllegalPartnerException {
		if (!isValidGender(gender))
			throw new IllegalArgumentException("Improper gender!");
		this.gender = gender;
		if (partner != null)
			marry(partner);
	}

	/**
	 * Initialize this new person as an unmarried person with
	 * given gender.
	 *
	 * @param  gender
	 *         The gender for this new person.
	 * @effect This new person is initialized with the given gender
	 *         as its gender and with no effective partner.
	 *       | this(gender,null)
	 */
	@Raw
	public Person(Gender gender) throws IllegalArgumentException {
		this(gender, null);
	}

	/**
	 * Initialize this new person as an unmarried person of female gender.
	 *
	 * @effect This new person is initialized as an unmarried person
	 *         with the female gender as its gender.
	 *       | this(Gender.FEMALE)
	 */
	public Person() {
		this(Gender.FEMALE);
	}

	/**
	 * Terminate this person, breaking the marriage in which that person
	 * might be involved.
	 *
	 * @post   This person is terminated.
	 *       | new.isTerminated()
	 * @effect This person is divorced from its spouse, if any.
	 *       | this.divorce()
	 */
	public void terminate() {
		divorce();
		// We feel no need to introduce a setter for "isTerminated". It is
		// only used at this point, because it should not be possible to
		// bring an object back to live.
		this.isTerminated = true;
	}

	/**
	 * Return a boolean indicating whether or not this person
	 * is terminated.
	 */
	@Basic @Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}

	/**
	 * Variable registering whether this person is terminated.
	 */
	private boolean isTerminated = false;

	/** 
	 * Return the gender of this person.
	 */
	@Basic
	@Raw
	@Immutable
	public Gender getGender() {
		return this.gender;
	}

	/**
	 * Check whether the given gender is a valid gender for a person.
	 * 
	 * @param  gender
	 *         The gender to check.
	 * @return True if and only if the given gender is the male gender
	 *         or the female gender.
	 *       | result ==
	 *       |   (gender == Gender.MALE) ||
	 *       |   (gender == Gender.FEMALE)
	 * @note   A test whether the given gender is effective would not
	 *         be as good. We might extend the enumeration of genders
	 *         later on with things such as the neutral gender
	 *         (Dutch 'onzijdig').
	 */
	public static boolean isValidGender(Gender gender) {
		return (gender == Gender.MALE) || (gender == Gender.FEMALE);
	}

	/**
	 * Return a boolean reflecting whether this person is a female.
	 *
	 * @return True if and only if the gender of this person is
	 *         the female gender.
	 *       | result == (getGender() == Gender.FEMALE)
	 */
	@Raw @Immutable
	public boolean isFemale() {
		return getGender() == Gender.FEMALE;
	}

	/**
	 * Return a boolean reflecting whether this person is a male.
	 *
	 * @return True if and only if the gender of this person is
	 *         the male gender.
	 *       | result == (getGender() == Gender.MALE)
	 */
	@Raw @Immutable
	public boolean isMale() {
		return getGender() == Gender.MALE;
	}

	/**
	 * Variable referencing the gender of this person.
	 */
	private final Gender gender;

	/** 
	 * Return the spouse of this person.
	 *    A null reference is returned if this person is not married.
	 */
	@Basic @Raw
	public Person getSpouse() {
		return this.spouse;
	}

	/**
	 * Check whether this person can have the other person as its spouse
	 * in view of the given gender.
	 *
	 * @param  other
	 *         The other person to check.
	 * @param  gender
	 *         The gender to be assumed for this person. 
	 * @return True if the other person is not effective.
	 *       | if (other == null)
	 *       |   then result == true
	 *         Otherwise, false if this person or the other person
	 *         is terminated.
	 *       | else if ( this.isTerminated() || other.isTerminated() )
	 *       |   then result == false
	 *         Otherwise, true if and only if the given gender is a valid
	 *         gender for a person and differs from the gender of the
	 *         other person.
	 *       | else
	 *       |   result ==
	 *       |     isValidGender(gender) &&
	 *       |     (gender != other.getGender())
	 * @note   We must add the gender as an extra argument to this method,
	 *         because we need the method in the constructor at a time
	 *         the gender of the new person is not yet registered.
	 */
	@Raw
	public boolean canHaveAsSpouse(@Raw Person other, Gender gender) {
		if (other == null)
			return true;
		else if ((other.isTerminated()) || (this.isTerminated()))
			return false;
		else
			return isValidGender(gender) && (gender != other.getGender());
	}

	/**
	 * Check whether this person has a proper spouse.
	 *
	 * @return True if and only if this person can have its spouse
	 *         as its spouse in view of its current gender, and if
	 *         that spouse, if it is effective, in turn references
	 *         this person as its spouse.
	 *       | result ==
	 *       |   canHaveAsSpouse(getSpouse(),getGender()) &&
	 *       |   ( (getSpouse() == null) ||
	 *       |     (getSpouse().getSpouse() == this) )
	 */
	@Raw
	public boolean hasProperSpouse() {
		return canHaveAsSpouse(getSpouse(), getGender())
				&& ((getSpouse() == null) || (getSpouse().getSpouse() == this));
	}

	/**
	 * Check whether this person is married.
	 *
	 * @return True if this person references an effective spouse;
	 *         false otherwise.
	 *       | result == (getSpouse() != null)
	 */
	@Raw
	public boolean isMarried() {
		return getSpouse() != null;
	}

	/**
	 * Register a marriage between this person and the given partner.
	 *
	 * @param  partner
	 *         The new spouse for this person.
	 * @post   The given partner is registered as the spouse of this person.
	 *       | new.getSpouse() == partner
	 * @post   This person is registered as the spouse of the given partner.
	 *       | (new partner).getSpouse() == this
	 * @throws IllegalPartnerException
	 *         The given partner is not effective, or this person cannot
	 *         have the given partner as its spouse in view of its current
	 *         gender.
	 *       | (partner == null) ||
	 *       | (! this.canHaveAsSpouse(partner,this.getGender()))
	 * @throws IllegalStateException
	 *         This person is already married, or the given partner is an
	 *         effective, married person.
	 *       | this.isMarried() ||
	 *       | ( (partner != null) && partner.isMarried())
	 */
	public void marry(Person partner) throws IllegalPartnerException,
			IllegalStateException {
		if ((partner == null) || (!canHaveAsSpouse(partner, this.getGender())))
			throw new IllegalPartnerException(this, partner);
		if (this.isMarried() || partner.isMarried())
			throw new IllegalStateException("Married persons cannot marry");
		setSpouse(partner);
		partner.setSpouse(this);
	}

	/**
	 * Register a divorce between this person and its partner, if any.
	 *
	 * @post   This person is no longer married.
	 *	     | ! new.isMarried()
	 * @post   The former spouse of this person, if any, is no longer
	 *		   married.
	 *       | if (this.isMarried())
	 *       |   then ! (new (this.getSpouse())).isMarried())
	 */
	public void divorce() {
		try {
			getSpouse().setSpouse(null);
			this.setSpouse(null);
		} catch (NullPointerException exc) {
			assert (!this.isMarried());
		}
	}

	/**
	 * Register a switch of partners between this person and the other
	 * person.
	 *
	 * @param  other
	 *         The person to switch partners with.
	 * @post   This person is married with the other person or with the
	 *         spouse of the other person.
	 *       | (new.getSpouse() == other) ||
	 *       | (new.getSpouse() == other.getSpouse())
	 * @post   This person's spouse, if any, is married with the other
	 *         person or with the spouse of the other person.
	 *       | if (isMarried()) then
	 *       |   ( ((new getSpouse()).getSpouse() == other) ||
	 *       |     ((new getSpouse())getSpouse() == other.getSpouse()) )
	 * @post   The other person is married with this person or with this
	 *         person's spouse.
	 *       | ((new other).getSpouse() == this) ||
	 *       | ((new other).getSpouse() = this.getSpouse())
	 * @post   The other person's spouse, if any, is married with this
	 *         person or with this person's spouse.
	 *       | if (other.isMarried()) then
	 *       |   ( ((new other.getSpouse()).getSpouse() == this) ||
	 *       |     ((new other.getSpouse()).getSpouse() = this.getSpouse()) )
	 * @note   In case this person nor the other person are married, and
	 *         both persons are of different gender, the specification
	 *         leaves 2 options: (1) leave things as they are, or (2) have
	 *         this person marry the other person.
	 * @throws IllegalArgumentException
	 *         The other person is not effective.
	 *       | other == null
	 */
	public void switchPartnerWith(Person other) throws IllegalArgumentException {
		try {
			if (this.getGender() != other.getGender()) {
				if (this.isMarried())
					this.getSpouse().switchPartnerWith(other);
				else if (other.isMarried())
					switchPartnerWith(other.getSpouse());
			} else {
				assert (other != null)
						&& ((this.getGender() == other.getGender()) || ((!this
								.isMarried()) && (!other.isMarried())));
				Person thisSpouse = this.getSpouse();
				Person otherSpouse = other.getSpouse();
				this.divorce();
				other.divorce();
				if (otherSpouse != null)
					this.marry(otherSpouse);
				if (thisSpouse != null)
					other.marry(thisSpouse);
			}
		} catch (NullPointerException exc) {
			assert (other == null);
			throw new IllegalArgumentException("Non-effective person!");
		} catch (IllegalPartnerException exc2) {
			assert false;
		}
	}

	/** 
	 * Register the given person as the spouse of this person.
	 * 
	 * @param  person
	 *         The person to be registered as the spouse of this person.
	 * @pre    This person must be able to have the given person as
	 *         its spouse, in view of its current gender.
	 *       | canHaveAsSpouse(person,getGender())
	 * @post   The spouse of this person is the same as the given person.
	 *       | new.getSpouse() == person
	 */
	@Raw
	private void setSpouse(@Raw Person person) {
		assert canHaveAsSpouse(person, getGender());
		this.spouse = person;
	}

	/**
	 * Variable referencing the spouse of this person.
	 */
	private Person spouse = null;

}