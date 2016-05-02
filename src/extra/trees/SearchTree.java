package extra.trees;

import extra.trees.exceptions.*;

/**
 * An interface of binary search trees for storing objects.
 *   A binary search tree stores objects of classes implementing
 *   the interface Comparable. The elements are stored in an
 *   ordered way.
 * 
 * @version  2.0
 * @author   Eric Steegmans
 */
public interface SearchTree extends BinaryTree {
	
	/**
     * Check whether this search tree can have occurrences of the given
     * element.
	 * 
     * @return False if the given element is not an effective comparable.
     *       | if (! (element instanceof Comparable))
     *       |   then result == false
 	 */
	@Override
	public default boolean canHaveAsElement(Object element) {
		return element instanceof Comparable;
	}

    /**
     * Add the given element to this search tree.
     */
	@Override
    public abstract NonEmptySearchTree addElement(Object element)
            throws IllegalElementException;

    /**
     * Remove one occurrence of the given element from this search tree.
     */
	@Override
    public abstract SearchTree removeElement(Object element)
            throws IllegalElementException;

}