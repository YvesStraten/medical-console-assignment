package com.yvesstraten.medicalconsole;

public class WrongHospitalException extends Exception {
	public WrongHospitalException(String s){
		super(s);
	}

	public WrongHospitalException(){
		super();
	}
}
