package com.yvesstraten.medicalconsole;

import java.util.Arrays;

public class Format {
  public static String enumeratedContent(String[] options, int startRange, int endRange) {
    StringBuilder builder = new StringBuilder();
		int currentIndex = 1;
    for (int i = 0; i < options.length; i++) {
      if (!options[i].startsWith("-") && i >= startRange) {
				builder.append(String.format("[%d] %s\n", currentIndex, options[i]));
				currentIndex += 1;
			}
			else
				builder.append(options[i]).append("\n");
    }

    return builder.toString();
  }

  public static String enumeratedContent(String[] options) {
    return enumeratedContent(options, 0, options.length);
  }

  public static String enumeratedContent(String[] options, int startRange) {
    return enumeratedContent(options, startRange, options.length);
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
