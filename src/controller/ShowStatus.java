package controller;

import java.time.LocalDateTime;

import model.Calendar;
import view.CalendarView;

/**
 * Shows the status of the calendar a given time. Command should follow the format "show status
 * on (date time).
 */
public class ShowStatus extends AbstractCalendarCommandImpl {

  public ShowStatus(String specifications) {
    super(specifications);
  }

  /**
   * Shows the status on the calendar at the given time as busy if there is an event
   * scheduled during that time, otherwise free.
   *
   * @param calendar the calendar to check from.
   * @param view     the view to pass the result to.
   */
  @Override
  public void execute(Calendar calendar, CalendarView view) {
    String[] s = specifications.split(" ");
    String status = calendar.showStatus(LocalDateTime.parse(s[1]));
    view.displayMessage("Status: " + status);
  }
}
