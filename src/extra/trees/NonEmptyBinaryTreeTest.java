package extra.trees;

import static org.junit.Assert.*;

import org.junit.*;

public class NonEmptyBinaryTreeTest {

    @SuppressWarnings("unused")
	private NonEmptyBinaryTreeImpl theTree, treeWithTheObjectOnly, treeWith2Elements,
            treeWithoutNulls, treeOfNulls, treeOfComparables,
            searchTreeOfIntegers;

    private Object theObject;

    @Before
    public void setUp() throws Exception {
        theTree = new ComposedUnsortedTree(55, null, 55, 27, null, 38, 55, null);
        theObject = 55;
        treeWithTheObjectOnly = new ComposedUnsortedTree(theObject, theObject,
            theObject);
        treeWith2Elements = new ComposedUnsortedTree(100,200);
        treeWithoutNulls = new ComposedUnsortedTree(55, 27);
        treeOfNulls = new ComposedUnsortedTree(null, null);
        treeOfComparables = new LeafSearchTree(22);
        searchTreeOfIntegers = new ComposedSearchTree(10, 20, 30, 40);
    }

    // Test for the method getNbOccurrencesOf

    @Test
    public void getNbOccurrencesOf_SingleOccurrence() {
        assertEquals(1, theTree.getNbOccurrencesOf(27));
    }

    @Test
    public void getNbOccurrencesOf_MutlipleOccurrences() {
        assertEquals(3, theTree.getNbOccurrencesOf(55));
    }

    @Test
    public void getNbOccurrencesOf_NullOccurrences() {
        assertEquals(3, theTree.getNbOccurrencesOf(null));
    }

    // Test for the method canHaveAsNbOccursofElement

    @Test
    public void canHaveAsNbOccursOfElement_NegativeNumber() {
        assertFalse(theTree.canHaveAsNbOccursOfElement(-1, 55));
    }

    @Test
    public void canHaveAsNbOccursOfElement_IllegalElement() {
        assertFalse(searchTreeOfIntegers.canHaveAsNbOccursOfElement(3, "abc"));
    }

    // Test for the method hasAsElement

    @Test
    public void hasAsElement_TrueCase() {
        assertTrue(treeWithoutNulls.hasAsElement(55));
    }

    @Test
    public void hasAsElement_FalseCase() {
        assertFalse(treeWithoutNulls.hasAsElement(null));
    }

    @Test
    public void hasAsElement_TrueCaseNullReference() {
        assertTrue(treeOfNulls.hasAsElement(null));
    }

    @Test
    public void hasAsElement_FalseCaseNullRoot() {
        assertFalse(treeOfNulls.hasAsElement(27));
    }

    // Test for the method getNbElements

    @Test
    public void getNbElements_SingleCase() {
        assertEquals(8, theTree.getNbElements());
    }

    // Test for the method isEmpty

    @Test
    public void isEmpty_SingleCase() {
        assertFalse(theTree.isEmpty());
    }

    // Test for the method setRootElement

    @Test
    public void setRootElement_SingleCase() {
        theTree.setRootElement(44);
        assertEquals(44, theTree.getRootElement());
    }

    // Test for the method removeRootElement

    @Test
    public void removeRootElement_SameTreeReturned() {
        theTree.setRootElement(null);
        NonEmptyBinaryTreeImpl oldTree = (NonEmptyBinaryTreeImpl) theTree
            .clone();
        int oldRootOccurrences = oldTree.getNbOccurrencesOf(null);
        theTree.removeRootElement();
        assertNotNull(theTree);
        assertEquals(oldRootOccurrences - 1, theTree.getNbOccurrencesOf(null));
        assertTrue(oldTree.getLeftTree().hasAsElement(theTree.getRootElement())
            || oldTree.getRightTree().hasAsElement(theTree.getRootElement()));
        assertTrue((oldTree.getLeftTree().getNbOccurrencesOf(
            theTree.getRootElement()) == theTree.getLeftTree()
            .getNbOccurrencesOf(theTree.getRootElement()) + 1)
            ^ (oldTree.getRightTree().getNbOccurrencesOf(
                theTree.getRootElement()) == theTree.getRightTree()
                .getNbOccurrencesOf(theTree.getRootElement()) + 1));
        assertEquals(oldTree.getNbElements() - 1, theTree.getNbElements());
        assertEquals(oldTree.getNbOccurrencesOf(55), theTree
            .getNbOccurrencesOf(55));
        assertEquals(oldTree.getNbOccurrencesOf(28), theTree
            .getNbOccurrencesOf(28));
        assertEquals(oldTree.getNbOccurrencesOf(37), theTree
            .getNbOccurrencesOf(37));
    }

    @Test
    public void removeRootElement_OtherTreeReturned() {
        NonEmptyBinaryTreeImpl oldTree = (NonEmptyBinaryTreeImpl) treeWith2Elements.clone();
        Object oldRootElement = treeWith2Elements.getRootElement();
        NonEmptyBinaryTreeImpl resultTree = (NonEmptyBinaryTreeImpl) treeWith2Elements.removeRootElement();
        assertNotNull(resultTree);
        assertEquals(0, resultTree.getNbOccurrencesOf(oldRootElement));
        assertTrue(oldTree.getLeftTree().hasAsElement(resultTree.getRootElement())
            ^ oldTree.getRightTree().hasAsElement(resultTree.getRootElement()));
        assertEquals(1, resultTree.getNbElements());
    }

    // Test for the method canHaveAsLeftTree

    @Test public void canHaveAsLeftTree_NonEffectiveTree() {
        assertFalse(theTree.canHaveAsLeftTree(null));
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

    @Test public void canHaveAsRightTree_NonEffectiveTree() {
        assertFalse(theTree.canHaveAsRightTree(null));
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

    // Test for the method hasAsSubtree

    @Test public void hasAsSubtree_DirectLeftSubtree() {
        assertTrue(theTree.hasAsSubTree(theTree.getLeftTree()));
    }

    @Test public void hasAsSubtree_IndirectLeftSubtree() {
        if (theTree.getLeftTree() instanceof NonEmptyBinaryTreeImpl) {
            NonEmptyBinaryTreeImpl leftTree = (NonEmptyBinaryTreeImpl) theTree
                .getLeftTree();
            assertTrue(theTree.hasAsSubTree(leftTree.getLeftTree()));
        }
    }

    @Test public void hasAsSubtree_DirectRightSubtree() {
        assertTrue(theTree.hasAsSubTree(theTree.getRightTree()));
    }

    @Test public void hasAsSubtree_IndirectRightSubtree() {
        if (theTree.getRightTree() instanceof NonEmptyBinaryTreeImpl) {
            NonEmptyBinaryTreeImpl rightTree = (NonEmptyBinaryTreeImpl) theTree
                .getRightTree();
            assertTrue(theTree.hasAsSubTree(rightTree.getRightTree()));
        }
    }

    // Test for the method canHaveAsSubTrees

    @Test public void canHaveAsSubTrees_InvalidLeftTree() {
        assertFalse(theTree.canHaveAsSubTrees(null, new LeafUnsortedTree(66)));
    }

    @Test public void canHaveAsSubTrees_InvalidRightTree() {
        assertFalse(theTree.canHaveAsSubTrees(new LeafUnsortedTree(66), null));
    }

}
