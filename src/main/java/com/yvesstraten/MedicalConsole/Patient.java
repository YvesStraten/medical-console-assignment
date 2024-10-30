package com.yvesstraten.medicalconsole;

import com.yvesstraten.medicalconsole.facilities.MedicalFacility;

public class Patient implements Comparable<Patient> {
  private int id;
  private String name;
  private boolean isPrivate;
  private double balance;
  private MedicalFacility currentFacility;

	public Patient(String name, double balance, MedicalFacility facility){
		// TODO: generate randomly
		//setId(random.nextInt());
		setName(name);
		setBalance(balance);
		setMedicalFacility(facility);
	}

	public Patient(String name, MedicalFacility facility){
		this(name, 0.0, facility);
	}

	public Patient(String name){
		this(name, 0.0, null);
	}

	public Patient(){
		this("", null);
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

	public boolean equals(Object other){
		if(this == other)
			return true;
		else {
			if(other instanceof Patient){
				return ((Patient) other).getId() == getId();
			}
		}

		return false;
	}

  @Override
  public String toString() {
    String statusString = isPrivate() ? "private" : "public";

    return statusString
        + " patient "
        + getId()
        + " named "
        + getName()
        + " balance "
        + getBalance()
        + getCurrentFacility().toString();
  }
}
