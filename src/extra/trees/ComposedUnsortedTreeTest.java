package extra.trees;

import static org.junit.Assert.*;

import org.junit.*;

import extra.trees.exceptions.IllegalElementException;

public class ComposedUnsortedTreeTest {

    private ComposedUnsortedTree theTree;

    @Before
    public void setUp() throws Exception {
        theTree = new ComposedUnsortedTree(20, 50, null, 20, null, 77);
    }

    // Test for the constructor

    @Test
    public void constructor_LegalCase() throws Exception {
        ComposedUnsortedTree newTree = new ComposedUnsortedTree(10, 20, 30,
            null, 10);
        assertEquals(2, newTree.getNbOccurrencesOf(10));
        assertEquals(1, newTree.getNbOccurrencesOf(20));
        assertEquals(1, newTree.getNbOccurrencesOf(30));
        assertEquals(1, newTree.getNbOccurrencesOf(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_IllegalCase() throws Exception {
        new ComposedUnsortedTree();
    }

    // Test for the method canHaveAsElement

    @Test
    public void canHaveAsElement_SingleCase() {
        assertTrue(theTree.canHaveAsElement(null));
    }

    // Test for the method canHaveAsNbOccursOfElement
    // These tests are not really needed.

    @Test
    public void canHaveAsNbOccursOfElement_TrueCase() {
        assertTrue(theTree.canHaveAsNbOccursOfElement(2, 66));
    }

    @Test
    public void canHaveAsNbOccursOfElement_NegativeNumber() {
        assertFalse(theTree.canHaveAsNbOccursOfElement(-1, 66));
    }

    @Test
    public void canHaveAsNbOccursOfElement_NotEnoughElementsLeft() {
        ComposedUnsortedTree smallTree = new ComposedUnsortedTree(20, 20);
        assertTrue(smallTree.canHaveAsNbOccursOfElement(1, 20));
    }

    // Test for the method addElement

    @Test
    public void addElement_SingleCase() {
        ComposedUnsortedTree resultTree = theTree.addElement(null);
        assertSame(theTree, resultTree);
        assertEquals(7, resultTree.getNbElements());
        assertEquals(3, resultTree.getNbOccurrencesOf(null));
        assertEquals(2, resultTree.getNbOccurrencesOf(20));
        assertEquals(1, resultTree.getNbOccurrencesOf(50));
        assertEquals(1, resultTree.getNbOccurrencesOf(77));
    }

    // Test for the method removeElement

    @Test
    public void removeElement_TreeWithTwoElements() throws Exception {
        ComposedUnsortedTree theTree = new ComposedUnsortedTree(10, 20);
        NonEmptyBinaryTree resultTree = theTree.removeElement(20);
        assertTrue(resultTree instanceof LeafUnsortedTree);
        assertEquals(1, resultTree.getNbOccurrencesOf(10));
    }

    @Test
    public void testRemoveElement$TreeWithMoreThanTwoElements()
            throws Exception {
        NonEmptyBinaryTree resultTree = theTree.removeElement(20);
        assertSame(theTree, resultTree);
        assertEquals(5, resultTree.getNbElements());
        assertEquals(1, resultTree.getNbOccurrencesOf(20));
        assertEquals(2, resultTree.getNbOccurrencesOf(null));
        assertEquals(1, resultTree.getNbOccurrencesOf(50));
        assertEquals(1, resultTree.getNbOccurrencesOf(77));
    }

    @Test(expected = IllegalElementException.class)
    public void removeElement_IllegalCase() throws Exception {
        theTree.removeElement(111);
    }

    // Test for the method clone

    @Test
    public void clone_SingleCase() {
        ComposedUnsortedTree clone = theTree.clone();
        assertNotNull(clone);
        assertSame(theTree.getClass(),clone.getClass());
        assertEquals(theTree.getNbElements(), clone.getNbElements());
        assertEquals(theTree.getNbOccurrencesOf(10), clone
            .getNbOccurrencesOf(10));
        assertEquals(theTree.getNbOccurrencesOf(20), clone
            .getNbOccurrencesOf(20));
    }

    // Test for the method removeRootElement

    @Test public void removeRootElement_TreeWithMoreThanTwoElements() {
        Object rootElement = theTree.getRootElement();
        int oldNbOccurrences = theTree.getNbOccurrencesOf(rootElement);
        NonEmptyUnsortedTree resultTree = theTree.removeRootElement();
        assertSame(theTree, resultTree);
        assertEquals(5, theTree.getNbElements());
        assertEquals(oldNbOccurrences - 1, resultTree
            .getNbOccurrencesOf(rootElement));
    }

    @Test public void removeRootElement_TreeWithTwoElements() {
        ComposedUnsortedTree theTree = new ComposedUnsortedTree(20, 30);
        Object rootElement = theTree.getRootElement();
        NonEmptyUnsortedTree resultTree = theTree.removeRootElement();
        assertTrue(resultTree instanceof LeafUnsortedTree);
        assertEquals(0, resultTree.getNbOccurrencesOf(rootElement));
    }

    // Test for the method canHaveAsLeftTree

    @Test public void canHaveAsLeftSubTree_LegalTree() {
        assertTrue(theTree.canHaveAsLeftTree(new LeafUnsortedTree(100)));
    }

    @Test public void canHaveAsLeftTree_NonEffectiveTree() {
        assertFalse(theTree.canHaveAsLeftTree(null));
    }

    @Test public void canHaveAsLeftTree_NonUnsortedTree() {
        assertFalse(theTree.canHaveAsLeftTree(EmptySearchTree.getPrototype()));
    }

    @Test public void canHaveAsLeftTree_Sametree() {
        assertFalse(theTree.canHaveAsLeftTree(theTree));
    }

    @Test public void canHaveAsLeftTree_Subtree() {
        NonEmptyBinaryTreeImpl theSubtree;
        if (theTree.getLeftTree() instanceof NonEmptyBinaryTreeImpl)
            theSubtree = (NonEmptyBinaryTreeImpl) theTree.getLeftTree();
        else
            theSubtree = (NonEmptyBinaryTreeImpl) theTree.getRightTree();
        assertFalse(theSubtree.canHaveAsLeftTree(theTree));
    }

    // Test for the method canHaveAsRightTree

    @Test public void canHaveAsRightSubTree_LegalTree() {
        assertTrue(theTree.canHaveAsRightTree(new LeafUnsortedTree(100)));
    }

    @Test public void canHaveAsRightTree_NonEffectiveTree() {
        assertFalse(theTree.canHaveAsRightTree(null));
    }

    @Test public void canHaveAsRightTree_NonUnsortedTree() {
        assertFalse(theTree.canHaveAsRightTree(EmptySearchTree.getPrototype()));
    }

    @Test public void canHaveAsRightTree_Sametree() {
        assertFalse(theTree.canHaveAsRightTree(theTree));
    }

    @Test public void canHaveAsRightTree_Subtree() {
        NonEmptyBinaryTreeImpl theSubtree;
        if (theTree.getLeftTree() instanceof NonEmptyBinaryTreeImpl)
            theSubtree = (NonEmptyBinaryTreeImpl) theTree.getLeftTree();
        else
            theSubtree = (NonEmptyBinaryTreeImpl) theTree.getRightTree();
        assertFalse(theSubtree.canHaveAsRightTree(theTree));
    }

    // Test for the method canHaveAsSubTrees

    @Test public void canHaveAsSubTrees_LegalTrees() {
        assertTrue(theTree.canHaveAsSubTrees(new LeafUnsortedTree(88),
            new LeafUnsortedTree(66)));
    }

    @Test public void canHaveAsSubTrees_InvalidLeftTree() {
        assertFalse(theTree.canHaveAsSubTrees(null, new LeafUnsortedTree(66)));
    }

    @Test public void canHaveAsSubTrees_InvalidRightTree() {
        assertFalse(theTree.canHaveAsSubTrees(new LeafUnsortedTree(66), null));
    }

    @Test public void canHaveAsSubTrees_EmptyTrees() {
        assertFalse(theTree.canHaveAsSubTrees(EmptyUnsortedTree.getPrototype(),
            EmptyUnsortedTree.getPrototype()));
    }

    // Test for the method changeToLeafTree

    @Test public void changeToLeafTree_AtLeastTwoElements() {
        NonEmptyUnsortedTree resultTree = theTree.changeToLeafTree();
        assertSame(theTree, resultTree);
    }

    @Test public void changeToLeafTree_SingleElement() {
        theTree.setLeftTree(EmptyUnsortedTree.getPrototype());
        theTree.setRightTree(EmptyUnsortedTree.getPrototype());
        Object theElement = theTree.getRootElement();
        NonEmptyUnsortedTree resultTree = theTree.changeToLeafTree();
        assertTrue(resultTree instanceof LeafUnsortedTree);
        assertEquals(1, resultTree.getNbOccurrencesOf(theElement));
    }

}
