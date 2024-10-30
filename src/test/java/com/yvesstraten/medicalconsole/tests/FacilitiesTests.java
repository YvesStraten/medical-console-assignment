package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.Procedure;

@Suite
@SuiteDisplayName("Facilities tests")
@SelectClasses({ FacilitiesTests.class, ClinicTests.class })
public class FacilitiesTests {
  @Test
  public void shouldBeSameHashCode() {
    Clinic clinic = new Clinic("test", 300, 0.3);
    Hospital hospital = new Hospital("test");
    Procedure procedure = new Procedure();

    assertEquals(clinic.hashCode(), clinic.getId());
    assertEquals(hospital.hashCode(), hospital.getId());
    assertEquals(procedure.hashCode(), procedure.getId());
  }
}
