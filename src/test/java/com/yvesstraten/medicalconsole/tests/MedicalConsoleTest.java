package com.yvesstraten.medicalconsole.tests;

import org.junit.jupiter.api.BeforeAll;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.MedicalConsole;

public class MedicalConsoleTest {
	static HealthService testService;
	@BeforeAll
	static public void setup(){
		testService = MedicalConsole.generateSampleData();
	}
}
