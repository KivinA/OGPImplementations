package extra.trees;

import static org.junit.Assert.*;

import java.util.Iterator;
import org.junit.*;

@SuppressWarnings("rawtypes")
public class LeafBinaryTreeTest {

    private static LeafBinaryTree theTree, treeStoringNull, sortedTree;

    private static Object theObject;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        theObject = new java.util.ArrayList<Object>();
        theTree = new LeafUnsortedTree(theObject);
        treeStoringNull = new LeafUnsortedTree(null);
        sortedTree = new LeafSearchTree(20);
    }

    // Test for the method getNbOccurrencesOf

    @Test
    public void getNbOccurrencesOf_TheElement() {
        assertEquals(1, theTree.getNbOccurrencesOf(theObject));
    }

    @Test
    public void getNbOccurrencesOf_OtherElement() {
        assertEquals(0, theTree.getNbOccurrencesOf(null));
    }

    @Test
    public void getNbOccurrencesOf_NullInNullTree() {
        assertEquals(1, treeStoringNull.getNbOccurrencesOf(null));
    }

    @Test
    public void getNbOccurrencesOf_NonNullInNullTree() {
        assertEquals(0, treeStoringNull.getNbOccurrencesOf(28));
    }

    // Test for the method canHaveAsNbOccursOfElement

    @Test
    public void canHaveAsNbOccursOfElement_NegativeNumber() {
        assertFalse(theTree.canHaveAsNbOccursOfElement(-3, null));
    }

    @Test
    public void canHaveAsNbOccursOfElement_ZeroOccurrencesOfElementinTree() {
        assertTrue(theTree.canHaveAsNbOccursOfElement(0, theTree
            .getRootElement()));
    }

    @Test
    public void canHaveAsNbOccursOfElement_ZeroOccurrencesOfOtherElement() {
        assertTrue(theTree.canHaveAsNbOccursOfElement(0, "abc"));
    }

    @Test
    public void canHaveAsNbOccursOfElement_OneOccurrenceOfNonAcceptableElement() {
        assertFalse(sortedTree.canHaveAsNbOccursOfElement(1, "xyz"));
    }

    @Test
    public void canHaveAsNbOccursOfElement_OneOccurrenceOfAcceptableElement() {
        assertTrue(sortedTree.canHaveAsNbOccursOfElement(1, 20));
    }

    @Test
    public void canHaveAsNbOccursOfElement_NumberExceeding1() {
        assertFalse(theTree.canHaveAsNbOccursOfElement(3, null));
    }

    // Test for the method hasAsElement

    @Test
    public void hasAsElement_TheElement() {
        assertTrue(theTree.hasAsElement(theObject));
    }

    @Test
    public void hasAsElement_OtherElement() {
        assertFalse(theTree.hasAsElement(30));
    }

    @Test
    public void hasAsElement_NullInNonNullTree() {
        assertFalse(theTree.hasAsElement(null));
    }

    @Test
    public void hasAsElement_NullInNullTree() {
        assertTrue(treeStoringNull.hasAsElement(null));
    }

    @Test
    public void hasAsElement_NonNullInNullTree() {
        assertFalse(treeStoringNull.hasAsElement(28));
    }

    // Test for the method getNbElements

    @Test
    public void getNbElements_SingleCase() {
        assertEquals(1, theTree.getNbElements());
    }

    // Test for the method clone

    @Test
    public void clone_Singlecase() {
        LeafBinaryTree clone = theTree.clone();
        assertNotNull(clone);
        assertEquals(theTree.getClass(), clone.getClass());
        assertSame(theTree.getRootElement(), clone.getRootElement());
        assertNotSame(this, clone);
    }

    // Test for the method iterator

    @Test
    public void iterator_SingleCase() {
        Iterator theIterator = theTree.iterator();
        assertNotNull(theIterator);
        assertTrue(theIterator.hasNext());
        Object nextObject = theIterator.next();
        assertSame(theTree.getRootElement(), nextObject);
        assertFalse(theIterator.hasNext());
    }

    // Test for the method canHaveAsLeftTree

    @Test
    public void canHaveAsLeftTree_NonEmptyTree() {
        assertFalse(theTree.canHaveAsLeftTree(null));
    }

    // Test for the method canHaveAsRightTree

    @Test
    public void canHaveAsRightTree_NonEmptyTree() {
        assertFalse(theTree.canHaveAsRightTree(null));
    }

    // Test for the method hasAsSubTree

    @Test
    public void hasAsSubTree_LeftTree() {
        assertTrue(theTree.hasAsSubTree(theTree.getLeftTree()));
    }

    @Test
    public void hasAsSubTree_RightTree() {
        assertTrue(theTree.hasAsSubTree(theTree.getRightTree()));
    }

    @Test
    public void hasAsSubTree_NonSubTree() {
        assertFalse(theTree.hasAsSubTree(theTree));
    }

    // Test for the method canHaveAsSubTrees.

    @Test
    public void canHaveAsSubTrees_LegalTrees() {
        assertTrue(theTree.canHaveAsSubTrees(EmptyUnsortedTree.getPrototype(),
            EmptyUnsortedTree.getPrototype()));
    }

    @Test
    public void canHaveAsSubTrees_IllegalLeftTree() {
        assertFalse(theTree.canHaveAsSubTrees(null, EmptyUnsortedTree
            .getPrototype()));
    }

    @Test
    public void testCanHaveAsSubTrees_IllegalRightTree() {
        assertFalse(theTree.canHaveAsSubTrees(EmptyUnsortedTree.getPrototype(),
            null));
    }

}
