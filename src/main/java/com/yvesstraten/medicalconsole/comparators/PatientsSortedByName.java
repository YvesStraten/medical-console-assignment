package com.yvesstraten.medicalconsole.comparators;

import java.util.Comparator;

import com.yvesstraten.medicalconsole.Patient;

public class PatientsSortedByName implements Comparator<Patient> {
	@Override
	public int compare(Patient o1, Patient o2) {
		return o1.getName().compareTo(o2.getName());
	}
}
