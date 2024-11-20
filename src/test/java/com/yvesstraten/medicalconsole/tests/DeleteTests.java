package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.MedicalConsole;
import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import com.yvesstraten.medicalconsole.facilities.Procedure;

@DisplayName("Medical console delete tests")
public class DeleteTests {
  @Test
  public void deletePatientDeletes() {
    ArrayList<Patient> patients = new ArrayList<Patient>();
    patients.add(new Patient(0, "Test patient", true));

    HealthService testService =
        new HealthService("Test service", new ArrayList<MedicalFacility>(), patients);
    ByteArrayInputStream input = new ByteArrayInputStream("1\n".getBytes());
    Scanner mockInput = new Scanner(input);

    MedicalConsole.deletePatient(testService, mockInput);
    assertEquals(new ArrayList<Patient>(), testService.getPatients());
  }

  @Test
  public void deleteFacilityDeletes() {
    ArrayList<Procedure> procedures = new ArrayList<Procedure>();
    procedures.add(new Procedure(2, "Test proc", "Desc", true, 300));
    Clinic clinic1 = new Clinic(0, "Test clinic", 300, 0.2);
    Hospital hospital1 = new Hospital(1, "Test", procedures);
    ArrayList<MedicalFacility> facilities = new ArrayList<MedicalFacility>();
    facilities.add(clinic1);
    facilities.add(hospital1);

    HealthService testService =
        new HealthService("Test service", facilities, new ArrayList<Patient>());
    ByteArrayInputStream input = new ByteArrayInputStream("1\n".getBytes());
    Scanner mockInput = new Scanner(input);

    MedicalConsole.deleteFacility(testService, mockInput);
    assertEquals(List.of(hospital1), testService.getMedicalFacilities());
    mockInput.close();

    ByteArrayInputStream nextInput = new ByteArrayInputStream("1\nyes\n".getBytes());
    Scanner nextMock = new Scanner(nextInput);
    MedicalConsole.deleteFacility(testService, nextMock);

    assertEquals(List.of(), testService.getMedicalFacilities());
    nextMock.close();
  }

}
