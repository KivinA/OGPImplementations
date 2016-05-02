package chapter_6.course_implementation.stockMarket;

import java.util.*;

import chapter_6.course_implementation.banking.money.MoneyAmount;
import chapter_6.course_implementation.banking.shares.Purchase;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class a shares involving a code, a value and a set of
 * purchases in which they are involved.
 * 
 * @invar   Each share must have a valid code.
 *        | canHaveAsCode(getCode())
 * @invar   The value of each share must be a valid value for any share.
 *        | isValidValue(getValue())
 * @invar   Each share must have proper purchases.
 *        | hasProperPurchases()
 * 
 * @version 2.0
 * @author  Eric Steegmans
 */
public class Share {

	/**
	 * Initialize this new share as a non-terminated share with given
	 * code, given initial value, and no purchases yet.
	 * 
	 * @param  code
	 *         The code for this new share.
	 * @param  value
	 *         The value for this new share.
	 * @post   The code for this new share is equal to the given code.
	 *       | new.getCode().equals(code)
	 * @effect The value for this new share is set to the given value.
	 *       | setValue(value)
	 * @post   This new share is not yet involved in any purchases.
	 *       | new.getNbPurchases() == 0
	 * @throws IllegalArgumentException
	 *         This new share cannot have the given code as its code.
	 *       | ! canHaveAsCode(code)
	 */
	@Raw
	public Share(String code, MoneyAmount value)
			throws IllegalArgumentException {
		if (!canHaveAsCode(code))
			throw new IllegalArgumentException("Illegal code!");
		setValue(value);
		this.code = code;
		allCodes.add(code);
	}

	/**
	 * Initialize this new share s a non-terminated share with given
	 * code, a value of 0 EUR, and no purchases yet.
	 * 
	 * @param  code
	 *         The code for this new share.
	 * @effect This new share is initialized with the given code
	 *         and with a value of 0 EUR.
	 *       | this(code,new MoneyAmount())
	 */
	@Raw
	public Share(String code) throws IllegalArgumentException {
		this(code, new MoneyAmount());
	}

	/**
	 * Check whether this share is terminated.
	 */
	@Basic @Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}

	/**
	 * Terminate this share.
	 *
	 * @post   This share is terminated.
	 *       | new.isTerminated()
	 * @post   All purchases belonging to this share
	 *         upon entry, have been terminated.
	 *       | for each purchase in Purchase:
	 *       |   if (this.hasAsPurchase(purchase))
	 *       |     then ((new purchase).isTerminated()) 
	 */
	public void terminate() {
		if (!isTerminated()) {
			// At this point, we cannot simply use an enhanced for statement
			// to iterate over all elements of the set and terminate each
			// purchase, because the underlying iterator throws a
			// ConcurrentModificationException.
			// We therefore collect the elements in an array.
			Purchase[] allPurchases = new Purchase[getNbPurchases()];
			purchases.toArray(allPurchases);
			for (Purchase purchase : allPurchases) {
				try {
					purchase.sell(purchase.getNbItems());
				} catch (Exception exc) {
					assert (!purchase.isGranted())
							|| (purchase.getNbItems() == 0);
				}
				purchase.terminate();
			}
			allCodes.remove(this.getCode());
			this.isTerminated = true;
		}
	}

	/**
	 * Variable registering whether or not this purchase is
	 * terminated.
	 */
	private boolean isTerminated = false;

	/**
	 * Return the code ascribed to this share.
	 */
	@Basic
	@Raw
	@Immutable
	public String getCode() {
		return this.code;
	}

	/**
	 * Check whether the given code is a valid code for this share.
	 * 
	 * @param  code
	 *         The code to check.
	 * @return False if the given code is not effective, or if it
	 *         is not composed of 4 letters or digits.
	 *       | if ( (code != null) || (! code.matches("\\w{4}")) )
	 *       |   then result == false
	 *         Otherwise true if and only if if the given code is
	 *         not ascribed to another non-terminated share.
	 *       | else result ==
	 *       |   for each share in Share:
	 *       |     ( share.isTerminated() ||
	 *       |       (share == this) ||
	 *       |       (! share.getCode().equals(code)) )
	 */
	@Raw
	public boolean canHaveAsCode(String code) {
		if ((code == null) || (!code.matches("\\w{4}")))
			return false;
		return code.equals(this.getCode()) || (!allCodes.contains(code));
	}

	/**
	 * Variable referencing the code ascribed to this share.
	 */
	private final String code;

	/**
	 * Variable collecting all the codes of non-terminated shares.
	 * 
	 * @invar  The sorted set of all codes is effective.
	 *       | allCodes != null
	 * @invar  Each code in the sorted set of all codes is
	 *         a code of a non-terminated share.
	 *       | for each code in allCodes():
	 *       |   for some share in Share:
	 *       |     ( (! share.isTerminated()) &&
	 *       |       share.getCode().equals(code) )
	 * @invar  The codes of all non-terminated shares are registered
	 *         in the set of sorted codes.
	 *       | for each share in Share:
	 *       |   if (! share.isTerminated())
	 *       |     then allCodes.contains(share.getCode())
	 */
	private static final SortedSet<String> allCodes = new TreeSet<String>();

	/**
	 * Return the current value of this share.
	 */
	@Basic @Raw
	public MoneyAmount getValue() {
		// A copy of the instance variable must be returned
		// in order to avoid undisciplined changes to that
		// value outside the scope of this class.
		if (this.value == null)
			return null;
		return this.value.getCopy();
	}

	/**
	 * Check whether the given value is a valid value for
	 * any share.
	 * 
	 * @param  value
	 *         The value to check.
	 * @return True if and only if the given value is effective
	 *         and not negative.
	 *       | result == (value != null) && (! value.isNegative())
	 */
	public static boolean isValidValue(MoneyAmount value) {
		return (value != null) && (!value.isNegative());
	}

	/**
	 * Set the value of this share to a value identical to
	 * the given value.
	 * 
	 * @param   value
	 *          The new value for this share.
	 * @post    The value of this share is set to a value that is
	 *          identical to the given value.
	 *        | new.getValue().isIdenticalTo(value)
	 * @throws  IllegalArgumentException
	 *          The given value is not a valid value for any share.
	 *        | ! isValidValue(value)
	 * @throws  IllegalStateException
	 *          This share is already terminated.
	 *        | isTerminated()
	 */
	@Raw
	public void setValue(MoneyAmount value) throws IllegalArgumentException,
			IllegalStateException {
		if (!isValidValue(value))
			throw new IllegalArgumentException("Inappropriate value!");
		if (isTerminated())
			throw new IllegalStateException("Terminated share!");
		// A copy of the given value must be registered
		// in order to avoid undisciplined changes to the
		// value outside the scope of this class.
		this.value.changeValue(value);
	}

	/**
	 * Variable referencing the value of this share.
	 * 
	 * @invar  The referenced value is a valid value for any share.
	 *       | isValidValue(value)
	 */
	private final MoneyAmount value = new MoneyAmount();

	/**
	 * Return the purchase associated with this share at the
	 * given index.
	 * 
	 * @param  index
	 *         The index of the purchase to return.
	 * @throws IndexOutOfBoundsException
	 *         The given index is not positive or it exceeds the
	 *         number of purchases for this share.
	 *       | (index < 1) || (index > getNbPurchases())
	 */
	@Basic
	@Raw
	public Purchase getPurchaseAt(int index) throws IndexOutOfBoundsException {
		return purchases.get(index - 1);
	}

	/**
	 * Return the number of purchases associated with this share.
	 */
	@Basic
	@Raw
	public int getNbPurchases() {
		return purchases.size();
	}

	/**
	 * Check whether this share can have the given purchase
	 * as one of its purchases.
	 * 
	 * @param  purchase
	 *         The purchase to check.
	 * @return True if and only if the given purchase is effective
	 *         and that purchase can have this share as its share.
	 *       | result ==
	 *       |   (purchase != null) &&
	 *       |   purchase.canHaveAsShare(this)
	 */
	@Raw
	public boolean canHaveAsPurchase(Purchase purchase) {
		return (purchase != null) && (purchase.canHaveAsShare(this));
	}

	/**
	 * Check whether this share can have the given purchase
	 * as one of its purchases at the given index.
	 * 
	 * @param  purchase
	 *         The purchase to check.
	 * @return False if the given index is not positive or exceeds the
	 *         number of purchases for this share + 1.
	 *       | if ( (index < 1) || (index > getNbPurchases()+1) )
	 *       |   then result == false
	 *         Otherwise, false if this share cannot have the given
	 *         purchase as one of its purchases.
	 *       | else if ( ! this.canHaveAsPurchase(purchase) )
	 *       |   then result == false
	 *         Otherwise, true if and only if the given purchase is
	 *         not registered at another index than the given index.
	 *       | else result ==
	 *       |   for each I in 1..getNbPurchases():
	         |     (index == I) || (getPurchaseAt(I) != purchase)
	 */
	@Raw
	public boolean canHaveAsPurchaseAt(Purchase purchase, int index) {
		if ((index < 1) || (index > getNbPurchases() + 1))
			return false;
		if (!this.canHaveAsPurchase(purchase))
			return false;
		for (int i = 1; i < getNbPurchases(); i++)
			if ((i != index) && (getPurchaseAt(i) == purchase))
				return false;
		return true;
	}

	/**
	 * Check whether this share has proper purchases attached to it.
	 * 
	 * @return True if and only if this share can have each of the
	 *         purchases attached to it as a purchase at the given index,
	 *         and if each of these purchases references this share as
	 *         the share to which they are attached.
	 *       | result ==
	 *       |   for each I in 1..getNbPurchases():
	 *       |     ( this.canHaveAsPurchaseAt(purchase,I) &&
	 *       |       (purchase.getShare() == this) )
	 */
	public boolean hasProperPurchases() {
		for (int i = 1; i <= getNbPurchases(); i++) {
			if (!canHaveAsPurchaseAt(getPurchaseAt(i), i))
				return false;
			if (getPurchaseAt(i).getShare() != this)
				return false;
		}
		return true;
	}

	/**
	 * Check whether this share has the given purchase as one of its
	 * purchases.
	 * 
	 * @param  purchase
	 * 		   The purchase to check.
	 * @return The given purchase is registered at some position as
	 *         a purchase of this share.
	 *       | for some I in 1..getNbPurchases():
	 *       |   getPurchaseAt(I) == purchase
	 */
	public boolean hasAsPurchase(@Raw Purchase purchase) {
		return purchases.contains(purchase);
		// A more efficient implementation would be possible if
		// the consistency imposed on the bi-directional association
		// would be guaranteed.
		// return (purchase != null) && (purchase.getShare() == this);
	}

	/**
	 * Add the given purchase to the list of purchases of this share.
	 * 
	 * @param  purchase
	 *         The purchase to be added.
	 * @pre    The given purchase is effective and already references
	 *         this share, and this share does not yet have the given
	 *         purchase as one of its purchases.
	 *       | (purchase != null) && (purchase.getShare() == this) &&
	 *       | (! this.hasAsPurchase(purchase))
	 * @post   The number of purchases of this share is
	 *         incremented by 1.
	 *       | new.getNbPurchases() == getNbPurchases() + 1
	 * @post   This share has the given purchase as its very last purchase.
	 *       | new.getPurchaseAt(getNbPurchases()+1) == purchase
	 */
	public void addPurchase(@Raw Purchase purchase) {
		assert (purchase != null) && (purchase.getShare() == this)
				&& (!this.hasAsPurchase(purchase));
		purchases.add(purchase);
	}

	/**
	 * Remove the given purchase from the list of purchases of this share.
	 * 
	 * @param  purchase
	 *         The purchase to be removed.
	 * @pre    The given purchase is effective, this share has the
	 *         given purchase as one of its purchases, and the given
	 *         purchase does not reference any share.
	 *       | (purchase != null) &&
	 *       | this.hasAsPurchase(purchase) &&
	 *       | (purchase.getShare() == null)
	 * @post   The number of purchases of this share is
	 *         decremented by 1.
	 *       | new.getNbPurchases() == getNbPurchases() - 1
	 * @post   This share no longer has the given purchase as
	 *         one of its purchases.
	 *       | ! new.hasAsPurchase(purchase)
	 * @post   All purchases registered at an index beyond the index at
	 *         which the given purchase was registered, are shifted
	 *         one position to the left.
	 *       | for each I,J in 1..getNbPurchases():
	 *       |   if ( (getPurchaseAt(I) == purchase) and (I < J) )
	 *       |     then new.getPurchaseAt(J-1) == getPurchaseAt(J)
	 */
	@Raw
	public void removePurchase(Purchase purchase) {
		assert (purchase != null) && this.hasAsPurchase(purchase)
				&& (purchase.getShare() == null);
		purchases.remove(purchase);
	}

	/**
	 * Variable referencing a list collecting all the purchases in
	 * which this share is involved.
	 * 
	 * @invar  The referenced list is effective.
	 *       | purchases != null
	 * @invar  Each purchase registered in the referenced list is
	 *         effective and not yet terminated.
	 *       | for each purchase in purchases:
	 *       |   ( (purchase != null) &&
	 *       |     (! purchase.isTerminated()) )
	 * @invar  No purchase is registered at several positions
	 *         in the referenced list.
	 *       | for each I,J in 0..purchases.size()-1:
	 *       |   ( (I == J) ||
	 *       |     (purchases.get(I) != purchases.get(J))
	 */
	private final List<Purchase> purchases = new ArrayList<Purchase>();

}