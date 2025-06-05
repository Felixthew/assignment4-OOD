package model;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertThrows;

public class EventImpTest {

  @Test
  public void testInvalidEvents() {

  }

  @Test
  public void compareTo() {
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