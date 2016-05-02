package extra.trees;


import static org.junit.Assert.*;

import org.junit.*;


public class UnsortedBinaryTreeTest {
    
    private static UnsortedBinaryTree theTree;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        theTree = new ComposedUnsortedTree(null,23,null,37);
    }
    
    // Test for the method canHaveAsElement
    
    @Test public void canHaveAsElement_SingleCase() {
        assertTrue(theTree.canHaveAsElement(null));
    }
    
}
