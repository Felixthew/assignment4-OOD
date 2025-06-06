package controller;

import java.io.File;
import java.io.FileNotFoundException;

import model.Calendar;
import model.CalendarApp;
import view.CalendarTextViewImp;
import view.CalendarView;

/**
 * This class is just here to contain the main method to run the program.
 */
public class ExecutableCalendar {
  /**
   * Runs the program in the mode as specified by the args. Defaults to interactive.
   *
   * @param args the command line args to specify the mode. Should start with --mode ___
   * @throws FileNotFoundException    if headless is run and the file cannot be found.
   * @throws IllegalArgumentException if no mode is specified.
   */
  public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException {
    Calendar model = new CalendarApp();
    CalendarView view = new CalendarTextViewImp();
    CalendarController controller = new CalendarControllerImpl(model, view);

    if (args.length < 2) {
      throw new IllegalArgumentException("Mode not specified!");
    }

    if (args[1].equalsIgnoreCase("interactive")) {
      controller.goInteractive(System.in);
    } else if (args[1].equalsIgnoreCase("headless")) {
      File file = new File(args[2]);
      controller.goHeadless(file);
    } else {
      // program defaults to interactive
      controller.goInteractive(System.in);
    }
  }
}
