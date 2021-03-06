package chapter_7.course_implementation.ownings;

import java.math.BigInteger;

import be.kuleuven.cs.som.annotate.*;
import chapter_7.course_implementation.exceptions.*;
import chapter_7.course_implementation.persons.Person;

/**
 * A class of paintings as a special kind of ownable. In addition to
 * a value and an owner, paintings have a title and a painter.
 * 
 * @invar   The title of each painting must be a valid title for
 *          a painting.
 *        | isValidTitle(getTitle())
 * @note    For purposes of illustration, all additional properties
 *          ascribed to paintings are worked out in a nominal way.
 * 
 * @version 2.0
 * @author  Eric Steegmans
 */
public class Painting extends Ownable {

    /**
     * Initialize this new painting with given owner, given value,
     * given title and given painter.
     *
     * @param  owner
     *         The owner of this new painting.
     * @param  value
     *         The value of this new painting.
     * @param  title
     *         The title of this new painting.
     * @param  painter
     *         The painter of this new painting.
     * @effect This new painting is initialized as a new ownable with
     *         given owner and given value.
     *       | super(owner,value)
     * @effect The title of this new painting is set to the given title.
     *       | setTitle(title)
     * @effect The painter of this new painting is set to the given painter.
     *	     | setPainter(painter)
     */
    @Raw
    public Painting(Person owner, BigInteger value, String title, Person painter)
            throws IllegalOwnerException, IllegalValueException {
        super(owner, value);
        setTitle(title);
        setPainter(painter);
    }

    /**
     * Initialize this new painting with given title, having no owner,
     * a value of 0, and no painter.
     *
     * @param  title
     *         The title of this new painting.
     * @effect This new painting is initialized as a new ownable with
     *         no owner and a value of 0.
     *       | super()
     * @effect The title of this new painting is set to the given title.
     *       | setTitle(title)
     * @post   This new painting does not have a painter.
     *       | new.getPainter() == null
     */
    @Raw
    public Painting(String title) {
        setTitle(title);
    }

    /**
     * Return the title of this painting.
     */
    @Basic
    public String getTitle() {
        return this.title;
    }

    /**
     * Check whether this painting can have the given title
     * as its title.
     *
     * @param  title
     *         The title to check.
     * @return True if and only if the given title is effective
     *         and not empty.
     *       | result == (title != null) && (title.length() > 0)
     */
    public static boolean isValidTitle(String title) {
        return (title != null) && (title.length() > 0);
    }

    /**
     * Set the title of this painting to the given title.
     *
     * @param  title
     *         The new title for this painting.
     * @pre    This painting may not have been terminated.
     *       | ! isTerminated()
     * @pre    The given title is a valid title for any painting.
     *       | isValidTitle(title)
     * @post   The title of this painting is equal to the
     *         given title.
     *       | new.getTitle().equals(title)
     */
    @Raw
    public void setTitle(String title) {
        assert ! isTerminated();
        assert isValidTitle(title);
        this.title = title;
    }

    /**
     * Variable referencing the title of this painting.
     */
    private String title = "Untitled";

    /**
     * Return the painter of this painting.
     *   A null reference is returned if the painter of this
     *   painting is not known.
     */
    @Basic
    public Person getPainter() {
        return this.painter;
    }

    /**
     * Set the painter of this painting to the given painter.
     *
     * @param  painter
     *         The new painter for this painting.
     * @pre    This painting may not have been terminated yet.
     *       | ! isTerminated()
     * @post   The painter of this painting is the same as the
     *         given painter.
     *       | new.getPainter() == painter
     */
    @Raw
    public void setPainter(Person painter) {
        assert ! isTerminated();
        this.painter = painter;
    }

    /**
     * Variable referencing the painter of this painting.
     */
    private Person painter = null;

}