package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.MedicalConsole;
import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.exceptions.NoHospitalsAvailableException;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import com.yvesstraten.medicalconsole.facilities.Procedure;

@DisplayName("Medical Console adding tests")
public class AddTests {
  @Test
  public void addingProcedureWithNoHospitalsThrows() {
    HealthService testService =
        new HealthService(
            "Test service", new ArrayList<MedicalFacility>(), new ArrayList<Patient>());
    ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());
    Scanner mockInput = new Scanner(input);

    assertThrows(
        NoHospitalsAvailableException.class,
        () -> MedicalConsole.addProcedure(testService, mockInput));
  }

  @Test
  public void addingProcedureWithHospital() throws NoHospitalsAvailableException {
    ArrayList<Procedure> expectedProcedures = new ArrayList<Procedure>();
    expectedProcedures.add(new Procedure(1, "TestName", "TestDesc", true, 300));

    ArrayList<MedicalFacility> facilities = new ArrayList<MedicalFacility>();
    facilities.add(new Hospital(0, "Test hospital"));

    HealthService testService =
        new HealthService("Test service", facilities, new ArrayList<Patient>());
    ByteArrayInputStream input =
        new ByteArrayInputStream("1\nTestName\nTestDesc\nyes\n300.0\n".getBytes());

    Scanner mockInput = new Scanner(input);

    MedicalConsole.addProcedure(testService, mockInput);
    assertEquals(
        expectedProcedures, ((Hospital) testService.getMedicalFacilities().get(0)).getProcedures());
  }
}
