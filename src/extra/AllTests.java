package extra;
import extra.trees.*;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { BinaryTreeTest.class, EmptyBinaryTreeTest.class,
    NonEmptyBinaryTreeTest.class, LeafBinaryTreeTest.class, ComposedBinaryTreeTest.class,
    UnsortedBinaryTreeTest.class, EmptyUnsortedTreeTest.class, LeafUnsortedTreeTest.class,
    ComposedUnsortedTreeTest.class, SearchTreeTest.class, EmptySearchTreeTest.class,
    NonEmptySearchTreeTest.class, LeafSearchTreeTest.class, ComposedSearchTreeTest.class })
public class AllTests {
}