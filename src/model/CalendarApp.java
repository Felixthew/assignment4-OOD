package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CalendarApp implements Calendar {
  List<Event> allEvents;

  public CalendarApp() {
    this.allEvents = new ArrayList<>();
  }

  @Override
  public Calendar add(Event event) {
    if (allEvents.stream().anyMatch((e) -> e.equals(event))) {
      throw new IllegalArgumentException("Cannot have duplicates");
    }
    this.allEvents.add(event);
    Collections.sort(allEvents);
    return this;
  }

  @Override
  public Event findEvent(Map<String, String> identifiers) throws IllegalArgumentException {
    List<Event> filteredEvents = new ArrayList<>(allEvents);
    for (Map.Entry<String, String> indentifierPair : identifiers.entrySet()) {
      String key = indentifierPair.getKey();
      String value = indentifierPair.getValue();
      switch (key) {
        case "subject":
          filteredEvents = filteredEvents.stream().filter((e) ->
                  e.getSubject().equals(value)).collect(Collectors.toList());
          ;
          break;
        case "from":
          filteredEvents = filteredEvents.stream().filter((e) ->
                  e.getStartDate().equals(LocalDateTime.parse(value))).collect(Collectors.toList());
          break;
        case "to":
          filteredEvents = filteredEvents.stream().filter((e) ->
                  e.getEndDate().equals(LocalDateTime.parse(value))).collect(Collectors.toList());
          break;
      }
    }
    if (filteredEvents.size() != 1) {
      throw new IllegalArgumentException("No unique event with those specifications");
    }
    return filteredEvents.get(0);
  }

  @Override
  public List<Event> findEvents(LocalDate day) {
    return findEvents(LocalDateTime.of(
                    day.getYear(), day.getMonthValue(), day.getDayOfMonth(), 0, 0),
            LocalDateTime.of(
                    day.getYear(), day.getMonthValue(), day.getDayOfMonth(), 23, 59
            ));
  }

  @Override
  public List<Event> findEvents(LocalDateTime start, LocalDateTime end) {
    List<Event> eventsMatched = new ArrayList<>();
    for (Event event : allEvents) {
      if (!event.getStartDate().isBefore(start) && !event.getStartDate().isAfter(end)) {
        eventsMatched.add(event);
      }
    }
    return eventsMatched;
  }

  @Override
  public String showStatus(LocalDateTime dateTime) {
    for (Event event : allEvents) {
      if (!event.getStartDate().isAfter(dateTime) && !event.getEndDate().isBefore(dateTime)) {
        return "busy";
      }
    }
    return "free";
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Calendar:");
    for (Event event : allEvents) {
      sb.append("\n");
      sb.append(event.toString());
    }
    return sb.toString();
  }
}
