package com.yvesstraten.medicalconsole;

import java.util.Iterator;

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
