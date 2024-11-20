package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.comparators.MedicalFacilitiesComparators;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

/** This class holds all tests related to a {@link HealthService} object */
@DisplayName("HealthService Tests")
public class HealthServiceTests {
  /** Test to check that id is sequential */
  @Test
  public void idIsSequential() {
    HealthService testService =
        new HealthService("Test", new ArrayList<MedicalFacility>(), new ArrayList<Patient>());

    assertEquals(1, testService.iterator().next());
    assertEquals(2, testService.iterator().next());
  }

  /** Test factory for all tests related to the sorting of {@link HealthService} objects */
  @TestFactory
  public Stream<DynamicTest> comparatorTests() {
    MedicalFacility facility1 = new Hospital(0, "Zeta");
    MedicalFacility facility2 = new Clinic(1, "Beta", 300, 0.3);
    List<MedicalFacility> facilities = List.of(facility1, facility2);
    Stream<SortingTest<MedicalFacility>> tests =
        Stream.of(
            new SortingTest<MedicalFacility>(
                "Sort by name",
                new MedicalFacilitiesComparators.SortByName(),
                List.of(facility2, facility1)),
            new SortingTest<MedicalFacility>(
                "Sort by hospital",
                new MedicalFacilitiesComparators.SortByHospital(),
                List.of(facility1, facility2)),
            new SortingTest<MedicalFacility>(
                "Sort by hospital",
                new MedicalFacilitiesComparators.SortByClinic(),
                List.of(facility2, facility1)));

    return tests.map(
        test ->
            dynamicTest(
                test.getName(),
                () ->
                    assertEquals(
                        test.getExpected(),
                        facilities.stream().sorted(test.getComparator()).toList())));
  }
}
