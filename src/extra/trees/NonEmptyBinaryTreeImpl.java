package extra.trees;

import be.kuleuven.cs.som.annotate.*;
import extra.trees.exceptions.*;

/**
 * A class of non-empty binary trees.
 *		A non-empty binary tree consists of a root node in which an
 *		element is stored, complemented with a left subtree and a right
 *		subtree.
 *
 * @version  2.0
 * @author   Eric Steegmans
 */
public abstract class NonEmptyBinaryTreeImpl implements
        NonEmptyBinaryTree {

    /**
     * Initialize this new non-empty binary tree with the given element
     * as its root element.
     *
     * @param   element
     *          The element to be stored in this new non-empty binary tree.
     * @post    The root element of this new non-empty binary tree is the
     *          same as the given element.
     *        | new.getRootElement() == element
     * @post    This new non-empty binary tree has no effective subtrees yet.
     *        | (new.getLeftTree() == null) && (new.getRightTree() == null)
     * @throws  IllegalElementException
     *          This new non-empty binary tree cannot have the given element
     *          as its element.
     *        | ! canHaveAsElement(element)
     */
    @Raw
    protected NonEmptyBinaryTreeImpl(Object element)
            throws IllegalElementException {
        if (!canHaveAsElement(element))
            throw new IllegalElementException(element, this);
        setRootElement(element);
    }

    /**
     * Initialize this new non-empty binary tree with the null reference
     * as its root element.
     * 
     * @post    The root element of this new non-empty binary tree is equal
     *          to the null reference.
     *        | new.getRootElement() == null
     * @post    This new non-empty binary tree has no effective subtrees yet.
     *        | (new.getLeftTree() == null) && (new.getRightTree() == null)
     */
    @Raw
    protected NonEmptyBinaryTreeImpl() {
    }

    /**
     * Return the number of occurrences of the given element in this
     * non-empty binary tree.
     */
    @Basic
    @Override
    public int getNbOccurrencesOf(Object element) {
        int number = 0;
        if ((getRootElement() == null) && (element == null))
            number = 1;
        else if ((getRootElement() != null) && getRootElement().equals(element))
            number = 1;
        // Because this is a raw method, there is no guarantee that
        // the left tree and the right tree are effective.
        if (getLeftTree() != null)
            number += getLeftTree().getNbOccurrencesOf(element);
        if (getRightTree() != null)
            number += getRightTree().getNbOccurrencesOf(element);
        return number;
    }

    /**
     * Check whether this non-empty binary tree has the given element as
     * one of its elements.
     */
    @Override
    public boolean hasAsElement(Object element) {
        // Re-implementation for reasons of efficiency.
        return ((getRootElement() == null) && (element == null))
            || ((getRootElement() != null) && getRootElement().equals(element))
            || getLeftTree().hasAsElement(element)
            || getRightTree().hasAsElement(element);
    }

    /**
     * Return the total number of elements in this non-empty binary tree.
     */
    @Raw
    @Override
    public int getNbElements() {
        int nbElements = 1;
        if (getLeftTree() != null)
            nbElements += getLeftTree().getNbElements();
        if (getRightTree() != null)
            nbElements += getRightTree().getNbElements();
        return nbElements;
    }

	/**
	 * Return a clone of this binary tree.
	 */
	@Override
	public NonEmptyBinaryTree clone() {
	    try {
	        return (NonEmptyBinaryTree) super.clone();
	    }
	    catch (CloneNotSupportedException exc) {
	        assert false;
	        return null;
	    }
	}

    /**
     * Return the element stored in the root of this non-empty
     * binary tree.
     */
    @Raw
    protected Object getRootElement() {
        return this.rootElement;
    }

    /**
     * Set the element stored in the root of this non-empty binary
     * tree to the given element.
     *
     * @param  element
     *         The element to be stored in the root.
     * @post   The root element of this non-empty binary tree is the
     *         same as the given element.
     *       | new.getRootElement() == element
     */
    @Raw
    protected final void setRootElement(Object element) {
        this.rootElement = element;
    }

    /**
     * Remove the element stored in the root of this non-empty binary tree.
     *
     * @post   If this non-empty binary tree is returned as a result, the
     *         number of occurrences of its root element is one less than the
     *         number of occurrences of that root element upon entry.
     *       | if (result == this)
     *       |   then new.getNbOccurences(getRootValue()) ==
     *       |             getNbOccurences(getRootValue()) - 1
     * @post   If this non-empty binary tree is returned as a result, the new
     *         element stored in the root of this non-empty binary tree was
     *         stored in its left subtree or in its right subtree upon entry.
     *       | if (result == this)
     *       |   then (getLeftTree().hasAsElement(new.getRootValue()) ||
     *       |         getRightTree().hasAsElement(new.getRootValue()) )
     * @post   If this non-empty binary tree is returned as a result, one
     *         occurrence of the new root element of this non-empty tree is
     *         removed from its left subtree or from its right subtree.
     *       | if (result == this)
     *       |   then ( ( (new getLeftTree()).getNbOccurences(new.getRootValue()) ==
     *       |             getLeftTree().getNbOccurences(new.getRootValue()) - 1) ^
     *       |          ( (new getRightTree()).getNbOccurences(new.getRootValue()) ==
     *       |            getRightTree().getNbOccurences(new.getRootValue()) - 1) ) )
     * @return Except for the old root element to be removed, the number of
     *         occurrences of all elements in the resulting tree is equal to
     *         their number of occurrences in this binary tree.
     *         | for each object in (Object union {null}):
     *         |   if (object != getRootElement()) then
     *         |     then (result.getNbOccurrences(object) ==
     *         |               this.getNbOccurrences(object))
     * @return The number of occurrences of the old root element in the resulting
     *         tree is one lower than the number of occurrences of that element
     *         in this binary tree (upon entry).
     *         | result.getNbOccurrencesOf(getRootElement()) ==
     *         |     this.getNbOccurencesOf(getRootElement()) - 1
     */
    protected abstract BinaryTree removeRootElement();

    /**
     * Variable referencing the element stored in the root of this
     * non-empty binary tree.
     */
    private Object rootElement;

    /**
     * Return the left subtree of this non-empty binary tree.
     */
    @Raw
    protected abstract BinaryTree getLeftTree();

    /**
     * Check whether this non-empty binary tree can have the given
     * binary tree as its left subtree.
     * 
     * @param   tree
     *          The tree to check.
     * @return  False if the given binary tree is not effective.
     *        | if (tree == null)
     *        |   then result == false
     * @return  False if the given binary tree is the same as this
     *          non-empty binary tree.
     *        | if (tree == this)
     *        |   then result == false
     * @return  False if the given binary tree has this tree as
     *          a direct or indirect subtree.
     *        | if ((BinaryTreeImpl)tree).hasAsSubtree(this))
     *        |   then result == false
     */
    @Raw
    protected boolean canHaveAsLeftTree(BinaryTree tree) {
        return (tree != null) && (tree != this)
            && (!tree.hasAsSubTree(this));
    }

    /**
     * Return the right subtree of this non-empty binary tree.
     */
    @Raw
    protected abstract BinaryTree getRightTree();

    /**
     * Check whether this non-empty binary tree can have the given
     * binary tree as its right subtree.
     * 
     * @param   tree
     *          The tree to check.
     * @return  False if the given binary tree is not effective.
     *        | if (tree == null)
     *        |   then result == false
     * @return  False if the given binary tree is the same as this
     *          non-empty binary tree.
     *        | if (tree == this)
     *        |   then result == false
     * @return  False if the given binary tree has this tree as
     *          a direct or indirect subtree.
     *        | if (((BinaryTreeImpl)tree).hasAsSubtree(this))
     *        |   then result == false
     */
    @Raw
    protected boolean canHaveAsRightTree(BinaryTree tree) {
        return (tree != null) && (tree != this)
            && (! tree.hasAsSubTree(this));
    }

    /**
     * Check whether this non-empty binary tree has the given tree as a direct
     * or indirect subtree.
     * 
     * @return  True if the given tree is the same as the left tree or as
     *          the right tree of this non-empty binary tree, or if the given
     *          tree is a subtree of the left tree or of the right tree of this
     *          non-empty binary tree; false otherwise.
     *        | result ==
     *        |   (getLeftTree() == tree) ||
     *        |   (getRightTree() == tree) ||
     *        |   ((BinaryTreeImpl)getLeftTree()).hasAsSubTree(tree)) ||
     *        |   ((BinaryTreeImpl)getRightTree()).hasAsSubTree(tree)) 
     */
    @Override
    @Raw
    public boolean hasAsSubTree(BinaryTree tree) {
        return (getLeftTree() == tree) || (getRightTree() == tree)
            || getLeftTree().hasAsSubTree(tree)
            || getRightTree().hasAsSubTree(tree);
    }

    /**
     * Check whether this non-empty binary tree can have the given
     * trees as its left subtree, respectively as its right subtree.
     * 
     * @param   left
     *          The tree to check as left subtree.
     * @param   right
     *          The tree to check as right subtree.
     * @return  False if this non-empty binary tree cannot have the
     *          given left tree as its left subtree.
     *        | if (! canHaveAsLeftTree(left))
     *        |   then result == false
     * @return  False if this non-empty binary tree cannot have the
     *          given right tree as its right subtree.
     *        | if (! canHaveAsRightTree(right))
     *        |   then result == false
     */
    protected boolean canHaveAsSubTrees(BinaryTree left, BinaryTree right) {
        return canHaveAsLeftTree(left) && canHaveAsRightTree(right);
    }

}