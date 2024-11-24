package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import com.yvesstraten.medicalconsole.comparators.ProcedureComparators;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

/** This class holds all tests related to a {@link Procedure} object */
@DisplayName("Procedure tests")
public class ProcedureTests {
	/** 
	 * Construct this test class 
	*/
	public ProcedureTests(){
	}

  /** 
	 * Test factory for all tests related to the sorting of {@link Procedure} objects
	 * @return stream of tests 
	*/
  @TestFactory
  public Stream<DynamicTest> sortProcedures() {
    Procedure procedure1 = new Procedure(1, "X-ray", "Desc", false, 1000);
    Procedure procedure2 = new Procedure(1, "Test", "Test des", true, 600);
    Procedure procedure3 = new Procedure(2, "Another", "Desc", false, 700);
    List<Procedure> procedures = List.of(procedure1, procedure2, procedure3);
    Stream<SortingTest<Procedure>> tests =
        Stream.of(
            new SortingTest<Procedure>(
                "By elective",
                new ProcedureComparators.SortByElective(),
                List.of(procedure2, procedure1, procedure3)),
            new SortingTest<Procedure>(
                "By name",
                new ProcedureComparators.SortByName(),
                List.of(procedure3, procedure2, procedure1)),
            new SortingTest<Procedure>(
                "By price",
                new ProcedureComparators.SortByPrice(),
                List.of(procedure1, procedure3, procedure2)));

    return tests.map(
        test ->
            dynamicTest(
                test.getName(),
                () ->
                    assertEquals(
                        test.getExpected(),
                        procedures.stream().sorted(test.getComparator()).toList())));
  }
}
