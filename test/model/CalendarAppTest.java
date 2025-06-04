package model;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class CalendarAppTest {

  @Test
  public void testAddDuplicates() {
    Calendar calendar = new CalendarApp();
    EventImp.EventBuilder builder = EventImp.getBuilder();
    builder.subject("test");
    builder.startDate(LocalDate.of(2025, 1, 1));
    calendar.add(builder.build());

    EventImp.EventBuilder builder2 = EventImp.getBuilder();
    builder2.subject("test");
    builder.startDate(LocalDate.of(2025, 1, 1));
    assertThrows(IllegalArgumentException.class, () -> calendar.add(builder.build()));
  }

  @Test
  public void testAddEvent() {
    Calendar calendar = buildBasicTestCalerndar();

    // assert the print statement of calendar is correct
  }

  private static Calendar buildBasicTestCalerndar() {
    Calendar calendar = new CalendarApp();
    EventImp.EventBuilder builder = EventImp.getBuilder();
    builder.subject("test");
    builder.startDate(LocalDate.of(2025, 1, 1));
    calendar.add(builder.build());
    builder.subject("Test other");
    calendar.add(builder.build());
    builder.startDate(LocalDate.of(2025, 1, 2));
    calendar.add(builder.build());
    return calendar;
  }

  @Test
  public void findEvent() {
    Calendar calendar = buildBasicTestCalerndar();
    EventImp expected = EventImp.getBuilder()
            .startDate(LocalDate.of(2025, 1, 2))
            .subject("Test other")
            .build();
    // assert the find result
  }

  @Test
  public void findEvents() {

  }

  @Test
  public void testPrintEvents() {
    Calendar calendar = buildBasicTestCalerndar();
    // assert the string
  }

  @Test
  public void testShowStatus() {

  }

}