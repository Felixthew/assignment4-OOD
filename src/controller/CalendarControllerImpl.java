package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import model.Calendar;
import view.CalendarView;

/**
 * Represents a calendar controller that utilizes a scanner to read and process inputs
 * from either a file or System.in
 */
public class CalendarControllerImpl implements CalendarController {

  private final Calendar calendar;
  private final CalendarView view;

  public CalendarControllerImpl(Calendar calendar, CalendarView view) {
    this.calendar = calendar;
    this.view = view;
  }

  @Override
  public void goHeadless(File file) throws FileNotFoundException {
    FileInputStream fileInputStream = new FileInputStream(file);
    Scanner in = new Scanner(fileInputStream);
    boolean run = true;
    while (in.hasNext() && run) {
      run = inputText(in);
    }
    if (run) {
      view.displayError("No exit command found!");
    }
  }

  @Override
  public void goInteractive(InputStream in) {
    Scanner scanner = new Scanner(in);
    boolean run = true;
    while (run) {
      try {
        view.promptForInput();
        run = inputText(scanner);
      } catch (Exception e) {
        view.displayError(e.getMessage());
      }
    }
  }

  private boolean inputText(Scanner in) {
    String commandKey;
    commandKey = in.next();
    if (commandKey.equalsIgnoreCase("q")
            || commandKey.equalsIgnoreCase("quit")) {
      return false;
    }
    commandKey += " " + in.next();

    String specifications = in.nextLine().toLowerCase().strip();
    CalendarCommand command;
    switch (commandKey) {
      case "create event":
        command = new CreateEvent(specifications);
        break;
      case "edit event":
        command = new EditEvent(specifications);
        break;
      case "edit events":
        command = new EditEvents(specifications);
        break;
      case "edit series":
        command = new EditSeries(specifications);
        break;
      case "print events":
        command = new PrintEvents(specifications);
        break;
      case "show status":
        command = new ShowStatus(specifications);
        break;
      default:
        throw new IllegalArgumentException("Invalid command");
    }
    command.execute(calendar, view);

    if (!commandKey.equals("print events") && !commandKey.equals("show status")) {
      view.displayCalendar(calendar);
    }
    return true;
  }
}
