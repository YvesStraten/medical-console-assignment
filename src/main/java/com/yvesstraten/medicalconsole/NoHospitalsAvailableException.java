package com.yvesstraten.medicalconsole;

public class NoHospitalsAvailableException extends Exception {
	public NoHospitalsAvailableException(String s){
		super(s);
	}

	public NoHospitalsAvailableException(){
		super();
	}
}
