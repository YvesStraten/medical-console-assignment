package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.yvesstraten.medicalconsole.MedicalConsole;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.Procedure;

@DisplayName("Medical console delete tests")
public class DeleteTests extends MedicalConsoleTest {
  @Test
  public void deletePatientDeletes() {
		int priorSize = testService.getPatients().size();
    ByteArrayInputStream input = new ByteArrayInputStream("1\n".getBytes());
    Scanner mockInput = new Scanner(input);

    MedicalConsole.deletePatient(testService, mockInput);
    assertEquals(priorSize - 1, testService.getPatients().size());
  }

  @Test
  public void deleteFacilityDeletes() {
		int priorNum = testService.getMedicalFacilities().size();
    ByteArrayInputStream input = new ByteArrayInputStream("2\n".getBytes());
    Scanner mockInput = new Scanner(input);

    MedicalConsole.deleteFacility(testService, mockInput);
    assertEquals(priorNum - 1, testService.getMedicalFacilities().size());

		priorNum = testService.getMedicalFacilities().size();
    ByteArrayInputStream nextInput = new ByteArrayInputStream("1\nyes\n".getBytes());
    Scanner nextMock = new Scanner(nextInput);
    MedicalConsole.deleteFacility(testService, nextMock);

    assertEquals(priorNum - 1, testService.getMedicalFacilities().size());
  }

	/** 
	 * This test tests whether deleting a procedure actually deletes
	*/
  @Test
  public void deleteProcedureDeletes() {
		List<Procedure> procedures = testService.getHospitals().flatMap(Hospital::getProceduresStream).toList();
		int priorSize = procedures.size();
    Scanner mockInput = new Scanner("1\n");

    MedicalConsole.deleteProcedure(testService, mockInput);
    assertEquals(priorSize - 1, testService.getHospitals().flatMap(Hospital::getProceduresStream).toList().size());
  }
}
