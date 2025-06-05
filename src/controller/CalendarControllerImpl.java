package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.Calendar;
import view.CalendarView;

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
    while (in.hasNext()) {
      inputText(in);
      in.nextLine();
    }
    // missing exit command
  }

  @Override
  public void goInteractive() {
    Scanner in = new Scanner(System.in);
    view.promptForInput();
    // try catch for graceful error handling
    inputText(in);
    view.displayCalendar(calendar);
    // call the view and then the scanner
  }

  private void inputText(Scanner in) {
    String commandKey;
    commandKey = in.next();
    if (commandKey.equals("q")) {
      System.exit(0);
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
  }

  public static String extractAndRemoveSubject(String specifications) {
    String subject;
    if (specifications.charAt(0) == '\"') {
      subject = specifications.substring(1).split("\" ")[0];
      specifications = specifications.substring(1).split("\" ")[1];
    } else {
      subject = specifications.split(" ")[0];
      specifications = specifications.replace(subject, "");
    }
    return subject;
  }
}
