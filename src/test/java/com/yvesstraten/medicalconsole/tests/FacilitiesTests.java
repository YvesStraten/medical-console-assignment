package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Test suite for all tests related to facilities
 *
 * @see MedicalFacility
 * @see Clinic
 * @see Hospital
 * @author Yves Straten e2400068
 */
@Suite
@SuiteDisplayName("Facilities tests")
@SelectClasses({ClinicTests.class, HospitalTests.class})
public class FacilitiesTests extends MedicalConsoleTest {
  /** Construct this test class */
  public FacilitiesTests() {
    super();
  }

  /**
   * This test tests whether medicalfacilities have the correct hashcode,
     * that is their ids are used
   */
  @Test
  public void shouldBeSameHashCode() {
    List<Integer> fetchedHashCodes =
        testService.getMedicalFacilitiesStream().map(Object::hashCode).toList();

    List<Integer> fetchedIds =
        testService.getMedicalFacilitiesStream().map(MedicalFacility::getId).toList();
    assertEquals(fetchedHashCodes, fetchedIds);
  }
}
