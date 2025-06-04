package model;

import java.util.List;
import java.util.Map;

public interface Calendar {

  /**
   * Adds the given event to the calendar
   *
   * @param event to be added.
   * @return the resulting Calendar.
   */
  Calendar add(Event event);

  /**
   * Finds the event that matches the specified criteria.
   *
   * @param identifiers subject, start time, and end time are all possible filters.
   * @return the event that matches the identifiers.
   * @throws IllegalArgumentException if there is not one singular matching event.
   */
  Event findEvent(Map<String, String> identifiers) throws IllegalArgumentException;

  /**
   * Finds all events in a series that matches the specified criteria.
   *
   * @param identifiers subject, start time, and end time are all possible filters.
   * @return the event that matches the identifiers.
   * @throws IllegalArgumentException if there is not one singular matching event.
   */
  List<Event> findEvents(List<String> identifiers) throws IllegalArgumentException;
}
