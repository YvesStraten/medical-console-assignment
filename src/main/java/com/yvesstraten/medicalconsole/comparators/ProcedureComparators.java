package com.yvesstraten.medicalconsole.comparators;

import com.yvesstraten.medicalconsole.facilities.Procedure;
import java.util.Comparator;

public class ProcedureComparators {
  public static class SortByPrice implements Comparator<Procedure> {
    @Override
    public int compare(Procedure o1, Procedure o2) {
      double cost1 = o1.getCost();
      double cost2 = o2.getCost();

      if (cost1 == cost2) return 0;
      else if (cost1 > cost2) return 1;
      else return -1;
    }
  }

	public static class SortByName implements Comparator<Procedure> {
		@Override 
		public int compare(Procedure o1, Procedure o2){
			return o1.getName().compareTo(o2.getName());
		}
	}
}
