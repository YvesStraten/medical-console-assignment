package com.yvesstraten.medicalconsole.operations;

import java.util.List;
import java.util.stream.Stream;

import com.yvesstraten.medicalconsole.Format;

/** 
 * This class holds a collection of helper functions for listing
 *
 * @author Yves Straten e2400068
*/
public class ListOperations {
  /** 
   * Unsupported constructor 
   * @throws UnsupportedOperationException always 
  */
  public ListOperations(){
    throw new UnsupportedOperationException(
      "This class is not meant to be constructed"
    );
  }

  /**
   * Gets details of provided object stream
   *
   * @param <T> type of objects present in the stream
   * @param stream stream to get details from
   * @param name name that should be included in final string
   * @return String containing details of objects in the stream
   */
  public static <T> String getObjectStreamDetails(Stream<T> stream, 
    String name) {
    // Using .count() is a terminal operation,
    // as such, the stream is converted to a list
    // first to get its size and return an appropriate message
    List<T> objects = stream.toList();
    if (objects.size() == 0) {
      return new String("There are no " + name + " for this service");
    }

    // Get every detail of an object, and append to builder
    StringBuilder builder = new StringBuilder("The following " 
      + name 
      + " are available \n");

    objects
      .stream()
      .map(Object::toString)
      .forEach(string -> builder.append(string)
        .append("\n"));

    return builder.toString();
  }

  /**
   * Print provided object stream details to the console
   *
   * @param streamFrom stream to get details from
   * @param name name to be added to description
   * @param <T> type of stream
   * @see #getObjectStreamDetails(Stream, String)
   */
  public static <T> void listObjectGroup(Stream<T> streamFrom, String name) {
    String output = getObjectStreamDetails(streamFrom, name);
    System.out.println(Format.enumeratedContent(output, 1));
  }
}
