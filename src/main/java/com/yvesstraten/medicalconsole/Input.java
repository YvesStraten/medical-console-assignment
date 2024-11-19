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
	public static void checkOption(int chosenOption, int maxOptions) throws InvalidOptionException {
		if (chosenOption < 0 || chosenOption > maxOptions) {
				throw new InvalidOptionException(
						String.format("Invalid option please select option [%d-%d]", 1, maxOptions));
		}
	}

	public static int chooseOption(String prompt, int maxOptions, Scanner stdin) {
		do {
			try {
				System.out.print(prompt + " ");
				int chosenOption = stdin.nextInt();
				stdin.nextLine();
				checkOption(chosenOption, maxOptions);

				return chosenOption;
			} catch(InvalidOptionException e){
				System.err.println(e.getMessage());
			}
		} while(true);
	}

  /**
   * Helper function to test a yes/no answer
   *
   * @param stringToTest input that should be tested
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

	public static boolean getYesNoInput(String prompt, Scanner stdin){
		do{
			try {
				String input = Input.getString(prompt, stdin);
				return testYesNo(input);
			} catch(InvalidYesNoException e){
				System.err.println(e.getMessage());
			}
		} while(true);
	}

  public static double getDouble(String prompt, Scanner stdin) {
    do {
      try {
        System.out.print(prompt + " ");
        double value = stdin.nextDouble();
				stdin.nextLine();
        return value;
      } catch (InputMismatchException e) {
        stdin.nextLine();
        System.err.println("Wrong input! Please input a non-negative decimal number!");
      }
    } while (true);
  }

  public static int getInt(String prompt, Scanner stdin) {
    do {
      try {
        System.out.print(prompt + " ");
        int value = stdin.nextInt();
				stdin.nextLine();
        return value;
      } catch (InputMismatchException e) {
        stdin.nextLine();
        System.err.println("Wrong input! Please input a non-negative integer!");
      }
    } while (true);
  }

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
