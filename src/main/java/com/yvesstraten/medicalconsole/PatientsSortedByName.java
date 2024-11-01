package com.yvesstraten.medicalconsole;

import java.util.Comparator;

public class PatientsSortedByName implements Comparator<Patient> {
	@Override
	public int compare(Patient o1, Patient o2) {
		return o1.getName().compareToIgnoreCase(o2.getName());
	}
}
