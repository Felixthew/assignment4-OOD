package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventSeriesImp implements EventSeries {
  List<Event> eventList;

  public EventSeriesImp() {
    this.eventList = new ArrayList<Event>();
  }

  @Override
  public void add(Event event) {
    this.eventList.add(event);
  }

  @Override
  public void editAll(String property, String newPropertyValue) {
    editFrom(property, newPropertyValue, LocalDateTime.MIN);
  }

  @Override
  public void editFrom(String property, String newPropertyValue, LocalDateTime start) {
    // I believe if start or end time is edited it's supposed to split the series
    EventSeries newSeries = new EventSeriesImp();

    Collections.sort(eventList);
    List<Event> removals = new ArrayList<>(); // to avoid CME
    for (Event event : eventList) {
      if (!start.isAfter(event.getStartDate())) {
        if (property.equals("start")) {
          newSeries.add(event);
          removals.add(event);
          event.setSeries(newSeries);
          newPropertyValue = event.getStartDate().toString().split("T")[0] + "T"
                  + newPropertyValue.split("[tT]")[1];
        } else if (property.equals("end")) {
          newPropertyValue = event.getEndDate().toString().split("T")[0] + "T"
                  + newPropertyValue.split("[tT]")[1];
        }
        event.edit(property, newPropertyValue);
      }
    }
    for (Event event : removals) {
      eventList.remove(event);
    }
  }


}
