package chapter_2.course_implementation;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OilTankTests {

	private static OilTank emptyTank;

	/**
	 * Set up n immutable test fixture.
	 * 
	 * @post   The variable emptyTank references a new
	 *         oil tank that is empty.
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		emptyTank = new OilTank(2000, 0);
	}

	private OilTank tankCap1000Cont200, tankCont500, fullTank;

	/**
	 * Set up a mutable test fixture.
	 * 
	 * @post   The variable tankCap1000Cont200 references a new
	 *         oil tank with capacity 1000 and contents 200.
	 * @post   The variable tankCont500 references a new
	 *         oil tank with contents 500 and a sufficiently
	 *         high capacity.
	 * @post   The variable fullTank references a new
	 *         oil tank that is completely filled.
	 */
	@Before
	public void setUp() throws Exception {
		tankCap1000Cont200 = new OilTank(1000, 200);
		tankCont500 = new OilTank(3000, 500);
		fullTank = new OilTank(2000, 2000);
	}

	@Test
	public final void extendedConstructor_SingleCase() {
		OilTank newTank = new OilTank(1000, 300);
		assertEquals(1000, newTank.getCapacity());
		assertEquals(300, newTank.getContents());
	}

	@Test
	public final void isValidCapacity_TrueCase() {
		assertTrue(OilTank.isValidCapacity(1));
	}

	@Test
	public final void isValidCapacity_FalseCase() {
		assertFalse(OilTank.isValidCapacity(0));
	}

	@Test
	public final void isValidContents_TrueCase() {
		assertTrue(OilTank.isValidContents(400, 800));
	}

	@Test
	public final void isValidContents_NegativeContents() {
		assertFalse(OilTank.isValidContents(-1, 1000));
	}

	@Test
	public final void issValidContents_ContentsExceedingCapacity() {
		assertFalse(OilTank.isValidContents(5, -10));
	}

	@Test
	public final void getFree_SingleCase() {
		assertEquals(800, tankCap1000Cont200.getFree());
	}

	@Test
	public final void isFull_TrueCase() {
		assertTrue(fullTank.isFull());
	}

	@Test
	public final void isFull_FalseCase() {
		assertFalse(tankCap1000Cont200.isFull());
	}

	@Test
	public final void isEmpty_TrueCase() {
		assertTrue(emptyTank.isEmpty());
	}

	@Test
	public final void isEmpty_FalseCase() {
		assertFalse(tankCap1000Cont200.isEmpty());
	}

	@Test
	public final void fillSingleAmount_SingleCase() {
		tankCap1000Cont200.fill(500);
		assertEquals(700, tankCap1000Cont200.getContents());
	}

	@Test
	public final void fillCompletely_NonFullTank() {
		tankCap1000Cont200.fill();
		assertTrue(tankCap1000Cont200.isFull());
	}

	@Test
	public final void fillCompletely_FullTank() {
		//	This test is not really needed in a pure black-box test.
		fullTank.fill();
		assertTrue(fullTank.isFull());
	}

	@Test
	public final void fillSeveralAmounts_SingleCase() {
		// Several amounts
		tankCap1000Cont200.fill(150, 40, 60);
		assertEquals(450, tankCap1000Cont200.getContents());
	}

	@Test
	public final void extractSingleAmount_SingleCase() {
		tankCap1000Cont200.extract(150);
		assertEquals(50, tankCap1000Cont200.getContents());
	}

	@Test
	public final void extractCompletely_SingleCase() {
		tankCap1000Cont200.extract();
		assertTrue(tankCap1000Cont200.isEmpty());
	}

	@Test
	public final void extractSeveralAmounts_SingleCase() {
		tankCap1000Cont200.extract(50, 70, 40);
		assertEquals(40, tankCap1000Cont200.getContents());
	}

	@Test
	public final void getPercentageFilled_SingleCase() {
		assertEquals(20.0F, tankCap1000Cont200.getPercentageFilled(), 0.1E-10);
	}

	@Test
	public final void isRelativelyFullerThan_TrueCase() {
		OilTank otherTank = new OilTank(500, 70);
		assertTrue(tankCap1000Cont200.isRelativelyFullerThan(otherTank));
	}

	@Test
	public final void isRelativelyFullerThan_FalseCase() {
		OilTank otherTank = new OilTank(500, 127);
		assertFalse(tankCap1000Cont200.isRelativelyFullerThan(otherTank));
	}

	@Test
	public final void isRelativelyFullerThan_EqualCase() {
		//	This test is not really needed in a pure black-box test.
		OilTank otherTank = new OilTank(500, 100);
		assertFalse(tankCap1000Cont200.isRelativelyFullerThan(otherTank));
	}

	
/** HOMEWORK */

	@Test
	public final void middleConstructor_SingleCase() {
		OilTank newTank = new OilTank(1000);
		assertEquals(1000, newTank.getCapacity());
		assertEquals(0, newTank.getContents());
	}

	@Test
	public final void defaultConstructor_SingleCase() {
		OilTank newTank = new OilTank();
		assertEquals(5000, newTank.getCapacity());
		assertEquals(0, newTank.getContents());
	}

	@Test
	public final void transferFrom_SingleCase() {
		tankCap1000Cont200.transferFrom(tankCont500);
		assertEquals(700, tankCap1000Cont200.getContents());
		assertTrue(tankCont500.isEmpty());
	}

	@Test
	public final void toString_SingleCase() {
		assertEquals("Oil tank with capacity 1000 and contents 200",
				tankCap1000Cont200.toString());
	}

	@Test
	public final void isIdenticalTo_TrueCase() {
		OilTank otherTank = new OilTank(1000, 200);
		assertTrue(tankCap1000Cont200.isIdenticalTo(otherTank));
	}

	@Test
	public final void isIdenticalTo_DifferentCapacity() {
		OilTank otherTank = new OilTank(500, 200);
		assertFalse(tankCap1000Cont200.isIdenticalTo(otherTank));
	}

	@Test
	public final void isIdenticalTo_DifferentContents() {
		OilTank otherTank = new OilTank(1000, 250);
		assertFalse(tankCap1000Cont200.isIdenticalTo(otherTank));
	}

	@Test
	public final void getCopy_SingleCase() {
		OilTank copy = tankCap1000Cont200.getCopy();
		assertNotNull(copy);
		assertNotSame(tankCap1000Cont200, copy);
		assertTrue(tankCap1000Cont200.isIdenticalTo(copy));
	}

}
