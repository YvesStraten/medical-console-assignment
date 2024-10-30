package com.yvesstraten.medicalconsole;

import java.util.ArrayList;

import com.yvesstraten.medicalconsole.facilities.MedicalFacility;

public class HealthService {
  private String name;
  private ArrayList<MedicalFacility> medicalFacilities;
  private ArrayList<Patient> patients;

  public HealthService(String name, ArrayList<MedicalFacility> medicalFacilities, ArrayList<Patient> patients) {
    setName(name);
		setMedicalFacilities(medicalFacilities);
		setPatients(patients);
  }

	public HealthService(){
		this("undefined", new ArrayList<MedicalFacility>(), new ArrayList<Patient>());
	}

  public String getName() {
    return this.name;
  }

  public ArrayList<MedicalFacility> getMedicalFacilities() {
    return this.medicalFacilities;
  }

	public ArrayList<Patient> getPatients() {
		return this.patients;
	}

  public void setName(String name) {
    this.name = name;
  }

	public void setPatients(ArrayList<Patient> patients){
		this.patients = patients;
	}

  public void setMedicalFacilities(ArrayList<MedicalFacility> medicalFacilities) {
    this.medicalFacilities = medicalFacilities;
  }
}
