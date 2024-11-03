package com.yvesstraten.medicalconsole.facilities;

import com.yvesstraten.medicalconsole.Patient;

/**
 * This class outlines the basic properties 
 * that a <code>MedicalFacility</code> should have
 * @author Yves Straten e2400068
*/
public abstract class MedicalFacility {
  private String name;
  private int id;

  public MedicalFacility(int id, String name) {
    setId(id);
    setName(name);
  }

	/** 
	 * A medical facility must have an id, 
	 * thus providing no details is <b>Unsupported</b>
	 * @throws UnsupportedOperationException
	*/
	public MedicalFacility(){
		throw new UnsupportedOperationException();
	}

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

	private void setId(int id) {
		this.id = id;
	}

  public abstract boolean visit(Patient pat); 

	@Override
  public int hashCode() {
    return getId();
  }

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
}
