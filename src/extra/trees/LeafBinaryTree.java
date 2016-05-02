package extra.trees;

import java.util.Iterator;
import java.util.NoSuchElementException;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

import extra.trees.exceptions.*;

/**
 * A class of binary trees consisting of a single leaf.
 *		A binary leaf tree has exactly one node in which a single
 *		element is stored.
 *
 * @invar   Each leaf binary tree has exactly one occurrence
 *          of some object (including the null reference).
 *        | getNbElements() == 1
 *
 * @version  2.0
 * @author   Eric Steegmans
 */
public abstract class LeafBinaryTree extends NonEmptyBinaryTreeImpl {

	/**
	 * Initialize this new binary leaf tree with the given element
	 * as its only element.
	 *
	 * @param   element
	 *          The element to be stored in this binary leaf tree.
	 * @post    The root element of this new binary leaf tree is the
	 *          same as the given element.
	 *        | new.getRootElement() == element
	 * @throws  IllegalElementException
	 *          This new binary leaf tree cannot have the given element
	 *          as its element.
	 *        | ! canHaveAsElement(element)
	 */
	@Raw
	protected LeafBinaryTree(Object element) throws IllegalElementException {
		super(element);
	}

	/**
	 * Initialize this new binary leaf tree with the null reference
	 * as its element.
	 * 
	 * @post    The root element of this new non-empty binary tree is equal
	 *          to the null reference.
	 *        | new.getRootElement() == null
	 * @note    This constructor is needed in some of the subclasses that
	 *          need a constructor not throwing any exceptions.
	 */
	@Raw
	protected LeafBinaryTree() {
	}

	/**
	 * Return the number of occurrences of the given element in this
	 * binary leaf tree.
	 */
	@Basic
	@Override
	public int getNbOccurrencesOf(Object element) {
		// Re-implementation for reasons of efficiency.
		if ((getRootElement() == null) && (element == null))
			return 1;
		else if ((getRootElement() != null) && getRootElement().equals(element))
			return 1;
		return 0;
	}

	/**
	 * Check whether this leaf binary tree can have the given number
	 * of occurrences of the given element as the number of occurrences
	 * of that element in it.
	 * 
	 * @return  True if and only if the given number of occurrences is zero,
	 *          or if the given number of occurrences is one and this leaf
	 *          binary tree can have the given element as its element.
	 *        | result ==
	 *        |   (nbOccurrences == 0) ||
	 *	      |   ( (nbOccurrences == 1) && canHaveAsElement(element) )
	 */
	@Raw
	@Override
	public final boolean canHaveAsNbOccursOfElement(int nbOccurrences,
			Object element) {
		return (nbOccurrences == 0)
				|| ((nbOccurrences == 1) && canHaveAsElement(element));
	}

	/**
	 * Check whether this binary leaf tree has the given element as one
	 * of its elements.
	 */
	@Override
	public boolean hasAsElement(Object element) {
		// Re-implementation for reasons of efficiency.
		return ((getRootElement() == null) && (element == null))
				|| ((getRootElement() != null) && getRootElement().equals(
						element));
	}

	/**
	 * Return the total number of elements in this binary leaf tree.
	 *
	 * @return	Always 1.
	 *	      | result == 1
	 */
	@Raw
	@Override
	public final int getNbElements() {
		return 1;
	}

	/**
	 * Remove one occurrence of the given element from this binary
	 * leaf tree.
	 */
	@Override
	public abstract EmptyBinaryTree removeElement(Object element)
			throws IllegalElementException;

	/**
	 * Return a clone of this binary leaf tree.
	 */
	@Override
	public LeafBinaryTree clone() {
		return (LeafBinaryTree) super.clone();
	}

	/**
	 * Return an iterator returning all the elements in this binary
	 * leaf tree one by one.
	 */
	@Override
	public Iterator<Object> iterator() {
		return new Iterator<Object>() {

			public boolean hasNext() {
				return !rootAlreadyReturned;
			}

			public Object next() throws NoSuchElementException {
				if (!hasNext())
					throw new NoSuchElementException();
				rootAlreadyReturned = true;
				return getRootElement();
			}

			private boolean rootAlreadyReturned;
		};
	}

	/**
	 * Remove the element stored in the root of this binary leaf tree.
	 */
	@Override
	protected abstract EmptyBinaryTree removeRootElement();

	/**
	 * Return the left subtree of this binary leaf tree.
	 */
	@Override
	protected abstract EmptyBinaryTree getLeftTree();

	/**
	 * Check whether this leaf binary tree can have the given
	 * binary tree as its left subtree.
	 * 
	 * @return  False if the given binary tree is not an empty binary
	 *          tree.
	 *        | if (! (tree instanceof EmptyBinaryTree)
	 *        |   then result == false
	 */
	@Override
	protected boolean canHaveAsLeftTree(BinaryTree tree) {
		return tree instanceof EmptyBinaryTree;
	}

	/**
	 * Return the right subtree of this binary leaf tree.
	 *
	 * @see    NonEmptyBinaryTreeImpl
	 */
	protected abstract EmptyBinaryTree getRightTree();

	/**
	 * Check whether this leaf binary tree can have the given
	 * binary tree as its right subtree.
	 * 
	 * @return  False if the given binary tree is not an empty binary
	 *          tree.
	 *        | if (! (tree instanceof EmptyBinaryTree)
	 *        |   then result == false
	 */
	@Override
	protected boolean canHaveAsRightTree(BinaryTree tree) {
		return tree instanceof EmptyBinaryTree;
	}

	/**
	 * Check whether this leaf binary tree has the given tree as a direct
	 * or indirect subtree.
	 * 
	 */
	@Override
	public boolean hasAsSubTree(BinaryTree tree) {
		// Re-implementation for reasons of efficiency.
		return (getLeftTree() == tree) || (getRightTree() == tree);
	}

	/**
	 * Check whether this leaf binary tree can have the given
	 * trees as its left subtree, respectively as its right subtree.
	 * 
	 * @return  True if and only if this non-empty binary tree can have
	 *          the given left tree as its left subtree and the given
	 *          right tree as its right subtree.
	 *        | result ==
	 *        |   canHaveAsLeftTree(left) && canHaveAsRightTree(right)
	 */
	@Override
	protected boolean canHaveAsSubTrees(BinaryTree left, BinaryTree right) {
		return super.canHaveAsSubTrees(left, right);
	}

}