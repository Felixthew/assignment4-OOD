package controller;

import java.io.File;

public interface CalendarController {
  /**
   * Runs the controller
   *
   * @param file to be read from. If null then interactive.
   */
  void go(File file);
}
