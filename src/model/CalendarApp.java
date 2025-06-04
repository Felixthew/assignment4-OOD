package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CalendarApp implements Calendar {
  List<Event> allEvents;

  @Override
  public Calendar add(Event event) {
    return null;
  }

  @Override
  public Event findEvent(List<String> identifiers) throws IllegalArgumentException {
    return null;
  }

  @Override
  public List<Event> findEvents(List<String> identifiers) throws IllegalArgumentException {
    return List.of();
  }
}
