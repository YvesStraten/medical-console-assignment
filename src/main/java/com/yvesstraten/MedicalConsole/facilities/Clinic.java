package com.yvesstraten.medicalconsole.facilities;

import com.yvesstraten.medicalconsole.Patient;

public class Clinic extends MedicalFacility {
	private double fee;
	private double gapPercent;

	public Clinic(String name, double fee, double gapPercent){
		super(name);
		setFee(fee);
		setGapPercent(gapPercent);
	}

	public double getFee(){
		return this.fee;
	}

	public void setFee(double fee){
		this.fee = fee;
	}

	public double getGapPercent(){
		return this.gapPercent;
	}

	public void setGapPercent(double gapPercent){
		this.gapPercent = gapPercent;
	}

	// TODO: Implement
	public boolean visit(Patient pat){
		return true;

	}

}
