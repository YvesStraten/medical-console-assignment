package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Test suite for all tests related to a Hospital
 *
 * @see Hospital
 * @see Procedure
 */
@Suite
@SuiteDisplayName("Hospital Tests")
@SelectClasses({ProcedureTests.class})
public class HospitalTests {
  /** Construct this test class */
  public HospitalTests() {}

  /**
   * This test tests whether a patient visiting a hospital behaves correctly
   *
   * @see MedicalFacility
   */
  @RepeatedTest(5)
  public void visitBehavesCorrectly() {
    Hospital hospital = new Hospital(0, "Test");
    Patient patient = new Patient(1, "Test patient", false, 0.0, null);
    boolean success = hospital.visit(patient);

    if (success) {
      assertEquals(hospital, patient.getCurrentFacility());
    } else {
      assertNull(patient.getCurrentFacility());
    }
  }
}
