package model;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import controller.CreateEvent;
import view.CalendarTextViewImp;
import view.CalendarView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the event implementation.
 */
public class EventImpTest {

  @Test
  public void testInvalidEvents() {
    EventImp.EventBuilder event = EventImp.getBuilder()
            .startDateTime(LocalDateTime.of(1, 1, 1, 2, 0))
            .endDateTime(LocalDateTime.of(1, 1, 1, 1, 0));
    assertThrows(IllegalArgumentException.class, event::build);
  }

  @Test
  public void compareTo() {
    EventImp.EventBuilder event1 = EventImp.getBuilder()
            .allDay(LocalDate.of(2025, 6, 5));
    EventImp.EventBuilder event2 = EventImp.getBuilder()
            .startDateTime(LocalDateTime.of(2025, 6, 5, 10, 0))
            .endDateTime(LocalDateTime.of(2025, 6, 5, 11, 30));
    assertTrue(event2.build().compareTo(event1.build()) > 0);

    event1.allDay(LocalDate.of(2025, 6, 6));
    assertTrue(event1.build().compareTo(event2.build()) > 0);

    event2.allDay(LocalDate.of(2025, 6, 6));
    assertEquals(0, event1.build().compareTo(event2.build()));
    assertEquals(event1.build(), event2.build());
  }

  @Test
  public void getStartDate() {
    Event event = EventImp.getBuilder()
            .allDay(LocalDate.of(2025, 6, 6))
            .build();
    assertEquals(LocalDateTime.of(2025, 6, 6, 8, 0),
            event.getStartDate());
    assertEquals(LocalDateTime.of(2025, 6, 6, 17, 0),
            event.getEndDate());
  }

  @Test
  public void editSingularEvent() {
    Event event = EventImp.getBuilder()
            .subject("OOD")
            .startDateTime(LocalDateTime.of(2025, 6, 6, 9, 0))
            .endDateTime(LocalDateTime.of(2025, 6, 6, 10, 0))
            .build();
    event.edit("subject", "not ood");
    assertEquals("not ood: starts 2025-06-06T09:00, ends 2025-06-06T10:00",
            event.toString());
    event.edit("end", "2025-06-06T11:00");
    assertEquals("not ood: starts 2025-06-06T09:00, ends 2025-06-06T11:00",
            event.toString());
    event.edit("location", "curry");
    assertEquals("not ood: starts 2025-06-06T09:00, ends 2025-06-06T11:00, " +
            "location: curry", event.toString());
    event.edit("description", "class");
    assertEquals("not ood: starts 2025-06-06T09:00, ends 2025-06-06T11:00, " +
            "location: curry, description: class", event.toString());
    event.edit("status", "public");
    assertEquals("not ood: starts 2025-06-06T09:00, ends 2025-06-06T11:00, " +
            "location: curry, description: class, status: public", event.toString());
  }

  // tests that setting end date to before start date swaps the tow, and vice versa
  @Test
  public void testDateSwapping() {
    Event event = EventImp.getBuilder()
            .subject("OOD")
            .startDateTime(LocalDateTime.of(2025, 6, 6, 9, 0))
            .endDateTime(LocalDateTime.of(2025, 6, 6, 10, 0))
            .build();
    event.edit("end", "2025-06-06T08:00");
    assertEquals("OOD: starts 2025-06-06T08:00, ends 2025-06-06T09:00",
            event.toString());
    event.edit("start", "2025-06-06T13:00");
    assertEquals("OOD: starts 2025-06-06T09:00, ends 2025-06-06T13:00",
            event.toString());
  }

  @Test
  public void testAllDay() {
    Event event = EventImp.getBuilder()
            .subject("OOD")
            .allDay(LocalDate.of(2025, 6, 6))
            .build();
    assertEquals("OOD: starts 2025-06-06T08:00, ends 2025-06-06T17:00",
            event.toString());
  }

  @Test
  public void testAllDayViaCreate() {
    Calendar calendar = new CalendarApp();
    CalendarView view = new CalendarTextViewImp();
    CreateEvent create = new CreateEvent(
            "ood on 2025-06-05");
    create.execute(calendar, view);
    assertEquals("Calendar:\n" +
            "ood: starts 2025-06-05T08:00, ends 2025-06-05T17:00", calendar.toString());
  }

  // should throw exception b/c not enough specifications
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidEvent() {
    Calendar calendar = new CalendarApp();
    CalendarView view = new CalendarTextViewImp();
    CreateEvent create = new CreateEvent(
            "ood from 2025-06-05T09:00");
    create.execute(calendar, view);
  }
}