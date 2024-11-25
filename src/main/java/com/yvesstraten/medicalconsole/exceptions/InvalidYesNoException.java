package com.yvesstraten.medicalconsole.exceptions;

/**
 * This exception is thrown when invalid input is given to a yes no prompt
 *
 * @author Yves Straten e2400068
 */
public class InvalidYesNoException extends Exception {
  /** Construct this exception with a default error message */
  public InvalidYesNoException() {
    super("This is not a yes/no answer, please input yes/no");
  }

  /**
   * Construct this exception with a custom error message
   *
   * @param s custom error message
   */
  public InvalidYesNoException(String s) {
    super(s);
  }
}
