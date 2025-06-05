package model;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

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
            .startDateTime(LocalDateTime.of(2025, 6, 5, 10, 0));
    assertTrue(event2.build().compareTo(event1.build()) > 0);

    event1.allDay(LocalDate.of(2025, 6, 6));
    assertTrue(event1.build().compareTo(event2.build()) > 0);

    event2.allDay(LocalDate.of(2025, 6, 6));
    assertEquals(0, event1.build().compareTo(event2.build()));
  }

  @Test
  public void getStartDate() {
  }

  @Test
  public void editSingularEvent() {
    Event event = EventImp.getBuilder()
            .subject("OOD")
            .startDateTime(LocalDateTime.of(2025, 6, 6, 9, 0))
            .endDateTime(LocalDateTime.of(2025, 6, 6, 10, 0))
            .build();
    event.edit("subject", "not ood");
    System.out.println(event);
    // use to string to check
  }
}