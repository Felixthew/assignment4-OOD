package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import view.CalendarView;

public interface CalendarController {
  /**
   * Runs the controller, reading lines from the file. Expects an exit command at the end.
   *
   * @param file to be read from.
   */
  void goHeadless(File file) throws FileNotFoundException;

  /**
   * Runs the controller waiting for user input.
   *
   * @param in
   */
  void goInteractive(InputStream in);
}
