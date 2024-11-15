package com.yvesstraten.medicalconsole;

public class InvalidOptionException extends Exception {
  public InvalidOptionException() {
    super();
  }

  public InvalidOptionException(String s) {
    super(s);
  }
}
