package com.yvesstraten.medicalconsole.facilities;

import java.io.Serializable;

import com.yvesstraten.medicalconsole.Editable;
import com.yvesstraten.medicalconsole.Patient;

/**
 * This class outlines the basic properties that a <code>MedicalFacility</code> should have
 *
 * @author Yves Straten e2400068
 */
public abstract class MedicalFacility implements Comparable<MedicalFacility>, Serializable {
	/** id of this medical facility */
	private final int id;
	/** name of this medical facility */
  @Editable private String name;

	/** 
	 * Constructs this medical facility 
	 * @param id id of this facility
	 * @param name name of this facility 
	*/
  public MedicalFacility(final int id, String name) {
		// final fields cannot be initialized through a setter
		// they must be initialized in the constructor
		this.id = id;
    setName(name);
  }

  /**
   * This medical facility must have an id, thus providing no details is <b>Unsupported</b>
   *
   * @throws UnsupportedOperationException always
   */
  public MedicalFacility() {
    throw new UnsupportedOperationException();
  }

	/** 
	 * Retrieves the id of this medical facility 
	 * @return id - id of this facility
	*/
  public int getId() {
    return this.id;
  }

	/** 
	 * Retrieves the name of this medical facility
	 * @return name - name of this facility
	*/
  public String getName() {
    return this.name;
  }

	/** 
	 * Sets the name of this medical facility
	 * @param name - name to set
	*/
  public void setName(String name) {
    this.name = name;
  }

	/** 
	 * Make a patient visit this medical facility 
	 * @param pat - patient that visits
	 * @return true if visit was successful, false otherwise
	*/
  public abstract boolean visit(Patient pat); 

	/** 
	 * Get hash of this medical facility 
	 * @return hash of this facility
	*/
	@Override
  public int hashCode() {
    return getId();
  }

	/** 
	 * Compares this medical facility to another object 
	 * @param obj - object to compare with
	 * @return true if object is the same false otherwise
	*/
	@Override
	public boolean equals(Object obj){
		if(this == obj || this.hashCode() == obj.hashCode())
			return true;
		else {
			if(obj instanceof MedicalFacility){
				return this.getId() == ((MedicalFacility) obj).getId(); 
			}
		}

    return false;
  }

  /**
   * Compares this MedicalFacility to another The natural order of medical facilities is by their
   * ids.
   *
   * @param o - medical facility to compare
   * @return 0 if same id, 1 if larger id, -1 otherwise
   */
  @Override
  public int compareTo(MedicalFacility o) {
    if (this == o) return 0;
    else return getId() - o.getId();
  }
}
