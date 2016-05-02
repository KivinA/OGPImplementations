package extra.trees;

import be.kuleuven.cs.som.annotate.Raw;
import extra.trees.exceptions.*;

/**
 * An interface of non-empty binary search trees.
 * 
 * @version  2.0
 * @author   Eric Steegmans
 */
public interface NonEmptySearchTree extends SearchTree, NonEmptyBinaryTree {

    /**
     * Check whether this non-empty search tree can have occurrences
     * of the given element.
     * 
     * @return  True if the given element is comparable with all the
     *          elements stored in this non-empty search tree.
     *        | ...
     * @note    The result of this method cannot be specified formally,
     *          because the interface Comparable does not offer a method
     *          for checking whether 2 objects are comparable. 
     */
    @Raw
    @Override
    public abstract boolean canHaveAsElement(Object element);

    /**
     * Add the given element to this non-empty search tree.
     */
    @Override
    public abstract ComposedSearchTree addElement(Object element)
            throws IllegalElementException;

    /**
     * Return a clone of this non-empty search tree.
     */
    @Override
    public abstract NonEmptySearchTree clone();

}