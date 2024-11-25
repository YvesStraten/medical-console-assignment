package com.yvesstraten.medicalconsole.tests;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.MedicalConsole;
import org.junit.jupiter.api.BeforeAll;

/**
 * This class acts as a wrapper. Each class that inherits from this class has access to generated
 * sample data for testing
 */
public class MedicalConsoleTest {
  /** Generated test service */
  static HealthService testService;

  /** Generate sample data to be used for tests before every test in child class */
  @BeforeAll
  public static void setup() {
    testService = MedicalConsole.generateSampleData();
  }

  /** Default constructor for a MedicalConsoleTest */
  public MedicalConsoleTest() {}
}
