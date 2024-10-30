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

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public abstract boolean visit(Patient pat); 

  public int hashCode() {
    return getId();
  }
}
