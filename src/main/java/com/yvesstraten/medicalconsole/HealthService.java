package com.yvesstraten.medicalconsole;

import java.util.ArrayList;
import java.util.Iterator;

import com.yvesstraten.medicalconsole.facilities.MedicalFacility;

/**
 * Main service class for the application
 * @author Yves Straten e2400068
 */
public class HealthService implements Iterator<Integer> {
  private String name;
  private ArrayList<MedicalFacility> medicalFacilities;
  private ArrayList<Patient> patients;
	private int lastDispensedId;

	/** 
	 * Constructs a HealthService object 
	 * @param name Name of the HealthService 
	 * @param medicalFacilities The medical facilities to be managed
	 * @param patients The patients to be managed
	 * @see Patient
	 * @see MedicalFacility
	 */
  public HealthService(String name, ArrayList<MedicalFacility> medicalFacilities, ArrayList<Patient> patients) {
    setName(name);
		setMedicalFacilities(medicalFacilities);
		setPatients(patients);
		setLastDispensedId(0);
  }

	/** 
	 * Alternate constructor for a HealthService object
	 * @see HealthService#HealthService(String, ArrayList, ArrayList)  
	 */
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

	public int getLastDispensedId(){
		return this.lastDispensedId;
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

	private void setLastDispensedId(int id){
		this.lastDispensedId = id;
	}

	public void addPatient(Patient patient){
		getPatients().add(patient);
	}

	public void addMedicalFacility(MedicalFacility facility){
		getMedicalFacilities().add(facility);
	}

	public void deleteMedicalFacility(int index){
		getMedicalFacilities().remove(index);
	}

	public void deletePatient(int index){
		getPatients().remove(index);
	}

	@Override
	public String toString(){
		StringBuilder base = new StringBuilder("HealthService that manages the following medical facilities: \n");

		getMedicalFacilities().stream().map((facility) -> "- " + facility.toString() + "\n").forEach((detail) -> base.append(detail));

		base.append("\n Patients: \n");
		getPatients().stream().map((patient) -> "- " + patient.toString() + "\n").forEach((detail) -> base.append(detail));

		return base.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		else if(obj instanceof HealthService){
			HealthService other = (HealthService) obj;
			return this.getName().equals(other.getName()) && this.getPatients().equals(other.getPatients()) && this.getMedicalFacilities().equals(other.getMedicalFacilities()) && this.getLastDispensedId() == other.getLastDispensedId();
		}

		return false;
	}

	public Integer next(){
		return getLastDispensedId() + 1;
	}

	public boolean hasNext(){ 
		return true;
	}
}
