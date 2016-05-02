package extra.trees;

import be.kuleuven.cs.som.annotate.Raw;
import extra.trees.exceptions.*;

/**
 * A class of unsorted composed binary trees.
 *	A composed binary tree consists of a root node in which an element
 *	is stored, complemented with a left subtree and a right subtree,
 *	of which at least one is not empty.
 * 
 * @version  2.0
 * @author   Eric Steegmans
 */
public class ComposedUnsortedTree extends ComposedBinaryTree implements
        NonEmptyUnsortedTree {

    /**
     * Initialize this new unsorted composed binary tree with given elements.
     *
     * @param   elements
     *          The elements to be stored in this new unsorted composed
     *          binary tree.
     * @post    The number of occurrences of each object (including the null
     *          reference) in this new unsorted composed binary tree is equal
     *          to the number of occurrences of that element in the given
     *          array of elements.
     *        | for each element in (Object union {null}):
     *        |   (new.getNbOccurrencesOf(element) ==
     *        |        ExtArray.getNbOcurrencesOf(elements,element))
     * @throws  IllegalArgumentException
     *          The given array of elements does not have at least 2 elements.
     *        | elements.length < 2
     * @note    We assume a definition of a static inspector getNbOccurrencesOf
     *          returning the number of occurrences of a given object in a given
     *          array.
     */
    public ComposedUnsortedTree(Object... elements)
            throws IllegalArgumentException {
        if (elements.length < 2)
            throw new IllegalArgumentException();
        // Register the element in the middle of the given array as
        // the root of this new unsorted composed binary tree.
        int middleIndex = elements.length / 2;
        setRootElement(elements[middleIndex]);
        // Register all elements before the middle index in the left
        // subtree of this new unsorted composed binary tree.
        setLeftTree(EmptyUnsortedTree.getPrototype());
        for (int i = 0; i < middleIndex; i++)
            setLeftTree(getLeftTree().addElement(elements[i]));
        // Register all elements after the middle index in the right
        // subtree of this new unsorted composed binary tree.
        setRightTree(EmptyUnsortedTree.getPrototype());
        for (int i = middleIndex + 1; i < elements.length; i++)
            setRightTree(getRightTree().addElement(elements[i]));
    }

    /**
     * Check whether this unsorted composed binary tree can have occurrences
     * of the given element.
     */
    @Raw
    @Override
    public final boolean canHaveAsElement(Object element) {
        return true;
    }

    /**
     * Add the given element to this unsorted composed binary tree.
     */
    @Override
    public ComposedUnsortedTree addElement(Object element) {
        try {
            return (ComposedUnsortedTree) super.addElement(element);
        }
        catch (IllegalElementException exc) {
            assert false;
            return null;
        }
    }

    /**
     * Remove one occurrence of the given element from this unsorted composed
     * binary tree.
     */
    @Override
    public NonEmptyUnsortedTree removeElement(Object element)
            throws IllegalElementException {
        return (NonEmptyUnsortedTree) super.removeElement(element);
    }

    /**
     * Return a clone of this unsorted composed binary tree.
     */
    @Override
    public ComposedUnsortedTree clone() {
        return (ComposedUnsortedTree) super.clone();
    }

    /**
     * Remove the element stored in the root of this composed unsorted tree.
     */
    @Override
    protected NonEmptyUnsortedTree removeRootElement() {
        return (NonEmptyUnsortedTree) super.removeRootElement();
    }

    /**
     * Return the left subtree of this composed unsorted tree.
     */
    @Override
    protected UnsortedBinaryTree getLeftTree() {
        return (UnsortedBinaryTree) super.getLeftTree();
    }

    /**
     * Check whether this composed unsorted tree can have the given
     * binary tree as its left subtree.
     * 
     * @return  True if and only if the given binary tree is an
     *          effective unsorted tree, and if the given binary tree
     *          is not the same as this composed unsorted tree, and if
     *          the given binary tree does not have this tree as a
     *          direct or indirect subtree.
     *        | result ==
     *        |   (tree instanceof UnsortedBinaryTree) &&
     *        |   (tree != this) &&
     *        |   (!((BinaryTreeImpl)tree).hasAsSubtree(this))
     */
    protected boolean canHaveAsLeftTree(BinaryTree tree) {
        return (tree instanceof UnsortedBinaryTree)
            && super.canHaveAsLeftTree(tree);
    }

    /**
     * Return the right subtree of this composed unsorted tree.
     */
    @Override
    protected UnsortedBinaryTree getRightTree() {
        return (UnsortedBinaryTree) super.getRightTree();
    }

    /**
     * Check whether this composed unsorted tree can have the given
     * binary tree as its right subtree.
     * 
     * @return  True if and only if the given binary tree is an
     *          effective unsorted tree, and if the given binary tree
     *          is not the same as this composed unsorted tree, and if
     *          the given binary tree does not have this tree as a
     *          direct or indirect subtree.
     *        | result ==
     *        |   (tree instanceof UnsortedBinaryTree) &&
     *        |   (tree != this) &&
     *        |   (!((BinaryTreeImpl)tree).hasAsSubtree(this))
     */
    protected boolean canHaveAsRightTree(BinaryTree tree) {
        return (tree instanceof UnsortedBinaryTree)
            && super.canHaveAsRightTree(tree);
    }

    /**
     * Check whether this composed unsorted tree can have the given
     * trees as its left subtree, respectively as its right subtree.
     * 
     * @return  True if and only if this composed unsorted tree can
     *          have the given left tree as its left subtree, and if
     *          this composed unsorted tree can have the given right
     *          tree as its right subtree, and if at least one of
     *          the given trees is not an empty tree.
     *        | result == 
     *        |   canHaveAsLeftTree(left) &&
     *        |   canHaveAsRightTree(right) &&
     *        |   ( (! (left instanceof EmptyBinaryTree) ) ||
     *        |     (! (right instanceof EmptyBinaryTree) ) )
     */
    @Override
    protected boolean canHaveAsSubTrees(BinaryTree left, BinaryTree right) {
        return ((!(left instanceof EmptyBinaryTree)) || (!(right instanceof EmptyBinaryTree)))
            && super.canHaveAsSubTrees(left, right);
    }

    /**
     * Change this composed binary into a binary leaf tree, if it only has
     * one element.
     */
    @Override
    protected NonEmptyUnsortedTree changeToLeafTree() {
        if (getLeftTree().isEmpty() && getRightTree().isEmpty())
            return new LeafUnsortedTree(getRootElement());
        else
            return this;
    }

}