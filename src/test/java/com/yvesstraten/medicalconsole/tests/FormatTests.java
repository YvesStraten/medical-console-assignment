package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import com.yvesstraten.medicalconsole.Format;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * This class contains all tests related to formatting and pretty-printing
 *
 * @see Format
 * @author Yves Straten e2400068
 */
public class FormatTests {
  /** Construct this test class */
  public FormatTests() {}

  /**
   * This test tests whether input strings are enumerated properly
   *
   * @param input input string to test enumeration
   * @param expected expected output string
   * @see Format
   */
  @ParameterizedTest
  @MethodSource("toEnumerateNoArgs")
  public void enumeratedProperlyWithNoArgs(String[] input, String[] expected) {
    String formatted = Format.enumeratedContent(input);
    String[] result = formatted.split("\n");
    assertArrayEquals(expected, result);
  }

  /**
   * This test tests whether input strings are enumerated properly, with specified start and finish
   *
   * @param input input string to test enumeration
   * @param expected expected output string
   * @param start index to start enumeration
   * @see Format
   */
  @ParameterizedTest
  @MethodSource("toEnumerateWithArgs")
  public void enumeratedProperlyWithArgs(String[] input, String[] expected, int start) {
    String formatted = Format.enumeratedContent(input, start);
    String[] result = formatted.split("\n");
    assertArrayEquals(expected, result);
  }

  /**
   * This method provides the needed arguments for {@link 
   * #enumeratedProperlyWithNoArgs(String[],
   * String[])} test
   *
   * @return stream of arguments
   */
  public static Stream<Arguments> toEnumerateNoArgs() {
    return Stream.of(
        Arguments.of(
                new String[] {"Test1", "Test2"},
                new String[] {"[1] Test1", "[2] Test2"}),
        Arguments.of(
                new String[] {"Test1", "- Test2"},
                new String[] {"[1] Test1", "- Test2"}));
  }

  /**
   * This method provides the needed arguments for {@link 
   * #enumeratedProperlyWithArgs(String[],
   * String[], int)} test
   *
   * @return stream of arguments
   */
  public static Stream<Arguments> toEnumerateWithArgs() {
    return Stream.of(
        Arguments.of(
                new String[] {"Test1", "Test2"},
                new String[] {"Test1", "[1] Test2"}, 1),
        Arguments.of(new String[] {"Test1", "- Test2"},
                new String[] {"Test1", "- Test2"},
                1),
        Arguments.of(new String[] {"Test1", "Test2"},
                new String[] {"Test1", "Test2"},
                2),
        Arguments.of(new String[] {"Test1", "- Test2"},
                new String[] {"Test1", "- Test2"},
                2));
  }
}
