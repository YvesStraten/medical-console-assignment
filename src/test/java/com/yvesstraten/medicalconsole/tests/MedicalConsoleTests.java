package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.InputMismatchException;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.InvalidOptionException;
import com.yvesstraten.medicalconsole.MedicalConsole;

@DisplayName("Medical console tests")
public class MedicalConsoleTests {
	@ParameterizedTest
	@ValueSource(ints = { -1, 7 })
	public void invalidOptionShouldThrow(int selectedOption){
    String[] options = {
      new String("Add a service"),
      new String("List all services"),
      new String("Delete a service"),
      new String("Simulate patient visit"),
      new String("Operate"),
      new String("Quit")
    };

		InvalidOptionException e = assertThrows(InvalidOptionException.class, () -> {
			MedicalConsole.checkChosenOption(selectedOption, options);
		});

		assertEquals(String.format("Invalid option please select option [%d-%d]", 1, options.length), e.getMessage());
	}

	@ParameterizedTest
	@ValueSource(strings = { "l", "z" })
	public void invalidYesNoInputShouldThrow(String input){
		assertThrows(InputMismatchException.class, () -> {
			MedicalConsole.testYesNo(input);
		});
	}

	private static Stream<Arguments> provideStringsForYesNo(){
		return Stream.of(Arguments.of("y", true), Arguments.of("yes", true), Arguments.of("n", false), Arguments.of("no", false));
	}

	@ParameterizedTest
	@MethodSource("provideStringsForYesNo")
	public void validYesNoReturnsBoolean(String input, boolean expected){
		boolean returned = MedicalConsole.testYesNo(input);

		assertEquals(expected, returned);
	}
}
