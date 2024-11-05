package com.yvesstraten.medicalconsole.facilities;

import com.yvesstraten.medicalconsole.Format;
import com.yvesstraten.medicalconsole.Patient;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

public class Hospital extends MedicalFacility {
  private double probAdmit;
  private ArrayList<Procedure> procedures;

  public Hospital(int id, String name, ArrayList<Procedure> procedures) {
    super(id, name);

    Random random = new Random();
    setProbAdmit(random.nextDouble(1));
    setProcedures(procedures);
  }

  public Hospital(int id, String name) {
    this(id, name, new ArrayList<Procedure>());
  }

  /** {@inheritDoc} */
  public Hospital() {
    super();
  }

  public double getProbAdmit() {
    return this.probAdmit;
  }

  public ArrayList<Procedure> getProcedures() {
    return this.procedures;
  }

	public Stream<Procedure> getProceduresStream(){
		return getProcedures().stream();
	}

  public void setProbAdmit(double probAdmit) {
    this.probAdmit = probAdmit;
  }

  public void setProcedures(ArrayList<Procedure> procedures) {
    this.procedures = procedures;
  }

	public void addProcedure(Procedure procedure){
		getProcedures().add(procedure);
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

  @Override
  public String toString() {
    String[] proceduresDetails =
        getProcedures().stream().map((procedure) -> procedure.toString()).toArray(String[]::new);
    String proceduresString =
        proceduresDetails.length == 0
            ? " and no procedures"
            : " and the following procedures: \n" + Format.bulletedContent(proceduresDetails);

    return "Hospital named " + getName() + " with id " + getId() + proceduresString;
  }
}
