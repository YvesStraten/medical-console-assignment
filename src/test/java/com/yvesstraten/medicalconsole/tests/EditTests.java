package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.yvesstraten.medicalconsole.Editable;
import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.MedicalConsole;
import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.exceptions.ClassIsNotEditableException;
import com.yvesstraten.medicalconsole.exceptions.InvalidOptionException;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Edit Tests")
public class EditTests {
  public class ClassWithNoEditable {}

  @Test
  public void attemptEditNoEditableThrows() {
    ClassWithNoEditable test = new ClassWithNoEditable();
    ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());
    Scanner stdin = new Scanner(input);
    assertThrows(ClassIsNotEditableException.class, () -> MedicalConsole.attemptEdit(test, stdin));
  }

  public class TestClassWithWrongSetter {
    @Editable private String testField;

    public void setTest(String testField) {
      this.testField = testField;
    }
  }

  @Test
  public void attemptEditWrongSetterThrows() {

    TestClassWithWrongSetter test = new TestClassWithWrongSetter();
    ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());
    Scanner stdin = new Scanner(input);
    assertThrows(RuntimeException.class, () -> MedicalConsole.attemptEdit(test, stdin));
  }

  public class TestClassWithRightSetter {
    @Editable private String field;

    public void setField(String testField) {
      this.field = testField;
    }

    public String getTestField() {
      return this.field;
    }
  }

  @Test
  public void attemptEditSuccessFullWithRightSetter() throws ClassIsNotEditableException {

    TestClassWithRightSetter test = new TestClassWithRightSetter();

    Scanner testInput = new Scanner(new ByteArrayInputStream("Another\n".getBytes()));
    MedicalConsole.attemptEdit(test, testInput);
    assertEquals("Another", test.getTestField());
  }

  @Test
  public void editService() throws ClassIsNotEditableException, InvalidOptionException {
    HealthService service =
        new HealthService("Test", new ArrayList<MedicalFacility>(), new ArrayList<Patient>());
    ByteArrayInputStream mockInput = new ByteArrayInputStream("1\nService\n".getBytes());
    Scanner scanner = new Scanner(mockInput);
    MedicalConsole.editObject(service, scanner);

    assertEquals("Service", service.getName());
  }

  public class IntegrationTest {
    private String name;
    private String userInput;
    private Object expected;

    public IntegrationTest(String name, String userInput, Object expected) {
      setName(name);
      setUserInput(userInput);
      setExpected(expected);
    }

    public String getName() {
      return this.name;
    }

    public String getUserInput() {
      return this.userInput;
    }

    public Object getExpected() {
      return this.expected;
    }

    public void setName(String name) {
      this.name = name;
    }

    public void setUserInput(String userInput) {
      this.userInput = userInput;
    }

    public void setExpected(Object expected) {
      this.expected = expected;
    }

    public Scanner inputToScanner() {
      return new Scanner(new ByteArrayInputStream(getUserInput().getBytes()));
    }
  }

  // @TestFactory
  // public Stream<DynamicTest> editTests() {
  //   HealthService service =
  //       new HealthService(
  //           "TestService", new ArrayList<MedicalFacility>(), new ArrayList<Patient>());
  //   service.addMedicalFacility(new Clinic(service.next(), "Croix", 300, 0.3));
  //   Hospital hospital = new Hospital(service.next(), "Hopitale");
  //   service.addMedicalFacility(hospital);
  //
  //   hospital.addProcedure(new Procedure(service.next(), "TestProc", "TestDesc", true, 300));
  //   List<IntegrationTest> tests =
  //       List.of(
  //           new IntegrationTest(
  //               "Edit Health Service",
  //               "1\nService",
  // 						"Service"
  //               ),
  //           new IntegrationTest(
  //               "Edit Hospital",
  //               "2\nCroix",
  // 						"Croix"
  //               ));
  //
  // return tests.stream().map(test -> dynamicTest(test.getName(), () -> {
  // 	MedicalConsole.editObject(service, test.inputToScanner());
  // 	assertEquals(test.getExpected(), )
  //
  // }))
  // }
}
