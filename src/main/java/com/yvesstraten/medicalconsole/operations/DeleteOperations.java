package com.yvesstraten.medicalconsole.operations;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.Input;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import com.yvesstraten.medicalconsole.facilities.Procedure;

public class DeleteOperations {
  /**
   * Control flow for when a user wishes to delete a patient from the service
   *
   * @param service Health service to delete from
   * @param stdin standard in, preferably set to <code>System.in</code>
   */
  public static void deletePatient(HealthService service, Scanner stdin) {
    int numPatients = service.getPatients().size();
    if(numPatients == 0){
      System.out.println("No patients have been added yet!");
      return;
    }

    ListOperations
      .listObjectGroup(service.getPatientsStream(), "patients");

    int patientToRemove =
        Input.chooseOption("Which patient would you like to remove?", 
      numPatients,
      stdin);
    service.deletePatient(patientToRemove - 1);
    System.out.println("Removed patient successfully!");
  }

  /**
   * Control flow for when a user wishes to delete a facility
   *
   * @param service Health service to delete from
   * @param stdin standard in, preferably set to <code>System.in</code>
   */
  public static void deleteFacility(HealthService service, Scanner stdin) {
    int numFacilities = service.getMedicalFacilities().size();
    if(numFacilities == 0){
      System.out.println("No facilities have been added yet!");
      return;
    }

    ListOperations
      .listObjectGroup(service.getMedicalFacilitiesStream(),
        "facilities");

    int facilityToDelete =
        Input.chooseOption("Which facility would you like to delete?",
      numFacilities,
      stdin);

    MedicalFacility facilityToBeDeleted =
        service.getMedicalFacilities().get(facilityToDelete - 1);
    if (facilityToBeDeleted instanceof Hospital) {
      Hospital hospitalToBeDeleted = 
      (Hospital) facilityToBeDeleted;

      int numProcedures = ((Hospital) hospitalToBeDeleted)
      .getProcedures()
      .size();

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

  /**
   * Control flow for when a user wishes to delete a procedure
   *
   * @param service Health service to delete from
   * @param stdin standard in, preferably set to <code>System.in</code>
   */
  public static void deleteProcedure(HealthService service, Scanner stdin) {
    List<Hospital> hospitals = service.getHospitals().toList();
    // Map procedures to related hospitals
    HashMap<Procedure, Hospital> map = new HashMap<Procedure, Hospital>();

    // Grab every procedure and map it to the hospital
    for (Hospital hospital : hospitals) {
      for (Procedure procedure : hospital.getProcedures()) {
        map.put(procedure, hospital);
      }
    }

    if(map.keySet().size() == 0){
      System.out.println("No patients have been added yet!");
      return;
    }

    ListOperations
      .listObjectGroup(map.keySet().stream(), "procedures");

    int toDelete =
        Input.chooseOption("Choose a procedure to remove:", 
      map.keySet().size(), 
      stdin);

    int i = 0;
    Iterator<Entry<Procedure, Hospital>> iterator = 
    map.entrySet().iterator();
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
