package com.yvesstraten.medicalconsole.comparators;

import java.util.Comparator;

import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;

public class MedicalFacilitiesComparators {
	public static class SortByName implements Comparator<MedicalFacility> {
		@Override 
		public int compare(MedicalFacility o1, MedicalFacility o2){
			return o1.getName().compareTo(o2.getName());
		}
	}

	public static class SortByHospital implements Comparator<MedicalFacility> {
		@Override 
		public int compare(MedicalFacility o1, MedicalFacility o2){
			if(o1 == o2) 
				return 0;
			else if(o2 instanceof Hospital)
				return 1;
			else return -1;

		}
	}

	public static class SortByClinic implements Comparator<MedicalFacility> {
		@Override 
		public int compare(MedicalFacility o1, MedicalFacility o2){
			if(o1 == o2) 
				return 0;
			else if(o2 instanceof Clinic)
				return 1;
			else return -1;

		}
	}
}
