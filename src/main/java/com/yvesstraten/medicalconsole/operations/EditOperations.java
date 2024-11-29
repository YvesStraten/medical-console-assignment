package com.yvesstraten.medicalconsole.operations;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.yvesstraten.medicalconsole.Editable;
import com.yvesstraten.medicalconsole.Input;
import com.yvesstraten.medicalconsole.exceptions.ClassIsNotEditableException;

/** This class holds a collection of helper functions for editing */
public class EditOperations {
  /** 
   * Unsupported constructor 
   * @throws UnsupportedOperationException always 
  */
  public EditOperations(){
    throw new UnsupportedOperationException(
      "This class is not meant to be constructed"
    );
  }

  /**
   * Helper function to aid the application in editing objects in the service in-place
   *
   * @param toEdit object that should be edited
   * @param stdin standard in source preferably <code>System.in</code>
   * @throws ClassIsNotEditableException if the class has no {@link Editable}
   */
  public static void attemptEdit(Object toEdit, Scanner stdin) 
  throws ClassIsNotEditableException {
    Class<?> selectedClass = toEdit.getClass();
    // All fields, including private ones
    List<Field> fields = new ArrayList<Field>();

    // Get fields of superclass, if any
    Class<?> superClass = selectedClass.getSuperclass();
    while (superClass != null) {
      // Fields are usually private
      Field[] superClassFields = superClass.getDeclaredFields();
      for (Field superClassField : superClassFields) {
        fields.add(superClassField);
      }

      // Repeat process until superclasses are exhausted
      superClass = superClass.getSuperclass();
    }

    // Get fields
    Field[] fetchedFields = selectedClass.getDeclaredFields();
    for (Field fetched : fetchedFields) {
      fields.add(fetched);
    }

    List<Field> editableFiltered =
        fields
    .stream()
    .filter(field -> 
      field.getAnnotation(Editable.class) != null)
    .toList();

    if (editableFiltered.size() == 0) {
      // No Editable annotation was present
      throw new ClassIsNotEditableException();
    } else {
      for (Field editableField : editableFiltered) {
        Editable editable = editableField.getAnnotation(Editable.class);
        // Field type
        Class<?> fieldType = editableField.getType();
        char[] fieldNameChars = editableField
        .getName()
        .toCharArray();

        fieldNameChars[0] = Character
        .toUpperCase(editableField
          .getName()
          .charAt(0));

        // Standard way to name setters
        String setterName =
            editable
              .setter()
              .isEmpty() 
            ? "set" 
              + new String(fieldNameChars) 
            : editable.setter();

        boolean validInput = false;

        do {
          try {
            Method setter;

            StringBuilder messageBuilder = new StringBuilder();
            if (editable.message().isEmpty()) {
              // Annotation was not provided a message to output
              messageBuilder
                  .append("Please input new value to set for")
                  .append(" ")
                  .append(editableField.getName())
                  .append(":");
            } else {
              // Annotation was provided a message to output
              messageBuilder.append(editable.message());
            }

            String prompt = messageBuilder.toString();

            // Check the type of field, invoke the respective setter
            // with name and appropriate input from the scanner
            if (fieldType.equals(double.class)) {
              setter = selectedClass.getMethod(setterName, double.class);
              setter.invoke(toEdit, Input.getDouble(prompt, stdin));
            } else if (fieldType.equals(String.class)) {
              setter = selectedClass.getMethod(setterName, String.class);
              setter.invoke(toEdit, Input.getString(prompt, stdin));
            } else if (fieldType.equals(int.class)) {
              setter = selectedClass.getMethod(setterName, int.class);
              setter.invoke(toEdit, Input.getInt(prompt, stdin));
            } else if (fieldType.equals(char.class)) {
              setter = selectedClass.getMethod(setterName, char.class);
              setter.invoke(toEdit, Input.getChar(prompt, stdin));
            } else if (fieldType.equals(boolean.class)) {
              setter = selectedClass.getMethod(setterName, boolean.class);
              setter.invoke(toEdit, Input.getYesNoInput(prompt, stdin));
            }

            validInput = true;
          } catch (NoSuchMethodException e) {
            throw new RuntimeException("There is no setter for this field!");
          } catch (InvocationTargetException e) {
            System.err.println(e.getMessage());
          } catch (IllegalAccessException e) {
            System.err.println("Setters should be public");
          }
        } while (!validInput);
      }
      System.out.println("Edited object successfully! Results:");
      System.out.println(toEdit.toString());
      System.out.println();
    }
  }


}
