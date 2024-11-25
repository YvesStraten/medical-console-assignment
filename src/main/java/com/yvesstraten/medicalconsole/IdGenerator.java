package com.yvesstraten.medicalconsole;

import java.util.Iterator;

/** This interface outlines methods that an Id Generator should have */
public interface IdGenerator extends Iterator<Integer> {
  /**
   * Get last dispensed id
   *
   * @return last dispensed id
   */
  public int getLastDispensedId();

  /**
   * Set last dispensed id
   *
   * @param id id to set
   */
  public void setLastDispensedId(int id);
}
