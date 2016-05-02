package extra.trees;

import static org.junit.Assert.*;

import org.junit.*;

import extra.trees.exceptions.IllegalElementException;

public class EmptyUnsortedTreeTest {

    private static EmptyUnsortedTree theTree;

    @BeforeClass
    public static void setUp() {
        theTree = EmptyUnsortedTree.getPrototype();
    }

    // Test for the method getPrototype.

    @Test
    public void getPrototype_SingleCase() {
        assertNotNull(EmptyUnsortedTree.getPrototype());
    }

    // Test for the method canHaveAsElement

    @Test
    public void canHaveAsElement_SingleCase() {
        assertTrue(theTree.canHaveAsElement(null));
    }

    // Test for the method canHaveAsNbOccursOfElement
    // These tests are not really needed.

    @Test
    public void canHaveAsNbOccursOfElement_ZeroNumber() {
        assertTrue(theTree.canHaveAsNbOccursOfElement(0, 5));
    }

    @Test
    public void cnHaveAsNbOccursOfElement_NonZeroNumber() {
        assertFalse(theTree.canHaveAsNbOccursOfElement(1, 5));
    }

    // Test for the method addElement

    @Test
    public void addElement_SingleCase() {
        Object theObject = 26;
        LeafUnsortedTree resultTree = theTree.addElement(theObject);
        assertNotNull(resultTree);
        assertEquals(1, resultTree.getNbOccurrencesOf(theObject));
    }

    // Test for the method removeElement

    @Test(expected = IllegalElementException.class)
    public void removeElement_SingleCase() throws Exception {
        theTree.removeElement(44);
    }

}
