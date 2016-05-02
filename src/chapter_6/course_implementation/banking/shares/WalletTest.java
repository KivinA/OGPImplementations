package chapter_6.course_implementation.banking.shares;

import static org.junit.Assert.*;
import org.junit.*;

import chapter_6.course_implementation.banking.money.MoneyAmount;
import chapter_6.course_implementation.stockMarket.Share;

public class WalletTest {

	private Wallet someWallet, terminatedWallet;
	private Share someShare, otherShare;
	private Share[] tempShares;

	@Before public void setUp() throws Exception {
		someWallet = new Wallet();
		someShare = new Share("ACEG");
		new Purchase(someWallet, 10, someShare, new MoneyAmount());
		otherShare = new Share("BDFH");
		new Purchase(someWallet, 6, otherShare, new MoneyAmount());
		terminatedWallet = new Wallet();
		terminatedWallet.terminate();
		tempShares = new Share[6];
		tempShares[0] = new Share("2345");
		tempShares[1] = new Share("3456");
		tempShares[2] = new Share("4567");
		tempShares[3] = new Share("7654");
		tempShares[4] = new Share("6543");
		tempShares[5] = new Share("9876");
	}
	
	@After
	public void tearDown() throws Exception {
		someShare.terminate();
		otherShare.terminate();
		for (Share share: tempShares)
			share.terminate();
		someWallet.terminate();
	}

	@Test public void constructor$SingleCase() {
		Wallet theWallet = new Wallet();
		assertEquals(0, theWallet.getNbPurchases());
		assertFalse(theWallet.isTerminated());
	}

	@Test public void terminate$WalletWithoutPurchases() {
		someWallet.terminate();
		assertTrue(someWallet.isTerminated());
	}

	@Test public void terminate$WalletWithNonGrantedPurchase() {
		// First add non-granted purchase to some wallet.
		Purchase nonGrantedPurchase = new Purchase(someWallet, 4, tempShares[0], new MoneyAmount());
		// Test the termination of the resulting wallet.
		someWallet.terminate();
		assertTrue(someWallet.isTerminated());
		assertTrue(nonGrantedPurchase.isTerminated());
	}

	@Test public void terminate$WalletWithGrantedPurchase() {
		// First add non-granted purchase to some wallet.
		Purchase grantedPurchase = new Purchase(someWallet, 4,
			tempShares[1], new MoneyAmount());
		grantedPurchase.grant();
		// Test the termination of the resulting wallet.
		someWallet.terminate();
		assertTrue(someWallet.isTerminated());
		assertTrue(grantedPurchase.isTerminated());
		assertEquals(0, grantedPurchase.getNbItems());
	}

	@Test public void terminate$WalletWithSeveralPurchases() {
		// First add several purchases to some wallet.
		Purchase[] severalPurchases = new Purchase[6];
		severalPurchases[0] = new Purchase(someWallet, 4, tempShares[0],
			new MoneyAmount());
		severalPurchases[1] = new Purchase(someWallet, 4, tempShares[1],
			new MoneyAmount());
		severalPurchases[2] = new Purchase(someWallet, 4, tempShares[2],
			new MoneyAmount());
		severalPurchases[3] = new Purchase(someWallet, 4, tempShares[3],
			new MoneyAmount());
		severalPurchases[4] = new Purchase(someWallet, 4, tempShares[4],
			new MoneyAmount());
		severalPurchases[5] = new Purchase(someWallet, 4, tempShares[5],
			new MoneyAmount());
		// Test the termination of the resulting wallet.
		someWallet.terminate();
		assertTrue(someWallet.isTerminated());
		for (int i = 0; i < severalPurchases.length; i++)
			assertTrue(severalPurchases[i].isTerminated());
	}

	@Test public void terminate$WalletAlreadyTerminated() {
		terminatedWallet.terminate();
		assertTrue(terminatedWallet.isTerminated());
	}

	@Test public void canHaveAsPurchase$LegalCaseOwnPurchase() {
		Purchase purchaseOfTheWallet = new Purchase(someWallet, 4, tempShares[2], new MoneyAmount());
		assertTrue(someWallet.canHaveAsPurchase(purchaseOfTheWallet));
	}

	@Test public void canHaveAsPurchase$NonEffectivePurchase() {
		assertFalse(someWallet.canHaveAsPurchase(null));
	}

	@Test public void canHaveAsPurchase$InvalidPurchase() {
		Purchase terminatedPurchase = new Purchase(new Wallet(), 4, tempShares[3], new MoneyAmount());
		terminatedPurchase.terminate();
		assertFalse(someWallet.canHaveAsPurchase(terminatedPurchase));
	}

	@Test public void getNbPurchases$ShareWithoutPurchases() {
		Wallet walletWithoutPurchases = new Wallet();
		assertEquals(0, walletWithoutPurchases.getNbPurchases());
	}

	@Test public void getNbPurchases$ShareWithPurchases() {
		Wallet theWallet = new Wallet();
		new Purchase(theWallet, 10, tempShares[0], new MoneyAmount());
		new Purchase(theWallet, 12, tempShares[1], new MoneyAmount());
		new Purchase(theWallet, 14, tempShares[2], new MoneyAmount());
		new Purchase(theWallet, 16, tempShares[3], new MoneyAmount());
		new Purchase(theWallet, 18, tempShares[4], new MoneyAmount());
		assertEquals(5, theWallet.getNbPurchases());
	}

	@Test public void getPurchaseOf$WalletWithGrantedPurchaseWithItemsOfShare() {
		new Purchase(someWallet, 2, tempShares[0], new MoneyAmount());
		Purchase thePurchase = new Purchase(someWallet, 16, tempShares[2],
			new MoneyAmount());
		thePurchase.grant();
		new Purchase(someWallet, 2, tempShares[4], new MoneyAmount());
		new Purchase(someWallet, 7, tempShares[3], new MoneyAmount());
		assertEquals(thePurchase,someWallet.getPurchaseOf(tempShares[2]));
	}

	@Test public void getPurchaseOf$WalletWithoutPurchasesOfShare() {
		new Purchase(someWallet, 10, tempShares[0], new MoneyAmount());
		new Purchase(someWallet, 2, tempShares[1], new MoneyAmount());
		new Purchase(someWallet, 7, tempShares[2], new MoneyAmount());
		assertNull(someWallet.getPurchaseOf(tempShares[4]));
	}

	@Test public void getPurchaseOf$NonEffectiveShare() {
		assertNull(someWallet.getPurchaseOf(null));
	}

    @Test public void hasPurchaseOf$WalletWithGrantedPurchaseWithItemsOfShare() {
        new Purchase(someWallet, 2, tempShares[0], new MoneyAmount());
        Purchase thePurchase = new Purchase(someWallet, 16, tempShares[5],
            new MoneyAmount());
        thePurchase.grant();
        new Purchase(someWallet, 2, tempShares[1], new MoneyAmount());
        new Purchase(someWallet, 7, tempShares[2], new MoneyAmount());
        assertTrue(someWallet.hasPurchaseOf(tempShares[5]));
    }

    @Test public void hasPurchaseOf$WalletWithGrantedPurchaseWithoutItemsOfShare() {
        // This test is redundant in the current version.
        new Purchase(someWallet, 10, tempShares[0], new MoneyAmount());
        Purchase thePurchase = new Purchase(someWallet, 16, tempShares[5],
            new MoneyAmount());
        thePurchase.grant();
        thePurchase.sell(thePurchase.getNbItems());
        new Purchase(someWallet, 2, tempShares[1], new MoneyAmount());
        new Purchase(someWallet, 7, tempShares[2], new MoneyAmount());
        assertTrue(someWallet.hasPurchaseOf(tempShares[5]));
    }

    @Test public void hasPurchaseOf$WalletWithNonGrantedPurchaseOfShare() {
        // This test is redundant in the current version.
        new Purchase(someWallet, 10, tempShares[0], new MoneyAmount());
        new Purchase(someWallet, 16, tempShares[5], new MoneyAmount());
        new Purchase(someWallet, 2, tempShares[1], new MoneyAmount());
        new Purchase(someWallet, 7, tempShares[2], new MoneyAmount());
        assertTrue(someWallet.hasPurchaseOf(tempShares[5]));
    }

    @Test public void hasPurchaseOf$WalletWithoutPurchasesOfShare() {
        new Purchase(someWallet, 10, tempShares[0], new MoneyAmount());
        new Purchase(someWallet, 2, tempShares[1], new MoneyAmount());
        new Purchase(someWallet, 7, tempShares[2], new MoneyAmount());
        assertFalse(someWallet.hasPurchaseOf(tempShares[5]));
    }

    @Test public void hasPurchaseOf$NonEffectiveShare() {
        assertFalse(someWallet.hasPurchaseOf(null));
    }

}