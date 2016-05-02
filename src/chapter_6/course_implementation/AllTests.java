package chapter_6.course_implementation;

import chapter_6.course_implementation.banking.money.*;
import chapter_6.course_implementation.banking.shares.*;
import chapter_6.course_implementation.stockMarket.ShareTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ MoneyAmountTest.class, ShareTest.class,
	PurchaseTest.class, WalletTest.class })
public class AllTests {
}