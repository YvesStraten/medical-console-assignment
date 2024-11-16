package com.yvesstraten.medicalconsole.comparators;

import com.yvesstraten.medicalconsole.Patient;
import java.util.Comparator;

/**
 * This class holds a selection of Comparator classes for comparing and sorting patients
 *
 * @author Yves Straten e2400068
 * @see Comparator
 */
public class PatientComparators {
	/** 
	 * Default constructor for PatientComparators
	 * @throws UnsupportedOperationException always 
	*/
	public PatientComparators(){
		new UnsupportedOperationException();
	}

	/** 
	 * Helper class to sort patients by balance
	*/
  public static class SortByBalance implements Comparator<Patient> {
    @Override
    public int compare(Patient o1, Patient o2) {
      double balance1 = o1.getBalance();
      double balance2 = o2.getBalance();

      if (balance1 == balance2) return 0;
      else if (balance1 > balance2) return -1;
      else return 1;
    }
  }

	/** 
	 * Helper class to sort patients by name
	*/
  public static class SortByName implements Comparator<Patient> {
    @Override
    public int compare(Patient o1, Patient o2) {
      return o1.getName().compareTo(o2.getName());
    }
  }
}
