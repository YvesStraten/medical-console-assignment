package com.yvesstraten.medicalconsole;

import com.yvesstraten.medicalconsole.comparators.MedicalFacilitiesComparators;
import com.yvesstraten.medicalconsole.comparators.PatientComparators;
import com.yvesstraten.medicalconsole.comparators.ProcedureComparators;
import com.yvesstraten.medicalconsole.exceptions.ClassIsNotEditableException;
import com.yvesstraten.medicalconsole.exceptions.NoHospitalsAvailableException;
import com.yvesstraten.medicalconsole.exceptions.WrongHospitalException;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Main driver class for the medical console program
 *
 * @author Yves Straten e2400068
 */
public class MedicalConsole {
  /**
   * Default constructor for MedicalConsole
   *
   * @throws UnsupportedOperationException always
   */
  public MedicalConsole() {
    throw new UnsupportedOperationException(
        "This is the main class for the medical console terminal program." 
			+ " It is not meant to be"
      + " constructed!");
  }

  /** This enum lists all possible main options available in this program */
  public enum ConsoleOption {
    /** Add an object of type */
    ADD("Add a service"),
    /** List an object of type */
    LIST("List all objects"),
    /** Delete an object */
    DELETE("Delete an object"),
    /** Simulate a visit */
    SIMULATE("Simulate a visit"),
    /** Simulate an operation */
    OPERATE("Operate a patient"),
    /** Sort an object of type */
    SORTED("Sort objects"),
    /** Edit an object of type */
    EDIT("Edit objects"),
    /** Quit program */
    QUIT("Quit program");

    // Name of option
    private final String optionName;

    /**
     * Construct an enum option
     *
     * @param optionName associated name for this option
     */
    private ConsoleOption(String optionName) {
      this.optionName = optionName;
    }

    /**
     * Get option name
     *
     * @return option name
     */
    public String getOptionName() {
      return this.optionName;
    }
  }

  /** Prints an introduction for users when first starting the program */
  public static void printIntroduction() {
    ConsoleOption[] declaredPanes = ConsoleOption.values();
    String options =
        Arrays.stream(declaredPanes)
            .map(pane -> pane.getOptionName())
            .reduce("", (before, next) -> before + next + "\n");
    System.out.println("Welcome to HELP Medical console");
    System.out.println("What would you like to do today?");
    System.out.println();

    System.out.println(Format.enumeratedContent(options));
  }

  /**
   * Control flow for when a user wishes to add a clinic to the service
   *
   * @param service Health service to add clinic to
   * @param stdin standard in, preferably set to <code>System.in</code>
   */
  public static void addClinic(HealthService service, Scanner stdin) {
    String name = Input.getString("What is the name of the clinic?", stdin);
    double fee = Input.getDouble("What is the fee of the clinic?", stdin);
    double gapPercent = Input.getDouble("What is the gap percent of the clinic?", stdin);

    service.initializeClinic(name, fee, gapPercent);
    System.out.println("Added clinic successfully!");
    System.out.println();
  }

  /**
   * Control flow for when a user wishes to add a hospital to the service
   *
   * @param service Health service to add clinic to
   * @param stdin standard in, preferably set to <code>System.in</code>
   */
  public static void addHospital(HealthService service, Scanner stdin) {
    String name = Input.getString("What is the name of the hospital?", stdin);
    service.initializeHospital(name);
    System.out.println("Added hospital successfully!");
    System.out.println();
  }

  /**
   * Control flow for when a user wishes to add a patient to the service
   *
   * @param service Health service to add clinic to
   * @param stdin standard in, preferably set to <code>System.in</code>
   */
  public static void addPatient(HealthService service, Scanner stdin) {
    String name = Input.getString("What is the name of the patient?", stdin);
    boolean isPrivate = Input.getYesNoInput("Is the patient private? [y/n]", stdin);

    service.initializePatient(name, isPrivate);
    System.out.println("Successfully added new patient!");
    System.out.println();
  }

  /**
   * Control flow for when a user wishes to add a procedure to the service
   *
   * @param service Health service to add clinic to
   * @param stdin standard in, preferably set to <code>System.in</code>
   * @throws NoHospitalsAvailableException if no hospitals are managed by the service
   */
  public static void addProcedure(HealthService service, Scanner stdin)
      throws NoHospitalsAvailableException {
    if (service.getHospitals().count() == 0) {
      throw new NoHospitalsAvailableException();
    }

    List<Hospital> filteredHospitals = service.getHospitals().toList();
    listObjectGroup(service.getHospitals(), "hospitals");

    int chosenHospital =
        Input.chooseOption(
            "Please select a hospital to add the procedure to:", filteredHospitals.size(), stdin);

    Hospital hospital = filteredHospitals.get(chosenHospital - 1);

    String name = Input.getString("Name of procedure?", stdin);
    String description = Input.getString("Description of procedure?", stdin);
    boolean isElective = Input.getYesNoInput("Is the procedure elective? [y/n]", stdin);
    double basicCost = Input.getDouble("What is the basic cost of the procedure?", stdin);

    service.initializeProcedure(hospital, name, description, isElective, basicCost);
    System.out.println("Added procedure successfully!");
    System.out.println();
  }

  /**
   * Control flow for when a user wishes to add an object the service. Possible objects are:
   *
   * <ul>
   *   <li>Medical facility
   *   <li>
   *       <ul>
   *         <li>Clinic
   *         <li>Hospital
   *       </ul>
   *   <li>Patient
   *   <li>Procedure
   * </ul>
   *
   * @param service Health service to add clinic to
   * @param stdin standard in, preferably set to <code>System.in</code>
   */
  public static void addObject(HealthService service, Scanner stdin) {
    String[] mainOptions = new String[] {"Medical facility", "Patient", "Procedure"};

    System.out.println(Format.enumeratedContent(mainOptions));

    int chosenOption =
        Input.chooseOption("Which service would you like to add?", mainOptions.length, stdin);

    switch (chosenOption) {
      case 1:
        System.out.println("The following medical facilities are available");
        String facilityOptions = "Clinic\n" + "Hospital\n";

        System.out.println(Format.enumeratedContent(facilityOptions));
        int chosenFacilityOption =
            Input.chooseOption(
                "Which medical facility would you like to add?",
                facilityOptions.split("\n").length,
                stdin);

        switch (chosenFacilityOption) {
          case 1:
            addClinic(service, stdin);
            break;
          case 2:
            addHospital(service, stdin);
            break;
        }

        break;
      case 2:
        addPatient(service, stdin);
        break;
      case 3:
        try {
          addProcedure(service, stdin);
        } catch (NoHospitalsAvailableException e) {
          System.err.println(e);
          addHospital(service, stdin);
        }
    }
  }

  /**
   * Control flow for when a user wishes to delete a patient from the service
   *
   * @param service Health service to delete from
   * @param stdin standard in, preferably set to <code>System.in</code>
   */
  public static void deletePatient(HealthService service, Scanner stdin) {
    int numPatients = service.getPatients().size();
    listObjectGroup(service.getPatientsStream(), "patients");

    if (numPatients != 0) {
      int patientToRemove =
          Input.chooseOption("Which patient would you like to remove?", numPatients, stdin);
      service.deletePatient(patientToRemove - 1);
      System.out.println("Removed patient successfully!");
    }
  }

  /**
   * Control flow for when a user wishes to delete a facility
   *
   * @param service Health service to delete from
   * @param stdin standard in, preferably set to <code>System.in</code>
   */
  public static void deleteFacility(HealthService service, Scanner stdin) {
    int numFacilities = service.getMedicalFacilities().size();
    listObjectGroup(service.getMedicalFacilitiesStream(), "facilities");

    if (numFacilities != 0) {
      int facilityToDelete =
          Input.chooseOption("Which facility would you like to delete?", numFacilities, stdin);
      MedicalFacility facilityToBeDeleted =
          service.getMedicalFacilities().get(facilityToDelete - 1);
      if (facilityToBeDeleted instanceof Hospital) {
        int numProcedures = ((Hospital) facilityToBeDeleted).getProcedures().size();
        if (numProcedures > 0) {
          String prompt =
              "This hospital can perform "
                  + numProcedures
                  + " procedures - do you still wish to delete it? [y/n]";
          if (Input.getYesNoInput(prompt, stdin)) {
            service.deleteMedicalFacility(facilityToDelete - 1);
          }
        }
      } else {
        service.deleteMedicalFacility(facilityToDelete - 1);
      }
      System.out.println("Removed facility successfully!");
    }
  }

  /**
   * Control flow for when a user wishes to delete a procedure
   *
   * @param service Health service to delete from
   * @param stdin standard in, preferably set to <code>System.in</code>
   */
  public static void deleteProcedure(HealthService service, Scanner stdin) {
    List<Hospital> hospitals = service.getHospitals().toList();
    HashMap<Procedure, Hospital> map = new HashMap<Procedure, Hospital>();

    for (Hospital hospital : hospitals) {
      for (Procedure procedure : hospital.getProcedures()) map.put(procedure, hospital);
    }

    listObjectGroup(map.keySet().stream(), "procedures");

    if (map.keySet().size() != 0) {

      int toDelete =
          Input.chooseOption("Choose a procedure to remove:", map.keySet().size(), stdin);

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
  }

  /**
   * Deletes an object from the provided HealthService this object can either be a {@link
   * MedicalFacility}, {@link Patient} or {@link Procedure}.
   *
   * @param service <code>HealthService</code> object to delete an object from
   * @param stdin <code>Scanner</code> object which preferably iterates over <code>System.in</code>
   */
  public static void deleteObject(HealthService service, Scanner stdin) {
    System.out.println("The following types of services are available: ");
    String[] types = new String[] {"Medical facility", "Patient", "Procedure"};

    System.out.println(Format.enumeratedContent(types));
    int chosenOption =
        Input.chooseOption("Which type of object would you like to delete?", types.length, stdin);

    switch (chosenOption) {
      case 1:
        deleteFacility(service, stdin);
        break;
      case 2:
        deletePatient(service, stdin);
        break;
      case 3:
        deleteProcedure(service, stdin);
        break;
    }
  }

  /**
   * Gets details of provided object stream
   *
   * @param <T> type of objects present in the stream
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

  /**
   * Print provided object stream details to the console
   *
   * @param streamFrom stream to get details from
   * @param name name to be added to description
   * @param <T> type of stream
   * @see #getObjectStreamDetails(Stream, String)
   */
  public static <T> void listObjectGroup(Stream<T> streamFrom, String name) {
    String output = getObjectStreamDetails(streamFrom, name);
    System.out.println(Format.enumeratedContent(output, 1));
  }

  /**
   * Control flow for when the user wishes to list an object
   *
   * @param service health service to list from
   * @param stdin standard in, preferably set to <code>System.in</code>
   */
  public static void listObjects(HealthService service, Scanner stdin) {
    System.out.println("Which type of object would you like to see listed?");
    String[] options = new String[] {"Medical Facilities", "Patients", "Procedures"};
    System.out.println(Format.enumeratedContent(options));

    int chosenOption = Input.chooseOption("Please select a type:", options.length, stdin);

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
  }

  /**
   * Control flow for when the user wishes to simulate a visit
   *
   * @param service health service to simulate visit on
   * @param stdin standard in, preferably set to <code>System.in</code>
   */
  public static void simulateVisit(HealthService service, Scanner stdin) {
    listObjectGroup(service.getPatientsStream(), "patients");
    int chosenPatient =
        Input.chooseOption(
            "Please choose a patient to simulate a visit:", service.getPatients().size(), stdin);

    listObjectGroup(service.getMedicalFacilitiesStream(), "facilities");
    int chosenFacility =
        Input.chooseOption(
            "Please choose a facility the patient should visit:",
            service.getMedicalFacilities().size(),
            stdin);
    MedicalFacility chosenFacilityObject = service.getMedicalFacilities().get(chosenFacility - 1);
    Patient chosenPatientObject = service.getPatients().get(chosenPatient - 1);

    boolean successfullVisit =
        chosenFacilityObject.visit(service.getPatients().get(chosenPatient - 1));

    if (successfullVisit)
      System.out.println(
          chosenPatientObject.getName()
              + " successfully visited "
              + chosenFacilityObject.getName());
    else
      System.out.println(
      chosenPatientObject.getName() 
			+ " failed to visit " 
			+ chosenFacilityObject.getName());
  }

  /**
   * Control flow for when a user wishes to simulate an operation/procedure on a patient that is
   * currently in the selected hospital
   *
   * @param service health service to use
   * @param stdin standard in, preferably set to <code>System.in</code>
   */
  public static void operate(HealthService service, Scanner stdin) {
    listObjectGroup(service.getHospitals(), "hospitals");
    int selectedHospitalIndex =
        Input.chooseOption(
            "Please select which hospital to operate in:",
            service.getHospitals().toList().size(),
            stdin);

    listObjectGroup(service.getPatientsStream(), "patients");
    System.out.print("Please select which patient to operate: ");
    int selectedPatientIndex =
        Input.chooseOption(
            "Please select which patient to operate:", service.getPatients().size(), stdin);

    Hospital selectedHospital = service.getHospitals().toList().get(selectedHospitalIndex - 1);
    Patient selectedPatient = service.getPatients().get(selectedPatientIndex - 1);

    try {
      if (selectedPatient.isInThisHospital(selectedHospital)) {
        listObjectGroup(selectedHospital.getProceduresStream(), "procedures");
        int selectedProcedureIndex =
            Input.chooseOption(
                "Please select which procedure to undertake:",
                selectedHospital.getProcedures().size(),
                stdin);

        Procedure selectedProcedure =
            selectedHospital.getProcedures().get(selectedProcedureIndex - 1);

        double cost = Hospital.getOperationCost(selectedPatient, selectedProcedure);
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

  /**
   * Control flow for when the user wishes to sort objects
   *
   * @param service health service to sort from
   * @param stdin standard in preferably set to <code>System.in</code>
   */
  public static void sortObjects(HealthService service, Scanner stdin) {
    String[] types = new String[] {"Medical Facilities", "Patients", "Procedures"};

    System.out.println(Format.enumeratedContent(types));
    System.out.print("Which object type would you like to see sorted? ");
    int selectedOpt =
        Input.chooseOption("Which object type would you like to see sorted?", types.length, stdin);

    String[] sortingCriteria;
    int selectedCriteria;

    switch (selectedOpt) {
      case 1:
        // Sort medical facilities
        sortingCriteria = new String[] {"Name", "Hospital", "Clinic"};
        System.out.println(Format.enumeratedContent(sortingCriteria));
        System.out.print("Which criteria would you like to sort them by? ");
        selectedCriteria =
            Input.chooseOption(
                "Which criteria would you like to sort them by?", sortingCriteria.length, stdin);
        Stream<MedicalFacility> facilities = service.getMedicalFacilitiesStream();
        // Invoke comparators according to input
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
        // Sort patients
        sortingCriteria = new String[] {"Name", "Balance"};
        System.out.println(Format.enumeratedContent(sortingCriteria));
        selectedCriteria =
            Input.chooseOption(
                "Which criteria would you like to sort them by?", sortingCriteria.length, stdin);
        Stream<Patient> patients = service.getPatientsStream();

        switch (selectedCriteria) {
          // Invoke comparators according to input
          case 1:
            listObjectGroup(patients.sorted(new PatientComparators.SortByName()), "patients");
            break;
          case 2:
            listObjectGroup(patients.sorted(new PatientComparators.SortByBalance()), "patients");
            break;
        }

        break;
      case 3:
        // Sort procedures
        sortingCriteria = new String[] {"Name", "Base cost", "Elective", "Non elective"};
        System.out.println(Format.enumeratedContent(sortingCriteria));
        selectedCriteria =
            Input.chooseOption(
                "Which criteria would you like to sort them by?", sortingCriteria.length, stdin);
        Stream<Procedure> procedures =
            service.getHospitals().flatMap(hospital -> hospital.getProceduresStream());

        switch (selectedCriteria) {
          // Invoke comparators according to input
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

  /**
   * Helper function to aid the application in editing objects in the service in-place
   *
   * @param toEdit object that should be edited
   * @param stdin standard in source preferably <code>System.in</code>
   * @throws ClassIsNotEditableException if the class has no {@link Editable} annotation
   */
  public static void attemptEdit(Object toEdit, Scanner stdin) throws ClassIsNotEditableException {
    System.out.println("Attempting edit");
    Class<?> selectedClass = toEdit.getClass();
    // All fields, including private ones
    List<Field> fields = new ArrayList<Field>();

    // Get fields of superclass, if any
    Class<?> superClass = selectedClass.getSuperclass();
    while (superClass != null) {
      // Fields are usually private
      Field[] superClassFields = superClass.getDeclaredFields();
      for (Field superClassField : superClassFields) {
        fields.add(superClassField);
      }

      // Repeat process until superclasses are exhausted
      superClass = superClass.getSuperclass();
    }

    // Get fields
    Field[] fetchedFields = selectedClass.getDeclaredFields();
    for (Field fetched : fetchedFields) {
      fields.add(fetched);
    }

    List<Field> editableFiltered =
        fields.stream().filter(field -> field.getAnnotation(Editable.class) != null).toList();
    if (editableFiltered.size() == 0) {
      // No Editable annotation was present
      throw new ClassIsNotEditableException();
    } else {
      for (Field editableField : editableFiltered) {
        Editable editable = editableField.getAnnotation(Editable.class);
        // Field type
        Class<?> fieldType = editableField.getType();
        char[] fieldNameChars = editableField.getName().toCharArray();
        fieldNameChars[0] = Character.toUpperCase(editableField.getName().charAt(0));
        // Standard way to name setters
        String setterName =
            editable.setter().isEmpty() ? "set" + new String(fieldNameChars) : editable.setter();

        boolean validInput = false;

        do {
          try {
            Method setter;

            StringBuilder messageBuilder = new StringBuilder();
            if (editable.message().isEmpty()) {
              // Annotation was not provided a message to output
              messageBuilder
                  .append("Please input new value to set for")
                  .append(" ")
                  .append(editableField.getName());
            } else {
              // Annotation was provided a message to output
              messageBuilder.append(editable.message());
            }

            String prompt = messageBuilder.toString();

            // Check the type of field, invoke the respective setter
						// with name and appropriate input from the scanner
            if (fieldType.equals(double.class)) {
              setter = selectedClass.getMethod(setterName, double.class);
              setter.invoke(toEdit, Input.getDouble(prompt, stdin));
            } else if (fieldType.equals(String.class)) {
              setter = selectedClass.getMethod(setterName, String.class);
              setter.invoke(toEdit, Input.getString(prompt, stdin));
            } else if (fieldType.equals(int.class)) {
              setter = selectedClass.getMethod(setterName, int.class);
              setter.invoke(toEdit, Input.getInt(prompt, stdin));
            } else if (fieldType.equals(char.class)) {
              setter = selectedClass.getMethod(setterName, char.class);
              setter.invoke(toEdit, Input.getChar(prompt, stdin));
            } else if (fieldType.equals(boolean.class)) {
              setter = selectedClass.getMethod(setterName, boolean.class);
              setter.invoke(toEdit, Input.getYesNoInput(prompt, stdin));
            }

            validInput = true;
          } catch (NoSuchMethodException e) {
            throw new RuntimeException("There is no setter for this field!");
          } catch (InvocationTargetException e) {
            System.err.println(e.getMessage());
          } catch (IllegalAccessException e) {
            System.err.println("Setters should be public");
          }
        } while (!validInput);
      }
      System.out.println("Edited object successfully! Results:");
      System.out.println(toEdit.toString());
      System.out.println();
    }
  }

  /**
   * Control flow for when a user wishes to edit an object
   *
   * @param service service to edit from
   * @param stdin standard in source, preferably <code>System.in</code>
   */
  public static void editObject(HealthService service, Scanner stdin) {
    String[] types = new String[] {"Health Service", "Clinic", "Hospital", "Patient", "Procedure"};
    System.out.println(Format.enumeratedContent(types));
    int selectedType =
        Input.chooseOption("Select the type of object you wish to edit:", types.length, stdin);

    try {
      switch (selectedType) {
        case 1:
          attemptEdit(service, stdin);
          break;
        case 2:
          String clinics = getObjectStreamDetails(service.getClinics(), "clinics");
          System.out.println(Format.enumeratedContent(clinics, 1));
          int clinicToEdit =
              Input.chooseOption(
                  "Please select a clinic to edit:", service.getClinics().toList().size(), stdin);
          attemptEdit(service.getClinics().toList().get(clinicToEdit - 1), stdin);
          break;
        case 3:
          String hospitals = getObjectStreamDetails(service.getHospitals(), "hospitals");
          System.out.println(Format.enumeratedContent(hospitals, 1));
          int hospitalToEdit =
              Input.chooseOption(
                  "Please select a hospital to edit:",
                  service.getHospitals().toList().size(),
                  stdin);
          attemptEdit(service.getHospitals().toList().get(hospitalToEdit - 1), stdin);
          break;
        case 4:
          String patients = getObjectStreamDetails(service.getPatientsStream(), "patients");
          System.out.println(Format.enumeratedContent(patients, 1));
          int patientToEdit =
              Input.chooseOption(
                  "Please select a patient to edit:", service.getPatients().size(), stdin);
          attemptEdit(service.getPatients().get(patientToEdit - 1), stdin);
          break;
        case 5:
          List<Procedure> proceduresList =
              service.getHospitals().flatMap(hospital -> hospital.getProceduresStream()).toList();
          String procedures = getObjectStreamDetails(proceduresList.stream(), "patients");
          System.out.println(Format.enumeratedContent(procedures, 1));
          System.out.print("");
          int procedureToEdit =
              Input.chooseOption(
                  "Please select a procedure to edit:", proceduresList.size(), stdin);
          attemptEdit(proceduresList.get(procedureToEdit - 1), stdin);
          break;
      }
    } catch (ClassIsNotEditableException e) {
      System.err.println(e.getMessage());
    }
  }

  /**
   * Executes an available main option given an integer input
   *
   * @param inputOption selected option number
   * @param service healthservice to run option under
   * @param stdin standard preferably set to <code>System.in</code>
   */
  public static void executeOption(int inputOption, HealthService service, Scanner stdin) {
    ConsoleOption selectedOption = ConsoleOption.values()[inputOption - 1];
    switch (selectedOption) {
      case ADD:
        addObject(service, stdin);
        break;
      case DELETE:
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

  /**
   * Generates sample data for running this application
   *
   * @return HealthService which includes all generated sample data
   */
  public static HealthService generateSampleData() {
    // Starting service
    HealthService service = new HealthService();
    // Id generator
    Iterator<Integer> idDispenser = service.getIdDispenser();

    // Adding starting objects
    Hospital hospital = new Hospital(idDispenser.next(), "TestHospital");
    Clinic clinic = new Clinic(idDispenser.next(), "Croix", 1000, 0.3);
    Patient patient = new Patient(idDispenser.next(), "Mark", false);
    service.addProcedure(
        hospital,
        new Procedure(
            idDispenser.next(),
            "MRI scan",
            "Magnetic Resonance Imaging scan of patient",
            false,
            700));
    service.addProcedure(
        hospital,
        new Procedure(
            idDispenser.next(),
			      "Radiological Inspection", 
			      "X-ray of patient", 
			      true, 
			      300));
    service.addMedicalFacility(hospital);
    service.addMedicalFacility(clinic);
    service.addPatient(patient);

    return service;
  }

  /**
   * Main entry point for the medical console
   *
   * @param args arguments passed
   */
  public static void main(String[] args) {
    HealthService service = generateSampleData();
    Scanner stdin = new Scanner(System.in);
    boolean done = false;

    do {
      printIntroduction();
      int inputOption =
          Input.chooseOption("Select an option:", ConsoleOption.values().length, stdin);
      executeOption(inputOption, service, stdin);
    } while (!done);
  }
}
