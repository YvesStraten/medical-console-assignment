package com.yvesstraten.medicalconsole.comparators;

import com.yvesstraten.medicalconsole.Patient;
import java.util.Comparator;

public class PatientComparators {
  public static class SortedByBalance implements Comparator<Patient> {
    @Override
    public int compare(Patient o1, Patient o2) {
      double balance1 = o1.getBalance();
      double balance2 = o2.getBalance();

      if (balance1 == balance2) return 0;
      else if (balance1 > balance2) return 1;
      else return -1;
    }
  }

  public static class SortedByName implements Comparator<Patient> {
    @Override
    public int compare(Patient o1, Patient o2) {
      return o1.getName().compareTo(o2.getName());
    }
  }
}
