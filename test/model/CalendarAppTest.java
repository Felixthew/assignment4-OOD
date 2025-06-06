package model;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * This class test the Calendar's functions work as intended.
 */
public class CalendarAppTest {

  @Test
  public void testAddDuplicates() {
    Calendar calendar = new CalendarApp();
    EventImp.EventBuilder builder = EventImp.getBuilder();
    builder.subject("test");
    builder.allDay(LocalDate.of(2025, 1, 1));
    calendar.add(builder.build());

    EventImp.EventBuilder builder2 = EventImp.getBuilder();
    builder2.subject("test");
    builder.allDay(LocalDate.of(2025, 1, 1));
    builder.description("different");
    assertThrows(IllegalArgumentException.class, () -> calendar.add(builder.build()));
  }

  @Test
  public void testAddEvent() {
    Calendar calendar = buildBasicTestCalendar();
    assertEquals("Calendar:\n" +
                    "test: starts 2025-01-01T08:00, ends 2025-01-01T17:00\n" +
                    "Test other: starts 2025-01-01T08:00, ends 2025-01-01T17:00\n" +
                    "Test other: starts 2025-01-02T08:00, ends 2025-01-02T17:00",
            calendar.toString());
  }

  private static Calendar buildBasicTestCalendar() {
    Calendar calendar = new CalendarApp();
    EventImp.EventBuilder builder = EventImp.getBuilder();
    builder.subject("test");
    builder.allDay(LocalDate.of(2025, 1, 1));
    calendar.add(builder.build());
    builder.subject("Test other");
    calendar.add(builder.build());
    builder.allDay(LocalDate.of(2025, 1, 2));
    calendar.add(builder.build());
    return calendar;
  }

  @Test
  public void findEvent() {
    Calendar calendar = buildBasicTestCalendar();
    EventImp expected = EventImp.getBuilder()
            .allDay(LocalDate.of(2025, 1, 2))
            .subject("Test other")
            .build();

    Map<String, String> specs = new HashMap<>(Map.of(
            "subject", "Test other",
            "from", "2025-01-02T08:00"
    ));
    assertEquals(expected, calendar.findEvent(specs));
  }

  @Test
  public void findEvents() {
    Calendar calendar = buildBasicTestCalendar();
    List<Event> expected = new ArrayList<>();
    EventImp.EventBuilder builder = EventImp.getBuilder();
    builder.subject("Test other");
    builder.allDay(LocalDate.of(2025, 1, 2));
    expected.add(builder.build());

    assertEquals(expected, calendar.findEvents(LocalDate.of(2025, 1, 2)));
  }

  @Test
  public void testPrintEvents() {
    Calendar calendar = new CalendarApp();
    EventImp.EventBuilder builder = EventImp.getBuilder();
    builder.subject("test");
    builder.allDay(LocalDate.of(2025, 1, 1));
    Event event1 = builder.build();
    calendar.add(event1);
    builder.subject("Test other");
    Event event2 = builder.build();
    calendar.add(event2);
    builder.allDay(LocalDate.of(2025, 1, 2));
    Event event3 = builder.build();
    calendar.add(event3);
    Event event4 = builder
            .startDateTime(LocalDateTime.of(2025, 1, 3, 7, 0))
            .endDateTime(LocalDateTime.of(2025, 1, 3, 9, 0))
            .build();
    calendar.add(event4);
    Event event5 = builder
            .allDay(LocalDate.of(2025, 1, 4))
            .build();
    calendar.add(event5);

    assertEquals(new ArrayList<>(List.of(event1, event2)),
            calendar.findEvents(LocalDate.of(2025, 1, 1)));

    assertEquals(new ArrayList<>(List.of(event3, event4)), calendar.findEvents(
            LocalDateTime.of(2025, 1, 2, 3, 0),
            LocalDateTime.of(2025, 1, 3, 12, 0)
    ));
  }

  @Test
  public void testShowStatus() {
    Calendar calendar = buildBasicTestCalendar();
    assertEquals("busy", calendar.showStatus(
            LocalDateTime.of(2025, 1, 1, 10, 0)));
    assertEquals("free", calendar.showStatus(
            LocalDateTime.of(2025, 1, 1, 5, 0)));
    Event event = EventImp.getBuilder()
            .startDateTime(LocalDateTime.of(2025, 1, 1, 4, 0))
            .endDateTime(LocalDateTime.of(2025, 1, 1, 7, 0))
            .build();
    calendar.add(event);
    assertEquals("busy", calendar.showStatus(
            LocalDateTime.of(2025, 1, 1, 5, 0)));
    event.edit("start", "2025-01-01T05:01");
    assertEquals("free", calendar.showStatus(
            LocalDateTime.of(2025, 1, 1, 5, 0)));
  }

}