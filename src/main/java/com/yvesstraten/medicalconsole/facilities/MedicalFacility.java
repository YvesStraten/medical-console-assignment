package com.yvesstraten.medicalconsole.facilities;

import com.yvesstraten.medicalconsole.Patient;

public abstract class MedicalFacility {
  private String name;
  private int id;

  public MedicalFacility(String name) {
		// TODO: Figure out how to generate random identifier
    // setId(id);
    setName(name);
  }

	public MedicalFacility(){
		this("");
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

  public int hashCode() {
    return getId();
  }

	public boolean equals(Object other){
		if(this == other)
			return true;
		else {
			if(other instanceof MedicalFacility){
				return this.getId() == ((MedicalFacility) other).getId(); 
			}
		}

		return false;
	}
}
