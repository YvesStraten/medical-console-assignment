package com.yvesstraten.medicalconsole.comparators;

import com.yvesstraten.medicalconsole.facilities.Procedure;
import java.util.Comparator;

/**
 * This class holds a selection of Comparator classes for comparing and sorting procedures
 *
 * @author Yves Straten e2400068
 * @see Comparator
 */
public class ProcedureComparators {
	/** 
	 * Default constructor for ProcedureComparators
	 * @throws UnsupportedOperationException always 
	*/
	public ProcedureComparators(){
		new UnsupportedOperationException();
	}

	/** 
	 * Helper class to sort procedures by price 
	*/
  public static class SortByPrice implements Comparator<Procedure> {
		/** 
		 * Construct this Comparator 
		*/
		public SortByPrice(){

		}

    @Override
    public int compare(Procedure o1, Procedure o2) {
      double cost1 = o1.getCost();
      double cost2 = o2.getCost();

      if (cost1 == cost2) return 0;
      else if (cost1 > cost2) return 1;
      else return -1;
    }
  }

	/** 
	 * Helper class to sort procedures by name 
	*/
  public static class SortByName implements Comparator<Procedure> {
		/** 
		 * Construct this Comparator 
		*/
		public SortByName(){

		}

    @Override
    public int compare(Procedure o1, Procedure o2) {
      return o1.getName().compareTo(o2.getName());
    }
  }

	/** 
	 * Helper class to sort procedures by their status 
	*/
  public static class SortByElective implements Comparator<Procedure> {
		/** 
		 * Construct this Comparator 
		*/
		public SortByElective(){
		}

    @Override
    public int compare(Procedure o1, Procedure o2) {
      int elective = o1.isElective() ? -1 : 1;
      if (o1 == o2) return 0;
      else return elective;
    }
  }
}
