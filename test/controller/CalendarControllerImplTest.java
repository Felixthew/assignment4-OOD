package controller;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.Calendar;
import model.CalendarApp;
import view.CalendarTextViewImp;
import view.CalendarView;

import static org.junit.Assert.assertEquals;

/**
 * This class tests CalendarControllerImpl by inputting through the controller.
 */
public class CalendarControllerImplTest {

  @Test
  public void goHeadless() throws FileNotFoundException {
    Calendar calendar = new CalendarApp();
    CalendarView view = new CalendarTextViewImp();
    CalendarController controller = new CalendarControllerImpl(calendar, view);
    controller.goHeadless(new File("commands.txt"));

    assertEquals("Calendar:\n" +
                    "hello: starts 2025-06-05T09:50, ends 2025-06-05T11:30, location: mugar\n" +
                    "hello: starts 2025-06-07T09:50, ends 2025-06-07T11:30, location: mugar\n" +
                    "hello: starts 2025-06-08T09:50, ends 2025-06-08T11:30, location: snell\n" +
                    "hello: starts 2025-06-12T09:50, ends 2025-06-12T11:30, location: mugar\n" +
                    "hello: starts 2025-06-14T09:50, ends 2025-06-14T11:30, location: mugar\n" +
                    "hello: starts 2025-06-15T10:00, ends 2025-06-15T11:30, location: shillman\n" +
                    "hello: starts 2025-06-19T10:00, ends 2025-06-19T11:30, location: shillman\n" +
                    "hello: starts 2025-06-21T10:00, ends 2025-06-21T11:30, location: shillman\n" +
                    "hello: starts 2025-06-22T10:00, ends 2025-06-22T11:30, location: shillman\n" +
                    "hello: starts 2025-06-26T10:00, ends 2025-06-26T11:30, location: shillman\n" +
                    "hello: starts 2025-06-28T10:00, ends 2025-06-28T11:30, location: shillman\n" +
                    "hello: starts 2025-06-29T10:00, ends 2025-06-29T11:30, location: shillman\n" +
                    "hello: starts 2025-07-03T10:00, ends 2025-07-03T11:30, location: shillman",
            calendar.toString());
  }
}