package extra.trees;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.*;

import extra.trees.exceptions.IllegalElementException;

@SuppressWarnings("rawtypes")
public class ComposedSearchTreeTest {

    private ComposedSearchTree theTree;

    @Before
    public void setUp() throws Exception {
        theTree = new ComposedSearchTree(20, 50, 40, 20, 70, 20, 100);
    }

    // Test for the constructor

    @Test
    public void constructor_LegalCase() throws Exception {
        ComposedSearchTree newTree = new ComposedSearchTree(20, 10, 30, 5, 10);
        assertEquals(5, newTree.getNbElements());
        assertEquals(1, newTree.getNbOccurrencesOf(20));
        assertEquals(2, newTree.getNbOccurrencesOf(10));
        assertEquals(1, newTree.getNbOccurrencesOf(30));
        assertEquals(1, newTree.getNbOccurrencesOf(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_LessThanTwoElements() throws Exception {
        new ComposedSearchTree(20);
    }

    @Test(expected = IllegalElementException.class)
    public void constructor_FirstElementNotComparable() throws Exception {
        new ComposedSearchTree(new java.util.ArrayList(), 20, 30, 40);
    }

    @Test(expected = IllegalElementException.class)
    public void constructor$IncompatibleElements() throws Exception {
        new ComposedSearchTree(20, "abc", 30);
    }

    // Test for the method getNbOccurrencesOf

    @Test
    public void getNbOccurrencesOf_ElementOccurs() {
        assertEquals(3, theTree.getNbOccurrencesOf(20));
    }

    @Test
    public void getNbOccurrencesOf_ElementDoesNotOccur() {
        assertEquals(0, theTree.getNbOccurrencesOf(-100));
    }

    @Test
    public void getNbOccurrencesOf_NonComparableElement() {
        assertEquals(0, theTree.getNbOccurrencesOf("abc"));
    }

    @Test
    public void getNbOccurrencesOf_NonEffectiveElement() {
        assertEquals(0, theTree.getNbOccurrencesOf(null));
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
    public void hasAsElement_ElementOccurs() {
        assertTrue(theTree.hasAsElement(100));
    }

    @Test
    public void hasAsElement_ElementDoesNotOccur() {
        assertFalse(theTree.hasAsElement(-100));
    }

    @Test
    public void hasAsElement_NonComparableElement() {
        assertFalse(theTree.hasAsElement("abc"));
    }

    @Test
    public void hasAsElement_NonEffectiveElement() {
        assertFalse(theTree.hasAsElement(null));
    }

    // Test for the method addElement

    @Test
    public void addElement_LegalCase() throws Exception {
        ComposedSearchTree resultTree = theTree.addElement(20);
        assertSame(theTree, resultTree);
        assertEquals(8, resultTree.getNbElements());
        assertEquals(4, resultTree.getNbOccurrencesOf(20));
    }

    @Test(expected = IllegalElementException.class)
    public void addElement_IllegalCase() throws Exception {
        theTree.addElement(null);
    }

    // Test for the method removeElement

    @Test
    public void removeElement_TreeWithTwoElements() throws Exception {
        ComposedSearchTree theTree = new ComposedSearchTree(10, 20);
        NonEmptySearchTree resultTree = theTree.removeElement(20);
        assertTrue(resultTree instanceof LeafSearchTree);
        assertEquals(1, resultTree.getNbOccurrencesOf(10));
    }

    @Test
    public void removeElement_TreeWithMoreThanTwoElements() throws Exception {
        NonEmptySearchTree resultTree = theTree.removeElement(20);
        assertEquals(6, resultTree.getNbElements());
        assertEquals(2, resultTree.getNbOccurrencesOf(20));
        assertEquals(1, resultTree.getNbOccurrencesOf(40));
        assertEquals(1, resultTree.getNbOccurrencesOf(50));
        assertEquals(1, resultTree.getNbOccurrencesOf(70));
        assertEquals(1, resultTree.getNbOccurrencesOf(100));
    }

    @Test(expected = IllegalElementException.class)
    public void removeElement_IllegalCase() throws Exception {
        theTree.removeElement(null);
    }

    // Test for the method clone

    @Test
    public void clone_SingleCase() {
        ComposedBinaryTree clone = theTree.clone();
        assertNotNull(clone);
        assertSame(theTree.getClass(),clone.getClass());
        assertEquals(theTree.getNbElements(), clone.getNbElements());
        assertEquals(theTree.getNbOccurrencesOf(10), clone
            .getNbOccurrencesOf(10));
        assertEquals(theTree.getNbOccurrencesOf(20), clone
            .getNbOccurrencesOf(20));
    }
    
    // Test for the method iterator

    @Test public void getElements_ExactNumberOfElements() {
        Iterator theIterator = theTree.iterator();
        assertNotNull(theIterator);
        assertTrue(theIterator.hasNext());
        assertEquals(20, theIterator.next());
        assertEquals(20, theIterator.next());
        assertEquals(20, theIterator.next());
        assertEquals(40, theIterator.next());
        assertEquals(50, theIterator.next());
        assertEquals(70, theIterator.next());
        assertEquals(100, theIterator.next());
        assertFalse(theIterator.hasNext());
    }

    // Test for the method removeRootElement

    @Test public void removeRootElement_TreeWithMoreThanTwoElements() {
        Object rootElement = theTree.getRootElement();
        int oldNbOccurs = theTree.getNbOccurrencesOf(rootElement);
        NonEmptyBinaryTree resultTree = theTree.removeRootElement();
        assertEquals(6, resultTree.getNbElements());
        assertEquals(oldNbOccurs - 1, resultTree
            .getNbOccurrencesOf(rootElement));
    }

    @Test public void removeRootElement_TreeWithTwoElements() throws Exception {
        ComposedSearchTree theTree = new ComposedSearchTree(20, 30);
        Object rootElement = theTree.getRootElement();
        int oldNbOccurs = theTree.getNbOccurrencesOf(rootElement);
        NonEmptyBinaryTree resultTree = theTree.removeRootElement();
        assertTrue(resultTree instanceof LeafSearchTree);
        assertEquals(oldNbOccurs - 1, resultTree
            .getNbOccurrencesOf(rootElement));
    }

    // Test for the method canHaveAsLeftTree

    @Test public void canHaveAsLeftTree_TrueCase() throws Exception {
        int rootValue = (Integer) theTree.getRootElement();
        SearchTree leftTree = new ComposedSearchTree(rootValue - 10,
            rootValue - 20, rootValue - 15);
        assertTrue(theTree.canHaveAsLeftTree(leftTree));
    }

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

    @Test public void canHaveAsLeftTree_ValuesExceedingRoot() throws Exception {
        int rootValue = (Integer) theTree.getRootElement();
        SearchTree leftTree = new ComposedSearchTree(rootValue - 10,
            rootValue + 20, rootValue - 15);
        assertFalse(theTree.canHaveAsLeftTree(leftTree));
    }

    @Test public void canHaveAsLeftTree_UncomparableValues() throws Exception {
        SearchTree leftTree = new ComposedSearchTree("abc", "xyz", "123");
        assertFalse(theTree.canHaveAsLeftTree(leftTree));
    }

    // Test for the method canHaveAsRightTree

    @Test public void canHaveAsRightTree_LegalCase() throws Exception {
        int rootValue = (Integer) theTree.getRootElement();
        SearchTree rightTree = new ComposedSearchTree(rootValue + 10,
            rootValue + 20, rootValue + 15);
        assertTrue(theTree.canHaveAsRightTree(rightTree));
    }

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

    @Test public void canHaveAsRightTree_ValuesExceedingRoot() throws Exception {
        int rootValue = (Integer) theTree.getRootElement();
        SearchTree rightTree = new ComposedSearchTree(rootValue + 10,
            rootValue - 20, rootValue + 15);
        assertFalse(theTree.canHaveAsRightTree(rightTree));
    }

    @Test public void canHaveAsRightTree_UncomparableValues() throws Exception {
        SearchTree rightTree = new ComposedSearchTree("abc", "xyz", "123");
        assertFalse(theTree.canHaveAsRightTree(rightTree));
    }

    // Test for the method canHaveAsSubTrees

    @Test public void canHaveAsSubTrees_LegalTrees() throws Exception {
        assertTrue(theTree.canHaveAsSubTrees(new LeafSearchTree(-1000),
            new LeafSearchTree(1000)));
    }

    @Test public void canHaveAsSubTrees_InvalidLeftTree() throws Exception {
        assertFalse(theTree.canHaveAsSubTrees(null, new LeafSearchTree(66)));
    }

    @Test public void canHaveAsSubTrees_InvalidRightTree() throws Exception {
        assertFalse(theTree.canHaveAsSubTrees(new LeafSearchTree(66), null));
    }

    @Test public void canHaveAsSubTrees_EmptyTrees() {
        assertFalse(theTree.canHaveAsSubTrees(EmptySearchTree.getPrototype(),
            EmptySearchTree.getPrototype()));
    }

    // Test for the method changeToLeafTree

    @Test public void changeToLeafTree_AtLeastTwoElements() {
        NonEmptySearchTree resultTree = theTree.changeToLeafTree();
        assertSame(theTree, resultTree);
    }

    @Test public void changeToLeafTree_SingleElement() {
        theTree.setLeftTree(EmptySearchTree.getPrototype());
        theTree.setRightTree(EmptySearchTree.getPrototype());
        Object theElement = theTree.getRootElement();
        NonEmptySearchTree resultTree = theTree.changeToLeafTree();
        assertTrue(resultTree instanceof LeafSearchTree);
        assertEquals(1, resultTree.getNbOccurrencesOf(theElement));
    }

}
