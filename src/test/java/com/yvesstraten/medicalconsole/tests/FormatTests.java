package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import com.yvesstraten.medicalconsole.Format;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class FormatTests {
  @ParameterizedTest
  @MethodSource("toEnumerateNoArgs")
  public void enumeratedProperlyWithNoArgs(String[] input, String[] expected) {
    String formatted = Format.enumeratedContent(input);
    String[] result = formatted.split("\n");
    assertArrayEquals(expected, result);
  }

  @ParameterizedTest
  @MethodSource("toEnumerateWithArgs")
  public void enumeratedProperlyWithArgs(String[] input, String[] expected, int start) {
    String formatted = Format.enumeratedContent(input, start);
    String[] result = formatted.split("\n");
    assertArrayEquals(expected, result);
  }

  public static Stream<Arguments> toEnumerateNoArgs() {
    return Stream.of(
        Arguments.of(new String[] {"Test1", "Test2"}, new String[] {"[1] Test1", "[2] Test2"}),
        Arguments.of(new String[] {"Test1", "- Test2"}, new String[] {"[1] Test1", "- Test2"}));
  }

  public static Stream<Arguments> toEnumerateWithArgs() {
    return Stream.of(
        Arguments.of(new String[] {"Test1", "Test2"}, new String[] {"Test1", "[1] Test2"}, 1),
        Arguments.of(new String[] {"Test1", "- Test2"}, new String[] {"Test1", "- Test2"}, 1),
        Arguments.of(new String[] {"Test1", "Test2"}, new String[] {"Test1", "Test2"}, 2),
        Arguments.of(new String[] {"Test1", "- Test2"}, new String[] {"Test1", "- Test2"}, 2));
  }
}
