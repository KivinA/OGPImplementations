package extra.trees;

import extra.trees.exceptions.IllegalElementException;

/**
 * An interface of non-empty binary trees for storing objects.
 *		A non-empty binary tree stores at least one element.
 * 
 * @invar   Each non-empty binary tree has at least one occurrence
 *          of some object (including the null reference).
 *        | getNbElements() > 0
 *
 * @version  2.0
 * @author   Eric Steegmans
 */
public interface NonEmptyBinaryTree extends BinaryTree {

	/**
	 * Check whether this non-empty binary tree has no elements.
	 *
	 * @return Always false.
	 *       | result == false
	 */
	@Override
	public default boolean isEmpty() {
		return false;
	}

	/**
	 * Add the given element to this non-empty binary tree.
	 */
	@Override
	public abstract ComposedBinaryTree addElement(Object element)
			throws IllegalElementException;

    /**
     * Return a clone of this non-empty binary tree.
     * 
     * @return  The resulting binary tree is not the same as this non-empty
     *          binary tree.
     *        | result != this
     */
	@Override
    public abstract NonEmptyBinaryTree clone();

}