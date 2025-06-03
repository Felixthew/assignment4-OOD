package model;

import java.util.ArrayList;
import java.util.List;

public class EventSeriesImp implements EventSeries {
  List<Event> eventList;

  public EventSeriesImp() {
    this.eventList = new ArrayList<Event>();
  }

  public void add(Event event) {
    this.eventList.add(event);
  }

}
