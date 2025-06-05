package model;

import java.time.LocalDateTime;

public interface Event extends Comparable<Event> {

  void edit(String property, String newPropertyValue);

  LocalDateTime getStartDate();

  LocalDateTime getEndDate();

  String getSubject();

  void setSeries(EventSeries series);

  EventSeries getEventSeries();

  String toString();
}
