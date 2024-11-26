package com.yvesstraten.medicalconsole;

import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Main service class for the application
 *
 * @author Yves Straten e2400068
 */
public class HealthService implements Iterable<Integer> {
  // Name of service
  @Editable private String name;
  // List of medical facilities
  private ArrayListSet<MedicalFacility> medicalFacilities;
  // List of patients
  private ArrayListSet<Patient> patients;

  /** The id dispenser for this service The developer can provide their own id generator */
  private final IdGenerator idDispenser;

  /** This class provides a system for unique and sequential ids */
  public static class SequentialIdDispenser implements IdGenerator {
    /** The last id that was dispensed */
    private int lastDispensedId;

    /** Constructs this dispenser */
    public SequentialIdDispenser() {
      setLastDispensedId(0);
    }

    /** {@inheritDoc} */
    public int getLastDispensedId() {
      return this.lastDispensedId;
    }

    /** {@inheritDoc} */
    public void setLastDispensedId(int id) {
      this.lastDispensedId = id;
    }

    /**
     * @see Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
      return lastDispensedId < Integer.MAX_VALUE;
    }

    /**
     * @see Iterator#next()
     */
    @Override
    public Integer next() {
      int newId = getLastDispensedId() + 1;
      setLastDispensedId(newId);

      return newId;
    }

    /**
     * Compares this id dispenser with one another by comparing their state
     *
     * @return true if object is equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
      if (this == other) return true;
      else if (other instanceof SequentialIdDispenser){
				SequentialIdDispenser dispenser = 
					(SequentialIdDispenser) other;
				return getLastDispensedId() == 
				dispenser.getLastDispensedId();
			}

      return false;
    }
  }

  /**
   * Constructs a HealthService object
   *
   * @param name Name of the HealthService
   * @param medicalFacilities The medical facilities to be managed
   * @param patients The patients to be managed
   * @param idDispenser The id generator for this service
   */
  public HealthService(
      String name,
      ArrayListSet<MedicalFacility> medicalFacilities,
      ArrayListSet<Patient> patients,
      final IdGenerator idDispenser) {
    setName(name);
    setMedicalFacilities(medicalFacilities);
    setPatients(patients);
    // final fields cannot be initialized using setters
    // only through the constructor
    this.idDispenser = idDispenser;
  }

  /**
   * Constructs a HealthService object
   *
   * @param name Name of the HealthService
   * @param medicalFacilities The medical facilities to be managed
   * @param patients The patients to be managed
   */
  public HealthService(
      String name, ArrayListSet<MedicalFacility> medicalFacilities, ArrayListSet<Patient> patients) {
    this(name, medicalFacilities, patients, new SequentialIdDispenser());
  }

  /** Alternate constructor for a HealthService object 
	 * @param name name of service 
	*/
  public HealthService(String name) {
    this(
        name,
        new ArrayListSet<MedicalFacility>(),
        new ArrayListSet<Patient>(),
        new SequentialIdDispenser());
  }

  /** Alternate constructor for a HealthService object */
  public HealthService() {
    this(
        "undefined",
        new ArrayListSet<MedicalFacility>(),
        new ArrayListSet<Patient>(),
        new SequentialIdDispenser());
  }

  /**
   * Get name of this health service
   *
   * @return name of service
   */
  public String getName() {
    return this.name;
  }

  /**
   * Get the list of medical facilities managed by this service
   *
   * @return list of MedicalFacility
   * @see MedicalFacility
   */
  public ArrayListSet<MedicalFacility> getMedicalFacilities() {
    return this.medicalFacilities;
  }

  /**
   * Get a stream of medical facilities managed by this service
   *
   * @return stream of MedicalFacility
   * @see MedicalFacility
   */
  public Stream<MedicalFacility> getMedicalFacilitiesStream() {
    return getMedicalFacilities().stream();
  }

  /**
   * Get a stream of hospitals managed by this service
   *
   * @return stream of Hospital
   * @see Hospital
   */
  public Stream<Hospital> getHospitals() {
    return getMedicalFacilities().stream()
        .filter((facility) -> facility instanceof Hospital)
        .map(Hospital.class::cast);
  }

  /**
   * Get a stream of clinics managed by this service
   *
   * @return stream of Clinic
   * @see Clinic
   */
  public Stream<Clinic> getClinics() {
    return getMedicalFacilities().stream()
        .filter((facility) -> facility instanceof Clinic)
        .map(Clinic.class::cast);
  }

  /**
   * Get a list of patients managed by this service
   *
   * @return list of Patient
   * @see Patient
   */
  public ArrayListSet<Patient> getPatients() {
    return this.patients;
  }

  /**
   * Returns the id dispenser for this health service
   *
   * @return id dispenser
   */
  public IdGenerator getIdDispenser() {
    return this.idDispenser;
  }

  /**
   * Get a stream of patients managed by this service
   *
   * @return stream of Patient
   * @see Patient
   */
  public Stream<Patient> getPatientsStream() {
    return getPatients().stream();
  }

  /**
   * Set name of this health service
   *
   * @param name name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Set list of patients managed by this service
   *
   * @param patients list of Patient to set
   */
  public void setPatients(ArrayListSet<Patient> patients) {
    this.patients = patients;
  }

  /**
   * Set list of patients managed by this service
   *
   * @param medicalFacilities list of MedicalFacility to set
   */
  public void setMedicalFacilities(ArrayListSet<MedicalFacility> medicalFacilities) {
    this.medicalFacilities = medicalFacilities;
  }

  /**
   * Add a Patient to be managed by this service
   *
   * @param patient Patient to add
   * @see Patient
   */
  public void addPatient(Patient patient) {
    getPatients().add(patient);
  }

  /**
   * Initialize a Patient to be managed by this service
   *
   * @param name name of patient
   * @param isPrivate private status of patient
   */
  public void initializePatient(String name, boolean isPrivate) {
    Patient patientToAdd = new Patient(iterator().next(), name, isPrivate);

    addPatient(patientToAdd);
  }

  /**
   * Add a MedicalFacility to be managed by this service
   *
   * @param facility facility to add
   * @see MedicalFacility
   */
  public void addMedicalFacility(MedicalFacility facility) {
    getMedicalFacilities().add(facility);
  }

  /**
   * Initialize a Hospital to be managed by this service
   *
   * @param name hospital name
   * @see Hospital
   */
  public void initializeHospital(String name) {
    Hospital hospitalToAdd = new Hospital(getIdDispenser().next(), name);

    addMedicalFacility(hospitalToAdd);
  }

  /**
   * Initialize a Clinic to be managed by the service
   *
   * @param name name of clinic
   * @param fee fee of clinic
   * @param gapPercent gap percentage of clinic
   */
  public void initializeClinic(String name, double fee, double gapPercent) {
    if (gapPercent > 1) {
      // Percentage in non-decimal format, e.g 3%.
      gapPercent /= 100;
    }

    Clinic clinicToAdd = new Clinic(iterator().next(), name, fee, gapPercent);

    addMedicalFacility(clinicToAdd);
  }

  /**
   * Adds a procedure to specified hospital
   *
   * @param hospital specified hospital
   * @param procedure procedure to add
   */
  public void addProcedure(Hospital hospital, Procedure procedure) {
    hospital.getProcedures().add(procedure);
  }

  /**
   * Initialize a Procedure to be managed by the service
   *
   * @param hospital specified hospital
   * @param name name of procedure
   * @param description description of procedure
   * @param isElective whether the procedure is elective
   * @param cost basic cost of procedure
   */
  public void initializeProcedure(
      Hospital hospital, 
		  String name,
		  String description,
		  boolean isElective,
		  double cost) {
    Procedure procedureToAdd =
        new Procedure(iterator().next(), 
				name, 
				description, 
				isElective, 
				cost);

    addProcedure(hospital, procedureToAdd);
  }

  /**
   * Remove a MedicalFacility from this service, and its management
   *
   * @param index index to remove from the list
   * @see HealthService#getMedicalFacilities()
   */
  public void deleteMedicalFacility(int index) {
    getMedicalFacilities().remove(index);
  }

  /**
   * Remove a Patient from this service, and its management
   *
   * @param index index to remove from the list
   * @see HealthService#getPatients()
   */
  public void deletePatient(int index) {
    getPatients().remove(index);
  }

  /**
   * String representation of this service
   *
   * @return string representation
	*/
  @Override
  public String toString() {
    StringBuilder base =
        new StringBuilder("HealthService ")
            .append(getName())
            .append(" that manages the following medical facilities: \n");

    getMedicalFacilities().stream()
        .map((facility) -> "- " + facility.toString() + "\n")
        .forEach((detail) -> base.append(detail));

    base.append("\n Patients: \n");
    getPatients().stream()
        .map((patient) -> "- " + patient.toString() + "\n")
        .forEach((detail) -> base.append(detail));

    return base.toString();
  }

  /**
   * Compares this health service with another. Must meet the
   * following conditions:
   *
   * <ul>
   *   <li>Same name
   *   <li>Same list of patients
   *   <li>Same list of medical facilities
   *   <li>Same id iterator
   * </ul>
   *
   * @return true if health services are the same, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    else if (obj instanceof HealthService) {
      HealthService other = (HealthService) obj;
      return this.getName().equals(other.getName())
          && this.getPatients().equals(other.getPatients())
          && this.getMedicalFacilities().equals(other.getMedicalFacilities())
          && this.getIdDispenser().equals(other.getIdDispenser());
    }

    return false;
  }

  /**
   * @see Iterable#iterator()
   */
  @Override
  public Iterator<Integer> iterator() {
    return getIdDispenser();
  }
}
