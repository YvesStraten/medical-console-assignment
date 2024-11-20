package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.yvesstraten.medicalconsole.Input;
import com.yvesstraten.medicalconsole.MedicalConsole;
import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.exceptions.InvalidOptionException;
import com.yvesstraten.medicalconsole.exceptions.InvalidYesNoException;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Medical console tests")
@SelectClasses({AddTests.class, DeleteTests.class, EditTests.class})
public class MedicalConsoleTests {
  @ParameterizedTest
	@MethodSource("invalidOptions")
  public void invalidOptionShouldThrow(int selectedOption, int maxOption) {
    InvalidOptionException e =
        assertThrows(
            InvalidOptionException.class,
            () -> Input.checkOption(selectedOption, maxOption));

    assertEquals(
        String.format("Invalid option please select option [%d-%d]", 1, maxOption),
        e.getMessage());
  }

	public static Stream<Arguments> invalidOptions() {
		return Stream.of(Arguments.of(-1, 1), Arguments.of(6, 5));
	}

  @ParameterizedTest
  @ValueSource(strings = {"l", "z"})
  public void invalidYesNoInputShouldThrow(String input) {
    assertThrows(
        InvalidYesNoException.class,
        () -> {
          Input.testYesNo(input);
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
    boolean returned = Input.testYesNo(input);

    assertEquals(expected, returned);
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
    double result = Hospital.getOperationCost(patient, procedure);

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

}
