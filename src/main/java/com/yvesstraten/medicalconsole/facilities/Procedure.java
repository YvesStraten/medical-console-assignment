package com.yvesstraten.medicalconsole.facilities;

import com.yvesstraten.medicalconsole.Editable;

/**
 * This class represents a procedure
 *
 * @author Yves Straten e2400068
 * @see Hospital
 */
public class Procedure implements Comparable<Procedure> {
  // id of this procedure
  private final int id;

  // name of this procedure
  @Editable(message = "What should the name of this procedure be?")
  private String name;

  // description of this procedure
  @Editable(message = "What should the description of this procedure be?")
  private String description;

  // whether this procedure is elective or not
  @Editable(message = "Is this procedure elective [y/n]")
  private boolean isElective;

  // base cost of this procedure
  @Editable private double cost;

  /**
   * Construct this procedure
   *
   * @param id id of this procedure
	 * @param name name of this procedure
   * @param description description of this procedure
   * @param isElective whether this procedure is elective
   * @param basicCost basicCost of this procedure
   */
  public Procedure(final int id, String name, String description, boolean isElective, double basicCost) {
		this.id = id;
    setName(name);
    setDescription(description);
    setIsElective(isElective);
    setCost(basicCost);
  }

  /**
   * This procedure must have an id, thus, providing no details is <b>unsupported</b>
   *
   * @see Procedure#Procedure(int, String, String, boolean, double)
   * @throws UnsupportedOperationException always
   */
  public Procedure() {
    throw new UnsupportedOperationException();
  }

  /**
   * Get the id of this procedure
   *
   * @return id of this procedure
   */
  public int getId() {
    return this.id;
  }

  /**
   * Get name of this procedure
   *
   * @return procedure name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Get the description of this procedure
   *
   * @return Procedure description
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Get elective status of this procedure
   *
   * @return true if elective, false otherwise
   */
  public boolean isElective() {
    return this.isElective;
  }

  /**
   * Get the basic cost of this procedure
   *
   * @return basic cost
   */
  public double getCost() {
    return this.cost;
  }

  /**
   * Set name of this procedure
   *
   * @param name name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Set description of this procedure
   *
   * @param description description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Set elective status of this procedure
   *
   * @param elective status to set
   */
  public void setIsElective(boolean elective) {
    this.isElective = elective;
  }

  /**
   * Set basic cost of this procedure
   *
   * @param cost cost to set
   */
  public void setCost(double cost) {
    this.cost = cost;
  }

  /**
   * Compares this procedure with another Object
   *
   * @param other object to compare to
   * @return true if objects are the same or have the same id, false otherwise
   */
  @Override
  public boolean equals(Object other) {
    if (this == other) return true;
    else {
      if (other instanceof Procedure) {
        return this.getId() == ((Procedure) other).getId();
      }
    }

    return false;
  }

  /**
   * Hash code of this procedure, that is the id of this procedure
   *
   * @return hashcode
   */
  @Override
  public int hashCode() {
    return getId();
  }

  /**
   * Compare this procedure with another
   *
   * @param o procedure to compare to
   * @return 0 if same, -1 if a lower id, 1 if a higher id
   */
  @Override
  public int compareTo(Procedure o) {
    if (this == o) return 0;
    else return getId() - o.getId();
  }

  /**
   * {@inheritDoc}
   *
   * <p>In this implementation, the string has the following format: Elective or non Elective
   * procedure Name with id id with description description and basic cost cost
   *
   * @return String representation
   */
  @Override
  public String toString() {
    String elective = isElective() ? "Elective" : "Non-elective";
    return new String(
        elective
            + " procedure "
            + getName()
            + " id "
            + getId()
            + " with description "
            + getDescription()
            + " and basic cost "
            + getCost());
  }
}
