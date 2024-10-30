package com.yvesstraten.medicalconsole.facilities;

import com.yvesstraten.medicalconsole.Patient;

public class Hospital extends MedicalFacility {
  private double probAdmit;

  public Hospital(String name) {
    super(name);
    // TODO: generate randomly
    this.probAdmit = 0;
  }

  public double getProbAdmit() {
    return this.probAdmit;
  }

  public void setProbAdmit(double probAdmit) {
    this.probAdmit = probAdmit;
  }

  public boolean visit(Patient pat) {
    // TODO: generate randomly
    double rand = 0.0;

    if (rand > getProbAdmit()) {
      pat.setMedicalFacility(this);
			return true;
    }

		return false;
  }
}
