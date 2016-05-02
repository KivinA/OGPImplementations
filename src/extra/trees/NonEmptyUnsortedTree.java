package extra.trees;

/**
 * A class of unsorted non-empty binary trees.
 * 
 * @version  2.0
 * @author   Eric Steegmans
 */
public interface NonEmptyUnsortedTree extends UnsortedBinaryTree, NonEmptyBinaryTree {

	/**
	 * Add the given element to this non-empty unsorted binary tree.
	 */
	@Override
	public abstract ComposedUnsortedTree addElement(Object element);

    /**
     * Return a clone of this non-empty unsorted binary tree.
     */
	@Override
    public abstract NonEmptyUnsortedTree clone();

}