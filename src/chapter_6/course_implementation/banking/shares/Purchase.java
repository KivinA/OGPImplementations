package chapter_6.course_implementation.banking.shares;

import be.kuleuven.cs.som.annotate.*;
import chapter_6.course_implementation.banking.money.MoneyAmount;
import chapter_6.course_implementation.stockMarket.Share;

/**
 * A class of purchases of a number of items of a share. 
 * 
 * @invar  Each purchase must have a proper state.
 *       | hasProperState()
 * @invar  Each purchase must can have its number of items
 *         as its number of items.
 *       | canHaveAsNbItems(getNbItems())
 * @invar  The highest price of each purchase must be a valid
 *         highest price for any purchase.
 *       | isValidHighestPrice(getHighestPrice())
 * @invar  Each purchase must have a proper wallet.
 *       | hasProperWallet()
 * @invar  Each purchase must have a proper share.
 *       | hasProperShare()
 * 
 * @version 2.0
 * @author  Eric Steegmans
 */
public class Purchase {

	/**
	 * Initialize this new purchase of the given number of items
	 * of the given share at the given highest price for the given wallet.
	 * 
	 * @param  wallet
	 *         The wallet for this new purchase.
	 * @param  nbItems
	 *         The number of items for this new purchase.
	 * @param  share
	 *         The share for this new purchase.
	 * @param  highestPrice
	 *         The highest price for this new purchase.
	 * @post   This new purchase is ordered.
	 *       | new.isOrdered()
	 * @post   The wallet of this new purchase is the same as the
	 *         given wallet.
	 *       | new.getWallet() == wallet
	 * @post   The given wallet has this new purchase as one of its
	 *         purchases.
	 *       | (new wallet).hasAsPurchase(this)
	 * @effect The number of items for this new purchase is set to
	 *         the given number of items.
	 *       | setNbItems(nbItems)
	 * @effect The highest price of this new purchase is set to the 
	 *         given highest price.
	 *       | setHighestPrice(highestPrice)
	 * @post   The share of this new purchase is the same as the
	 *         given share.
	 *       | new.getShare() == share
	 * @post   The number of purchases for the given share is
	 *         incremented by 1.
	 *       | (new share).getNbPurchases() == share.getNbPurchases() + 1
	 * @post   The given share has this new purchase as its very last purchase.
	 *       | (new share).getPurchaseAt(share.getNbPurchases()+1) == this
	 * @throws IllegalArgumentException
	 *         This new purchase cannot have the given share as
	 *         its share.
	 *       | ! canHaveAsShare(share)
	 * @throws IllegalArgumentException
	 *         This new purchase cannot have the given wallet as its wallet,
	 *         or the given wallet already has purchases of the given share.
	 *       | (! canHaveAsWallet(wallet)) ||
	 *       | (wallet.getPurchaseOf(share) != null)
	 */
	@Raw
	public Purchase(Wallet wallet, int nbItems, Share share,
			MoneyAmount highestPrice) throws IllegalArgumentException {
		if ( (wallet != null) && (wallet.hasPurchaseOf(share)))
			throw new IllegalArgumentException();
		setShare(share);
		setWallet(wallet);
		share.addPurchase(this);
		wallet.addPurchase(this);
		setNbItems(nbItems);
		setHighestPrice(highestPrice);
	}

	/**
	 * Check whether this purchase is terminated.
	 */
	@Basic @Raw
	public boolean isTerminated() {
		return this.getState() == State.TERMINATED;
	}

	/**
	 * Terminate this purchase.
	 * 
	 * @post   This purchase is terminated.
	 *       | new.isTerminated()
	 * @post   This purchase is no longer granted.
	 *       | ! new.isGranted()
	 * @post   This purchase no longer references an effective wallet.
	 *       | new.getWallet() == null
	 * @post   If this purchase was not yet terminated, this purchase
	 *         is no longer one of the purchases for the wallet to which
	 *         this purchase belonged.
	 *       | if (! isTerminated())
	 *       |   then ! (new getWallet()).hasAsPurchase(this))
	 * @post   If this purchase was not yet terminated, the number of
	 *         purchases of the share involved in this purchase is
	 *         decremented by 1.
	 *       | if (! isTerminated())
	 *       |   then (new getShare()).getNbPurchases() ==
	 *       |            getShare().getNbPurchases() - 1
	 * @post   If this purchase was not yet terminated, the share
	 *         involved in this purchase no longer has this purchase as
	 *         one of its purchases.
	 *       | if (! isTerminated())
	 *       |   then (! (new getShare()).hasAsPurchase(this))
	 * @post   If this purchase was not yet terminated, all purchases
	 *         of the share involved in this purchase registered at an
	 *         index beyond the index at which this purchase was registered,
	 *         are shifted one position to the left.
	 *       | for each I,J in 1..getShare().getNbPurchases():
	 *       |   if ( (getShare().getPurchaseAt(I) == purchase) and (I < J) )
	 *       |     then (new getShare()).getPurchaseAt(J-1) == getShare().getPurchaseAt(J)
	 * @post   This purchase no longer has any items.
	 *       | new.getNbItems() == 0
	 * @throws IllegalStateException
	 *         This purchase is granted, and it still has some items.
	 *       | isGranted() && (getNbItems() > 0)
	 */
	public void terminate() throws IllegalStateException {
		if (!isTerminated()) {
			if (isGranted() && (getNbItems() > 0))
				throw new IllegalStateException("Purchase with items!");
			setState(State.TERMINATED);
			Wallet oldWallet = getWallet();
			setWallet(null);
			oldWallet.removePurchase(this);
			Share oldShare = this.getShare();
			setShare(null);
			oldShare.removePurchase(this);
			setNbItems(0);
		}
	}

	/**
	 * Check whether this purchase has a proper state.
	 * 
	 * @return True if and only if this purchase is either ordered,
	 *         granted or terminated.
	 *       | result == isOrdered() ^ isGranted() ^ isTerminated()
	 */
	@Raw
	public boolean hasProperState() {
		return isOrdered() ^ isGranted() ^ isTerminated();
	}
	
	/**
	 * Check whether this purchase is ordered.
	 */
	@Basic
	public boolean isOrdered() {
		return this.getState() == State.ORDERED;
	}

	/**
	 * Check whether this purchase has already been granted.
	 */
	@Basic
	public boolean isGranted() {
		return this.getState() == State.GRANTED;
	}

	/**
	 * Register that this purchase has been granted.
	 * 
	 * @post    This purchase is granted.
	 *        | new.isGranted() == true
	 * @throws  IllegalStateException
	 *          This purchase is already terminated.
	 *        | isTerminated()
	 */
	public void grant() throws IllegalStateException {
		if (isTerminated())
			throw new IllegalStateException("Ungrantable purchase");
		this.setState(State.GRANTED);
	}

	/**
	 * Enumeration of all possible states of a purchase.
	 * 
	 * @Note   Java supports the definition of nested types, meaning
	 *         that classes, enumerations, ... can be defined as 
	 *         ingredients of other classes, ...
	 */
	private static enum State {
		ORDERED, GRANTED, TERMINATED;
	}
	
	/**
	 * Return the state of this purchase.
	 */
	@Raw
	private State getState() {
		return this.state;
	}

	/**
	 * Set the state of this purchase to the given state.
	 * 
	 * @param  state
	 *         The new state for this purchase.
	 * @pre    The given state must be effective.
	 *       | state != null
	 * @post   The state of this purchase is the same as the
	 *         given state.
	 *       | new.getState() == state
	 */
	private void setState(State state) {
		assert (state != null);
		this.state = state;
	}

	/**
	 * Variable registering the state of this purchase is.
	 */
	private State state = State.ORDERED;

	/**
	 * Return the highest possible price at which this purchase
	 * may be granted.
	 */
	@Basic @Raw
	public MoneyAmount getHighestPrice() {
		// A copy must be returned, because money amounts are mutable
		// objects, and users must not have the ability to change the
		// highest price for a purchase without appealing to methods
		// offered by the class of purchases.
		return highestPrice.getCopy();
	}

	/**
	 * Check whether the given highest price is a valid highest
	 * price for any purchase.
	 * 
	 * @param  highestPrice
	 *         The highest price to check.
	 * @return True if and only if the given highest price is effective
	 *         and not negative.
	 *       | result ==
	 *       |   (highestPrice != null) &&
	 *       |   (! highestPrice.isNegative())
	 */
	public static boolean isValidHighestPrice(MoneyAmount highestPrice) {
		return (highestPrice != null) && (!highestPrice.isNegative());
	}

	/**
	 * Set the highest price at which this purchase may be
	 * granted to the given highest price.
	 * 
	 * @param  highestPrice
	 *         The new highest price for this purchase.
	 * @post   The highest price at which this purchase may be granted
	 *         is identical to the given highest price.
	 *       | new.getHighestPrice().isIdenticalTo(highestPrice)
	 * @throws IllegalArgumentException
	 *         The given highest price is not a valid highest price
	 *         for any purchase.
	 *       | ! isValidHighestPrice(highestPrice)
	 * @throws IllegalStateException
	 *         This purchase has already been granted, or it is already
	 *         terminated.
	 *       | (isGranted() || isTerminated())
	 */
	@Raw
	public void setHighestPrice(MoneyAmount highestPrice)
			throws IllegalArgumentException, IllegalStateException {
		if (!isValidHighestPrice(highestPrice))
			throw new IllegalArgumentException("Invalid highest price!");
		if (isGranted())
			throw new IllegalStateException("Purchase already granted!");
		if (isTerminated())
			throw new IllegalStateException("Purchase already terminated!");
		// At this point the inspector getHighestPrice cannot be invoked
		// because it returns a copy (and not the real container for the
		// highest price for this purchase).
		this.highestPrice.changeValue(highestPrice);
	}

	/**
	 * Variable referencing the highest price at which this purchase
	 * may be granted.
	 * 
	 * @invar  The referenced money amount is effective.
	 *       | highestPrice != null
	 * @note   Although this instance variable is not referencing a collection,
	 *         we better impose some representation invariants. Indeed, the
	 *         getter returns a copy, and is therefore not of any use in the
	 *         implementation of the method. We therefore directly access
	 *         the instance variable, and simplify that process by imposing
	 *         representation invariants on its contents.
	 */
	private final MoneyAmount highestPrice = new MoneyAmount();

	/**
	 * Return the number of items involved in this purchase.
	 */
	@Basic @Raw
	public int getNbItems() {
		return this.nbItems;
	}

	/**
	 * Check whether this purchase can have the given number of
	 * items as its number of items.
	 * 
	 * @param  nbItems
	 *         The number of items to check.
	 * @return True if and only if the given number of items is strict
	 *         positive, or if this purchase is granted or terminated
	 *         and the given number of items is 0.
	 *       | result == 
	 *       |    (nbItems > 0) ||
	 *       |    ( (nbItems == 0) && (isGranted() || isTerminated()) )
	 */
	@Raw
	public boolean canHaveAsNbItems(int nbItems) {
		return (nbItems > 0)
				|| ((nbItems == 0) && (isGranted() || isTerminated()));
	}

	/**
	 * Change the number of items involved in this purchase
	 * to the given number of items.
	 * 
	 * @param  nbItems
	 *         The new number of items for this purchase.
	 * @effect The number of items involved in this purchase is set
	 *         to the given number of items.
	 *       | setNbItems(nbItems)
	 * @throws IllegalStateException
	 *         This purchase has already been granted,
	 *         or it is already terminated.
	 *       | (isGranted() || isTerminated())
	 */
	public void changeNbItems(int nbItems) throws IllegalArgumentException,
			IllegalStateException {
		if (isGranted())
			throw new IllegalStateException("Purchase already granted!");
		if (isTerminated())
			throw new IllegalStateException("Purchase already terminated!");
		setNbItems(nbItems);
	}

	/**
	 * Sell the given number of items of the share involved
	 * in this purchase.
	 * 
	 * @param  nbItems
	 *         The number of items to sell.
	 * @effect The number of items of this purchase is set to
	 *         its current number of items decremented with the
	 *         given number of items.
	 *       | setNbItems(getNbItems() - nbItems)
	 * @throws IllegalArgumentException
	 *         The given number of items is not positive.
	 *       | (nbItems <= 0)
	 * @throws IllegalStateException
	 *         This purchase has not been granted yet.
	 *       | (! isGranted())
	 */
	public void sell(int nbItems) throws IllegalArgumentException,
			IllegalStateException {
		if (nbItems <= 0)
			throw new IllegalArgumentException("Invalid number of items!");
		if (!isGranted())
			throw new IllegalStateException("Ungranted purchase!");
		setNbItems(getNbItems() - nbItems);
	}

	/**
	 * Set the number of items involved in this purchase
	 * to the given number of items.
	 * 
	 * @param  nbItems
	 *         The new number of items for this purchase.
	 * @post   The number of items involved in this purchase is equal
	 *         to the given number of items.
	 *       | new.getNbItems() == nbItems
	 * @throws IllegalArgumentException
	 *         This purchase cannot have the given number of items 
	 *         as its number of items.
	 *       | ! canHaveAsNbItems(nbItems)
	 * @note   The setter for a characteristic will not involve other
	 *         characteristics (such as the granted state in this case)
	 *         in its definition. That explains why a separate method
	 *         changeNbItems has been introduced.
	 */
	@Raw
	@Model
	private void setNbItems(int nbItems) throws IllegalArgumentException {
		if (!canHaveAsNbItems(nbItems))
			throw new IllegalArgumentException("Invalid number of items!");
		this.nbItems = nbItems;
	}

	/**
	 * Variable registering the number of items for this purchase.
	 */
	private int nbItems = 0;

	/**
	 * Return the wallet to which this purchase belongs.
	 */
	@Basic @Raw
	public Wallet getWallet() {
		return this.wallet;
	}

	/**
	 * Check whether this purchase can have the given wallet as
	 * its wallet.
	 * 
	 * @param  wallet
	 * 		   The wallet to check.
	 * @return If this purchase is terminated, true if and only if the
	 *         given wallet is not effective.
	 *       | if (this.isTerminated())
	 *       |   then result == (wallet == null)
	 * @return If this purchase is not terminated, true if and only if the given
	 *         wallet is effective and not yet terminated.
	 *       | if (! this.isTerminated())
	 *       |   then result ==
	 *       |     (wallet != null) &&
	 *       |     (! wallet.isTerminated())
	 */
	@Raw
	public boolean canHaveAsWallet(Wallet wallet) {
		if (this.isTerminated())
			return wallet == null;
		return (wallet != null)
				&& (!wallet.isTerminated());
	}

	/**
	 * Check whether this purchase has a proper wallet.
	 * 
	 * @return True if and only if this purchase can have its wallet as its
	 *         wallet, and if the wallet of this purchase is either not effective
	 *         or if it has this purchase as one of its purchases.
	 *       | result ==
	 *       |   canHaveAsWallet(getWallet()) &&
	 *       |   ( (getWallet() == null) || getWallet().hasAsPurchase(this))
	 * @note   At this point we do not impose the condition that the wallet
	 *         may not have another purchase of the same share. That condition
	 *         is taken care of in the invariants of wallet.
	 */
	@Raw
	public boolean hasProperWallet() {
		return canHaveAsWallet(getWallet())
				&& ((getWallet() == null) || (getWallet().hasAsPurchase(this)));
	}

	/**
	 * Set the wallet of this purchase to the given wallet.
	 * 
	 * @param  wallet
	 *         The new wallet for this purchase.
	 * @post   The wallet of this purchase is the same as the
	 *         given wallet.
	 *       | new.getWallet() == wallet
	 * @throws IllegalArgumentException
	 *         This purchase cannot have the given wallet as its wallet.
	 *       | ! canHaveAsWallet(wallet)
	 */
	@Raw
	private void setWallet(Wallet wallet) {
		if (!canHaveAsWallet(wallet))
			throw new IllegalArgumentException("Inappropriate wallet!");
		this.wallet = wallet;
	}

	/**
	 * Variable referencing the wallet to which this purchase belongs.
	 */
	private Wallet wallet = null;

	/**
	 * Return the share involved in this purchase.
	 */
	@Basic @Raw
	public Share getShare() {
		return this.share;
	}

	/**
	 * Check whether this purchase can have the given share as
	 * its share.
	 * 
	 * @param   share
	 *          The share to check.
	 * @return  If this purchase is not yet terminated, true if and
	 *          only if the given share is effective and not yet
	 *          terminated
	 *        | if (! isTerminated())
	 *        |   then result == (share != null) && (! share.isTerminated())
	 * @return  If this purchase is terminated, true if and only if
	 *          the given share is not effective.
	 *        | if (! this.isTerminated())
	 *        |   then result == (share == null)
	 */
	@Raw
	public boolean canHaveAsShare(Share share) {
		if (isTerminated())
			return (share == null);
		return (share != null) && (!share.isTerminated());
	}

	/**
	 * Check whether this purchase has a proper share.
	 * 
	 * @return  True if and only if this purchase can have its share as its
	 *          share, and if this purchase is terminated or the share of
	 *          this purchase has this purchase as one of its purchases.
	 *        | result ==
	 *        |   canHaveAsShare(getShare()) &&
	 *        |   ( isTerminated() || getShare().hasAsPurchase(this))
	 */
	public boolean hasProperShare() {
		return canHaveAsShare(getShare())
				&& (isTerminated() || (getShare().hasAsPurchase(this)));
	}
	
	/**
	 * Set the share for this purchase to the given share.
	 * 
	 * @param   share
	 *          The share for this purchase.
	 * @post    The share of this purchase is the same as the
	 *          given share.
	 *        | new.getShare() == share
	 * @throws  IllegalArgumentException
	 *          This purchase cannot have the given share as its share.
	 *        | ! canHaveAsShare(share)
	 */
	@Raw
	private void setShare(Share share) throws IllegalArgumentException {
		if (!canHaveAsShare(share))
			throw new IllegalArgumentException("Inappropriate share!");
		this.share = share;
	}

	/**
	 * Variable referencing the share involved in this purchase.
	 */
	private Share share;

}