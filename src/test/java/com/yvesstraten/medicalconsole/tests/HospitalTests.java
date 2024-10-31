package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.facilities.Hospital;

@DisplayName("Hospital Tests")
public class HospitalTests {
	@Test
	public void visitBehavesCorrectly(){
		Hospital hospital = new Hospital("Test");
		Patient patient = new Patient("Test patient");
		boolean success = hospital.visit(patient);

		if(success){
			assertEquals(hospital, patient.getCurrentFacility());
		} else {
			assertNull(patient.getCurrentFacility());
		}
	}
}
