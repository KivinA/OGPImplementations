package extra.trees;

import be.kuleuven.cs.som.annotate.Raw;
import extra.trees.exceptions.*;

/**
 * A class of empty binary search trees.
 */
public class EmptySearchTree extends EmptyBinaryTree implements SearchTree {

    /**
     * Return a reference to a predefined empty search tree.
     *
     * @return	A reference to an effective predefined empty search tree
     *			(all empty search trees are identical, because they
     *			do not store any elements).
     *	      | result != null
     */
    public static EmptySearchTree getPrototype() {
        return prototype;
    }

    /**
     * Initialize this new empty search tree.
     */
    protected EmptySearchTree() {
        // This constructor cannot be qualified private, if it must
        // still be possible to derive subclasses from it.
    }

    /**
     * The prototype instance of an empty search tree.
     *
     * @invar	The prototype of the empty search tree is effective.
     *	      | prototype != null
     */
    private static EmptySearchTree prototype = new EmptySearchTree();

    /**
     * Check whether this empty search tree can have occurrences of the
     * given element.
     * 
     * @return True if and only if the given element is an effective
     *         comparable.
     *       | result == (element instanceof Comparable)
     */
    @Raw
    public boolean canHaveAsElement(Object element) {
        return SearchTree.super.canHaveAsElement(element);
    }

    /**
     * Add the given element to this empty search tree.
     */
    @Override
    public LeafSearchTree addElement(Object element)
            throws IllegalElementException {
        return new LeafSearchTree(element);
    }

    /**
     * Remove one occurrence of the given element from this empty
     * binary tree.
     *
     * @note    A redefinition is needed because the return types of the
     *          method in EmptyBinaryTree [BinaryTree] and in
     *          SearchTree [SearchTree] differ. The redefinition must
     *          reveal the actual type. That type must be compatible
     *          with both types (i.e. the same type or a subtype).
     */
    @Override
    public EmptySearchTree removeElement(Object element)
            throws IllegalElementException {
        return (EmptySearchTree) super.removeElement(element);
    }

}