package model;

import java.time.LocalDateTime;

public interface Event extends Comparable<Event> {

  void edit(String property, String newPropertyValue);

  LocalDateTime getStartDate();

  EventSeries getEventSeries();
}
