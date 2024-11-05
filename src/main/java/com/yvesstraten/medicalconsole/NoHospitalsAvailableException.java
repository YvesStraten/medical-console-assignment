package com.yvesstraten.medicalconsole;

/** 
 * This exception is thrown when no hospitals 
 * have yet been added to the {@link HealthService} 
 * but the user is trying to delete a procedure
 * @see com.yvesstraten.medicalconsole.facilities.Hospital
*/
public class NoHospitalsAvailableException extends Exception {
	/** 
	 * Construct this exception with an error message
	 * @param s error message
	*/
	public NoHospitalsAvailableException(String s){
		super(s);
	}

	/** 
	 * {@inheritDoc}
	*/
	public NoHospitalsAvailableException(){
		super();
	}
}
