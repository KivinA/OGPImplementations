package extra.trees;

import java.util.*;

import be.kuleuven.cs.som.annotate.*;
import extra.trees.exceptions.*;

/**
 * A class of empty binary trees.
 *     An empty binary tree has no nodes in which elements are stored.
 *
 * @invar   Each empty binary tree has no occurrences of any object
 *          (including the null reference).
 *        | for each element in (Object union {null}):
 *        |   (getNbOccurrences(element) == 0)
 * @note    This invariant is not really needed, because it follows
 *          from the general invariant for binary trees and from the
 *          fact that empty binary trees can only have 0 occurrences
 *          of each object.
 * 
 * @version 2.0
 * @author  Eric Steegmans
 */
public abstract class EmptyBinaryTree implements BinaryTree {

    /**
     * Return the number of occurrences of the given element in this
     * empty binary tree.
     *
     * @return Always 0.
     *       | result == 0
     */
	@Raw
    @Override
    public final int getNbOccurrencesOf(Object element) {
        return 0;
    }

    /**
     * Check whether this empty binary tree can have the given number of
     * occurrences of the given element as the number of occurrences of
     * that element in it.
     * 
     * @return True if and only if the given number of occurrences is zero.
     *       | result == (nbOccurrences == 0)
     */
    @Raw
    @Override
    public final boolean canHaveAsNbOccursOfElement(int nbOccurrences,
            Object element) {
        return (nbOccurrences == 0);
    }

    /**
     * Check whether this empty binary tree has the given element as one of
     * its elements.
     */
    @Override
    public final boolean hasAsElement(Object element) {
        // Re-implementation for reasons of efficiency.
        return false;
    }

    /**
     * Return the total number of elements in this empty binary tree.
     *
     * @return Always 0.
     *       | result == 0
     */
    @Raw
    @Override
    public final int getNbElements() {
        return 0;
    }

    /**
     * Check whether this empty binary tree has no elements.
     *
     * @return Always true.
     *       | result == true
     */
    @Override
    public final boolean isEmpty() {
        // Re-implementation for reasons of efficiency.
        return true;
    }

    /**
     * Add the given element to this empty binary tree.
     */
    @Override
    public abstract LeafBinaryTree addElement(Object element)
            throws IllegalElementException;

    /**
     * Remove one occurrence of the given element from this empty
     * binary tree.
     *
     * @throws IllegalElementException
     *         Always.
     *       | true
     */
    @Override
    public BinaryTree removeElement(Object element)
            throws IllegalElementException {
        throw new IllegalElementException(element, this);
    }

    /**
     * Return a clone of this empty binary tree.
     * 
     * @return The resulting binary tree is the same as this empty binary
     *         tree.
     *       | result == this
     */
    @Override
    public final EmptyBinaryTree clone() {
        return this;
    }

    /**
     * Return an enumerator returning all elements in this empty
     * binary tree one by one.
     */
    @Override
    public Iterator<Object> iterator() {
        return new Iterator<Object>() {

            public boolean hasNext() {
                return false;
            }

            public Object next() throws NoSuchElementException {
                throw new NoSuchElementException();
            }

        };
    }

    /**
     * Check whether this empty binary tree has the given tree as a direct
     * or indirect subtree.
     * 
     * @return Always false.
     *       | result == false 
     */
    @Override
    public boolean hasAsSubTree(BinaryTree tree) {
        return false;
    }

}