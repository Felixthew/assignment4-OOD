package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CalendarApp implements Calendar {
  List<Event> allEvents;

  public static findSubject(String s) {
    if (s.charAt(0) == '\"') {
      subject = specifications.substring(1).split("\" ")[0];
      specifications = specifications.substring(1).split("\" ")[1];
    } else {
      subject = specifications.split(" ")[0];
      specifications = specifications.replace("\"" + subject + "\" ", "");
    }
  }

}
