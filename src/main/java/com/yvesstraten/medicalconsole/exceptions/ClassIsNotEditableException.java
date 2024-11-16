package com.yvesstraten.medicalconsole.exceptions;

import com.yvesstraten.medicalconsole.Editable;

/**
 * This exception is thrown when an attempt is made at editing a class at runtime, but that class is
 * not marked with the Editable decorator
 *
 * @author Yves Straten e2400068
 * @see Editable
 */
public class ClassIsNotEditableException extends Exception {
  /** Construct this exception with default error message */
  public ClassIsNotEditableException() {
    super("This object cannot be edited!");
  }

  /**
   * Construct this exception with a custom error message
   *
   * @param s custom error message
   */
  public ClassIsNotEditableException(String s) {
    super(s);
  }
}
