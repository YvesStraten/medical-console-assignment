package com.yvesstraten.medicalconsole.facilities;

import com.yvesstraten.medicalconsole.Patient;
import java.util.ArrayList;
import java.util.Random;

public class Hospital extends MedicalFacility {
  private double probAdmit;
  private ArrayList<Procedure> procedures;

  public Hospital(int id, String name, ArrayList<Procedure> procedures) {
    super(id, name);

    Random random = new Random();
    setProbAdmit(random.nextDouble(1));
  }

  public Hospital(int id, String name) {
    this(id, name, new ArrayList<Procedure>());
  }

	/** 
	 * {@inheritDoc}
	*/
	public Hospital(){
		super();
	}

  public double getProbAdmit() {
    return this.probAdmit;
  }

  public ArrayList<Procedure> getProcedures() {
    return this.procedures;
  }

  public void setProbAdmit(double probAdmit) {
    this.probAdmit = probAdmit;
  }

  public void setProcedures(ArrayList<Procedure> procedures) {
    this.procedures = procedures;
  }

  public boolean visit(Patient pat) {
		Random random = new Random();
    double rand = random.nextDouble(1);

    if (rand > getProbAdmit()) {
      pat.setMedicalFacility(this);
      return true;
    }

    return false;
  }
}
