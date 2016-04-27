package chapter_3.course_implementation;

import static org.junit.Assert.*;

import org.junit.Test;


public class ExtMathTest {

	@Test
	public void areAddable_TrueCase() {
		assertTrue(ExtMath.areAddable(7, 50));
		assertTrue(ExtMath.areAddable(Long.MAX_VALUE-4, 4));
		assertTrue(ExtMath.areAddable(Long.MIN_VALUE+4, -4));
	}

	@Test
	public void areAddable_AboveMaxValue() {
		assertFalse(ExtMath.areAddable(Long.MAX_VALUE-20, 21));
		assertFalse(ExtMath.areAddable(Long.MAX_VALUE, Long.MAX_VALUE));
	}

	@Test
	public void areAddable_BelowMinValue() {
		assertFalse(ExtMath.areAddable(Long.MIN_VALUE+4, -5));
		assertFalse(ExtMath.areAddable(Long.MIN_VALUE, Long.MIN_VALUE));
	}

	@Test
	public void areAddable_DifferentSigns() {
		assertTrue(ExtMath.areAddable(7, -5));
		assertTrue(ExtMath.areAddable(-5,7));
		assertTrue(ExtMath.areAddable(Long.MIN_VALUE, Long.MAX_VALUE));
		assertTrue(ExtMath.areAddable(Long.MAX_VALUE, Long.MIN_VALUE));
	}
	
	@Test
	public void add_LegalCase() throws Exception {
		assertEquals(25,ExtMath.add(20, 5));
	}
	
	@Test(expected=AddOverflowException.class)
	public void add_IllegalCase() throws Exception {
		ExtMath.add(Long.MAX_VALUE, 5);
	}

	@Test
	public void areMultipliable_PositiveNumbers() {
		assertTrue(ExtMath.areMultipliable(5, 30));
		assertTrue(ExtMath.areMultipliable(Long.MAX_VALUE/3, 3));
		assertFalse(ExtMath.areMultipliable(Long.MAX_VALUE/4, 5));
		assertFalse(ExtMath.areMultipliable(Long.MAX_VALUE/2+1, 2));
		assertTrue(ExtMath.areMultipliable(3,Long.MAX_VALUE/3));
		assertFalse(ExtMath.areMultipliable(5,Long.MAX_VALUE/4));
		assertFalse(ExtMath.areMultipliable(2,Long.MAX_VALUE/2+1));
	}

	@Test
	public void areMultipliable_NegativeNumbers() {
		assertTrue(ExtMath.areMultipliable(-5, -30));
		assertTrue(ExtMath.areMultipliable(-Long.MAX_VALUE/3, -3));
		assertFalse(ExtMath.areMultipliable(-Long.MAX_VALUE/4, -5));
		assertFalse(ExtMath.areMultipliable(-Long.MAX_VALUE/2-1, -2));
		assertTrue(ExtMath.areMultipliable(-3,-Long.MAX_VALUE/3));
		assertFalse(ExtMath.areMultipliable(-5,-Long.MAX_VALUE/4));
		assertFalse(ExtMath.areMultipliable(-2,-Long.MAX_VALUE/2-1));
		assertFalse(ExtMath.areMultipliable(Long.MIN_VALUE,-1));
		assertFalse(ExtMath.areMultipliable(-1,Long.MIN_VALUE));
	}
	
	@Test
	public void areMultipliable_DifferentSigns() {
		assertTrue(ExtMath.areMultipliable(-5, 30));
		assertTrue(ExtMath.areMultipliable(5, -30));
		assertFalse(ExtMath.areMultipliable(Long.MAX_VALUE/4, -5));
		assertFalse(ExtMath.areMultipliable(Long.MIN_VALUE/10, 11));
		assertTrue(ExtMath.areMultipliable(Long.MAX_VALUE, -1));
		assertTrue(ExtMath.areMultipliable(Long.MIN_VALUE, 1));
		assertTrue(ExtMath.areMultipliable(Long.MAX_VALUE/2, -2));
		assertTrue(ExtMath.areMultipliable(Long.MAX_VALUE/2+1, -2));
		assertFalse(ExtMath.areMultipliable(Long.MAX_VALUE/2+2, -2));
		assertTrue(ExtMath.areMultipliable(Long.MIN_VALUE/2, 2));
		assertFalse(ExtMath.areMultipliable(Long.MIN_VALUE/2-1, 2));
	}
	
	@Test
	public void areMultipliable_Zeros() {
		assertTrue(ExtMath.areMultipliable(0, Long.MAX_VALUE));
		assertTrue(ExtMath.areMultipliable(Long.MIN_VALUE,0));
	}
	@Test
	public void times_LegalCase() throws Exception {
		assertEquals(100,ExtMath.times(20, 5));
	}
	
	@Test(expected=TimesOverflowException.class)
	public void times_IllegalCase() throws Exception {
		ExtMath.times(Long.MAX_VALUE, 5);
	}

	@Test
	public void gcd_LegalCase() throws Exception {
		assertEquals(5, ExtMath.gcd(30, 35));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void gcd_NegativeFirstNumber() throws Exception {
		ExtMath.gcd(-4,20);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void gcd_NegativeSecondNumber() throws Exception {
		ExtMath.gcd(4,-20);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void gcd_ZeroNumbers() throws Exception {
		ExtMath.gcd(0,0);
	}

}
