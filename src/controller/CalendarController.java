package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Represents all the actions of a calendar controller.
 */
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
   * @param in the input stream to take in input
   */
  void goInteractive(InputStream in);
}
