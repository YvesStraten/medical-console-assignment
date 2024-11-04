package com.yvesstraten.medicalconsole;

import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
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

    System.out.println(Format.enumeratedContent(options));
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
    service.addMedicalFacility(clinicToAdd);
  }

  public static void addHospital(HealthService service, Scanner stdin) {
    System.out.print("What is the name of the hospital? ");
    String name = stdin.nextLine();
    Hospital hospitalToAdd = new Hospital(service.next(), name);

    service.addMedicalFacility(hospitalToAdd);
  }

  public static void addPatient(HealthService service, Scanner stdin) throws InvalidYesNoException {
    System.out.print("What is the name of the patient? ");
    String name = stdin.nextLine();

    System.out.print("Is the patient private? ");
    String isPrivateString = stdin.nextLine();
    boolean isPrivate = testYesNo(isPrivateString);

    Patient patientToAdd = new Patient(service.next(), name, isPrivate);
    service.addPatient(patientToAdd);
    System.out.println("Successfully added " + patientToAdd.toString());
    System.out.println();
  }

  public static void addProcedure(HealthService service, Scanner stdin) throws NoHospitalsAvailableException, InvalidOptionException, InvalidYesNoException {
    if (service.getMedicalFacilities().size() == 0) {
      throw new NoHospitalsAvailableException("Please add a hospital first!");
    }

    List<MedicalFacility> filteredHospitals =
        service.getMedicalFacilities().stream()
            .filter((facility) -> facility instanceof Hospital)
            .collect(Collectors.toList());

    String[] hospitalDetails =
        filteredHospitals.stream()
            .map((facility) -> ((Hospital) facility).toString())
            .toArray(String[]::new);

    System.out.println(Format.enumeratedContent(hospitalDetails));
    System.out.print("Please select a hospital to add the procedure to: ");
    int chosenHospital = stdin.nextInt();
    checkChosenOption(chosenHospital, hospitalDetails);
    stdin.nextLine();

		Hospital hospital = (Hospital) (filteredHospitals.get(chosenHospital - 1));

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
    hospital.addProcedure(procedureToAdd);
  }

  public static boolean testYesNo(String stringToTest) throws InvalidYesNoException {
    if (stringToTest.startsWith("y") || stringToTest.startsWith("Y")) {
      return true;
    } else if (stringToTest.startsWith("n") || stringToTest.startsWith("N")) {
      return false;
    } else {
      throw new InvalidYesNoException(stringToTest + " is not a yes/no answer, please input yes/no");
    }
  }

  public static void addObject(HealthService service, Scanner stdin) throws InvalidOptionException, InvalidYesNoException {
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
          addPatient(service, stdin);
          validInput = true;
        } while (!validInput);
        break;
      case 3:
        do {
          try {
            addProcedure(service, stdin);
            validInput = true;
          } catch (NoHospitalsAvailableException e) {
            System.err.println(e);
            addHospital(service, stdin);
          }
        } while (!validInput);
        break;
    }
  }

  public static void deleteObject(HealthService service, Scanner stdin)
      throws InvalidOptionException, InvalidYesNoException {
    System.out.println("The following types of services are available: ");
    String[] types =
        new String[] {
          new String("Medical facility"), new String("Patient"), new String("Procedure")
        };

    System.out.println(Format.enumeratedContent(types));
    System.out.print("Which type of object would you like to delete? ");
    int chosenOption = stdin.nextInt();
    checkChosenOption(chosenOption, types);
    stdin.nextLine();

    boolean validInput = false;
    switch (chosenOption) {
      case 1:
        do {
          System.out.println("The following medical facilities are stored: ");
          String[] facilitiesDetails = service.getMedicalFacilities().toArray(String[]::new);
          System.out.println(Format.enumeratedContent(facilitiesDetails));
          System.out.print("Which facility would you like to delete? ");
          int facilityToDelete = stdin.nextInt();
          stdin.nextLine();
          checkChosenOption(facilityToDelete, facilitiesDetails);
          MedicalFacility facilityToBeDeleted =
              service.getMedicalFacilities().get(facilityToDelete);
          if (facilityToBeDeleted instanceof Hospital) {
            int numProcedures = ((Hospital) facilityToBeDeleted).getProcedures().size();
            if (numProcedures > 0) {
              System.out.print(
                  "This hospital can perform "
                      + numProcedures
                      + " procedures - do you still wish to delete it? [y/n]");
              String choice = stdin.nextLine();
              if (testYesNo(choice)) {
                service.deleteMedicalFacility(facilityToDelete);
              }
            }
          } else {
            service.deleteMedicalFacility(facilityToDelete);
          }

          validInput = true;
        } while (!validInput);

      case 2:
        do {
          System.out.println("The following patients are stored: ");
          String[] patientsDetails =
              service.getPatients().stream()
                  .map((patient) -> patient.toString())
                  .toArray(String[]::new);

          System.out.println(Format.enumeratedContent(patientsDetails));
          System.out.print("Which patient would you like to remove? ");
          int patientToRemove = stdin.nextInt();
          checkChosenOption(patientToRemove, patientsDetails);
          service.deletePatient(patientToRemove - 1);
          validInput = true;
        } while (!validInput);

        break;
    }
  }

  public static String getObjectStreamDetails(Stream<Object> stream, String name) {
    List<Object> objects = stream.toList();
		System.out.println(objects.get(0));
    if (objects.size() == 0) {
      return new String("There are no " + name + " for this service");
    }

		String toReturn = objects.stream().map((object) -> object.toString()).reduce("The following " + name + " are available \n", (before, next) -> (before + next + "\n"));

		System.out.println(toReturn);

    return toReturn;
  }

  public static void listObjects(HealthService service, Scanner stdin)
      throws InvalidOptionException {
    System.out.println("Which type of object would you like to see listed?");
    String[] options =
        new String[] {
          new String("Medical Facilities"), new String("Patients"), new String("Procedures")
        };
    System.out.println(Format.enumeratedContent(options));
    System.out.print("Please select a type: ");
    int chosenOption = stdin.nextInt();
    stdin.nextLine();

    checkChosenOption(chosenOption, options);

    switch (chosenOption) {
      case 1:
        System.out.println(
            getObjectStreamDetails(Stream.of(service.getMedicalFacilities()), "facilities"));
        break;

      case 2:
				System.out.println(getObjectStreamDetails(Stream.of(service.getPatients()), "patients"));
        break;

      case 3:
				Stream<Procedure> procedures = service.getMedicalFacilities().stream().filter((facility) -> facility instanceof Hospital).map(Hospital.class::cast).map((hospital) -> hospital.getProcedures()).flatMap((procedureArr) -> procedureArr.stream());
				System.out.println(getObjectStreamDetails(Stream.of(procedures), "procedures"));
				break;
    }
  }

  public static void executeOption(
      ConsoleOption selectedOption, HealthService service, Scanner stdin)
      throws InvalidOptionException, InvalidYesNoException, InputMismatchException {
    switch (selectedOption) {
      case ADD:
        boolean done = false;
        do {
          addObject(service, stdin);
          done = true;
        } while (!done);
        break;
      case DELETE:
        // TODO
        deleteObject(service, stdin);
        break;
      case SIMULATE:
        // TODO
        break;
      case OPERATE:
        // TODO
        break;
      case LIST:
        listObjects(service, stdin);
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
        System.err.println("Wrong input, please try again");
      } catch (InvalidOptionException exception) {
        System.err.println(exception.getMessage() + "\n");
      } catch (InvalidYesNoException exception){
				System.err.println(exception.getMessage() + "\n");
			}

    } while (true);
  }
}
