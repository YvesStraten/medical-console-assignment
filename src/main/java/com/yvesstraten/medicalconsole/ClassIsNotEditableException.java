package com.yvesstraten.medicalconsole;

public class ClassIsNotEditableException extends Exception {
	public ClassIsNotEditableException(){
		super("This object cannot be edited!");
	}

	public ClassIsNotEditableException(String s){
		super(s);
	}
}
