package extra.trees;

import static org.junit.Assert.*;
import java.util.stream.Stream;

import org.junit.*;

import extra.trees.exceptions.IllegalElementException;

@SuppressWarnings("rawtypes")
public class BinaryTreeTest {
	
	private static BinaryTree treeWithTheObject, emptyTree, nonEmptyTree, searchTreeofIntegers;

	private static Object theObject;

	@BeforeClass public static void setUpBeforeClass() throws Exception {
		theObject = 4;
		treeWithTheObject = new ComposedUnsortedTree(6,28,theObject,-11,6,theObject);
		emptyTree = EmptyUnsortedTree.getPrototype();
		nonEmptyTree = new LeafUnsortedTree(5);
		searchTreeofIntegers = new ComposedSearchTree(20,40, 60);
	}
	
	// Test for the method canHaveAsNbOccursOfElement
	
	@Test public void canHaveAsNbOccursOfElement_ZeroOccurrences() {
		assertTrue(treeWithTheObject.canHaveAsNbOccursOfElement(0,4));
	}
    
    @Test public void canHaveAsNbOccursOfElement_NegativeNumber() {
        assertFalse(treeWithTheObject.canHaveAsNbOccursOfElement(-1,4));
    }
	
	@Test public void canHaveAsNbOccursOfElement_PositiveNumberIllegalElement() {
		assertFalse(searchTreeofIntegers.canHaveAsNbOccursOfElement(1,"abc"));
	}

	// Test for the method hasAsElement
	
	@Test public void hasAsElement_TrueCase() {
		assertTrue(treeWithTheObject.hasAsElement(theObject));
	}
	
	@Test public void hasAsElement_FalseCase() {
		assertFalse(treeWithTheObject.hasAsElement(5));
	}

    // Test for the method getNbElements
    
    @Test public void getNbElements_SingleCase() {
        assertEquals(6,treeWithTheObject.getNbElements());
    }
    
	// Test for the method isEmpty
	
	@Test public void isEmpty_TrueCase() {
		assertTrue(emptyTree.isEmpty());
	}
	
	@Test public void isEmpty_FalseCase() {
		assertFalse(nonEmptyTree.isEmpty());
	}
	
    // Test for the method addElement
    
    @Test public void addElement_LegalCase() throws Exception {
        int oldNbOccurrences = treeWithTheObject.getNbOccurrencesOf(theObject);
        int oldNbElements = treeWithTheObject.getNbElements();
        BinaryTree resultTree = treeWithTheObject.addElement(theObject);
        assertNotNull(resultTree);
        assertEquals(oldNbOccurrences+1,resultTree.getNbOccurrencesOf(theObject));
        assertEquals(oldNbElements+1,resultTree.getNbElements());
    }
    
    @Test(expected=IllegalElementException.class) public void addElement_IllegalCase() throws Exception {
        searchTreeofIntegers.addElement("abc");
    }
    
    // Test for the method removeElement
    
    @Test public void removeElement_LegalCase() throws Exception {
        int oldNbOccurrences = treeWithTheObject.getNbOccurrencesOf(theObject);
        int oldNbElements = treeWithTheObject.getNbElements();
        BinaryTree resultTree = treeWithTheObject.removeElement(theObject);
        assertNotNull(resultTree);
        assertEquals(oldNbOccurrences-1,resultTree.getNbOccurrencesOf(theObject));
        assertEquals(oldNbElements-1,resultTree.getNbElements());
    }
    
    @Test(expected=IllegalElementException.class) public void removeElement_IllegalCase() throws Exception {
        treeWithTheObject.removeElement("abc");
    }
    
    // Test for the method clone
    
    @Test public void clone_SingleCase() {
        BinaryTree clone = treeWithTheObject.clone();
        assertNotNull(clone);
        assertEquals(treeWithTheObject.getClass(),clone.getClass());
        assertEquals(treeWithTheObject.getNbElements(),clone.getNbElements());
       assertEquals(treeWithTheObject.getNbOccurrencesOf(6),clone.getNbOccurrencesOf(6));
       assertEquals(treeWithTheObject.getNbOccurrencesOf(28),clone.getNbOccurrencesOf(28));
       assertEquals(treeWithTheObject.getNbOccurrencesOf(theObject),clone.getNbOccurrencesOf(theObject));
       assertEquals(treeWithTheObject.getNbOccurrencesOf(-11),clone.getNbOccurrencesOf(-11));
    }
    
    // Test for the method iterator
    
    @Test public void iterator_NonEmptyTree() {
        int value = 0;
        assertNotNull(searchTreeofIntegers.iterator());
        assertTrue(searchTreeofIntegers.iterator().hasNext());
        for (Object element: searchTreeofIntegers) {
            value += 20;
            assertEquals(value,element);
        }
    }
    
    @Test public void iterator_EmptyTree() {
        assertNotNull(emptyTree.iterator());
        assertFalse(emptyTree.iterator().hasNext());
    }
    
    // Test for the method stream.
    
    @Test public void stream_NonEmptyTree() {
        Stream stream = searchTreeofIntegers.stream();
        Object[] elements = stream.toArray();
        int value = 0;
        for (Object element: elements) {
            value += 20;
            assertEquals(value,element);
        }
    	
    }
        
    // Test for the method hasAsSubtree (in BinaryTreeImpl)
    
    @Test public void hasAsSubtree_NonEffectiveTree() {
        assertFalse(treeWithTheObject.hasAsSubTree(null));
    }

}
