package com.yvesstraten.medicalconsole;

public class Format {
  public static String enumeratedContent(String[] options) {
		StringBuilder builder = new StringBuilder();
    for (int i = 0; i < options.length; i++) {
      builder.append(String.format("[%d] %s \n", i + 1, options[i]));
    }

		return builder.toString();
  }

  public static String bulletedContent(String[] options) {
		StringBuilder builder = new StringBuilder();
    for (int i = 0; i < options.length; i++) {
      builder.append("- " + options[i]);
    }

		return builder.toString();
  }
}
