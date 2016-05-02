package chapter_6.course_implementation.stockMarket;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.*;

import chapter_6.course_implementation.banking.money.Currency;
import chapter_6.course_implementation.banking.money.MoneyAmount;
import chapter_6.course_implementation.banking.shares.Purchase;
import chapter_6.course_implementation.banking.shares.Wallet;

public class ShareTest {

	private Share theShare, shareWithCodeABCD, shareWithCodeWXYZ, shareWithoutPurchases, shareWith5Purchases, shareOfTerminatedPurchase;

	private Purchase purchaseOfOtherShare,
			purchaseOfShareWith5Purchases,
			terminatedPurchase;

	@Before public void setUp() throws Exception {
		theShare = new Share("ABBD");
		shareWithCodeABCD = new Share("ABCD");
		shareWithCodeWXYZ = new Share("WXYZ");
		shareWithoutPurchases = new Share("APPL");
		shareWith5Purchases = new Share("ZYXW");
		new Purchase(new Wallet(), 10, shareWith5Purchases, new MoneyAmount());
		new Purchase(new Wallet(), 12, shareWith5Purchases, new MoneyAmount());
		purchaseOfShareWith5Purchases = new Purchase(new Wallet(), 14,
			shareWith5Purchases, new MoneyAmount());
		new Purchase(new Wallet(), 16, shareWith5Purchases, new MoneyAmount());
		new Purchase(new Wallet(), 18, shareWith5Purchases, new MoneyAmount());
		new Purchase(new Wallet(), 4, theShare,
			new MoneyAmount());
		purchaseOfOtherShare = new Purchase(new Wallet(), 4, new Share("1234"),
			new MoneyAmount());
		terminatedPurchase = new Purchase(new Wallet(), 4, new Share("TERM"),
			new MoneyAmount());
		shareOfTerminatedPurchase = terminatedPurchase.getShare();
		terminatedPurchase.terminate();
	}
	
	@After public void tearDown() throws Exception {
		theShare.terminate();
		shareWith5Purchases.terminate();
		shareWithCodeABCD.terminate();
		shareWithCodeWXYZ.terminate();
		shareWithoutPurchases.terminate();
		purchaseOfOtherShare.getShare().terminate();
		shareOfTerminatedPurchase.terminate();
	}

	@Test public void extendedConstructor$LegalCase() {
		MoneyAmount amount_155_USD = new MoneyAmount(BigInteger.valueOf(155),
			Currency.EUR);
		Share newShare = new Share("NEWS", amount_155_USD);
		assertFalse(newShare.isTerminated());
		assertEquals("NEWS", newShare.getCode());
		assertTrue(newShare.getValue().isIdenticalTo(amount_155_USD));
		assertEquals(0, newShare.getNbPurchases());
		newShare.terminate();
	}

	@Test(expected = IllegalArgumentException.class) public void extendedConstructor$IllegalCode()
			throws IllegalArgumentException {
		new Share("%()A", new MoneyAmount());
	}

	@Test(expected = IllegalArgumentException.class) public void extendedConstructor$IllegalValue()
			throws IllegalArgumentException {
		new Share("NEWS", null);
	}

	@Test public void simpleConstructor$LegalCase() {
		Share newShare = new Share("NEWS");
		assertFalse(newShare.isTerminated());
		assertEquals("NEWS", newShare.getCode());
		assertTrue(newShare.getValue().isIdenticalTo(new MoneyAmount()));
		assertEquals(0, newShare.getNbPurchases());
		newShare.terminate();
	}

	@Test(expected = IllegalArgumentException.class) public void simpleConstructor$IllegalCode()
			throws IllegalArgumentException {
		new Share("%()A");
	}
	
	@Test public void terminate$ShareWithoutPurchases() {
		shareWithoutPurchases.terminate();
		assertTrue(shareWithoutPurchases.isTerminated());
	}
	
	@Test public void terminate$ShareWithNonGrantedPurchase() {
		Purchase nonGrantedPurchase = new Purchase(new Wallet(), 4,
			theShare, new MoneyAmount());
		theShare.terminate();
		assertTrue(theShare.isTerminated());
		assertTrue(nonGrantedPurchase.isTerminated());
	}
	
	@Test public void terminate$ShareWithGrantedPurchase() {
		Purchase grantedPurchase = new Purchase(new Wallet(), 4,
			theShare, new MoneyAmount());
		grantedPurchase.grant();
		theShare.terminate();
		assertTrue(theShare.isTerminated());
		assertTrue(grantedPurchase.isTerminated());
		assertEquals(0,grantedPurchase.getNbItems());
	}
	
	@Test public void terminate$ShareWithSeveralPurchases() {
		shareWith5Purchases.terminate();
		assertTrue(shareWith5Purchases.isTerminated());
		assertTrue(purchaseOfShareWith5Purchases.isTerminated());
	}
	
	@Test public void terminate$ShareAlreadyTerminated() {
		theShare.terminate();
		theShare.terminate();
		assertTrue(theShare.isTerminated());
	}

	@Test public void canHaveAsCode$NonEffectiveCode() {
		assertFalse(theShare.canHaveAsCode(null));
	}

	@Test public void canHaveAsCode$NonLetterDigitCode() {
		assertFalse(theShare.canHaveAsCode("123%"));
	}

	@Test public void canHaveAsCode$NonFourSymbolCode() {
		assertFalse(theShare.canHaveAsCode("AB"));
	}
	
	// This case cannot be tested, because once shares have been
	// initialized cannot have any other code.
//	@Test public void canHaveAsCode_UnusedCode() {
//		assertTrue(theShare.canHaveAsCode("XDBU"));
//	}
	
	@Test public void canHaveAsCode_OwnCode() {
		assertTrue(theShare.canHaveAsCode(theShare.getCode()));
	}
	
	@Test public void canHaveAsCode_CodeOfOtherShare() {
		assertFalse(theShare.canHaveAsCode(shareWithCodeABCD.getCode()));
	}
	
	// This case cannot be tested, because once shares have been
	// initialized cannot have any other code.
//	@Test public void canHaveAsCode_CodeOfTerminatedShare() {
//		shareWithCodeABCD.terminate();
//		assertTrue(theShare.canHaveAsCode(shareWithCodeABCD.getCode()));
//	}
    
	@Test public void isValidValue$LegalValue() {
		assertTrue(Share.isValidValue(new MoneyAmount()));
	}
    
	@Test public void isValidValue$NonEffectiveValue() {
		assertFalse(Share.isValidValue(null));
	}
    
	@Test public void isValidValue$NegativeValue() {
		MoneyAmount negativeValue = new MoneyAmount(BigInteger.valueOf(-1),Currency.EUR);
		assertFalse(Share.isValidValue(negativeValue));
	}

	@Test public void setValue$LegalCase() {
		MoneyAmount legalValue = new MoneyAmount(BigInteger.valueOf(123), Currency.EUR);
			theShare.setValue(legalValue);
			assertTrue(theShare.getValue().isIdenticalTo(legalValue));
	}

	@Test (expected = IllegalArgumentException.class) public void setValue$IllegalValue() throws IllegalArgumentException {
			theShare.setValue(null);
	}

	@Test (expected = IllegalStateException.class) public void setValue$TerminatedShare() throws IllegalStateException {
			theShare.terminate();
			theShare.setValue(new MoneyAmount());
	}
	
	@Test
	public void canHaveAsPurchaseAt_OwnPurchaseAtIndex() {
		assertTrue(shareWith5Purchases.canHaveAsPurchaseAt(purchaseOfShareWith5Purchases, 3));
	}

	@Test
	public void canHaveAsPurchaseAt_OtherPurchaseInBetween() {
		assertTrue(shareWith5Purchases.canHaveAsPurchaseAt(purchaseOfOtherShare, 3));
	}

	@Test
	public void canHaveAsPurchaseAt_OtherPurchaseAfterLast() {
		assertTrue(shareWith5Purchases.canHaveAsPurchaseAt(purchaseOfOtherShare, 6));
	}
	
	@Test
	public void canHaveAsPurchaseAt_NonEffectivePurchase() {
		assertFalse(shareWith5Purchases.canHaveAsPurchaseAt(null, 3));
	}
	
	@Test
	public void canHaveAsPurchaseAt_NonPositiveIndex() {
		assertFalse(shareWith5Purchases.canHaveAsPurchaseAt(purchaseOfOtherShare, 0));
	}
	
	@Test
	public void canHaveAsPurchaseAt_IndexTooHigh() {
		assertFalse(shareWith5Purchases.canHaveAsPurchaseAt(purchaseOfOtherShare, 7));
	}
	
	@Test
	public void canHaveAsPurchaseAt_IllegalPurchase() {
		assertFalse(shareWith5Purchases.canHaveAsPurchaseAt(terminatedPurchase, 3));
	}
	
	@Test
	public void canHaveAsPurchaseAt_OwnPurchaseAtOtherIndex() {
		assertFalse(shareWith5Purchases.canHaveAsPurchaseAt(purchaseOfShareWith5Purchases, 6));
	}

    @Test public void hasProperPurchases$TrueCase() {
        assertTrue(shareWith5Purchases.hasProperPurchases());
    }

    @Test public void hasAsPurchase$TrueCase() {
        assertTrue(shareWith5Purchases.hasAsPurchase(purchaseOfShareWith5Purchases));
    }

    @Test public void hasAsPurchase$FalseCase() {
        assertFalse(theShare.hasAsPurchase(purchaseOfShareWith5Purchases));
    }

    @Test public void hasAsPurchase$NonEffectivePurchase() {
    	// Not really needed in a black-box test
        assertFalse(theShare.hasAsPurchase(null));
    }

}
