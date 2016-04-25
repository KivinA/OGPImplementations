package chapter_1.course_implementation;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class for dealing with digital clocks, displaying time in terms of
 * hours, minutes and seconds.
 *    In the current version, hours are displayed according
 *    to the European format (0..23). Other format may be
 *    added in later versions.
 * 
 * @version  2.0
 * @author   Eric Steegmans
 */
public class DigitalClock {

	/**
	 * Initialize this new digital clock with given hours and
	 * given minutes.
	 *
	 * @param  hours
	 *         The hours for this new digital clock.
	 * @param  minutes
	 *         The minutes for this new digital clock.
	 * @post   The lowest possible value for the hours of this new
	 * 		   digital clock is equal to 0.
	 *       | new.getMinHours() == 0
	 * @post   The highest possible value for the hours of this new
	 * 		   digital clock is equal to 23.
	 *       | new.getMaxHours() == 23
	 * @post   If the given hours are in the range 0..23, the hours of
	 *         this new digital clock are equal to the given hours.
	 *         If the given hours exceed 23, the hours for this new
	 *         digital clock are equal to the given hours modulo 24.
	 *         If the given hours are negative, the hours for  this new
	 *         digital clock are equal to 0.
	 *       | if ( (hours >= 0) && (hours <= 23) )
	 *       |   then new.getHours() == hours
	 *       | else if (hours > 23)
	 *       |   then new.getHours() == (hours % 24)
	 *       | else if (hours < 0)
	 *       |   then new.getHours() == 0
	 * @effect The given minutes are set as the minutes of this
	 *         new digital clock.
	 *       | setMinutes(minutes)
	 * @note   We cannot use the mutator setHours(int) in the specification,
	 *         because the range for the hours of this new digital clock is
	 *         0..0 upon entry to this constructor. We could introduce a more
	 *         general method setHours(hours,minHours,maxHours) to avoid
	 *         a duplication of the specification of setting the hours. At this
	 *         stage, we prefer not to do so to keep things simple at this
	 *         stage of the course.
	 */
	public DigitalClock(int hours, int minutes) {
		// At this point, we can invoke the mutator setHours, because the range
		// is known at this point.
		setHours(hours);
		setMinutes(minutes);
	}

	/**
	 * Initialize this new digital clock with hours, minutes and seconds
	 * set to their lowest possible values.
	 *
	 * @effect This new digital clock is initialized with 0 as its
	 *         hours and with the lowest possible value for the minutes
	 *         as its minutes.
	 *       | this(0,DigitalClock.getMinMinutes())
	 */
	public DigitalClock() {
		// Java does not allow us to invoke instance methods in the
		// argument list. This seems reasonable, because at that time,
		// the object is not guaranteed to have a proper state.
		// We can therefore not write this.getMinMinutes() at this
		// point.
		this(0, DigitalClock.getMinMinutes());
	}

	/**
	 * Return the hours displayed by this digital clock.
	 * 
	 * @note   Basic instance inspectors reveal information concerning
	 *         the current state of their prime object.
	 *         They are are annotated @Basic.
	 */
	@Basic
	public int getHours() {
		return this.hours;
//		// Alternative implementation in case secondsSinceMidnight is
//		// used as the representation.
//		return (this.secondsSinceMidnight / (getMinutesPerHour()*SECONDS_PER_MINUTE)) + getMinHours();
	}

	/**
	 * Return the lowest possible value for the hours of this
	 * digital clock.
	 * 
	 * @return The lowest possible value for the hours of this
	 *         digital clock is a non-negative value.
	 *       | result >= 0
	 */
	@Basic
	public int getMinHours() {
		return 0;
	}

	/**
	 * Return the highest possible value for the hours of this digital
	 * clock.
	 * 
	 * @return The highest possible value for the hours of this
	 *         digital clock is not below the lowest possible value
	 *         for the hours of this digital clock.
	 *       | result >= getMinHours()
	 * @note   Because the minimal hours and the maximal hours are offered
	 *         as instance methods, meaning that different clocks can have
	 *         different values for their lowest hour, and because the
	 *         documentation has not revealed the actual value, it is
	 *         possible to change these values without having to notify
	 *         any clients of the class. At that time, we might have to
	 *         add new constructors by means of which we can initialize
	 *         new clocks with given range for the hours. Adding new
	 *         methods to a clock does not break the contract with the
	 *         clients.
	 * @note   Later on, restrictions imposed on basic inspectors such
	 *         as getMinHours() and getMaxHours() will be worked in as
	 *         class invariants.
	 */
	@Basic
	public int getMaxHours() {
		return 23;
	}

	/**
	 * Set the hours for this digital clock to the given hours.
	 *
	 * @param	hours
	 *			The new hours for this digital clock.
	 * @post    If the given hours are in the range established by the minimum
	 *          and maximum hours for this digital clock, the hours of this digital
	 *          clock are equal to the given hours.
	 *        | if ( (hours >= getMinHours()) && (hours <= getMaxHours()) )
	 *        |   then new.getHours() == hours
	 * @post    If the given hours exceed the the maximum hours for this digital
	 *          clock, the hours of this digital clock are equal to the given hours,
	 *          modulo the range established by the minimum and maximum hours of
	 *          this digital clock.
	 *        | if (hours > getMaxHours())
	 *        |   then new.getHours() ==
	 *        |          ((hours-getMinHours()) % (getMaxHours()-getMinHours()+1)) + getMinHours()
	 * @post   If the given hours are below the minimum hours for this digital
	 *         clock, the hours of this digital clock are left untouched.
	 *        | if (hours < getMinHours())
	 *        |     then new.getHours() = this.getHours()
	 * @note   This method, as well as others in this class, illustrates the
	 *         principles of total programming. Exceptional cases, i.e.
	 *         exceptional values for the hours of a clock are handled in
	 *         postconditions. Obviously, other options are open in handling
	 *         these exceptional cases. As an example, we could have chosen
	 *         to leave the hours untouched in case the given value exceeded
	 *         the highest possible value for the hours. The important thing
	 *         is that the documentation of the method reveals what happens
	 *         in those exceptional cases.
	 * @note   The last postcondition is not really needed. Because of the interia
	 *         axiom, everything that is not touched upon in the specification
	 *         of a method keeps its value.
	 */
	public void setHours(int hours) {
		if (hours > getMaxHours())
			hours = ((hours - getMinHours()) % (getMaxHours() - getMinHours() + 1))
					+ getMinHours();
		else if (hours < getMinHours())
			hours = getHours();
		this.hours = hours;
//		// Alternative implementation in case secondsSinceMidnight is
//		// used as the representation.
//		if (hours > getMaxHours())
//			hours = ((hours - getMinHours()) % (getMaxHours() - getMinHours() + 1))
//					+ getMinHours();
//		else if (hours < getMinHours())
//			hours = getHours();
//		this.secondsSinceMidnight += 
//			((hours - this.getHours()) - getMinHours())*getMinutesPerHour()*SECONDS_PER_MINUTE;
	}

	/**
	 * Advance the time displayed by this digital clock with 1 hour.
	 *
	 * @effect The hours currently displayed by this digital clock incremented
	 *         by 1 are set as the new hours displayed by this digital
	 *         clock.
	 *       | setHours(getHours()+1)
	 * @note   The effect of mutators can be specified in terms of other
	 *         mutators. In this case, we specify that advancing the hours
	 *         of a digital clock is at all times equivalent with setting
	 *         the hours to their old value incremented by 1. Expanding the
	 *         invocation of the method setHours with its postconditions, yields
	 *         postconditions for the method advanceHours.  
	 */
	public void advanceHours() {
		setHours(getHours() + 1);
	}

	/**
	 * Reset the hours of this digital clock to the lowest
	 * possible value for the hours of this digital clock.
	 *
	 * @effect The hours displayed by this digital clock are set to
	 *         the lowest possible value for the hours of this digital
	 *         clock.
	 *       | setHours(getMinHours())
	 */
	public void resetHours() {
		setHours(getMinHours());
	}

	/**
	 * Variable registering the hours of this digital clock.
	 */
	private int hours = getMinHours();

	/**
	 * Return the minutes displayed by this digital clock.
	 */
	@Basic
	public int getMinutes() {
		return this.minutes;
//		// Alternative implementation in case secondsSinceMidnight is
//		// used as the representation.
//		return (this.secondsSinceMidnight / SECONDS_PER_MINUTE) % getMinutesPerHour() + getMinHours();
	}

	/**
	 * Return the lowest possible value for the minutes of all digital
	 * clocks.
	 *
	 * @return The lowest possible value for the minutes of all digital
	 *         clocks is non-negative.
	 *		 | result >= 0
	 * @note   This method reflects that all digital clocks use the same
	 *         lowest possible value for their minutes. In taking that
	 *         decision, we have chosen not to make our class adaptable
	 *         towards different lowest possible values for the minutes
	 *         of different digital clocks. In that case, the method must
	 *         be changed into an instance method, and all our clients
	 *         must change their code at points where they used the
	 *         method.
	 *         Notice that the documentation does not reveal the effective
	 *         value returned by this inspector. This means that we can
	 *         change the lowest possible value for the minutes of all
	 *         digital clocks into another value, without having to notify
	 *         all the clients of the class. If we would reveal that the
	 *         lowest possible value for the minutes of all digital clocks
	 *         is 0, we had better used a symbolic constant instead of a
	 *         static method. Indeed, in that case, the only thing we can
	 *         still change without having to notify the clients is the
	 *         implementation of the method. As a silly example, we could
	 *         change the body of the method to "return 1-1;". In the end,
	 *         we must always return the value 0, otherwise we would break
	 *         the contract.
	 */
	@Basic
	public static int getMinMinutes() {
		return 0;
	}

	/**
	 * Return the highest possible value for the minutes of all digital
	 * clocks.
	 *
	 * @return The highest possible value for the minutes of all
	 *         digital clocks is not below the lowest possible value
	 *         for the minutes of all digital clocks.
	 *       | result >= getMinMinutes()
	 */
	@Basic
	public static int getMaxMinutes() {
		return 59;
	}

	/**
	 * Return the number of minutes per hour.
	 * 
	 * @return The difference between the maximum number of minutes and
	 *         the minimum number of minutes incremented by 1.
	 *       | result == getMaxMinutes() - getMinMinutes() + 1
	 */
	public static int getMinutesPerHour() {
		return getMaxMinutes() - getMinMinutes() + 1;
	}

	/**
	 * Return the number of minutes passed since midnight.
	 *    This method in fact returns the number of minutes between the
	 *    time currently displayed by this digital clock and the time
	 *    in which hours and minutes seconds have their minimum value.
	 * 
	 * @return The sum of (1) the number of hours above the lowest possible value
	 *         for the hours, multiplied by the number of minutes per hour, 
	 *         and (2) the number of minutes above the lowest possible value for
	 *         the minutes.
	 *       | result ==
	 *       |   (getHours()-getMinHours())*getMinutesPerHour() +
	 *       |   (getMinutes()-getMinMinutes())
	 */
	public int getMinutesSinceMidnight() {
		return (getHours() - getMinHours()) * getMinutesPerHour()
			 + (getMinutes() - getMinMinutes());
	}

	/**
	 * Set the minutes of this digital clock to the given minutes.
	 *
	 * @param  minutes
	 *		   The new minutes for this digital clock.
	 * @post   If the given minutes are in the range of the minutes for all
	 * 		   digital clocks, the minutes of this digital clock are equal
	 * 		   to the given minutes.
	 *       | if ( (minutes >= getMinMinutes()) && (minutes <= getMaxMinutes()) )
	 *       |   then new.getMinutes() == minutes
	 * @post   If the given minutes exceed the highest possible value for the
	 * 		   minutes of all digital clocks, or the given minutes are below
	 *         the lowest possible value for the minutes of all digital clocks,
	 *         the minutes of this digital clock remain unchanged.
	 *       | if ( (minutes > getMaxMinutes() || (minutes < getMinMinutes()) )
	 *       |   then new.getMinutes() = getMinutes()
	 * @note   The second postcondition is not really needed. Indeed, the
	 *         inertia axiom for specifications states that everything that is
	 *         not mentioned in the specification of a method, is left untouched.
	 */
	public void setMinutes(int minutes) {
		if ((minutes >= getMinMinutes()) && (minutes <= getMaxMinutes()))
			this.minutes = minutes;
//		// Alternative implementation in case secondsSinceMidnight is
//		// used as the representation.
//		if ((minutes >= getMinMinutes()) && (minutes <= getMaxMinutes()))
//			this.secondsSinceMidnight += ((minutes - this.getMinutes()) - getMinMinutes())*SECONDS_PER_MINUTE;
	}

	/**
	 * Advance the time displayed by this digital clock with 1 minute.
	 *
	 * @effect If the minutes of this digital clock have not reached their highest
	 *		   possible value, the minutes currently displayed by this digital
	 *         clock incremented by 1 are set as the new minutes displayed
	 *         by this digital clock.
	 *       | if (getMinutes() < getMaxMinutes())
	 *       |   then setMinutes(getMinutes()+1)
	 * @effect If the minutes of this digital clock have reached their highest
	 *		   possible value, the hours of this digital clock are advanced by
	 *		   1 and the minutes of this digital clock are reset to their
	 *         lowest possible value.
	 *       | if (getMinutes() == getMaxMinutes())
	 *       |   then (advanceHours() && resetMinutes())
	 */
	public void advanceMinutes() {
		if (getMinutes() < getMaxMinutes())
			setMinutes(getMinutes() + 1);
		else {
			resetMinutes();
			advanceHours();
		}
	}

	/**
	 * Reset the minutes of this digital clock to the lowest
	 * possible value for the minutes of a digital clock.
	 *
	 * @effect The minutes displayed by this digital clock are set to
	 *         the lowest possible value for the minutes of all digital
	 *         clocks.
	 *       | setMinutes(getMinMinutes())
	 */
	public void resetMinutes() {
		setMinutes(getMinMinutes());
	}

	/**
	 * Variable registering the minutes of this digital clock.
	 */
	private int minutes = getMinMinutes();
	
	//	/**
	//	 * Variable registering the seconds that have passed since midnight.
	//	 * 
	//	 * @note   This is an alternative representation. Getters and setters
	//	 *         include an alternative implementation in view of this representation.
	//	 */
	//	private int secondsSinceMidnight;
	
		/**
		 * Check whether the time displayed by this digital clock is earlier
		 * than the time displayed by the other digital clock.
		 * 
		 * @param  other
		 * 		   The clock to compare with.
		 * @return False if the other clock is not effective.
		 *       | if (other == null)
		 *       |   then result == false
		 * @return If the other clock is effective, true if and only if
		 *         the seconds passed since midnight according to this
		 *         digital clock are less than the seconds passed since
		 *         midnight according to the other digital clock.
		 *       | if (other != null)
		 *       |   then result ==
		 *       |          this.getSecondsSinceMidnight() <
		 *       |               other.getSecondsSinceMidnight()
		 * @note   This method is a generalized version of the original
		 *         method (without the homework) to take seconds into
		 *         account as well.
		 */
		public boolean displaysEarlierTimeThan(DigitalClock other) {
			return (other != null)
					&& (this.getSecondsSinceMidnight() < other
							.getSecondsSinceMidnight());
		}

	/**
	 * Synchronize the time displayed by this digital clock with the
	 * time displayed by the given digital clock.
	 * 
	 * @param  other
	 *         The digital clock to synchronize with.
	 * @post   If the given digital clock is effective the new number of
	 *         seconds that have passed according to this clock are equal
	 *         to the number of seconds that have passed according to the
	 *         other clock..
	 *       | if ( (other != null)
	 *       |   then new.getSecondsSinceMidnight() ==
	 *       |          other.getSecondsSinceMidnight()
	 * @note   This method is a generalized version of the original
	 *         method (without the homework) to take seconds into
	 *         account as well.
	 */
	public void synchronizeWith(DigitalClock other) {
		if (other != null) {
			this.setSeconds(other.getSecondsSinceMidnight()
					% SECONDS_PER_MINUTE);
			this.setMinutes((other.getSecondsSinceMidnight() / SECONDS_PER_MINUTE)
					% getMinutesPerHour());
			this.setHours(other.getSecondsSinceMidnight()
					/ (SECONDS_PER_MINUTE * getMinutesPerHour()));
		}
	}

	

	
/** HOMEWORK **/	

	/**
	 * Initialize this new digital clock with given hours, given
	 * minutes and given seconds.
	 *
	 * @param  hours
	 *		   The hours for this new digital clock.
	 * @param  minutes
	 *		   The minutes for this new digital clock.
	 * @param  seconds
	 *		   The seconds for this new digital clock.
	 * @post   The lowest possible value for the hours of this new
	 * 		   digital clock is equal to 0.
	 *       | new.getMinHours() == 0
	 * @post   The highest possible value for the hours of this new
	 * 		   digital clock is equal to 23.
	 *       | new.getMaxHours() == 23
	 * @post   If the given hours are in the range 0..23, the hours of
	 *         this new digital clock are equal to the given hours.
	 *         If the given hours exceed 23, the hours for this new
	 *         digital clock are equal to the given hours modulo 24.
	 *         If the given hours are negative, the hours for  this new
	 *         digital clock are equal to 0.
	 *       | if ( (hours >= 0) && (hours <= 23) )
	 *       |   then new.getHours() == hours
	 *       | else if (hours > 23)
	 *       |   then new.getHours() == (hours % 24)
	 *       | else if (hours < 0)
	 *       |   then new.getHours() == 0
	 * @effect The given minutes are set as the minutes of this
	 *         new digital clock.
	 *       | setMinutes(minutes)
	 * @effect The given seconds are set as the seconds of this
	 *         new digital clock.
	 *       | setSeconds(seconds)
	 * @note   Because this constructor becomes the most extended constructor,
	 *         we should define the other constructors in terms of this one.
	 *         We have not done that to keep the original code without the
	 *         howework intact.
	 */
	public DigitalClock(int hours, int minutes, int seconds) {
		setHours(hours);
		setMinutes(minutes);
		setSeconds(seconds);
	}
	
	/**
	 * Return the seconds displayed by this digital clock.
	 */
	@Basic
	public int getSeconds() {
		return this.seconds;
//		// Alternative implementation in case secondsSinceMidnight is
//		// used as the representation.
//		return this.secondsSinceMidnight % SECONDS_PER_MINUTE;
	}

	/**
	 * Return the number of seconds passed since midnight.
	 *    This method in fact returns the number of seconds between the
	 *    time currently displayed by this digital clock and the time
	 *    in which hours, minutes and seconds have their minimum value.
	 * 
	 * @return The sum of (1) the minutes that have passed since midnight,
	 *         multiplied by the number of seconds per minute, and (3) the
	 *         number of seconds above the lowest possible value for the seconds.
	 *       | result ==
	 *       |   getMinutesSinceMidnight()*SECONDS_PER_MINUTE +
	 *       |   (getSeconds()-MIN_SECONDS)
	 */
	public int getSecondsSinceMidnight() {
		return getMinutesSinceMidnight()*SECONDS_PER_MINUTE + (getSeconds() - MIN_SECONDS);
	}

	/**
	 * Constant reflecting the lowest possible value for the seconds
	 * of a digital clock.
	 * 
	 * @return The lowest possible value for the seconds of all
	 *         digital clocks is 0.
	 *       | result == 0
	 */
	public static final int MIN_SECONDS = 0;

	/**
	 * Constant reflecting the highest possible value for the seconds
	 * of a digital clock.
	 * 
	 * @return The highest possible value for the seconds of all
	 *         digital clocks is 59.
	 *       | result == 59
	 */
	public static final int MAX_SECONDS = 59;

	/**
	 * Constant reflecting the number of seconds per minute.
	 * 
	 * @return The number of seconds per minute is equal to the highest
	 *         possible value for the seconds diminished with the lowest
	 *         possible value + 1.
	 *       | result == MAX_SECONDS - MIN_SECONDS + 1
	 */
	public static final int SECONDS_PER_MINUTE = MAX_SECONDS - MIN_SECONDS + 1;

	/**
	 * Set the seconds of this digital clock to the given seconds.
	 *
	 * @param  seconds
	 *		   The new seconds for this digital clock.
	 * @post   If the given seconds are in the range of the seconds for a
	 * 		   digital clock, the seconds of this digital clock are equal
	 * 		   to the given seconds.
	 *       | if ( (seconds >= MIN_SECONDS) && (seconds <= MAX_SECONDS) )
	 *       |   then new.getSeconds() == seconds
	 * @post   If the given seconds exceed the highest possible value for the
	 * 		   seconds of all digital clocks, the seconds of this digital clock
	 * 		   are equal to the the highest possible value for the seconds
	 *         of all digital clocks.
	 *       | if (seconds > MAX_SECONDS)
	 *       |   then new.getSeconds() == MAX_SECONDS
	 * @post   If the given seconds are below the lowest possible value for
	 * 		   the seconds of all digital clocks, the seconds of this digital
	 * 		   clock are equal to the lowest possible value for the seconds
	 *         of all digital clocks.
	 *       | if (seconds < MIN_SECONDS)
	 *       |   then new.getSeconds() == MIN_SECONDS
	 */
	public void setSeconds(int seconds) {
		if (seconds > MAX_SECONDS)
			seconds = MAX_SECONDS;
		else if (seconds < MIN_SECONDS)
			seconds = MIN_SECONDS;
		this.seconds = seconds;
//		// Alternative implementation in case secondsSinceMidnight is
//		// used as the representation.
//		if (seconds > MAX_SECONDS)
//			seconds = MAX_SECONDS;
//		else if (seconds < MIN_SECONDS)
//			seconds = MIN_SECONDS;
//		this.secondsSinceMidnight += seconds - this.getSeconds();
	}

	/**
	 * Advance the time displayed by this digital clock with 1 second.
	 *
	 * @effect If the seconds of this digital clock have not reached their highest
	 *		   possible value, the seconds currently displayed by this digital
	 *         clock incremented by 1 are set as the new seconds displayed
	 *         by this digital clock.
	 *       | if (getSeconds() < MAX_SECONDS)
	 *       |   then setSeconds(getSeconds()+1)
	 * @effect If the seconds of this digital clock have reached their highest
	 *		   possible value, the minutes of this digital clock are advanced by
	 *		   1 and the seconds of this digital clock are reset to their
	 *         lowest possible value.
	 *       | if (getSeconds() == MAX_SECONDS)
	 *       |   then (advanceMinutes() && resetSeconds())
	 */
	public void advanceSeconds() {
		if (getSeconds() < MAX_SECONDS)
			setSeconds(getSeconds() + 1);
		else {
			resetSeconds();
			advanceMinutes();
		}
	}

	/**
	 * Reset the seconds of this digital clock to the lowest
	 * possible value for the seconds of a digital clock.
	 *
	 * @effect The econds displayed by this digital clock are set to
	 *         the lowest possible value for the seconds of all digital
	 *         clocks.
	 *       | setSeconds(MIN_SECONDS)
	 */
	public void resetSeconds() {
		setSeconds(MIN_SECONDS);
	}

	/**
	 * Variable registering the seconds of this digital clock.
	 */
	private int seconds = MIN_SECONDS;
	
//	/**
//	 * Variable registering the seconds that have passed since midnight.
//	 * 
//	 * @note   This is an alternative representation. Getters and setters
//	 *         include an alternative implementation in view of this representation.
//	 */
//	private int secondsSinceMidnight;


}