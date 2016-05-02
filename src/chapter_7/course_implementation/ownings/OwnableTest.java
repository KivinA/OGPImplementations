package chapter_7.course_implementation.ownings;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import chapter_7.course_implementation.exceptions.IllegalOwnerException;
import chapter_7.course_implementation.exceptions.IllegalValueException;
import chapter_7.course_implementation.persons.Person;

public class OwnableTest {

	private Ownable someOwnable, ownableWithOwner, ownableWithoutOwner,
			terminatedOwnable;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		someOwnable = new Dog("Bobby");
		ownableWithOwner = new Painting(new Person(), BigInteger.valueOf(100),
				"Title", new Person());
		ownableWithoutOwner = new Painting("Waterfront");
		terminatedOwnable = new Dog("Bessy");
		terminatedOwnable.terminate();
	}

	@Test
	public void canHaveAsValue_NonTerminatedOwnableLegalValue() {
		assertTrue(someOwnable.canHaveAsValue(BigInteger.ZERO));
	}

	@Test
	public void canHaveAsValue_NonTerminatedOwnableIllegalValue() {
		assertFalse(someOwnable.canHaveAsValue(BigInteger.valueOf(-1)));
	}

	@Test
	public void canHaveAsValue_NonTerminatedOwnableNonEffectiveValue() {
		assertFalse(someOwnable.canHaveAsValue(null));
	}

	@Test
	public void canHaveAsValue_TerminatedOwnableNonEffectiveValue() {
		assertTrue(terminatedOwnable.canHaveAsValue(null));
	}

	@Test
	public void canHaveAsValue_TerminatedOwnableEffectiveValue() {
		// Not really needed in a black-box test.
		assertTrue(terminatedOwnable.canHaveAsValue(BigInteger.ZERO));
	}

	@Test
	public void setValue_LegalEffectiveValue() throws Exception {
		BigInteger theValue = BigInteger.valueOf(100);
		someOwnable.setValue(theValue);
		assertSame(theValue, someOwnable.getValue());
	}

	@Test(expected = IllegalValueException.class)
	public void setValue_NonEffectiveValue() throws Exception {
		someOwnable.setValue(null);
	}

	@Test(expected = IllegalValueException.class)
	public void setValue_IllegalValue() throws Exception {
		someOwnable.setValue(BigInteger.valueOf(-100));
	}

	@Test(expected = IllegalStateException.class)
	public void set_ValueTerminatedOwnable() throws Exception {
		terminatedOwnable.setValue(BigInteger.valueOf(-100));
	}

	@Test
	public void canHaveAsOwner_NonEffectiveOwner() {
		assertTrue(someOwnable.canHaveAsOwner(null));
	}

	@Test
	public void canHaveAsOwner_EffectiveNonTerminatedOwner() {
		Person otherPerson = new Person();
		assertTrue(ownableWithOwner.canHaveAsOwner(otherPerson));
	}

	@Test
	public void canHaveAsOwner_TerminatedOwner() {
		Person terminatedPerson = new Person();
		terminatedPerson.terminate();
		assertFalse(someOwnable.canHaveAsOwner(terminatedPerson));
	}

	@Test
	public void canHaveAsOwner_TerminatedOwnable() {
		assertFalse(terminatedOwnable.canHaveAsOwner(new Person()));
	}

	@Test
	public void hasProperOwner() {
		assertTrue(ownableWithOwner.hasProperOwner());
	}

	@Test
	public void hasOwner_TrueCase() {
		assertTrue(ownableWithOwner.hasOwner());
	}

	@Test
	public void hasOwner_FalseCase() {
		assertFalse(ownableWithoutOwner.hasOwner());
	}

	@Test
	public void setOwner_LegalOwnerForOwnableWithoutOwner() throws Exception {
		Person newOwner = new Person();
		ownableWithoutOwner.setOwnerTo(newOwner);
		assertSame(newOwner, ownableWithoutOwner.getOwner());
		assertSame(ownableWithoutOwner, newOwner.getOwningAt(1));
	}

	@Test(expected = IllegalStateException.class)
	public void setOwner_OwnableWithOwner() throws Exception {
		ownableWithOwner.setOwnerTo(new Person());
	}

	@Test(expected=IllegalOwnerException.class)
	public void setOwner_OwnableWithSameOwner() throws Exception {
		ownableWithOwner.setOwnerTo(ownableWithOwner.getOwner());
	}

	@Test(expected = IllegalOwnerException.class)
	public void setOwner_IllegalOwner() throws Exception {
		Person terminatedPerson = new Person();
		terminatedPerson.terminate();
		ownableWithoutOwner.setOwnerTo(terminatedPerson);
	}

	@Test(expected = IllegalOwnerException.class)
	public void setOwner_NonEffectiveOwner() throws Exception {
		ownableWithoutOwner.setOwnerTo(null);
	}

	@Test
	public void removeOwner_OwnableWithoutOwner() {
		ownableWithoutOwner.unsetOwner();
		assertFalse(ownableWithoutOwner.hasOwner());
	}

	@Test
	public void removeOwner_OwnableWithOwner() {
		Person formerOwner = ownableWithOwner.getOwner();
		ownableWithOwner.unsetOwner();
		assertFalse(ownableWithOwner.hasOwner());
		assertFalse(formerOwner.hasAsOwning(ownableWithOwner));
	}

	@Test
	public void terminate_OwnableNotOwned() {
		ownableWithoutOwner.terminate();
		assertTrue(ownableWithoutOwner.isTerminated());
	}

	@Test
	public void terminate_OwnableOwned() {
		Person formerOwner = ownableWithOwner.getOwner();
		ownableWithOwner.terminate();
		assertTrue(ownableWithOwner.isTerminated());
		assertFalse(formerOwner.hasAsOwning(ownableWithOwner));
	}

	@Test
	public void terminate_AlreadyTermiantedOwnable() {
		terminatedOwnable.terminate();
		assertTrue(terminatedOwnable.isTerminated());
	}

}
