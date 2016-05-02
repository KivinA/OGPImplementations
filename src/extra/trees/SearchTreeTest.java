package extra.trees;


import static org.junit.Assert.*;

import org.junit.*;


public class SearchTreeTest {
    
    private static SearchTree theTree;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        theTree = new ComposedSearchTree(10,40,80,7);
    }
    
    @Test public void canHaveAsElement_NonComparable() {
        assertFalse(theTree.canHaveAsElement(null));
    }

}
