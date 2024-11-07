package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.InvalidOptionException;
import com.yvesstraten.medicalconsole.InvalidYesNoException;
import com.yvesstraten.medicalconsole.MedicalConsole;
import com.yvesstraten.medicalconsole.NoHospitalsAvailableException;
import com.yvesstraten.medicalconsole.Patient;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Medical console tests")
public class MedicalConsoleTests {
  @ParameterizedTest
  @ValueSource(ints = {-1, 7})
  public void invalidOptionShouldThrow(int selectedOption) {
    String options =
        "Add a service"
            + "List all services"
            + "Delete a service"
            + "Simulate patient visit"
            + "Operate"
            + "Quit";

    System.out.println(selectedOption);

    InvalidOptionException e =
        assertThrows(
            InvalidOptionException.class,
            () -> MedicalConsole.checkChosenOption(selectedOption, List.of(options.split("\n"))));

    assertEquals(
        String.format("Invalid option please select option [%d-%d]", 1, options.split("\n").length),
        e.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"l", "z"})
  public void invalidYesNoInputShouldThrow(String input) {
    assertThrows(
        InvalidYesNoException.class,
        () -> {
          MedicalConsole.testYesNo(input);
        });
  }

  private static Stream<Arguments> provideStringsForYesNo() {
    return Stream.of(
        Arguments.of("y", true),
        Arguments.of("yes", true),
        Arguments.of("n", false),
        Arguments.of("no", false));
  }

  @ParameterizedTest
  @MethodSource("provideStringsForYesNo")
  public void validYesNoReturnsBoolean(String input, boolean expected)
      throws InvalidYesNoException {
    boolean returned = MedicalConsole.testYesNo(input);

    assertEquals(expected, returned);
  }

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
  public void addingProcedureWithHospital()
      throws InvalidYesNoException, InvalidOptionException, NoHospitalsAvailableException {
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

  @Test
  public void emptyStreamShouldReturnNoDetailsString() {
    Stream<Object> stream = Stream.of();
    String result = MedicalConsole.getObjectStreamDetails(stream, "test");

    assertEquals("There are no test for this service", result);
  }

  @ParameterizedTest
  @MethodSource("objectStreams")
  public <T> void filledStreamShouldReturnDetailsString(List<T> listToTest, String name) {
    StringBuilder builder = new StringBuilder("The following " + name + " are available \n");

    listToTest.stream().forEach((item) -> builder.append(item.toString()).append("\n"));
    String result = MedicalConsole.getObjectStreamDetails(listToTest.stream(), name);

    assertEquals(builder.toString(), result);
  }

  public static Stream<Arguments> objectStreams() {
    return Stream.of(
        Arguments.of(
            List.of(new Patient(0, "Mark", false), new Patient(1, "John", true)), "patients"),
        Arguments.of(
            List.of(new Clinic(0, "Victoria", 400, 0.3), new Clinic(1, "Saint Croix", 1000, 1.2)),
            "facilities"));
  }

  @ParameterizedTest
  @MethodSource("operationCostsArguments")
  public void gettingOperationCostReturnsProperResults(
      Patient patient, Procedure procedure, double expected) {
    double result = MedicalConsole.getOperationCost(patient, procedure);

    assertEquals(expected, result);
  }

  public static Stream<Arguments> operationCostsArguments() {
    return Stream.of(
        // Private, elective
        Arguments.of(
            new Patient(0, "TestPatient", true),
            new Procedure(1, "TestProcedure", "TestDesc", true, 300),
            2000),
        // Private, non-elective
        Arguments.of(
            new Patient(0, "TestPatient", true),
            new Procedure(1, "TestProcedure", "TestDesc", false, 300),
            1000),
        // Public, elective
        Arguments.of(
            new Patient(0, "TestPatient", false),
            new Procedure(1, "TestProcedure", "TestDesc", true, 300),
            300),
        // Public, non-elective
        Arguments.of(
            new Patient(0, "TestPatient", false),
            new Procedure(1, "TestProcedure", "TestDesc", false, 300),
            0));
  }

  @Test
  public void deletePatientDeletes() throws InvalidOptionException {
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
  public void invalidPatientDeletionChoiceThrows() {
    ArrayList<Patient> patients = new ArrayList<Patient>();
    patients.add(new Patient(0, "Test patient", true));

    HealthService testService =
        new HealthService("Test service", new ArrayList<MedicalFacility>(), patients);
    ByteArrayInputStream input = new ByteArrayInputStream("2\n".getBytes());
    Scanner mockInput = new Scanner(input);

    assertThrows(
        InvalidOptionException.class, () -> MedicalConsole.deletePatient(testService, mockInput));
  }

	@Test
  public void deleteFacilityDeletes() throws InvalidOptionException, InvalidYesNoException {
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
