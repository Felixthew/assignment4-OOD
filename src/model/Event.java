package model;

import java.time.LocalDateTime;

/**
 * Represents all the actions of an event.
 */
public interface Event extends Comparable<Event> {

  /**
   * Edits the given property of the event to the new value.
   *
   * @param property         can be, subject, start, end, location, description or status.
   * @param newPropertyValue the string representation of the new value to assign.
   */
  void edit(String property, String newPropertyValue);

  /**
   * Gets the start date as a local date time.
   *
   * @return the start date as a local date time.
   */
  LocalDateTime getStartDate();

  /**
   * Gets the end date.
   *
   * @return the end date as a local date time.
   */
  LocalDateTime getEndDate();

  /**
   * gets the subject
   *
   * @return the subject as a string.
   */
  String getSubject();

  /**
   * Sets the series of an event.
   *
   * @param series the series to assign the event
   */
  void setSeries(EventSeries series);

  /**
   * Gets the series of the event.
   *
   * @return the series of the event.
   */
  EventSeries getEventSeries();

  /**
   * Returns a string representation of the event.
   *
   * @return the string.
   */
  String toString();
}
