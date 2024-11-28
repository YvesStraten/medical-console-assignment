package com.yvesstraten.medicalconsole.exceptions;

import com.yvesstraten.medicalconsole.Patient;

/**
 * This exception is thrown when this patient has not yet visited a hospital and the hospital that
 * the patient is to operated in not the same as the last visited hospital of the patient
 *
 * @see Patient#isInThisHospital(com.yvesstraten.medicalconsole.facilities.Hospital)
 */
public class WrongHospitalException extends Exception {
  /**
   * Construct this exception with an error message
   *
   * @param s error message
   */
  public WrongHospitalException(String s) {
    super(s);
  }

  /** Construct this exception without an error message */
  public WrongHospitalException() {
    super();
  }
}
