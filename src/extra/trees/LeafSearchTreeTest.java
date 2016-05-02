package extra.trees;

import static org.junit.Assert.*;

import org.junit.*;

import extra.trees.exceptions.IllegalElementException;

@SuppressWarnings("rawtypes")
public class LeafSearchTreeTest {

    private static LeafSearchTree theTree;

    @BeforeClass
    public static void setUp() throws Exception {
        theTree = new LeafSearchTree(23);
    }

    // Test for the constructor

    @Test
    public void constructor_LegalCase() throws Exception {
        LeafSearchTree newTree = new LeafSearchTree(33);
        assertEquals(1, newTree.getNbOccurrencesOf(33));
    }

    @Test(expected = IllegalElementException.class)
    public void constructor_IllegalCase() throws Exception {
        new LeafSearchTree(null);
    }

    // Test for the method canHaveAsElement

    @Test
    public void canHaveAsElement_LegalElement() {
        assertTrue(theTree.canHaveAsElement(50));
    }

    @Test
    public void canHaveAsElement_IncompatibleElement() {
        assertFalse(theTree.canHaveAsElement("345"));
    }

    @Test
    public void canHaveAsElement_NonComparable() {
        assertFalse(theTree.canHaveAsElement(new java.util.ArrayList()));
    }

    @Test
    public void canHaveAsElement_Null() {
        assertFalse(theTree.canHaveAsElement(null));
    }

    // Test for the method hasAsElement

    @Test
    public void hasAsElement_TrueCase() {
        assertTrue(theTree.hasAsElement(23));
    }

    @Test
    public void hasAsElement_FalseCase() {
        assertFalse(theTree.hasAsElement(null));
    }

    // Test for the method addElement

    @Test
    public void addElement_LegalCase() throws Exception {
        ComposedSearchTree resultTree = theTree.addElement(45);
        assertEquals(2, resultTree.getNbElements());
        assertEquals(1, resultTree.getNbOccurrencesOf(23));
        assertEquals(1, resultTree.getNbOccurrencesOf(45));
    }

    @Test(expected = IllegalElementException.class)
    public void addElement_IllegalCase() throws Exception {
        theTree.addElement(null);
    }

    // Test for the method removeElement

    @Test public void removeElement_LegalCase() throws Exception {
            EmptySearchTree resultTree = theTree.removeElement(23);
            assertNotNull(resultTree);
    }

    @Test(expected=IllegalElementException.class) public void removeElement_IllegalCase() throws Exception {
            theTree.removeElement(null);
    }
    
    // clone
    @Test public void clone_SingleCase() {
        LeafSearchTree clone = theTree.clone();
        assertNotNull(clone);
        assertSame(theTree.getClass(),clone.getClass());
        assertTrue(clone.hasAsElement(theTree.getRootElement()));
    }

    // Test for the method getRootElement

    @SuppressWarnings("unchecked")
	@Test public void getRootElement_SingleCase() {
        Comparable<Object> result = theTree.getRootElement();
        assertSame(theTree.getRootElement(), result);
    }

    // Test for the method removeRootElement

    @Test public void removeRootElement_SingleCase() {
        EmptySearchTree resultTree = theTree.removeRootElement();
        assertNotNull(resultTree);
    }

    // Test for the method getLeftTree

    @Test public void getLeftTree_SingleCase() {
        EmptySearchTree leftTree = theTree.getLeftTree();
        assertNotNull(leftTree);
    }

    // Test for the method canHaveAsLeftTree

    @Test public void canHaveAsLeftTree_TrueCase() {
        assertTrue(theTree.canHaveAsLeftTree(EmptySearchTree.getPrototype()));
    }

    @Test public void canHaveAsLeftTree_FalseCase() {
        assertFalse(theTree.canHaveAsLeftTree(null));
    }

    // Test for the method getRightTree

    @Test public void getRightTree_SingleCase() {
        EmptySearchTree rightTree = theTree.getRightTree();
        assertNotNull(rightTree);
    }

    // Test for the method canHaveAsRightTree

    @Test public void canHaveAsRightTree_TrueCase() {
        assertTrue(theTree.canHaveAsRightTree(EmptySearchTree.getPrototype()));
    }

    @Test public void canHaveAsRightTree_FalseCase() {
        assertFalse(theTree.canHaveAsRightTree(null));
    }

}
