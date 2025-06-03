package controller;

import java.io.File;
import java.io.FileNotFoundException;

import view.CalendarView;

public interface CalendarController {
  /**
   * Runs the controller
   *
   * @param file to be read from. If null then interactive.
   */
  void goHeadless(File file) throws FileNotFoundException;

  void goInteractive(CalendarView calendarView);
}
