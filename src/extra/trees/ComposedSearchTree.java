package extra.trees;

import java.util.Iterator;
import be.kuleuven.cs.som.annotate.*;

import extra.trees.exceptions.*;

/**
 * A class of composed binary search trees.
 * 
 * @version  2.0
 * @author   Eric Steegmans
 */
/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
@SuppressWarnings("all")
public class ComposedSearchTree extends ComposedBinaryTree implements
        NonEmptySearchTree {

    /**
     * Initialize this new composed search tree with given elements.
     *
     * @param   elements
     *          The elements to be stored in this new composed search tree.
     * @post    The number of occurrences of each object (including the null
     *          reference) in this new composed search tree is equal
     *          to the number of occurrences of that element in the given
     *          array of elements.
     *        | for each element in (Object union {null}):
     *        |   (new.getNbOccurrencesOf(element) ==
     *        |        ExtArray.getNbOcurrencesOf(elements,element))
     * @throws  IllegalElementException
     *          This new composed search tree cannot have the first element
     *          in the given array of elements as an element.
     *        | ! canHaveAsElement(elements[0])
     * @throws  IllegalElementException
     *          At least one of the remaining elements in the given array
     *          of elements is not comparable with the first element.
     *        | for some I in 1..elements.length:
     *        |   ... [Unable to specify this part in a formal way,
     *        |     because the interface Comparable lacks methods]
     * @throws  IllegalArgumentException
     *          The given array of elements does not have at least 2 elements.
     *        | elements.length < 2
     * @note    We assume a definition of a static inspector getNbOccurrencesOf
     *          returning the number of occurrences of a given object in a given
     *          array.
     */
	public ComposedSearchTree(Object... elements)
            throws IllegalArgumentException, IllegalElementException {
        // Register the first element in the given array as the
        // root element of this new composed search tree.
        super(elements[0]);
        setLeftTree(EmptySearchTree.getPrototype());
        setRightTree(EmptySearchTree.getPrototype());
        if (elements.length < 2)
            throw new IllegalArgumentException();
        // Register the second element in the given array in the
        // proper subtree. We cannot yet invoke the method addElement
        // against this new composed search tree, because it does
        // not yet satisfy its invariants.
        if (!canHaveAsElement(elements[1]))
            throw new IllegalElementException(elements[1], this);
        if (((Comparable) elements[1]).compareTo(elements[0]) <= 0)
            setLeftTree(new LeafSearchTree(elements[1]));
        else
            setRightTree(new LeafSearchTree(elements[1]));
        // Add all remaining elements in the given array to this
        // new composed search tree.
        for (int i = 2; i < elements.length; i++)
            this.addElement(elements[i]);
    }

    /**
     * Return the number of occurrences of the given element in this composed
     * search tree.
     */
	@Basic
    @Override
    public int getNbOccurrencesOf(Object element) {
        // Re-implementation for reasons of efficiency.
        try {
            // Because this a a raw method, there is no guarantee that
            // the root element, the left tree and the right tree are
            // effective.
            if ( (getRootElement() == null) || (element == null) )
                return super.getNbOccurrencesOf(element);
            if (getRootElement().compareTo(element) >= 0) {
                int nbOccurrences = (getRootElement().equals(element) ? 1 : 0);
                if (getLeftTree() != null)
                    return nbOccurrences
                        + getLeftTree().getNbOccurrencesOf(element);
            }
            else if (getRightTree() != null)
                return getRightTree().getNbOccurrencesOf(element);
            return 0;
        }
        catch (ClassCastException exc) {
            // The given element is not comparable with the root element
            // of this composed search tree.
            return 0;
        }
    }

    /**
     * Check whether this composed search tree can have occurrences of the given
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
            // This composed search tree is still under construction or
            // the given element is not effective.
            assert (getRootElement() == null) || (element == null);
            return (element instanceof Comparable);
        }
    }

    /**
     * Check whether the given element is stored in this composed search tree.
     */
    @Override
    public boolean hasAsElement(Object element) {
        // Re-implementation for reasons of efficiency.
        try {
            if (getRootElement().equals(element))
                return true;
            if (getRootElement().compareTo(element) >= 0)
                return getLeftTree().hasAsElement(element);
            else
                return getRightTree().hasAsElement(element);
        }
        catch (ClassCastException exc) {
            // The given element is not comparable with the root element
            // of this composed search tree.
            return false;
        }
        catch (NullPointerException exc) {
            assert element == null;
            return false;
        }
    }

    /**
     * Add the given element to this composed search tree.
     */
    public ComposedSearchTree addElement(Object element)
            throws IllegalElementException {
        if (!canHaveAsElement(element))
            throw new IllegalElementException(element, this);
        Comparable rootElement = getRootElement();
        if (rootElement.compareTo(element) >= 0)
            setLeftTree(getLeftTree().addElement(element));
        else
            setRightTree(getRightTree().addElement(element));
        return this;
    }

    /**
     * Remove one occurrence of the given element from this composed
     * search tree.
     */
    public NonEmptySearchTree removeElement(Object element)
            throws IllegalElementException {
        try {
            if (getRootElement().equals(element))
                return removeRootElement();
            if (getRootElement().compareTo(element) > 0)
                setLeftTree(getLeftTree().removeElement(element));
            else
                setRightTree(getRightTree().removeElement(element));
            return changeToLeafTree();
        }
        catch (NullPointerException exc) {
            assert element == null;
            throw new IllegalElementException(element, this);
        }
    }

    /**
     * Return a clone of this composed search tree.
     */
    @Override
    public ComposedSearchTree clone() {
        return (ComposedSearchTree) super.clone();
    }

//    /**
//     * Return an iterator returning elements in this binary search tree
//     * restricted to the given maximum number of elements.
//     * 
//     * @return  The resulting iterator will return elements of this composed
//     *          search tree in ascending order.
//     *          (A formal specification of this clause is not possible, because
//     *          Iterator does not offer enough methods for that purpose)
//     *
//     * @see    BinaryTree
//     */
//    public Iterator getElements(final int maxNbElements) {
//        return super.getElements(maxNbElements);
//    }

    /**
     * Return the element stored in the root of this composed search
     * tree.
     */
	@Override
    protected Comparable getRootElement() {
        return (Comparable) super.getRootElement();
    }

    /**
     * Remove the element stored in the root of this composed search tree.
     */
    @Override
    protected NonEmptySearchTree removeRootElement() {
        if (getLeftTree().isEmpty())
            return (NonEmptySearchTree) getRightTree();
        else if (getLeftTree().getNbElements() == 1) {
            NonEmptyBinaryTreeImpl leftTree = (NonEmptyBinaryTreeImpl) getLeftTree();
            setRootElement(leftTree.getRootElement());
            setLeftTree(EmptySearchTree.getPrototype());
            return this.changeToLeafTree();
        }
        else {
            ComposedSearchTree leftTree = (ComposedSearchTree) getLeftTree();
            this.setLeftTree(leftTree.getRightTree());
            if (this.getNbElements() == 1)
                leftTree.setRightTree(EmptySearchTree.getPrototype());
            else
                leftTree.setRightTree(this.removeRootElement());
            return leftTree;
        }
    }

    /**
     * Return the left subtree of this composed search tree.
     */
    @Override
    protected SearchTree getLeftTree() {
        return (SearchTree) super.getLeftTree();
    }

    /**
     * Check whether this composed search tree can have the given
     * binary tree as its left subtree.
     * 
     * @return  True if and only if the given binary tree is an
     *          effective search tree different from this composed
     *          search tree, and if the given binary tree does not
     *          have this composed search tree as a direct or
     *          indirect subtree, and if this composed search tree
     *          can have each of the elements in the given tree as
     *          an element, and if each of the elements in the given
     *          tree is less than or equal to the element in the root
     *          of this composed binary tree.
     *        | result ==
     *        |   (tree instanceof SearchTree) && (tree != this) &&
     *        |   (! ((BinaryTreeImpl)tree).hasAsSubtree(this)) &&
     *        |   ( for each Object in (Object union {null}):
     *        |       if (tree.hasAsElement(object))
     *        |         then this.canHaveAsElement(object) ) &&
     *        |   ( for each comparable in Comparable:
     *        |       if (tree.hasAsElement(comparable))
     *        |         then this.getRootElement().compareTo(comparable) >= 0 )
     */
    @Override
    protected boolean canHaveAsLeftTree(BinaryTree tree) {
        if (!super.canHaveAsLeftTree(tree))
            return false;
        if (!(tree instanceof SearchTree))
            return false;
        Iterator treeIterator = tree.iterator();
        while (treeIterator.hasNext()) {
            Object nextElement = treeIterator.next();
            if (!this.canHaveAsElement(nextElement))
                return false;
            if (getRootElement().compareTo(nextElement) < 0)
                return false;
        }
        return true;
    }

    /**
     * Return the right subtree of this composed search tree.
     */
    @Override
    protected SearchTree getRightTree() {
        return (SearchTree) super.getRightTree();
    }

    /**
     * Check whether this composed search tree can have the given
     * binary tree as its right subtree.
     * 
     * @return  True if and only if the given binary tree is an
     *          effective search tree different from this composed
     *          search tree, and if the given binary tree does not
     *          have this composed search tree as a direct or
     *          indirect subtree, and if this composed search tree
     *          can have each of the elements in the given tree as
     *          an element, and if each of the elements in the given
     *          tree is greater than the element in the root of this
     *          composed binary tree.
     *        | result ==
     *        |   (tree instanceof SearchTree) && (tree != this) &&
     *        |   (! ((BinaryTreeImpl)tree).hasAsSubtree(this)) &&
     *        |   ( for each Object in (Object union {null}):
     *        |       if (tree.hasAsElement(object))
     *        |         then this.canHaveAsElement(object) ) &&
     *        |   ( for each comparable in Comparable:
     *        |       if (tree.hasAsElement(comparable))
     *        |         then this.getRootElement().compareTo(comparable) < 0 )
     */
    @Override
    protected boolean canHaveAsRightTree(BinaryTree tree) {
        if (!super.canHaveAsLeftTree(tree))
            return false;
        if (!(tree instanceof SearchTree))
            return false;
        Iterator treeIterator = tree.iterator();
        while (treeIterator.hasNext()) {
            Object nextElement = treeIterator.next();
            if (!this.canHaveAsElement(nextElement))
                return false;
            if (getRootElement().compareTo(nextElement) >= 0)
                return false;
        }
        return true;
    }

    /**
     * Check whether this composed search tree can have the given
     * trees as its left subtree, respectively as its right subtree.
     * 
     * @return  True if this composed search tree can have the
     *          given left tree as its left subtree, and if this
     *          composed search tree can have the given right
     *          tree as its right subtree, and if at least one of
     *          the given trees is not an empty tree; false
     *          otherwise.
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
     * Change this composed search into a leaf search tree, if it only has
     * one element.
     *
     * @return	A new leaf search tree, if this unsorted search binary tree has a
     *			single node; this tree otherwise.
     *	      | if (getNbElements() == 1)
     *		  |   then result instanceof LeafSearchTree
     *	      |   else result == this
     */
    @Override
    protected NonEmptySearchTree changeToLeafTree() {
        try {
            if (getLeftTree().isEmpty() && getRightTree().isEmpty())
                return new LeafSearchTree(getRootElement());
            else
                return this;
        }
        catch (IllegalElementException exc) {
            assert false;
            return null;
        }
    }

}