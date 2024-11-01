package com.yvesstraten.medicalconsole.tests;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;

@DisplayName("HealthService Tests")
public class HealthServiceTests {
	// TODO: Add test case when sure
	@Test 
	public void toStringExpectedOutput(){
		ArrayList<MedicalFacility> facilities = new ArrayList<MedicalFacility>();
		facilities.add(new Clinic(0, "Test", 300, 1.2));

		ArrayList<Patient> patients = new ArrayList<Patient>();
		patients.add(new Patient(0, "Mark", false, 0.0, facilities.get(0)));

		HealthService service = new HealthService("TestService", facilities, patients);
	}
}
