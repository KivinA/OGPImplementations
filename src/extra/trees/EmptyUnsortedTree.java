package extra.trees;

import be.kuleuven.cs.som.annotate.*;
import extra.trees.exceptions.IllegalElementException;

/**
 * A class of unsorted empty binary trees.
 * 
 * @version  2.0
 * @author   Eric Steegmans
 */
public class EmptyUnsortedTree extends EmptyBinaryTree implements
        UnsortedBinaryTree {

    /**
     * Return a reference to a predefined empty unsorted binary tree.
     *
     * @return A reference to an effective predefined empty unsorted binary
     *         tree (all empty unsorted binary trees are identical, because
     *         they do not store any values).
     *       | result != null
     */
    @Immutable
    public static EmptyUnsortedTree getPrototype() {
        return prototype;
    }

    /**
     * Initialize this new empty unsorted binary tree.
     */
    protected EmptyUnsortedTree() {
        // This constructor cannot be qualified private, if it must
        // still be possible to derive subclasses from this class.
    }

    /**
     * The prototype instance of an empty unsorted binary tree.
     *
     * @invar  The prototype of the empty unsorted binary tree is effective.
     *       | prototype != null
     */
    private final static EmptyUnsortedTree prototype = new EmptyUnsortedTree();

    /**
     * Check whether this unsorted empty binary tree can have occurrences
     * of the given element.
     */
    @Raw
    @Override
    public final boolean canHaveAsElement(Object element) {
        return true;
    }

    /**
     * Add the given element to this empty unsorted binary tree.
     */
    @Override
    public LeafUnsortedTree addElement(Object element) {
        return new LeafUnsortedTree(element);
    }

    /**
     * Remove one occurrence of the given element from this empty
     * unsorted binary tree.
     *
     * @note   A redefinition is needed because the return types of the
     *         method in EmptyBinaryTree [BinaryTree] and in
     *         UnsortedBinaryTree [UnsortedBinaryTree] differ. The
     *         redefinition must reveal the actual type. That type
     *         must be compatible with both types (i.e. the same type
     *         or a subtype).
     */
    @Override
    public EmptyUnsortedTree removeElement(Object element)
            throws IllegalElementException {
        return (EmptyUnsortedTree) super.removeElement(element);
    }

}