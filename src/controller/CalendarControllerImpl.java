package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import model.Calendar;
import model.CalendarApp;
import view.CalendarTextViewImp;
import view.CalendarView;

public class CalendarControllerImpl implements CalendarController {
  public static void main(String[] args) throws FileNotFoundException {
    Calendar model = new CalendarApp();
    CalendarController controller = new CalendarControllerImpl(model);
    String mode;
    if (args.length > 0) {
      mode = args[0].toLowerCase();
    } else {
      mode = "";
    }


    if (mode.contains("--mode interactive")) {
      CalendarView view = new CalendarTextViewImp();
      controller.goInteractive(view);
    } else if (mode.contains("--mode headless")) {
      File file = new File(mode.split("--mode headless")[0]);
      controller.goHeadless(file);
    } else {
      // whatever the default is supposed to be idk.
      CalendarView view = new CalendarTextViewImp();
      controller.goInteractive(view);
    }
  }

  private final Calendar calendar;

  public CalendarControllerImpl(Calendar calendar) {
    this.calendar = calendar;
  }

  @Override
  public void goHeadless(File file) throws FileNotFoundException {
    FileInputStream fileInputStream = new FileInputStream(file);
    Scanner in = new Scanner(fileInputStream);
    while (in.hasNext()) {
      inputText(in);
      in.nextLine();
    }
  }

  @Override
  public void goInteractive(CalendarView calendarView) {
    Scanner in = new Scanner(System.in);
    calendarView.promptForInput();
    // try catch for graceful error handling
    inputText(in);
    calendarView.displayCalendar(calendar);
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
    command.execute(calendar);
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
