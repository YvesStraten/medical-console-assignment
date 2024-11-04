package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.InvalidOptionException;
import com.yvesstraten.medicalconsole.InvalidYesNoException;
import com.yvesstraten.medicalconsole.MedicalConsole;
import com.yvesstraten.medicalconsole.NoHospitalsAvailableException;
import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import com.yvesstraten.medicalconsole.facilities.Procedure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.ValueSources;

@DisplayName("Medical console tests")
public class MedicalConsoleTests {
  @ParameterizedTest
  @ValueSource(ints = {-1, 7})
  public void invalidOptionShouldThrow(int selectedOption) {
    String[] options = {
      new String("Add a service"),
      new String("List all services"),
      new String("Delete a service"),
      new String("Simulate patient visit"),
      new String("Operate"),
      new String("Quit")
    };

    InvalidOptionException e =
        assertThrows(
            InvalidOptionException.class,
            () -> {
              MedicalConsole.checkChosenOption(selectedOption, options);
            });

    assertEquals(
        String.format("Invalid option please select option [%d-%d]", 1, options.length),
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
  public void validYesNoReturnsBoolean(String input, boolean expected) throws InvalidYesNoException {
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
        NoHospitalsAvailableException.class, () -> MedicalConsole.addProcedure(testService, mockInput));
  }

	// TODO: Not right output, investigate
	@Test
  public void addingProcedureWithHospital() {
		ArrayList<Procedure> expectedProcedures = new ArrayList<Procedure>();
		expectedProcedures.add(new Procedure(1, "TestName", "TestDesc", true, 300));

		ArrayList<MedicalFacility> facilities = new ArrayList<MedicalFacility>();
		facilities.add(new Hospital(0, "Test hospital"));

    HealthService testService =
        new HealthService(
            "Test service", facilities, new ArrayList<Patient>());
    ByteArrayInputStream input = new ByteArrayInputStream("3\n 1\n TestName\n TestDesc\n yes\n 300.0\n".getBytes());
    Scanner mockInput = new Scanner(input);

    try {
      MedicalConsole.addObject(testService, mockInput);
			assertEquals(expectedProcedures, ((Hospital) testService.getMedicalFacilities().get(0)).getProcedures());

    } catch (Exception e) {
			System.err.println(e.toString());
    }
  }
}
