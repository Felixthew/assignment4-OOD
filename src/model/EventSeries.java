package model;

import java.time.LocalDateTime;

/**
 * This interface represents all methods of an event series that can store and modify
 * its own events.
 */
public interface EventSeries {

  /**
   * Adds an event to the event series.
   *
   * @param event to be added
   */
  void add(Event event);

  /**
   * Changes the specified property to the new value for all the events in the series.
   *
   * @param property         event property to change.
   * @param newPropertyValue the value to change it to as a string.
   */
  void editAll(String property, String newPropertyValue);

  /**
   * Changes the specified property to the new value for all events in the series that fall
   * after the start date.
   *
   * @param property         the property to change.
   * @param newPropertyValue the value to change the property to.
   * @param start            date time where in the series the changes should start to occur.
   */
  void editFrom(String property, String newPropertyValue, LocalDateTime start);

  /**
   * Indicates whether a series has mor ethan one event.
   *
   * @return true if more than one event, otherwise false.
   */
  boolean isFullSeries();
}
