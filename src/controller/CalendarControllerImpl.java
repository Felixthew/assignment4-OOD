package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import model.Calendar;
import model.CalendarApp;
import view.CalendarView;

public class CalendarControllerImpl implements CalendarController {
  public static void main(String[] args) throws FileNotFoundException {
    Calendar model = new CalendarApp();
    CalendarController controller = new CalendarControllerImpl(model);
    String mode = args[0].toLowerCase();

    if (mode.contains("--mode interactive")) {
      CalendarView view = null;
      controller.goInteractive(view);
    } else if (mode.contains("--mode headless")) {
      File file = new File(mode.split("--mode headless")[0]);
      controller.goHeadless(file);
    } else {
      // whatever the default is supposed to be idk.
    }
  }

  Calendar calendar;

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
    // call the view and then the scanner
  }

  private void inputText(Scanner in) {
    String commandKey = "";
    for (int i = 0; i < 2; i++) {
      if (!in.hasNext()) {
        throw new IllegalArgumentException("Incomplete command");
      }
      commandKey += in.next();
    }
    String[] specifications = in.nextLine().toLowerCase().split(" ");
    CalendarCommand command;
    if ((commandKey).equalsIgnoreCase("create event")) {
      if (Arrays.stream(specifications).anyMatch((s) -> s.equalsIgnoreCase("repeats"))) {
        command = new CreateEventSeries(specifications);
      } else {
        command = new CreateEvent(specifications);
      }
      command.execute(calendar);
    }
    switch (commandKey) {
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
}
