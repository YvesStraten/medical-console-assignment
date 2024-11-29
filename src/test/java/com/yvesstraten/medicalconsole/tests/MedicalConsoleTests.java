package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.yvesstraten.medicalconsole.Input;
import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.exceptions.InvalidOptionException;
import com.yvesstraten.medicalconsole.exceptions.InvalidYesNoException;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import com.yvesstraten.medicalconsole.operations.ListOperations;

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

/**
 * This test suite groups all integration tests related to the medical console program
 *
 * @see MedicalConsole
 * @author Yves Straten e2400068
 */
@Suite
@SuiteDisplayName("Medical console tests")
@SelectClasses({AddTests.class, DeleteTests.class, EditTests.class})
public class MedicalConsoleTests extends MedicalConsoleTest {
  /** Construct this test class */
  public MedicalConsoleTests() {
    super();
  }

  /**
   * This test tests that an invalid select option throws
   *
   * @param selectedOption selected test option
   * @param maxOption maxOptions available
   * @see InvalidOptionException
   */
  @ParameterizedTest
  @MethodSource("invalidOptions")
  public void invalidOptionShouldThrow(int selectedOption, int maxOption) {
    InvalidOptionException e =
        assertThrows(
            InvalidOptionException.class, 
                () -> Input.checkOption(selectedOption, maxOption));

    assertEquals(
        String.format("Invalid option please select option [%d-%d]",
                1,
                maxOption),
            e.getMessage());
  }

  /**
   * This method provides the needed arguments for 
     * {@link #invalidOptionShouldThrow(int, int)} test
   *
   * @return stream of arguments
   */
  public static Stream<Arguments> invalidOptions() {
    return Stream.of(Arguments.of(-1, 1), Arguments.of(6, 5));
  }

  /**
   * This test tests whether an invalid yes no input throws
   *
   * @param input test String input
   * @see InvalidYesNoException
   */
  @ParameterizedTest
  @ValueSource(strings = {"l", "z"})
  public void invalidYesNoInputShouldThrow(String input) {
    assertThrows(
        InvalidYesNoException.class,
        () -> {
          Input.testYesNo(input);
        });
  }

  /**
   * This method provides the needed arguments for 
   * {@link #validYesNoReturnsBoolean(String,
   * boolean)} test
   *
   * @return stream of arguments
   */
  public static Stream<Arguments> provideStringsForYesNo() {
    return Stream.of(
        Arguments.of("y", true),
        Arguments.of("yes", true),
        Arguments.of("n", false),
        Arguments.of("no", false));
  }

  /**
   * This test tests that {@link Input#testYesNo(String)} returns a boolean
   * only when inputs are y, yes, n, no.
   *
   * @param input input test String
   * @param expected expected boolean output
   * @throws InvalidYesNoException when yes no is not valid
   */
  @ParameterizedTest
  @MethodSource("provideStringsForYesNo")
  public void validYesNoReturnsBoolean(String input, boolean expected)
      throws InvalidYesNoException {
    boolean returned = Input.testYesNo(input);

    assertEquals(expected, returned);
  }

  /**
   * This test tests that an empty stream
   * returns an appropriate message when using 
   */
  @Test
  public void emptyStreamShouldReturnNoDetailsString() {
    Stream<Object> stream = Stream.of();
    String result = ListOperations.getObjectStreamDetails(stream, "test");

    assertEquals("There are no test for this service", result);
  }

  /**
   * This test tests that a filled stream returns an appropriate message
   *
   * @param <T> type of list
   * @param listToTest list that should be used for testing
   * @param name to be given as argument for testing
   */
  @ParameterizedTest
  @MethodSource("objectStreams")
  public <T> void filledStreamShouldReturnDetailsString(List<T> listToTest,
        String name) {
    StringBuilder builder = new StringBuilder("The following " + name + " are available \n");

    listToTest
            .stream()
            .forEach((item) -> 
                builder
                .append(item.toString())
                .append("\n"));
    String result = ListOperations
            .getObjectStreamDetails(listToTest.stream(), name);

    assertEquals(builder.toString(), result);
  }

  /**
   * This method provides the needed arguments for {@link
   * #filledStreamShouldReturnDetailsString(List, String)} test
   *
   * @return stream of arguments
   */
  public static Stream<Arguments> objectStreams() {
    return Stream.of(
        Arguments.of(testService.getPatients(), "patients"),
        Arguments.of(testService.getMedicalFacilities(), "facilities"));
  }

  /**
   * This test tests whether trying to get the cost of an operation returns 
   * appropriate results
   *
   * @param patient patient to test with
   * @param procedure procedure to test with
   * @param expected expected result
   */
  @ParameterizedTest
  @MethodSource("operationCostsArguments")
  public void gettingOperationCostReturnsProperResults(
      Patient patient, Procedure procedure, double expected) {
    double result = Hospital.getOperationCost(patient, procedure);

    assertEquals(expected, result);
  }

  /**
   * This method provides the needed arguments for {@link
   * #gettingOperationCostReturnsProperResults(Patient, Procedure, double)}
   *
   * @return stream of arguments
   */
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
