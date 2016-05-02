package extra.trees;

import static org.junit.Assert.*;

import org.junit.*;

import extra.trees.exceptions.IllegalElementException;

public class LeafUnsortedTreeTest {

    private LeafUnsortedTree theTree;

    @Before
    public void setUp() {
        theTree = new LeafUnsortedTree(20);
    }

    // Test for the constructor

    @Test
    public void constructor_SingleCase() {
        Object theObject = 55;
        LeafUnsortedTree newTree = new LeafUnsortedTree(theObject);
        assertEquals(1, newTree.getNbOccurrencesOf(theObject));
    }

    // Test for the method canHaveAsElement

    @Test
    public void canHaveAsElement_SingleCase() {
        assertTrue(theTree.canHaveAsElement(null));
    }

    // Test for the method canHaveAsNbOccursOfElement
    // These tests are not really needed.

    @Test
    public void canHaveAsNbOccursOfElemen_ZeroOccursOfOtherElement() {
        assertTrue(theTree.canHaveAsNbOccursOfElement(0, 35));
    }

    @Test
    public void canHaveAsNbOccursOfElemen_ZeroOccursOfElement() {
        assertTrue(theTree.canHaveAsNbOccursOfElement(0, 20));
    }

    @Test
    public void canHaveAsNbOccursOfElemen_OneOccursOfElement() {
        assertTrue(theTree.canHaveAsNbOccursOfElement(1, 20));
    }

    @Test
    public void canHaveAsNbOccursOfElemen_OneOccursOfOtherElement() {
        assertTrue(theTree.canHaveAsNbOccursOfElement(1, 35));
    }

    @Test
    public void canHaveAsNbOccursOfElemen_OccursAboveOne() {
        assertFalse(theTree.canHaveAsNbOccursOfElement(2, 20));
    }

    // Test for the method addElement

    @Test
    public void addElement_SingleCase() {
        Object rootElement = theTree.getRootElement();
        ComposedUnsortedTree newTree = theTree.addElement(60);
        assertNotNull(newTree);
        assertEquals(2, newTree.getNbElements());
        assertEquals(1, newTree.getNbOccurrencesOf(rootElement));
        assertEquals(1, newTree.getNbOccurrencesOf(60));
    }

    // Test for the method removeElement

    @Test
    public void removeElement_LegalCase() throws Exception {
        theTree.removeElement(20);
    }

    @Test(expected = IllegalElementException.class)
    public void removeElement_IllegalCase() throws Exception {
        theTree.removeElement(60);
    }
    
    // clone
    @Test public void clone_SingleCase() {
        LeafUnsortedTree clone = theTree.clone();
        assertNotNull(clone);
        assertSame(theTree.getClass(),clone.getClass());
        assertTrue(clone.hasAsElement(theTree.getRootElement()));
    }

    // Test for the method removeRootElement

    @Test public void removeRootElement$SingleCase() {
        EmptyUnsortedTree newTree = theTree.removeRootElement();
        assertNotNull(newTree);
    }

    // Test for the method getLeftTree

    @Test public void getLeftTree_SingleCase() {
        EmptyUnsortedTree leftTree = theTree.getLeftTree();
        assertNotNull(leftTree);
    }

    // Test for the method canHaveAsLeftTree

    @Test public void canHaveAsLeftTree_LegalTree() {
        assertTrue(theTree.canHaveAsLeftTree(EmptyUnsortedTree.getPrototype()));
    }

    @Test public void canHaveAsLeftTree_IllegalTree() {
        assertFalse(theTree.canHaveAsLeftTree(null));
    }

    // Test for the method getRightTree

    @Test public void getRightTree_SingleCase() {
        EmptyUnsortedTree rightTree = theTree.getRightTree();
        assertNotNull(rightTree);
    }

    // Test for the method canHaveAsRightTree

    @Test public void canHaveAsRightTree_LegalTree() {
        assertTrue(theTree.canHaveAsRightTree(EmptyUnsortedTree.getPrototype()));
    }

    @Test public void canHaveAsRightTree_IllegalTree() {
        assertFalse(theTree.canHaveAsRightTree(null));
    }
}
