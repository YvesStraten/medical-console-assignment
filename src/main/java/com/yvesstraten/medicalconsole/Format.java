package com.yvesstraten.medicalconsole;

import java.util.Arrays;

public class Format {
  public static String enumeratedContent(String[] options) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < options.length; i++) {
      if (!options[i].startsWith("-"))
        builder.append(String.format("[%d] %s \n", i + 1, options[i]));
			else 
				builder.append(options[i]).append("\n");
    }

    return builder.toString();
  }

  public static String enumeratedContent(String[] options, int startRange, int endRange) {
    String[] sliced = Arrays.copyOfRange(options, startRange, endRange);
    return enumeratedContent(sliced);
  }

  public static String enumeratedContent(String[] options, int startRange) {
    String[] sliced = Arrays.copyOfRange(options, startRange, options.length);
    return enumeratedContent(sliced);
  }

  public static String bulletedContent(String[] options) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < options.length; i++) {
			if(!options[i].startsWith("["))
      	builder.append("- " + options[i]);
			else 
				builder.append(options[i]).append("\n");
    }

    return builder.toString();
  }
}
