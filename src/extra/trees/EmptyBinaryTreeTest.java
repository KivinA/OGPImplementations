package extra.trees;

import static org.junit.Assert.*;

import java.util.Iterator;
import extra.trees.exceptions.IllegalElementException;

import org.junit.*;

@SuppressWarnings("rawtypes")
public class EmptyBinaryTreeTest {

    private static EmptyBinaryTree theTree;

    @BeforeClass
    public static void setUpBeforeClass() {
        theTree = EmptyUnsortedTree.getPrototype();
    }

    // Test for the method getNbOccurrencesOf

    @Test
    public void getNbOccurrencesOf_SingleCase() {
        assertEquals(0, theTree.getNbOccurrencesOf(5));
    }

    // Test for the method canHaveAsNbOccursOfElement.

    @Test
    public void canHaveAsNbOccursOfElement_NonZeroNumber() {
        assertFalse(theTree.canHaveAsNbOccursOfElement(1, 5));
    }

    @Test
    public void canHaveAsNbOccursOfElement_ZeroNumber() {
        assertTrue(theTree.canHaveAsNbOccursOfElement(0, 5));
    }

    // Test for the method hasAsElement

    @Test
    public void hasAsElement_SingleCase() {
        assertFalse(theTree.hasAsElement(6));
    }

    // Test for the method getNbElements

    @Test
    public void getNbElements_SingleCase() {
        assertEquals(0, theTree.getNbElements());
    }

    // Test for the method isEmpty

    @Test
    public void issEmpty_SingleCase() {
        assertTrue(theTree.isEmpty());
    }

    // Test for the method addElement

    @Test
    public void addElement_LegalCase() throws Exception {
        Object theObject = 10;
        BinaryTree resultTree = theTree.addElement(theObject);
        assertNotNull(resultTree);
        assertEquals(1, resultTree.getNbOccurrencesOf(theObject));
        assertEquals(1, resultTree.getNbElements());
    }

    // Test for the method removeElement

    @Test(expected = IllegalElementException.class)
    public void removeElementSingleCase() throws Exception {
        theTree.removeElement(5);
    }

    // Test for the method clone

    @Test
    public void clone_SingleCase() {
        EmptyBinaryTree clone = theTree.clone();
        assertSame(theTree, clone);
    }

    // Test for the method iterator

    @Test
    public void iterator_SingleCase() {
        Iterator theIterator = theTree.iterator();
        assertNotNull(theIterator);
        assertFalse(theIterator.hasNext());
    }

    // Test for the method hasAsSubTree

    @Test
    public void hasAsSubTree_SingleCase() {
        assertFalse(theTree.hasAsSubTree(theTree));
    }

}
