package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.yvesstraten.medicalconsole.MedicalConsole;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import com.yvesstraten.medicalconsole.operations.DeleteOperations;

import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * This class contains all tests related to deleting
 *
 * @see MedicalConsole
 */
@DisplayName("Medical console delete tests")
public class DeleteTests extends MedicalConsoleTest {
  /** Construct this test class */
  public DeleteTests() {
    super();
  }

  /** This test tests whether deleting a patient actually deletes */
  @Test
  public void deletePatientDeletes() {
    int priorSize = testService.getPatients().size();
    Scanner mockInput = new Scanner("1\n");

    DeleteOperations.deletePatient(testService, mockInput);
    assertEquals(priorSize - 1, testService.getPatients().size());
  }

  /** This test tests whether deleting a facility actually deletes */
  @Test
  public void deleteFacilityDeletes() {
    int priorNum = testService.getMedicalFacilities().size();
    Scanner mockInput = new Scanner("2\n");

    DeleteOperations.deleteFacility(testService, mockInput);
    assertEquals(priorNum - 1, testService.getMedicalFacilities().size());

    priorNum = testService.getMedicalFacilities().size();
    Scanner nextMock = new Scanner("1\nyes\n");
    DeleteOperations.deleteFacility(testService, nextMock);

    assertEquals(priorNum - 1, testService.getMedicalFacilities().size());
  }

  /** This test tests whether deleting a procedure actually deletes */
  @Test
  public void deleteProcedureDeletes() {
    List<Procedure> procedures =
        testService.getHospitals()
        .flatMap(Hospital::getProceduresStream)
        .toList();
    int priorSize = procedures.size();
    Scanner mockInput = new Scanner("1\n");

    DeleteOperations.deleteProcedure(testService, mockInput);
    assertEquals(
        priorSize - 1,
        testService.getHospitals()
            .flatMap(Hospital::getProceduresStream)
            .toList()
            .size());
  }
}
