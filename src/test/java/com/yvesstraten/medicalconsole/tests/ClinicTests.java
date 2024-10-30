package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.facilities.Clinic;

public class ClinicTests {
  @Test
  public void firstVisitReturnsFalse() {
    Clinic clinic = new Clinic("Test clinic", 0.0, 0.0);
    Patient patient = new Patient("Test");

    assertFalse(clinic.visit(patient));
  }

  @Test
  public void registeredPatientVisitReturnsTrue() {
    Clinic clinic = new Clinic("Test clinic", 0.0, 0.0);
    Patient patient = new Patient("Test", 0.0, clinic);

    assertTrue(clinic.visit(patient));
  }

}
