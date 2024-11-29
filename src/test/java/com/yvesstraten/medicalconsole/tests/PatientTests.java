package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.comparators.PatientComparators;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

/** Test suite for all tests related to the {@code Patient} class */
@DisplayName("Patient tests")
public class PatientTests extends MedicalConsoleTest {
  /** Construct this test class */
  public PatientTests() {
    super();
  }

  /**
   * Test to check that the id returned by {@link Patient#hashCode()} is the same as the id of this
   * Patient
   */
  @Test
  public void shouldBeSameHashCode() {
    Patient patient = testService.getPatients().get(0);
    assertEquals(patient.getId(), patient.hashCode());
  }

  /**
   * Test to check that Patients are sorted by their natural order, that is their ids
   *
   * @see Patient
   */
  @Test
  public void comparableTest() {
    Patient patient = new Patient(0,
            "Mark",
            false,
            0.0,
            new Clinic(2, "Victory", 300, 0.3));
    Patient patient2 = new Patient(1,
            "Other",
            false,
            0.0,
            new Clinic(3, "Victory", 300, 0.3));
    patient2.setBalance(30.0);

    Patient[] patients = new Patient[] {patient2, patient};
    Patient[] expected = new Patient[] {patient, patient2};

    Arrays.sort(patients);

    assertArrayEquals(expected, patients);
  }

  /**
   * Test to check that equal patients are indeed treated as equal
   *
   * @see Patient
   */
  @Test
  public void equalsTest() {
    Patient patient = new Patient(0,
            "Mark",
            false,
            0.0,
            new Clinic(2, "Victory", 300, 0.3));
    Patient patient2 = new Patient(1,
            "Other",
            false,
            0.0,
            new Clinic(3, "Victory", 300, 0.3));

    assertTrue(patient.equals(patient));
    assertFalse(patient.equals(patient2));
  }

  /**
   * Test factory for all tests related to the sorting of Patient objects
   *
   * @return stream of tests
   * @see PatientComparators
   */
  @TestFactory
  public Stream<DynamicTest> comparatorTests() {
    Patient patient1 = new Patient(0, "Quentin", false, 100);
    Patient patient2 = new Patient(1, "Other", false, 600);
    List<Patient> facilities = List.of(patient1, patient2);
    Stream<SortingTest<Patient>> tests =
        Stream.of(
            new SortingTest<Patient>(
                "Sort by name", 
                        new PatientComparators
                            .SortByName(),
                        List.of(patient2, patient1)),
            new SortingTest<Patient>(
                "Sort by balance",
                new PatientComparators.SortByBalance(),
                List.of(patient2, patient1)));

    return tests.map(
        test ->
            dynamicTest(
                test.getName(),
                () ->
                    assertEquals(
                        test.getExpected(),
                        facilities
                                  .stream()
                                  .sorted(test
                                    .getComparator())
                                  .toList())));
  }
}
