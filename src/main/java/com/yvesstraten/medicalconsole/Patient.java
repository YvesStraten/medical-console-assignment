package com.yvesstraten.medicalconsole;

import com.yvesstraten.medicalconsole.exceptions.WrongHospitalException;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import java.io.Serializable;

/**
 * This class represents a patient
 *
 * @author Yves Straten e2400068
 */
public class Patient implements Comparable<Patient>, Serializable {
  /** Id of this patient */
  private final int id;

  /** Name of this patient */
  @Editable private String name;

  /** Whether this patient is private or public */
  @Editable(message = "Is the patient private? [y/n]", setter = "setPrivate")
  private boolean isPrivate;

  /** Balance of this patient */
  @Editable private double balance;

  /** Current facility of this patient */
  private MedicalFacility currentFacility;

  /**
   * Constructs a Patient object
   *
   * @param id id of this patient
   * @param name Name of this patient
   * @param isPrivate Whether this patient is a private or public patient
   * @param balance Starting balance of this patient
   * @param facility Starting medical facility of this patient
   * @see MedicalFacility
   */
  public Patient(
      final int id, String name, boolean isPrivate, double balance, MedicalFacility facility) {
    // final fields cannot be initialized through a setter
    // they must be initialized in the constructor
    this.id = id;
    setName(name);
    setPrivate(isPrivate);
    setBalance(balance);
    setMedicalFacility(facility);
  }

  /**
   * Alternate constructor for this patient. Sets facility to <code>null</code>
   *
   * @param id Id of this patient
   * @param name Name of this patient
   * @param isPrivate Whether this patient is private or public
   * @param balance Starting balance of this patient
   * @see Patient#Patient(int, String, boolean, double, MedicalFacility)
   */
  public Patient(int id, String name, boolean isPrivate, double balance) {
    this(id, name, false, balance, null);
  }

  /**
   * Alternate constructor for this patient Sets facility to <code>null</code> Sets balance to 0.0
   *
   * @param id Id of this patient
   * @param name Name of this patient
   * @param isPrivate Whether this patient is private or public
   * @see Patient#Patient(int, String, boolean, double, MedicalFacility)
   */
  public Patient(int id, String name, boolean isPrivate) {
    this(id, name, isPrivate, 0.0, null);
  }

  /**
   * This patient must have an id, thus, providing no details is an <b>Unsupported</b> operation
   *
   * @see Patient#Patient(int, String, boolean, double, MedicalFacility)
   * @throws UnsupportedOperationException always
   */
  public Patient() {
    throw new UnsupportedOperationException();
  }

  /**
   * Get id of this patient
   *
   * @return id of patient
   */
  public int getId() {
    return this.id;
  }

  /**
   * Get name of this patient
   *
   * @return name of patient
   */
  public String getName() {
    return this.name;
  }

  /**
   * Get status of this patient
   *
   * @return true if this patient is private, false otherwise
   */
  public boolean isPrivate() {
    return this.isPrivate;
  }

  /**
   * Get balance of this patient
   *
   * @return patient balance
   */
  public double getBalance() {
    return this.balance;
  }

  /**
   * Get the current facility this patient is in
   *
   * @return MedicalFacility or null if this patient is currently not in a facility
   */
  public MedicalFacility getCurrentFacility() {
    return this.currentFacility;
  }

  /**
   * Set name of this patient
   *
   * @param name name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Set status of this patient
   *
   * @param isPrivate status to set
   */
  public void setPrivate(boolean isPrivate) {
    this.isPrivate = isPrivate;
  }

  /**
   * Set balance of this patient
   *
   * @param balance balance to set
   */
  public void setBalance(double balance) {
    this.balance = balance;
  }

  /**
   * Set current medical facility of this patient
   *
   * @param medicalFacility facility to set
   */
  public void setMedicalFacility(MedicalFacility medicalFacility) {
    this.currentFacility = medicalFacility;
  }

  /**
   * Add balance to this patient
   *
   * @param toAdd amount to add to the balance
   * @throws IllegalArgumentException when amount to add is negative
   */
  public void addBalance(double toAdd) throws IllegalArgumentException {
    if (toAdd < 0) {
      throw new IllegalArgumentException("Balance to be added is invalid!");
    }

    setBalance(getBalance() + toAdd);
  }

  /**
   * Check whether this patient is currently in the specified hospital
   *
   * @param hospitalToCheck Hospital object to check with this patient's current facility
   * @throws WrongHospitalException if this patient is currently not in any hospital
   * @return true if specified hospital is the same as the patient's {@link Patient#currentFacility}
   */
  public boolean isInThisHospital(Hospital hospitalToCheck) throws WrongHospitalException {
    if (getCurrentFacility() == null || !getCurrentFacility().equals(hospitalToCheck)) {
      throw new WrongHospitalException("Patient has not visited any hospital yet!");
    }

    return true;
  }

  /**
   * Hash code of this patient
   *
   * @return Hash code
   */
  public int hashCode() {
    return this.id;
  }

  /**
   * Compares this patient to another. The natural order of patients is by their ids.
   *
   * @param oPatient - Patient to compare to
   * @return 0 if same id, 1 if larger id, -1 if smaller id
   */
  public int compareTo(Patient oPatient) {
    if (this == oPatient) return 0;
    else return getId() - oPatient.getId();
  }

  /**
   * Compares this Patient with another Object
   *
   * <p>Patients are equal if and only if their ids are the same
   *
   * @param obj - Object to compare
   * @return true if this object is the same as the obj argument
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    else {
      if (obj instanceof Patient) {
        return ((Patient) obj).getId() == getId();
      }
    }

    return false;
  }

  /**
   * String representation of this patient
   *
   * @return string representing this patient in the following format: status id name balance
   *     current facility
   */
  @Override
  public String toString() {
    String statusString = isPrivate() ? "private" : "public";
    MedicalFacility currentFacility = getCurrentFacility();
    String facilityString;

    if (currentFacility == null) {
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
