package com.yvesstraten.medicalconsole.exceptions;

/** 
 * This exception is thrown when invalid input is given to a number prompt 
 * @author Yves Straten e2400068
*/
public class InvalidOptionException extends Exception {
	/**  
	 * Construct this exception with a default error message 
	*/
  public InvalidOptionException() {
    super("This is a number prompt, please input a number");
  }

	/**  
	 * Construct this exception with a custom error message 
	 * @param s custom error message
	*/
  public InvalidOptionException(String s) {
    super(s);
  }
}
