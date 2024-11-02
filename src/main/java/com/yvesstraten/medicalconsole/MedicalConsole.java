package com.yvesstraten.medicalconsole;

import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import com.yvesstraten.medicalconsole.facilities.Procedure;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Stream;

public class MedicalConsole {
  public enum ConsoleOption {
    ADD(1),
    LIST(2),
    DELETE(3),
    SIMULATE(4),
    OPERATE(5),
    QUIT(6);

    private final int value;

    private ConsoleOption(int value) {
      this.value = value;
    }

    public int getValue() {
      return this.value;
    }
  }

  public static void printIntroduction() {
    String[] options = {
      new String("Add a service"),
      new String("List all services"),
      new String("Delete a service"),
      new String("Simulate patient visit"),
      new String("Operate"),
      new String("Quit")
    };
    System.out.println("Welcome to HELP Medical console");
    System.out.println("What would you like to do today?");
    System.out.println();

    for (int i = 0; i < options.length; i++) {
      System.out.printf("[%d] %s \n", i + 1, options[i]);
    }
  }

  public static void checkChosenOption(int chosenOption, String[] availableOptions)
      throws InvalidOptionException {
    if (chosenOption <= 0 || chosenOption > availableOptions.length) {
      throw new InvalidOptionException(
          String.format("Invalid option please select option [%d-%d]", 1, availableOptions.length));
    }
  }

  public static void addClinic(HealthService service, Scanner stdin) {
    System.out.println("What is the name of the clinic? ");
    String name = stdin.nextLine();
    System.out.println("What is the fee of the clinic? ");
    double fee = stdin.nextDouble();
    System.out.println("What is the gap percent of the clinic? ");
    double gapPercent = stdin.nextDouble();

    stdin.nextLine();
    Clinic clinicToAdd = new Clinic(service.next(), name, fee, gapPercent);
    service.AddMedicalFacility(clinicToAdd);
  }

  public static void addHospital(HealthService service, Scanner stdin) {
    System.out.print("What is the name of the hospital? ");
    String name = stdin.nextLine();
    Hospital hospitalToAdd = new Hospital(service.next(), name);

    service.AddMedicalFacility(hospitalToAdd);
  }

  public static boolean testYesNo(String stringToTest) {
    if (stringToTest.startsWith("y") || stringToTest.startsWith("Y")) {
      return true;
    } else if (stringToTest.startsWith("n") || stringToTest.startsWith("N")) {
      return false;
    } else {
      throw new InputMismatchException();
    }
  }

  public static void addObject(HealthService service, Scanner stdin) throws InvalidOptionException {
    String[] mainOptions =
        new String[] {
          new String("Medical facility"), new String("Patient"), new String("Procedure"),
        };

    System.out.println(Format.enumeratedContent(mainOptions));

    System.out.print("Which service would you like to add? ");
    int chosenOption = stdin.nextInt();
    stdin.nextLine();

    checkChosenOption(chosenOption, mainOptions);

    boolean validInput = false;
    switch (chosenOption) {
      case 1:
        do {
          System.out.println("The following medical facilities are available");
          String[] facilityOptions =
              new String[] {
                new String("Clinic"), new String("Hospital"),
              };

          System.out.println(Format.enumeratedContent(facilityOptions));
          int chosenFacilityOption = stdin.nextInt();
          stdin.nextLine();
          checkChosenOption(chosenFacilityOption, facilityOptions);

          switch (chosenFacilityOption) {
            case 1:
              addClinic(service, stdin);
              validInput = true;
              break;
            case 2:
              addHospital(service, stdin);
              validInput = true;
              break;
          }

        } while (!validInput);
        break;
      case 2:
        do {
          System.out.print("What is the name of the patient? ");
          String name = stdin.nextLine();

          System.out.print("Is the patient private? ");
          String isPrivateString = stdin.nextLine();
          boolean isPrivate = testYesNo(isPrivateString);

          Patient patientToAdd = new Patient(service.next(), name, isPrivate);
          service.AddPatient(patientToAdd);
          validInput = true;
          System.out.println("Successfully added " + patientToAdd.toString());
          System.out.println();

        } while (!validInput);
        break;
      case 3:
        do {
          if (service.getMedicalFacilities().size() == 0) {
            System.out.println("Please add a hospital first!");
            addHospital(service, stdin);
          } else {
            Stream<MedicalFacility> filteredHospitals =
                service.getMedicalFacilities().stream()
                    .filter((facility) -> facility instanceof Hospital);

            String[] hospitalDetails =
                filteredHospitals
                    .map((facility) -> ((Hospital) facility).toString())
                    .toArray(String[]::new);

						System.out.println(Format.enumeratedContent(hospitalDetails));
            System.out.print("Please select a hospital to add the procedure to: ");
						int chosenHospital = stdin.nextInt();
						checkChosenOption(chosenHospital, hospitalDetails);
					  stdin.nextLine();
            System.out.print("Name of procedure? ");
            String name = stdin.nextLine();
            System.out.print("Description of procedure? ");
            String description = stdin.nextLine();
            System.out.print("Is the procedure elective? ");
            boolean isElective = testYesNo(stdin.nextLine());
            System.out.print("What is the basic cost of the procedure? ");
            double basicCost = stdin.nextDouble();
            stdin.nextLine();

            Procedure procedureToAdd =
                new Procedure(service.next(), name, description, isElective, basicCost);

            validInput = true;
          }

        } while (!validInput);
        break;
    }
  }

  public static void executeOption(
      ConsoleOption selectedOption, HealthService service, Scanner stdin)
      throws InvalidOptionException, InputMismatchException {
    switch (selectedOption) {
      case ADD:
        boolean done = false;
        do {
          addObject(service, stdin);
          done = true;
        } while (!done);
        break;
      case DELETE:
        break;
      case SIMULATE:
        // TODO
        break;
      case OPERATE:
        // TODO
        break;
      case LIST:
        // TODO
        break;
      case QUIT:
        System.exit(0);
    }
  }

  public static boolean checkOption(int inputOption) throws InvalidOptionException {
    ConsoleOption[] optionValues = ConsoleOption.values();

    if (inputOption == optionValues[inputOption - 1].getValue()) {
      return true;
    }

    throw new InvalidOptionException(
        String.format(
            "This option is invalid, please input a number from [%d-%d]", 1, optionValues.length));
  }

  public static void main(String[] args) {
    HealthService service = new HealthService();
    Scanner stdin = new Scanner(System.in);
    boolean quit = false;

    do {
      printIntroduction();
      System.out.print("Select an option: ");
      try {
        int inputOption = stdin.nextInt();
        boolean validOption = checkOption(inputOption);

        if (validOption) {
          executeOption(ConsoleOption.values()[inputOption - 1], service, stdin);
        }
      } catch (InputMismatchException exception) {
        System.err.println("Wrong option, please try again!");
      } catch (InvalidOptionException exception) {
        System.err.println(exception.getMessage() + "\n");
      }

    } while (true);
  }
}
