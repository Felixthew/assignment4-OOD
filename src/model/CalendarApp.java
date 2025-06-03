package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CalendarApp implements Calendar {
  // field: list of Event
  // field: list of EventSeries

  public static LocalDateTime parseDateTime(String s) {
    String[] split = s.split("[:T]");
    if (s.length() != 5) {
      throw new IllegalArgumentException("invalid date format");
    }
    return LocalDateTime.of(
            Integer.parseInt(split[0]),
            Integer.parseInt(split[1]),
            Integer.parseInt(split[2]),
            Integer.parseInt(split[3]),
            Integer.parseInt(split[4])
    );
  }

  public static LocalDate parseDate(String s) {
    String[] split = s.split(":");
    if (s.length() != 3) {
      throw new IllegalArgumentException("invalid date format");
    }
    return LocalDate.of(
            Integer.parseInt(split[0]),
            Integer.parseInt(split[1]),
            Integer.parseInt(split[2])
    );
  }


}
