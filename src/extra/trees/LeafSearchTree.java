package extra.trees;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import extra.trees.exceptions.*;

/**
 * A class of search trees consisting of a single leaf.
 * 
 * @version  2.0
 * @author   Eric Steegmans
 */
@SuppressWarnings("all")
public class LeafSearchTree extends LeafBinaryTree implements
        NonEmptySearchTree {

    /**
     * Initialize this new leaf search tree with given element.
     *
     * @param  element
     *         The element to be stored in this new leaf search tree.
     * @post   This new leaf search tree stores the given element
     *         as its only element.
     *       | new.getNbOccurrences(element) == 1
     * @throws IllegalElementException
     *         This new leaf search tree can not have the given element
     *         as one of its elements.
     *       | ! canHaveAsElement(element)
     */
    public LeafSearchTree(Object element) throws IllegalElementException {
        super(element);
    }

    /**
     * Return the number of occurrences of the given element in this
     * leaf search tree.
     */
    @Basic
    @Override
    public final int getNbOccurrencesOf(Object element) {
        // Re-implementation for reasons of efficiency.
        return (getRootElement().equals(element) ? 1 : 0);
    }

    /**
     * Check whether this leaf search tree can have occurrences of the given
     * element.
     */
    @Raw
    @Override
    public boolean canHaveAsElement(Object element) {
        try {
            getRootElement().compareTo(element);
            return true;
        }
        catch (ClassCastException exc) {
            // The element in the root is not comparable with the
            // given element.
            return false;
        }
        catch (NullPointerException exc) {
            // This leaf search tree is still under construction or
            // the given element is not effective.
            assert (getRootElement() == null) || (element == null);
            return (element instanceof Comparable);
        }
    }

    /**
     * Check whether the given element is stored in this leaf search tree.
     */
    @Override
    public boolean hasAsElement(Object element) {
        // Re-implementation for reasons of efficiency.
        return getRootElement().equals(element);
    }

    /**
     * Add the given element to this leaf search tree.
     */
    @Override
    public ComposedSearchTree addElement(Object element)
            throws IllegalElementException {
        return new ComposedSearchTree(getRootElement(), element);
    }

    /**
     * Remove one occurrence of the given element from this leaf search tree.
     */
    @Override
    public EmptySearchTree removeElement(Object element)
            throws IllegalElementException {
        if (!hasAsElement(element))
            throw new IllegalElementException(element, this);
        return EmptySearchTree.getPrototype();
    }

    /**
     * Return a clone of this leaf search tree.
     */
    @Override
    public LeafSearchTree clone() {
        return (LeafSearchTree) super.clone();
    }

    /**
     * Return the element stored in the root of this leaf search
     * tree.
     */
	@Override
    protected Comparable getRootElement() {
        return (Comparable) super.getRootElement();
    }

    /**
     * Remove the element stored in the root of this leaf search tree.
     */
    @Override
    protected EmptySearchTree removeRootElement() {
        return EmptySearchTree.getPrototype();
    }

    /**
     * Return the left subtree of this leaf search tree.
     */
    @Override
    protected EmptySearchTree getLeftTree() {
        return EmptySearchTree.getPrototype();
    }

    /**
     * Check whether this leaf search tree can have the given
     * binary tree as its left subtree.
     * 
     * @return  True if and only if the given binary tree is
     *          an empty search tree.
     *        | result == (tree instanceof EmptySearchTree)
     */
    @Override
    protected boolean canHaveAsLeftTree(BinaryTree tree) {
        return tree instanceof EmptySearchTree;
    }

    /**
     * Return the right subtree of this leaf search tree.
     */
    @Override
    protected EmptySearchTree getRightTree() {
        return EmptySearchTree.getPrototype();
    }

    /**
     * Check whether this leaf search tree can have the given
     * binary tree as its right subtree.
     * 
     * @return  True if and only if the given binary tree is
     *          an empty search tree.
     *        | result == (tree instanceof EmptySearchTree)
     */
    @Override
    protected boolean canHaveAsRightTree(BinaryTree tree) {
        return tree instanceof EmptySearchTree;
    }

}