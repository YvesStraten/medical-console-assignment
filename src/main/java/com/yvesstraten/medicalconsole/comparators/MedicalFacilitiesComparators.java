package com.yvesstraten.medicalconsole.comparators;

import com.yvesstraten.medicalconsole.comparators.PatientComparators.SortByName;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import java.util.Comparator;

/**
 * This class holds a selection of Comparator classes for comparing and sorting medical facilities
 *
 * @author Yves Straten e2400068
 * @see Comparator
 */
public class MedicalFacilitiesComparators {
	/** 
	 * Default constructor for MedicalFacilitiesComparators
	 * @throws UnsupportedOperationException always 
	*/
	public MedicalFacilitiesComparators(){
		new UnsupportedOperationException();
	}

	/** 
	 * Helper class to sort facilities by name 
	*/
  public static class SortByName implements Comparator<MedicalFacility> {
		/** 
			* Construct this Comparator 
		*/
		public SortByName(){

		}

    @Override
    public int compare(MedicalFacility o1, MedicalFacility o2) {
      return o1.getName().compareTo(o2.getName());
    }
  }

	/** 
	 * Helper class to sort facilities by hospital 
	*/
  public static class SortByHospital implements Comparator<MedicalFacility> {
		/** 
		 * Construct this Comparator 
		*/
		public SortByHospital(){

		}

    @Override
    public int compare(MedicalFacility o1, MedicalFacility o2) {
      if (o1 == o2) return 0;
      else if (o2 instanceof Hospital) return 1;
      else return -1;
    }
  }

	/** 
	 * Helper class to sort facilities by clinic 
	*/
  public static class SortByClinic implements Comparator<MedicalFacility> {
		/** 
		 * Construct this Comparator 
		*/
		public SortByClinic(){
		}

    @Override
    public int compare(MedicalFacility o1, MedicalFacility o2) {
      if (o1 == o2) return 0;
      else if (o2 instanceof Clinic) return 1;
      else return -1;
    }
  }
}
