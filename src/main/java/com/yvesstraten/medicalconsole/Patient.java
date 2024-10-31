package com.yvesstraten.medicalconsole;

import com.yvesstraten.medicalconsole.facilities.MedicalFacility;

/** 
 * Class that represents a patient 
 * @author Yves Straten e2400068
*/
public class Patient implements Comparable<Patient> {
  private int id;
  private String name;
  private boolean isPrivate;
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
	public Patient(String name, boolean isPrivate, double balance, MedicalFacility facility){
		// TODO: generate randomly
		//setId(random.nextInt());
		setName(name);
		setPrivate(isPrivate);
		setBalance(balance);
		setMedicalFacility(facility);
	}

	/** 
	 * Alternate constructor for a Patient
	 * @see Patient#Patient(String, boolean, double, MedicalFacility)
	*/
	public Patient(String name, MedicalFacility facility){
		this(name, false, 0.0, facility);
	}

	/** 
	 * Alternate constructor for a Patient
	 * @see Patient#Patient(String, boolean, double, MedicalFacility)
	*/
	public Patient(String name){
		this(name, false, 0.0, null);
	}

	/** 
	 * Alternate constructor for a Patient
	 * @see Patient#Patient(String, boolean, double, MedicalFacility)
	*/
	public Patient(){
		this("");
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

  public int hashCode() {
    return this.id;
  }

  public int compareTo(Patient oPatient) {
    if (this == oPatient) return 0;
    else return (int) (getBalance() - oPatient.getBalance());
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
		String facilityString = getCurrentFacility().toString();

    return statusString
        + " patient "
        + getId()
        + " named "
        + getName()
        + " balance "
        + getBalance()
				+ " current facility "
        + (facilityString != null ? facilityString : "none");
  }
}
