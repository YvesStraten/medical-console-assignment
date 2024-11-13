package com.yvesstraten.medicalconsole.facilities;

import com.yvesstraten.medicalconsole.Editable;
import com.yvesstraten.medicalconsole.Patient;

public class Clinic extends MedicalFacility {
	@Editable
  private double fee;
	@Editable(message="What is the gap percentage?")
  private double gapPercent;

  public Clinic(int id, String name, double fee, double gapPercent) {
    super(id, name);
    setFee(fee);
    setGapPercent(gapPercent);
  }

	/** 
	 * {@inheritDoc}
	*/
	public Clinic(){
		super();
	}

  public double getFee() {
    return this.fee;
  }

  public void setFee(double fee) {
    this.fee = fee;
  }

  public double getGapPercent() {
    return this.gapPercent;
  }

  public void setGapPercent(double gapPercent) {
    this.gapPercent = gapPercent;
  }

  // TODO: Test
  public boolean visit(Patient pat) {
    MedicalFacility medicalFacility = pat.getCurrentFacility();
    if (medicalFacility != null && medicalFacility.equals(this)) {
      double cost;
      if (pat.isPrivate()) {
        cost = getFee();
      } else {
        cost = getFee() * getGapPercent();
      }

      pat.setBalance(pat.getBalance() + cost);

      return true;
    } else {
      pat.setMedicalFacility(this);
      return false;
    }
  }

	@Override
	public String toString(){
		return "Clinic " + getName() + " fee " + getFee() + " and gap percent " + gapPercent;

	}
}
