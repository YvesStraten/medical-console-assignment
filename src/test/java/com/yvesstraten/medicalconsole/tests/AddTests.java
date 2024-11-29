package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.operations.AddOperations;
import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.exceptions.NoHospitalsAvailableException;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import com.yvesstraten.medicalconsole.facilities.Procedure;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** This class contains all tests related to adding objects */
@DisplayName("Medical Console adding tests")
public class AddTests extends MedicalConsoleTest {
  /** Construct this test class */
  public AddTests() {
    super();
  }

  /**
   * This test tests that {@link NoHospitalsAvailableException} is thrown when no Hospitals are
   * available to add a procedure to
   */
  @Test
  public void addingProcedureWithNoHospitalsThrows() {
    HealthService testService =
        new HealthService();
    Scanner mockInput = new Scanner("");

    assertThrows(
        NoHospitalsAvailableException.class,
        () -> AddOperations.addProcedure(testService, mockInput));
  }

  /**
   * This test tests for procedure adding functionality
   *
   * @throws NoHospitalsAvailableException when a hospital is not successfully added
   */
  @Test
  public void addingProcedureWithHospital()
      throws NoHospitalsAvailableException {
    ArrayList<Procedure> expectedProcedures = 
        new ArrayList<Procedure>();
    expectedProcedures.add(
            new Procedure(
                1,
                "TestName",
                "TestDesc",
                true,
                300
            )
        );

    ArrayList<MedicalFacility> facilities = 
        new ArrayList<MedicalFacility>();
    facilities.add(new Hospital(0, "Test hospital"));

    HealthService testService =
        new HealthService("Test service",
            facilities,
            new ArrayList<Patient>());

    Scanner mockInput = new Scanner("1\nTestName\nTestDesc\nyes\n300.0\n");

    AddOperations.addProcedure(testService, mockInput);
    assertEquals(
        expectedProcedures, ((Hospital) testService.getMedicalFacilities().get(0)).getProcedures());
  }

  /** This test tests for hospital adding functionality */
  @Test
  public void hospitalIsCorrectlyAdded() {
    AddOperations.addHospital(testService, new Scanner("TestHospital\n"));
    List<Hospital> newHospitalList = testService.getHospitals().toList();
    assertEquals(
        new Hospital(testService.getIdDispenser().getLastDispensedId(), 
                "TestHospital"),
        newHospitalList.get(newHospitalList.size() - 1));
  }

  /** This test tests for clinic adding functionality */
  @Test
  public void clinicIsCorrectlyAdded() {
    AddOperations.addClinic(testService, new Scanner("TestClinic\n300\n0.3\n"));
    List<Clinic> newClinicList = testService.getClinics().toList();
    assertEquals(
        new Clinic(testService.getIdDispenser().getLastDispensedId(), "TestClinic", 300, 0.3),
        newClinicList.get(newClinicList.size() - 1));
  }

  /** This test tests for patient adding functionality */
  @Test
  public void patientIsCorrectlyAdded() {
    AddOperations.addPatient(testService, new Scanner("Mark\nyes\n"));
    List<Patient> newPatientList = testService.getPatients();
    assertEquals(
        new Patient(testService.getIdDispenser().getLastDispensedId(), "Mark", true),
        newPatientList.get(newPatientList.size() - 1));
  }
}
