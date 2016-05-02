package chapter_6.course_implementation.banking.shares;

import java.util.*;

import chapter_6.course_implementation.banking.shares.Purchase;
import chapter_6.course_implementation.stockMarket.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of wallets involving a number of purchases of
 * shares.
 * 
 * @invar   Each wallet must have proper purchases.
 *        | hasProperPurchases()
 * 
 * @version  2.0
 * @author   Eric Steegmans
 */
public class Wallet {

    /**
     * Initialize this new wallet as a non-terminated wallet
     * without any purchases yet.
     */
	@Raw
    public Wallet() {
    }

    /**
     * Check whether this wallet is already terminated.
     */
    @Basic @Raw
    public boolean isTerminated() {
        return this.isTerminated;
    }

    /**
     * Terminate this wallet.
     *
     * @post    This wallet is terminated.
     *        | new.isTerminated()
     * @post    All purchases belonging to this wallet
     *          upon entry, have been terminated.
     *        | for each purchase in Purchase:
     *        |   if (this.hasAsPurchase(purchase))
     *        |     then ((new purchase).isTerminated()) 
     * @post    All granted purchases belonging to this wallet
     *          upon entry, have been sold completely.
     *        | for each purchase in Purchase:
     *        |   if (this.hasAsPurchase(purchase) &&
     *        |       purchase.isGranted())
     *        |     then ((new purchase).getNbItems() == 0) 
     */
    public void terminate() {
        if (!isTerminated()) {
            Collection<Purchase> elements = purchases.values();
            // We must create a new collection, because removing elements
            // from the collection 'elements' would also remove them
            // from the map 'purchases'. That results in an exception
            // of type 'ConcurrentModificationException'.
            Collection<Purchase> elementsClone = new ArrayList<Purchase>(
                elements);
            for (Purchase thePurchase : elementsClone) {
                try {
                    thePurchase.sell(thePurchase.getNbItems());
                }
                catch (Exception exc) {
                    assert (!thePurchase.isGranted())
                        || (thePurchase.getNbItems() == 0);
                }
                thePurchase.terminate();
            }
            this.isTerminated = true;
        }
    }

    /**
     * Variable registering whether or not this wallet has been
     * terminated.
     */
    private boolean isTerminated = false;

    /**
     * Check whether this wallet has the given purchase as one
     * of its purchases.
     */
    @Basic @Raw
    public boolean hasAsPurchase(@Raw
    Purchase purchase) {
        try {
            return purchases.get(purchase.getShare().getCode()) == purchase;
        }
        catch (NullPointerException exc) {
            // Because the given purchase is raw, it is possible that
            // it does not yet reference an effective share.
            assert (purchase == null) || (purchase.getShare() == null);
            return false;
        }
    }

    /**
     * Check whether this wallet can have the given purchase
     * as one of its purchases.
     * 
     * @param   purchase
     *          The purchase to check.
     * @return  True if and only if the given purchase is effective and that
     *          purchase can have this wallet as its wallet.
     *        | result ==
     *        |   (purchase != null) &&
     *        |   purchase.canHaveAsWallet(this)
     */
    @Raw
    public boolean canHaveAsPurchase(Purchase purchase) {
        return (purchase != null) && (purchase.canHaveAsWallet(this));
    }

    /**
     * Check whether this wallet has proper purchases.
     * 
     * @return  True if and only if for all purchases in this wallet,
     *          this wallet can have that purchase as one of its
     *          purchases, that purchase is the (only) purchase of
     *          its share in this wallet, and that purchase
     *          references this wallet as its wallet.
     *        | for each purchase in Purchase:
     *        |   if (hasAsPurchase(purchase))
     *        |     then canHaveAsPurchase(purchase) &&
     *        |          (getPurchaseOf(purchase.getShare()) == purchase) &&
     *        |          (purchase.getWallet() == this)
     */
    public boolean hasProperPurchases() {
        for (Purchase purchase : purchases.values()) {
            if (!canHaveAsPurchase(purchase))
                return false;
            if (getPurchaseOf(purchase.getShare()) != purchase)
                return false;
            if (purchase.getWallet() != this)
                return false;
        }
        return true;
    }

    /**
     * Return the number of purchases in this wallet.
     * 
     * @return  The total number of purchases collected in this wallet.
     *        | result ==
     *        |   card({purchase:Purchase | hasAsPurchase(purchase)})
     */
    public int getNbPurchases() {
        return purchases.size();
    }

    /**
     * Return the purchase of the given share, if any, registered in this wallet.
     * 
     * @param   share
     *          The share to check.
     * @return  The resulting purchase is either not effective, or it has the given
     *          share as its share and this wallet as its wallet.
     *        | (result == null)  || 
     *        | ( (result.getShare() == share) &&
     *        |   (result.getWallet() == this) )
     * @return  The resulting purchase is effective, if and only if this wallet
     *          has a purchase involving the given share.
     *        | (result != null) ==
     *        |   (for some purchase in Purchase:
     *        |     (purchase.getShare() == share) &&
     *        |      this.hasAsPurchase(purchase) )
     */
    public Purchase getPurchaseOf(Share share) {
        if (share == null)
            return null;
        return purchases.get(share.getCode());
    }

    /**
     * Check whether this wallet has a purchase of the given share.
     * 
     * @param   share
     *          The share to check.
     * @return  True if this wallet includes a purchase of the
     *          given share; false otherwise.
     *        | result == (getPurchaseOf(share) != null)
     */
    public boolean hasPurchaseOf(Share share) {
        return (getPurchaseOf(share) != null);
    }

    /**
     * Add the given purchase to the set of purchases registered in
     * this wallet.
     * 
     * @param   purchase
     *          The purchase to be added.
     * @pre     The given purchase is effective.
     *        | purchase != null
     * @pre     The given purchase must reference an effective share.
     *        | purchase.getShare() != null
     * @pre     The given purchase references this wallet as its wallet.
     *        | purchase.getWallet() == this
     * @pre     This wallet does not include any purchase of the
     *          share involved in the given purchase.
     *        | ! hasPurchaseOfShare(purchase.getShare())
     * @post    The given purchase is registered as one of the
     *          purchases for this wallet.
     *        | new.hasAsPurchase(purchase)
     */
    void addPurchase(@Raw
    Purchase purchase) {
        assert purchase != null;
        assert purchase.getWallet() == this;
        assert purchase.getShare() != null;
        assert !hasPurchaseOf(purchase.getShare());
        purchases.put(purchase.getShare().getCode(), purchase);
    }

    /**
     * Remove the given purchase from the purchases registered in
     * this wallet.
     * 
     * @param   purchase
     *          The purchase to be removed.
     * @pre     The given purchase is registered as one of the purchases
     *          for this wallet.
     *        | hasAsPurchase(purchase)
     * @pre     The given purchase is not referencing any wallet.
     *        | purchase.getWallet() == null
     * @post    This wallet no longer has the given purchase as one
     *          of its purchases.
     *        | ! new.hasAsPurchase(this)
     */
    void removePurchase(@Raw
    Purchase purchase) {
        assert hasAsPurchase(purchase);
        assert purchase.getWallet() == null;
        purchases.remove(purchase.getShare().getCode());
    }

    /**
     * Variable referencing a map collecting all the purchases
     * registered in this wallet.
     * 
     * @invar   The referenced map is effective.
     *        | purchases != null
     * @invar   Each key registered in the map is an effective
     *          string.
     *        | for each key in purchases.keySet():
     *        |   (key != null)
     * @invar   Each value associated with a key in the map is
     *          an effective, non-terminated purchase involving this
     *          wallet and involving a share whose code is identical
     *          to the associating key.
     *        | for each key in purchases.keySet():
     *        |   (purchases.get(key) != null) &&
     *        |   (! purchases.get(key).isTerminated()) &&
     *        |   (purchases.get(key).getWallet() == this) &&
     *        |   (purchases.get(key).getShare().getCode().equals(key))
     */
    private final Map<String, Purchase> purchases = new HashMap<String, Purchase>();

}