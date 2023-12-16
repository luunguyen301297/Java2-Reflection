package project.reflection.utility_class;

import java.util.List;

public class StringHeaderUtils {
  public static String headerBuilder(List<String> tList) {
    StringBuilder headerBuilder = new StringBuilder();
    for (String t : tList) {
      headerBuilder.append(convertToSnakeCase(t)).append("|");
    }
    headerBuilder.setLength(headerBuilder.length() - 1);
    return headerBuilder.toString();
  }

  public static String convertToSnakeCase(String input) {
    return input.replaceAll("([a-z])([A-Z]+)", "$1_$2").toUpperCase();
  }
}
