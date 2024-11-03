package com.yvesstraten.medicalconsole;

public class NoHospitalsAvailable extends Exception {
	public NoHospitalsAvailable(String s){
		super(s);
	}

	public NoHospitalsAvailable(){
		super();
	}
}
