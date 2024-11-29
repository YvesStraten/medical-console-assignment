package com.yvesstraten.medicalconsole.operations;

import java.util.List;
import java.util.Scanner;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.Input;
import com.yvesstraten.medicalconsole.exceptions.NoHospitalsAvailableException;
import com.yvesstraten.medicalconsole.facilities.Hospital;

public class AddOperations {
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
    String name = Input.getString("What is the name of the patient?", 
			stdin);
    boolean isPrivate = Input
		.getYesNoInput("Is the patient private? [y/n]", 
			stdin);

    service.initializePatient(name, isPrivate);
    System.out.println("Successfully added new patient!");
    System.out.println();
  }

  /**
   * Control flow for when a user wishes to add a procedure to the service
   *
   * @param service Health service to add clinic to
   * @param stdin standard in, preferably set to <code>System.in</code>
   * @throws NoHospitalsAvailableException if no hospitals 
	 * are managed by the service
   */
  public static void addProcedure(HealthService service, Scanner stdin)
      throws NoHospitalsAvailableException {
    if (service.getHospitals().count() == 0) {
      throw new NoHospitalsAvailableException();
    }

    List<Hospital> filteredHospitals = service
		.getHospitals()
		.toList();

    ListOperations
			.listObjectGroup(service.getHospitals(), "hospitals");

    int chosenHospital =
        Input.chooseOption(
            "Please select a hospital to add the procedure to:",
			filteredHospitals.size(),
			stdin);

    Hospital hospital = filteredHospitals.get(chosenHospital - 1);

    String name = Input.getString("Name of procedure?", stdin);
    String description = Input.getString("Description of procedure?", stdin);
    boolean isElective = Input
		.getYesNoInput("Is the procedure elective? [y/n]", 
			stdin);
    double basicCost = Input
		.getDouble("What is the basic cost of the procedure?",
			stdin);

    service.initializeProcedure(hospital,
			name,
			description,
			isElective,
			basicCost);
    System.out.println("Added procedure successfully!");
    System.out.println();
  }

}
