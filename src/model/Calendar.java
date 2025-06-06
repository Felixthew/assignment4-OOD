package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * this is an interface that represents all the function of a calendar that can store and query events.
 */
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
   * @param identifiers a map of identifying details with possible keys "subject", "from", and "to"
   * @return the event that matches the identifiers.
   * @throws IllegalArgumentException if there is not one singular matching event.
   */
  Event findEvent(Map<String, String> identifiers) throws IllegalArgumentException;

  /**
   * Finds all events that start on a given day.
   *
   * @param day the given day.
   * @return a list of events on that day.
   */
  List<Event> findEvents(LocalDate day);

  /**
   * Finds all events that start in the given time frame.
   *
   * @param start of the time frame.
   * @param end   of the time frame.
   * @return a list of all events in the time frame.
   */
  List<Event> findEvents(LocalDateTime start, LocalDateTime end);

  /**
   * Returns whether the user has something scheduled at the given time.
   *
   * @param dateTime the time to check.
   * @return status of the user (busy or free)
   */
  String showStatus(LocalDateTime dateTime);
}
