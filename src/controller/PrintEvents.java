package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import model.Calendar;
import model.Event;
import view.CalendarView;

/**
 * The command to print the events of calendar in a specified timeframe.
 * Command should follow the format "print events on (date)" or "print events from
 * (date time) to (date time).
 */
public class PrintEvents extends AbstractCalendarCommandImpl {

  /**
   * Constructs the command with the specifications.
   *
   * @param specifications the specifications of what events to print.
   */
  public PrintEvents(String specifications) {
    super(specifications);
  }

  /**
   * Prints the events of the specified time frame.
   *
   * @param calendar the calendar the command runs on.
   * @param view     the view to pass any results to.
   */
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
      view.displayEvent(event);
    }
  }
}
