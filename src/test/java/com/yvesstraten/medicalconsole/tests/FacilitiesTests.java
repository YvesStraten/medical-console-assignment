package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Facilities tests")
@SelectClasses({ClinicTests.class, HospitalTests.class})
public class FacilitiesTests {
  @Test
  public void shouldBeSameHashCode() {
    Clinic clinic = new Clinic(0, "test", 300, 0.3);
    Hospital hospital = new Hospital(1, "test");
    Procedure procedure = new Procedure(2, "Test Procedure", "Test procedure desc", false, 0.30);

    assertEquals(clinic.hashCode(), clinic.getId());
    assertEquals(hospital.hashCode(), hospital.getId());
    assertEquals(procedure.hashCode(), procedure.getId());
  }
}
