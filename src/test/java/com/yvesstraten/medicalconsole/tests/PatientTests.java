package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.PatientsSortedByName;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Test suite for all tests related to the {@code Patient} class */
@DisplayName("Patient tests")
public class PatientTests {
  @Test
  public void shouldBeSameHashCode() {
    Patient patient = new Patient("Mark", new Clinic("Victory", 300, 0.3));
    assertEquals(patient.hashCode(), patient.getId());
  }

  @Test
  public void comparableTest() {
    Patient patient = new Patient("Mark", new Clinic("Victory", 300, 0.3));
    Patient patient2 = new Patient("Other", new Clinic("Victory", 300, 0.3));
    patient2.setBalance(30.0);

    Patient[] patients = new Patient[] {patient2, patient};
    Patient[] expected = new Patient[] {patient, patient2};

    Arrays.sort(patients);

    assertArrayEquals(expected, patients);
  }

	@Test 
	public void sortByNameTest(){
    Patient patient = new Patient("Mark", new Clinic("Victory", 300, 0.3));
    Patient patient2 = new Patient("Other", new Clinic("Victory", 300, 0.3));
		Patient[] expected = new Patient[] { patient, patient2 };
		Patient[] actual = new Patient[] { patient2, patient };

		Arrays.sort(actual, new PatientsSortedByName());
		assertArrayEquals(expected, actual);
	}

  @Test
  public void equalsTest() {
    Patient patient = new Patient("Mark", new Clinic("Victory", 300, 0.3));
    Patient patient2 = new Patient("Other", new Clinic("Victory", 300, 0.3));

    assertTrue(patient.equals(patient));
    assertFalse(patient.equals(patient2));
  }
}
