package com.yvesstraten.medicalconsole.comparators;

import java.util.Comparator;

import com.yvesstraten.medicalconsole.Patient;

public class PatientsSortedByBalance implements Comparator<Patient> {
	@Override
	public int compare(Patient o1, Patient o2){
		double balance1 = o1.getBalance();
		double balance2 = o2.getBalance();

		if(balance1 == balance2)
			return 0;
		else if(balance1 > balance2)
			return 1;
		else return -1;
	}
}
