package controller;

import java.io.File;
import java.io.FileNotFoundException;

import model.Calendar;
import model.CalendarApp;
import view.CalendarTextViewImp;
import view.CalendarView;

public class ExecutableCalendar {
  public static void main(String[] args) throws FileNotFoundException {
    Calendar model = new CalendarApp();
    CalendarView view = new CalendarTextViewImp();
    CalendarController controller = new CalendarControllerImpl(model, view);

    if (args.length < 2) {
      throw new IllegalArgumentException("Mode not specified!");
    }

    if (args[1].equalsIgnoreCase("interactive")) {
      controller.goInteractive();
    } else if (args[1].equalsIgnoreCase("headless")) {
      File file = new File(args[2]);
      controller.goHeadless(file);
    } else {
      // program defaults to interactive
      controller.goInteractive();
    }
  }
}
