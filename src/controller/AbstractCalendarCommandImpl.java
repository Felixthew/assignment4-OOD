package controller;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCalendarCommandImpl implements CalendarCommand {
  String specifications;

  public AbstractCalendarCommandImpl(String specifications) {
    this.specifications = specifications;
  }

  protected static List<String> extractOptionalQuote(List<String> s) {
    if (s.get(0).charAt(0) == '\"') {
      StringBuilder enclosed = new StringBuilder();
      for (int i = 0; i < s.size(); i++) {
        String string = s.get(i);
        enclosed.append(string);
        if (string.endsWith("\"")) {
          // recombines what was in quotes to now be one string
          List<String> returnList = new ArrayList<>();
          returnList.add(enclosed.substring(1, enclosed.length() - 1));
          returnList.addAll(s.subList(i + 1, s.size()));
          return returnList;
        }
      }
    }
    return s;
  }
}
