package model;

import java.time.LocalDateTime;
import java.util.List;

public interface EventSeries {

  void add(Event event);

  void editAll(String property, String newPropertyValue);

  void editFrom(String property, String newPropertyValue, LocalDateTime start);

  boolean isFullSeries();
}
