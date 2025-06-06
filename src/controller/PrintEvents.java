package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import model.Calendar;
import model.Event;
import view.CalendarView;

public class PrintEvents extends AbstractCalendarCommandImpl {

  public PrintEvents(String specifications) {
    super(specifications);
  }

  @Override
  public void execute(Calendar calendar, CalendarView view) {
    String[] specs = specifications.split(" ");
    List<Event> events;
    if (specs[0].equals("on")) {
      events = calendar.findEvents(LocalDate.parse(specs[1]));
    } else if (specs[0].equals("from")) {
      events = calendar.findEvents(LocalDateTime.parse(specs[1]), LocalDateTime.parse(specs[3]));
    } else {
      throw new IllegalArgumentException("Invalid command");
    }
    for (Event event : events) {
      // should go through view
      view.displayEvent(event);
    }
  }
}
