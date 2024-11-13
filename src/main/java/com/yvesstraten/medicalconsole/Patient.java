package com.yvesstraten.medicalconsole;

import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;

/** 
 * This class represents a patient 
 * @author Yves Straten e2400068
*/
public class Patient implements Comparable<Patient> {
  private int id;
	@Editable
  private String name;
	@Editable(message="Is the patient private or public? [y/n]", setter="setPrivate")
  private boolean isPrivate;
	@Editable
  private double balance;
  private MedicalFacility currentFacility;

	/** 
	 * Constructs a Patient object 
	 * @param name Name of patient 
	 * @param isPrivate Whether this patient is a private 
	 * or public patient 
	 * @param balance Starting balance of this patient
	 * @param facility Starting medical facility of 
	 * this patient
	 * @see MedicalFacility
	*/
	public Patient(int id, String name, boolean isPrivate, double balance, MedicalFacility facility){
		setId(id);
		setName(name);
		setPrivate(isPrivate);
		setBalance(balance);
		setMedicalFacility(facility);
	}

	/** 
	 * Alternate constructor for this patient 
	 * Sets facility to <code>null</code>
	 * @see Patient#Patient(int, String, boolean, double, MedicalFacility)
	*/
	public Patient(int id, String name, boolean isPrivate, double balance){
		this(id, name, false, balance, null);
	}

	/** 
	 * Alternate constructor for this patient 
	 * Sets facility to <code>null</code>
	 * Sets balance to 0.0 
	 * @see Patient#Patient(int, String, boolean, double, MedicalFacility)
	*/
	public Patient(int id, String name, boolean isPrivate){
		this(id, name, isPrivate, 0.0, null);
	}

	/** 
	 * This patient must have an id, thus,
	 * providing no details is an <b>Unsupported</b>
	 * operation
	 * @see Patient#Patient(int, String, boolean, double, MedicalFacility)
	 * @throws UnsupportedOperationException
	*/
	public Patient(){
		throw new UnsupportedOperationException();
	}

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public boolean isPrivate() {
    return this.isPrivate;
  }

  public double getBalance() {
    return this.balance;
  }

  public MedicalFacility getCurrentFacility() {
    return this.currentFacility;
  }

  private void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPrivate(boolean isPrivate) {
    this.isPrivate = isPrivate;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public void setMedicalFacility(MedicalFacility medicalFacility) {
    this.currentFacility = medicalFacility;
  }

	public void addBalance(double toAdd) throws IllegalArgumentException {
		if(toAdd < 0){
			throw new IllegalArgumentException("Balance to be added is invalid!");
		}

		setBalance(getBalance() + toAdd);
	}

	public boolean isInThisHospital(Hospital hospitalToCheck) throws WrongHospitalException {
		if(getCurrentFacility() == null){
				throw new WrongHospitalException("Patient has not visited any hospital yet!");
		} else if(!getCurrentFacility().equals(hospitalToCheck)) {
				throw new WrongHospitalException("Patient's current facility is not this hospital!");
		}

		return true;
	}

  public int hashCode() {
    return this.id;
  }

	/** 
	 * Compares this patient to another.  
	 * The natural order of patients 
	 * is by their ids. 
	 * @param oPatient - Patient to compare to
	 * @return 0 - same id 
	 * @return 1 - larger id
	 * @return -1 - smaller id
	*/
  public int compareTo(Patient oPatient) {
    if (this == oPatient) return 0;
    else return getId() - oPatient.getId();
  }

	/** 
	 * Compares this Patient with another Patient
	 * <p>Patients are equal if and only if their ids
	 * are the same</p>
	 * @param obj - Object to compare
	 * @return true if this object is the same as the obj argument
	*/
	@Override
	public boolean equals(Object obj){
		if(this == obj)
			return true;
		else {
			if(obj instanceof Patient){
				return ((Patient) obj).getId() == getId();
			}
		}

		return false;
	}

  @Override
  public String toString() {
    String statusString = isPrivate() ? "private" : "public";
		MedicalFacility currentFacility = getCurrentFacility();
		String facilityString;

		if(currentFacility == null){
			facilityString = new String("none");
		} else {
			facilityString = currentFacility.toString();
		}

    return statusString
        + " patient "
        + getId()
        + " named "
        + getName()
        + " with balance "
        + getBalance()
				+ " and current facility "
        + facilityString;
  }
}
