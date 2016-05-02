package extra.trees;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.*;

import extra.trees.exceptions.IllegalElementException;

@SuppressWarnings("rawtypes")
public class ComposedBinaryTreeTest {

    private ComposedBinaryTree theTree;

    @Before
    public void setUp() throws Exception {
        theTree = new ComposedSearchTree(10, 20);
    }

    // Test for the method canHaveAsNbOccursOfElement

    @Test
    public void canHaveAsNbOccursOfElement_NegativeNumber() {
        assertFalse(theTree.canHaveAsNbOccursOfElement(-1, 30));
    }

    @Test
    public void canHaveAsNbOccursOfElement_ZeroOccurrencesInsufficentElementsLeft() {
        assertTrue(theTree.canHaveAsNbOccursOfElement(0, 10));
    }

    @Test
    public void canHaveAsNbOccursOfElement_ZeroOccurrencesSufficientElementsLeft() {
        assertTrue(theTree.canHaveAsNbOccursOfElement(0, 30));
    }

    @Test
    public void canHaveAsNbOccursOfElement_PositiveOccurrencesInsufficentElementsLeft()
            throws Exception {
        theTree = new ComposedSearchTree(10, 10);
        assertTrue(theTree.canHaveAsNbOccursOfElement(1, 10));
    }

    @Test
    public void canHaveAsNbOccursOfElement_PositiveOccurrencesSufficentElementsLeft() {
        assertTrue(theTree.canHaveAsNbOccursOfElement(4, 30));
    }

    @Test
    public void canHaveAsNbOccursOfElement_PositiveOccurrencesIllegalElement() {
        assertFalse(theTree.canHaveAsNbOccursOfElement(4, "abc"));
    }

    // Test for the method addElement

    @Test
    public void addElement_LegalCase() throws Exception {
        ComposedBinaryTree resultTree = theTree.addElement(20);
        assertSame(theTree, resultTree);
        assertEquals(3, resultTree.getNbElements());
        assertEquals(2, resultTree.getNbOccurrencesOf(20));
    }

    @Test(expected = IllegalElementException.class)
    public void testIllegalCase() throws Exception {
        theTree.addElement("abc");
    }

    // Test for the method removeElement

    @Test
    public void removeElement_TreeWithTwoElements() throws Exception {
        NonEmptyBinaryTree resultTree = theTree.removeElement(20);
        assertTrue(resultTree instanceof LeafBinaryTree);
        assertEquals(1, resultTree.getNbOccurrencesOf(10));
    }

    @Test
    public void testRemoveElement_TreeWithMoreThanTwoElements()
            throws Exception {
        theTree.addElement(20);
        NonEmptyBinaryTree resultTree = theTree.removeElement(10);
        assertEquals(2, resultTree.getNbElements());
        assertEquals(0, resultTree.getNbOccurrencesOf(10));
        assertEquals(2, resultTree.getNbOccurrencesOf(20));
    }

    @Test(expected = IllegalElementException.class)
    public void removeElement_IllegalCase() throws Exception {
        theTree.removeElement(null);
    }

    // Test for the method clone

    @Test
    public void clone_SingleCase() {
        ComposedBinaryTree clone = theTree.clone();
        assertEquals(theTree.getNbElements(), clone.getNbElements());
        assertEquals(theTree.getNbOccurrencesOf(10), clone
            .getNbOccurrencesOf(10));
        assertEquals(theTree.getNbOccurrencesOf(20), clone
            .getNbOccurrencesOf(20));
    }

    // Test for the method iterator

    @Test
    public void iterator_SingleCase() {
        Iterator theIterator = theTree.iterator();
        assertNotNull(theIterator);
        assertTrue(theIterator.hasNext());
        assertEquals(10, theIterator.next());
        assertEquals(20, theIterator.next());
        assertFalse(theIterator.hasNext());
    }

    // Test for the method removeRootElement

    @Test
    public void removeRootElement_TreeWithMoreThanTwoElements()
            throws Exception {
        theTree.addElement(30);
        Object rootElement = theTree.getRootElement();
        NonEmptyBinaryTree resultTree = theTree.removeRootElement();
        assertEquals(2, resultTree.getNbElements());
        assertEquals(0, resultTree.getNbOccurrencesOf(rootElement));
    }

    @Test
    public void removeRootElement_TreeWithTwoElements() {
        Object rootElement = theTree.getRootElement();
        NonEmptyBinaryTree resultTree = theTree.removeRootElement();
        assertTrue(resultTree instanceof LeafBinaryTree);
        assertEquals(0, resultTree.getNbOccurrencesOf(rootElement));
    }

    // Test for the method setLeftTree

    @Test
    public void setLeftTree_SingleCase() throws Exception {
        BinaryTree subTree = new LeafSearchTree(5);
        theTree.setLeftTree(subTree);
        assertSame(subTree, theTree.getLeftTree());
    }

    // Test for the method setRightTree

    @Test
    public void setRightTree_SingleCase() throws Exception {
        BinaryTree subTree = new LeafSearchTree(40);
        theTree.setRightTree(subTree);
        assertSame(subTree, theTree.getRightTree());
    }

    // Test for the method canHaveAsSubTrees

    @Test
    public void canHaveAsSubTrees_InvalidLeftTree() {
        assertFalse(theTree.canHaveAsSubTrees(null, new LeafUnsortedTree(66)));
    }

    @Test
    public void canHaveAsSubTrees_InvalidRightTree() {
        assertFalse(theTree.canHaveAsSubTrees(new LeafUnsortedTree(66), null));
    }

    @Test
    public void canHaveAsSubTrees_EmptyTrees() {
        assertFalse(theTree.canHaveAsSubTrees(EmptyUnsortedTree.getPrototype(),
            EmptyUnsortedTree.getPrototype()));
    }

    // Test for the method changeToLeafTree

    @Test
    public void changeToLeafTree_SingleElement() {
        theTree.setLeftTree(EmptySearchTree.getPrototype());
        theTree.setRightTree(EmptySearchTree.getPrototype());
        NonEmptyBinaryTreeImpl resultTree = (NonEmptyBinaryTreeImpl) theTree
            .changeToLeafTree();
        assertTrue(resultTree instanceof LeafBinaryTree);
        assertEquals(theTree.getRootElement(), resultTree.getRootElement());
    }

    @Test
    public void changeToLeafTree_SeveralElements() {
        NonEmptyBinaryTree resultTree = theTree.changeToLeafTree();
        assertSame(theTree, resultTree);
    }

}
