package controller;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import model.Calendar;
import model.CalendarApp;
import model.Event;
import model.EventImp;
import view.CalendarTextViewImp;
import view.CalendarView;

import static org.junit.Assert.assertEquals;

/**
 * This class tests CalendarControllerImpl by inputting through the controller.
 */
public class CalendarControllerImplTest {

  public class MockCalendar implements Calendar {
    final StringBuilder log;

    public MockCalendar(StringBuilder log) {
      this.log = Objects.requireNonNull(log);
    }

    public Calendar add(Event event) {
      log.append("added " + event.toString());
      return new CalendarApp();
    }

    public Event findEvent(Map<String, String> identifiers) throws IllegalArgumentException {
      log.append("found event " + identifiers.get("subject"));
      return EventImp.getBuilder().build();
    }

    public List<Event> findEvents(LocalDate day) {
      log.append("found events on " + day.toString());
      return new ArrayList<>();
    }

    public List<Event> findEvents(LocalDateTime start, LocalDateTime end) {
      log.append("found events on " + start.toString() + " to " + end.toString());
      return new ArrayList<>();
    }

    public String showStatus(LocalDateTime dateTime) {
      log.append("status for time " + dateTime.toString());
      return "";
    }

  }

  @Test
  public void testInputs() {
    CalendarView view = new CalendarTextViewImp();

    StringBuilder log = new StringBuilder();
    Calendar cal = new MockCalendar(log);

    CalendarController controller = new CalendarControllerImpl(cal, view);

    String myString = "create event Dinner from 2025-06-01T17:00 to 2025-06-01T18:30";
    InputStream in = new ByteArrayInputStream(myString.getBytes());

    controller.goInteractive(in);
    assertEquals("added Dinner: starts 2025-06-01T17:00, ends 2025-06-01T18:30",
            log.toString());
  }

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