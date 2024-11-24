package com.yvesstraten.medicalconsole.exceptions;

import com.yvesstraten.medicalconsole.HealthService;

/**
 * This exception is thrown when no hospitals have yet been added to the {@link HealthService} but
 * the user is trying to delete a procedure
 *
 * @author Yves Straten e2400068
 * @see com.yvesstraten.medicalconsole.facilities.Hospital
 */
public class NoHospitalsAvailableException extends Exception {
  /**
   * Construct this exception with an error message
   *
   * @param s error message
   */
  public NoHospitalsAvailableException(String s) {
    super(s);
  }

  /** Construct this exception with a predefined error message */
  public NoHospitalsAvailableException() {
    super("There are no hospitals available! Please add one first");
  }
}
