package com.yvesstraten.medicalconsole.facilities;

import com.yvesstraten.medicalconsole.Editable;
import com.yvesstraten.medicalconsole.Patient;

/**
 * This class represents a clinic
 *
 * @author Yves Straten e2400068
 */
public class Clinic extends MedicalFacility {
  /** The visit fee when a private patient visits */
  @Editable private double fee;

  /** The percentage, in decimal format, that is added the fee when a public patient visits */
  @Editable(message = "What is the gap percentage?")
  private double gapPercent;

  /**
   * Constructs this clinic
   *
   * @param id - id to give this clinic
   * @param name - name to give this clinic
   * @param fee - base fee to give this clinic
   * @param gapPercent - gap percentage to give this clinic
   */
  public Clinic(final int id, String name, double fee, double gapPercent) {
    super(id, name);
    setFee(fee);
    setGapPercent(gapPercent);
  }

  /**
   * This hospital must have an id, as such, this is operation is <b>unsupported</b>
   *
   * @throws UnsupportedOperationException always
   */
  public Clinic() {
    super();
  }

  /**
   * Get fee of this clinic
   *
   * @return fee of this clinic
   */
  public double getFee() {
    return this.fee;
  }

  /**
   * Setter for fee
   *
   * @param fee - fee to set
   */
  public void setFee(double fee) {
    this.fee = fee;
  }

  /**
   * Getter for gapPercent
   *
   * @return gap percent of this clinic
   */
  public double getGapPercent() {
    return this.gapPercent;
  }

  /**
   * Setter for gapPercent
   *
   * @param gapPercent - gapPercent to set
   */
  public void setGapPercent(double gapPercent) {
    if (gapPercent > 1) {
      // Percentage in non-decimal format, e.g 3%.
      gapPercent /= 100;
    }

		this.gapPercent = gapPercent;
  }

  /**
   * Make a patient visit this facility
   *
   * @param pat patient that will visit
   * @return true if patient has already visited this clinic and the
	 * cost has been added to their balance according
	 * to these criteria:
   *     <ul>
   *       <li>Private patient - only fee
   *       <li>Public patient - fee * gapPercent
   *     </ul>
   *     or false if patient has not already visited this clinic
   */
  public boolean visit(Patient pat) {
    MedicalFacility medicalFacility = pat.getCurrentFacility();
    if (medicalFacility != null && medicalFacility.equals(this)) {
      // Patient is in this facility
      double cost;
      if (pat.isPrivate()) {
        // Private patient
        cost = getFee();
      } else {
        cost = getFee() + (getFee() * getGapPercent());
      }

      pat.addBalance(cost);

      return true;
    } else {
      pat.setMedicalFacility(this);
      return false;
    }
  }

  /**
   * String representation of this clinic
   *
   * @return string representation of this clinic
   */
  @Override
  public String toString() {
    return "Clinic named "
        + getName()
        + " with id "
        + getId()
        + " fee "
        + getFee()
        + " and gap percent "
        + gapPercent;
  }
}
