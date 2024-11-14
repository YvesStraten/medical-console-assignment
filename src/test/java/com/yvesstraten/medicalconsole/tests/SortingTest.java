package com.yvesstraten.medicalconsole.tests;

import java.util.Comparator;
import java.util.List;

/**
 * This class represents a setup for a sorting test
 *
 * @author Yves Straten e2400068
 */
public class SortingTest<T> {
  // Name of test
  private String name;
  // Comparator to use
  private Comparator<T> comparator;
  // Expected output
  private List<T> expected;

  /**
   * Constructs a sorting test
   *
   * @param name - name of test
   * @param comparator - comparator to use
   * @param expected - expected result
   */
  public SortingTest(String name, Comparator<T> comparator, List<T> expected) {
    setName(name);
    setComparator(comparator);
    setExpected(expected);
  }

  /**
   * Get name of this test
   *
   * @return name of test
   */
  public String getName() {
    return name;
  }

  /**
   * Get comparator for this test
   *
   * @return comparator for this test
   */
  public Comparator<T> getComparator() {
    return comparator;
  }

  /**
   * Get expected output when sorting with the specified {@link SortingTest#comparator}
   *
   * @return sorted list
   */
  public List<T> getExpected() {
    return expected;
  }

  /**
   * Set name of test
   *
   * @param name - name of test
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Set comparator to use in this test
   *
   * @param comparator - comparator to use
   */
  public void setComparator(Comparator<T> comparator) {
    this.comparator = comparator;
  }

  /**
   * Set expected list output of test
   *
   * @param expected - list representing expected output
   */
  public void setExpected(List<T> expected) {
    this.expected = expected;
  }
}
