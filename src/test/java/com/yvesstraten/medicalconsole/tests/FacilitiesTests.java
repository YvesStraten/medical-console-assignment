package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Facilities tests")
@SelectClasses({ClinicTests.class, HospitalTests.class})
public class FacilitiesTests extends MedicalConsoleTest {
  @Test
  public void shouldBeSameHashCode() {
		List<Integer> fetchedHashCodes = testService.getMedicalFacilitiesStream().map(Object::hashCode).toList();

		List<Integer> fetchedIds = testService.getMedicalFacilitiesStream().map(MedicalFacility::getId).toList();
    assertEquals(fetchedHashCodes, fetchedIds);
  }
}
