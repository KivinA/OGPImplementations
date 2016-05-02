package extra.trees;

import static org.junit.Assert.*;

import org.junit.*;

import extra.trees.exceptions.IllegalElementException;

@SuppressWarnings("rawtypes")
public class EmptySearchTreeTest {

    private static EmptySearchTree theTree;

    @BeforeClass
    public static void setUp() throws Exception {
        theTree = EmptySearchTree.getPrototype();
    }

    // Test for the method getPrototype.

    @Test
    public void getPrototype_SingleCase() {
        assertNotNull(EmptySearchTree.getPrototype());
    }

    // Test for the method canHaveAsElement

    @Test
    public void canHaveAsElement_Comparable() {
        assertTrue(theTree.canHaveAsElement(20));
    }

    @Test
    public void canHaveAsElement_NonComparable() {
        assertFalse(theTree.canHaveAsElement(new java.util.ArrayList()));
    }

    @Test
    public void canHaveAsElement_Null() {
        assertFalse(theTree.canHaveAsElement(null));
    }

    // Test for the method canHaveAsNbOccursOfElement
    // These tests are not really needed.

    @Test
    public void canHaveAsNbOccursOfElement_TrueCase() {
        assertTrue(theTree.canHaveAsNbOccursOfElement(0, 30));
    }

    @Test
    public void canHaveAsNbOccursOfElement_NonZeroNumber() {
        assertFalse(theTree.canHaveAsNbOccursOfElement(1, 30));
    }

    @Test
    public void canHaveAsNbOccursOfElement_NonComparable() {
        assertTrue(theTree.canHaveAsNbOccursOfElement(0,
            new java.util.Date()));
    }

    // Test for the method addElement

    @Test
    public void addElement_LegalCase() throws Exception {
        LeafSearchTree resultTree = theTree.addElement(20);
        assertEquals(1, resultTree.getNbElements());
        assertEquals(1, resultTree.getNbOccurrencesOf(20));
    }

    @Test(expected = IllegalElementException.class)
    public void addElement_IllegalCase() throws Exception {
        theTree.addElement(new java.util.ArrayList());
    }

    // Test for the method removeElement

    @Test(expected = IllegalElementException.class)
    public void removeElement_SingleCase() throws Exception {
        theTree.removeElement(5);
    }

}
