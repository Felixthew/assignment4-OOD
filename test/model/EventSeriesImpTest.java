package model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import controller.CreateEvent;
import view.CalendarTextViewImp;
import view.CalendarView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;


/**
 * This class tests teh event series implementation.
 */
public class EventSeriesImpTest {

  private Calendar calendar;
  private CalendarView view;

  @Before
  public void setUp() {
    calendar = new CalendarApp();
    view = new CalendarTextViewImp();
  }

  @Test
  public void testInvalidSeries() {
    CreateEvent create = new CreateEvent(
            "ood from 2025-06-05T09:00 to 2025-06-06T09:00 repeats u for 5 times");
    assertThrows(IllegalArgumentException.class, () -> create.execute(calendar, view));
  }

  @Test
  public void testCreateProperSeries() {
    CreateEvent create = new CreateEvent(
            "ood from 2025-06-05T09:00 to 2025-06-05T10:00 repeats u for 5 times");
    // also test the implicit inclusion of the current day in the series repeats
    create.execute(calendar, view);
    assertEquals("Calendar:\n" +
            "ood: starts 2025-06-05T09:00, ends 2025-06-05T10:00\n" +
            "ood: starts 2025-06-08T09:00, ends 2025-06-08T10:00\n" +
            "ood: starts 2025-06-12T09:00, ends 2025-06-12T10:00\n" +
            "ood: starts 2025-06-15T09:00, ends 2025-06-15T10:00\n" +
            "ood: starts 2025-06-19T09:00, ends 2025-06-19T10:00\n" +
            "ood: starts 2025-06-22T09:00, ends 2025-06-22T10:00", calendar.toString());
  }

  @Test
  public void testEditWholeSeries() {
    EventSeries series = new EventSeriesImp();
    Event event1 = EventImp.getBuilder()
            .subject("ood")
            .allDay(LocalDate.of(2025, 6, 6))
            .series(series)
            .build();
    series.add(event1);
    Event event2 = EventImp.getBuilder()
            .subject("ood")
            .allDay(LocalDate.of(2025, 6, 7))
            .series(series)
            .build();
    series.add(event2);
    Event event3 = EventImp.getBuilder()
            .subject("ood")
            .allDay(LocalDate.of(2025, 6, 8))
            .series(series)
            .build();
    series.add(event3);

    series.editAll("subject", "fundies");
    assertEquals("fundies", event1.getSubject());
    assertEquals("fundies", event2.getSubject());
    assertEquals("fundies", event3.getSubject());
  }

  @Test
  public void testEditPartOfSeries() {
    EventSeries series = new EventSeriesImp();
    Event event1 = EventImp.getBuilder()
            .subject("ood")
            .allDay(LocalDate.of(2025, 6, 6))
            .series(series)
            .build();
    series.add(event1);
    Event event2 = EventImp.getBuilder()
            .subject("ood")
            .allDay(LocalDate.of(2025, 6, 7))
            .series(series)
            .build();
    series.add(event2);
    Event event3 = EventImp.getBuilder()
            .subject("ood")
            .allDay(LocalDate.of(2025, 6, 8))
            .series(series)
            .build();
    series.add(event3);

    series.editFrom("subject", "fundies",
            LocalDateTime.of(2025, 6, 7, 1, 0));

    assertEquals("ood", event1.getSubject());
    assertEquals("fundies", event2.getSubject());
    assertEquals("fundies", event3.getSubject());
    assertEquals(event1.getEventSeries(), event2.getEventSeries());
    assertEquals(event2.getEventSeries(), event3.getEventSeries());
  }

  @Test
  public void testEditStartOnPartOfSeries() {
    EventSeries series = new EventSeriesImp();
    Event event1 = EventImp.getBuilder()
            .subject("ood")
            .allDay(LocalDate.of(2025, 6, 6))
            .series(series)
            .build();
    series.add(event1);
    Event event2 = EventImp.getBuilder()
            .subject("ood")
            .allDay(LocalDate.of(2025, 6, 7))
            .series(series)
            .build();
    series.add(event2);
    Event event3 = EventImp.getBuilder()
            .subject("ood")
            .allDay(LocalDate.of(2025, 6, 8))
            .series(series)
            .build();
    series.add(event3);

    series.editFrom("start", "2025-06-07T10:00",
            LocalDateTime.of(2025, 6, 7, 1, 0));
    assertNotEquals(event1.getEventSeries(), event2.getEventSeries());
    assertEquals(LocalDateTime.of(2025, 6, 6, 8, 0),
            event1.getStartDate());
    assertEquals(LocalDateTime.of(2025, 6, 7, 10, 0),
            event2.getStartDate());
    assertEquals(LocalDateTime.of(2025, 6, 7, 17, 0),
            event2.getEndDate());
    assertEquals(LocalDateTime.of(2025, 6, 8, 10, 0),
            event3.getStartDate());

    assertThrows(IllegalArgumentException.class, () ->
            event2.edit("end", "2025-06-08T10:00"));
  }
}