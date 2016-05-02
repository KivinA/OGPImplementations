package extra.trees;

import be.kuleuven.cs.som.annotate.Raw;
import extra.trees.exceptions.*;

/**
 * An interface of unsorted binary trees for storing objects.
 *   An unsorted binary tree stores its elements at arbitrary
 *   positions.
 * 
 * @version  2.0
 * @author   Eric Steegmans
 */
public interface UnsortedBinaryTree extends BinaryTree {

    /**
     * Check whether this unsorted binary tree can have occurrences
     * of the given element.
     * 
     * @return  Always true.
     *        | result == true
     */
    @Raw
    @Override
    public abstract boolean canHaveAsElement(Object element);

    /**
     * Add the given element to this unsorted binary tree.
     *
     * @note    This version no longer throws an exception, reflecting that
     *          it will always be possible to add the element, be it in
     *          a new unsorted binary tree of another concrete type.
     */
    @Override
    public abstract NonEmptyUnsortedTree addElement(Object element);

    /**
     * Remove one occurrence of the given element from this unsorted
     * binary tree.
     */
    @Override
    public abstract UnsortedBinaryTree removeElement(Object element)
            throws IllegalElementException;

}