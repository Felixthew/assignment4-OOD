package controller;

import model.Calendar;
import view.CalendarView;

/**
 * Represents the functionality of a calendar command.
 */
public interface CalendarCommand {
  /**
   * Executes the command on the given calendar.
   *
   * @param calendar the calendar the command runs on.
   * @param view     the view to pass any results to.
   */
  void execute(Calendar calendar, CalendarView view);
}
