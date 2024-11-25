package com.yvesstraten.medicalconsole;

import com.yvesstraten.medicalconsole.exceptions.InvalidOptionException;
import com.yvesstraten.medicalconsole.exceptions.InvalidYesNoException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class contains a collection of helper functions to aid in parsing user input
 *
 * @author Yves Straten e2400068
 */
public class Input {
  /**
   * Default constructor for Input
   *
   * @throws UnsupportedOperationException always
   */
  public Input() {
    throw new UnsupportedOperationException(
        "This class contains a collection of helper functions to" 
				+ " aid in parsing user input, it is"
        + " not meant to be constructed!");
  }

  /**
   * This helper functions checks an integer input against the max available options
   *
   * @param chosenOption integer input
   * @param maxOptions max num of options available
   * @throws InvalidOptionException integer input is negative or more than max available options
   */
  public static void checkOption(int chosenOption, int maxOptions) 
	throws InvalidOptionException {
    if (chosenOption < 0 || chosenOption > maxOptions) {
      throw new InvalidOptionException(maxOptions);
    }
  }

  /**
   * Helper function to allow the user to choose an option from the maximum options available
   *
   * @param prompt prompt to print to console
   * @param maxOptions maximum num of options available
   * @param stdin standard in preferably set to <code>System.in</code>
   * @return chosen option
   */
  public static int chooseOption(String prompt, int maxOptions, 
		Scanner stdin) {
    do {
      try {
        System.out.print(prompt + " ");
        int chosenOption = stdin.nextInt();
        stdin.nextLine();
        checkOption(chosenOption, maxOptions);

        return chosenOption;
      } catch (InvalidOptionException e) {
        System.err.println(e.getMessage());
      }
    } while (true);
  }

  /**
   * Helper function to test a yes/no answer
   *
   * @param stringToTest input that should be tested
   * @return true if yes, false if no
   * @throws InvalidYesNoException input has anything other than y, yes, n, no
   */
  public static boolean testYesNo(String stringToTest) throws InvalidYesNoException {
    if (stringToTest.startsWith("y") || stringToTest.startsWith("Y")) {
      return true;
    } else if (stringToTest.startsWith("n") || stringToTest.startsWith("N")) {
      return false;
    } else {
      // Not a yes/no answer
      throw new InvalidYesNoException(
          stringToTest + " is not a yes/no answer, please input yes/no");
    }
  }

  /**
   * Helper function to allow the user to enter a yes no answer
   *
   * @param prompt prompt to print to console
   * @param stdin standard in preferably set to <code>System.in</code>
   * @return true if yes, false if no
   */
  public static boolean getYesNoInput(String prompt, Scanner stdin) {
    do {
      try {
        String input = Input.getString(prompt, stdin);
        return testYesNo(input);
      } catch (InvalidYesNoException e) {
        System.err.println(e.getMessage());
      }
    } while (true);
  }

  /**
   * Helper function to allow the user to enter a decimal value
   *
   * @param prompt prompt to print to console
   * @param stdin standard in preferably set to <code>System.in</code>
   * @param allowNegative whether to allow negative decimals
   * @return entered decimal value
   */
  public static double getDouble(String prompt, Scanner stdin, 
		boolean allowNegative) {
    do {
      try {
        System.out.print(prompt + " ");
        double value = stdin.nextDouble();
        if (!allowNegative && value < 0) {
          throw new InputMismatchException(
              "Wrong input! Please input a non-negative decimal number!");
        }
        stdin.nextLine();
        return value;
      } catch (InputMismatchException e) {
        stdin.nextLine();
        System.err.println(e.getMessage());
      }
    } while (true);
  }

  /**
   * Helper function to allow the user to enter a positive decimal value
   *
   * @param prompt prompt to print to console
   * @param stdin standard in preferably set to <code>System.in</code>
   * @return entered positive decimal value
   */
  public static double getDouble(String prompt, Scanner stdin) {
    return getDouble(prompt, stdin, false);
  }

  /**
   * Helper function to allow the user to enter an integer value
   *
   * @param prompt prompt to print to console
   * @param stdin standard in preferably set to <code>System.in</code>
   * @param allowNegative whether to allow negative integers
   * @return entered integer value
   */
  public static int getInt(String prompt, Scanner stdin, boolean allowNegative) {
    do {
      try {
        System.out.print(prompt + " ");
        int value = stdin.nextInt();

        if (!allowNegative && value < 0)
          throw new InputMismatchException("Wrong input! Please input a non-negative integer!");
        stdin.nextLine();
        return value;
      } catch (InputMismatchException e) {
        stdin.nextLine();
        System.err.print(e.getMessage());
      }
    } while (true);
  }

  /**
   * Helper function to allow the user to enter a positive integer value
   *
   * @param prompt prompt to print to console
   * @param stdin standard in preferably set to <code>System.in</code>
   * @return entered positive integer value
   */
  public static int getInt(String prompt, Scanner stdin) {
    return getInt(prompt, stdin, false);
  }

  /**
   * Helper function to allow the user to enter a String value
   *
   * @param prompt prompt to print to console
   * @param stdin standard in preferably set to <code>System.in</code>
   * @return entered String
   */
  public static String getString(String prompt, Scanner stdin) {
    do {
      try {
        System.out.print(prompt + " ");
        String value = stdin.nextLine();
        return value;
      } catch (InputMismatchException e) {
        System.err.println(e);
      }
    } while (true);
  }

  /**
   * Helper function to allow the user to enter a char
   *
   * @param prompt prompt to print to console
   * @param stdin standard in preferably set to <code>System.in</code>
   * @return entered character
   */
  public static char getChar(String prompt, Scanner stdin) {
    do {
      try {
        System.out.print(prompt + " ");
        char value = stdin.next().charAt(0);
        stdin.nextLine();
        return value;
      } catch (InputMismatchException e) {
        System.err.println(e);
      }
    } while (true);
  }
}
