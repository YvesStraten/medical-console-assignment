package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import com.yvesstraten.medicalconsole.Editable;
import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.MedicalConsole;
import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.exceptions.ClassIsNotEditableException;
import com.yvesstraten.medicalconsole.exceptions.InvalidOptionException;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

@DisplayName("Edit Tests")
public class EditTests extends MedicalConsoleTest {
  @Test
  public void attemptEditNoEditableThrows() {
    class ClassWithNoEditable {}
    ClassWithNoEditable test = new ClassWithNoEditable();
    ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());
    Scanner stdin = new Scanner(input);
    assertThrows(ClassIsNotEditableException.class, () -> MedicalConsole.attemptEdit(test, stdin));
  }

  @Test
  public void attemptEditWrongSetterThrows() {
    class TestClassWithWrongSetter {
      @Editable private String testField;

      public void setTest(String testField) {
        this.testField = testField;
      }
    }

    TestClassWithWrongSetter test = new TestClassWithWrongSetter();
    ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());
    Scanner stdin = new Scanner(input);
    assertThrows(RuntimeException.class, () -> MedicalConsole.attemptEdit(test, stdin));
  }

	/** 
	 * Test factory for all tests related to editing 
	 * @return stream of tests
	*/
  @TestFactory
  public Stream<DynamicTest> editTests() {
    List<String> inputs =
        List.of(
            "Service\n",
            "Croix\n0.5\n",
            "Croix\n300\n0.4\n",
            "Mark\nyes\n300\n",
            "TestProc\nTestDesc\nyes\n300\n");

    List<Scanner> scanners =
        inputs.stream()
            .map(Scanner::new)
            .toList();

    return Stream.of(
        dynamicTest(
            "Edit service",
            () -> {
              MedicalConsole.attemptEdit(testService, scanners.get(0));
              assertEquals(testService.getName(), "Service");
            }),
        dynamicTest(
            "Edit hospital",
            () -> {
              Hospital hospital = testService.getHospitals().toList().get(0);
              MedicalConsole.attemptEdit(hospital, scanners.get(1));
              Hospital toCompare = new Hospital(hospital.getId(), "Croix");
              toCompare.setProbAdmit(0.5);
              assertEquals(toCompare, hospital);
            }),
        dynamicTest(
            "Edit clinic",
            () -> {
              Clinic clinic = testService.getClinics().toList().get(0);
              MedicalConsole.attemptEdit(clinic, scanners.get(2));

              assertEquals(new Clinic(clinic.getId(), "Croix", 300, 0.3), clinic);
            }),
        dynamicTest(
            "Edit Patient",
            () -> {
              Patient patient = testService.getPatients().get(0);
              MedicalConsole.attemptEdit(patient, scanners.get(3));

              assertEquals(new Patient(patient.getId(), "Mark", true, 300), patient);
            }),
        dynamicTest(
            "Edit Procedure",
            () -> {
              Procedure procedure =
                  testService.getHospitals().flatMap(Hospital::getProceduresStream).toList().get(0);
              MedicalConsole.attemptEdit(procedure, scanners.get(4));

              assertEquals(
                  new Procedure(procedure.getId(), "TestProc", "TestDesc", true, 300), procedure);
            }));
  }
}
