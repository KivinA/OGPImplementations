package chapter_8.course_implementation.expressions;

import static org.junit.Assert.*;

import org.junit.*;

public class ExpressionTest {

	private static Expression someExpression, expressionWithValue10,
			expressionWithValue20, mutableExpression, immutableExpression;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		someExpression = new Addition(new IntegerLiteral(4),
				new IntegerLiteral(5));
		expressionWithValue10 = new IntegerLiteral(10);
		expressionWithValue20 = new IntegerLiteral(20);
		mutableExpression = new MemoryCell(200);
		immutableExpression = new IntegerLiteral(1);
	}

	@Test
	public void hasAsSubExpression_SameExpression() {
		assertTrue(someExpression.hasAsSubExpression(someExpression));
	}

	@Test
	public void hasAsSubExpression_NonEffectiveExpression() {
		assertFalse(someExpression.hasAsSubExpression(null));
	}

	@Test
	public void equals_NonEffectiveExpression() {
		assertFalse(someExpression.equals(null));
	}

	@Test
	public void equals_SameExpression() {
		assertTrue(someExpression.equals(someExpression));
	}

	@Test
	public void equals_DifferentKindsOfObjects() {
		assertFalse(someExpression.equals(new java.util.Date()));
	}

	@Test
	public void equals_ExpressionsWithDifferentValues() {
		assertFalse(expressionWithValue10.equals(expressionWithValue20));
	}
	
	@Test
	public void equals_MutableEqualExpressions() {
		assertTrue(mutableExpression.equals(mutableExpression));
	}
	
	@Test
	public void equals_MutableNonEqualExpressions() {
		assertFalse(mutableExpression.equals(mutableExpression.clone()));
	}

	@Test
	public void isIdenticalTo_NonEffectiveExpression() {
		assertFalse(someExpression.isIdenticalTo(null));
	}

	@Test
	public void isIdenticalTo_DifferentKindsOfExpressions() {
		assertFalse(someExpression.equals(expressionWithValue10));
	}
	
	@Test
	public void isIdenticalTo_EqualExpressions() {
		assertTrue(expressionWithValue10.isIdenticalTo(new IntegerLiteral(10)));
	}

	@Test
	public void clone_MutableExpression() {
		Expression clone = mutableExpression.clone();
		assertNotNull(clone);
		assertTrue(clone.isIdenticalTo(mutableExpression));
		assertNotSame(mutableExpression, clone);
	}

	@Test
	public void clone_ImmutableExpression() {
		Expression clone = immutableExpression.clone();
		assertNotNull(clone);
		assertEquals(immutableExpression, clone);
		assertSame(immutableExpression, clone);
	}

	@Test
	public void hashCode_SingleCase() {
		Expression cloneSomeExpression = someExpression.clone();
		assertEquals(someExpression.hashCode(), cloneSomeExpression.hashCode());
	}

	@Test
	public void toString_SingleCase() {
		String result = someExpression.toString();
		assertNotNull(result);
		assertTrue(result.length() > 0);
	}

	@Test
	public void toPostfix_SingleCase() {
		String result = someExpression.toPostfix();
		assertNotNull(result);
		assertTrue(result.length() > 0);
	}

}
