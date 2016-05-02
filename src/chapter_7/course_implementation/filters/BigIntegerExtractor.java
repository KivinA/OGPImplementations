package chapter_7.course_implementation.filters;

import java.math.BigInteger;

/**
 * A class for extracting big integer values out of objects.
 * 
 * @version 2.0
 * @author  Eric Steegmans
 * 
 * @note    This class is better defined as a generic interface,
 *          parameterized in the type of objects from which
 *          a big integer must be extracted. At this stage in
 *          the course, we do not have enough information on
 *          how to define interfaces nor how to define generic
 *          types in Java.
 */
public abstract class BigIntegerExtractor {
	
	/**
	 * Return the value resulting from applying this big integer extractor to
	 * the given object.
	 * 
	 * @param  object
	 *         The object to evaluate.
	 * @return The resulting value is effective.
	 *       | result != null
	 */
	public abstract BigInteger getValueFor(Object object); 

}
