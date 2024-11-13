package com.yvesstraten.medicalconsole;

import com.yvesstraten.medicalconsole.comparators.MedicalFacilitiesComparators;
import com.yvesstraten.medicalconsole.comparators.PatientComparators;
import com.yvesstraten.medicalconsole.comparators.ProcedureComparators;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import com.yvesstraten.medicalconsole.facilities.Procedure;

import java.util.Arrays;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

public class MedicalConsole {
  public enum ConsoleOption {
    ADD(1, "Add a service"),
    LIST(2, "List all objects"),
    DELETE(3, "Delete an object"),
    SIMULATE(4, "Simulate a visit"),
    OPERATE(5, "Operate a patient"),
		SORTED(6, "Sort objects"),
    QUIT(7, "Quit program");

    private final int value;
		private final String optionName;

    private ConsoleOption(int value, String optionName) {
      this.value = value;
			this.optionName = optionName;
    }

    public int getValue() {
      return this.value;
    }

		public String getOptionName(){
			return this.optionName;
		}
  }

  public static void printIntroduction() {
		ConsoleOption[] declaredPanes = ConsoleOption.values();
		String options = Arrays.stream(declaredPanes).map(pane -> pane.getOptionName()).reduce("", (before, next) -> before + next + "\n");
    System.out.println("Welcome to HELP Medical console");
    System.out.println("What would you like to do today?");
    System.out.println();

    System.out.println(Format.enumeratedContent(options));
  }

  public static <T> void checkChosenOption(int chosenOption, Collection<T> availableOptions)
      throws InvalidOptionException {
    chosenOption -= 1;
    if (chosenOption < 0 || chosenOption >= availableOptions.size()) {
      throw new InvalidOptionException(
          String.format("Invalid option please select option [%d-%d]", 1, availableOptions.size()));
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

  public static void addProcedure(HealthService service, Scanner stdin)
      throws NoHospitalsAvailableException, InvalidOptionException, InvalidYesNoException {
    if (service.getMedicalFacilities().size() == 0) {
      throw new NoHospitalsAvailableException("Please add a hospital first!");
    }

    List<Hospital> filteredHospitals = service.getHospitals().toList();
    String hospitalDetails = getObjectStreamDetails(service.getHospitals(), "hospitals");

    System.out.println(Format.enumeratedContent(hospitalDetails, 1));
    System.out.print("Please select a hospital to add the procedure to: ");
    int chosenHospital = stdin.nextInt();
    checkChosenOption(chosenHospital, service.getHospitals().toList());
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
      throw new InvalidYesNoException(
          stringToTest + " is not a yes/no answer, please input yes/no");
    }
  }

  public static void addObject(HealthService service, Scanner stdin)
      throws InvalidOptionException, InvalidYesNoException {
    String mainOptions = "Medical facility\n" + "Patient\n" + "Procedure\n";

    System.out.println(Format.enumeratedContent(mainOptions));

    System.out.print("Which service would you like to add? ");
    int chosenOption = stdin.nextInt();
    stdin.nextLine();

    checkChosenOption(chosenOption, List.of(mainOptions.split("\n")));

    boolean validInput = false;
    switch (chosenOption) {
      case 1:
        do {
          System.out.println("The following medical facilities are available");
					String facilityOptions = "Clinic\n" + "Hospital\n";

          System.out.println(Format.enumeratedContent(facilityOptions));
          int chosenFacilityOption = stdin.nextInt();
          stdin.nextLine();
          checkChosenOption(chosenFacilityOption, List.of(facilityOptions.split("\n")));

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

  public static void deletePatient(HealthService service, Scanner stdin)
      throws InvalidOptionException {
    String patientsDetails = getObjectStreamDetails(service.getPatientsStream(), "patients");

    System.out.println(Format.enumeratedContent(patientsDetails, 1));
    System.out.print("Which patient would you like to remove? ");
    int patientToRemove = stdin.nextInt();
    stdin.nextLine();
    checkChosenOption(patientToRemove, service.getPatients());
    service.deletePatient(patientToRemove - 1);
    System.out.println("Removed patient successfully!");
  }

  public static void deleteFacility(HealthService service, Scanner stdin)
      throws InvalidOptionException, InvalidYesNoException {
    String facilitiesDetails =
        getObjectStreamDetails(service.getMedicalFacilitiesStream(), "facilities");
    System.out.println(Format.enumeratedContent(facilitiesDetails, 1));
    System.out.print("Which facility would you like to delete? ");
    int facilityToDelete = stdin.nextInt();
    stdin.nextLine();
    checkChosenOption(facilityToDelete, service.getMedicalFacilities());
    MedicalFacility facilityToBeDeleted = service.getMedicalFacilities().get(facilityToDelete - 1);
    if (facilityToBeDeleted instanceof Hospital) {
      int numProcedures = ((Hospital) facilityToBeDeleted).getProcedures().size();
      if (numProcedures > 0) {
        System.out.print(
            "This hospital can perform "
                + numProcedures
                + " procedures - do you still wish to delete it? [y/n]");
        String choice = stdin.nextLine();
        if (testYesNo(choice)) {
          service.deleteMedicalFacility(facilityToDelete - 1);
        }
      }
    } else {
      service.deleteMedicalFacility(facilityToDelete - 1);
    }
		System.out.println("Removed facility successfully!");
  }

  public static void deleteProcedure(HealthService service, Scanner stdin)
      throws InvalidOptionException {
    System.out.println("The following procedures are stored: ");
    List<Hospital> hospitals = service.getHospitals().toList();
    HashMap<Procedure, Hospital> map = new HashMap<Procedure, Hospital>();

    for (Hospital hospital : hospitals) {
      for (Procedure procedure : hospital.getProcedures()) map.put(procedure, hospital);
    }

    String procedureDetails =
        map.keySet().stream()
            .map(procedure -> procedure.toString())
            .reduce("", (before, next) -> before + next + "\n");

    System.out.println(Format.enumeratedContent(procedureDetails));
    System.out.print("Choose a procedure to remove: ");
    int toDelete = stdin.nextInt();
    stdin.nextLine();
    checkChosenOption(toDelete, map.keySet());

    int i = 0;
    Iterator<Entry<Procedure, Hospital>> iterator = map.entrySet().iterator();
    Hospital hospitalToAffect = null;
    Procedure procedureToDelete = null;

    while (iterator.hasNext()) {
      Entry<Procedure, Hospital> current = iterator.next();
      if (i == toDelete - 1) {
        procedureToDelete = current.getKey();
        hospitalToAffect = current.getValue();
      }
      i++;
    }

    hospitalToAffect.removeProcedure(procedureToDelete);
    System.out.println("Selected procedure was removed successfully!");
  }

  /**
   * Deletes an object from the provided HealthService this object can either be a {@link
   * MedicalFacility}, {@link Patient} or {@link Procedure}.
   *
   * @param service <code>HealthService</code> object to delete an object from
   * @param stdin <code>Scanner</code> object which preferably iterates over <code>System.in</code>
   * @throws InvalidOptionException
   * @throws InvalidYesNoException
   */
  public static void deleteObject(HealthService service, Scanner stdin)
      throws InvalidOptionException, InvalidYesNoException {
    System.out.println("The following types of services are available: ");
		String types = "Medical facility\n" + "Patient\n" + "Procedure\n";

    System.out.println(Format.enumeratedContent(types));
    System.out.print("Which type of object would you like to delete? ");
    int chosenOption = stdin.nextInt();
    checkChosenOption(chosenOption, List.of(types.split("\n")));
    stdin.nextLine();

    boolean validInput = false;
    switch (chosenOption) {
      case 1:
        do {
          deleteFacility(service, stdin);
          validInput = true;
        } while (!validInput);
        break;
      case 2:
        do {
          deletePatient(service, stdin);
          validInput = true;
        } while (!validInput);

        break;
      case 3:
        do {
					deleteProcedure(service, stdin);
          validInput = true;
        } while (!validInput);
        break;
    }
  }

  /**
   * Gets details of provided object stream
   *
   * @param stream stream to get details from
   * @param name name that should be included in final string
   * @return String containing details of objects in the stream This String always has the following
   *     format: <code>The following <strong>name</strong> are available \n details of object</code>
   *     unless there are not items present, in this case, <code>
   *     There are no <strong>name</strong> for this service \n</code>
   */
  public static <T> String getObjectStreamDetails(Stream<T> stream, String name) {
    // Using .count() is a terminal operation,
    // as such, the stream is converted to a list
    // first to get its size and return an appropriate message
    List<T> objects = stream.toList();
    if (objects.size() == 0) {
      return new String("There are no " + name + " for this service");
    }

    // Get every detail of an object, and append to builder
    StringBuilder builder = new StringBuilder("The following " + name + " are available \n");
    objects.stream().forEach(object -> builder.append(object.toString()).append("\n"));

    return builder.toString();
  }

	public static <T> void listObjectGroup(Stream<T> streamFrom, String name) {
     String output = getObjectStreamDetails(streamFrom, name);
     System.out.println(Format.enumeratedContent(output, 1));
	}

  public static void listObjects(HealthService service, Scanner stdin)
      throws InvalidOptionException {
    System.out.println("Which type of object would you like to see listed?");
    String options = "Medical Facilities\n" + "Patients\n" + "Procedures\n";
    System.out.println(Format.enumeratedContent(options));
		boolean validInput = false;

		do {
			System.out.print("Please select a type: ");
			int chosenOption = stdin.nextInt();
			stdin.nextLine();

			checkChosenOption(chosenOption, List.of(options.split("\n")));

			switch (chosenOption) {
				case 1:
				listObjectGroup(service.getMedicalFacilitiesStream(), "facilities");
				break;

				case 2:
				listObjectGroup(service.getPatientsStream(), "patients");
				break;

				case 3:
				Stream<Procedure> procedures =
				service.getHospitals().flatMap(Hospital::getProceduresStream);
				listObjectGroup(procedures, "procedures");
				break;
			}

			validInput = true;
		} while(!validInput);
  }

  public static void simulateVisit(HealthService service, Scanner stdin)
      throws InvalidOptionException {
    String patientsDetails = getObjectStreamDetails(service.getPatientsStream(), "patients");
    System.out.println(Format.enumeratedContent(patientsDetails, 1));
    System.out.print("Please choose a patient to simulate a visit: ");
    int chosenPatient = stdin.nextInt();
    stdin.nextLine();
    checkChosenOption(chosenPatient, service.getPatients());

    String medicalFacilitiesDetails =
        getObjectStreamDetails(service.getMedicalFacilitiesStream(), "facilities");
    System.out.println(Format.enumeratedContent(medicalFacilitiesDetails, 1));
    System.out.print("Please choose a facility the patient should visit: ");
    int chosenFacility = stdin.nextInt();
    stdin.nextLine();
    checkChosenOption(chosenFacility, service.getMedicalFacilities());

    service
        .getMedicalFacilities()
        .get(chosenFacility - 1)
        .visit(service.getPatients().get(chosenPatient - 1));
  }

  public static double getOperationCost(Patient patient, Procedure procedure) {
    double cost;
    if (patient.isPrivate()) {
      if (procedure.isElective()) {
        cost = 2000;
      } else {
        cost = 1000;
      }
    } else {
      if (procedure.isElective()) {
        cost = procedure.getCost();
      } else {
        cost = 0;
      }
    }

    return cost;
  }

  public static void operate(HealthService service, Scanner stdin) throws InvalidOptionException {
    String hospitalsDetails = getObjectStreamDetails(service.getHospitals(), "hospitals");
    System.out.println(Format.enumeratedContent(hospitalsDetails, 1));
    System.out.print("Please select which hospital to operate in: ");
    int selectedHospitalIndex = stdin.nextInt();
    checkChosenOption(selectedHospitalIndex, service.getHospitals().toList());

    String patientDetails = getObjectStreamDetails(service.getPatientsStream(), "patients");
    System.out.println(Format.enumeratedContent(patientDetails, 1));
    System.out.print("Please select which patient to operate: ");
    int selectedPatientIndex = stdin.nextInt();
    checkChosenOption(selectedPatientIndex, service.getPatients());

    Hospital selectedHospital = service.getHospitals().toList().get(selectedHospitalIndex - 1);
    Patient selectedPatient = service.getPatients().get(selectedPatientIndex - 1);

    try {
      if (selectedPatient.isInThisHospital(selectedHospital)) {
        String procedureDetails =
            getObjectStreamDetails(selectedHospital.getProcedures().stream(), "procedures");
        System.out.println(Format.enumeratedContent(procedureDetails, 1));
        System.out.print("Please select which procedure to undertake: ");
        int selectedProcedureIndex = stdin.nextInt();
        checkChosenOption(selectedProcedureIndex, selectedHospital.getProcedures());
        stdin.nextLine();

        Procedure selectedProcedure =
            selectedHospital.getProcedures().get(selectedProcedureIndex - 1);

        double cost = getOperationCost(selectedPatient, selectedProcedure);
        selectedPatient.addBalance(cost);

        double randomNum = new Random().nextDouble(1);
        if (randomNum < selectedHospital.getProbAdmit()) {
          selectedPatient.setMedicalFacility(null);
        }
      }
    } catch (WrongHospitalException e) {
      System.err.println(e.getMessage());
    } catch (IllegalArgumentException e) {
      System.err.println("Invalid balance!");
    }
  }

  public static void sortObjects(HealthService service, Scanner stdin)
      throws InvalidOptionException {
    String types = "Medical Facilities\n" + "Patients\n" + "Procedures\n";

    System.out.println(Format.enumeratedContent(types));
    System.out.print("Which object type would you like to see sorted? ");
    int selectedOpt = stdin.nextInt();
    stdin.nextLine();
    checkChosenOption(selectedOpt, List.of(types.split("\n")));

    String sortingCriteria;
    int selectedCriteria;

    switch (selectedOpt) {
      case 1:
        sortingCriteria = "Name\n" + "Hospital\n" + "Clinic\n";
        System.out.println(Format.enumeratedContent(sortingCriteria));
        System.out.print("Which criteria would you like to sort them by? ");
        selectedCriteria = stdin.nextInt();
        stdin.nextLine();
        checkChosenOption(selectedCriteria, List.of(sortingCriteria.split("\n")));
        Stream<MedicalFacility> facilities = service.getMedicalFacilitiesStream();
        switch (selectedCriteria) {
          case 1:
            listObjectGroup(
                facilities.sorted(new MedicalFacilitiesComparators.SortByName()), "facilities");
            break;
          case 2:
            listObjectGroup(
                facilities.sorted(new MedicalFacilitiesComparators.SortByHospital()), "facilities");
            break;
          case 3:
            listObjectGroup(
                facilities.sorted(new MedicalFacilitiesComparators.SortByClinic()), "facilities");
            break;
        }
        break;
      case 2:
        sortingCriteria = "Name\n" + "Balance\n";
        System.out.println(Format.enumeratedContent(sortingCriteria));
        System.out.print("Which criteria would you like to sort them by? ");
        selectedCriteria = stdin.nextInt();
        stdin.nextLine();
        checkChosenOption(selectedCriteria, List.of(sortingCriteria.split("\n")));
        Stream<Patient> patients = service.getPatientsStream();

        switch (selectedCriteria) {
          case 1:
            listObjectGroup(patients.sorted(new PatientComparators.SortByName()), "patients");
            break;
          case 2:
            listObjectGroup(patients.sorted(new PatientComparators.SortByBalance()), "patients");
            break;
        }

        break;
      case 3:
        sortingCriteria = "Name\n" + "Base cost\n" + "Elective\n" + "Non elective\n";
        System.out.println(Format.enumeratedContent(sortingCriteria));
        System.out.print("Which criteria would you like to sort them by? ");
        selectedCriteria = stdin.nextInt();
        stdin.nextLine();
        checkChosenOption(selectedCriteria, List.of(sortingCriteria.split("\n")));
        Stream<Procedure> procedures =
            service.getHospitals().flatMap(hospital -> hospital.getProceduresStream());

        switch (selectedCriteria) {
          case 1:
            listObjectGroup(procedures.sorted(new ProcedureComparators.SortByName()), "procedures");
            break;
          case 2:
            listObjectGroup(
                procedures.sorted(new ProcedureComparators.SortByPrice().reversed()), "procedures");
            break;
          case 3:
            listObjectGroup(
                procedures.sorted(new ProcedureComparators.SortByElective()), "procedures");
            break;
          case 4:
            listObjectGroup(
                procedures.sorted(new ProcedureComparators.SortByElective().reversed()),
                "procedures");
            break;
        }
        break;
    }
  }

	public static void attemptEdit(Object toEdit, Scanner stdin){
		System.out.println("Attempting edit");
		Class<?> selectedClass = toEdit.getClass();
		Field[] fields = selectedClass.getDeclaredFields();

		for(Field field: fields){
			Editable editable = field.getAnnotation(Editable.class);
			if(editable != null){
				System.out.println(field.getName());
				Class<?> fieldType = field.getType();
				System.out.println(fieldType);
				char[] fieldNameChars = field.getName().toCharArray();
				fieldNameChars[0] = Character.toUpperCase(field.getName().charAt(0));
				String setterName = editable.setter().isEmpty() ? "set" + new String(fieldNameChars) : editable.setter();
				
				System.out.println(setterName);
				boolean validInput = false;

					do {
						try {
							Method setter;
							
							StringBuilder messageBuilder = new StringBuilder();
							if(editable.message().isEmpty()){
								messageBuilder.append("Please input new value to set for ").append(field.getName()).append(" ");
							} else {
								messageBuilder.append(editable.message()).append(" ");
							}
							System.out.print(messageBuilder.toString());

								if(fieldType.equals(double.class)){
									setter = selectedClass.getMethod(setterName, double.class);
									setter.invoke(toEdit, stdin.nextDouble());
									stdin.nextLine();
								} else if(fieldType.equals(String.class)){
									setter = selectedClass.getMethod(setterName, String.class);
									setter.invoke(toEdit, stdin.nextLine());
								} else if(fieldType.equals(int.class)){
									setter = selectedClass.getMethod(setterName, int.class);
									setter.invoke(toEdit, stdin.nextInt());
									stdin.nextLine();
								} else if(fieldType.equals(char.class)){
									setter = selectedClass.getMethod(setterName, char.class);
									setter.invoke(toEdit, stdin.next().charAt(0));
									stdin.nextLine();
								} else if(fieldType.equals(boolean.class)){
									setter = selectedClass.getMethod(setterName, boolean.class);
									String input = stdin.nextLine();
									boolean isYes = testYesNo(input);
									setter.invoke(toEdit, isYes);
								}

								validInput = true;
							System.out.println(toEdit.toString());
						} catch (NoSuchMethodException e){
							throw new RuntimeException("There is no setter for this field!");
						} catch (InvocationTargetException e){
							System.err.println(e.getMessage());
						} catch(IllegalAccessException e){
							System.err.println("Setters should be public");
						} 
						catch(InvalidYesNoException e){
							System.err.println(e.getMessage());
						}
				} while(!validInput); 
			}
		}
	}

	public static void editObject(HealthService service, Scanner stdin) throws InvalidOptionException {
		String types = "Health Service\n" + "Hospital\n" + "Clinic\n" + "Patient\n" + "Procedure\n"; 
		System.out.println(Format.enumeratedContent(types));
		System.out.print("Select the type of object you wish to edit: ");
		int selectedType = stdin.nextInt();
		stdin.nextLine();
		checkChosenOption(selectedType, List.of(types.split("\n")));

		switch(selectedType){
			case 1: 
				attemptEdit(service, stdin);
				break;
			case 2: 
				String clinics = getObjectStreamDetails(service.getClinics(), "clinics");
				System.out.println(Format.enumeratedContent(clinics, 1));
				int clinicToEdit = stdin.nextInt();
				stdin.nextLine();
				checkChosenOption(clinicToEdit, List.of(clinics.split("\n")));
				attemptEdit(service.getClinics().toList().get(clinicToEdit - 1), stdin);
				break;
			case 3: 
				String hospitals = getObjectStreamDetails(service.getHospitals(), "hospitals");				
				System.out.println(Format.enumeratedContent(hospitals, 1));
				int hospitalToEdit = stdin.nextInt();
				stdin.nextLine();
				checkChosenOption(hospitalToEdit, List.of(hospitals.split("\n")));
				attemptEdit(service.getHospitals().toList().get(hospitalToEdit - 1), stdin);
				break;
			case 4: 
				String patients = getObjectStreamDetails(service.getPatientsStream(), "patients");				
				System.out.println(Format.enumeratedContent(patients, 1));
				int patientToEdit = stdin.nextInt();
				stdin.nextLine();
				checkChosenOption(patientToEdit, List.of(patients.split("\n")));
				attemptEdit(service.getPatientsStream().toList().get(patientToEdit - 1), stdin);
				break;
			case 5: 
				List<Procedure> proceduresList = service.getHospitals().flatMap(hospital -> hospital.getProceduresStream()).toList();
				String procedures = getObjectStreamDetails(proceduresList.stream(), "patients");				
				System.out.println(Format.enumeratedContent(procedures, 1));
				int procedureToEdit = stdin.nextInt();
				stdin.nextLine();
				checkChosenOption(procedureToEdit, List.of(procedures.split("\n")));
				attemptEdit(proceduresList.get(procedureToEdit - 1), stdin);
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
        simulateVisit(service, stdin);
        break;
      case OPERATE:
        operate(service, stdin);
        break;
			case SORTED: 
				sortObjects(service, stdin);
				break;
      case LIST:
        listObjects(service, stdin);
        break;
			case EDIT: 
				editObject(service, stdin);
				break;
      case QUIT:
        System.exit(0);
    }
  }

  public static boolean checkOption(int inputOption) throws InvalidOptionException {
    ConsoleOption[] optionValues = ConsoleOption.values();

    if (inputOption - 1 >= optionValues.length) {
      throw new InvalidOptionException(
          String.format(
              "This option is invalid, please input a number from [%d-%d]",
              1, optionValues.length));
    } else if (inputOption == optionValues[inputOption - 1].getValue()) {
      return true;
    }

    throw new InvalidOptionException(
        String.format(
            "This option is invalid, please input a number from [%d-%d]", 1, optionValues.length));
  }

  public static void main(String[] args) {
    HealthService service = new HealthService();
    Hospital hospital = new Hospital(service.next(), "TestHospital");
    Clinic clinic = new Clinic(service.next(), "Croix", 1000, 0.3);
    Patient patient = new Patient(service.next(), "Mark", false);
    hospital.addProcedure(
        new Procedure(
            service.next(), "MRI scan", "Magnetic Resonance Imaging scan of patient", false, 700));
    hospital.addProcedure(
        new Procedure(service.next(), "Radiological Inspection", "X-ray of patient", true, 300));
    service.addMedicalFacility(hospital);
    service.addMedicalFacility(clinic);
    service.addPatient(patient);

    Scanner stdin = new Scanner(System.in);

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
				stdin.nextLine();
      } catch (InvalidOptionException exception) {
        System.err.println(exception.getMessage() + "\n");
      } catch (InvalidYesNoException exception) {
        System.err.println(exception.getMessage() + "\n");
      }

    } while (true);
  }
}
