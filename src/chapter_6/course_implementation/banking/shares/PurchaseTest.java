package chapter_6.course_implementation.banking.shares;

import static org.junit.Assert.*;

import java.math.BigInteger;
import org.junit.*;

import chapter_6.course_implementation.banking.money.*;
import chapter_6.course_implementation.stockMarket.*;

public class PurchaseTest {

	private static Wallet theWallet, emptyWallet, walletWithSameShares;

	private static Share theShare, otherShare;

	private static Purchase somePurchase, terminatedPurchase, grantedPurchase;

	private static MoneyAmount somePositiveAmount;

	@Before
	public void setUp() throws Exception {
		somePositiveAmount = new MoneyAmount(BigInteger.valueOf(1000),
				Currency.EUR);
		theWallet = new Wallet();
		emptyWallet = new Wallet();
		walletWithSameShares = new Wallet();
		theShare = new Share("XXXX", new MoneyAmount());
		otherShare = new Share("YYYY", new MoneyAmount());
		new Purchase(walletWithSameShares, 3, theShare, new MoneyAmount());
		somePurchase = new Purchase(theWallet, 4, theShare, new MoneyAmount());
		terminatedPurchase = new Purchase(new Wallet(), 1, theShare,
				new MoneyAmount());
		terminatedPurchase.terminate();
		grantedPurchase = new Purchase(new Wallet(), 10, theShare,
				new MoneyAmount());
		grantedPurchase.grant();
		new Purchase(new Wallet(), 10, theShare, new MoneyAmount());
	}
	
	@After
	public void tearDown() throws Exception {
		theShare.terminate();
		otherShare.terminate();
	}

	@Test
	public void constructor$LegalCase() {
		MoneyAmount highestPrice = new MoneyAmount(BigInteger.valueOf(134),
				Currency.EUR);
		int oldNbPurchases = theShare.getNbPurchases();
		Purchase thePurchase = new Purchase(emptyWallet, 4, theShare,
				highestPrice);
		assertEquals(emptyWallet, thePurchase.getWallet());
		assertTrue(emptyWallet.hasAsPurchase(thePurchase));
		assertEquals(4, thePurchase.getNbItems());
		assertTrue(highestPrice.isIdenticalTo(thePurchase.getHighestPrice()));
		assertSame(theShare, thePurchase.getShare());
		assertEquals(oldNbPurchases + 1, theShare.getNbPurchases());
		assertSame(thePurchase, theShare.getPurchaseAt(oldNbPurchases + 1));
		assertFalse(thePurchase.isTerminated());
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructor$InvalidWallet() {
		new Purchase(null, 4, theShare, new MoneyAmount());
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructor$WalletWithPurchasesOfShare() {
		new Purchase(walletWithSameShares, 4, theShare, new MoneyAmount());
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructor$IllegalNbItems() {
		new Purchase(theWallet, 0, theShare, new MoneyAmount());
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructor$IllegalShare() {
		new Purchase(theWallet, 4, null, new MoneyAmount());
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructor$IllegalHighestPrice() {
		new Purchase(theWallet, 4, theShare, null);
	}

	@Test
	public void terminate$NonTeterminatedNonGrantedPurchase() {
		int oldNbPurchases = theShare.getNbPurchases();
		Purchase lastPurchase = theShare.getPurchaseAt(oldNbPurchases);
		somePurchase.terminate();
		assertTrue(somePurchase.isTerminated());
		assertFalse(somePurchase.isGranted());
		assertNull(somePurchase.getWallet());
		assertFalse(theWallet.hasAsPurchase(somePurchase));
		assertNull(somePurchase.getShare());
		assertEquals(oldNbPurchases-1,theShare.getNbPurchases());
		assertFalse(theShare.hasAsPurchase(somePurchase));
		assertSame(lastPurchase,theShare.getPurchaseAt(oldNbPurchases-1));
		assertEquals(0, somePurchase.getNbItems());
	}

	@Test
	public void terminate$NonTeterminatedGrantedPurchaseWithNoItems() {
		int oldNbPurchases = theShare.getNbPurchases();
		Purchase lastPurchase = theShare.getPurchaseAt(oldNbPurchases);
		somePurchase.grant();
		somePurchase.sell(somePurchase.getNbItems());
		somePurchase.terminate();
		assertTrue(somePurchase.isTerminated());
		assertFalse(somePurchase.isGranted());
		assertNull(somePurchase.getWallet());
		assertFalse(theWallet.hasAsPurchase(somePurchase));
		assertNull(somePurchase.getShare());
		assertEquals(oldNbPurchases-1,theShare.getNbPurchases());
		assertFalse(theShare.hasAsPurchase(somePurchase));
		assertSame(lastPurchase,theShare.getPurchaseAt(oldNbPurchases-1));
		assertEquals(0, somePurchase.getNbItems());
	}

	@Test
	public void terminate$AlreadyTerminatedPurchase() {
		somePurchase.terminate();
		somePurchase.terminate();
		assertTrue(somePurchase.isTerminated());
		assertFalse(somePurchase.isGranted());
		assertNull(somePurchase.getWallet());
		assertNull(somePurchase.getShare());
	}

	@Test(expected = IllegalStateException.class)
	public void terminate$GrantedPurchaseWithItems() {
		somePurchase.grant();
		somePurchase.terminate();
	}

	@Test
	public void hasProperSate_OrderedPurchase() {
		assertTrue(somePurchase.hasProperState());
	}

	@Test
	public void hasProperSate_GrantedPurchase() {
		assertTrue(grantedPurchase.hasProperState());
	}

	@Test
	public void hasProperSate_TerminatedPurchase() {
		assertTrue(terminatedPurchase.hasProperState());
	}
	
	@Test
	public void grant$LegalCase() {
		somePurchase.grant();
		assertTrue(somePurchase.isGranted());
	}

	@Test(expected = IllegalStateException.class)
	public void grant$IllegalCase() throws IllegalStateException {
		terminatedPurchase.grant();
	}

	@Test
	public void isValidHighestPrice$TrueCase() {
		assertTrue(Purchase.isValidHighestPrice(new MoneyAmount()));
	}

	@Test
	public void isValidHighestPrice$NonEffectivePrice() {
		assertFalse(Purchase.isValidHighestPrice(null));
	}

	@Test
	public void isValidHighestPrice$NegativePrice() {
		assertFalse(Purchase.isValidHighestPrice(new MoneyAmount(BigInteger
				.valueOf(-100), Currency.EUR)));
	}

	@Test
	public void setHighestPrice$LegalCase() {
		somePurchase.setHighestPrice(somePositiveAmount);
		assertTrue(somePurchase.getHighestPrice().isIdenticalTo(
				somePositiveAmount));
	}

	@Test(expected = IllegalArgumentException.class)
	public void setHighestPrice$IllegalPrice() throws IllegalArgumentException {
		somePurchase.setHighestPrice(null);
	}

	@Test(expected = IllegalStateException.class)
	public void setHighestPrice$AlreadyGranted() throws IllegalStateException {
		grantedPurchase.setHighestPrice(somePositiveAmount);
	}

	@Test(expected = IllegalStateException.class)
	public void setHighestPrice$AlreadyTerminated()
			throws IllegalStateException {
		terminatedPurchase.setHighestPrice(somePositiveAmount);
	}

	@Test
	public void canHaveAsNbItems$NonGrantedPurchaseTrueCase() {
		assertTrue(somePurchase.canHaveAsNbItems(1));
	}

	@Test
	public void canHaveAsNbItems$NonGrantedPurchaseFalseCase() {
		assertFalse(somePurchase.canHaveAsNbItems(0));
	}

	@Test
	public void canHaveAsNbItems$GrantedPurchaseTrueCase() {
		assertTrue(grantedPurchase.canHaveAsNbItems(0));
	}

	@Test
	public void canHaveAsNbItems$GrantedPurchaseFalseCase() {
		assertFalse(grantedPurchase.canHaveAsNbItems(-1));
	}

	@Test
	public void canHaveAsNbItems$TerminatedPurchaseTrueCase() {
		assertTrue(terminatedPurchase.canHaveAsNbItems(0));
	}

	@Test
	public void canHaveAsNbItems$TerminatedPurchaseFalseCase() {
		assertFalse(terminatedPurchase.canHaveAsNbItems(-1));
	}

	@Test
	public void changeNbItems$LegalCase() {
		somePurchase.changeNbItems(7);
		assertEquals(7, somePurchase.getNbItems());
	}

	@Test(expected = IllegalArgumentException.class)
	public void changeNbItems$IllegalNbItems() throws IllegalArgumentException {
		somePurchase.changeNbItems(-1);
	}

	@Test(expected = IllegalStateException.class)
	public void changeNbItems$AlreadyGranted() throws IllegalStateException {
		grantedPurchase.changeNbItems(7);
	}

	@Test(expected = IllegalStateException.class)
	public void changeNbItems$AlreadyTerminated() throws IllegalStateException {
		terminatedPurchase.changeNbItems(7);
	}

	@Test
	public void sell$LegalCase() {
		int oldNbItems = grantedPurchase.getNbItems();
		grantedPurchase.sell(2);
		assertEquals(oldNbItems - 2, grantedPurchase.getNbItems());
	}

	@Test(expected = IllegalArgumentException.class)
	public void sell$NumberOfItemsTooHigh() throws IllegalArgumentException {
		grantedPurchase.sell(grantedPurchase.getNbItems() + 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void sell$NonPositiveNumberOfItems() throws IllegalArgumentException {
		grantedPurchase.sell(0);
	}

	@Test(expected = IllegalStateException.class)
	public void sell$NonGrantedPurchase() throws IllegalStateException {
		somePurchase.sell(1);
	}

	@Test
	public void canHaveAsWallet$NonEffectiveWalletForTerminatedPurchase() {
		somePurchase.terminate();
		assertTrue(somePurchase.canHaveAsWallet(null));
	}

	@Test
	public void canHaveAsWallet$EffectiveWalletForTerminatedPurchase() {
		somePurchase.terminate();
		assertFalse(somePurchase.canHaveAsWallet(emptyWallet));
	}

	@Test
	public void canHaveAsWallet$OtherWalletForNonTerminatedPurchase() {
		assertTrue(somePurchase.canHaveAsWallet(emptyWallet));
	}

	@Test
	public void canHaveAsWallet$NonEffectiveWalletForNonTerminatedPurchase() {
		assertFalse(somePurchase.canHaveAsWallet(null));
	}

	@Test
	public void canHaveAsWallet$TerminatedWalletForNonTerminatedPurchase() {
		emptyWallet.terminate();
		assertFalse(somePurchase.canHaveAsWallet(emptyWallet));
	}

	@Test
	public void canHaveAsWallet$OwnWallet() {
		Wallet theWallet = somePurchase.getWallet();
		assertTrue(somePurchase.canHaveAsWallet(theWallet));
	}

	@Test
	public void hasProperWallet$NonTerminatedPurchase() {
		assertTrue(somePurchase.hasProperWallet());
	}

	@Test
	public void hasProperWallet$TerminatedPurchase() {
		assertTrue(terminatedPurchase.hasProperWallet());
	}

	@Test
	public void canHaveAsShare$NonEffectiveShareForTerminatedPurchase() {
		assertTrue(terminatedPurchase.canHaveAsShare(null));
	}

	@Test
	public void canHaveAsShare$LegalShareForNonTerminatedPurchase() {
		assertTrue(somePurchase.canHaveAsShare(otherShare));
	}

	@Test
	public void canHaveAsShare$EffectiveShareForTerminatedPurchase() {
		assertFalse(terminatedPurchase.canHaveAsShare(otherShare));
	}

	@Test
	public void canHaveAsShare$NonEffectiveShareForNonTerminatedPurchase() {
		assertFalse(somePurchase.canHaveAsShare(null));
	}

	@Test
	public void canHaveAsShare$TerminatedShareForNonTerminatedPurchase() {
		otherShare.terminate();
		assertFalse(somePurchase.canHaveAsShare(otherShare));
	}

	@Test
	public void hasProperShare$NonTerminatedPurchase() {
		assertTrue(somePurchase.hasProperShare());
	}

	@Test
	public void hasProperShare$TerminatedPurchase() {
		// False case cannot be tested for own share.
		assertTrue(terminatedPurchase.hasProperShare());
	}

}
