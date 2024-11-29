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
import com.yvesstraten.medicalconsole.operations.AddOperations;
import com.yvesstraten.medicalconsole.operations.DeleteOperations;
import com.yvesstraten.medicalconsole.operations.EditOperations;
import com.yvesstraten.medicalconsole.operations.ListOperations;

import java.util.Iterator;
import java.util.List;
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

  /**
   * Control flow for when a user wishes to add an object the service.
   * Possibilities:
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
    String[] mainOptions = new String[] {
      "Medical facility",
      "Patient",
      "Procedure"
    };

    System.out.println(Format.enumeratedContent(mainOptions));

    int chosenOption =
        Input.chooseOption("Which service would you like to add?", 
      mainOptions.length, 
      stdin);

    switch (chosenOption) {
      case 1:
        System.out.println("The following medical facilities are available");
        String[] facilityOptions = new String[] {
        "Clinic",
        "Hospital"
      };

        System.out.println(Format.enumeratedContent(facilityOptions));
        int chosenFacilityOption =
            Input.chooseOption(
                "Which medical facility would you like to add?",
                facilityOptions.length,
                stdin);

        switch (chosenFacilityOption) {
          case 1:
            AddOperations.addClinic(service, stdin);
            break;
          case 2:
            AddOperations.addHospital(service, stdin);
            break;
        }

        break;
      case 2:
        AddOperations.addPatient(service, stdin);
        break;
      case 3:
        try {
          AddOperations.addProcedure(service, stdin);
        } catch (NoHospitalsAvailableException e) {
          System.err.println(e);
          AddOperations.addHospital(service, stdin);
        }
    }
  }

  /**
   * Deletes an object from the provided HealthService this object 
   * can either be a {@linkMedicalFacility}, {@link Patient}
   * or {@link Procedure}.
   *
   * @param service <code>HealthService</code> object to 
   * delete an object from
   * @param stdin standard in preferably set to <code>System.in</code>
   */
  public static void deleteObject(HealthService service, Scanner stdin) {
    System.out.println("The following types of services are available: ");
    String[] types = new String[] {
      "Medical facility",
      "Patient",
      "Procedure"
    };

    System.out.println(Format.enumeratedContent(types));
    int chosenOption =
        Input.chooseOption("Which type of object would you like to delete?", 
      types.length, 
      stdin);

    switch (chosenOption) {
      case 1:
        DeleteOperations.deleteFacility(service, stdin);
        break;
      case 2:
        DeleteOperations.deletePatient(service, stdin);
        break;
      case 3:
        DeleteOperations.deleteProcedure(service, stdin);
        break;
    }
  }

  /**
   * Control flow for when the user wishes to list an object
   *
   * @param service health service to list from
   * @param stdin standard in, preferably set to <code>System.in</code>
   */
  public static void listObjects(HealthService service, Scanner stdin) {
    System.out.println("Which type of object would you like to see listed?");
    String[] options = new String[] {
      "Medical Facilities", 
      "Patients",
      "Procedures"
    };

    System.out.println(Format.enumeratedContent(options));

    int chosenOption = Input.chooseOption("Please select a type:", 
      options.length, 
      stdin);

    switch (chosenOption) {
      case 1:
        ListOperations
        .listObjectGroup(
          service.getMedicalFacilitiesStream(), 
          "facilities"
        );
        break;

      case 2:
        ListOperations
        .listObjectGroup(
          service.getPatientsStream(),
          "patients"
        );
        break;

      case 3:
        Stream<Procedure> procedures =
            service.getHospitals().flatMap(Hospital::getProceduresStream);
        ListOperations
        .listObjectGroup(
          procedures,
          "procedures"
        );
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
    ListOperations
     .listObjectGroup(service.getPatientsStream(), "patients");
    int chosenPatient =
        Input.chooseOption(
            "Please choose a patient to simulate a visit:", 
      service.getPatients().size(),
      stdin);

    ListOperations
     .listObjectGroup(service.getMedicalFacilitiesStream(), "facilities");
    int chosenFacility =
        Input.chooseOption(
            "Please choose a facility the patient should visit:",
            service.getMedicalFacilities().size(),
            stdin);
    MedicalFacility chosenFacilityObject = 
    service
    .getMedicalFacilities()
    .get(chosenFacility - 1);

    Patient chosenPatientObject = service
    .getPatients()
    .get(chosenPatient - 1);

    boolean successfullVisit =
        chosenFacilityObject
    .visit(
      service
      .getPatients()
      .get(chosenPatient - 1));

    if (successfullVisit)
      System.out.println(
          chosenPatientObject.getName()
              + " successfully visited "
              + chosenFacilityObject.getName());
    else {
      StringBuilder builder = 
        new StringBuilder(
        chosenPatientObject
        .getName()
      );

      if(chosenFacilityObject instanceof Hospital){
        builder.append(" did not get admitted to ");
      } else if(chosenFacilityObject instanceof Clinic){
        builder.append(" was registered in ");
      }
      
      builder.append(chosenFacilityObject.getName());
      System.out.println(builder.toString());
    }
  }

  /**
   * Control flow for when a user wishes to simulate an
   * operation/procedure on a patient that is
   * currently in the selected hospital
   *
   * @param service health service to use
   * @param stdin standard in, preferably set to <code>System.in</code>
   */
  public static void operate(HealthService service, Scanner stdin) {
    ListOperations
     .listObjectGroup(service.getHospitals(), "hospitals");
    int selectedHospitalIndex =
        Input.chooseOption(
            "Please select which hospital to operate in:",
            service.getHospitals().toList().size(),
            stdin);

    ListOperations
     .listObjectGroup(service.getPatientsStream(), "patients");
    System.out.print("Please select which patient to operate: ");
    int selectedPatientIndex =
        Input.chooseOption(
            "Please select which patient to operate:", 
      service.getPatients().size(), 
      stdin);

    Hospital selectedHospital = service
    .getHospitals()
    .toList()
    .get(selectedHospitalIndex - 1);

    Patient selectedPatient = service
    .getPatients()
    .get(selectedPatientIndex - 1);

    try {
      if (selectedPatient.isInThisHospital(selectedHospital)) {
        ListOperations
          .listObjectGroup(selectedHospital.getProceduresStream(), "procedures");
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
    String[] types = new String[] {
      "Medical Facilities",
      "Patients",
      "Procedures"
    };

    System.out.println(Format.enumeratedContent(types));
    System.out.print("Which object type would you like to see sorted? ");
    int selectedOpt =
        Input.chooseOption("Which object type would you like to see sorted?",
      types.length,
      stdin);

    String[] sortingCriteria;
    int selectedCriteria;

    switch (selectedOpt) {
      case 1:
        // Sort medical facilities
        sortingCriteria = new String[] {"Name", "Hospital", "Clinic"};
        System.out.println(Format.enumeratedContent(sortingCriteria));
        selectedCriteria =
            Input.chooseOption(
              "Which criteria would you " 
              + "like to sort them by?", 
              sortingCriteria.length, 
            stdin);
        Stream<MedicalFacility> facilities =
          service
          .getMedicalFacilitiesStream();
        // Invoke comparators according to input
        switch (selectedCriteria) {
          case 1:
            ListOperations
              .listObjectGroup(
                facilities.sorted(
                  new 
                  MedicalFacilitiesComparators
                  .SortByName()), 
            "facilities");
            break;
          case 2:
            ListOperations
              .listObjectGroup(
                facilities.sorted(
                  new 
                  MedicalFacilitiesComparators
                  .SortByHospital()),
            "facilities");
            break;
          case 3:
            ListOperations
              .listObjectGroup(
                facilities.sorted(
                new 
                MedicalFacilitiesComparators
                .SortByClinic()),
            "facilities");
            break;
        }
        break;
      case 2:
        // Sort patients
        sortingCriteria = new String[] {"Name", "Balance"};
        System.out.println(Format.enumeratedContent(sortingCriteria));
        selectedCriteria =
            Input.chooseOption(
                "Which criteria would you like to sort them by?",
        sortingCriteria.length,
        stdin);
        Stream<Patient> patients = service.getPatientsStream();

        switch (selectedCriteria) {
          // Invoke comparators according to input
          case 1:
            ListOperations
            .listObjectGroup(patients.sorted(
              new PatientComparators
              .SortByName()), 
            "patients");
            break;
          case 2:
            ListOperations
            .listObjectGroup(patients.sorted(
              new PatientComparators
              .SortByBalance()),
            "patients");
            break;
        }

        break;
      case 3:
        // Sort procedures
        sortingCriteria = new String[] {"Name", "Base cost", "Elective", "Non elective"};
        System.out.println(Format.enumeratedContent(sortingCriteria));
        selectedCriteria =
            Input.chooseOption(
                "Which criteria would you like to sort them by?", 
        sortingCriteria.length,
        stdin);
        Stream<Procedure> procedures =
            service.getHospitals().flatMap(hospital -> 
        hospital.getProceduresStream());

        switch (selectedCriteria) {
          // Invoke comparators according to input
          case 1:
            ListOperations
            .listObjectGroup(procedures.sorted(
              new ProcedureComparators
              .SortByName()),
            "procedures");
            break;
          case 2:
            ListOperations.listObjectGroup(
                procedures.sorted(
                new 
                ProcedureComparators
                .SortByPrice()
                .reversed()),
            "procedures");
            break;
          case 3:
            ListOperations
            .listObjectGroup(
                procedures.sorted(
                new ProcedureComparators
                .SortByElective()),
            "procedures");
            break;
          case 4:
            ListOperations
            .listObjectGroup(
                procedures.sorted(
                new ProcedureComparators
                .SortByElective()
                .reversed()),
            "procedures");
            break;
        }
        break;
    }
  }

  /**
   * Control flow for when a user wishes to edit an object
   *
   * @param service service to edit from
   * @param stdin standard in source, preferably <code>System.in</code>
   */
  public static void editObject(HealthService service, Scanner stdin) {
    String[] types = new String[] {
      "Health Service",
      "Clinic",
      "Hospital",
      "Patient",
      "Procedure"
    };
    System.out.println(Format.enumeratedContent(types));
    int selectedType =
        Input.chooseOption("Select the type of object you wish to edit:", types.length, stdin);

    try {
      switch (selectedType) {
        case 1:
          EditOperations
            .attemptEdit(service,
            stdin);
          break;
        case 2:
          String clinics = ListOperations
          .getObjectStreamDetails(service.getClinics(),
          "clinics");
          System.out.println(Format.enumeratedContent(clinics, 1));
          int clinicToEdit =
              Input.chooseOption(
                  "Please select a clinic to edit:", 
          service.getClinics().toList().size(), 
          stdin);
          EditOperations
          .attemptEdit(service
            .getClinics()
            .toList()
            .get(clinicToEdit - 1), 
          stdin);
          break;
        case 3:
          String hospitals = ListOperations
          .getObjectStreamDetails(service.getHospitals(),
          "hospitals");
          System.out.println(Format.enumeratedContent(hospitals, 1));
          int hospitalToEdit =
              Input.chooseOption(
                  "Please select a hospital to edit:",
                  service.getHospitals().toList().size(),
                  stdin);
          EditOperations.attemptEdit(service
              .getHospitals()
              .toList()
              .get(hospitalToEdit - 1), 
          stdin);
          break;
        case 4:
          String patients = ListOperations
            .getObjectStreamDetails(service.getPatientsStream(),
              "patients");
          System.out.println(Format.enumeratedContent(patients, 1));
          int patientToEdit =
              Input.chooseOption(
                  "Please select a patient to edit:", 
          service.getPatients().size(), 
          stdin);

          EditOperations
          .attemptEdit(
            service
            .getPatients()
            .get(patientToEdit - 1), 
          stdin);
          break;
        case 5:
          List<Procedure> proceduresList =
              service.getHospitals()
              .flatMap(hospital -> 
                        hospital
                        .getProceduresStream())
              .toList();
          String procedures = ListOperations
            .getObjectStreamDetails(proceduresList.stream(),
              "patients");
          System.out.println(Format.enumeratedContent(procedures, 1));
          System.out.print("");
          int procedureToEdit =
              Input.chooseOption(
                  "Please select a procedure to edit:", 
          proceduresList.size(),
          stdin);
          EditOperations
            .attemptEdit(proceduresList
            .get(procedureToEdit - 1),
            stdin);
          break;
      }
    } catch (ClassIsNotEditableException e) {
      System.err.println(e.getMessage());
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
    Patient patient1 = new Patient(idDispenser.next(), "Mark", false, 1000);
    Patient patient2 = new Patient(idDispenser.next(), "John", true);
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
    service.addPatient(patient1);
    service.addPatient(patient2);

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
      String[] options = { 
        "Add a service", 
        "List all objects", 
        "Delete an object", 
        "Simulate a visit", 
        "Operate a patient", 
        "Sort objects", 
        "Edit objects", 
        "Quit program"
      };

      System.out.println("Welcome to HELP Medical console");
      System.out.println("What would you like to do today?");
      System.out.println();

      System.out.println(Format.enumeratedContent(options));
      int inputOption =
          Input.chooseOption("Select an option:", options.length, stdin);

      switch (inputOption) {
        case 1:
          addObject(service, stdin);
          break;
        case 2:
          listObjects(service, stdin);
          break;
        case 3:
          deleteObject(service, stdin);
          break;
        case 4:
          simulateVisit(service, stdin);
          break;
        case 5:
          operate(service, stdin);
          break;
        case 6:
          sortObjects(service, stdin);
          break;
        case 7:
          editObject(service, stdin);
          break;
        case 9:
          System.exit(0);
      }
    } while (!done);
  }
}
