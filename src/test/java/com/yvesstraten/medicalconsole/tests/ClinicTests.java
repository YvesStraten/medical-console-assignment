package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.facilities.Clinic;

public class ClinicTests {
  @Test
  public void firstVisitReturnsFalse() {
    Clinic clinic = new Clinic(0, "Test clinic", 0.0, 0.0);
    Patient patient = new Patient(1, "Test", false);

    assertFalse(clinic.visit(patient));
  }

  @Test
  public void registeredPatientVisitReturnsTrue() {
    Clinic clinic = new Clinic(0, "Test clinic", 0.0, 0.0);
    Patient patient = new Patient(1, "Test", false, 0.0, clinic);

    assertTrue(clinic.visit(patient));
  }

	@Test 
	public void patientVisitAddsBalance(){
    Clinic clinic = new Clinic(0, "Test clinic", 300, 1.2);
		double fee = clinic.getFee();
		double gapPercent = clinic.getGapPercent();

    Patient privatePatient = new Patient(1, "Test", true, 0.0, clinic);
    Patient publicPatient = new Patient(2, "Test", false, 0.0, clinic);

		clinic.visit(privatePatient);
		assertEquals(privatePatient.getBalance(), fee);

		clinic.visit(publicPatient);
		assertEquals(publicPatient.getBalance(), fee * gapPercent);
	}

}
