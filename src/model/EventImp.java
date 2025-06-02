package model;

public class EventImp implements Event, Comparable<Event> {
  EventSeries series;
  String subject;

  Event() {

  }

  @Override
  public int compareTo(Event o) {
    return 0;
  }

  @Override
  public Event edit(String property, String newPropertyValue) {
    return null;
  }

  // builder
}
