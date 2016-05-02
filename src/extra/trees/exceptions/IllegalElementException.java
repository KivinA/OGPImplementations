package extra.trees.exceptions;

import be.kuleuven.cs.som.annotate.*;
import extra.trees.BinaryTree;

/**
 * A class for signaling illegal element exceptions.
 * 
 * @version  2.0
 * @author   Eric Steegmans
 */
public class IllegalElementException extends RuntimeException {

	/**
	 * Initialize this new illegal element exception exception with given element
	 * and given binary tree.
	 *
	 * @param   element
	 *          The element for this new illegal element exception exception.
	 * @param   binaryTree
	 *          The binary tree for this new illegal element exception exception.
	 * @post    The element for this new illegal element exception exception
	 *          is equal to the given element.
	 *          | new.getElement() == element
	 * @post    The binary tree for this new illegal element exception exception
	 *          is equal to the given binary tree.
	 *          | new.getBinaryTree() == binaryTree
	 */
	@Raw
	public IllegalElementException(Object element, BinaryTree binaryTree) {
		this.element = element;
		this.binaryTree = binaryTree;
	}

	/**
	 * Return the element of this illegal element exception exception.
	 */
	@Basic
	@Immutable
	public Object getElement() {
		return this.element;
	}

	/**
	 * Variable registering the element of this illegal element exception exception.
	 */
	private final Object element;

	/**
	 * Return the binary tree of this illegal element exception exception.
	 */
	@Raw
	@Immutable
	public BinaryTree getBinaryTree() {
		return this.binaryTree;
	}

	/**
	 * Variable registering the binary tree of this illegal element exception exception.
	 */
	private final BinaryTree binaryTree;

	/**
	 * The Java API strongly recommends to explicitly define a version
	 * number for classes that implement the interface Serializable.
	 * At this stage, that aspect is of no concern to us. 
	 */
	private static final long serialVersionUID = 2003001L;

}