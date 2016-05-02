package extra.trees;

import be.kuleuven.cs.som.annotate.Raw;
import extra.trees.exceptions.*;

/**
 * A class of unsorted binary trees consisting of a single leaf.
 *
 * @version  2.0
 * @author   Eric Steegmans
 */
public class LeafUnsortedTree extends LeafBinaryTree implements
        NonEmptyUnsortedTree {

    /**
     * Initialize this new unsorted binary leaf tree with the given
     * element.
     *
     * @param  element
     *         The element to be stored in this new unsorted binary
     *         leaf tree.
     * @post   This new unsorted binary leaf tree stores a single
     *         occurrence of the given element.
     *       | new.getNbOccurrences(element) == 1
     */
    public LeafUnsortedTree(Object element) {
        // The super constructor with an element as its argument cannot be used
        // here, because its exception cannot be catched in the body of this
        // constructor.
        setRootElement(element);
    }

    /**
     * Check whether this unsorted binary leaf tree can have occurrences
     * of the given element.
     */
    @Raw
    @Override
    public final boolean canHaveAsElement(Object element) {
        return true;
    }

    /**
     * Add the given element to this unsorted binary leaf tree.
     */
    public ComposedUnsortedTree addElement(Object element) {
        return new ComposedUnsortedTree(getRootElement(), element);
    }

    /**
     * Remove one occurrence of the given element from this unsorted
     * binary leaf tree.
     */
    @Override
    public EmptyUnsortedTree removeElement(Object element)
            throws IllegalElementException {
        if (!hasAsElement(element))
            throw new IllegalElementException(element, this);
        return EmptyUnsortedTree.getPrototype();
    }

    /**
     * Return a clone of this unsorted binary leaf tree.
     */
    @Override
    public LeafUnsortedTree clone() {
        return (LeafUnsortedTree) super.clone();
    }

    /**
     * Remove the element stored in the root of this unsorted binary leaf
     * tree.
     */
    @Override
    protected EmptyUnsortedTree removeRootElement() {
        return EmptyUnsortedTree.getPrototype();
    }

    /**
     * Return the left subtree of this unsorted binary leaf tree.
     */
    @Override
    protected EmptyUnsortedTree getLeftTree() {
        return EmptyUnsortedTree.getPrototype();
    }

    /**
     * Check whether this unsorted binary leaf tree can have the given
     * binary tree as its left subtree.
     * 
     * @return  True if and only if the given binary tree is an empty
     *          unsorted binary tree.
     *          | result == (tree instanceof EmptyUnsortedTree)
     */
    @Override
    protected boolean canHaveAsLeftTree(BinaryTree tree) {
        return tree instanceof EmptyUnsortedTree;
    }

    /**
     * Return the right subtree of this unsorted binary leaf tree.
     */
    @Override
    protected EmptyUnsortedTree getRightTree() {
        return EmptyUnsortedTree.getPrototype();
    }

    /**
     * Check whether this unsorted binary leaf tree can have the given
     * binary tree as its right subtree.
     * 
     * @return  True if and only if the given binary tree is an empty
     *          unsorted binary tree.
     *          | result == (tree instanceof EmptyUnsortedTree)
     */
    @Override
    protected boolean canHaveAsRightTree(BinaryTree tree) {
        return tree instanceof EmptyUnsortedTree;
    }

}