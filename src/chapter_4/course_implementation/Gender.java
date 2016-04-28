package chapter_4.course_implementation;
/**
 * An enumeration of genders.
 *    In its current definition, the class only distinguishes between
 *    the male gender and the female gender.
 * 
 * @version  2.0
 * @author   Eric Steegmans
 */
public enum Gender {

	MALE {
		
		/**
		 * Return the symbol representing the male gender.
	     *
		 * @return The symbol with unicode U+2642.
		 *       | result == '\u2642'
		 */
		public char getSymbol() {
			return '\u2642';
		}
		
	},
	
	FEMALE {

		/**
		 * Return the symbol representing the female gender.
	     *
		 * @return The symbol with unicode U+2640.
		 *       | result == '\u2640'
		 */
		public char getSymbol() {
			return '\u2640';
		}
	};
	
	/**
	 * Return the symbol representing this gender.
     *
	 * @note    An enumeration may define abstract methods (i.e.
	 *          methods without a body). Each element of the
	 *          enumeration must then supply its own body.
	 */
	public abstract char getSymbol();
	
}