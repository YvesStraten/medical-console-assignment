package com.yvesstraten.medicalconsole;

/** 
 * This class holds a selection of pretty-printing methods 
 * @author Yves Straten e2400068
*/
public class Format {
	/** 
	 * Default constructor for Format
	 * @throws UnsupportedOperationException always 
	*/
	public Format(){
		new UnsupportedOperationException();
	}

	/** 
	 * Pretty-prints content in an enumerated format to the console
	*/
  public static String enumeratedContent(String[] options, int startRange, int endRange) {
    StringBuilder builder = new StringBuilder();
    int currentIndex = 1;
    for (int i = 0; i < options.length; i++) {
      if (!options[i].startsWith("-") && i >= startRange) {
        builder.append("[").append(currentIndex).append("] ").append(options[i]).append("\n");
        currentIndex += 1;
      } else builder.append(options[i]).append("\n");
    }

    return builder.toString();
  }

  public static String enumeratedContent(String[] options) {
    return enumeratedContent(options, 0, options.length);
  }

  public static String enumeratedContent(String options, int startRange, int endRange) {
    String[] splitted = options.split("\n");
    return enumeratedContent(splitted, startRange, endRange);
  }

  public static String enumeratedContent(String options, int startRange) {
    String[] splitted = options.split("\n");
    return enumeratedContent(splitted, startRange, splitted.length);
  }

  public static String enumeratedContent(String options) {
    String[] splitted = options.split("\n");
    return enumeratedContent(splitted, 0, splitted.length);
  }

  public static String enumeratedContent(String[] options, int startRange) {
    return enumeratedContent(options, startRange, options.length);
  }

	/** 
	 * Pretty prints content in a bulleted format to the console
	*/
  public static String bulletedContent(String[] options, int startRange, int endRange) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < options.length; i++) {
      if (!options[i].startsWith("[") && i >= startRange)
        builder.append("- ").append(options[i]).append("\n");
      else builder.append(options[i]).append("\n");
    }

    return builder.toString();
  }

  public static String bulletedContent(String options, int startRange, int endRange) {
    String[] splitted = options.split("\n");
    return bulletedContent(splitted, startRange, endRange);
  }

  public static String bulletedContent(String options, int startRange) {
    String[] splitted = options.split("\n");
    return bulletedContent(options, startRange, splitted.length);
  }

  public static String bulletedContent(String options) {
    String[] splitted = options.split("\n");
    return bulletedContent(splitted, 0, splitted.length);
  }
}
