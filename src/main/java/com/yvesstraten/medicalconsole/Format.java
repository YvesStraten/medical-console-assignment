package com.yvesstraten.medicalconsole;

/**
 * This class holds a selection of pretty-printing methods
 *
 * @author Yves Straten e2400068
 */
public class Format {
  /**
   * Default constructor for Format
   *
   * @throws UnsupportedOperationException always
   */
  public Format() {
    new UnsupportedOperationException();
  }

  /**
   * Get pretty-printed string in an enumerated format
   *
   * @param options string split by newlines representing each option
   * @param startRange index to start pretty-printing
   * @param endRange index to stop pretty-printing
   * @return pretty printed enumerated string
   */
  public static String enumeratedContent(String[] options, int startRange, int endRange) {
    StringBuilder builder = new StringBuilder();
    int currentIndex = 1;
    for (int i = 0; i < options.length; i++) {
      if (i == endRange) {
        break;
      }

      if (!options[i].startsWith("-") && i >= startRange) {
        builder.append("[").append(currentIndex).append("] ").append(options[i]).append("\n");
        currentIndex += 1;
      } else builder.append(options[i]).append("\n");
    }

    return builder.toString();
  }

  /**
   * Get pretty-printed string in an enumerated format
   *
   * @param options string split by newlines representing each option
   * @return pretty printed enumerated string
   */
  public static String enumeratedContent(String[] options) {
    return enumeratedContent(options, 0, options.length);
  }

  /**
   * Get pretty-printed string in an enumerated format
   *
   * @param options string representing each option
   * @param startRange index to start pretty-printing
   * @return pretty printed enumerated string
   */
  public static String enumeratedContent(String[] options, int startRange) {
    return enumeratedContent(options, startRange, options.length);
  }

  /**
   * Get pretty-printed string in an enumerated format
   *
   * @param options string representing each option
   * @param startRange index to start pretty-printing
   * @param endRange index to stop pretty-printing
   * @return pretty printed enumerated string
   */
  public static String enumeratedContent(String options, int startRange, int endRange) {
    String[] splitted = options.split("\n");
    return enumeratedContent(splitted, startRange, endRange);
  }

  /**
   * Get pretty-printed string in an enumerated format
   *
   * @param options string representing each option
   * @param startRange index to start pretty-printing
   * @return pretty printed enumerated string
   */
  public static String enumeratedContent(String options, int startRange) {
    String[] splitted = options.split("\n");
    return enumeratedContent(splitted, startRange, splitted.length);
  }

  /**
   * Get pretty-printed string in an enumerated format
   *
   * @param options string representing each option
   * @return pretty printed enumerated string
   */
  public static String enumeratedContent(String options) {
    String[] splitted = options.split("\n");
    return enumeratedContent(splitted, 0, splitted.length);
  }

  /**
   * Get pretty printed string in a bulleted format
   *
   * @param options newline split string representing available options
   * @param startRange index to start pretty printing
   * @param endRange index to stop pretty printing
   * @return pretty printed bulleted string
   */
  public static String bulletedContent(String[] options, int startRange, int endRange) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < options.length; i++) {
      if (i == endRange) break;

      if (!options[i].startsWith("[") && i >= startRange)
        builder.append("- ").append(options[i]).append("\n");
      else builder.append(options[i]).append("\n");
    }

    return builder.toString();
  }

  /**
   * Get pretty printed string in a bulleted format
   *
   * @param options string representing available options
   * @param startRange index to start pretty printing
   * @param endRange index to stop pretty printing
   * @return pretty printed bulleted string
   */
  public static String bulletedContent(String options, int startRange, int endRange) {
    String[] splitted = options.split("\n");
    return bulletedContent(splitted, startRange, endRange);
  }

  /**
   * Get pretty printed string in a bulleted format
   *
   * @param options string representing available options
   * @param startRange index to start pretty printing
   * @return pretty printed bulleted string
   */
  public static String bulletedContent(String options, int startRange) {
    String[] splitted = options.split("\n");
    return bulletedContent(options, startRange, splitted.length);
  }

  /**
   * Get pretty printed string in a bulleted format
   *
   * @param options string representing available options
   * @return pretty printed bulleted string
   */
  public static String bulletedContent(String options) {
    String[] splitted = options.split("\n");
    return bulletedContent(splitted, 0, splitted.length);
  }
}
