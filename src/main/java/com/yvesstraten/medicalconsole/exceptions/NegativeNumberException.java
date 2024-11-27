package com.yvesstraten.medicalconsole.exceptions;

/** 
 * This exception is thrown when a negative number, both decimal 
 * and integer is input, in methods which do not support it 
 *
 * @author Yves Straten e2400068
*/
public class NegativeNumberException extends Exception {
	/** Types of numbers */
  public enum NumTypes {
    /** Integer */
    INTEGER,
		/** Decimal */
    DECIMAL
  }

  /** Construct this exception with custom error message
	 * @param type type of number
	*/
  public NegativeNumberException(NumTypes type) {
    super("Wrong input! Please input a non-negative " + type.toString().toLowerCase() + "!");
  }

  /** Construct this exception with default error message */
  public NegativeNumberException() {
    super("Wrong input! Please input a non-negative number!");
  }

  /** Construct this exception with a custom error message
	 * @param s custom message 
	*/
  public NegativeNumberException(String s) {
    super(s);
  }
}
