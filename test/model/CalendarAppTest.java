package model;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

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
    assertThrows(IllegalArgumentException.class, () -> calendar.add(builder.build()));
  }

  @Test
  public void testAddEvent() {
    Calendar calendar = buildBasicTestCalendar();
    System.out.println(calendar);
    // assert the print statement of calendar is correct
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
    Calendar calendar = buildBasicTestCalendar();
    calendar.add(EventImp.getBuilder()
            .subject("ood")
            .allDay(LocalDate.of(2025, 1, 3))
            .build());
    List<Event> expected = new ArrayList<>(List.of(
            EventImp.getBuilder()
                    .subject("test")
                    .allDay(LocalDate.of(2025, 1, 1))
                    .build()
            , EventImp.getBuilder()
                    .subject("test other")
                    .allDay(LocalDate.of(2025, 1, 1))
                    .build()
    ));
    // should work, the problem lies with junit.
    assertEquals(expected, calendar.findEvents(LocalDate.of(2025, 1, 1)));
    // assert the string
  }

  @Test
  public void testShowStatus() {
    Calendar calendar = buildBasicTestCalendar();
    assertEquals("busy", calendar.showStatus(LocalDateTime.of(2025, 1, 1, 10, 0)));
    assertEquals("free", calendar.showStatus(LocalDateTime.of(2025, 1, 1, 5, 0)));
  }

}