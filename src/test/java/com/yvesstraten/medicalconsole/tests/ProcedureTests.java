package com.yvesstraten.medicalconsole.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.yvesstraten.medicalconsole.comparators.ProcedureComparators;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Procedure tests")
public class ProcedureTests {
  private ArrayList<Procedure> procedures =
      new ArrayList<Procedure>(
          List.of(
              new Procedure(1, "Test", "Test des", true, 300),
              new Procedure(1, "Another", "Desc", false, 600),
              new Procedure(2, "X-ray", "Desc", false, 100)));

  @Test
  public void testSortByPrice() {
    assertEquals(
        List.of(
            new Procedure(2, "X-ray", "Desc", false, 100),
            new Procedure(1, "Test", "Test des", true, 300),
            new Procedure(1, "Another", "Desc", false, 600)),
        procedures.stream().sorted(new ProcedureComparators.SortByPrice()).toList());
  }

  @Test
  public void testSortByName() {
    assertEquals(
        List.of(
            new Procedure(1, "Another", "Desc", false, 600),
            new Procedure(1, "Test", "Test des", true, 300),
            new Procedure(2, "X-ray", "Desc", false, 100)),
        procedures.stream().sorted(new ProcedureComparators.SortByName()).toList());
  }

  @Test
  public void testSortByElective() {
    assertEquals(
        List.of(
				new Procedure(1, "Test", "Test des", true, 300),
            new Procedure(1, "Another", "Desc", false, 600),
            new Procedure(2, "X-ray", "Desc", false, 100)),
        procedures.stream().sorted(new ProcedureComparators.SortByElective()).toList());
  }
}
