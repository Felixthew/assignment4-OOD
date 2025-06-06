package view;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;

import model.Calendar;
import model.CalendarApp;
import model.Event;
import model.EventImp;

import static org.junit.Assert.assertTrue;

/**
 * Test class for the text view ui.
 */
public class CalendarTextViewImpTest {

  private CalendarTextViewImp view;
  private ByteArrayOutputStream outContent;
  private Calendar cal;
  private Event event1;

  @Before
  public void setUp() {
    view = new CalendarTextViewImp();
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    cal = new CalendarApp();
    event1 = EventImp.getBuilder().subject("Lunch")
            .startDateTime(LocalDateTime.of(2025, 6, 5, 12, 30))
            .endDateTime(LocalDateTime.of(2025, 6, 5, 13, 30))
            .build();
    cal.add(event1);
  }

  @Test
  public void testPrompt() {
    view.promptForInput();

    assertTrue(outContent.toString().contains("Enter your input:"));
  }

  @Test
  public void testDisplayError() {
    view.displayError("Cannot span multiple days!");

    assertTrue(outContent.toString().contains("Error: Cannot span multiple days!"));
  }

  @Test
  public void testDisplayCalendar() {
    Event event2 = EventImp.getBuilder().subject("Class")
            .allDay(LocalDate.of(2025, 6, 8)).build();
    cal.add(event2);
    view.displayCalendar(cal);
    assertTrue(outContent.toString().contains("Calendar:\nLunch: starts 2025-06-05T12:30, ends 2025-06-05T13:30\n" +
            "Class: starts 2025-06-08T08:00, ends 2025-06-08T17:00"));
  }

  @Test
  public void testDisplayEvent() {
    view.displayEvent(event1);
    assertTrue(outContent.toString().contains("• Lunch: starts 2025-06-05T12:30, " +
            "ends 2025-06-05T13:30"));
  }

}